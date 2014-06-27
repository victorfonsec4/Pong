package Multiplayer;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

import javax.imageio.ImageIO;

import Global.*;
import Main.PanelManager;

public class Client extends Thread
{
	private Socket clientsocket;
	private ObjectInputStream inputstream;
	private ObjectOutputStream outputstream;
	private String comandoString;
	Jogador jogador1;
	Jogador jogador2;
	PanelJogo grafico;
	PanelManager tela;
	Bola bola;
	boolean subindo, descendo;
	boolean terminar;

	public Client(PanelJogo grafico, PanelManager tela)
	{
		System.err.println("Client");
		try {
			clientsocket=new Socket("localhost",5432);
			outputstream=new ObjectOutputStream(clientsocket.getOutputStream());
			inputstream=new ObjectInputStream(clientsocket.getInputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.err.println("Conectado");

		this.grafico = grafico;
		this.tela = tela;
		
		tela.add(grafico);
		
		subindo = false;
		descendo = false;
		terminar = false;

		tela.addKeyListener(new Controle());

		try
		{
			jogador1 = new Jogador(0, 300, ImageIO.read(new File("imagens/jogador.png")));
			jogador2 = new Jogador(grafico.size().width - jogador1.rect.width, 300, ImageIO.read(new File("imagens/jogador.png")));
			bola = new Bola(100, 300, ImageIO.read(new File("imagens/bola.png")));
		} 
		catch(IOException e)
		{
			e.printStackTrace();
		}
		System.err.println("Inicializado");
	}

	public void run()
	{
		while(!terminar)
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
		System.err.println("Tentando falar");
		try {
			if(jogador2.getdY()==0)
				outputstream.writeObject("parar");
			else if(jogador2.getdY()==5)
				outputstream.writeObject("descer");
			else
				outputstream.writeObject("subir");
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			comandoString=(String)inputstream.readObject();
			if(comandoString.equals("parar"))
				jogador1.parar();
			else if (comandoString.equals("subir"))
				jogador1.subir();
			else if (comandoString.equals("descer"))
				jogador1.descer();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.err.println("Falou");
		jogador1.update();
		jogador2.update();
		bola.update();

		//logica de colisao da bola com as bordas
		if(bola.getX() + bola.rect.width > grafico.size().width || bola.getX() < 0)
			bola.vx *= -1;
		if(bola.getY() + bola.rect.height > grafico.size().height || bola.getY() < 0)
			bola.vy *= -1;
		
		//logica de colisao da bola com os jogadores
		if(bola.rect.intersects(jogador1.rect))
			bola.vx *= -1;
		if(bola.rect.intersects(jogador2.rect))
			bola.vx *= -1;
		
		//limites do movimento do jogador2;
		if(jogador2.getY() < 0)
			jogador2.parar();
		if(jogador2.getY() + jogador2.rect.height > grafico.size().height)
			jogador2.parar();

		//sistema de pontuacao
		if(bola.getX() < jogador1.getX())
		{
			bola.setX(grafico.getWidth()/2);
			bola.setY(grafico.getHeight()/2);
			jogador2.pontos++;
		}
		if(bola.getX() + bola.rect.width > jogador2.getX() + jogador2.rect.getWidth())
		{
			bola.setX(grafico.getWidth()/2);
			bola.setY(grafico.getHeight()/2);
			jogador1.pontos++;
		}
	}

	private void draw()
	{
		grafico.desenhar(jogador1.imagem);
		grafico.desenhar(jogador2.imagem);
		grafico.desenhar(bola.imagem.Clone());

		grafico.score(jogador1.pontos, jogador2.pontos);

		grafico.executar = true;
		grafico.repaint();
		
		tela.repaint();
	}

	private class Controle extends KeyAdapter 
	{
		public void keyPressed(KeyEvent e)
		{
			if(e.getKeyCode() == KeyEvent.VK_UP && !(jogador2.getY() < 0))
			{
				jogador2.subir();
				subindo = true;
			}
			else if(e.getKeyCode() == KeyEvent.VK_DOWN && ! (jogador2.getY() + jogador2.rect.height > grafico.size().height) )
			{
				jogador2.descer();
				descendo = true;
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
			if(e.getKeyCode() == KeyEvent.VK_UP) 
			{
				subindo = false;
				if(!descendo)
					jogador2.parar();
			}

			else if(e.getKeyCode() == KeyEvent.VK_DOWN )
			{
				descendo = false;
				if(!subindo)
					jogador2.parar();
			}
		}
	}

}
