package br.edu.unoesc.tasktimer.util;

import java.util.ArrayList;

import br.edu.unoesc.tasktimer.model.Atividade;
import br.edu.unoesc.tasktimer.model.ModeloRelatorio;
import br.edu.unoesc.tasktimer.model.Pausa;
import br.edu.unoesc.tasktimer.model.Tarefa;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Classe que cria relatório final
 * 
 * @author Jackson Willian e Luiz Henrique
 * @since 27/06/2018
 * @version 1.0
 */
public class Relatorio {

	/**
	 * Método que cria uma lista de atividades e nos mostra na tela o relatorio
	 * final
	 * 
	 * @param atividades
	 */
	public void imprimir(ArrayList<Tarefa> atividades) throws JRException {

		ArrayList<ModeloRelatorio> modeloRelatorio = new ArrayList<ModeloRelatorio>();

		// Monta o objeto para imprimir relatorio
		atividades.forEach(tarefa -> {

			modeloRelatorio.add(criarRegistroRelatorio(tarefa, Boolean.FALSE));

			if (tarefa.getPausas() != null && tarefa.getPausas().size() > 0) {
				for (Pausa pausa : tarefa.getPausas()) {
					modeloRelatorio.add(criarRegistroRelatorio(pausa, Boolean.TRUE));
				}
			}

		});

		// Cria o relatorio e o exibe na tela
		JasperReport report = JasperCompileManager.compileReport("resources/Relatorio.jrxml");
		JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(modeloRelatorio));
		JasperViewer.viewReport(print, false);
	}

	private ModeloRelatorio criarRegistroRelatorio(Atividade atividade, Boolean pausa) {

		ModeloRelatorio novoItem = new ModeloRelatorio();
		novoItem.setDataFim(Util.getInstance().formatarHora(atividade.getDataFim()));
		novoItem.setDataInicio(Util.getInstance().formatarHora(atividade.getDataInicio()));
		novoItem.setDescricao(pausa ? "" : atividade.getDescricao());
		novoItem.setPausa(pausa ? atividade.getDescricao() : "");
		novoItem.setTempoGasto(Util.getInstance().formatarTempoGasto(atividade.getSegundos()));

		return novoItem;
	}
}