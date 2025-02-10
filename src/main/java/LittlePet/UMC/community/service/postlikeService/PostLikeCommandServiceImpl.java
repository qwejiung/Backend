package LittlePet.UMC.community.service.postlikeService;

import LittlePet.UMC.User.repository.UserRepository;
import LittlePet.UMC.community.repository.postRepository.PostLikeRepository;
import LittlePet.UMC.community.repository.postRepository.PostRepository;

import LittlePet.UMC.domain.postEntity.Post;
import LittlePet.UMC.domain.postEntity.mapping.PostLike;
import LittlePet.UMC.domain.userEntity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikeCommandServiceImpl implements PostLikeCommandService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;


    @Override
    @Transactional
    public PostLike addlike(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        // ✅ 이미 좋아요를 눌렀는지 확인
        Optional<PostLike> existingLike = postLikeRepository.findByUserIdAndPostId(userId, postId);

        if (existingLike.isPresent()) {
            PostLike deletedLike = existingLike.get(); // 삭제 전 저장
            postLikeRepository.delete(deletedLike);
            return deletedLike; // 삭제된 PostLike 객체 반환
        } else {
            PostLike postLike = PostLike.builder()
                    .user(user)
                    .post(post)
                    .build();
            return postLikeRepository.save(postLike); // 좋아요 안 함 → 저장 (좋아요 추가)
        }
    }
}