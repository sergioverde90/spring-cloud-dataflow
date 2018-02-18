package com.sergio.example;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * Hello world!
 *
 */
@EnableBinding(Sink.class)
@SpringBootApplication
@Slf4j
public class App
{
    Logger log = LoggerFactory.getLogger(App.class);

    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }

    @StreamListener(Sink.INPUT)
    public void loggerSink(String dateParsed) {
        log.info("dateParsed = " + dateParsed);
    }
}
