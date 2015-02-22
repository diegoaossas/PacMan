package pacman.menus;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import Libreria.Actions;
import Libreria.Mapa;
import pacman.main.Panel;
import pacman.mapa.Cell;
import pacman.mapa.Pacman;

public class PruebaMapa extends GameState
{
    private int tileHeight;
    private int tileWidth;
    private Cell[][] cells;
    final static int CELL = Cell.CELL;
	private BufferedImage bg;
	
	ArrayList<String> lineList;
	
	private Pacman pacman1;
	private Pacman pacman2;
	private Pacman pacman3;
	private Pacman pacman4;

    public Cell[][] getCells() {
        return cells;
    }
    
    /**
     * Reads from the map file and create the two dimensional array
     */
    private void createCellArray()
    {
        tileHeight = lineList.size();
        tileWidth  = lineList.get(0).length();
        int anchoTablero = tileWidth * CELL;

        // Create the cells
        cells = new Cell[tileHeight][tileWidth];

        for (int row = 0; row < tileHeight; row++) {
            String line = lineList.get(row);

            for (int column = 0; column < tileWidth; column++) {
                char type = line.charAt(column);

                cells[row][column] = new Cell(column, row, type);
                cells[row][column].setSeparacion( (Panel.ANCHO-anchoTablero)/(2*CELL), 50/CELL);
            }
        }
    }
    
	public PruebaMapa(GameStateManager gsm)
	{
		this.gsm = gsm;
		
		try
		{
			bg = ImageIO.read(getClass().getResource("/Backgrounds/fondoTablero.jpg"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
        //setPreferredSize(new Dimension(CELL * tileWidth, CELL * tileHeight));
	}
	
	@Override
	public void draw(Graphics2D g)
	{        
		g.drawImage(bg, 0, 0, Panel.ANCHO, Panel.ALTO, null);
		//g.setColor(Color.BLACK);
	    //g.fillRect(0, 0, tileWidth * CELL, tileHeight * CELL);
	
	    // Outer loop loops through each row in the array
	    for (int row = 0; row < tileHeight; row++) {
	
	        // Inner loop loops through each column in the array
	        for (int column = 0; column < tileWidth; column++) {
	            cells[row][column].drawBackground(g);
	        }
	    }
	    
	    if(pacman1 != null)
	    	pacman1.drawPacman(g);
	    if(pacman2 != null)
	    	pacman2.drawPacman(g);
	    if(pacman3 != null)
	    	pacman3.drawPacman(g);
	    if(pacman4 != null)
	    	pacman4.drawPacman(g);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		try
		{
			GameStateManager.cliente.getOut().writeObject(Actions.GETMAPA);
			Mapa mapa = (Mapa) GameStateManager.cliente.getIn().readObject();
			lineList = mapa.lineList;
			System.out.println("Mapa recibido: " + lineList.size() + "x"+lineList.get(0).length());
			createCellArray();
	        pacman1 = new Pacman(1, 1, this, 4);
	        pacman2 = new Pacman(29, 1, this, 4);
	        pacman3 = new Pacman(29, 26, this, 4);
	        pacman4 = new Pacman(1, 26, this, 4);
		}
		catch (ClassNotFoundException | IOException e)
		{
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		// TODO Auto-generated method stub
		pacman1.moverDer();
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent ke) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
