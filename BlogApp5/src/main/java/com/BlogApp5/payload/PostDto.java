package com.BlogApp5.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PostDto
{
    private long id;


    @NotNull
    @Size(min=2,message = "Post title should have at least 2 chracter")
    private String title;


    private String description;


    private String content;
}
