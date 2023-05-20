package com.netflix_clone.fileservice;

import com.netflix_clone.fileservice.repository.fileRepository.FileRepository;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class WakeUp {
    private final FileRepository repository;

    @EventListener(value = {ApplicationReadyEvent.class})
    public void message(){
        log.warn(" {} is ready", repository.wakeUp("\n" +
                "\t\t\t  _____  _  _              ____               _                  \n" +
                "\t\t\t |  ___|(_)| |  ___       / ___|   ___  _ __ (_)__   __ ___  ___ \n" +
                "\t\t\t | |_   | || | / _ \\ _____\\___ \\  / _ \\| '__|| |\\ \\ / // __|/ _ \\\n" +
                "\t\t\t |  _|  | || ||  __/|_____|___) ||  __/| |   | | \\ V /| (__|  __/\n" +
                "\t\t\t |_|    |_||_| \\___|      |____/  \\___||_|   |_|  \\_/  \\___|\\___|  "));
    }
}
