package com.BlogApp5.repositories;

import com.BlogApp5.entities.Comment;
import com.BlogApp5.payload.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long>
{
    List<Comment>  findByPostId(long postId);
}
