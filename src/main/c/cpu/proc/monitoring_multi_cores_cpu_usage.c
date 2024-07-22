#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

/*
  - 목적
  (1) /proc/stat의 cpu0, ..., cpu7의 의미 이해
  (2) cpu 코어별 사용률 측정. ( 특정 코어 기준: cpu 사용률 = ( cpu 전체 시간 - cpu 유휴 시간 ) / cpu 전체 시간 )
*/

// 코어 수 정의
#define NUM_CORES 8

void get_cpu_usage(double usage[]) {
    // cpu 시간 정보를 가진 /proc/stat 파일 읽기
    FILE *file;
    file = fopen("/proc/stat", "r");
    if (file == NULL) {
        perror("fopen");
        return;
    }


    // 각 코어별(cpu0~cpu7) 단어 이후 10가지 숫자값
    unsigned long long int num0;    // user mode에서의 cpu 시간
    unsigned long long int num1;    // nice 값(사용자가 프로세스에 부여한 우선순위) 조정된 user mode에서의 cpu 시간
    unsigned long long int num2;    // kernel mode에서의 cpu 시간
    unsigned long long int num3;    // idle(유휴 상태)로 소비한 cpu 시간
    unsigned long long int num4;    // I/O 대기로 소비한 cpu 시간
    unsigned long long int num5;    // 하드웨어 인터럽트 서비스 처리로 소비한 cpu 시간
    unsigned long long int num6;    // 소프트웨어 인터럽트 서비스 처리로 소비한 cpu 시간
    unsigned long long int num7;    // 가상화 환경에서 다른 가상 머신으로 소비한 cpu 시간
    unsigned long long int num8;    // 기타 값
    unsigned long long int num9;    // 기타 값


    // 이전 값을 유지하기 위해 static 변수로 진행
    static unsigned long long int prev_num0[NUM_CORES] = {0};
    static unsigned long long int prev_num1[NUM_CORES] = {0};
    static unsigned long long int prev_num2[NUM_CORES] = {0};
    static unsigned long long int prev_num3[NUM_CORES] = {0};
    static unsigned long long int prev_num4[NUM_CORES] = {0};
    static unsigned long long int prev_num5[NUM_CORES] = {0};
    static unsigned long long int prev_num6[NUM_CORES] = {0};
    static unsigned long long int prev_num7[NUM_CORES] = {0};
    static unsigned long long int prev_num8[NUM_CORES] = {0};
    static unsigned long long int prev_num9[NUM_CORES] = {0};


    for (int i = 0; i < NUM_CORES; i++) {
        char cpu_name[5]; // "cpu" + 한 자리 숫자(0-7) + 종료 문자

        // 특정 코어별로 num0 ~ num9 값
        int result = fscanf(file, "%4s %llu %llu %llu %llu %llu %llu %llu %llu %llu %llu", cpu_name, &num0, &num1, &num2, &num3, &num4, &num5, &num6, &num7, &num8, &num9);


        if (result != 11 || strncmp(cpu_name, "cpu", 3) != 0 || cpu_name[3] < '0' || cpu_name[3] > '7') {
            // 올바른 형식이 아니면 다음 줄로 넘어감
            fscanf(file, "%*[^\n]\n");
            i--;
            continue;
        }


        // 전체 소비한 cpu 시간
        unsigned long long int total_cpu_time = num0 + num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9;
        unsigned long long int prev_total_time = prev_num0[i] + prev_num1[i] + prev_num2[i] + prev_num3[i] + prev_num4[i] + prev_num5[i] + prev_num6[i] + prev_num7[i] + prev_num8[i] + prev_num9[i];

        unsigned long long int total_diff = total_cpu_time - prev_total_time;
        unsigned long long int idle_diff = num3 - prev_num3[i];// num3는 idle 시간


        // 구하고자 하는 cpu 사용률은 전체 시간에서 유휴 시간을 뺀 값을 전체 시간로 나눈 값
        usage[i] = 100.0 * (total_diff - idle_diff) / total_diff;


        // 다음 수치들을 위해 현재 값을 이전 변수에 저장
        prev_num0[i] = num0;
        prev_num1[i] = num1;
        prev_num2[i] = num2;
        prev_num3[i] = num3;
        prev_num4[i] = num4;
        prev_num5[i] = num5;
        prev_num6[i] = num6;
        prev_num7[i] = num7;
        prev_num8[i] = num8;
        prev_num9[i] = num9;
    }

    fclose(file);
}

int main() {
    double usage[NUM_CORES];

    while (1) {
        get_cpu_usage(usage);
        for (int i = 0; i < NUM_CORES; i++) {
            printf("%d Core Usage: %.2f%%\n", i + 1, usage[i]);
        }
        printf("\n");
        sleep(1); // 단위는 초
    }

    return 0;
}
