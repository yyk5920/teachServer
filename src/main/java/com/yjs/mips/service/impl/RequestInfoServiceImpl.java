package com.yjs.mips.service.impl;

import com.yjs.mips.dao.requestInfo.RequestInfoMapper;
import com.yjs.mips.pojo.requestInfo.RequestInfo;
import com.yjs.mips.service.RequestInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RequestInfoServiceImpl implements RequestInfoService {
    @Autowired
    RequestInfoMapper requestInfoMapper;
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void setRequestInfo(RequestInfo requestInfo){
        requestInfoMapper.setRequestInfo(requestInfo);

    }
}
