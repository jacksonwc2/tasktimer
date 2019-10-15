package br.edu.unoesc.tasktimer.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Classe que utilitaria que contem diversos metodos que podem ser reutilizados na aplicação
 * 
 * @author Jackson Willian e Luiz Henrique
 * @since 27/06/2018
 * @version 1.0
 */
public class Util {

	private static Util uniqueInstance;

	public static synchronized Util getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new Util();
		return uniqueInstance;
	}
	
	/**
	 * Método utilitaro para formatação do tempo gasto
	 * 
	 * @param int
	 * */
	public String formatarTempoGasto(int tempo) {
		int segundo = tempo % 60;
		int minutos = tempo / 60;
		int minuto = minutos % 60;
		int hora = minutos / 60;

		return String.format("%02d:%02d:%02d", hora, minuto, segundo);
	}

	/**
	 * Método utilitario para formatação da hora
	 * 
	 *  @param Date
	 * */
	public String formatarHora(Date data) {
		return new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).format(data);
	}

}
