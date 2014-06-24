import java.awt.Image;


public class Jogador 
{
	private int pontos;
	public Imagem imagem; 
	private int x;
	private int y;
	private int dy = 0;

	public Jogador(int x, int y, Image imagem)
	{
		this.pontos = 0;

		this.x = x;
		this.y = y;
		
		this.imagem = new Imagem(x, y, imagem);
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
