package com.example.SpringBatchTutorial.job.HelloWorld;

import lombok.RequiredArgsConstructor;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * desc: tasklet을 활용하여 Hello World를 출력
 * run: --job.names=helloWorldJob
 */

@Configuration
@RequiredArgsConstructor
public class HelloWorldJobConfig {
    private final JobBuilderFactory jobBuilderFactory;  // @Autowired 사용시 오류가 나서 final로 설정함...
    private final StepBuilderFactory stepBuilderFactory; // 이유는 잘 모르겠음...

    @Bean // 빈 등록
    public Job helloWorldJob() { // 잡 이름 생성
        return jobBuilderFactory.get("helloWorldJob")
                .incrementer(new RunIdIncrementer()) // 시퀀스를 순차적으 부여할수 있도록 함
                .start(helloWorldStep())
                .build();
    }

    @JobScope
    @Bean
    public Step helloWorldStep() { // 스텝 하위에는 read, process, write 가 존재함.
        return stepBuilderFactory.get("helloWorldStep")
                /* * 읽고 쓸게 없는 간단한 배치 작업을 하기 위해 설정함 */
                .tasklet(helloWorldTasklet())
                .build();
    }

    @StepScope // 스텝 하위에서 실행되기 떄문에 @StepScope 명시
    @Bean
    public Tasklet helloWorldTasklet() {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("Hello World Spring Batch");
                return RepeatStatus.FINISHED;
            }
        };
    }
}