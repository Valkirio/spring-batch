package br.com.kirio.transacoes.service.transacoes;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("restriction")
@XmlRootElement(name = "transactionRecord")
public class Transacao {

	private String nome;
    private int userId;
    private Date dataTransacao;
    private double valor;
        
	public String getNome() {
		return nome;
	}
	public int getUserId() {
		return userId;
	}
	public Date getDataTransacao() {
		return dataTransacao;
	}
	public double getValor() {
		return valor;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setDataTransacao(Date dataTransacao) {
		this.dataTransacao = dataTransacao;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	@Override
	public String toString() {
		return "Transacao [nome=" + nome + ", userId=" + userId + ", dataTransacao=" + dataTransacao + ", valor="
				+ valor + "]";
	}
}
