package com.example.monitoring.resources.cpu;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CpuStatDTO {
    private Map<String, List<Integer>> cpuStats;

    public CpuStatDTO() {
        cpuStats = new HashMap<>();
    }

    @JsonAnyGetter
    public Map<String, List<Integer>> getCpuStats() {
        return cpuStats;
    }

    @JsonAnySetter
    public void setCpuStat(String key, List<Integer> value) {
        cpuStats.put(key, value);
    }

    public void putSingleCoreStat(String name, List<Integer> stat) {
        cpuStats.put(name, stat);
    }
}