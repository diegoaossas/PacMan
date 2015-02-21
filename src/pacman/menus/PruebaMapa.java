package pacman.menus;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import pacman.main.Panel;
import pacman.mapa.Cell;
import pacman.mapa.Pacman;

public class PruebaMapa extends GameState
{
    private String map = "src/pacman/mapa/mapa.txt";
    private int tileHeight;
    private int tileWidth;
    private Cell[][] cells;
    final static int CELL = Cell.CELL;
	private BufferedImage bg;

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
    private void createCellArray(String mapFile) {

        // Scanner object to read from map file
        Scanner           fileReader;
        ArrayList<String> lineList = new ArrayList<String>();

        // Attempt to load the maze map file
        try {
            fileReader = new Scanner(new File(mapFile));

            while (true) {
                String line = null;

                try {
                    line = fileReader.nextLine();
                } catch (Exception eof) {

                    // throw new A5FatalException("Could not read resource");
                }

                if (line == null) {
                    break;
                }

                lineList.add(line);
            }

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
        } catch (FileNotFoundException e) {
            System.out.println("Maze map file not found");
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
		
        createCellArray(map);

        pacman1 = new Pacman(1, 1, this, 4);
        pacman2 = new Pacman(29, 1, this, 4);
        pacman3 = new Pacman(29, 26, this, 4);
        pacman4 = new Pacman(1, 26, this, 4);
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
	    
	    pacman1.drawPacman(g);
	    pacman2.drawPacman(g);
	    pacman3.drawPacman(g);
	    pacman4.drawPacman(g);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
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
