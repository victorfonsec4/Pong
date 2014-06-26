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

public class Client
{
	private Socket clientsocket;
	private ObjectInputStream inputstream;
	private ObjectOutputStream outputstream;
	private Info info;
	Jogador jogador1;
	Jogador jogador2;
	PanelJogo grafico;
	Bola bola;

	public Client()
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
		
		info=new Info();
		
		grafico = new PanelJogo();

		grafico.setVisible(true);

		grafico.addKeyListener(new Controle());

		try
		{
			jogador1 = new Jogador(0, 300, ImageIO.read(new File("imagens/jogador.png")));
			jogador2 = new Jogador(grafico.size().width - jogador1.rect.width, 300, ImageIO.read(new File("imagens/jogador.png")));
			bola = new Bola(100, 300, ImageIO.read(new File("imagens/ball.jpg")));
		} 
		catch(IOException e)
		{
		}
		System.err.println("Inicializado");
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
			info=(Info)inputstream.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(info.dy1==0)
			jogador1.parar();
		else if(info.dy1==5)
			jogador1.descer();
		else if(info.dy1==-5)
			jogador1.subir();
		bola.vx=info.bolavx;
		bola.vy=info.bolavy;
		System.err.println("Falou");
		jogador1.update();
		jogador2.update();
		bola.update();


		//limites do movimento do jogador2;
		if(jogador2.getY() < 0)
			jogador2.parar();
		if(jogador2.getY() + jogador2.rect.height > grafico.size().height)
			jogador2.parar();

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

	private void draw()
	{
		grafico.desenhar(jogador1.imagem);
		grafico.desenhar(jogador2.imagem);
		grafico.desenhar(bola.imagem);

		grafico.score(jogador1.pontos, jogador2.pontos);

		grafico.executar = true;
		grafico.repaint();
	}

	private class Controle extends KeyAdapter 
	{
		public void keyPressed(KeyEvent e)
		{
			if(e.getKeyCode() == KeyEvent.VK_UP && !(jogador2.getY() < 0))
				jogador2.subir();
			else if(e.getKeyCode() == KeyEvent.VK_DOWN && ! (jogador2.getY() + jogador2.rect.height > grafico.size().height) )
				jogador2.descer();
		}

		public void keyReleased(KeyEvent e)
		{
			jogador2.parar();
		}
	}
	public static void main(String args[])
	{
		Client client=new Client();
	}


}
