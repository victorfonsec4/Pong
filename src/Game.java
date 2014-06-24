import java.awt.Graphics;

import javax.swing.JFrame;

public class Game extends JFrame
{
	public Game()
	{
		super("Pong");
		run();
	}

	private void run()
	{
		update();
	}

	private void update()
	{
	}

	public void paint(Graphics g)
	{
		g.drawLine(10, 10, 150, 150);
	}
}
