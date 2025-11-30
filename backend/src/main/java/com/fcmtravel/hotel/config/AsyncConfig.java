package com.fcmtravel.hotel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Configuration class enabling asynchronous execution using Spring @Async.
 *
 * <p>Registers a ThreadPoolTaskExecutor which is used to run external hotel
 * provider calls in parallel. This reduces total response latency significantly
 * when aggregating results from multiple providers.</p>
 *
 * Features:
 * - Defines async executor thread pool size
 * - Enables @Async method execution in the application
 *
 * Used By:
 *  → HotelAggregationService
 *  → ProviderAClient / ProviderBClient (asynchronous external calls)
 */
@Configuration
public class AsyncConfig {

    @Bean(name = "hotelTaskExecutor")
    public Executor hotelTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(8);
        executor.setMaxPoolSize(16);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("hotel-async-");
        executor.initialize();
        return executor;
    }
}
