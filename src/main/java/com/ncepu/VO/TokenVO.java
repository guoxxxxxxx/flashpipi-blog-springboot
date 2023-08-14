package com.ncepu.VO;

import lombok.Data;

@Data
public class TokenVO {
    Integer status;        // 若代码为444则为Token过期或不存在，前端跳转到登录界面
}
