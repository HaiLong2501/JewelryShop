---

description: "Task list for Facebook login feature"
---

# Tasks: Đăng nhập bằng Facebook

**Input**: Design documents from `/specs/001-facebook-login/`

**Prerequisites**: plan.md, spec.md, research.md, data-model.md, contracts/facebook-login-endpoints.md, quickstart.md

**Tests**: Spec không yêu cầu test tự động (project chưa có test framework) → không có task viết test; mỗi phase kết thúc bằng kiểm tra thủ công theo [quickstart.md](./quickstart.md).

**Organization**: Tasks are grouped by user story to enable independent implementation and testing of each story.

## Format: `[ID] [P?] [Story] Description`

- **[P]**: Can run in parallel (different files, no dependencies)
- **[Story]**: Which user story this task belongs to (US1, US2, US3)
- Mọi đường dẫn tính từ thư mục gốc repo; mã nguồn nằm trong `PNJShop/`

## Quy ước chung cho mọi task

- Giữ phong cách code hiện có: servlet trong `controller`, truy vấn JDBC (`PreparedStatement`) trong `dal` kế thừa `DBContext` (field `connection`), model trong `model`.
- Không ghi `access_token`, `code`, `FB_APP_SECRET` ra log hay ra trang (contract §5).
- Không sửa `LoginServlet`, `UserGoogleDAO`, `Constants` (plan: không đổi luồng đăng nhập thường/Google).
- Model `User` đã có field `id` (String, dùng cho Google) — **không dùng lại**; dùng field mới `facebookID`.

---

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Chuẩn bị cơ sở dữ liệu và cấu hình

- [ ] T001 Create migration file `PNJShop/database/001-add-facebookID.sql` containing exactly: `ALTER TABLE dbo.tblUsers ADD facebookID NVARCHAR(64) NULL;` then `CREATE UNIQUE INDEX UX_tblUsers_facebookID ON dbo.tblUsers(facebookID) WHERE facebookID IS NOT NULL;` (column must be NULL-able, max 64, added at the end of the table; unique only when not null — data-model.md)
- [ ] T002 [P] Create `PNJShop/src/java/config/FacebookConfig.java` (package `config`): static methods `getAppId()`, `getAppSecret()`, `getRedirectUri()` reading `System.getenv("FB_APP_ID")`, `"FB_APP_SECRET"`, `"FB_REDIRECT_URI"`; `isConfigured()` returning true only when all three are non-blank; constant `GRAPH_VERSION = "v21.0"`; constants `DIALOG_URL = "https://www.facebook.com/" + GRAPH_VERSION + "/dialog/oauth"`, `TOKEN_URL = "https://graph.facebook.com/" + GRAPH_VERSION + "/oauth/access_token"`, `ME_URL = "https://graph.facebook.com/" + GRAPH_VERSION + "/me"`; constant `SCOPE = "public_profile,email"` (research R2, R5)

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Model, DAO và servlet khung mà mọi user story đều cần

**⚠️ CRITICAL**: No user story work can begin until this phase is complete

- [ ] T003 [P] Add field `private String facebookID;` with `getFacebookID()` / `setFacebookID(String)` to `PNJShop/src/java/model/User.java`; do not change any existing constructor; add `facebookID` to `toString()` (data-model.md "Model Java User")
- [ ] T004 [P] Create `PNJShop/src/java/model/FacebookProfile.java` (package `model`) with fields `id`, `name`, `email` (all String), no-arg constructor, getters/setters; `email` may be null (data-model.md "FacebookProfile")
- [ ] T005 [P] Create `PNJShop/src/java/dal/UserFacebookDAO.java` (package `dal`, does NOT extend DBContext) using `org.apache.http.client.fluent.Request` and `com.google.gson`: method `String getToken(String code)` → GET `FacebookConfig.TOKEN_URL` with URL-encoded query params `client_id`, `redirect_uri`, `client_secret`, `code`, returns `access_token` from JSON; method `FacebookProfile getProfile(String accessToken)` → GET `FacebookConfig.ME_URL + "?fields=id,name,email&access_token=" + URLEncoder.encode(token, UTF_8)`, parsed with Gson into `FacebookProfile`; use `.connectTimeout(10000).socketTimeout(10000)` and read response as UTF-8 so Vietnamese names are correct; throw `IOException` on missing `access_token`/`id` (contract §5, research R1)
- [ ] T006 Add a private helper `User mapUser(ResultSet rs)` in `PNJShop/src/java/dal/UserDAO.java` that builds `User` the same way existing methods do (`new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getBoolean(7), rs.getString(8), rs.getString(9))`) and then sets `facebookID` from `rs.getString("facebookID")`; refactor existing `checkEmail(String email)` to use `mapUser` so the returned user carries `facebookID` (needed by T017); add `User findByID(int userID)` (`select * from tblUsers where userID = ?`) and `User findByFacebookID(String facebookID)` (`select * from tblUsers where facebookID = ?`), both returning null when not found (research R8, data-model.md)
- [ ] T007 Create `PNJShop/src/java/controller/FacebookLoginServlet.java` with `@WebServlet(name = "FacebookLoginServlet", urlPatterns = {"/login-facebook"})`, `doGet` only. Implement the **start** branch (no `code`/`error`/`state` param): if `!FacebookConfig.isConfigured()` → redirect `login?service=login&fbError=unavailable`; else create `state` with `SecureRandom` 32 bytes + `Base64.getUrlEncoder().withoutPadding()`, store session attrs `fbState` and `fbRedirect` (= `safeRedirect(request, request.getParameter("redirectUrl"))`), redirect to `FacebookConfig.DIALOG_URL` with URL-encoded `client_id`, `redirect_uri`, `state`, `scope`, `response_type=code`. Add private `String safeRedirect(HttpServletRequest req, String url)`: blank → `"home"`; contains a backslash → `"home"`; parse with `java.net.URI` — if it has a scheme or host, accept only when scheme is `http`/`https` AND host equals `req.getServerName()` AND effective port (`-1` → 80/443 by scheme) equals `req.getServerPort()`, then return `uri.getRawPath()` plus `"?" + uri.getRawQuery()` when the query is not null; otherwise `"home"`; relative url starting with `//` → `"home"`, other relative url → returned as-is; `URISyntaxException` → `"home"` (contract §1, research R3, R4 — existing pages send `window.location.href`, an absolute URL)
- [ ] T008 Implement the **callback** guard in `PNJShop/src/java/controller/FacebookLoginServlet.java`: when `state` param is present, read and immediately remove session attrs `fbState`/`fbRedirect`; if session `fbState` is null or not equal to param `state` → redirect `login?service=login&fbError=invalid`; if param `error` present → redirect `login?service=login&fbError=cancelled`; wrap everything after these checks (token, profile, account matching, session) in `try { … } catch (IOException | RuntimeException e)` → redirect `login?service=login&fbError=failed`, logging only `e.getClass().getSimpleName() + ": " + e.getMessage()` (never token/code) (contract §2, FR-003, FR-010, SC-003)
- [ ] T009 Add Facebook error messages to `PNJShop/web/jsp/Login.jsp`: use `<c:choose>` on `param.fbError` with exactly the seven values and Vietnamese messages from contracts/facebook-login-endpoints.md §3 (`cancelled`, `noemail`, `linked`, `locked`, `invalid`, `failed`, `unavailable`), rendered inside the existing `alert alert-danger` markup; unknown values render nothing and the raw param is never printed (FR-010)

**Checkpoint**: Foundation ready — chạy T001 trên SQL Server; build thành công; mở `/pnj/login-facebook?code=abc&state=xyz` → thông báo "Phiên đăng nhập không hợp lệ" (quickstart #8)

---

## Phase 3: User Story 1 - Khách hàng mới đăng nhập bằng Facebook (Priority: P1) 🎯 MVP

**Goal**: Khách chưa có tài khoản bấm nút Facebook, được tạo tài khoản và đăng nhập, quay về trang trước đó.

**Independent Test**: quickstart #1, #2, #3, #7, #9, #9b, #11, #12, #13, #14

- [ ] T010 [US1] Add `void insertFacebookUser(User u)` to `PNJShop/src/java/dal/UserDAO.java`: `INSERT INTO [dbo].[tblUsers] ([fullName],[password],[roleID],[email],[activate],[UserName],[facebookID]) VALUES (?,?,?,?,?,?,?)` with `password` = NULL (`ptm.setNull(2, Types.NVARCHAR)`), `roleID` = 2, `activate` = true, `UserName` = email (data-model.md, FR-004)
- [ ] T011 [US1] Implement account matching for new users in `PNJShop/src/java/controller/FacebookLoginServlet.java` callback after the guard: `profile = new UserFacebookDAO().getProfile(new UserFacebookDAO().getToken(code))`; **first** `u = uDao.findByFacebookID(profile.getId())` — this lookup must run before any email lookup or insert so returning users never create duplicates (FR-005, FR-006); if null and `profile.getEmail()` is blank → redirect `login?service=login&fbError=noemail` (Clarification Q2); if null and `uDao.checkEmail(email)` is null → build `User` (name, email, facebookID) and call `insertFacebookUser`, then `u = uDao.findByFacebookID(profile.getId())`; if `u` is still null (insert failed — `UserDAO` swallows `SQLException`) → redirect `login?service=login&fbError=failed` (FR-004, SC-003)
- [ ] T012 [US1] Finish login in `PNJShop/src/java/controller/FacebookLoginServlet.java`: `User sessionUser = uDao.findByID(u.getUserID())` (always reload from DB — research R8); `session.setAttribute("user", sessionUser)` — this replaces any user already in the session, never merges (edge case "đang đăng nhập sẵn"); redirect to `admin` if `roleID == 1`, otherwise to saved `fbRedirect` (already sanitized) or `home` (FR-008)
- [ ] T013 [P] [US1] Add a "Đăng nhập bằng Facebook" button next to the Google button in `PNJShop/web/jsp/Login.jsp` (inside `.login-google` div): `<a class="btn btn-primary" href="<%= request.getContextPath() %>/login-facebook?redirectUrl=<%= java.net.URLEncoder.encode(redirectUrl == null ? "" : redirectUrl, "UTF-8") %>">` styled with Facebook blue `#1877F2` (FR-001, contract §4)
- [ ] T014 [P] [US1] Add the same Facebook button in `PNJShop/web/jsp/Register.jsp` near the commented-out Google button (around line 127, before `</form>`), linking to `<%= request.getContextPath() %>/login-facebook` (FR-001)

**Checkpoint**: User Story 1 hoạt động độc lập — có thể demo MVP

---

## Phase 4: User Story 2 - Khách hàng cũ đăng nhập lại bằng Facebook (Priority: P2)

**Goal**: Đăng nhập lại vào đúng tài khoản cũ; tài khoản bị khoá bị từ chối.

**Independent Test**: quickstart #4, #5, #10

- [ ] T015 [US2] In `PNJShop/src/java/controller/FacebookLoginServlet.java`, after `u` is resolved and before setting the session: if `!u.getActivate()` → redirect `login?service=login&fbError=locked` without touching the session `user` (FR-009)

**Checkpoint**: US1 + US2 hoạt động độc lập

---

## Phase 5: User Story 3 - Khách đã có tài khoản thường / Google dùng Facebook (Priority: P3)

**Goal**: Email Facebook trùng tài khoản có sẵn → tự đăng nhập vào tài khoản đó và gắn Facebook.

**Independent Test**: quickstart #6, #6b

- [ ] T016 [US3] Add `void setFacebookID(int userID, String facebookID)` to `PNJShop/src/java/dal/UserDAO.java`: `UPDATE [dbo].[tblUsers] SET [facebookID] = ? WHERE userID = ? AND facebookID IS NULL` (never overwrite an existing link) (Clarification Q1, FR-007)
- [ ] T017 [US3] In `PNJShop/src/java/controller/FacebookLoginServlet.java` matching logic (T011): when `findByFacebookID` is null and `existing = uDao.checkEmail(email)` is not null → if `existing.getFacebookID()` is not null (already linked to a different Facebook) → redirect `login?service=login&fbError=linked` without changing anything; otherwise call `uDao.setFacebookID(existing.getUserID(), profile.getId())` and use `existing` (no insert); then continue through T015 (locked check) and T012 (session + redirect) (FR-007)

**Checkpoint**: Cả ba user story hoạt động độc lập

---

## Phase 6: Polish & Cross-Cutting Concerns

- [ ] T018 [P] Remove any `System.out.println` that prints `code`, `state`, token or full profile from `PNJShop/src/java/controller/FacebookLoginServlet.java` and `PNJShop/src/java/dal/UserFacebookDAO.java` (contract §5, FR-012)
- [ ] T019 [P] Confirm `git grep -n "FB_APP_SECRET"` in the repo only shows the variable name in `PNJShop/src/java/config/FacebookConfig.java` and docs, never a real value (FR-012)
- [ ] T020 Run all 16 scenarios in `specs/001-facebook-login/quickstart.md` (1–14 plus 6b, 9b) and tick results; fix any failure in the related file

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: no dependencies
- **Foundational (Phase 2)**: depends on Setup — BLOCKS all user stories
- **US1 (Phase 3)**: depends on Foundational
- **US2 (Phase 4)**: depends on Foundational; edits the same servlet as US1, so do after T011–T012
- **US3 (Phase 5)**: depends on Foundational; T017 extends the matching code from T011
- **Polish (Phase 6)**: after the desired stories are done

### Task-level dependencies

```text
T001 ─┐
T002 ─┼─> T005, T007
T003 ─┼─> T006 ─> T010 ─> T011 ─> T012 ─> T015
T004 ─┘                    T011 ─> T016 ─> T017
T007 ─> T008 ─> T011
T009, T013, T014 (JSP) independent of Java tasks after T007 exists
T018–T020 last
```

### Parallel Opportunities

- Phase 1: T001 ‖ T002
- Phase 2: T003 ‖ T004 ‖ T005 (different files); T009 ‖ any Java task
- Phase 3: T013 ‖ T014 (different JSPs) ‖ T010
- Phase 6: T018 ‖ T019

## Parallel Example: User Story 1

```text
Task: "T010 [US1] insertFacebookUser in PNJShop/src/java/dal/UserDAO.java"
Task: "T013 [P] [US1] Facebook button in PNJShop/web/jsp/Login.jsp"
Task: "T014 [P] [US1] Facebook button in PNJShop/web/jsp/Register.jsp"
```

## Implementation Strategy

### MVP First (User Story 1 Only)

1. Phase 1 → Phase 2 → Phase 3
2. **STOP and VALIDATE**: quickstart #1, #2, #3, #7, #8, #9, #9b
3. Demo

### Incremental Delivery

1. Setup + Foundational → nền tảng sẵn sàng
2. + US1 → kiểm tra → demo (MVP)
3. + US2 → kiểm tra #4, #5 → demo
4. + US3 → kiểm tra #6, #6b → demo
5. Polish → chạy đủ 16 kịch bản

## Notes

- Mỗi task hoặc nhóm task logic nên là một commit
- T001 phải được chạy trên cơ sở dữ liệu trước khi chạy thử bất kỳ story nào
- Cần tạo app Facebook và đặt biến môi trường theo quickstart trước khi kiểm tra thủ công
