package com.op.common.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class chgPasswdDto implements Serializable {
//    @NotBlank(message = "id 不能为空")
    private Long id;

//    @NotBlank(message = "密码不能为空")
    private String oldpassword;

//    @NotBlank(message = "密码不能为空")
    private String password;

//    @NotBlank(message = "密码不能为空")
    private String password2;

}