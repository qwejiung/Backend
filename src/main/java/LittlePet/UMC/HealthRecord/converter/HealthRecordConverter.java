package LittlePet.UMC.HealthRecord.converter;

import LittlePet.UMC.HealthRecord.dto.HealthRecordDateResponseDTO;
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
                selectedSymptom = AtypicalSymptomEnum.OTHER;
                otherSymptom = request.getOtherSymptom();
            } else if (request.getAtypicalSymptom() != null) {
                try {
                    // 🚨 Enum 변환 예외 처리
                    selectedSymptom = AtypicalSymptomEnum.fromDescription(request.getAtypicalSymptom());
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("유효하지 않은 atypicalSymptom 값입니다: " + request.getAtypicalSymptom());
                }
            }

            FecesStatusEnum fecesStatus = FecesStatusEnum.fromDescription(request.getFecesStatus().toUpperCase());
            FecesColorStatusEnum fecesColorStatus = null;  // 기본적으로 null로 설정

            // 🚨 "대변 안 봄"일 때 `fecesColorStatus`가 null이 아니면 예외 발생
            if (fecesStatus == FecesStatusEnum.NOT_DEFECATED && request.getFecesColorStatus() != null) {
                throw new IllegalArgumentException("배변 상태가 '대변 안 봄'일 경우 배변 색상을 입력할 수 없습니다.");
            }

            // ✅ "대변 안 봄"이 아닐 경우에만 색상 저장
            if (fecesStatus != FecesStatusEnum.NOT_DEFECATED && request.getFecesColorStatus() != null) {
                fecesColorStatus = FecesColorStatusEnum.fromDescription(request.getFecesColorStatus().toUpperCase());
            }


            return HealthRecord.builder()
                    .recordDate(LocalDate.parse(request.getRecordDate()))
                    .weight(request.getWeight())
                    .mealAmount(MealAmountEnum.fromDescription(request.getMealAmount().toUpperCase()))
                    .fecesStatus(fecesStatus)
                    .fecesColorStatus(fecesColorStatus)
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

        String atypicalSymptom = null;
//        String atypicalSymptom = latestRecord.getAtypicalSymptom() != null
//                ? latestRecord.getAtypicalSymptom().getDescription() // 🚨 Enum 값 변환
//                : (latestRecord.getOtherSymptom() != null ? "기타: " + latestRecord.getOtherSymptom() : null); // 🚨 기타 입력 값이 있으면 추가

        // 최신 기록이 있는 경우 처리
        if (latestRecord != null) {
            if (latestRecord.getAtypicalSymptom() != null) {
                // Enum 값이 `OTHER`인 경우 "기타: <otherSymptom>" 처리
                if (latestRecord.getAtypicalSymptom() == AtypicalSymptomEnum.OTHER && latestRecord.getOtherSymptom() != null) {
                    atypicalSymptom = "기타: " + latestRecord.getOtherSymptom();
                } else {
                    // 기타가 아닌 경우 Enum의 description 값 사용
                    atypicalSymptom = latestRecord.getAtypicalSymptom().getDescription();
                }
            } else if (latestRecord.getOtherSymptom() != null) {
                // Enum 값이 없고, 기타 설명이 있는 경우 처리
                atypicalSymptom = "기타: " + latestRecord.getOtherSymptom();
            }
        }

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
    public static HealthRecordResponseDTO.HealthRecordDetailDTO toHealthRecordDetailDTO(HealthRecord healthRecord, String atypicalSymptom) {
        MealAmountEnum mealAmount = healthRecord.getMealAmount();
        HealthStatusEnum healthStatus = healthRecord.getHealthStatus();


        return HealthRecordResponseDTO.HealthRecordDetailDTO.builder()
                .recordDate(healthRecord.getRecordDate().toString())
                .weight(healthRecord.getWeight())
                // 한글로 응답할 때는 getDescription() 사용
                .mealAmount(mealAmount != null ? mealAmount.getDescription() : null)
                .healthStatus(healthStatus != null ? healthStatus.getDescription() : null)
                .atypicalSymptom(atypicalSymptom)
                .otherSymptom(healthRecord.getOtherSymptom())
                .diagnosisName(healthRecord.getDiagnosisName())
                .prescription(healthRecord.getPrescription())
                .build();
    }


    public static HealthRecordDateResponseDTO toHealthRecordResponseDateDTO(UserPet pet, HealthRecord healthRecord,Double weightDifference) {
        MealAmountEnum mealAmount = healthRecord.getMealAmount();
        FecesStatusEnum fecesStatus = healthRecord.getFecesStatus();
        FecesColorStatusEnum fecesColor = healthRecord.getFecesColorStatus();
        HealthStatusEnum healthStatus = healthRecord.getHealthStatus();

        String atypicalSymptom = null;

        if (healthRecord.getAtypicalSymptom() != null) {
            if (healthRecord.getAtypicalSymptom() == AtypicalSymptomEnum.OTHER && healthRecord.getOtherSymptom() != null) {
                atypicalSymptom = "기타: " + healthRecord.getOtherSymptom();
            } else {
                atypicalSymptom = healthRecord.getAtypicalSymptom().getDescription();
            }
        } else if (healthRecord.getOtherSymptom() != null) {
            atypicalSymptom = "기타: " + healthRecord.getOtherSymptom();
        }

        String fecesCondition;
        if (healthRecord.getFecesStatus() == FecesStatusEnum.NORMAL &&
                healthRecord.getFecesColorStatus() == FecesColorStatusEnum.BROWN) {
            fecesCondition = "정상";
        } else {
            fecesCondition = "이상";
        }

        return HealthRecordDateResponseDTO.builder()
                .petName(pet.getName()) // 반려동물 이름
                .weight(healthRecord.getWeight()) // 현재 체중
                .weightDifference(weightDifference) // ✅ 체중 변화 추가
                .mealAmount(mealAmount != null ? mealAmount.getDescription() : null)
                .fecesStatus(fecesStatus != null ? fecesStatus.getDescription() : null)
                .fecesColorStatus(fecesColor != null ? fecesColor.getDescription() : null)
                .fecesStatusProfile(fecesCondition)
                .healthStatus(healthStatus != null ? healthStatus.getDescription() : null)
                .atypicalSymptom(atypicalSymptom)
                .diagnosisName(healthRecord.getDiagnosisName())
                .prescription(healthRecord.getPrescription())
                .build();
    }



}
