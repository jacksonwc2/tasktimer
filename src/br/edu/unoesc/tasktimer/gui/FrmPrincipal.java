package br.edu.unoesc.tasktimer.gui;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemColor;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import br.edu.unoesc.tasktimer.model.Pausa;
import br.edu.unoesc.tasktimer.model.Tarefa;
import br.edu.unoesc.tasktimer.util.Relatorio;
import br.edu.unoesc.tasktimer.util.Util;

/**
 * Classe que representa a tela principal da aplicação. Nesta tela fica todo o
 * controle das tarefas e funcionalidades com o usuário.
 * 
 * @author Jackson Willian e Luiz Henrique
 * @since 11/05/2018
 * @version 1.0
 */
public class FrmPrincipal extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;

	// atributos globais utilizados durante a execução da aplicação
	private JPanel panelPrincipal;
	private JLabel lblLogo;
	private JLabel lblMenu;
	private JLabel lblEsconderMenu;
	private JLabel lblStop;
	private JLabel lblGerarRelatrio;
	private JLabel lblNovaTarefa;
	private Thread iniciou;
	private JLabel lblTempo;
	private Boolean start = Boolean.FALSE;
	private Boolean startPausa = Boolean.FALSE;
	private Tarefa tarefaAtual;
	private ArrayList<Tarefa> tarefas;
	private Pausa atividadePausa;
	private int segundosCorrente;
	private int segundosPausa;
	private TrayIcon trayIcon;

	// Consctantes utilizadas na classe
	private static final String HORA_PADRAO = "00:00:00";
	private JLabel lblSobre;

	/**
	 * Criação da tela principal, atribuindo um menu e atalhos para o usuário. A
	 * aplicação trabalha em background, ou seja possui a implementação de um
	 * SystemTray
	 */
	public FrmPrincipal() {

		// configurando o app para utilizar SystemTray
		setType(Type.POPUP);
		FrmPrincipal frmPrincipal = this;

		if (!SystemTray.isSupported()) {
			System.exit(0);
		}

		trayIcon = new TrayIcon(createIcon("/imagens/icon.png", "TaskTimer"));

		final SystemTray tray = SystemTray.getSystemTray();
		final PopupMenu menu = new PopupMenu();

		MenuItem abrir = new MenuItem("Abrir");
		MenuItem sair = new MenuItem("Fechar");

		abrir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				frmPrincipal.show();

			}
		});

		sair.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if (JOptionPane.showConfirmDialog(null, "Deseja realmente sair? Seus dados não serão salvos automaticamente!") == 0) {
					System.exit(0);
				}
			}
		});

		menu.add(abrir);
		menu.addSeparator();
		menu.add(sair);

		trayIcon.setPopupMenu(menu);

		try {
			tray.add(trayIcon);
		} catch (AWTException e1) {
			e1.printStackTrace();
		}

		// inicializando configurações da tela/dados de tarefas.
		tarefas = new ArrayList<Tarefa>();

		setIconImage(Toolkit.getDefaultToolkit().getImage(FrmPrincipal.class.getResource("/imagens/icon.png")));
		setResizable(false);
		setBackground(new Color(51, 51, 51));
		setBounds(100, 100, 414, 193);

		panelPrincipal = new JPanel();
		panelPrincipal.setLocation(-49, 0);
		panelPrincipal.setBackground(new Color(153, 153, 153));
		panelPrincipal.setOpaque(false);
		panelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelPrincipal);
		panelPrincipal.setLayout(null);

		// criação do menu lateral da aplicação
		JPanel panelMenuLateral = new JPanel();
		panelMenuLateral.setVisible(false);
		panelMenuLateral.setBackground(new Color(211, 211, 211));
		panelMenuLateral.setBounds(0, 40, 401, 114);
		panelPrincipal.add(panelMenuLateral);
		panelMenuLateral.setLayout(null);

		// separador da toolbar com restante da tela
		JPanel panelSeparator = new JPanel();
		panelSeparator.setBackground(new Color(255, 140, 0));
		panelSeparator.setBounds(0, 0, 403, 4);
		panelMenuLateral.add(panelSeparator);

		// config para atalho finalizar
		JLabel lblFinalaizar = new JLabel("");
		lblFinalaizar.setEnabled(false);
		lblFinalaizar.setOpaque(true);
		lblFinalaizar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblFinalaizar.setBackground(new Color(0, 153, 255));
		lblFinalaizar.setHorizontalAlignment(SwingConstants.CENTER);
		lblFinalaizar.setIcon(new ImageIcon(FrmPrincipal.class.getResource("/imagens/finish.png")));
		lblFinalaizar.setBounds(361, 0, 30, 40);
		lblFinalaizar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				frmPrincipal.mouseEntredAtalho(lblFinalaizar);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				frmPrincipal.mouseExitedAtalho(lblFinalaizar);
			}

			@Override
			public void mouseClicked(MouseEvent e) {

				if (!lblFinalaizar.isEnabled()) {
					return;
				}

				if (JOptionPane.showConfirmDialog(null, "Deseja realmente finalizar?") == 0) {
					lblTempo.setText(HORA_PADRAO);
					start = Boolean.FALSE;
					lblFinalaizar.setEnabled(Boolean.FALSE);
					lblGerarRelatrio.setEnabled(Boolean.TRUE);
					lblNovaTarefa.setEnabled(Boolean.TRUE);
					lblStop.setEnabled(Boolean.FALSE);
					tarefaAtual.setDataFim(new Date());
					tarefaAtual.setSegundos(segundosCorrente);

					tarefas.add(tarefaAtual);
				}
			}
		});

		// config para atalho start
		JLabel lblStart = new JLabel("");
		lblStart.setEnabled(false);
		lblStart.setOpaque(true);
		lblStart.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblStart.setHorizontalAlignment(SwingConstants.CENTER);
		lblStart.setBackground(new Color(0, 153, 255));
		lblStart.setIcon(new ImageIcon(FrmPrincipal.class.getResource("/imagens/start.png")));
		lblStart.setBounds(301, 0, 30, 40);
		lblStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				frmPrincipal.mouseEntredAtalho(lblStart);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				frmPrincipal.mouseExitedAtalho(lblStart);
			}

			@Override
			public void mouseClicked(MouseEvent e) {

				if (!lblStart.isEnabled()) {
					return;
				}

				lblStart.setEnabled(Boolean.FALSE);
				lblStop.setEnabled(Boolean.TRUE);
				lblFinalaizar.setEnabled(Boolean.TRUE);

				atividadePausa.setDataFim(new Date());
				atividadePausa.setSegundos(segundosPausa);

				ArrayList<Pausa> pausas = tarefaAtual.getPausas();

				if (pausas == null) {
					pausas = new ArrayList<Pausa>();
				}

				pausas.add(atividadePausa);

				tarefaAtual.setPausas(pausas);

				start = Boolean.TRUE;
				startPausa = Boolean.FALSE;
			}
		});

		// config para o atalho stop
		lblStop = new JLabel("");
		lblStop.setEnabled(false);
		lblStop.setOpaque(true);
		lblStop.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblStop.setBackground(new Color(0, 153, 255));
		lblStop.setHorizontalAlignment(SwingConstants.CENTER);
		lblStop.setIcon(new ImageIcon(FrmPrincipal.class.getResource("/imagens/pause.png")));
		lblStop.setBounds(331, 0, 30, 40);
		lblStop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				frmPrincipal.mouseEntredAtalho(lblStop);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				frmPrincipal.mouseExitedAtalho(lblStop);
			}

			@Override
			public void mouseClicked(MouseEvent e) {

				if (!lblStop.isEnabled()) {
					return;
				}

				String mtPausa = JOptionPane.showInputDialog("Motivo da pausa:");

				if (mtPausa == null) {
					return;
				} else if (mtPausa.trim() == "") {
					JOptionPane.showMessageDialog(null, "Você precisa inserir o motivo da pausa!", "Aviso",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				lblStart.setEnabled(Boolean.TRUE);
				lblStop.setEnabled(Boolean.FALSE);
				lblFinalaizar.setEnabled(Boolean.FALSE);

				segundosPausa = 0;
				atividadePausa = new Pausa();
				atividadePausa.setDataInicio(new Date());
				;
				atividadePausa.setDescricao(mtPausa);

				start = Boolean.FALSE;
				startPausa = Boolean.TRUE;
			}
		});

		// config para o label que contem o cronometro do tempo da tarefa corrente
		lblTempo = new JLabel(HORA_PADRAO);
		lblTempo.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		lblTempo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTempo.setForeground(new Color(105, 105, 105));
		lblTempo.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 72));
		lblTempo.setBounds(0, 40, 401, 114);
		panelPrincipal.add(lblTempo);

		// config para a opção nova tarefa do menu lateral
		lblNovaTarefa = new JLabel("NOVA TAREFA");
		lblNovaTarefa.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNovaTarefa.setForeground(SystemColor.windowBorder);
		lblNovaTarefa.setFont(new Font("Calibri", Font.BOLD, 12));
		lblNovaTarefa.setBounds(10, 15, 76, 14);
		panelMenuLateral.add(lblNovaTarefa);
		lblNovaTarefa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				frmPrincipal.mouseEntredMenu(lblNovaTarefa);

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				frmPrincipal.mouseExitedMenu(lblNovaTarefa);
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {

				if (!lblNovaTarefa.isEnabled()) {
					return;
				}

				segundosCorrente = 0;

				String nvTarefa = JOptionPane.showInputDialog("Descrição Tarefa:");

				if (nvTarefa == null) {
					return;
				} else if (nvTarefa.trim() == "") {
					JOptionPane.showMessageDialog(null, "Você precisa inserir a descrição da tarefa! ", "Aviso",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				tarefaAtual = new Tarefa();
				tarefaAtual.setDataInicio(new Date());
				tarefaAtual.setDescricao(nvTarefa);

				lblStop.setEnabled(Boolean.TRUE);
				lblFinalaizar.setEnabled(Boolean.TRUE);

				panelMenuLateral.setVisible(false);
				lblEsconderMenu.setVisible(false);
				lblMenu.setVisible(true);

				lblGerarRelatrio.setEnabled(Boolean.FALSE);
				lblNovaTarefa.setEnabled(Boolean.FALSE);

				start = Boolean.TRUE;
			}
		});

		// config para opção gerar relatorio do menu lateral
		lblGerarRelatrio = new JLabel("GERAR RELAT\u00D3RIO");
		lblGerarRelatrio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblGerarRelatrio.setForeground(SystemColor.windowBorder);
		lblGerarRelatrio.setFont(new Font("Calibri", Font.BOLD, 12));
		lblGerarRelatrio.setBounds(10, 40, 101, 14);
		lblGerarRelatrio.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				frmPrincipal.mouseEntredMenu(lblGerarRelatrio);

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				frmPrincipal.mouseExitedMenu(lblGerarRelatrio);
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {

				Relatorio relatorio = new Relatorio();

				if (!lblGerarRelatrio.isEnabled()) {
					return;
				}

				try {
					relatorio.imprimir(tarefas);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		panelMenuLateral.add(lblGerarRelatrio);

		// config para opção sair do menu lateral
		JLabel lblSair = new JLabel("SAIR");
		lblSair.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblSair.setForeground(SystemColor.windowBorder);
		lblSair.setFont(new Font("Calibri", Font.BOLD, 12));
		lblSair.setBounds(10, 89, 31, 14);
		lblSair.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				frmPrincipal.mouseEntredMenu(lblSair);

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				frmPrincipal.mouseExitedMenu(lblSair);
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				frmPrincipal.hide();
			}
		});
		panelMenuLateral.add(lblSair);

		// background para o menu
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(FrmPrincipal.class.getResource("/imagens/relogio.png")));
		label.setBounds(213, 15, 178, 88);
		panelMenuLateral.add(label);
		
		FrmSobre sobre = new FrmSobre();
		
		//abre a tela de sobre
		lblSobre = new JLabel("SOBRE");
		lblSobre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblSobre.addMouseListener(new MouseAdapter() {	
			@Override
			public void mouseClicked(MouseEvent arg0) {
				sobre.setVisible(true);;
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				frmPrincipal.mouseEntredMenu(lblSobre);

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				frmPrincipal.mouseExitedMenu(lblSobre);
			}

		});
		lblSobre.setForeground(SystemColor.windowBorder);
		lblSobre.setFont(new Font("Calibri", Font.BOLD, 12));
		lblSobre.setBounds(10, 65, 46, 14);
		panelMenuLateral.add(lblSobre);

		// config da toolbar principal
		JPanel toolbarMenu = new JPanel();
		toolbarMenu.setBackground(new Color(0, 153, 255));
		toolbarMenu.setBounds(0, 0, 401, 40);
		panelPrincipal.add(toolbarMenu);
		toolbarMenu.setLayout(null);

		// logo do aplicativo
		lblLogo = new JLabel("");
		lblLogo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblLogo.setIcon(new ImageIcon(FrmPrincipal.class.getResource("/imagens/tasktimer.png")));
		lblLogo.setBounds(47, 0, 89, 40);
		toolbarMenu.add(lblLogo);

		// adiciona os atalhos na toolbar
		toolbarMenu.add(lblStart);
		toolbarMenu.add(lblStop);
		toolbarMenu.add(lblFinalaizar);

		// config opção abrir menu
		lblMenu = new JLabel("");
		lblMenu.setOpaque(true);
		lblMenu.setBackground(SystemColor.textHighlight);
		lblMenu.setBounds(0, 0, 40, 40);
		lblMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblMenu.setIcon(new ImageIcon(FrmPrincipal.class.getResource("/imagens/menuicon.png")));
		lblMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panelMenuLateral.setVisible(true);
				lblEsconderMenu.setVisible(true);
				lblMenu.setVisible(false);
			}

		});

		toolbarMenu.add(lblMenu);

		// config opção fechar menu
		lblEsconderMenu = new JLabel("x");
		lblEsconderMenu.setVisible(false);
		lblEsconderMenu.setOpaque(true);
		lblEsconderMenu.setBackground(SystemColor.textHighlight);
		lblEsconderMenu.setBounds(0, 0, 40, 40);
		lblEsconderMenu.setForeground(SystemColor.inactiveCaptionBorder);
		lblEsconderMenu.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblEsconderMenu.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		lblEsconderMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblEsconderMenu.setHorizontalTextPosition(SwingConstants.CENTER);
		lblEsconderMenu.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblEsconderMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panelMenuLateral.setVisible(false);
				lblEsconderMenu.setVisible(false);
				lblMenu.setVisible(true);
			}
		});

		toolbarMenu.add(lblEsconderMenu);

		// cria uma thread para rodar a aplicação
		if (iniciou == null) {
			iniciou = new Thread(this);
			iniciou.start();
		}

	}

	/**
	 * Efeito para quando o mouse ficar sobre os icones de atalho
	 * 
	 * @param jLabel
	 */
	private void mouseEntredAtalho(JLabel lbl) {
		lbl.setBackground(SystemColor.textHighlight);
	}

	/**
	 * Efeito para quando o mouse sair sobre os icones de atalho
	 * 
	 * @param jLabel
	 */
	private void mouseExitedAtalho(JLabel lbl) {
		lbl.setBackground(new Color(0, 153, 255));
	}

	/**
	 * Efeito para quando o mouse ficar sobre as opções do menu
	 * 
	 * @param jLabel
	 */
	private void mouseEntredMenu(JLabel lbl) {
		lbl.setBackground(Color.DARK_GRAY);
	}

	/**
	 * Efeito para quando o mouse sair sobre as opções do menu
	 * 
	 * @param jLabel
	 */
	private void mouseExitedMenu(JLabel lbl) {
		lbl.setBackground(SystemColor.windowBorder);
	}

	/**
	 * Método que cria o icone para TrayIcon
	 * 
	 * @param path,desc
	 */
	private Image createIcon(String path, String desc) {
		return new ImageIcon(FrmPrincipal.class.getResource(path), desc).getImage();
	}

	/**
	 * Controle da thread e segundos executados na aplicação. Nesse método é
	 * contabilizado os periodos das tarefas e das pausas.
	 */
	@Override
	public void run() {

		while (true) {

			if (this.start) {
				segundosCorrente++;
				lblTempo.setText(Util.getInstance().formatarTempoGasto(segundosCorrente));
			} else if (this.startPausa) {
				segundosPausa++;
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
