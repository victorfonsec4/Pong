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
	private Double posOutro=(double) 300;
	private Double xbola=(double) 100;
	private Double ybola=(double) 300;
	private Integer j1p=0;
	private Integer j2p=0;
	Jogador jogador1;
	Jogador jogador2;
	PanelJogo grafico;
	PanelManager tela;
	Bola bola;
	boolean subindo, descendo;
	boolean terminar,ready;
	boolean apertouUP = false;
	boolean apertouDOWN = false;
	boolean boost = false;

	public Client(PanelJogo grafico, PanelManager tela,String ipString)
	{
		System.err.println("Client");
		try {
			clientsocket=new Socket(ipString,5432);
			outputstream=new ObjectOutputStream(clientsocket.getOutputStream());
			inputstream=new ObjectInputStream(clientsocket.getInputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.err.println("Conectado");

		this.grafico = grafico;
		this.tela = tela;
		
		subindo = false;
		descendo = false;
		terminar = false;
		ready=false;

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
	}

	public void run()
	{
		(new Send()).start();
		(new Listen()).start();
		try {
			outputstream.writeObject("podago?");
			inputstream.readObject();
			ready=true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
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
		jogador1.setY(posOutro);
		jogador2.update();
		bola.setX(xbola);
		bola.setY(ybola);
		jogador1.pontos=j1p;
		jogador2.pontos=j2p;
		
		//limites do movimento do jogador2;
		if(jogador2.getY() < 0)
			jogador2.parar();
		if(jogador2.getY() + jogador2.rect.height > grafico.size().height)
			jogador2.parar();
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
			if(e.getKeyCode() == KeyEvent.VK_UP && !(jogador2.getY() < 0))
			{
				jogador2.subir();
				subindo = true;
				apertouUP=true;
			}
			else if(e.getKeyCode() == KeyEvent.VK_DOWN && ! (jogador2.getY() + jogador2.rect.height > grafico.size().height) )
			{
				jogador2.descer();
				descendo = true;
				apertouDOWN=true;
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
	
	private class Send extends Thread
	{
		public Send() {
		}
		public void run()
		{
			while(!ready){
				try {
					this.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			while(true)
			{
				try {
					outputstream.writeObject(jogador2.getY());
					outputstream.writeObject(apertouUP);
					outputstream.writeObject(apertouDOWN);
					outputstream.writeObject(boost);
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					this.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private class Listen extends Thread
	{
		public Listen() {
		}
		public void run()
		{
			while(!ready){
				try {
					this.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			while(true)
			{
				try {
					posOutro=(Double)inputstream.readObject();
					xbola=(Double)inputstream.readObject();
					ybola=(Double)inputstream.readObject();
					j1p=(Integer)inputstream.readObject();
					j2p=(Integer)inputstream.readObject();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
