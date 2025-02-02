package LittlePet.UMC.community.service.commentService;

import LittlePet.UMC.User.repository.UserRepository;
import LittlePet.UMC.community.dto.commentDTO.CommentRequestDTO;
import LittlePet.UMC.community.dto.commentDTO.CommentResponseDTO;
import LittlePet.UMC.community.repository.commentRepository.CommentRepository;
import LittlePet.UMC.community.repository.postRepository.PostRepository;
import LittlePet.UMC.domain.postEntity.Post;
import LittlePet.UMC.domain.postEntity.mapping.Comment;
import LittlePet.UMC.domain.userEntity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

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
