#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

/*
  - 목적
  (1) /proc/stat의 cpu0, ..., cpu7의 의미 이해
  (2) cpu 사용률 측정. ( cpu 사용률 = ( cpu 전체 시간 - cpu 유휴 시간 ) / cpu 전체 시간 )
*/

void get_cpu_usage(double *usage) {

    // cpu 시간 정보를 가진 /proc/stat 파일 읽기
    FILE *file;
    file = fopen("/proc/stat", "r");
    if (file == NULL) {
        perror("fopen");
        return;
    }


    // cpu0 ~ cpu7 값에 대한 변수 선언
    unsigned long long int cpu0;    // user mode에서의 cpu 시간
    unsigned long long int cpu1;    // nice 값(사용자가 프로세스에 부여한 우선순위) 조정된 user mode에서의 cpu 시간
    unsigned long long int cpu2;    // kernel mode에서의 cpu 시간
    unsigned long long int cpu3;    // idle(유휴 상태)로 소비한 cpu 시간
    unsigned long long int cpu4;    // I/O 대기로 소비한 cpu 시간
    unsigned long long int cpu5;    // 하드웨어 인터럽트 서비스 처리로 소비한 cpu 시간
    unsigned long long int cpu6;    // 소프트웨어 인터럽트 서비스 처리로 소비한 cpu 시간
    unsigned long long int cpu7;    // 가상화 환경에서 다른 가상 머신으로 소비한 cpu 시간


    // 이전 값을 유지하기 위해 static 변수로 진행
    // static 변수라서 쓰레기 값이 아닌 0의 값을 가짐
    static unsigned long long int prev_cpu0;
    static unsigned long long int prev_cpu1;
    static unsigned long long int prev_cpu2;
    static unsigned long long int prev_cpu3;
    static unsigned long long int prev_cpu4;
    static unsigned long long int prev_cpu5;
    static unsigned long long int prev_cpu6;
    static unsigned long long int prev_cpu7;


    // /proc/stat 파일에서 cpu 단어 이후 cpu0 ~ cpu7 값 할당
    fscanf(file, "cpu %llu %llu %llu %llu %llu %llu %llu %llu", &cpu0, &cpu1, &cpu2, &cpu3, &cpu4, &cpu5, &cpu6, &cpu7);
    fclose(file);


    // 전체 소비한 cpu 시간
    unsigned long long int total_cpu_time = cpu0 + cpu1 + cpu2 + cpu3 + cpu4 + cpu5 + cpu6 + cpu7;
    unsigned long long int prev_total_time = prev_cpu0 + prev_cpu1 + prev_cpu2 + prev_cpu3 + prev_cpu4 + prev_cpu5 + prev_cpu6 + prev_cpu7;

    unsigned long long int total_diff = total_cpu_time - prev_total_time;
    unsigned long long int idle_diff = cpu3 - prev_cpu3; // cpu3는 idle 시간


    // 구하고자 하는 cpu 사용률은 전체 시간에서 유휴 시간을 뺀 값을 전체 시간로 나눈 값
    *usage = 100.0 * (total_diff - idle_diff) / total_diff;


    // 다음 수치들을 위해 현재 값을 이전 변수에 저장
    prev_cpu0 = cpu0;
    prev_cpu1 = cpu1;
    prev_cpu2 = cpu2;
    prev_cpu3 = cpu3;
    prev_cpu4 = cpu4;
    prev_cpu5 = cpu5;
    prev_cpu6 = cpu6;
    prev_cpu7 = cpu7;
}

int main() {
    double usage;

    while (1) {
        get_cpu_usage(&usage);
        printf("CPU Usage: %.2f%%\n", usage);
        sleep(1); // 단위는 second
    }

    return 0;
}
