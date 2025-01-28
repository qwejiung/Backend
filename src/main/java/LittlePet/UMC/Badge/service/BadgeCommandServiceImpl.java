package LittlePet.UMC.Badge.service;

import LittlePet.UMC.Badge.converter.UserBadgeConverter;
import LittlePet.UMC.Badge.repository.badgeRepository.BadgeRepository;
import LittlePet.UMC.Badge.repository.badgeRepository.UserBadgeRepository;
import LittlePet.UMC.User.repository.UserRepository;
import LittlePet.UMC.apiPayload.code.status.ErrorStatus;
import LittlePet.UMC.apiPayload.exception.handler.BadgeHandler;
import LittlePet.UMC.community.repository.CommentRepository;
import LittlePet.UMC.community.repository.PostLlikeRepository;
import LittlePet.UMC.community.repository.PostRepository;
import LittlePet.UMC.domain.BadgeEntity.Badge;
import LittlePet.UMC.domain.BadgeEntity.mapping.UserBadge;
import LittlePet.UMC.domain.userEntity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BadgeCommandServiceImpl implements BadgeCommandService {
    private final BadgeRepository badgeRepository;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final UserBadgeRepository userBadgeRepository;

    private final PostLlikeRepository postLlikeRepository;

    private final CommentRepository commentRepository;

    @Override
    public UserBadge checkBadges(Long userId,String badgeType) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found")); //바꿔야함 validation

        boolean criteriaMet = false;
        // 문자열을 Enum으로 변환
        switch (badgeType) {
            case "글쓰기마스터":
                long postCount = postRepository.countByUserId(userId);
                criteriaMet = postCount >= 1; // Test를 위해 일단 1개 ,원래 15개 이상이면 True
                break;

            case "소통천재":
                long commentCount = commentRepository.getCountByUserId(userId); // 댓글 수 가져오기 (추가 메서드 필요)
                criteriaMet = commentCount >= 1; // Test를 위해 일단 1개 30개 이상이면 True
                break;

            case "소셜응원왕":
                long likeCount = postLlikeRepository.getCountByUserId(userId); // 좋아요 수 가져오기 (추가 메서드 필요)
                criteriaMet = likeCount >= 1; // Test를 위해 일단 1개 50개 이상이면 True
                break;

//            case "CHALLENGER":

            case "인기스타":
                long totalLikesReceived = postRepository.getTotalLikesReceivedByUserId(userId); // 유저가 받은 좋아요 총합
                criteriaMet = totalLikesReceived >= 2; // 좋아요 수 30개 이상
                break;

            default:
                log.error("Invalid badge type: {}", badgeType);
                throw new IllegalArgumentException("Invalid badge type");
        }
        if (criteriaMet) {
            log.info("Criteria met for badge type: {}", badgeType);
        } else {
            log.info("Criteria not met for badge type: {}", badgeType);
        }

        return assignBadge(user, badgeType, criteriaMet);

    }

    @Override
    @Transactional
    public UserBadge assignBadge(User user, String badgeType, boolean criteriaMet) {
        log.info("메소드 시작:"+user+badgeType+criteriaMet);
        if (!criteriaMet) {
            // 조건을 충족하지 못함
            throw new BadgeHandler(ErrorStatus.BADGE_NOT_QUALIFIED);
        }

        // 이미 뱃지가 할당된 경우 처리
        boolean alreadyHasBadge = user.getUserBadgeList().stream()
                .anyMatch(userBadge -> userBadge.getBadge().getName().equals(badgeType));

        if (alreadyHasBadge) {
            log.info("already has badge for badge type: {}", badgeType);
            // 이미 뱃지를 보유하고 있다면 예외를 던짐
            throw new BadgeHandler(ErrorStatus.BADGE_ALREADY_OWNED);
        }

        Badge badge = badgeRepository.findByBadgeType(badgeType);

        UserBadge userBadge = UserBadge.builder()
                .user(user)
                .badge(badge)
                .build();

        user.getUserBadgeList().add(userBadge);
        userRepository.save(user);
        return userBadge;
    }

    @Override
    public List<Badge> getBadgesByUserId(Long userId) {

        // 사용자의 모든 UserBadge 조회 후 Badge 리스트 반환
        List<Badge> BadgeList = userBadgeRepository.findByUserId(userId).stream()
                                        .map(UserBadge::getBadge)
                                        .collect(Collectors.toList());
        return BadgeList;
    }

    @Override
    @Transactional
    public List<UserBadge> assignChallenger() {
        List<Long> userIdList = postLlikeRepository.findTopUsersByChallenge();

        // 해당 ID로 User 리스트 조회
        List<User> userList = userRepository.findAllById(userIdList);

        Badge challengerBadge = badgeRepository.findByBadgeType("챌린저");

        // 4. 이미 챌린저 배지를 가진 사용자 제거
        List<UserBadge> UserBadgeList = userList.stream()
                .filter(user -> !userBadgeRepository.existsByUserAndBadge(user, challengerBadge))
                .map(user -> UserBadge.builder()
                        .badge(challengerBadge)
                        .user(user)
                        .build())
                .collect(Collectors.toList());


        userRepository.saveAll(userList);
        return UserBadgeList;

    }

    @Override
    @Transactional
    public UserBadge deleteUserBadge(Long userId, String badgeType) {
        // 유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        // 뱃지 타입에 해당하는 뱃지 조회
        Badge badge = badgeRepository.findByBadgeType(badgeType);

        UserBadge deletedUserBadge = userBadgeRepository.findByUserIdandBadgeType(user,badge);
        // UserBadge 엔티티 삭제
        userBadgeRepository.deleteByUserAndBadge(user.getId(), badge.getId());

        return deletedUserBadge;
    }


}


