package br.com.kirio.transacoes.service.transacoes;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

	public static void main(String[] args) {	
		final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(TransacoesConfig.class);
		context.register(TransacoesSpringBatchConfig.class);
		context.refresh();
		
		final JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		final Job job = (Job) context.getBean("firstBatchJob");
		System.out.println("Starting the batch job");
		try {
			final JobExecution execution = jobLauncher.run(job, new JobParameters());
			System.out.println("Job Status : " + execution.getStatus());
			System.out.println("Job completed");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Job failed");
		}
	}
}
