package com.BlogApp5.repositories;

import com.BlogApp5.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long>
{

}
