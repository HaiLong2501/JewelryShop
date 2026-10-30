# Research: Đăng nhập bằng Facebook

**Feature**: [spec.md](./spec.md) | **Plan**: [plan.md](./plan.md) | **Date**: 2026-10-30

## R1. Luồng xác thực với Facebook

- **Decision**: Dùng luồng OAuth 2.0 "authorization code" phía server của Facebook Login cho web (manual flow), giống cấu trúc đăng nhập Google hiện có:
  1. Trình duyệt chuyển tới hộp thoại `https://www.facebook.com/{version}/dialog/oauth` với `client_id`, `redirect_uri`, `state`, `scope=public_profile,email`.
  2. Facebook chuyển về `redirect_uri` của cửa hàng với `code` + `state` (hoặc `error=access_denied` nếu khách huỷ).
  3. Server đổi `code` lấy access token tại `https://graph.facebook.com/{version}/oauth/access_token`.
  4. Server lấy thông tin tại `https://graph.facebook.com/{version}/me?fields=id,name,email`.
- **Rationale**: Không cần JavaScript SDK của Facebook; token không bao giờ đi qua trình duyệt; tái sử dụng được thư viện `fluent-hc` + `gson` đã có trong project (dùng cho Google).
- **Alternatives considered**:
  - Facebook JavaScript SDK (đăng nhập phía client rồi gửi token lên server): phải xác minh token thêm một bước, thêm script bên thứ ba vào trang.
  - Thư viện OAuth (ScribeJava, pac4j): thêm phụ thuộc mới cho một luồng chỉ cần 2 request HTTP.

## R2. Phiên bản Graph API

- **Decision**: Lưu phiên bản (ví dụ `v21.0`) thành một hằng số cấu hình duy nhất, dùng chung cho cả 3 URL.
- **Rationale**: Facebook ngừng hỗ trợ phiên bản cũ theo lịch; đổi một chỗ là đủ khi nâng cấp.
- **Alternatives considered**: Gọi không kèm phiên bản — Facebook tự dùng phiên bản cũ nhất còn hỗ trợ của app, hành vi khó đoán.

## R3. Chống giả mạo phản hồi (FR-003)

- **Decision**: Sinh `state` ngẫu nhiên (`SecureRandom`, 32 byte, Base64 URL-safe) mỗi lần khách bấm nút, lưu trong `HttpSession` cùng URL cần quay về. Khi Facebook gọi lại: so sánh `state` với giá trị trong session, xoá ngay sau khi so (dùng một lần). Không khớp/không có → từ chối.
- **Rationale**: Chống CSRF đăng nhập và chống dùng lại phản hồi. Luồng Google hiện tại dùng `state` cố định (`loginGoogle|url`) nên không có lớp bảo vệ này — không đổi Google trong phạm vi feature này, nhưng ghi nhận.
- **Alternatives considered**: PKCE — Facebook hỗ trợ nhưng với client có secret phía server thì `state` là đủ cho phạm vi này.

## R4. Quay về trang trước (FR-008) và chống open redirect

- **Decision**: URL quay về được lưu trong session (không đưa vào `state`). Chỉ chấp nhận đường dẫn tương đối trong ứng dụng (bắt đầu bằng `/` hoặc tên servlet, không chứa `//` hay `://`); sai → về `home`. Tài khoản `roleID == 1` → `admin`.
- **Rationale**: Hiện `LoginServlet` redirect thẳng theo tham số `redirectUrl`, có thể bị lợi dụng chuyển khách sang trang lừa đảo.

## R5. Lưu thông tin bí mật (FR-012)

- **Decision**: Đọc `FB_APP_ID`, `FB_APP_SECRET`, `FB_REDIRECT_URI` từ biến môi trường của máy chạy Tomcat (`System.getenv`). Thiếu biến → nút Facebook vẫn hiện nhưng bấm vào sẽ báo "Đăng nhập Facebook tạm thời không khả dụng", ghi log cho admin.
- **Rationale**: Không có bí mật nào trong mã nguồn hay trong repo; đổi key không cần build lại.
- **Alternatives considered**:
  - Hằng số trong `Constants.java` (cách Google đang làm) — lộ secret khi push.
  - `context-param` trong `web.xml` — `web.xml` nằm trong repo.
  - File `.properties` ngoài repo — được, nhưng phải quản lý đường dẫn trên từng máy; biến môi trường đơn giản hơn với NetBeans/Tomcat.

## R6. Lưu liên kết Facebook

- **Decision**: Thêm cột `facebookID NVARCHAR(64) NULL` vào cuối bảng `tblUsers`, kèm unique index có lọc `WHERE facebookID IS NOT NULL`.
- **Rationale**: Mỗi người dùng tối đa một Facebook (spec); thêm cột ở cuối không làm lệch các truy vấn đang đọc theo vị trí cột (`rs.getXxx(1..9)` với `SELECT *`). Unique index đảm bảo FR-006 ngay ở cơ sở dữ liệu.
- **Alternatives considered**: Bảng riêng `tblUserFacebook(userID, facebookID, linkedAt)` — linh hoạt hơn nếu sau này liên kết nhiều nhà cung cấp, nhưng thêm join cho một quan hệ 1–1.

## R7. Thứ tự tìm tài khoản

- **Decision**: (1) tìm theo `facebookID`; (2) không có → tìm theo email (theo Clarification Q1) rồi gắn `facebookID`; (3) vẫn không có → tạo tài khoản mới (roleID = 2, activate = 1, userName = email, password = NULL) với `facebookID`. Facebook không trả `email` và không tìm được theo `facebookID` → từ chối (Clarification Q2). Sau đó kiểm tra `activate`; `false` → từ chối (FR-009).
- **Rationale**: Ưu tiên `facebookID` để vẫn nhận ra khách khi họ đổi email trên Facebook (FR-005).
- **Ghi chú**: Khách đã liên kết trước đó nhưng sau này rút quyền email vẫn đăng nhập được qua bước (1).

## R8. Đối tượng User trong session

- **Decision**: Sau khi tìm/tạo, luôn nạp lại `User` từ cơ sở dữ liệu (có `userID`) rồi mới đặt vào session `user`.
- **Rationale**: Luồng Google hiện tại đặt vào session đối tượng tạo từ JSON của Google (không có `userID`), khiến giỏ hàng/đơn hàng có thể không gắn đúng người dùng. Feature này không lặp lại lỗi đó (FR-013).

## R9. Môi trường phát triển và yêu cầu của Facebook

- **Decision**: Phát triển với app Facebook ở chế độ Development; `http://localhost:9999/pnj/...` được Facebook chấp nhận làm redirect URI khi phát triển. Khi triển khai thật cần HTTPS, domain thật và chuyển app sang Live (cần chính sách quyền riêng tư và URL xoá dữ liệu).
- **Rationale**: Quyền `public_profile` và `email` không cần App Review.

## R10. Kiểm thử

- **Decision**: Project không có test framework; kiểm thử thủ công theo [quickstart.md](./quickstart.md) bằng tài khoản Facebook thật/test user của app.
- **Alternatives considered**: Thêm JUnit + Mockito — đáng làm nhưng nằm ngoài phạm vi feature.
