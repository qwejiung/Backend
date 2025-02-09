package LittlePet.UMC.Hospital.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KakaoMapService {

    private final RestTemplate restTemplate;

    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    public KakaoMapService() {
        this.restTemplate = new RestTemplate();
    }

    //주소를 입력받아 카카오 API를 호출하여 위도/경도를 반환하는 메서드
    public String[] getCoordinates(String address) {
        String apiUrl = "https://dapi.kakao.com/v2/local/search/address.json?query=" + address;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoApiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

        return parseCoordinates(response.getBody());
    }

    //위도/경도 추출 메서드
    private String[] parseCoordinates(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode firstDocument = rootNode.path("documents").get(0);

            if (firstDocument != null) {
                String longitude = firstDocument.path("x").asText(); // 경도
                String latitude = firstDocument.path("y").asText();  // 위도
                return new String[]{latitude, longitude};
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String[]{null, null};
    }
}
