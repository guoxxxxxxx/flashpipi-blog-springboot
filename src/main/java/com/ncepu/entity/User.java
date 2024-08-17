package com.ncepu.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Entity
@Table(name = "user")
public class User {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @Id
    @Column(name = "id", unique = true, columnDefinition = "int")
    private Integer id;

    /**
     * 用户姓名
     */
    @Column(name = "name", columnDefinition = "varchar(32)")
    private String name;

    /**
     * 用户邮箱地址
     */
    @Column(name = "email", columnDefinition = "varchar(256)")
    private String email;

    /**
     * 用户密码
     */
    @Column(name = "password", columnDefinition = "varchar(512)")
    private String password;

    /**
     * 用户头像地址
     */
    @Column(name = "avatar", columnDefinition = "varchar(512)")
    private String avatar;

    /**
     * 用户权限等级
     */
    @Column(name = "rank_level", columnDefinition = "int")
    private Integer rankLevel;
}
