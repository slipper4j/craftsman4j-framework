package com.craftsman4j.framework.operatelog.core.service;

import cn.hutool.extra.spring.SpringUtil;
import com.craftsman4j.framework.security.core.LoginUser;
import com.craftsman4j.framework.security.core.util.SecurityFrameworkUtils;
import com.mzt.logapi.beans.LogRecord;
import com.mzt.logapi.service.ILogRecordService;
import com.craftsman4j.framework.common.util.monitor.TracerUtils;
import com.craftsman4j.framework.common.util.servlet.ServletUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 操作日志 ILogRecordService 实现类
 * <p>
 * 基于 {@link } 实现，记录操作日志
 *
 * @author craftsman4j
 */
@Slf4j
public class LogRecordServiceImpl implements ILogRecordService {

    @Override
    public void record(LogRecord logRecord) {
        // 1. 补全通用字段
        OperateLogV2 operateLog = new OperateLogV2();
        operateLog.setTraceId(TracerUtils.getTraceId());
        // 补充用户信息
        fillUserFields(operateLog);
        // 补全模块信息
        fillModuleFields(operateLog, logRecord);
        // 补全请求信息
        fillRequestFields(operateLog);

        // 只负责发送事件，不负责消费事件，具体时间由使用者实现
        SpringUtil.getApplicationContext().publishEvent(operateLog);
        // TODO 测试结束删除或搞个开关
        log.info("操作日志 ===> {}", operateLog);
    }

    private static void fillUserFields(OperateLogV2 operateLog) {
        // 使用 SecurityFrameworkUtils。因为要考虑，rpc、mq、job，它其实不是 web；
        LoginUser loginUser = SecurityFrameworkUtils.getLoginUser();
        if (loginUser == null) {
            return;
        }
        operateLog.setUserId(loginUser.getId());
        operateLog.setUserType(loginUser.getUserType());
    }

    public static void fillModuleFields(OperateLogV2 operateLog, LogRecord logRecord) {
        operateLog.setType(logRecord.getType()); // 大模块类型，例如：CRM 客户
        operateLog.setSubType(logRecord.getSubType());// 操作名称，例如：转移客户
        operateLog.setBizId(Long.parseLong(logRecord.getBizNo())); // 业务编号，例如：客户编号
        operateLog.setAction(logRecord.getAction());// 操作内容，例如：修改编号为 1 的用户信息，将性别从男改成女，将姓名从craftsman4j改成源码。
        operateLog.setExtra(logRecord.getExtra()); // 拓展字段，有些复杂的业务，需要记录一些字段 ( JSON 格式 )，例如说，记录订单编号，{ orderId: "1"}
    }

    private static void fillRequestFields(OperateLogV2 operateLog) {
        // 获得 Request 对象
        HttpServletRequest request = ServletUtils.getRequest();
        if (request == null) {
            return;
        }
        // 补全请求信息
        operateLog.setRequestMethod(request.getMethod());
        operateLog.setRequestUrl(request.getRequestURI());
        operateLog.setUserIp(ServletUtils.getClientIP(request));
        operateLog.setUserAgent(ServletUtils.getUserAgent(request));
    }

    @Override
    public List<LogRecord> queryLog(String bizNo, String type) {
        throw new UnsupportedOperationException("使用 OperateLogV2Api 进行操作日志的查询");
    }

    @Override
    public List<LogRecord> queryLogByBizNo(String bizNo, String type, String subType) {
        throw new UnsupportedOperationException("使用 OperateLogV2Api 进行操作日志的查询");
    }

}