package br.com.kirio.transacoes.service.transacoes;

import java.net.MalformedURLException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

public class TransacoesSpringBatchConfig {
	
	@Autowired
    private JobBuilderFactory jobs;
 
    @Autowired
    private StepBuilderFactory steps;
 
    @Value("input/record.csv")
    private Resource inputCsv;
 
    @Value("file:xml/output.xml")
    private Resource outputXml;
 
    @Bean
    public ItemReader<Transacao> itemReader()
      throws UnexpectedInputException, ParseException {
        FlatFileItemReader<Transacao> reader = new FlatFileItemReader<Transacao>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        String[] tokens = {"username","user_id","transaction_date","transaction_amount"};
        tokenizer.setNames(tokens);
        reader.setResource(inputCsv);
        DefaultLineMapper<Transacao> lineMapper = new DefaultLineMapper<Transacao>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new RecordFieldSetMapper());
        reader.setLinesToSkip(1);
        reader.setLineMapper(lineMapper);
        return reader;
    }
 
    @Bean
    public ItemProcessor<Transacao, Transacao> itemProcessor() {
        return new CustomItemProcessor();
    }
 
    @Bean
    public ItemWriter<Transacao> itemWriter(Marshaller marshaller) throws MalformedURLException {
        StaxEventItemWriter<Transacao> itemWriter = new StaxEventItemWriter<Transacao>();
        itemWriter.setMarshaller(marshaller);
        itemWriter.setRootTagName("transactionRecord");
        itemWriter.setResource(outputXml);
        return itemWriter;
    }
 
    @Bean
    public Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(Transacao.class);
        return marshaller;
    }
 
    @Bean
    protected Step step1(ItemReader<Transacao> reader, ItemProcessor<Transacao, Transacao> processor, ItemWriter<Transacao> writer) {
        return steps.get("step1")
        		    .<Transacao, Transacao> chunk(10)
        		    .reader(reader)
        		    .processor(processor)
        		    .writer(writer)
        		    .build();
    }
 
    @Bean(name = "firstBatchJob")
    public Job job(@Qualifier("step1") Step step1) {
        return jobs.get("firstBatchJob").start(step1).build();
    }
}