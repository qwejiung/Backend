package LittlePet.UMC.community.service.commentService;

import LittlePet.UMC.Badge.service.BadgeCommandService;
import LittlePet.UMC.User.repository.UserRepository;
import LittlePet.UMC.community.dto.commentDTO.CommentRequestDTO;
import LittlePet.UMC.community.dto.commentDTO.CommentResponseDTO;
import LittlePet.UMC.community.repository.commentRepository.CommentRepository;
import LittlePet.UMC.community.repository.postRepository.PostRepository;
import LittlePet.UMC.domain.BadgeEntity.mapping.UserBadge;
import LittlePet.UMC.domain.postEntity.Post;
import LittlePet.UMC.domain.postEntity.mapping.Comment;
import LittlePet.UMC.domain.userEntity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final BadgeCommandService badgeCommandService;
    @Transactional
    public CommentResponseDTO createComment(Long postId, CommentRequestDTO requestDTO) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        //대댓글일 경우 부모 댓글 찾기
        Comment parent = null;
        if (requestDTO.getParentId() != null) {
            parent = commentRepository.findById(requestDTO.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        }

        Comment comment = Comment.builder()
                .content(requestDTO.getContent())
                .post(post)
                .user(user)
                .parent(parent)
                .build();

        commentRepository.save(comment);

        //뱃지 조건 확인 및 수여
        System.out.println("뱃지 조건 확인 시작---------");

        if(badgeCommandService.checkBadges(requestDTO.getUserId(),"소통천재")) {
            UserBadge userBadge = badgeCommandService.assignBadge(requestDTO.getUserId(), "소통천재");
            if (userBadge != null) {
                log.info("User {} received a new badge: {}", requestDTO.getUserId(), userBadge.getBadge().getName());
            } else {
                log.info("User {} did not receive a new badge", requestDTO.getUserId());
            }
        }

        return CommentResponseDTO.of(comment);
    }


    @Transactional
    public CommentResponseDTO updateComment(Long commentId, CommentRequestDTO requestDTO) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        if (!comment.getUser().getId().equals(requestDTO.getUserId())) {
            throw new IllegalArgumentException("댓글 작성자만 수정할 수 있습니다.");
        }

        comment.updateContent(requestDTO.getContent());

        return CommentResponseDTO.of(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        if (!comment.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("댓글 작성자만 삭제할 수 있습니다.");
        }

        // 재귀적으로 대댓글 삭제
        deleteRepliesRecursive(comment);

        // 최종적으로 해당 댓글 삭제
        commentRepository.delete(comment);
    }

    private void deleteRepliesRecursive(Comment comment) { //재귀적으로 대댓글 삭제
        for (Comment reply : comment.getReplies()) {
            deleteRepliesRecursive(reply);
            commentRepository.delete(reply);
        }
    }

}
