package Main;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import Global.PanelJogo;
import SinglePlayer.SinglePlayer;


public class PanelManager extends JFrame
{
	public PanelMenu menuPrincipal;
	PanelJogo telaJogoSP;
	public ControleMenu controleMenu;
	public PanelManager() 
	{
		super("Pong");


		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);//tela cheia
		this.setVisible(true);

		menuPrincipal = new PanelMenu();
		telaJogoSP = new PanelJogo();

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
				add(telaJogoSP);
				getContentPane().invalidate();
				getContentPane().validate();
				removeKeyListener(this);
				new SinglePlayer(telaJogoSP, PanelManager.this).start();
			}

			if(e.getKeyCode() == KeyEvent.VK_ENTER && menuPrincipal.getOpcao() == 1)
				;

			if(e.getKeyCode() == KeyEvent.VK_ENTER && menuPrincipal.getOpcao() == 2)
				dispose();
		}
	}
}
