import java.awt.BorderLayout;
import java.awt.Color;
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

	public Grafico()
	{
		super("Pong");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);//tela cheia


		tela = new Tela(new BorderLayout());
		tela.setBackground(Color.BLACK);

		this.add(tela);

		imagensDesenhar = new ArrayList<Imagem>();
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
