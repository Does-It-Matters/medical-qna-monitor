package com.example.monitoring.cpu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cpu")
public class CpuMonitoringController {
    @Autowired
    private CpuMonitoringService service;

    @GetMapping("/v1/load")
    public Mono<Map<String, Double>> getSystemLoad() {
        return service.getSystemLoadAvg()
                .map(avg -> {
                    Map<String, Double> load = new HashMap<>();
                    load.put("loadAvg", avg);
                    return load;
                });
    }
}
