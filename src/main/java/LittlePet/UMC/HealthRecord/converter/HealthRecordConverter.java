package LittlePet.UMC.HealthRecord.converter;

import LittlePet.UMC.HealthRecord.dto.HealthRecordRequestDTO;
import LittlePet.UMC.HealthRecord.dto.HealthRecordResponseDTO;
import LittlePet.UMC.domain.enums.*;
import LittlePet.UMC.domain.petEntity.categories.PetBigCategory;
import LittlePet.UMC.domain.petEntity.mapping.HealthRecord;
import LittlePet.UMC.domain.petEntity.mapping.UserPet;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HealthRecordConverter {

    /**
     * HealthRecordRequestDTO를 HealthRecord Entity로 변환
     */
        public static HealthRecord toHealthRecordEntity(HealthRecordRequestDTO request, UserPet pet) {

            AtypicalSymptomEnum selectedSymptom = null;
            String otherSymptom = null;

            // 🚨 `atypicalSymptom`이 NULL인지 체크 후 처리
            if (request.getAtypicalSymptom() != null && request.getAtypicalSymptom().equals("기타")) {
                // "기타"를 선택한 경우
                if (request.getOtherSymptom() == null || request.getOtherSymptom().trim().isEmpty()) {
                    throw new IllegalArgumentException("기타 증상을 선택했지만, 증상 내용을 입력하지 않았습니다.");
                }
                otherSymptom = request.getOtherSymptom();
            } else if (request.getAtypicalSymptom() != null) {
                try {
                    // 🚨 Enum 변환 예외 처리
                    selectedSymptom = AtypicalSymptomEnum.fromDescription(request.getAtypicalSymptom());
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("유효하지 않은 atypicalSymptom 값입니다: " + request.getAtypicalSymptom());
                }
            }


            return HealthRecord.builder()
                    .recordDate(LocalDate.parse(request.getRecordDate()))
                    .weight(request.getWeight())
                    .mealAmount(MealAmountEnum.fromDescription(request.getMealAmount().toUpperCase()))
                    .fecesStatus(FecesStatusEnum.fromDescription(request.getFecesStatus().toUpperCase()))
                    .fecesColorStatus(FecesColorStatusEnum.fromDescription(request.getFecesColorStatus().toUpperCase()))
                    .healthStatus(HealthStatusEnum.fromDescription(request.getHealthStatus()))
                    .atypicalSymptom(selectedSymptom)
                    .otherSymptom(otherSymptom)
                    .diagnosisName(request.getDiagnosisName())
                    .prescription(request.getPrescription())
                    .userPet(pet)
                    .build();
        }


    /**
     * UserPet 객체와 HealthRecord 객체를 바탕으로 HealthRecordResponseDTO를 생성하는 메서드
     *
     * @param pet         UserPet 객체 (반려동물 정보)
     * @param recentUpdate 최근 업데이트 상태 ("오늘", "어제", "2일 전" 등)
     * @param latestRecord 최신 HealthRecord 객체
     * @return HealthRecordResponseDTO
     */
    public static HealthRecordResponseDTO toHealthRecordResponseDTO(UserPet pet, String recentUpdate, HealthRecord latestRecord) {

        String atypicalSymptom = latestRecord.getAtypicalSymptom() != null
                ? latestRecord.getAtypicalSymptom().getDescription() // 🚨 Enum 값 변환
                : (latestRecord.getOtherSymptom() != null ? "기타: " + latestRecord.getOtherSymptom() : null); // 🚨 기타 입력 값이 있으면 추가

        return HealthRecordResponseDTO.builder()
                .petName(pet.getName()) // 반려동물 이름
                .gender(pet.getGender().toString())
                .petCategory(pet.getPetCategory().getSpecies() != null ? pet.getPetCategory().getSpecies() : null)
                .profilePhoto(pet.getProfilePhoto()) // 반려동물 프로필 사진
                .birthDay(pet.getBirthDay() != null ? pet.getBirthDay().toString() : null) // 반려동물 생년월일
                .recentUpdate(recentUpdate) // 최근 업데이트 정보
                .latestRecord(latestRecord != null ? toHealthRecordDetailDTO(latestRecord, atypicalSymptom) : null) // 최신 건강 기록 정보
                .build();
    }

    /**
     * HealthRecord 객체를 세부 DTO로 변환하는 메서드
     *
     * @param healthRecord HealthRecord 객체
     * @return HealthRecordDetailDTO
     */
    private static HealthRecordResponseDTO.HealthRecordDetailDTO toHealthRecordDetailDTO(HealthRecord healthRecord, String atypicalSymptom) {
        MealAmountEnum mealAmount = healthRecord.getMealAmount();
        FecesStatusEnum fecesStatus = healthRecord.getFecesStatus();
        FecesColorStatusEnum fecesColor = healthRecord.getFecesColorStatus();
        HealthStatusEnum healthStatus = healthRecord.getHealthStatus();


        return HealthRecordResponseDTO.HealthRecordDetailDTO.builder()
                .recordDate(healthRecord.getRecordDate().toString())
                .weight(healthRecord.getWeight())
                // 한글로 응답할 때는 getDescription() 사용
                .mealAmount(mealAmount != null ? mealAmount.getDescription() : null)
                .fecesStatus(fecesStatus != null ? fecesStatus.getDescription() : null)
                .fecesColorStatus(fecesColor != null ? fecesColor.getDescription() : null)
                .healthStatus(healthStatus != null ? healthStatus.getDescription() : null)
                .atypicalSymptom(atypicalSymptom)
                .diagnosisName(healthRecord.getDiagnosisName())
                .prescription(healthRecord.getPrescription())
                .build();
    }
}
