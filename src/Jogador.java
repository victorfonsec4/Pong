import java.awt.Image;
import java.awt.Rectangle;


public class Jogador 
{
	private int pontos;
	public Imagem imagem;
	public Rectangle rect;
	private int x;
	private int y;
	private int dy = 0;

	public Jogador(int x, int y, Image image)
	{
		this.pontos = 0;

		this.x = x;
		this.y = y;
		
		this.imagem = new Imagem(x, y, image);
		rect = new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
		imagem.Y = y;
	}

	public void update()
	{
		this.setY(y + dy);
		this.rect.y = y;
	}
	
	public void subir()
	{
		dy = -5;
	}

	public void descer()
	{
		dy = 5;
	}

	public void parar()
	{
		dy = 0;
	}

}
