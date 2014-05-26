package com.blackmoon.tutopic.data;

import com.google.gson.annotations.SerializedName;

public class JsonpResponse<T> {

    @SerializedName("rtn")
    public int returnCode;

    public T data;
}
