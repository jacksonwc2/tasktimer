package br.edu.unoesc.tasktimer.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.ComponentOrientation;
import java.awt.Toolkit;

public class FrmSobre extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public FrmSobre() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrmSobre.class.getResource("/imagens/icon.png")));
		setBounds(100, 100, 313, 257);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(0, 153, 255));
		panel.setBounds(0, 0, 401, 40);
		contentPane.add(panel);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(FrmSobre.class.getResource("/imagens/tasktimer.png")));
		label.setBounds(92, 0, 89, 40);
		panel.add(label);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 140, 0));
		panel_1.setBounds(0, 40, 403, 4);
		contentPane.add(panel_1);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(FrmSobre.class.getResource("/imagens/relogio.png")));
		label_1.setBounds(185, 40, 109, 198);
		contentPane.add(label_1);
		
		JLabel lblSobre = new JLabel("SOBRE");
		lblSobre.setForeground(SystemColor.windowBorder);
		lblSobre.setFont(new Font("Calibri", Font.BOLD, 12));
		lblSobre.setBounds(10, 63, 76, 14);
		contentPane.add(lblSobre);
		
		JLabel lblInformaes = new JLabel("ACAD\u00CAMICOS");
		lblInformaes.setForeground(SystemColor.windowBorder);
		lblInformaes.setFont(new Font("Calibri", Font.BOLD, 12));
		lblInformaes.setBounds(10, 120, 87, 16);
		contentPane.add(lblInformaes);
		
		JLabel lblTutor = new JLabel("TUTOR");
		lblTutor.setForeground(SystemColor.windowBorder);
		lblTutor.setFont(new Font("Calibri", Font.BOLD, 12));
		lblTutor.setBounds(10, 179, 87, 16);
		contentPane.add(lblTutor);
		
		JLabel lblNomeTaskTimer = new JLabel("Nome: Task Timer");
		lblNomeTaskTimer.setForeground(Color.DARK_GRAY);
		lblNomeTaskTimer.setBounds(10, 79, 284, 14);
		contentPane.add(lblNomeTaskTimer);
		
		JLabel lblVerso = new JLabel("Vers\u00E3o: 1.0");
		lblVerso.setForeground(Color.DARK_GRAY);
		lblVerso.setBounds(10, 97, 284, 14);
		contentPane.add(lblVerso);
		
		JLabel lblJacksonWillianCarbonera = new JLabel("Jackson Willian Carbonera");
		lblJacksonWillianCarbonera.setForeground(Color.DARK_GRAY);
		lblJacksonWillianCarbonera.setBounds(10, 137, 165, 14);
		contentPane.add(lblJacksonWillianCarbonera);
		
		JLabel lblLuizHenriqueSecco = new JLabel("Luiz Henrique Secco");
		lblLuizHenriqueSecco.setForeground(Color.DARK_GRAY);
		lblLuizHenriqueSecco.setBounds(10, 153, 127, 14);
		contentPane.add(lblLuizHenriqueSecco);
		
		JLabel lblRobersonJuniorFernandes = new JLabel("Roberson Junior Fernandes Alves ");
		lblRobersonJuniorFernandes.setForeground(Color.DARK_GRAY);
		lblRobersonJuniorFernandes.setBounds(10, 195, 199, 14);
		contentPane.add(lblRobersonJuniorFernandes);
	}
}
