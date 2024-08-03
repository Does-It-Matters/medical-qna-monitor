package com.example.monitoring.rsocket.cpu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CpuStatDTO {
    private Map<String, List<Integer>> cpuStats;

    public CpuStatDTO() {
        cpuStats = new HashMap<>();
    }

    public void putSingleCoreStat(String name, List<Integer> stat) {
        cpuStats.put(name, stat);
    }
}
