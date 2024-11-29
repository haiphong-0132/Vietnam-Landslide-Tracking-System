package team08.oop.lms.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import team08.oop.lms.config.MapConfig;
import team08.oop.lms.model.FinalResult;
import team08.oop.lms.model.Image;
import team08.oop.lms.model.MiniFeature;
import team08.oop.lms.model.Properties;
import team08.oop.lms.model.Result;
import team08.oop.lms.model.User;

@Service
public class NotificationService {

    @Autowired
    private FinalResultService finalResultService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Scheduled(cron = "0 0 7 * * *") 
    // @Scheduled(cron = "0 * * * * *")
    public void sendDailyNotifications() {
        // Lấy tất cả người dùng có receiveAlerts = true
        List<User> users = userService.getUsersWithAlerts();

        List<String> types = Arrays.asList("BB", "BS"); 
        List<String> typeStructs = Arrays.asList("7", "8", "9", "65", "66", "88", "86", "94");

        // Gửi email cho từng người dùng
        for (User user : users) {
            String address = MapConfig.MAP_CITY.get(user.getAddress());
            String subject = "CẬP NHẬT TÌNH HÌNH SẠT LỞ VÀ LŨ QUÉT KHU VỰC";

            // Gọi FinalResultService để lấy thông tin liên quan đến địa chỉ của người dùng
            FinalResult finalResult = finalResultService.generateFinalResult(
                    Collections.singletonList(address),
                    types,
                    typeStructs);

            // Tạo nội dung email cá nhân hóa
            String emailContent = generateEmailContent(user, finalResult);

            // Gửi email
            emailService.sendEmail(user.getEmail(), subject, emailContent);
        }
    }

    private String generateEmailContent(User user, FinalResult finalResult) {
        StringBuilder content = new StringBuilder();

        content.append("<html><head><style>")
                .append("body { font-family: Arial, sans-serif; font-size: 16px; line-height: 1.5; color: #333; }")
                .append("h2 { color: #2E8B57; }")
                .append("h3 { color: #FF6347; }")
                .append("ul { padding-left: 20px; }")
                .append("li { margin-bottom: 10px; font-size: 14px; }")
                .append("a { color: #FF6347; text-decoration: none; }")
                .append("p { font-size: 16px; }")
                .append(".warning { color: red; }")
                .append(".image-gallery { display: flex; flex-wrap: nowrap; gap: 10px; justify-content: center; }")
                .append(".image-gallery img { width: 450px; height: 300px; object-fit: cover; border-radius: 8px; }")
                .append("</style></head><body>");

        content.append("<h2>Kính gửi: ").append(user.getFirstName()).append(" ").append(user.getLastName())
                .append("</h2>")
                .append("<p>Thông tin cập nhật tại địa chỉ: <strong>").append(user.getAddress())
                .append("</strong></p>");

        // Xử lý danh sách kết quả
        List<Result> results = finalResult.getList_result();
        if (results.isEmpty()) {
            content.append(
                    "<p class='warning'>Hiện tại không có thông tin cảnh báo nguy hiểm nào tại khu vực của bạn.</p>");
        } else {
            for (Result result : results) {
                content.append("<h3>Loại Sạt Lở: ").append(MapConfig.MAP_TYPE.get(result.getType())).append("</h3>")
                        .append("<h4>Tình Trạng: <strong style=\"color: red;\">")
                        .append(MapConfig.MAP_STRUCT.get(result.getType_struct())).append("</strong></h4>")
                        .append("<p>Chi tiết:</p>");

                int num = 1;
                for (MiniFeature feature : result.getList_minifeature()) {
                    Properties properties = feature.getFeature().getProperties();
                    content.append("<div>")
                            .append("<p><strong>Số thứ tự cảnh báo:</strong> ").append(num).append("</p>")
                            .append("<p><strong>").append(properties.getTennhan()).append("</strong></p>");

                    // Địa điểm
                    if (properties.getHuyenref() != null && !properties.getHuyenref().isEmpty() &&
                            properties.getXaref() != null && !properties.getXaref().isEmpty()) {
                        content.append("<p>Địa điểm: Xã ").append(properties.getXaref())
                                .append(", Huyện ").append(properties.getHuyenref()).append("</p>");
                    }

                    // Tác động
                    if (properties.getTacdong() != null && !properties.getTacdong().isEmpty()) {
                        content.append("<p>Ảnh Hưởng Tới: ").append(properties.getTacdong()).append("</p>");
                    }

                    // Chiều dài
                    content.append("<p>Chiều dài: ").append(properties.getChieudai()).append(" m</p>");

                    // Hình ảnh và video
                    addImageAndVideo(content, feature.getImage());

                    content.append("</div>");
                    num++;
                }
            }
        }

        // Kết thúc email
        content.append("<p>Hãy chú ý và liên hệ với chính quyền địa phương khi cần thiết.</p>")
                .append("<p>Trân trọng,</p>")
                .append("<p><strong>Đội ngũ hỗ trợ</strong></p>")
                .append("<p><strong>Vietnam Landslide Tracking System</strong></p>")
                .append("</body></html>");

        return content.toString();
    }

    private void addImageAndVideo(StringBuilder content, Image image) {
        if (image.getSource_images() != null && !image.getSourceImagesList().isEmpty()) {
            content.append("<p style='color: #2E8B57;'><strong>Một số hình ảnh cụ thể:</strong></p>")
                    .append("<div class='image-gallery'>");

            // Vòng lặp tạo thẻ ảnh
            for (String imgUrl : image.getSourceImagesList()) {
                content.append("<div>")
                        .append("<img src='").append(imgUrl).append("' alt='Hình ảnh' />")
                        .append("</div>");
            }

            content.append("</div>"); // Kết thúc image-gallery
        }

        // Thêm video bên dưới phần ảnh
        if (image.getUrl_video() != null) {
            content.append("<div style='margin-top: 20px;'>")
                    .append("<p><strong>Video: </strong><a href='").append(image.getUrl_video())
                    .append("' style='color: #FF6347; text-decoration: none;'>Xem Video</a></p>")
                    .append("</div>");
        }
    }

}
