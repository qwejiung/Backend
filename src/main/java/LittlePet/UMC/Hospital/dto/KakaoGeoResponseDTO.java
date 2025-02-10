package LittlePet.UMC.Hospital.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoGeoResponseDTO {
    private List<KakaoGeoDocument> documents;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class KakaoGeoDocument {
        private String address_name;  // 주소 이름
        private String x;             // 경도 (longitude)
        private String y;             // 위도 (latitude)
    }
}
