package com.BlogApp5.services;

import com.BlogApp5.payload.PostDto;
import com.BlogApp5.payload.PostResponse;

import java.util.List;

public interface PostService
{

    PostDto createPosts(PostDto postDto);

    PostResponse getPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getOnePosts(long id);

    PostDto updatePosts(long id, PostDto postDto);

    void deletePosts(long id);
}
