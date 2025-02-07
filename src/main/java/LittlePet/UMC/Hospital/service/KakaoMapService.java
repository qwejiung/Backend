package LittlePet.UMC.Hospital.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

@Service
public class KakaoMapService {

    private final RestTemplate restTemplate;

    @Value("${kakao.api.key}")
    private String kakaoApiKey;  // application.yml에서 Kakao API Key 가져오기

    // RestTemplate을 생성자에서 직접 주입하는 방식
    public KakaoMapService() {
        this.restTemplate = new RestTemplate();  // RestTemplate 직접 생성
    }

    // 주소를 좌표로 변환하는 메서드
    public String getCoordinates(String address) {
        String apiUrl = "https://dapi.kakao.com/v2/local/search/address.json?query=" + address;

        // 요청 헤더 설정 (Authorization에 Kakao API Key 추가)
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoApiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // API 호출 (GET 방식으로 API 요청)
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, org.springframework.http.HttpMethod.GET, entity, String.class);

        return response.getBody(); // JSON 응답 반환
    }

    private String formatResponse(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);

            // documents 배열에서 첫 번째 문서 가져오기
            JsonNode firstDocument = rootNode.path("documents").get(0);
            if (firstDocument != null) {
                String addressName = firstDocument.path("address_name").asText();
                String x = firstDocument.path("x").asText();
                String y = firstDocument.path("y").asText();

                // 포맷팅된 응답 메시지 반환
                return String.format("주소: %s\n위도: %s\n경도: %s", addressName, y, x);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "주소를 찾을 수 없습니다.";
    }
}
