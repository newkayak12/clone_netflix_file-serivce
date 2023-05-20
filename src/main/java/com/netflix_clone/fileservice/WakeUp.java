package com.netflix_clone.fileservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Created on 2023-05-04
 * Project user-service
 */
@Component
@Slf4j
public class WakeUp {

    @EventListener(value = {ApplicationReadyEvent.class})
    public void message(){
        log.warn("is ready");
    }
}
