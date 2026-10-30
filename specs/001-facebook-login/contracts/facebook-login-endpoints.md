# Contract: Endpoint đăng nhập Facebook

Servlet mới `FacebookLoginServlet`, ánh xạ `/login-facebook`. Mọi phản hồi đều là redirect (302); không trả JSON.

## 1. Bắt đầu đăng nhập

`GET /login-facebook?redirectUrl={url}`

| Tham số | Bắt buộc | Mô tả |
|---|---|---|
| redirectUrl | không | Trang cần quay về sau khi đăng nhập. Nhận đường dẫn tương đối trong ứng dụng, hoặc URL đầy đủ cùng host + port với server (chỉ giữ path + query). Domain khác / `//…` / không hợp lệ / thiếu → `home`. |

**Hành vi**

- Thiếu cấu hình (`FB_APP_ID`/`FB_APP_SECRET`/`FB_REDIRECT_URI`) → 302 `login?service=login&fbError=unavailable`.
- Ngược lại: tạo `fbState`, lưu `fbState` + `fbRedirect` vào session → 302 tới
  `https://www.facebook.com/{version}/dialog/oauth?client_id={FB_APP_ID}&redirect_uri={FB_REDIRECT_URI}&state={fbState}&scope=public_profile,email&response_type=code`

## 2. Facebook gọi lại (callback)

`GET /login-facebook?code={code}&state={state}` — hoặc khi khách huỷ: `GET /login-facebook?error=access_denied&error_reason=user_denied&state={state}`

`FB_REDIRECT_URI` phải trỏ đúng endpoint này (ví dụ `http://localhost:9999/pnj/login-facebook`) và được khai báo trong cài đặt app Facebook.

| Điều kiện | Kết quả |
|---|---|
| `state` không khớp `fbState` trong session (hoặc session không có) | 302 `login?service=login&fbError=invalid` |
| có `error` | 302 `login?service=login&fbError=cancelled` |
| lỗi khi đổi token / lấy hồ sơ, JSON không hợp lệ, hoặc ghi DB thất bại | 302 `login?service=login&fbError=failed` |
| hồ sơ không có email và chưa từng liên kết | 302 `login?service=login&fbError=noemail` |
| email trùng tài khoản đã gắn với Facebook khác | 302 `login?service=login&fbError=linked` |
| tài khoản `activate = 0` | 302 `login?service=login&fbError=locked` |
| thành công, `roleID = 1` | session `user` = tài khoản; 302 `admin` |
| thành công, khách hàng | session `user` = tài khoản; 302 `fbRedirect` (hoặc `home`) |

Trong mọi trường hợp, `fbState` và `fbRedirect` bị xoá khỏi session sau khi xử lý callback.

## 3. Thông báo trên trang đăng nhập

`jsp/Login.jsp` đọc `param.fbError` và hiện trong khung `alert-danger` có sẵn:

| fbError | Thông báo |
|---|---|
| cancelled | Bạn đã huỷ đăng nhập bằng Facebook. |
| noemail | Tài khoản Facebook của bạn chưa chia sẻ email, vui lòng dùng cách đăng nhập khác. |
| linked | Email này đã được liên kết với một tài khoản Facebook khác. Vui lòng đăng nhập bằng cách cũ. |
| locked | Tài khoản của bạn đã bị khoá. Vui lòng liên hệ cửa hàng. |
| invalid | Phiên đăng nhập không hợp lệ hoặc đã hết hạn, vui lòng thử lại. |
| failed | Không thể đăng nhập bằng Facebook, vui lòng thử lại hoặc dùng cách đăng nhập khác. |
| unavailable | Đăng nhập Facebook tạm thời không khả dụng. |

Giá trị khác → không hiện gì (không in thẳng tham số ra trang).

## 4. Nút trên giao diện

`jsp/Login.jsp` và `jsp/Register.jsp`: nút "Đăng nhập bằng Facebook" cạnh nút Google, là liên kết tới
`{contextPath}/login-facebook?redirectUrl={redirectUrl đã encode}`.

## 5. Gọi ra Facebook (server → Facebook)

| Bước | Request | Lấy ra |
|---|---|---|
| Đổi token | `GET https://graph.facebook.com/{version}/oauth/access_token?client_id&redirect_uri&client_secret&code` | `access_token` |
| Hồ sơ | `GET https://graph.facebook.com/{version}/me?fields=id,name,email&access_token={token}` | `id`, `name`, `email` (có thể thiếu) |

Không ghi `access_token`, `code` hay `client_secret` ra log.
