package com.BlogApp5.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CREATED)
public class ResourceNotFoundException extends RuntimeException
{
    private String resourceName;
    private String feildName;
    private long feildValue;

    public ResourceNotFoundException(String resourceName,String feildName,long feildValue)
    {
        super(String.format("%s not found with %s:%s",resourceName,feildName,feildValue));
        this.resourceName=resourceName;
        this.feildName=feildName;
        this.feildValue=feildValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFeildName() {
        return feildName;
    }

    public long getFeildValue() {
        return feildValue;
    }
}
