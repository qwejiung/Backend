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

            List<AtypicalSymptomEnum> atypicalSymptomEnums = request.getAtypicalSymptom() == null
                    ? Collections.emptyList()
                    : request.getAtypicalSymptom().stream()
                    .filter(description -> !description.startsWith("기타")) // "기타"로 시작하는 값 제외
                    .map(AtypicalSymptomEnum::fromDescription) // Enum으로 변환
                    .collect(Collectors.toList());

            // "기타" 증상인지 확인하고 저장
            String otherSymptom = request.getAtypicalSymptom().stream()
                    .filter(description -> description.startsWith("기타")) // "기타"로 시작하는 값만 필터링
                    .map(description -> description.replace("기타: ", "")) // "기타: " 제거
                    .findFirst()
                    .orElse(null); // 값이 없으면 null


            return HealthRecord.builder()
                    .recordDate(LocalDate.parse(request.getRecordDate()))
                    .weight(request.getWeight())
                    .mealAmount(MealAmountEnum.fromDescription(request.getMealAmount().toUpperCase()))
                    .fecesStatus(FecesStatusEnum.fromDescription(request.getFecesStatus().toUpperCase()))
                    .fecesColorStatus(FecesColorStatusEnum.fromDescription(request.getFecesColorStatus().toUpperCase()))
                    .healthStatus(HealthStatusEnum.fromDescription(request.getHealthStatus()))
                    .atypicalSymptom(atypicalSymptomEnums)
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

        List<String> atypicalList = latestRecord.getAtypicalSymptom() == null
                ? Collections.emptyList()
                : latestRecord.getAtypicalSymptom().stream()
                .map(AtypicalSymptomEnum::getDescription)
                .collect(Collectors.toList());

        // "기타" 증상이 있으면 리스트에 추가
        if (latestRecord.getOtherSymptom() != null && !latestRecord.getOtherSymptom().isEmpty()) {
            atypicalList.add("기타: " + latestRecord.getOtherSymptom());
        }

        return HealthRecordResponseDTO.builder()
                .petName(pet.getName()) // 반려동물 이름
                .gender(pet.getGender().toString())
                .petCategory(pet.getPetCategory().getSpecies() != null ? pet.getPetCategory().getSpecies() : null)
                .profilePhoto(pet.getProfilePhoto()) // 반려동물 프로필 사진
                .birthDay(pet.getBirthDay() != null ? pet.getBirthDay().toString() : null) // 반려동물 생년월일
                .recentUpdate(recentUpdate) // 최근 업데이트 정보
                .latestRecord(latestRecord != null ? toHealthRecordDetailDTO(latestRecord, atypicalList) : null) // 최신 건강 기록 정보
                .build();
    }

    /**
     * HealthRecord 객체를 세부 DTO로 변환하는 메서드
     *
     * @param healthRecord HealthRecord 객체
     * @return HealthRecordDetailDTO
     */
    private static HealthRecordResponseDTO.HealthRecordDetailDTO toHealthRecordDetailDTO(HealthRecord healthRecord, List<String> atypicalList) {
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
                // 여러 증상이면 DTO 필드에도 List<String>으로 만들어 두시면 됩니다.
                .atypicalSymptom(atypicalList)
                .diagnosisName(healthRecord.getDiagnosisName())
                .prescription(healthRecord.getPrescription())
                .build();
    }
}
