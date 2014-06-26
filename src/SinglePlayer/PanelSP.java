package SinglePlayer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import Global.Imagem;


public class PanelSP extends JPanel
{
	List<Imagem> imagensDesenhar;
	public boolean executar = false;
	private int pontJogador1, pontJogador2;
	Font fonte;

	public PanelSP()
	{
		pontJogador1 = 0;
		pontJogador2 = 0;

		fonte = new Font("Score", Font.PLAIN, 300);

		this.setBackground(Color.BLACK);
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

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		g.setFont(fonte);
		g.setColor(Color.WHITE);
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
