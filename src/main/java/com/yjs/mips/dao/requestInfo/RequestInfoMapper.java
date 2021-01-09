package com.yjs.mips.dao.requestInfo;

import com.yjs.mips.pojo.requestInfo.RequestInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface RequestInfoMapper {
//    public RequestInfo getRequestInfo(String stu_id);
        public void setRequestInfo(RequestInfo requestInfo);
}

