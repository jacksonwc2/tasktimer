package br.edu.unoesc.tasktimer.model;

import java.util.ArrayList;

/**
 * Classe que representa uma tarefa da aplicação
 * 
 * @author Jackson Willian e Luiz Henrique
 * @since 11/05/2018
 * @version 1.0
 */
public class Tarefa extends Atividade {

	private ArrayList<Pausa> pausas;

	public ArrayList<Pausa> getPausas() {
		return pausas;
	}

	public void setPausas(ArrayList<Pausa> pausas) {
		this.pausas = pausas;
	}

}
