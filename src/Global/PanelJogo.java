package Global;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;


public class PanelJogo extends JPanel
{
	private static final long serialVersionUID = 649474721633315529L;
	List<Imagem> imagensDesenhar;
	List<Imagem> efeitoBola;
	public boolean executar = false;
	private int pontJogador1, pontJogador2;
	Font fonte;

	public PanelJogo()
	{
		pontJogador1 = 0;
		pontJogador2 = 0;

		fonte = new Font("Score", Font.PLAIN, 300);

		this.setBackground(Color.BLACK);
		imagensDesenhar = new ArrayList<Imagem>();
		efeitoBola = new ArrayList<Imagem>();
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

	public void desenharBola(Imagem bola)
	{
		efeitoBola.add(0, bola);
		if(efeitoBola.size() > 10)
			efeitoBola.remove(efeitoBola.size() - 1);
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		g.setFont(fonte);
		g.setColor(Color.WHITE);
		g.drawString("" + pontJogador1, this.getWidth()/2 - this.getHeight()/5 - 200, this.getHeight()*1/3);
		g.drawString("" + pontJogador2, this.getWidth()/2 + 200, this.getHeight()*1/3);

		if(executar)
		{
			for(int i = imagensDesenhar.size() - 1; i >= 0; i--)
			{
				Imagem img = imagensDesenhar.get(i);
				g.drawImage(img.imagem, img.X, img.Y, this);
			}
			imagensDesenhar.clear();

			for(int i = efeitoBola.size() - 1; i >= 0; i--)
			{
				Imagem img = efeitoBola.get(i);
				g.setColor(new Color(255 - 25*i, 255 - 25*i, 255 - 25*i));
				g.fillRect(img.X, img.Y, img.imagem.getWidth(), img.imagem.getHeight());
			}

			executar = false;
		}
	}
}
