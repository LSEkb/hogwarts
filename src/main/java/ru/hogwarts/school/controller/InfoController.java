package ru.hogwarts.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.impl.FacultyServiceImpl;

import java.util.stream.Stream;

@RestController
@RequestMapping("/info")
public class InfoController {
    Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);
    @Value("${server.port}")
    private Integer port;

    @GetMapping("/getPort")
    public Integer getPort() {
        return port;
    }

    @GetMapping("/sum")
    public Integer sum() {
        logger.info("The Sum method was called");
        long start = System.currentTimeMillis();
        int sum = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
        long runningTime = System.currentTimeMillis() - start;
        logger.info("Returned from the Sum method at {} ms", runningTime);
        return sum;
        // По итогам многократного запуска выявлено, что распараллеливание как до, так и после метода limit()
        // ухудшает производительность метода - поэтому данный стрим оставлен без изменений. В файле логов сохранились
        // данные запуска (первый - для непараллельного стрима). Даже после прогрева параллельный стрим медленнее
    }
}
