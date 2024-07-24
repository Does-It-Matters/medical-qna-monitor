package com.example.monitoring.webflux.exercise.monitor.cpu;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

@Service
public class CpuMonitoringService {

    private final OperatingSystemMXBean osBean;

    public CpuMonitoringService() {
        this.osBean = ManagementFactory.getOperatingSystemMXBean();
    }

    public Mono<Double> getSystemLoadAvg() {
        return Mono.fromCallable(osBean::getSystemLoadAverage);
    }
}
