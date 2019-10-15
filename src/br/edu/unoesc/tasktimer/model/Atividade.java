package br.edu.unoesc.tasktimer.model;

import java.util.Date;

/**
 * Classe que representa uma atividade da aplicação
 * 
 * @author Jackson Willian e Luiz Henrique  
 * @since 11/05/2018
 * @version 1.0
 */
public abstract class Atividade {
	
	private Date dataInicio;
	private Date dataFim;
	private String descricao;
	private int segundos;
	
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getSegundos() {
		return segundos;
	}
	public void setSegundos(int segundos) {
		this.segundos = segundos;
	}
	
}
