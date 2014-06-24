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
	List<Imagem> imagensDesenhar;
	public boolean executar = false;
	JPanel tela;
	public Grafico()
	{
		super("Pong");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());

		imagensDesenhar = new ArrayList<Imagem>();
		Imagem fundoPreto;

		tela = new JPanel(new BorderLayout());
		tela.setSize(300, 300);
		tela.setBackground(Color.BLUE);

		this.add(tela, BorderLayout.CENTER);


		try
		{
			fundoPreto = new Imagem(0, 0, ImageIO.read(new File("imagens/black.jpg")));
			//imagensDesenhar.add(fundoPreto);
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

	public void paint(Graphics g)
	{
		if(executar)
		{
			for(int i = 0; i < imagensDesenhar.size(); i++)
			{
				Imagem img = imagensDesenhar.get(i);
				g.drawImage(img.imagem, img.X, img.Y, null);
			}
			imagensDesenhar.clear();
			executar = false;
		}
	}
}
