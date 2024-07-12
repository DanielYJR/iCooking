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
public class UserMarkClickVO implements Serializable{

    // 点击后收藏状态
    private int status;
    // 点击后该菜谱收藏数
    private int marksCount;

}
