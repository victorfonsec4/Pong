package Main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;


public class PanelMenu extends JPanel
{
	private int opcao;
	Font fonte;
	public PanelMenu()
	{
		opcao = 0;
		this.setBackground(Color.BLACK);

		fonte = new Font("Score", Font.PLAIN, 50);
	}
	
	public void incOpcao()
	{
		opcao++;
		opcao %= 4;
	}
	
	public void decOpcao()
	{
		opcao--;
		if(opcao == -1)
			opcao = 3;
	}

	public int getOpcao()
	{
		return opcao;
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		g.setFont(fonte);

		if(opcao == 0)
			g.setColor(Color.RED);
		else
			g.setColor(Color.WHITE);
		g.drawString("Single Player", this.size().width/2 - this.size().width/5, this.size().height*1/3);

		if(opcao == 1)
			g.setColor(Color.RED);
		else
			g.setColor(Color.WHITE);
		g.drawString("MultiplayerHost", this.size().width/2 - this.size().width/5, this.size().height*1/3 + this.size().height*1/7);

		if(opcao == 2)
			g.setColor(Color.RED);
		else
			g.setColor(Color.WHITE);
		g.drawString("MultiplayerClient", this.size().width/2 - this.size().width/5, this.size().height*1/3 + this.size().height*2/7);
		
		if(opcao == 3)
			g.setColor(Color.RED);
		else
			g.setColor(Color.WHITE);
		g.drawString("Exit", this.size().width/2 - this.size().width/5, this.size().height*1/3 + this.size().height*3/7);
	}

}
