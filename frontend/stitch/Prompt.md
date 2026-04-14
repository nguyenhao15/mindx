# Câu lệnh Prompt cho Thiết Kế UI/UX - Hệ Thống Quản Lý Sửa Chữa Nội Bộ

Để quá trình thiết kế và sinh code giao diện qua AI đạt hiệu quả cao nhất dựa trên định hướng Minimalist đã chốt ở `DESIGN.md`, bạn có thể sử dụng nguyên văn câu Prompt dưới đây để yêu cầu hệ thống sinh ra bộ UI.

---

**Act as an Expert Frontend Developer and UI/UX Designer.**
Hãy tạo giao diện React sử dụng Tailwind CSS cho **Hệ Thống Quản Lý Bảo Trì & Sửa Chữa Nội Bộ**.

## 1. Quy tắc Thiết kế chung (Bắt buộc áp dụng nghiêm ngặt)

- **Màu chủ đạo:** Dùng `#1d3557` (Xanh Navy đậm) cho Sidebar, Nút bấm chính (Primary Buttons), và các text/biểu tượng nhấn mạnh.
- **Bố cục (Layout):** Sidebar điều hướng bên trái (nền `#1d3557`, chữ trắng nhạt, biểu tượng rõ ràng). Phần nội dung (Main Content) hiển thị bên phải với nền xám nhạt (`bg-slate-50`) nhằm làm nổi bật giao diện chức năng.
- **Thành phần Card:** Các khối nội dung phải dùng nền trắng (`bg-white`), bo góc lớn (`rounded-2xl`), viền mỏng tinh tế (`border-slate-100`), tuyệt đối **không** dùng bóng đổ đậm (heavy shadows).
- **Tương tác Input:** Các ô nhập liệu bo góc vừa (`rounded-lg`). Khi user tương tác (focus) phải có viền mờ `ring-2 ring-[#1d3557]/20` để phản hồi rõ ràng.
- **Màu trạng thái:** Xanh lá chức năng (Thành công/Hoàn tất), Vàng (Đang chờ/Cảnh báo), Xanh dương nhạt (Thông tin/Đang xử lý).
- **Phong cách:** Tối giản (Minimalist), sử dụng nhiều khoảng trắng (high-gap layout), ưu tiên sự gọn gàng và dễ đọc.

## 2. Yêu cầu chi tiết các Màn Hình:

Vui lòng xây dựng một ứng dụng React Dashboard, có thanh điều hướng chung để hiển thị 3 màn hình cốt lõi dưới đây (sử dụng Dữ liệu giả - Mock data phong phú để minh họa):

### Màn hình 1: Trang chủ / Dashboard Tổng Quan

- **Thống kê (Metrics Dashboard):** Hiển thị số lượng các đề xuất sửa chữa, tỷ lệ tình trạng cơ sở vật chất (Hoạt động tốt, Đang sửa chữa, Khẩn cấp).
- **Danh sách Đề xuất (Data Table):** Bảng tối giản hiển thị các đơn đề xuất sửa chữa tại các cơ sở, đi kèm huy hiệu (Badge) thể hiện tình trạng của mỗi đơn.
- **Điều hướng nhanh:** Một nút hoặc vùng Banner Call-to-action (phông nền hoặc viền nổi bật) ở góc trên hoặc khu vực trung tâm để điều hướng người dùng tới hành động "Tạo mới Đề xuất sửa chữa" một cách dễ dàng nhất.

### Màn hình 2: Tạo Mới Đề Xuất Sửa Chữa

- Giao diện Form nhập liệu sạch sẽ, phân bổ vị trí từ trên xuống dưới hoặc chia làm 2 cột rõ ràng.
- **Thiết kế bao gồm các trường:**
  - Hạng mục sửa chữa (Select Dropdown).
  - Danh mục / Hạng mục con (Select Dropdown phụ thuộc).
  - Cơ sở đề xuất (Select Dropdown / Input).
  - Khu vực Diễn giải chi tiết (Textarea rộng rãi).
  - **Khu vực đính kèm (File Upload):** Cần một khung nét đứt (dashed border) mang hiệu ứng Drag & Drop, gợi ý người dùng tải lên "Hình ảnh / Video tình trạng sửa chữa".
- Nút "Gửi Đề Xuất" nổi bật ở cuối form, sử dụng màu chủ đạo `#1d3557`.

### Màn hình 3: Chi tiết Đơn Sửa Chữa (Ticket Detail)

- **Thanh Tiến Độ (Progress Stepper):** Đặt ở vị trí nổi bật trên cùng để người dùng nắm bắt mạch luân chuyển (VD: Chờ duyệt > Đang sửa > Đợi nghiệm thu > Hoàn thành).
- **Khu vực Media (Trước / Sau):**
  - Phần hiển thị "Hình ảnh/Video tình trạng lúc báo cáo".
  - **Lưu ý quan trọng:** Cần thiết kế sẵn một block UI dành cho "Hình ảnh nghiệm thu sau sửa chữa" (nếu có cập nhật) hiển thị song song hoặc ngay bên dưới để dễ dàng so sánh Trước-Sau.
- **Phương án Kỹ thuật:** Một khối Card riêng hiển thị danh sách các "Phương án sửa chữa" được kỹ thuật viên đề xuất (Bao gồm nội dung, thời gian ước tính, vật tư dự kiến).
- **Lịch sử & Cập nhật (Activity Timeline):** Thiết lập một vùng giao diện chuỗi Timeline (Luồng sự kiện). Tại đây, mọi thao tác cập nhật trạng thái, diễn giải tình huống hoặc bình luận thay đổi từ các đối tượng khác nhau (Người báo cáo, Kĩ thuật viên, Quản lý) được ghi rõ ràng kèm Vai trò (Role) và Mốc thời gian (Timestamp).

> Yêu cầu kĩ thuật cho Code: Sử dụng Tailwind CSS chuẩn. Có thể dùng thư viện `lucide-react` để cung cấp icon hợp quy chuẩn tối giản. Code cần sắp xếp phân rã theo Components để đảm bảo khả năng bảo trì.
