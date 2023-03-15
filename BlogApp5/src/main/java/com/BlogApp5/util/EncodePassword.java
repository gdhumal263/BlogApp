package com.BlogApp5.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EncodePassword
{
    public static void main(String[] args)
    {
        PasswordEncoder encodepassword=new BCryptPasswordEncoder();
        System.out.println(encodepassword.encode("admin"));

    }
}
