package team08.oop.lms.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import team08.oop.lms.model.FeatureCollection;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FeatureService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public FeatureService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.baseUrl("http://satlov2.vndss.com").build();
        this.objectMapper = objectMapper;
    }

    public FeatureCollection getFeatureCollection(String city, String type, String typeStruct) {
        // String url = "http://satlov2.vndss.com/Layer/Api?type=" + type + "&sts=" +
        // typeStruct + "&matinh=" + city;
        String url = "http://satlov2.vndss.com/Layer/Api?type=" + type + "&sts=" + typeStruct + "&matinh=" + city;
        log.info("Fetching data from URL: {}", url);
        try {
            String rawJson = webClient
                    .get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            String cleanedJson = rawJson.replace("\\", "");
            String tmp = cleanedJson.substring(1, cleanedJson.length() - 1);
            // Sử dụng trực tiếp ObjectMapper để ánh xạ JSON thành FeatureCollection
            return objectMapper.readValue(tmp, FeatureCollection.class);

        } catch (Exception e) {
            log.error("Error while fetching or parsing data: {}", e.getMessage(), e);
            return new FeatureCollection(); // Trả về đối tượng rỗng nếu lỗi
        }
    }
}
