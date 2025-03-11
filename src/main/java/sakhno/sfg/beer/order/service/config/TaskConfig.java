package sakhno.sfg.beer.order.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Этот класс конфигурирует поддержку асинхронного выполнения задач и планирования заданий в Spring Boot.
 * EnableAsync - Включает поддержку асинхронного выполнения задач. Это позволяет использовать @Async для
 * выполнения методов в отдельном потоке.
 */
@EnableScheduling
@EnableAsync
@Configuration
public class TaskConfig {

    /**
     * Определяет бин TaskExecutor, который используется для асинхронного выполнения задач.
     * SimpleAsyncTaskExecutor — это простой исполнитель задач, создающий новый поток для каждой
     * задачи без управления пулом потоков.
     * @return - исполнитель задач в асинхронном режиме
     */
    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }
}
