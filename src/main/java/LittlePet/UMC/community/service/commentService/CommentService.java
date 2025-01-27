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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentResponseDTO addComment((CommentRequestDTO commentRequestDTO){
        User user = userRepository
    });

}
