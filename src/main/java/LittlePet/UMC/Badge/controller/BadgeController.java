package LittlePet.UMC.Badge.controller;

import LittlePet.UMC.Badge.converter.BadgeConverter;
import LittlePet.UMC.Badge.converter.UserBadgeConverter;
import LittlePet.UMC.Badge.dto.BadgeResponseDTO;
import LittlePet.UMC.Badge.service.BadgeCommandService;
import LittlePet.UMC.Badge.validation.annotation.ExistBadgeType;
import LittlePet.UMC.Badge.validation.annotation.ExistUser;
import LittlePet.UMC.apiPayload.ApiResponse;
import LittlePet.UMC.domain.BadgeEntity.Badge;
import LittlePet.UMC.domain.BadgeEntity.mapping.UserBadge;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//validation 및 errorstatus 추가해야됨
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/badge")
@Validated
public class BadgeController {

    private final BadgeCommandService badgeCommandService;

    /**
     * 유저의 뱃지 등록 API
     *
     * @param userId 사용자 ID (PathVariable)
     * @param badgetype 뱃지 타입 ex)글쓰기 마스터,소셜 응원왕
     * @return 등록한 유저뱃지 응답 DTO
     */
    @Operation(summary = "뱃지 등록", description = "뱃지 자격을 확인하고 사용자에게 뱃지를 주는 API입니다.")
    @PostMapping("/{userId}/{badgetype}/check")
    public ApiResponse<BadgeResponseDTO.badgeResultDTO> checkBadges(
            @PathVariable @ExistUser Long userId,
            @PathVariable @ExistBadgeType String badgetype)
    {
        UserBadge new_userbadge  = badgeCommandService.checkBadges(userId,badgetype);
        return ApiResponse.onSuccess(BadgeConverter.toBadgeResponseDTO(new_userbadge));
    }

    /**
     * 챌린저 뱃지 등록 API
     *일주일 후 챌린지가 종료되었을때 챌린지 게시글의 좋아요를 가장 많이 받은 유저 3명에게 '챌린저'뱃지등록
     * @return 챌린저 유저뱃지 응답 DTO
     */
    @Operation(summary = "챌린저 뱃지 등록", description = "챌린저 뱃지를 받을 수 있는 사용자를 추려 뱃지를 주는 API입니다.")
    @PostMapping("/challengers")//이미 받은 사람 못받게 수정 추가해야됨
    public ApiResponse<List<BadgeResponseDTO.ChallengerResultDTO>> addChallenger()
    {
        List<UserBadge> challenger_userbadge  = badgeCommandService.assignChallenger();
        return ApiResponse.onSuccess(UserBadgeConverter.tochallengersDTO(challenger_userbadge));
    }


    /**
     * 유저의 뱃지 확인 API
     *
     * @param userId 사용자 ID (PathVariable)
     * @return 등록된 유저뱃지 응답 DTO
     */
    @Operation(summary = "사용자의 뱃지 확인", description = "사용자가 얻은 뱃지들을 확인할 수 있는 API입니다.")
    @GetMapping("/{userId}")
    public ApiResponse<BadgeResponseDTO.getBadgeResultDTO> getUserBadges(
            @PathVariable @ExistUser Long userId)

    {
        List<Badge> badges = badgeCommandService.getBadgesByUserId(userId);
        return ApiResponse.onSuccess(BadgeConverter.toGetBadgeResuletDTO(badges));
    }


    /**
     * 유저가 뱃지 확인 API
     *
     * @param userId 사용자 ID (PathVariable)
     * @return 등록된 유저뱃지 응답 DTO
     */
    @Operation(summary = "획득하지 못한 뱃지 조회", description = "사용자가 아직 획득하지 못한 뱃지 목록을 반환합니다.")
    @GetMapping("/{userId}/missingbadge")
    public ApiResponse<List<String>> getMissingBadges(@PathVariable @ExistUser Long userId) {
        List<String> missingBadges = badgeCommandService.getMissingBadges(userId);
        return ApiResponse.onSuccess(missingBadges);
    }


    /**
     * 유저의 목표뱃지 진행상황 API
     *
     * @param userId 사용자 ID (PathVariable)
     * @param badgeType 뱃지 타입 ex)글쓰기 마스터,소셜 응원왕
     * @return 삭제된 유저뱃지 응답 DTO
     */
    @Operation(summary = "목표 뱃지 진행 상황", description = "사용자가 목표 뱃지를 얻기까지 남은 조건을 반환합니다.")
    @GetMapping("/{userId}/{badgeType}/progress")
    public ApiResponse<String> getBadgeProgress(
            @PathVariable @ExistUser Long userId,
            @PathVariable @ExistBadgeType String badgeType) {

        String progressMessage = badgeCommandService.getBadgeProgress(userId, badgeType);
        return ApiResponse.onSuccess(progressMessage);
    }


    /**
     * 유저의 뱃지 삭제API
     *
     * @param userId 사용자 ID (PathVariable)
     * @param badgetype 뱃지 타입 ex)글쓰기 마스터,소셜 응원왕
     * @return 삭제된 유저뱃지 응답 DTO
     */
    @Operation(summary = "사용자의 뱃지 삭제", description = "사용자가 얻은 뱃지들을 삭제할 수 있는 API입니다.")
    @DeleteMapping("/{userId}/{badgetype}/delete")
    public ApiResponse<BadgeResponseDTO.badgeResultDTO> deleteBadge(
            @PathVariable @ExistUser Long userId,
            @PathVariable @ExistBadgeType String badgetype)
    {
            UserBadge deleted_UserBadge = badgeCommandService.deleteUserBadge(userId, badgetype);
            return ApiResponse.onSuccess(BadgeConverter.toBadgeResponseDTO(deleted_UserBadge));
    }


}

