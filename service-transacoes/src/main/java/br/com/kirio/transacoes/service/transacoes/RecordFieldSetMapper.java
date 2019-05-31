package br.com.kirio.transacoes.service.transacoes;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class RecordFieldSetMapper implements FieldSetMapper<Transacao> {

	public Transacao mapFieldSet(FieldSet fieldSet) throws BindException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Transacao transacao = new Transacao();
		transacao.setNome(fieldSet.readString("username"));
		transacao.setUserId(fieldSet.readInt("user_id"));		
		String dateString = fieldSet.readString("transaction_date");
		transacao.setValor(fieldSet.readDouble("transaction_amount"));
	
		try {
			transacao.setDataTransacao(dateFormat.parse(dateString));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return transacao;
	}

}
