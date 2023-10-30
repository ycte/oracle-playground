package com.op.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class CommitVO {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "昵称不能为空")
    private String username;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long blogId;

    private Long userId;

    private String text;

    private LocalDateTime created;

    private Integer status;
}
