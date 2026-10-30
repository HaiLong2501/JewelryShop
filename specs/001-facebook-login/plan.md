# Implementation Plan: Đăng nhập bằng Facebook

**Branch**: `001-facebook-login` | **Date**: 2026-10-30 | **Spec**: [spec.md](./spec.md)

**Input**: Feature specification from `/specs/001-facebook-login/spec.md`

**Note**: This template is filled in by the `/speckit-plan` command; its definition describes the execution workflow.

## Summary

Thêm nút "Đăng nhập bằng Facebook" cho cửa hàng PNJShop. Dùng luồng OAuth 2.0 authorization code phía server của Facebook (tương tự cách đăng nhập Google đang có), qua một servlet mới `/login-facebook`. Khách được nhận diện theo `facebookID` (cột mới trong `tblUsers`), nếu chưa có thì theo email (tự liên kết), nếu vẫn chưa có thì tạo tài khoản khách hàng mới. Facebook không trả email → từ chối. Có kiểm tra `state` chống giả mạo, chống open redirect, và đọc App ID/Secret từ biến môi trường thay vì mã nguồn. Chi tiết quyết định: [research.md](./research.md).

## Technical Context

**Language/Version**: Java (Jakarta EE 10: Servlet 6.0, JSP 3.1), JSTL 2.0

**Primary Dependencies**: Apache HttpClient fluent-hc 4.5.5 + httpcore 4.4.9, Gson 2.8.2 (đều đã có trong project, đang dùng cho Google) — không thêm thư viện mới

**Storage**: SQL Server Express (`tblUsers`) qua JDBC (`dal.DBContext`); thêm cột `facebookID`

**Testing**: Kiểm thử thủ công theo [quickstart.md](./quickstart.md) (project chưa có test framework)

**Target Platform**: Apache Tomcat (chạy từ NetBeans, `http://localhost:9999/pnj`); trình duyệt máy tính và điện thoại

**Project Type**: Web application dạng monolith Servlet/JSP (NetBeans Ant project)

**Performance Goals**: Toàn bộ luồng đăng nhập < 30 giây với người dùng (SC-001); phía server < 3 giây cho 2 request tới Facebook (đặt timeout 10 giây)

**Constraints**: Không lưu secret trong repo (FR-012); không đổi hành vi đăng nhập thường và Google; giữ phong cách code hiện có (servlet trong `controller`, truy vấn trong `dal`, model trong `model`)

**Scale/Scope**: Cửa hàng nhỏ (đồ án), vài trăm người dùng; 1 servlet mới, 1 DAO mới, sửa 2 JSP, 1 model, 1 migration

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

`.specify/memory/constitution.md` vẫn là template chưa điền (chưa chạy `/speckit-constitution`) → **không có gate nào để kiểm tra**. PASS (mặc định).

Các nguyên tắc chung vẫn được áp dụng tự nguyện: dùng lại thư viện có sẵn, không thêm phụ thuộc, thay đổi nhỏ nhất, không đụng tới luồng đăng nhập khác.

**Re-check sau Phase 1**: PASS — thiết kế không thêm project, thư viện hay lớp trừu tượng mới.

## Project Structure

### Documentation (this feature)

```text
specs/001-facebook-login/
├── plan.md              # File này
├── research.md          # Phase 0
├── data-model.md        # Phase 1
├── quickstart.md        # Phase 1
├── contracts/
│   └── facebook-login-endpoints.md
├── checklists/
│   └── requirements.md
└── tasks.md             # Phase 2 (/speckit-tasks — chưa tạo)
```

### Source Code (repository root)

```text
PNJShop/
├── src/java/
│   ├── config/
│   │   └── FacebookConfig.java          # MỚI: đọc FB_APP_ID / FB_APP_SECRET / FB_REDIRECT_URI, phiên bản Graph API, URL
│   ├── controller/
│   │   └── FacebookLoginServlet.java    # MỚI: /login-facebook — bắt đầu + callback
│   ├── dal/
│   │   ├── UserFacebookDAO.java         # MỚI: đổi code lấy token, lấy hồ sơ /me
│   │   └── UserDAO.java                 # SỬA: findByFacebookID, setFacebookID, insertFacebookUser, findByID
│   └── model/
│       ├── FacebookProfile.java         # MỚI: id, name, email
│       └── User.java                    # SỬA: thêm facebookID
├── web/jsp/
│   ├── Login.jsp                        # SỬA: nút Facebook + thông báo fbError
│   └── Register.jsp                     # SỬA: nút Facebook
└── database/
    └── 001-add-facebookID.sql           # MỚI: migration
```

**Structure Decision**: Giữ nguyên cấu trúc monolith hiện có của `PNJShop` (package theo tầng `controller` / `dal` / `model` / `config`). Tách servlet riêng `/login-facebook` thay vì thêm nhánh vào `LoginServlet` để không động vào luồng đăng nhập thường/Google và giữ servlet ngắn gọn.

## Complexity Tracking

Không có vi phạm cần giải trình.
