#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

/*
  - 목적
  (1) cpu 이후 숫자 값 이해
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


    // cpu 단어 이후 8가지 숫자값 이해
    unsigned long long int num0;    // user mode에서의 cpu 시간
    unsigned long long int num1;    // nice 값(사용자가 프로세스에 부여한 우선순위) 조정된 user mode에서의 cpu 시간
    unsigned long long int num2;    // kernel mode에서의 cpu 시간
    unsigned long long int num3;    // idle(유휴 상태)로 소비한 cpu 시간
    unsigned long long int num4;    // I/O 대기로 소비한 cpu 시간
    unsigned long long int num5;    // 하드웨어 인터럽트 서비스 처리로 소비한 cpu 시간
    unsigned long long int num6;    // 소프트웨어 인터럽트 서비스 처리로 소비한 cpu 시간
    unsigned long long int num7;    // 가상화 환경에서 다른 가상 머신으로 소비한 cpu 시간


    // 이전 값을 유지하기 위해 static 변수로 진행
    // static 변수라서 쓰레기 값이 아닌 0의 값을 가짐
    static unsigned long long int prev_num0;
    static unsigned long long int prev_num1;
    static unsigned long long int prev_num2;
    static unsigned long long int prev_num3;
    static unsigned long long int prev_num4;
    static unsigned long long int prev_num5;
    static unsigned long long int prev_num6;
    static unsigned long long int prev_num7;


    // /proc/stat 파일에서 cpu 단어 이후 num0 ~ num7 값 할당
    fscanf(file, "cpu %llu %llu %llu %llu %llu %llu %llu %llu", &num0, &num1, &num2, &num3, &num4, &num5, &num6, &num7);
    fclose(file);


    // 전체 소비한 cpu 시간
    unsigned long long int total_cpu_time = num0 + num1 + num2 + num3 + num4 + num5 + num6 + num7;
    unsigned long long int prev_total_time = prev_num0 + prev_num1 + prev_num2 + prev_num3 + prev_num4 + prev_num5 + prev_num6 + prev_num7;

    unsigned long long int total_diff = total_cpu_time - prev_total_time;
    unsigned long long int idle_diff = num3 - prev_num3; // num3는 idle 시간


    // 구하고자 하는 cpu 사용률은 전체 시간에서 유휴 시간을 뺀 값을 전체 시간로 나눈 값
    *usage = 100.0 * (total_diff - idle_diff) / total_diff;


    // 다음 수치들을 위해 현재 값을 이전 변수에 저장
    prev_num0 = num0;
    prev_num1 = num1;
    prev_num2 = num2;
    prev_num3 = num3;
    prev_num4 = num4;
    prev_num5 = num5;
    prev_num6 = num6;
    prev_num7 = num7;
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
