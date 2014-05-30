package com.kankan.tutopic.model;

import java.io.Serializable;

public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public int id;// 评论id
    public String content;// 评论内容
    public int start;// 被心数量
    public boolean isStared;// 是否被当前用户心
}
