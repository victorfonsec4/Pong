import java.awt.Image;
import java.awt.Rectangle;


public class Bola 
{
	public Imagem imagem;
	public Rectangle rect;
	public int x;
	public int y;
	public int vx;
	public int vy;

	public Bola(int x, int y, Image image)
	{
		this.x = x;
		this.y = y;
		vx = 1;
		vy = 1;
		imagem = new Imagem(x, y, image);
		rect = new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
	}

	public void update()
	{
		x += vx;
		y += vy;
		imagem.X = x;
		imagem.Y = y;
		rect.x = x;
		rect.y = y;
	}
}
