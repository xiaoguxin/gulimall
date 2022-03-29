package com.atguigu.gulimall.member.vo;
import lombok.Data;

@Data
public class MemberRegistVo extends RuntimeException{

    private String userName;

    private String password;

    private String phone;
}
