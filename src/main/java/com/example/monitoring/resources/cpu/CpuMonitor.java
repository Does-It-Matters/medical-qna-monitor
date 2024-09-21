package com.example.monitoring.resources.cpu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CpuMonitor {

    // file name은 linux의 /proc/stat 파일
    public CpuStatDTO getCpuStat(String fileName) throws IOException {
        CpuStatDTO dto = new CpuStatDTO();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                if (!line.startsWith("cpu")) {
                    break;
                }

                // 불필요한 연산 줄이도록 나중에 코드 수정
                String[] tokens = line.split("\\s+"); // 학습 내용: \\s: 모든 공백 문자(스페이스, 탭, 줄바꿈), +: 하나 이상
                String name = tokens[0];
                dto.putSingleCoreStat(name, getStatsOfSingleCore(tokens));
                System.out.println(name); // 임시 로그 
            }
        }

        return dto;
    }

    private List<Integer> getStatsOfSingleCore(String[] tokens) {
        List<Integer> stats = new ArrayList<>();

        for(int i = 1; i < tokens.length; i++) {
            stats.add(Integer.parseInt(tokens[i]));
        }
        return stats;
    }
}
