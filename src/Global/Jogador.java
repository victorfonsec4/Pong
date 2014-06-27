package Global;
import java.awt.Image;
import java.awt.Rectangle;


public class Jogador 
{
	public int pontos;
	public Imagem imagem;
	public Rectangle rect;
	private double x;
	private double y;
	private double dy = 0;

	public Jogador(int x, int y, Image image)
	{
		this.pontos = 0;

		this.x = x;
		this.y = y;
		
		this.imagem = new Imagem(x, y, image);
		rect = new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
	}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}
	
	public double getdY()
	{
		return dy;
	}

	public void setY(double y)
	{
		this.y = y;
		this.imagem.Y = (int) y;
		this.rect.y = (int) y;
	}

	public void update()
	{
		this.setY(y + dy);
		this.rect.y = (int) y;
	}
	
	public void subir()
	{
		dy = -3;
	}

	public void descer()
	{
		dy = 3;
	}

	public void parar()
	{
		dy = 0;
	}

}
