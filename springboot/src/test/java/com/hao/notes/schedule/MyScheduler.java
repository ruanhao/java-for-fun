package com.hao.notes.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
class MyScheduler {

    @Scheduled(cron="0 * * * * *")
    public void scheduleEveryMinute() {
        log.info("schedule every minute");
    }

    @Scheduled(cron="*/10 * * * * *")
    public void scheduleEvery10Seconds() {
        log.info("schedule every 10s");
    }
}
