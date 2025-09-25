package LittlePet.UMC.Badge.service;

import LittlePet.UMC.Badge.repository.badgeRepository.BadgeRepository;
import LittlePet.UMC.Badge.repository.badgeRepository.UserBadgeRepository;
import LittlePet.UMC.User.repository.UserRepository;
import LittlePet.UMC.apiPayload.code.status.ErrorStatus;
import LittlePet.UMC.apiPayload.exception.handler.BadgeHandler;
import LittlePet.UMC.community.repository.commentRepository.CommentRepository;
import LittlePet.UMC.community.repository.postRepository.PostLikeRepository;
import LittlePet.UMC.community.repository.postRepository.PostRepository;
import LittlePet.UMC.domain.BadgeEntity.Badge;
import LittlePet.UMC.domain.BadgeEntity.mapping.UserBadge;
import LittlePet.UMC.domain.userEntity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    private final PostLikeRepository postlikeRepository;

    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public Boolean checkBadges(Long userId,String badgeType) {


        boolean criteriaMet = false;
        // 문자열을 Enum으로 변환
        switch (badgeType) {
            case "글쓰기마스터":
                long postCount = postRepository.countByUserId(userId);
                criteriaMet = postCount >= 2; // Test를 위해 일단 1개 ,원래 15개 이상이면 True
                break;

            case "소통천재":
                long commentCount = commentRepository.getCountByUserId(userId); // 댓글 수 가져오기 (추가 메서드 필요)
                criteriaMet = commentCount >= 2; // Test를 위해 일단 1개 30개 이상이면 True
                break;

            case "소셜응원왕":
                long likeCount = postlikeRepository.getCountByUserId(userId); // 좋아요 수 가져오기 (추가 메서드 필요)
                criteriaMet = likeCount >= 2; // Test를 위해 일단 1개 50개 이상이면 True
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
            return false;
        }

        return criteriaMet;

    }

    @Override
    @Transactional
    public UserBadge assignBadge(Long userId,String badgeType) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found")); //바꿔야함 validation

        // 이미 뱃지가 할당된 경우 처리
        boolean alreadyHasBadge = user.getUserBadgeList().stream()
                .anyMatch(userBadge -> userBadge.getBadge().getName().equals(badgeType));

        if (alreadyHasBadge) {
            log.info("already has badge for badge type: {}", badgeType);
            // 1.이미 뱃지를 보유하고 있다면 예외를 던짐
//            throw new BadgeHandler(ErrorStatus.BADGE_ALREADY_OWNED);
            //2. null을 던짐
            return null;  // 예외를 던지지 않고 그냥 종료
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
        List<Long> userIdList = postlikeRepository.findTopUsersByChallenge();

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

    @Override
    public String getBadgeProgress(Long userId, String badgeType) {
        Long currentCount;
        int requiredCount;

        switch (badgeType) {
            case "글쓰기마스터":
                currentCount = postRepository.countByUserId(userId);
                requiredCount = 15;
                return "다음 목표까지 게시글 작성 " + Math.max(0, requiredCount - currentCount) + "개 남았어요!";

            case "소통천재":
                currentCount = commentRepository.getCountByUserId(userId);
                requiredCount = 30;
                return "다음 목표까지 댓글 작성 " + Math.max(0, requiredCount - currentCount) + "개 남았어요!";

            case "소셜응원왕":
                currentCount = postlikeRepository.getCountByUserId(userId);
                requiredCount = 50;
                return "다음 목표까지 좋아요 " + Math.max(0, requiredCount - currentCount) + "개 남았어요!";

            case "인기스타":
                currentCount = postRepository.getTotalLikesReceivedByUserId(userId);
                requiredCount = 30;
                return "다음 목표까지 받을 좋아요 " + Math.max(0, requiredCount - currentCount) + "개 남았어요!";

            case "챌린저":
                currentCount = postlikeRepository.findMyRankingByChallenge(userId);
                if (currentCount == null)
                    return "이번 주 챌린지에 도전하세요!";
                return "현재 내 등수는 "+ currentCount+"등 이에요";

            default:
                throw new IllegalArgumentException("Invalid badge type");
        }
    }

    @Override
    public List<String> getMissingBadges(Long userId) {
        List<Badge> TestBadges = badgeRepository.findAll();
        System.out.println("TestBadges:"+TestBadges);

        List<String> allBadges = badgeRepository.findAll().stream()
                .map(Badge::getName)
                .collect(Collectors.toList());
        System.out.println("allBadges:"+allBadges);

        List<String> userBadges = userBadgeRepository.findByUserId(userId).stream()
                .map(userBadge -> userBadge.getBadge().getName())
                .collect(Collectors.toList());
        System.out.println("userBadges:"+userBadges);

        allBadges.removeAll(userBadges);
        System.out.println("allBadges:"+allBadges);
        return allBadges;
    }
    }







