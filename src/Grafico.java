import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Grafico extends JFrame
{
	Image fundoPreto;
	public Grafico()
	{
		super("Pong");
		try
		{
			fundoPreto = ImageIO.read(new File("imagens/black.jpg"));
		} 
		catch(IOException e)
		{
			System.out.println("failed to read imagens/black.jpg");
		}
	}

	public void paint(Graphics g)
	{
		g.drawImage(fundoPreto, 0, 0, null);
	}
}
