package com.spmb.domain.chapter07.fileupload;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @Description:
 * @Author: leiyulin
 * @date: 2018/6/9
 */
public class User implements Serializable {
    private String username;
    private MultipartFile image;

    public User() {
        super();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
