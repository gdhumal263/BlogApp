package com.BlogApp5.controller;

import com.BlogApp5.payload.PostDto;
import com.BlogApp5.payload.PostResponse;
import com.BlogApp5.services.PostService;
import com.BlogApp5.util.AddConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")

public class PostController
{
    private PostService postService;


    public PostController(PostService postService)
    {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Object> createPosts(@Valid @RequestBody PostDto postDto, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }

        PostDto dto = postService.createPosts(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    @GetMapping
    public PostResponse getPosts(@RequestParam(value="pageNo",defaultValue = AddConstant.DEFAULT_PAGE_NUMBER,required = false)int pageNo,
                                 @RequestParam(value = "pageSize",defaultValue = AddConstant.DEFAULT_PAGE_SIZE,required = false)int pageSize,
                                 @RequestParam(value = "sortBy",defaultValue = AddConstant.DEFAULT_SORT_BY,required = false)String sortBy,
                                 @RequestParam(value = "sortDir",defaultValue = AddConstant.DEFAULT_SORT_DIR,required = false)String sortDir)
    {
        return postService.getPosts(pageNo,pageSize,sortBy,sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto>  getPosts(@PathVariable("id")long id)
    {
        PostDto dto = postService.getOnePosts(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePosts(@PathVariable("id")long id,
                                               @RequestBody PostDto postDto)
    {
        PostDto postDto1 = postService.updatePosts(id, postDto);
        return new ResponseEntity<>(postDto1,HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id")long id)
    {
        postService.deletePosts(id);
        return new ResponseEntity<>("Record deleted successfully..!!",HttpStatus.OK);
    }
}
