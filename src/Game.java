import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Game
{
	Jogador jogador1;
	Grafico grafico;
	Bola bola;
	public Game()
	{
		grafico = new Grafico();

		grafico.setVisible(true);

		grafico.addKeyListener(new Controle());

		try
		{
			jogador1 = new Jogador(0, 300, ImageIO.read(new File("imagens/jogador.png")));
			bola = new Bola(0, 300, ImageIO.read(new File("imagens/ball.jpg")));
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
		bola.update();
	}

	private void draw()
	{
		grafico.desenhar(jogador1.imagem);
		grafico.desenhar(bola.imagem);
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
