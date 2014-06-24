import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Grafico extends JFrame
{
	List<Imagem> imagensDesenhar;
	public Grafico()
	{
		super("Pong");

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

	public void paint(Graphics g)
	{
		for(Imagem img : imagensDesenhar)
		{
			g.drawImage(img.imagem, img.X, img.Y, null);
		}
	}
}
