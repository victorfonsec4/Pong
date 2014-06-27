package Main;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import Global.PanelJogo;
import Multiplayer.*;
import SinglePlayer.SinglePlayer;


public class PanelManager extends JFrame
{
	private static final long serialVersionUID = 5523431677817125277L;
	public PanelMenu menuPrincipal;
	public ControleMenu controleMenu;
	public PanelJogo panelSP,panelMPHost,panelMPClient;
	public PanelManager() 
	{
		super("Pong");


		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);//tela cheia
		this.setVisible(true);

		panelSP=new PanelJogo();
		panelMPHost=new PanelJogo();
		panelMPClient=new PanelJogo();
		
		menuPrincipal = new PanelMenu();

		controleMenu = new ControleMenu();

		this.add(menuPrincipal);

		this.addKeyListener(controleMenu);
	}

	public class ControleMenu extends KeyAdapter 
	{
		public void keyPressed(KeyEvent e)
		{
			if(e.getKeyCode() == KeyEvent.VK_UP)
				menuPrincipal.decOpcao();
			else if(e.getKeyCode() == KeyEvent.VK_DOWN)
				menuPrincipal.incOpcao();

			menuPrincipal.repaint();

			if(e.getKeyCode() == KeyEvent.VK_ENTER && menuPrincipal.getOpcao() == 0)
			{
				getContentPane().remove(menuPrincipal);
				add(panelSP);
				getContentPane().invalidate();
				getContentPane().validate();
				removeKeyListener(this);
				new SinglePlayer(panelSP,PanelManager.this).start();
			}

			if(e.getKeyCode() == KeyEvent.VK_ENTER && menuPrincipal.getOpcao() == 1)
			{
				getContentPane().remove(menuPrincipal);
				add(panelMPHost);
				getContentPane().invalidate();
				getContentPane().validate();
				removeKeyListener(this);
				new Host(panelMPHost,PanelManager.this).start();
			}
			
			if(e.getKeyCode() == KeyEvent.VK_ENTER && menuPrincipal.getOpcao() == 2)
			{
				getContentPane().remove(menuPrincipal);
				add(panelMPClient);
				getContentPane().invalidate();
				getContentPane().validate();
				removeKeyListener(this);
				new Client(panelMPClient,PanelManager.this).start();
			}

			if(e.getKeyCode() == KeyEvent.VK_ENTER && menuPrincipal.getOpcao() == 3)
				dispose();
		}
	}
}
