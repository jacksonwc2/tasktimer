package br.edu.unoesc.tasktimer.model;

import java.util.ArrayList;

/**
 * Classe que representa uma tarefa da aplicação
 * 
 * @author Jackson Willian e Luiz Henrique
 * @since 11/05/2018
 * @version 1.0
 */
public class ModeloRelatorio {

	private String pausa;

	private String tempoGasto;

	private String descricao;

	private String dataInicio;

	private String dataFim;

	public String getPausa() {
		return pausa;
	}

	public void setPausa(String pausa) {
		this.pausa = pausa;
	}

	public String getTempoGasto() {
		return tempoGasto;
	}

	public void setTempoGasto(String tempoGasto) {
		this.tempoGasto = tempoGasto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDataFim() {
		return dataFim;
	}

	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}

	public String getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}

}
