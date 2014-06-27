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

public class Host extends Thread
{
	private ServerSocket serversocket;
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
	

	public Host(PanelJogo grafico, PanelManager tela)
	{
		System.err.println("Host");
		System.err.println("Esperando conexão");
		try {
			serversocket=new ServerSocket(5432);
			clientsocket=serversocket.accept();
			inputstream=new ObjectInputStream(clientsocket.getInputStream());
			outputstream=new ObjectOutputStream(clientsocket.getOutputStream());
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
			comandoString=(String)inputstream.readObject();
			if(comandoString.equals("parar"))
				jogador2.parar();
			else if (comandoString.equals("subir"))
				jogador2.subir();
			else if (comandoString.equals("descer"))
				jogador2.descer();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if(jogador1.getdY()==0)
				outputstream.writeObject("parar");
			else if(jogador1.getdY()==5)
				outputstream.writeObject("descer");
			else
				outputstream.writeObject("subir");
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

		//limites do movimento do jogador1;
		if(jogador1.getY() < 0)
			jogador1.parar();
		if(jogador1.getY() + jogador1.rect.height > grafico.size().height)
			jogador1.parar();

		//sistema de pontuacao
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

	private void draw()
	{
		grafico.desenhar(jogador1.imagem);
		grafico.desenhar(jogador2.imagem);
		grafico.desenhar(bola.imagem);

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
			}
			else if(e.getKeyCode() == KeyEvent.VK_DOWN && ! (jogador1.getY() + jogador1.rect.height > grafico.size().height) )
			{
				jogador1.descer();
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
