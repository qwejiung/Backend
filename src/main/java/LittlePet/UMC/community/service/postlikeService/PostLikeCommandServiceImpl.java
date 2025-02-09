package LittlePet.UMC.community.service.postlikeService;

import LittlePet.UMC.User.repository.UserRepository;
import LittlePet.UMC.community.repository.postRepository.PostRepository;
import LittlePet.UMC.community.repository.postlikerepository.PostLikeRepository;
import LittlePet.UMC.domain.postEntity.Post;
import LittlePet.UMC.domain.postEntity.mapping.PostLike;
import LittlePet.UMC.domain.userEntity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

        PostLike postLike = PostLike.builder()
                .user(user)
                .post(post)
                .build();

        return postLikeRepository.save(postLike);
    }
}