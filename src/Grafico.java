import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Grafico extends JFrame
{
	public Tela tela;
	List<Imagem> imagensDesenhar;
	public boolean executar = false;

	public Grafico()
	{
		super("Pong");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(1024, 768);


		tela = new Tela(new BorderLayout());
		tela.setSize(1024, 768);
		tela.setBackground(Color.BLACK);

		this.getContentPane().add(tela);

		this.pack();

		imagensDesenhar = new ArrayList<Imagem>();
		Imagem fundoPreto;

		try
		{
			fundoPreto = new Imagem(0, 0, ImageIO.read(new File("imagens/black.jpg")));
			imagensDesenhar.add(fundoPreto);
		} 
		catch(IOException e)
		{
			System.out.println("failed to read imagens/black.jpg");
		}

	}

	public void desenhar(Imagem img)
	{
		imagensDesenhar.add(img);
	}

	private class Tela extends JPanel
	{
		public Tela(BorderLayout b)
		{
			super(b);
		}
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);

			if(executar)
			{
				for(int i = 0; i < imagensDesenhar.size(); i++)
				{
					Imagem img = imagensDesenhar.get(i);
					g.drawImage(img.imagem, img.X, img.Y, this);
				}
				imagensDesenhar.clear();
				executar = false;
			}
		}
	}
}
