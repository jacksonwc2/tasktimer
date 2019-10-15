package br.edu.unoesc.tasktimer.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

/**
 * Classe que representa tela de splash da aplicação,
 * primeira tela a ser carregada.
 * 
 * @author Jackson Willian e Luiz Henrique  
 * @since 11/05/2018
 * @version 1.0
 */
public class FrmSplash extends JFrame {

	private JPanel contentPane;
	private Timer timer;

	/**
	 * Launch the application.
	 * Inicializa a aplicação
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmSplash frame = new FrmSplash();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Criação da tela de splash
	 */
	public FrmSplash() {
		setUndecorated(true);
		setType(Type.POPUP);
		setEnabled(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 409, 175);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCarregando_1 = new JLabel("CARREGANDO...");
		lblCarregando_1.setBounds(0, 122, 409, 53);
		lblCarregando_1.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblCarregando_1.setForeground(UIManager.getColor("Button.background"));
		lblCarregando_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblCarregando_1.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblCarregando_1);
		
		JLabel lblCarregando = new JLabel("");
		lblCarregando.setBounds(0, 0, 409, 175);
		lblCarregando.setIcon(new ImageIcon(FrmSplash.class.getResource("/imagens/splash.gif")));
		lblCarregando.setForeground(Color.DARK_GRAY);
		lblCarregando.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCarregando.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblCarregando);

		timer = new Timer(2000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				//cria a tela princial
				FrmPrincipal frmPrincipal = new FrmPrincipal();
				frmPrincipal.setVisible(true);
				
				// interrompe o timer
				timer.stop();
				
				// fechando a splash
				dispose();

			}
		});
		timer.start();
	}

}
