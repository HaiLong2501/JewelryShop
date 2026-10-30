# Quickstart: Kiểm tra đăng nhập Facebook

Hướng dẫn chạy và kiểm tra tính năng từ đầu đến cuối. Chi tiết endpoint: [contracts/facebook-login-endpoints.md](./contracts/facebook-login-endpoints.md). Chi tiết dữ liệu: [data-model.md](./data-model.md).

## Chuẩn bị

1. **Tạo app Facebook** tại trang dành cho nhà phát triển của Facebook (developers.facebook.com):
   - Loại app có sản phẩm "Facebook Login"; giữ ở chế độ Development.
   - Valid OAuth Redirect URIs: `http://localhost:9999/pnj/login-facebook`
   - Ghi lại App ID và App Secret.
   - Thêm 1–2 test user (hoặc dùng tài khoản của chính bạn — tài khoản có vai trò trong app mới đăng nhập được khi app ở Development).
2. **Cập nhật cơ sở dữ liệu**: chạy đoạn migration trong [data-model.md](./data-model.md).
3. **Đặt biến môi trường** cho Tomcat (NetBeans → Services → Servers → Tomcat → Properties, hoặc biến môi trường Windows rồi khởi động lại NetBeans):
   - `FB_APP_ID=<App ID>`
   - `FB_APP_SECRET=<App Secret>`
   - `FB_REDIRECT_URI=http://localhost:9999/pnj/login-facebook`
4. Clean and Build, rồi Run project.
5. Luôn mở trang bằng đúng host đã khai báo trong `FB_REDIRECT_URI` (`localhost`, không dùng `127.0.0.1`), nếu không cookie phiên bị mất và luôn gặp lỗi "Phiên đăng nhập không hợp lệ".

## Kịch bản kiểm tra

| # | Bước | Kết quả mong đợi | Spec |
|---|---|---|---|
| 1 | Mở `/pnj/login?service=login`, bấm "Đăng nhập bằng Facebook", đồng ý | Về trang chủ, header hiện tên Facebook; có dòng mới trong `tblUsers` với `facebookID`, `roleID=2`, `activate=1` | US1-1, FR-004 |
| 2 | Từ giỏ hàng bị yêu cầu đăng nhập → đăng nhập Facebook | Quay lại đúng giỏ hàng | US1-2, FR-008 |
| 3 | Bấm nút, trên Facebook bấm "Huỷ" | Về trang login, thông báo "Bạn đã huỷ…"; không có dòng mới | US1-3, FR-010 |
| 4 | Thêm sản phẩm vào giỏ, đăng xuất, đăng nhập Facebook lại | Giỏ hàng còn; `tblUsers` không có dòng trùng | US2-1, FR-006 |
| 5 | Admin khoá tài khoản ở kịch bản 1, đăng nhập Facebook lại | Thông báo "Tài khoản đã bị khoá" | US2-2, FR-009 |
| 6 | Đăng ký tài khoản thường với email = email Facebook của test user khác, rồi đăng nhập Facebook | Vào tài khoản có sẵn; dòng đó được điền `facebookID`; không có dòng trùng email | US3, FR-007 |
| 6b | Dùng test user thứ hai có cùng email với tài khoản ở kịch bản 6 (đã gắn Facebook) | Thông báo "Email này đã được liên kết…"; `facebookID` của tài khoản không đổi | FR-007 |
| 7 | Đăng nhập bằng test user bỏ chọn quyền email (lần đầu) | Thông báo "chưa chia sẻ email"; không có dòng mới | Edge, Q2 |
| 8 | Mở thẳng `/pnj/login-facebook?code=abc&state=xyz` | Thông báo "Phiên đăng nhập không hợp lệ"; không ai được đăng nhập | FR-003 |
| 9 | Mở `/pnj/login-facebook?redirectUrl=https://evil.example` rồi đăng nhập | Về trang chủ của cửa hàng, không sang trang ngoài | R4 |
| 9b | Đang ở trang chi tiết sản phẩm (chưa đăng nhập), bấm đăng nhập → đăng nhập Facebook | Quay lại đúng trang chi tiết sản phẩm đó | FR-008, R4 |
| 10 | Đăng nhập Facebook bằng tài khoản admin đã liên kết | Vào trang `admin` | Edge |
| 11 | Xoá biến `FB_APP_ID`, khởi động lại, bấm nút | Thông báo "tạm thời không khả dụng"; không lỗi 500 | R5 |
| 12 | Với tài khoản ở kịch bản 1: thêm hàng, đặt hàng, thanh toán | Đơn hàng tạo thành công, gắn đúng `userID` | FR-013, SC-005 |
| 13 | Dùng test user có tên tiếng Việt có dấu | Tên hiện đúng trên header và trong `tblUsers` | Edge |
| 14 | Đang đăng nhập bằng Facebook, bấm đăng xuất | Về trang chủ, header không còn tên | FR-011 |

## Kiểm tra bảo mật nhanh

- `git grep -i "FB_APP_SECRET="` chỉ thấy tên biến, không có giá trị thật.
- Log Tomcat không chứa `access_token` hay `code`.
