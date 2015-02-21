package pacman.mapa;

import java.awt.Color;
import java.awt.Graphics;

import pacman.menus.PruebaMapa;

public class Pacman extends Thread {
    private int pacmanRow;
	private int pacmanCol;
	private PruebaMapa maze;
	private int livesLeft;
	private Cell[][] cells;
	
	private int x;
	private int y;

	public Pacman(int initialRow, int initialColumn, PruebaMapa startMaze, int lives)
	{
        pacmanRow = initialRow;
        pacmanCol = initialColumn;
        maze      = startMaze;
        livesLeft = lives;
        cells     = maze.getCells();
    }
	
	public void setSeparacion(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	/*
	 * Draw Pacman
     *
 	 */
   public void drawPacman(Graphics g)
   {
	   g.setColor(Color.YELLOW);
	   g.fillOval(cells[pacmanRow][pacmanCol].getX()*18, cells[pacmanRow][pacmanCol].getY()*18, 22, 22);
   }
   
   public void moverDer()
   {
	   if((cells[pacmanRow][pacmanCol+1].getType() == 'm')||(cells[pacmanRow][pacmanCol+1].getType() == 'n')
			   ||(cells[pacmanRow][pacmanCol+1].getType() == 'v'))
		   pacmanCol++;
   }
}
