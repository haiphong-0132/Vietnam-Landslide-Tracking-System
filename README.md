
# Vietnam Landslide Tracking System

## Mô tả

Hệ thống theo dõi tình trạng sạt lở bờ sông và xói lở bờ biển tại Việt Nam, nhằm cung cấp thông tin kịp thời và chính xác về các hiện tượng thiên tai như sạt lở đất và xói mòn. Website được phát triển để hỗ trợ người dân, giúp họ nhận thức rõ hơn về mức độ nguy hiểm của các thiên tai này và chủ động xây dựng kế hoạch ứng phó.

Cụ thể, hệ thống cung cấp các bản đồ thời tiết, cập nhật tình trạng sạt lở theo từng tỉnh, và cảnh báo nguy cơ sạt lở nguy hiểm. Ngoài ra, hệ thống còn hỗ trợ thông báo qua email để người dân nắm bắt thông tin nhanh chóng.
> [!NOTE]  
> Dự án của chúng tôi được lấy cảm hứng từ website [satlov2.vndss.com](http://satlov2.vndss.com/), một nền tảng cung cấp thông tin về tình trạng sạt lở tại Việt Nam.
> **Tuy nhiên, toàn bộ mã nguồn trong dự án của chúng tôi được phát triển _TỪ CON SỐ KHÔNG_, do các thành viên trong nhóm thực hiện, _KHÔNG KẾ THỪA BẤT KỲ ĐOẠN MÃ NGUỒN NÀO TỪ CÁC DỰ ÁN BÊN NGOÀI_**.


## Các chức năng chính

1. **Đăng ký và đăng nhập**: Người dùng có thể đăng ký tài khoản và đăng nhập vào hệ thống để theo dõi tình trạng sạt lở tại địa phương.
2. **Xử lý dữ liệu sạt lở**: Hệ thống thu thập, xử lý và cập nhật dữ liệu sạt lở từ API của Viện Khoa học Thủy lợi Việt Nam.
3. **Hiển thị tình trạng sạt lở**: Cung cấp thông tin về tình trạng sạt lở tại các địa phương, bao gồm mức độ cảnh báo và các thông tin liên quan khác.
4. **Gửi thông báo hàng ngày**: Hệ thống tự động gửi email thông báo về tình trạng sạt lở theo chu kỳ, giúp người dân kịp thời cập nhật tình hình.
5. **Quản lý người dùng**: Chức năng dành cho quản trị viên hệ thống để quản lý người dùng, theo dõi các người dùng đã đăng ký..

## Các thành viên nhóm

- **Kiều Hồng Phong** - B22DCKH084 - Xử lý FrontEnd và các xử lý logic trên bản đồ.
- **Nguyễn Hải Nam** - B22DCCN558 - Xử lý dữ liệu thô và tạo RestAPI, trả kết quả dưới dạng JSON
- **Nguyễn Phúc Hưng** - B22DCCN414 - Xử lý dữ liệu người dùng, cải thiện hiệu suất fetch data từ API

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

5. Ngoài ra, bạn có thể dùng **Extension Spring Boot Extension Pack** trên VS Code.
   ![image](https://github.com/user-attachments/assets/b0af38e0-ded8-45b9-936c-d0c6a7a090e5)
   
Sau đó dùng lệnh sau để tiến hành build lại dự án và tạo file .jar / .war.
```bash
mvn clean install
```

6. Sau khi build thành công, tiến hành cấu hình lại file ```application.properties```:
```bash
spring.application.name=lms

# Database Configuration
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/satlo # EDIT YOUR JDBC CONNECTION HERE, AND CREATE SCHEMA IN MYSQL
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:<MYSQL_PASSWORD>}            # TYPE YOUR MYSQL PASSWORD HERE
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Hibernate Properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Logging
spring.jpa.show-sql=true
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type=TRACE
logging.level.org.springframework.security=DEBUG

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=<YOUR_EMAIL>                               # TYPE YOUR EMAIL WHICH USED FOR SENDING NOTIFICATION TO USERS.
spring.mail.password=<YOUR_PASSWORD>                            # TYPE YOUR APPLICATION PASSWORDS EMAIL.
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

## Sử dụng

- Người dùng có thể đăng ký tài khoản và chọn các tỉnh thành để nhận thông báo qua email về tình trạng sạt lở tại khu vực mình.
- Theo dõi tình hình sạt lở qua bản đồ thời tiết và các dữ liệu cập nhật theo từng tỉnh.

## Cảm ơn
- Chúng em xin gửi lời cảm ơn chân thành và sâu sắc tới **thầy Nguyễn Mạnh Sơn**, người đã tận tình hỗ trợ và giải đáp những thắc mắc của chúng em trong quá trình làm dự án, tạo mọi điều kiện tốt nhất để chúng em có thể hoàn thành tốt bài báo cáo này.
- Cảm ơn Viện Khoa học Thủy lợi Việt Nam đã cung cấp dữ liệu API.
- Cảm ơn các nhà phát triển các thư viện và công nghệ: Spring Boot, Maven, LeafletJS, Windy API, Leaflet PixiOverlay, MySQL.
