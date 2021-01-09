package com.yjs.mips.pojo.requestInfo;

import lombok.Data;

/**
 * 请求信息类
 */
@Data
public class RequestInfo {
    private String stu_id;
    private String request_header;
    private Long request_time;
}
