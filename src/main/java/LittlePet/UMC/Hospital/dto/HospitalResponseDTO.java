package LittlePet.UMC.Hospital.dto;

import LittlePet.UMC.domain.hospitalEntity.Hospital;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HospitalResponseDTO {
    private Long id;
    private String name;
    private String address;

    public HospitalResponseDTO(Hospital hospital) {
        this.id = hospital.getId();
        this.name = hospital.getName();
    }
}
