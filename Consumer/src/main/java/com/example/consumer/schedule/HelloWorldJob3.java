package com.example.consumer.schedule;
import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author lgk
 * @classDesc: 功能描述(定时任务调度)
 * @date 2025/1/31
 */
@Component
@Slf4j
public class HelloWorldJob3 extends JavaProcessor {

    @Override
    public ProcessResult process(JobContext context) {
        log.info("hello HelloWorldJob3");
        return new ProcessResult(true);
    }

}