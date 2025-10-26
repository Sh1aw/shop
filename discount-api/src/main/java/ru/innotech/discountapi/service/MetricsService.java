package ru.innotech.discountapi.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class MetricsService {
    private final MeterRegistry meterRegistry;

    private final Map<String, Counter> errorsCounter = new ConcurrentHashMap<>();

    public void incrementErrorCounter(String operation) {
        errorsCounter.computeIfAbsent(operation, o -> meterRegistry.counter("service.errors", "operation", o))
                .increment();
    }
}
