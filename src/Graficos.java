import java.awt.Graphics;
import javax.swing.JFrame;

public class Graficos extends JFrame
{
	public Graficos()
	{
		super("Pong");
	}

	public void paint(Graphics g)
	{
		g.drawLine(10, 10, 150, 150);
	}
}
