package com.blackmoon.tutopic.data;


public class Featured {
    public static final int TEMPLATE_A = 1;
    public static final int TEMPLATE_B = TEMPLATE_A + 1;

    public int template = TEMPLATE_A;// 精选页面模板编号1：横版，2：竖版
    public Movie[] items;
}
