# Data Model: Đăng nhập bằng Facebook

**Feature**: [spec.md](./spec.md) | **Research**: [research.md](./research.md)

## Bảng `tblUsers` (đã có — bổ sung 1 cột)

| Cột | Kiểu | Ghi chú |
|---|---|---|
| userID | INT, PK, identity | đã có |
| fullName | NVARCHAR | đã có — lấy từ `name` của Facebook khi tạo mới |
| password | NVARCHAR, NULL | đã có — `NULL` với tài khoản tạo từ Facebook |
| roleID | INT | đã có — `2` (khách hàng) khi tạo mới |
| address, phone, gender | | đã có — để trống khi tạo mới |
| email | NVARCHAR | đã có — từ Facebook; dùng để liên kết (Clarification Q1) |
| activate | BIT | đã có — `1` khi tạo mới; `0` → không được đăng nhập (FR-009) |
| UserName | NVARCHAR | đã có — đặt bằng email khi tạo mới (giống Google) |
| **facebookID** | **NVARCHAR(64), NULL** | **mới** — định danh của khách trong app Facebook (`id` trả về từ `/me`) |

**Ràng buộc mới**

- `UX_tblUsers_facebookID`: UNIQUE trên `facebookID` WHERE `facebookID IS NOT NULL` → một Facebook chỉ gắn với tối đa một tài khoản (FR-006).
- Cột thêm ở **cuối bảng** để không làm lệch các truy vấn `SELECT *` đang đọc theo vị trí cột.

**Migration** (chạy một lần trên SQL Server):

```sql
ALTER TABLE dbo.tblUsers ADD facebookID NVARCHAR(64) NULL;
CREATE UNIQUE INDEX UX_tblUsers_facebookID ON dbo.tblUsers(facebookID) WHERE facebookID IS NOT NULL;
```

## Model Java `User` (đã có — bổ sung)

- Thêm thuộc tính `facebookID` (String) + getter/setter. Không đổi các constructor đang dùng.

## Đối tượng tạm `FacebookProfile` (mới, không lưu DB)

| Trường | Nguồn | Bắt buộc |
|---|---|---|
| id | `/me` → `id` | có |
| name | `/me` → `name` | có |
| email | `/me` → `email` | không (có thể thiếu → xem quy tắc dưới) |

## Dữ liệu trong session (tạm, trong một lượt đăng nhập)

| Khoá | Giá trị | Vòng đời |
|---|---|---|
| `fbState` | chuỗi ngẫu nhiên | tạo khi bấm nút; xoá ngay khi Facebook gọi lại |
| `fbRedirect` | URL tương đối cần quay về | tạo khi bấm nút; xoá khi xong |
| `user` | `User` nạp từ DB (có `userID`) | như đăng nhập hiện tại |

## Quy tắc khớp tài khoản (FR-004 → FR-009)

```text
profile = Facebook /me
u = findByFacebookID(profile.id)
if u == null:
    if profile.email rỗng: TỪ CHỐI "chưa chia sẻ email"          (Clarification Q2)
    u = findByEmail(profile.email)
    if u != null and u.facebookID != null: TỪ CHỐI "đã liên kết Facebook khác"  (FR-007)
    if u != null: setFacebookID(u, profile.id)                   (Clarification Q1)
    else: u = insert(fullName=name, email, userName=email,
                     roleID=2, activate=1, facebookID=id)        (FR-004)
if !u.activate: TỪ CHỐI "tài khoản đã bị khoá"                     (FR-009)
session.user = reload(u.userID)                                   (R8)
```

## Trạng thái lượt đăng nhập

```text
[Trang login] --bấm nút--> [Chờ Facebook] --code+state hợp lệ--> [Đổi token] --> [Lấy hồ sơ] --> [Khớp tài khoản] --> [Đã đăng nhập]
                                 |--error (huỷ)--> [Trang login + "đã huỷ"]
                                 |--state sai/hết hạn--> [Trang login + "không hợp lệ"]
                   [Đổi token]/[Lấy hồ sơ] lỗi mạng --> [Trang login + "không thể đăng nhập"]
                   [Khớp tài khoản] thiếu email / bị khoá / đã liên kết Facebook khác --> [Trang login + thông báo tương ứng]
                   lỗi bất ngờ (JSON lạ, ghi DB thất bại) --> [Trang login + "không thể đăng nhập"]
```
