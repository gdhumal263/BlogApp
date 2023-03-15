package com.BlogApp5.services;

import com.BlogApp5.entities.Comment;
import com.BlogApp5.entities.Post;
import com.BlogApp5.exception.ResourceNotFoundException;
import com.BlogApp5.payload.CommentDto;
import com.BlogApp5.repositories.CommentRepository;
import com.BlogApp5.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServicesImpl implements CommentServices
{
    private CommentRepository commentRepo;
    private PostRepository postRepo;

    public CommentServicesImpl(CommentRepository commentRepo, PostRepository postRepo)
    {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
    }
    @Override
    public CommentDto createComment(long postId, CommentDto commentDto)
    {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId)
        );
        Comment comment = mapToComment(commentDto);
        comment.setPost(post);
        Comment save = commentRepo.save(comment);
        return  mapToDto(save);
    }

    @Override
    public List<CommentDto> findByCommentId(long postId)
    {
        List<Comment> comments = commentRepo.findByPostId(postId);
        return comments.stream().map(c ->mapToDto(c)).collect(Collectors.toList());
    }

    @Override
    public CommentDto updateById(long postId, long id,CommentDto commentDto)
    {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId)
        );
        Comment comment = commentRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", id)
        );
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment save = commentRepo.save(comment);
        return mapToDto(save);
    }

    @Override
    public void deleteById(long postId, long id)
    {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId)
        );
        Comment comment = commentRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", id)
        );
        commentRepo.deleteById(id);

    }


    Comment mapToComment(CommentDto commentDto)
    {
        Comment comment=new Comment();
        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());
        return comment;
    }

    CommentDto mapToDto(Comment comment)
    {
        CommentDto dto=new CommentDto();
        dto.setId(comment.getId());
        dto.setName(comment.getName());
        dto.setEmail(comment.getEmail());
        dto.setBody(comment.getBody());
        return dto;
    }


}
