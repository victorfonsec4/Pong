import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.EventListener;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game
{
	Jogador jogador1;
	Grafico grafico;
	Imagem fundoPreto;
	public Game()
	{
		grafico = new Grafico();

		grafico.setSize(1024, 768);
		grafico.setVisible(true);

		grafico.addKeyListener(new Controle());

		try
		{
			jogador1 = new Jogador(0, 300, ImageIO.read(new File("imagens/jogador.png")));
			fundoPreto = new Imagem(0, 0, ImageIO.read(new File("imagens/black.jpg")));
		} 
		catch(IOException e)
		{
		}
		run();
	}

	private void run()
	{
		while(true)
		{
			update();
			draw();

			try {
				Thread.sleep(10);
			} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}
	}

	private void update()
	{
		jogador1.update();
	}

	private void draw()
	{
		grafico.desenhar(fundoPreto);
		grafico.desenhar(jogador1.imagem);
		grafico.executar = true;
		grafico.repaint();
	}

	private class Controle extends KeyAdapter 
	{
		public void keyPressed(KeyEvent e)
		{
			if(e.getKeyCode() == KeyEvent.VK_UP)
				jogador1.subir();
			else if(e.getKeyCode() == KeyEvent.VK_DOWN)
				jogador1.descer();
		}

		public void keyReleased(KeyEvent e)
		{
			jogador1.parar();
		}
	}

}
