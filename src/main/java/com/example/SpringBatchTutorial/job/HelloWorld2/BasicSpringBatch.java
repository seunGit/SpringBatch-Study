package com.example.SpringBatchTutorial.job.HelloWorld2;

import lombok.RequiredArgsConstructor;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 코드의 흐름을 파악하기 위해 따로 만든 클래스이다.
 * 정상적으로 콘솔창에 Hello Spring Batch 가 출력이 되어야 한다.
 * 아....
 */

@Configuration      // 하나의 배치 job을 정의하고 빈 설정을 한다.
@RequiredArgsConstructor
public class BasicSpringBatch {

    private final JobBuilderFactory jobBuilderFactory;      // job, step을 생성하는 빌더 팩토리.
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job helloJob() {     // helloJob라는 이름으로 Job을 생성한다.
        return jobBuilderFactory.get("helloJob")
// 1. job이 생성되면 start(helloStep)으로 Step을 실행한다.
                .start(helloStep())
                .build();
    }

    @Bean
    public Step helloStep() {       // helloStep이라는 이름으로 Step을 생성한다.
// 2. stepBuilderFactory.get("helloStep")으로 생성된 Step이 job에 의해 구동되면
        return stepBuilderFactory.get("helloStep")
// 3. tasklet()을 실행하고 생성된 Tasklet 객체가 실행이 된다.
                .tasklet((contribution, chunkContext)->{        // Step안에서 단일 태스크로 수행되는 로직을 수행한다.
                    System.out.println("=========================");
                    System.out.println("Hello Spring Batch");
                    System.out.println("=========================");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
