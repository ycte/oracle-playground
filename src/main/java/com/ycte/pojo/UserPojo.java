package com.ycte.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "Users")
public class UserPojo {

    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    @TableField(value = "userId")
    private String userId;

    @TableField(value = "username")
    private String userName;

    @TableField(value = "password")
    private String password;

    @TableField(value = "college")
    private String college;

    @TableField(value = "name")
    private String name;

    @TableField(value = "tel")
    private String tel;

    @TableField(value = "address")
    private String address;


}
