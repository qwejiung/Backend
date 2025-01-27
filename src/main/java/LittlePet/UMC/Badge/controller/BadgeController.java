package LittlePet.UMC.Badge.controller;

import LittlePet.UMC.Badge.converter.BadgeConverter;
import LittlePet.UMC.Badge.converter.UserBadgeConverter;
import LittlePet.UMC.Badge.dto.BadgeResponseDTO;
import LittlePet.UMC.Badge.service.BadgeCommandService;
import LittlePet.UMC.Badge.validation.annotation.ExistBadgeType;
import LittlePet.UMC.Badge.validation.annotation.ExistUser;
import LittlePet.UMC.apiPayload.ApiResponse;
import LittlePet.UMC.domain.BadgeEntity.Badge;
import LittlePet.UMC.domain.BadgeEntity.UserBadge;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//validation 및 errorstatus 추가해야됨
@RestController
@RequiredArgsConstructor
@RequestMapping("/badge")
@Validated
public class BadgeController {

    private final BadgeCommandService badgeCommandService;

    @PostMapping("/{userId}/{badgetype}/check")
    public ApiResponse<BadgeResponseDTO.badgeResultDTO> checkBadges(
            @PathVariable @ExistUser Long userId,
            @PathVariable @ExistBadgeType String badgetype)
    {
        UserBadge new_userbadge  = badgeCommandService.checkBadges(userId,badgetype);
        return ApiResponse.onSuccess(BadgeConverter.toBadgeResponseDTO(new_userbadge));
    }

    @PostMapping("/challengers")//이미 받은 사람 못받게 수정 추가해야됨
    public ApiResponse<List<BadgeResponseDTO.ChallengerResultDTO>> addChallenger()
    {
        List<UserBadge> challenger_userbadge  = badgeCommandService.assignChallenger();
        return ApiResponse.onSuccess(UserBadgeConverter.tochallengersDTO(challenger_userbadge));
    }

    @GetMapping("/{userId}")
    public ApiResponse<BadgeResponseDTO.getBadgeResultDTO> getUserBadges(
            @PathVariable @ExistUser Long userId)

    {
        List<Badge> badges = badgeCommandService.getBadgesByUserId(userId);
        return ApiResponse.onSuccess(BadgeConverter.toGetBadgeResuletDTO(badges));
    }
    @DeleteMapping("/{userId}/{badgetype}/delete")
    public ApiResponse<BadgeResponseDTO.badgeResultDTO> deleteBadge(
            @PathVariable @ExistUser Long userId,
            @PathVariable @ExistBadgeType String badgetype)
    {
            UserBadge deleted_UserBadge = badgeCommandService.deleteUserBadge(userId, badgetype);
            return ApiResponse.onSuccess(BadgeConverter.toBadgeResponseDTO(deleted_UserBadge));
    }
}

