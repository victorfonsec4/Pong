package Global;
import java.awt.Image;
import java.awt.Rectangle;


public class Bola 
{
	public Imagem imagem;
	public Rectangle rect;
	private int x;
	private int y;
	public int vx;
	public int vy;

	public Bola(int x, int y, Image image)
	{
		this.x = x;
		this.y = y;
		vx = 3;
		vy = 3;
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

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public void setX(int x)
	{
		this.x = x;
		imagem.X = x;
		rect.x = x;
	}
	public void setY(int y)
	{
		this.y = y;
		imagem.Y = y;
		rect.y = y;
	}
}
