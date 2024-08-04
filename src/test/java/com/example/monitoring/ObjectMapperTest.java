package com.example.monitoring;

import com.example.monitoring.resources.cpu.CpuStatDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObjectMapperTest {
    @Test
    public void testSerialization() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CpuStatDTO dto = new CpuStatDTO();
        dto.putSingleCoreStat("cpu0", List.of(100, 200, 300, 400));

        String json = objectMapper.writeValueAsString(dto);
        CpuStatDTO deserializedDto = objectMapper.readValue(json, CpuStatDTO.class);

        assertEquals(dto.getCpuStats(), deserializedDto.getCpuStats());
    }
}
