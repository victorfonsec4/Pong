import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Grafico extends JFrame
{
	public Tela tela;
	List<Imagem> imagensDesenhar;
	public boolean executar = false;
	private int pontJogador1, pontJogador2;
	Font fonte;

	public Grafico()
	{
		super("Pong");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);//tela cheia

		pontJogador1 = 0;
		pontJogador2 = 0;

		fonte = new Font("Score", Font.PLAIN, 300);

		tela = new Tela(new BorderLayout());
		tela.setBackground(Color.BLACK);

		this.add(tela);

		imagensDesenhar = new ArrayList<Imagem>();
	}

	public void score(int p1, int p2)
	{
		pontJogador1 = p1;
		pontJogador2 = p2;
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

			g.setFont(fonte);
			g.drawString("" + pontJogador1, this.size().width/2 - this.size().width/5, this.size().height*1/3);
			g.drawString("" + pontJogador2, this.size().width/2, this.size().height*1/3);

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
