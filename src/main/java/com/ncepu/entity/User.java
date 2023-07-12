package com.ncepu.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class User {
    private Integer id;
    private String name;
    private String sex;
    private String phone;
    private String email;
    private Integer isDelete;
    private String password;
    private String avatar;
    private static final long serialVersionUID = 1L;
}
