import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Game 
{
	Jogador jogador1;
	Grafico grafico;
	Imagem fundoPreto;
	public Game()
	{
		grafico = new Grafico();
		grafico.setSize(800, 600);
		grafico.setVisible(true);

		
		try
		{
			//jogador1 = new Jogador(ImageIO.read(new File("jogador.jpg")));
			fundoPreto = new Imagem(0, 0, ImageIO.read(new File("imagens/black.jpg")));
		} 
		catch(IOException e)
		{
		}
		run();
	}

	private void run()
	{
		update();
		draw();
	}

	private void update()
	{
	}

	private void draw()
	{
		grafico.desenhar(fundoPreto);
		grafico.executar = true;
		grafico.repaint();
	}
}
