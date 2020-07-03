package com.mybatismbg.testmybatismbg.service.impl;

import com.mybatismbg.testmybatismbg.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AsyncServiceImpl implements AsyncService {

    @Async
    @Override
    public void hello() {
        // 休眠3s
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // springboot将会创建新的线程来执行这个方法
        log.info("Hello World!");
    }

    /*
    * 默认开启的是单线程的任务,顺序的进行执行
    * cron表达式:*秒*分*时*日*月*周几
    * eg:
    *       MON-FRI:周一到周五
    *       列举:0,1,2 * * * * 1-7 表示每个分钟的前3秒会执行
    *       区间:使用"-",0-4
    *       任意: *
    *       步长:0/4,每隔4秒执行
    *       最后:L
    * */
    @Scheduled(cron = "30 * * * * MON")
    @Override
    public void testSched(){
        // 定时计划
        log.warn("我是设置的定时任务");
    }


}
