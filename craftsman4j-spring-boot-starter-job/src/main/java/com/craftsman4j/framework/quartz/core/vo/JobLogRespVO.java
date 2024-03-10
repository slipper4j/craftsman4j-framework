package com.craftsman4j.framework.quartz.core.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 定时任务日志 Response VO")
@Data
public class JobLogRespVO {

    @Schema(description = "日志编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "任务编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long jobId;

    @Schema(description = "处理器的名字", requiredMode = Schema.RequiredMode.REQUIRED, example = "sysUserSessionTimeoutJob")
    private String handlerName;

    @Schema(description = "处理器的参数", example = "")
    private String handlerParam;

    @Schema(description = "第几次执行", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer executeIndex;

    @Schema(description = "开始执行时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime beginTime;

    @Schema(description = "结束执行时间")
    private LocalDateTime endTime;

    @Schema(description = "执行时长", example = "123")
    private Integer duration;

    @Schema(description = "任务状态，参见 JobLogStatusEnum 枚举", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "结果数据", example = "执行成功")
    private String result;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
