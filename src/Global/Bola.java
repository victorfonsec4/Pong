package Global;

import java.awt.Image;
import java.awt.Rectangle;


public class Bola 
{
	public Imagem imagem;
	public Rectangle rect;
	private double x;
	private double y;
	public double vx;
	public double vy;

	public Bola(int x, int y, Image image)
	{
		this.x = x;
		this.y = y;
		vx = 2;
		vy = 2;
		imagem = new Imagem(x, y, image);
		rect = new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
	}

	public void update()
	{
		if(Math.abs(vx)>12)
			vx=7.5*Math.signum(vx);
		x += vx;
		y += vy;
		imagem.X = (int) x;
		imagem.Y = (int) y;
		rect.x = (int) x;
		rect.y = (int) y;
	}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public void setX(double x)
	{
		this.x = x;
		imagem.X = (int) x;
		rect.x = (int) x;
	}
	public void setY(double y)
	{
		this.y = y;
		imagem.Y = (int) y;
		rect.y = (int) y;
	}
}
