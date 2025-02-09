package LittlePet.UMC.Hospital.dto;

import LittlePet.UMC.domain.hospitalEntity.Hospital;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HospitalRequestDTO {
    private Long id;
    private String name;
    private String address;
    private String closedDay;
    private String latitude;
    private String longitude;

    // Hospital 엔티티를 기반으로 DTO 객체 생성
    public HospitalRequestDTO(Hospital hospital) {
        this.id = hospital.getId();
        this.name = hospital.getName();
        this.address = hospital.getAddress();
        this.latitude = hospital.getLatitude();
        this.longitude = hospital.getLongitude();
        this.closedDay = hospital.getClosedDay();
    }
}
