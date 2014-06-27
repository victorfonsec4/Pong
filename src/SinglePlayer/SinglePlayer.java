package SinglePlayer;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.lang.Math;

import Global.Bola;
import Global.Jogador;
import Global.PanelJogo;
import Main.PanelManager;

public class SinglePlayer extends Thread
{
	Jogador jogador1;
	Jogador jogador2;
	PanelJogo grafico;
	PanelManager tela;
	Bola bola;
	boolean subindo, descendo;
	boolean terminar;
	boolean apertouUP = false;
	boolean apertouDOWN = false;
	boolean boost = false;

	public SinglePlayer(PanelJogo grafico, PanelManager tela)
	{
		this.grafico = grafico;
		this.tela = tela;

		subindo = false;
		descendo = false;
		terminar = false;

		tela.addKeyListener(new Controle());
		try
		{
			jogador1 = new Jogador(0, 300, ImageIO.read(new File("imagens/jogador.png")));
			jogador2 = new Jogador(grafico.getWidth() - jogador1.rect.width, 300, ImageIO.read(new File("imagens/jogador.png")));
			bola = new Bola(100, 300, ImageIO.read(new File("imagens/bola.png")));
		} 
		catch(IOException e)
		{
		}
	}

	public void run()
	{
		while(!terminar)
		{
			update();
			draw();
			
			try {
				Thread.sleep(5);
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
		if(bola.getX() + bola.rect.width > grafico.getWidth() || bola.getX() < 0)
			bola.vx *= -1;
		if(bola.getY() + bola.rect.height > grafico.getHeight() || bola.getY() < 0)
			bola.vy *= -1;

		//logica de colisao da bola com os jogadores
		if(bola.rect.intersects(jogador1.rect))
		{
			bola.vx *= -1.1;
			if (boost == true)
				bola.vx *= 1.1;
			bola.vy = -(jogador1.getY() + jogador1.rect.getHeight()/2 - bola.getY()-bola.rect.getHeight()/2)/30;
		}
		if(bola.rect.intersects(jogador2.rect))
		{
			bola.vx *= -1.1;
			bola.vy = -(jogador2.getY() + jogador2.rect.getHeight()/2 - bola.getY()-bola.rect.getHeight()/2)/30;
		}

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
		if(jogador1.getY() + jogador1.rect.height > grafico.getHeight())
			jogador1.parar();

		//sistema de pontuacao
		if(bola.getX() < jogador1.getX() && !bola.rect.intersects(jogador1.rect))
		{
			bola.setX(grafico.getWidth()/2);
			bola.setY(grafico.getHeight()/2);
			bola.vx=2;
			bola.vy=2;
			jogador2.pontos++;
		}
		if(bola.getX() + bola.rect.width > jogador2.getX() + jogador2.rect.getWidth() && !bola.rect.intersects(jogador2.rect))
		{
			bola.setX(grafico.getWidth()/2);
			bola.setY(grafico.getHeight()/2);
			bola.vx=2;
			bola.vy=2;
			jogador1.pontos++;
		}
	}

	private void draw()
	{
		grafico.desenhar(jogador1.imagem);
		grafico.desenhar(jogador2.imagem);
		grafico.desenharBola(bola.imagem.Clone());
		grafico.score(jogador1.pontos, jogador2.pontos);
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
				subindo = true;
				apertouUP = true;
			}
			else if(e.getKeyCode() == KeyEvent.VK_DOWN && ! (jogador1.getY() + jogador1.rect.height > grafico.getHeight()) )
			{
				jogador1.descer();
				descendo = true;
				apertouDOWN = true;
			}
			else if(e.getKeyCode() == KeyEvent.VK_SPACE)
			{
				boost = true;
			}
			else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			{
				tela.getContentPane().remove(grafico);
				tela.add(tela.menuPrincipal);
				tela.getContentPane().invalidate();
				tela.getContentPane().validate();
				tela.removeKeyListener(this);
				tela.addKeyListener(tela.controleMenu);
				tela.repaint();
				terminar = true;
			}
		}
	
		public void keyReleased(KeyEvent e)
		{
			apertouDOWN = false;
			apertouUP = false;
			boost = false;
			if(e.getKeyCode() == KeyEvent.VK_UP) 
			{
				subindo = false;
				if(!descendo)
					jogador1.parar();
			}
			else if(e.getKeyCode() == KeyEvent.VK_DOWN )
			{
				descendo = false;
				if(!subindo)
					jogador1.parar();
			}
		}
	}
}
