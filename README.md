
# Vietnam Landslide Tracking System

## Mô tả

Hệ thống theo dõi tình trạng sạt lở bờ sông và xói lở bờ biển tại Việt Nam, nhằm cung cấp thông tin kịp thời và chính xác về các hiện tượng thiên tai như sạt lở đất và xói mòn. Website được phát triển để hỗ trợ người dân, giúp họ nhận thức rõ hơn về mức độ nguy hiểm của các thiên tai này và chủ động xây dựng kế hoạch ứng phó.

Cụ thể, hệ thống cung cấp các bản đồ thời tiết, cập nhật tình trạng sạt lở theo từng tỉnh, và cảnh báo nguy cơ sạt lở nguy hiểm. Ngoài ra, hệ thống còn hỗ trợ thông báo qua email để người dân nắm bắt thông tin nhanh chóng.

## Các chức năng chính

1. **Đăng ký và đăng nhập**: Người dùng có thể đăng ký tài khoản và đăng nhập vào hệ thống để theo dõi tình trạng sạt lở tại địa phương.
2. **Xử lý dữ liệu sạt lở**: Hệ thống thu thập, xử lý và cập nhật dữ liệu sạt lở từ các nguồn đáng tin cậy, như Viện Khoa học Thủy lợi Việt Nam.
3. **Hiển thị tình trạng sạt lở**: Cung cấp thông tin về tình trạng sạt lở tại các địa phương, bao gồm mức độ cảnh báo và các thông tin liên quan khác.
4. **Gửi thông báo hàng ngày**: Hệ thống tự động gửi email thông báo về tình trạng sạt lở, giúp người dân kịp thời cập nhật tình hình.
5. **Quản lý người dùng**: Chức năng dành cho quản trị viên hệ thống để quản lý người dùng, theo dõi và cập nhật dữ liệu.

## Các thành viên nhóm

- **Kiều Hồng Phong** - B22DCKH084
- **Nguyễn Hải Nam** - B22DCCN558
- **Nguyễn Phúc Hưng** - B22DCCN414

## Cài đặt

### Yêu cầu

1. **JDK 21**: Đảm bảo bạn đã cài đặt JDK 21 hoặc phiên bản mới hơn.
2. **Apache Maven**: Cần có Maven để xây dựng ứng dụng Spring Boot.

### Hướng dẫn cài đặt

1. **Clone repository**:
   ```bash
   git clone https://github.com/haiphong-0132/Vietnam-Landslide-Tracking-System.git
   ```

2. **Chuyển đến thư mục dự án**:
   ```bash
   cd Vietnam-Landslide-Tracking-System
   ```

3. **Cài đặt dự án bằng Maven**:
   ```bash
   mvn clean install
   ```

4. **Chạy ứng dụng**:
   ```bash
   mvn spring-boot:run
   ```

5. Mở trình duyệt và truy cập `http://localhost:8080` để sử dụng hệ thống.

## Sử dụng

- Người dùng có thể đăng ký tài khoản và chọn các tỉnh thành để nhận thông báo qua email về tình trạng sạt lở tại khu vực mình.
- Theo dõi tình hình sạt lở qua bản đồ thời tiết và các dữ liệu cập nhật theo từng tỉnh.

## Giấy phép

Dự án này được cấp phép dưới Giấy phép MIT - xem chi tiết tại [LICENSE](LICENSE).

## Cảm ơn

- Cảm ơn Viện Khoa học Thủy lợi Việt Nam đã cung cấp dữ liệu hỗ trợ.
- Cảm ơn các công cụ và thư viện như Spring Boot, Maven, LeafletJS, và Windy API.
