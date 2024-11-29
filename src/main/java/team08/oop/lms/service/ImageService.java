package team08.oop.lms.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import team08.oop.lms.model.Image;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ImageService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public ImageService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.baseUrl("http://satlov2.vndss.com").build();
        this.objectMapper = objectMapper;
    }

    public Image getImage(String id) {
        String url = "http://satlov2.vndss.com/Layer/getInfoImageVid?id=" + id;
        log.info("Fetching image from URL: {}", url);

        try {
            // Gửi yêu cầu GET tới API và nhận JSON trả về
            String rawJson = webClient
                    .get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            log.debug("Raw JSON response: {}", rawJson);

            String cleanedJson = rawJson.replace("\\", "");
            String tmp = cleanedJson.substring(1, cleanedJson.length() - 1);
            // Ánh xạ JSON thành đối tượng Image
            return objectMapper.readValue(tmp, Image.class);

        } catch (Exception e) {
            log.error("Error fetching or parsing image data: {}", e.getMessage(), e);
            // Trả về một đối tượng Image rỗng nếu lỗi
            return new Image();
        }
    }
}