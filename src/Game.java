import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Game 
{
	Jogador jogador1;
	Grafico grafico;
	public Game()
	{
		grafico = new Grafico();
		grafico.setSize(800, 600);
		grafico.setVisible(true);
		
		try
		{
			jogador1 = new Jogador(ImageIO.read(new File("jogador.jpg")));
		} 
		catch(IOException e)
		{
		}
		run();
	}

	private void run()
	{
		update();
	}

	private void update()
	{
	}
}
