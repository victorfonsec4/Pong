package Global;
import java.awt.Image;
import java.awt.image.BufferedImage;


public class Imagem 
{
	public int X;
	public int Y;
	public BufferedImage imagem;
	
	public Imagem(int X, int Y, Image imagem)
	{
		this.X = X;
		this.Y = Y;
		this.imagem = (BufferedImage) imagem;
	}
	
	public Imagem Clone()
	{
		return new Imagem(X, Y, imagem);
	}
}
