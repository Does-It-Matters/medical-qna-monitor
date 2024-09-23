package com.example.monitoring.resources.cpu;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Duration;

@Controller
public class CpuStatsController {
    private static final String TEST_PROC_STAT = "./linux/proc/stat.txt";
    private static final String REAL_PROC_STAT = "/proc/stat";
    private static final String PROC_STAT = TEST_PROC_STAT;

    private final CpuMonitor cpuMonitor = new CpuMonitor();

    // CPU 통계를 전송하는 엔드포인트
    @MessageMapping("cpu-stats")
    public Mono<CpuStatDTO> getCpuStats() {
        try {
            CpuStatDTO cpuStatDTO = cpuMonitor.getCpuStat(PROC_STAT);
            return Mono.just(cpuStatDTO);
        } catch (IOException e) {
            return Mono.error(e);
        }
    }

    // 0.2초마다 CPU 통계를 전송하는 엔드포인트
    @MessageMapping("cpu-stats-stream")
    public Flux<CpuStatDTO> streamCpuStats() {
        return Flux.interval(Duration.ofMillis(200))
                .flatMap(tick -> {
                    try {
                        CpuStatDTO cpuStatDTO = cpuMonitor.getCpuStat(PROC_STAT);
                        return Mono.just(cpuStatDTO);
                    } catch (IOException e) {
                        return Mono.error(e);
                    }
                });
    }
}
