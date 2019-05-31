package br.com.kirio.transacoes.service.transacoes;

import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor implements ItemProcessor<Transacao, Transacao> {

	public Transacao process(Transacao item) throws Exception {
		System.out.println("Processing..." + item.toString());
		return item;
	}

}
