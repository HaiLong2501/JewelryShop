# Feature Specification: Đăng nhập bằng Facebook

**Feature Branch**: `001-facebook-login`

**Created**: 2026-10-30

**Status**: Draft

**Input**: User description: "tôi muốn thêm một tính năng login bằng facebook"

## Clarifications

### Session 2026-10-30

- Q: Khi email Facebook trùng với một tài khoản đã có (đăng ký bằng mật khẩu hoặc Google), hệ thống nên xử lý thế nào? → A: Tự động đăng nhập vào tài khoản có sẵn theo email và gắn Facebook vào tài khoản đó (giống cách đăng nhập Google hiện tại).

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Khách hàng mới đăng nhập bằng Facebook (Priority: P1)

Một khách hàng chưa có tài khoản trên cửa hàng muốn mua trang sức nhưng không muốn điền form đăng ký. Họ bấm nút "Đăng nhập bằng Facebook" trên trang đăng nhập, xác nhận cho phép cửa hàng truy cập thông tin cơ bản (tên, email) trên Facebook, và được đưa trở lại cửa hàng ở trạng thái đã đăng nhập. Hệ thống tự tạo tài khoản khách hàng cho họ.

**Why this priority**: Đây là giá trị cốt lõi của tính năng — giảm rào cản đăng ký, giúp khách mới mua hàng nhanh hơn.

**Independent Test**: Dùng một tài khoản Facebook chưa từng đăng nhập vào cửa hàng, bấm nút đăng nhập Facebook, xác nhận quyền; kiểm tra người dùng được đăng nhập, tên hiển thị đúng và một tài khoản khách hàng mới xuất hiện trong trang quản lý người dùng của admin.

**Acceptance Scenarios**:

1. **Given** khách chưa có tài khoản và đang ở trang đăng nhập, **When** bấm "Đăng nhập bằng Facebook" và đồng ý cấp quyền, **Then** khách được đăng nhập, thấy tên của mình trên header, và có một tài khoản khách hàng mới ở trạng thái đang hoạt động.
2. **Given** khách đang xem một trang (ví dụ giỏ hàng) và được yêu cầu đăng nhập, **When** đăng nhập bằng Facebook thành công, **Then** khách được đưa trở lại đúng trang đang xem trước đó.
3. **Given** khách đang ở màn hình xin quyền của Facebook, **When** bấm "Huỷ" / từ chối, **Then** khách quay lại trang đăng nhập của cửa hàng với thông báo thân thiện rằng đăng nhập Facebook đã bị huỷ, và không có tài khoản nào được tạo.

---

### User Story 2 - Khách hàng cũ đăng nhập lại bằng Facebook (Priority: P2)

Khách đã từng đăng nhập bằng Facebook trước đó quay lại cửa hàng. Họ bấm nút Facebook và được đăng nhập ngay vào đúng tài khoản cũ, với giỏ hàng và lịch sử đơn hàng trước đây.

**Why this priority**: Không có câu chuyện này thì mỗi lần đăng nhập sẽ tạo tài khoản trùng, mất giỏ hàng và lịch sử đơn hàng.

**Independent Test**: Đăng nhập bằng Facebook, thêm sản phẩm vào giỏ, đăng xuất, đăng nhập lại bằng cùng tài khoản Facebook; kiểm tra giỏ hàng còn nguyên và không có tài khoản trùng trong danh sách người dùng.

**Acceptance Scenarios**:

1. **Given** khách đã có tài khoản tạo từ Facebook, **When** đăng nhập lại bằng cùng tài khoản Facebook, **Then** hệ thống đăng nhập vào đúng tài khoản cũ, không tạo tài khoản mới.
2. **Given** tài khoản của khách đã bị admin khoá (không hoạt động), **When** khách đăng nhập bằng Facebook, **Then** việc đăng nhập bị từ chối với thông báo tài khoản đã bị khoá.

---

### User Story 3 - Khách đã có tài khoản thường / Google dùng Facebook (Priority: P3)

Khách đã đăng ký bằng tên đăng nhập/mật khẩu hoặc bằng Google với email X. Facebook của họ cũng dùng email X. Khi họ bấm đăng nhập bằng Facebook, hệ thống nhận ra đây là cùng một người, tự động đăng nhập vào tài khoản có sẵn theo email và gắn Facebook vào tài khoản đó, giống cách đăng nhập Google đang làm.

**Why this priority**: Tránh tạo tài khoản trùng cho cùng một người, nhưng ít gặp hơn hai trường hợp trên.

**Independent Test**: Tạo tài khoản thường với email X; đăng nhập bằng một Facebook dùng email X; kiểm tra khách vào đúng tài khoản có sẵn (thấy lịch sử đơn hàng cũ) và không có tài khoản trùng email.

**Acceptance Scenarios**:

1. **Given** đã có tài khoản với email X, **When** khách đăng nhập bằng Facebook có email X, **Then** khách được đăng nhập vào tài khoản có sẵn mà không phải nhập thêm gì, tài khoản đó được gắn với Facebook (lần sau nhận ra bằng định danh Facebook), và không có hai tài khoản cùng email.

---

### Edge Cases

- Tài khoản Facebook không có email (đăng ký Facebook bằng số điện thoại) hoặc khách không cho phép chia sẻ email: [NEEDS CLARIFICATION: từ chối đăng nhập và yêu cầu dùng cách khác, hay cho phép đăng nhập và yêu cầu khách bổ sung email sau?]
- Facebook không phản hồi hoặc lỗi kết nối: khách quay về trang đăng nhập với thông báo "Không thể đăng nhập bằng Facebook, vui lòng thử lại hoặc dùng cách đăng nhập khác"; không có lỗi kỹ thuật nào hiển thị cho khách.
- Yêu cầu quay về từ Facebook bị giả mạo hoặc đã hết hạn / dùng lại: hệ thống từ chối và không đăng nhập ai.
- Tài khoản admin: đăng nhập bằng Facebook vào tài khoản có quyền admin vẫn được đưa tới trang quản trị, giống đăng nhập thường.
- Khách đang đăng nhập sẵn mà bấm đăng nhập Facebook: phiên cũ được thay bằng phiên của tài khoản Facebook.
- Tên trên Facebook chứa ký tự tiếng Việt có dấu hoặc emoji: tên được lưu và hiển thị đúng, không lỗi font.

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: Trang đăng nhập (và trang đăng ký) MUST hiển thị nút "Đăng nhập bằng Facebook" bên cạnh các cách đăng nhập hiện có.
- **FR-002**: Hệ thống MUST chuyển khách sang Facebook để xác thực và chỉ xin quyền tối thiểu: tên công khai và email.
- **FR-003**: Hệ thống MUST xác minh rằng phản hồi trả về từ Facebook là hợp lệ, thuộc đúng lượt đăng nhập đã khởi tạo, và từ chối mọi phản hồi giả mạo, hết hạn hoặc dùng lại.
- **FR-004**: Khi khách đăng nhập Facebook lần đầu và chưa có tài khoản tương ứng, hệ thống MUST tự tạo tài khoản khách hàng (vai trò khách hàng thường, trạng thái hoạt động) với tên và email lấy từ Facebook.
- **FR-005**: Hệ thống MUST lưu định danh Facebook của khách để những lần đăng nhập sau nhận ra đúng tài khoản, kể cả khi khách đổi email trên Facebook.
- **FR-006**: Hệ thống MUST không bao giờ tạo hai tài khoản cho cùng một định danh Facebook hoặc cùng một email.
- **FR-007**: Khi email Facebook trùng với tài khoản đã có (đăng ký bằng mật khẩu hoặc Google), hệ thống MUST tự động đăng nhập vào tài khoản đó và gắn định danh Facebook vào tài khoản, không yêu cầu xác nhận thêm.
- **FR-008**: Sau khi đăng nhập Facebook thành công, khách MUST được đưa về trang đang xem trước khi đăng nhập; nếu không có thì về trang chủ; tài khoản admin được đưa về trang quản trị.
- **FR-009**: Tài khoản đang bị khoá MUST không đăng nhập được bằng Facebook.
- **FR-010**: Khi khách huỷ cấp quyền hoặc có lỗi, hệ thống MUST đưa khách về trang đăng nhập với thông báo dễ hiểu bằng tiếng Việt.
- **FR-011**: Đăng xuất MUST hoạt động giống như với các cách đăng nhập khác (kết thúc phiên trên cửa hàng; không cần đăng xuất khỏi Facebook).
- **FR-012**: Thông tin bí mật dùng để kết nối với Facebook MUST không được lưu trong mã nguồn đưa lên kho mã.
- **FR-013**: Tài khoản tạo từ Facebook MUST dùng được đầy đủ các chức năng của khách hàng: giỏ hàng, đặt hàng, thanh toán, xem đơn hàng.

### Key Entities *(include if feature involves data)*

- **Người dùng (User)**: tài khoản hiện có của cửa hàng (tên, email, tên đăng nhập, vai trò, trạng thái hoạt động). Bổ sung khả năng gắn với một định danh Facebook.
- **Liên kết Facebook**: mối liên hệ giữa một người dùng và một tài khoản Facebook (định danh Facebook duy nhất, ngày liên kết). Mỗi định danh Facebook chỉ gắn với tối đa một người dùng.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: Khách mới có thể từ trang đăng nhập đến trạng thái đã đăng nhập bằng Facebook trong dưới 30 giây và không phải gõ bất kỳ thông tin nào trên cửa hàng.
- **SC-002**: 100% lượt đăng nhập lại bằng cùng tài khoản Facebook vào đúng tài khoản cũ; số tài khoản trùng email/định danh Facebook bằng 0.
- **SC-003**: 100% trường hợp huỷ hoặc lỗi đều đưa khách về trang đăng nhập với thông báo dễ hiểu, không hiện trang lỗi kỹ thuật.
- **SC-004**: Ít nhất 95% lượt thử đăng nhập Facebook hợp lệ (có email) thành công ngay lần đầu.
- **SC-005**: Khách đăng nhập bằng Facebook hoàn tất được một đơn hàng thử từ đầu đến cuối như khách đăng ký thường.

## Assumptions

- Cửa hàng sẽ đăng ký một ứng dụng trên trang dành cho nhà phát triển của Facebook để có thông tin kết nối; việc này do chủ cửa hàng thực hiện.
- Phạm vi chỉ là đăng nhập; không đăng bài, không chia sẻ lên Facebook, không đọc danh sách bạn bè.
- Tài khoản tạo từ Facebook không có mật khẩu trên cửa hàng; khách tiếp tục đăng nhập bằng Facebook (chức năng đặt mật khẩu nằm ngoài phạm vi).
- Cách đăng nhập hiện có (tên đăng nhập/mật khẩu và Google) giữ nguyên, không bị ảnh hưởng.
- Chức năng huỷ liên kết Facebook khỏi tài khoản nằm ngoài phạm vi phiên bản này.
- Chạy được trên cả máy tính và điện thoại qua trình duyệt web; không có ứng dụng di động riêng.
