import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Game
{
	Jogador jogador1;
	Jogador jogador2;
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
			jogador2 = new Jogador(grafico.size().width - 100, 300, ImageIO.read(new File("imagens/jogador.png")));
			bola = new Bola(100, 300, ImageIO.read(new File("imagens/ball.jpg")));
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
		jogador2.update();
		bola.update();

		//logica de colisao da bola com as bordas
		if(bola.x + bola.rect.width > grafico.size().width || bola.x < 0)
			bola.vx *= -1;
		if(bola.y + bola.rect.height > grafico.size().height || bola.y < 0)
			bola.vy *= -1;

		//logica de colisao da bola com os jogadores
		if(bola.rect.intersects(jogador1.rect))
			bola.vx *= -1;
		if(bola.rect.intersects(jogador2.rect))
			bola.vx *= -1;

		//"inteligencia artificial" do jogador2
		if(bola.y == jogador2.getY())
			jogador2.parar();
		if(bola.y < jogador2.getY() /*&& !(jogador2.getY() < 0)*/)
			jogador2.subir();
		if(bola.y + bola.rect.height > jogador2.getY() + jogador2.rect.height )
			jogador2.descer();

		//limites do movimento do jogador1;
		if(jogador1.getY() < 0)
			jogador1.parar();
		if(jogador1.getY() + jogador1.rect.height > grafico.size().height)
			jogador1.parar();
	}

	private void draw()
	{
		grafico.desenhar(jogador1.imagem);
		grafico.desenhar(jogador2.imagem);
		grafico.desenhar(bola.imagem);
		grafico.executar = true;
		grafico.repaint();
	}

	private class Controle extends KeyAdapter 
	{
		public void keyPressed(KeyEvent e)
		{
			if(e.getKeyCode() == KeyEvent.VK_UP && !(jogador1.getY() < 0))
				jogador1.subir();
			else if(e.getKeyCode() == KeyEvent.VK_DOWN && ! (jogador1.getY() + jogador1.rect.height > grafico.size().height) )
				jogador1.descer();
		}

		public void keyReleased(KeyEvent e)
		{
			jogador1.parar();
		}
	}

}
