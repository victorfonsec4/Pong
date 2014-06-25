import java.awt.Image;


public class Bola 
{
	public Imagem imagem;
	private int x;
	private int y;
	private int vx;
	private int vy;

	public Bola(int x, int y, Image image)
	{
		this.x = x;
		this.y = y;
		vx = 1;
		vy = 1;
		imagem = new Imagem(x, y, image);
	}

	public void update()
	{
		x += vx;
		y += vy;
		imagem.X = x;
		imagem.Y = y;
	}
}
