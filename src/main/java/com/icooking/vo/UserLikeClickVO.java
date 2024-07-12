package com.icooking.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 点击点赞，要返回给前端的数据类型
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLikeClickVO implements Serializable {
    // 点击后点赞状态
    private int status;
    // 点击后点赞数
    private int likesCount;

}
