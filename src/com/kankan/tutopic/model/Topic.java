package com.kankan.tutopic.model;

import java.io.Serializable;

public class Topic implements Serializable {

    private static final long serialVersionUID = 1L;
    public int groupId;// 话题组id
    public int id;// 话题id
    public String poster;// 话题图片
    public String title;// 话题名称
    public String summary;// 话题简介
    public String[] tags;// 话题的标签
    public int star;// 被心数量
    public int isStared;// 是否被当前用户心

    public Comment[] comments;// 评论
    public int totalComments;// 总的评论数
    public int commentPageSize;// 评论页大小

    public Topic[] sibling;// 话题擂台,此话题组中当前分页的话题
    public int totalTopic;// 此话题组下面，总话题数
    public int topicPageSize;// 话题页大小

    public long timeStamp;
}
