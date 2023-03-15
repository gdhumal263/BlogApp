package com.BlogApp5.services;

import com.BlogApp5.entities.Post;
import com.BlogApp5.exception.ResourceNotFoundException;
import com.BlogApp5.payload.PostDto;
import com.BlogApp5.payload.PostResponse;
import com.BlogApp5.repositories.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServicesImpl implements PostService
{

    private PostRepository postRepo;

    public PostServicesImpl(PostRepository postRepo)
    {
        this.postRepo = postRepo;
    }

    @Override
    public PostDto createPosts(PostDto postDto)
    {
        Post post = mapToEntity(postDto);
        Post save = postRepo.save(post);
        PostDto dto = mapToDto(save);
        return dto;
    }

    @Override
    public PostResponse getPosts(int pageNo,int pageSize,String sortBy,String sortDir)
    {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageble= (Pageable) PageRequest.of(pageNo,pageSize, sort);

        Page<Post> posts = postRepo.findAll(pageble);
        List<Post> content = posts.getContent();
        List<PostDto> contents=content.stream().map(post1 -> mapToDto(post1)).collect(Collectors.toList());

        PostResponse postResponse=new PostResponse();
        postResponse.setContent(contents);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setLast(posts.isLast());
        return postResponse;

    }

    @Override
    public PostDto getOnePosts(long id)
    {
        Post post= postRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("post","id",id)
        );
        PostDto postDto = mapToDto(post);
        return postDto;
    }

    @Override
    public PostDto updatePosts(long id, PostDto postDto)
    {
       Post post=postRepo.findById(id).orElseThrow(
            ()-> new ResourceNotFoundException("post","id",id)
    );
       post.setTitle(postDto.getTitle());
       post.setDescription(postDto.getDescription());
       post.setContent(postDto.getContent());
        Post post1 = postRepo.save(post);

        return mapToDto(post1);
    }

    @Override
    public void deletePosts(long id)
    {
        postRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("post","id",id)
        );
        postRepo.deleteById(id);
    }


    public Post mapToEntity(PostDto postDto)
    {
        Post post=new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }

    public  PostDto mapToDto(Post post)
    {
        PostDto postDto=new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }
}
