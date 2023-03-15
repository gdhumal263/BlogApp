package com.BlogApp5.services;

import com.BlogApp5.payload.CommentDto;

import java.util.List;

public interface CommentServices
{
    CommentDto createComment(long postId,CommentDto commentDto);

    List<CommentDto> findByCommentId(long postId);

    CommentDto updateById(long postId,long id,CommentDto commentDto);

    void deleteById(long postId,long id);

}
