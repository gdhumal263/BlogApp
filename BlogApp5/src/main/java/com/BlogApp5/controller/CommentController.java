package com.BlogApp5.controller;

import com.BlogApp5.payload.CommentDto;
import com.BlogApp5.services.CommentServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController
{
    private CommentServices commentServices;

    public CommentController(CommentServices commentServices)
    {
        this.commentServices = commentServices;
    }

    @PostMapping("posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable("postId")long postId,
                                                    @RequestBody CommentDto commentDto)
    {
        CommentDto dto = commentServices.createComment(postId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("posts/{postId}/comments")
    public List<CommentDto> getBypostId(@PathVariable("postId")long postId)
    {
        List<CommentDto> dto = commentServices.findByCommentId(postId);
        return dto;
    }

    @PutMapping("posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("postId")long postId,
                                                    @PathVariable("id")long id,
                                                    @RequestBody CommentDto commentDto)
    {
        CommentDto dto = commentServices.updateById(postId, id, commentDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);

    }

    @DeleteMapping("posts/{postId}/comments/{id}")
    public ResponseEntity<String>  deleteById(@PathVariable("postId")long postId,
                                              @PathVariable("id")long id)
    {
        commentServices.deleteById(postId,id);
        return new ResponseEntity<>("Comment deleted successfully..!",HttpStatus.OK);
    }
}
