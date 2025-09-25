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
    private String phoneNumber;
    private String openingHours;
    private String imageUrl;
    private Double rating;

    public HospitalResponseDTO(Hospital hospital) {
        this.id = hospital.getId();
        this.name = hospital.getName();
        this.address = hospital.getAddress();
        this.phoneNumber = hospital.getPhoneNumber();
        this.openingHours = hospital.getOpeningHours();
        this.imageUrl = hospital.getImageUrl();
        this.rating = hospital.getRating();
    }
}
