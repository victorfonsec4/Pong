import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.lang.Math;

import javax.imageio.ImageIO;

public class SinglePlayer extends Thread
{
	Jogador jogador1;
	Jogador jogador2;
	PanelSP grafico;
	PanelManager tela;
	Bola bola;
	boolean apertouUP = false;
	boolean apertouDOWN = false;
	boolean boost = false;
	boolean doge = false;

	public SinglePlayer(PanelSP grafico, PanelManager tela)
	{
		this.grafico = grafico;
		this.tela = tela;

		tela.addKeyListener(new Controle());

		try
		{
			jogador1 = new Jogador(0, 300, ImageIO.read(new File("imagens/jogador.png")));
			jogador2 = new Jogador(grafico.size().width - jogador1.rect.width, 300, ImageIO.read(new File("imagens/jogador.png")));
			bola = new Bola(100, 300, ImageIO.read(new File("imagens/ball.jpg")));
		} 
		catch(IOException e)
		{
		}
	}

	public void run()
	{
		while(true)
		{
			update();
			try {
				draw();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

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
		if(bola.getX() + bola.rect.width > grafico.size().width || bola.getX() < 0)
		{
			bola.vx *= -1;
			bola.vx = 3;
			bola.vy = 3;
		}
		if(bola.getY() + bola.rect.height > grafico.size().height || bola.getY() < 0)
			bola.vy *= -1;

		//logica de colisao da bola com os jogadores
		if(bola.rect.intersects(jogador1.rect))
		{
			bola.vx *= -1;
			bola.vx += 1;
			if (boost == true)
			{
				bola.vx += 2;
				doge = true;
			}
			if(apertouUP == true)
				bola.vy += 3;
			if(apertouDOWN == true)
				bola.vy -= 3;
			bola.vy += -(jogador1.getY() - jogador1.rect.getHeight()/2 - bola.getY())/50;
		}
		if(bola.rect.intersects(jogador2.rect))
			bola.vx *= -1;

		//"inteligencia artificial" do jogador2
		if(bola.getY() >= jogador2.getY() && bola.getY() <= jogador2.getY() + jogador2.rect.height)
			jogador2.parar();
		if(bola.getY() < jogador2.getY() && !(jogador2.getY() < 0))
			jogador2.subir();
		if(bola.getY() + bola.rect.height > jogador2.getY() + jogador2.rect.height )
			jogador2.descer();

		//limites do movimento do jogador1;
		if(jogador1.getY() < 0)
			jogador1.parar();
		if(jogador1.getY() + jogador1.rect.height > grafico.size().height)
			jogador1.parar();

		//sistema de pontuação
		if(bola.getX() < jogador1.getX())
		{
			bola.setX(grafico.size().width/2);
			bola.setY(grafico.size().height/2);
			jogador2.pontos++;
		}
		if(bola.getX() > jogador2.getX())
		{
			bola.setX(grafico.size().width/2);
			bola.setY(grafico.size().height/2);
			jogador1.pontos++;
		}
	}

	private void draw() throws IOException
	{
		grafico.desenhar(jogador1.imagem);
		grafico.desenhar(jogador2.imagem);
		grafico.desenhar(bola.imagem);

		grafico.score(jogador1.pontos, jogador2.pontos);
		
		if(doge == true)
		{
			grafico.boost(bola.getY());
			doge = false;
		}
		
		grafico.executar = true;
		grafico.repaint();

		tela.repaint();
	}

	private class Controle extends KeyAdapter 
	{
		public void keyPressed(KeyEvent e)
		{
			if(e.getKeyCode() == KeyEvent.VK_UP && !(jogador1.getY() < 0))
			{
				jogador1.subir();
				apertouUP = true;
			}
			else if(e.getKeyCode() == KeyEvent.VK_DOWN && ! (jogador1.getY() + jogador1.rect.height > grafico.size().height) )
			{
				jogador1.descer();
				apertouDOWN = true;
			}
			else if(e.getKeyCode() == KeyEvent.VK_SPACE)
			{
				boost = true;
			}
		}

		public void keyReleased(KeyEvent e)
		{
			jogador1.parar();
			apertouDOWN = false;
			apertouUP = false;
			boost = false;
		}
	}

}
