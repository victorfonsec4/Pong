import java.awt.Graphics;
import javax.swing.JFrame;

public class Grafico extends JFrame
{
	public Grafico()
	{
		super("Pong");
	}

	public void paint(Graphics g)
	{
		g.drawLine(10, 10, 150, 150);
	}
}
