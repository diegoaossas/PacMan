package pacman.mapa;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Cell {
	public final static int CELL = 18;
	protected char type;
	protected int x, y;

	/*
	 * Constructor
	 */
	public Cell(int x, int y, char type) {
		this.type = type;
		this.x = x;
		this.y = y;
	}
	
	public void setSeparacion(int x, int y)
	{
		this.x += x;
		this.y += y;
	}

	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	/*
	 * Gets the type
	 */
	public char getType() {
		return type;
	}

	/*
	 * Draw the cell
	 */
	public void drawBackground(Graphics g) {
		int xBase = 0;
		int yBase = 0;

		switch (type) {
		/*
		 * case 'e' : // corral exit g.setColor(Color.WHITE); g.fillRect(x *
		 * CELL, y * CELL + CELL / 2 - 10, CELL, 3);
		 * 
		 * break;
		 */

		case 'k': // horizontal line
		case 'e':
			g.setColor(Color.BLUE);
			g.fillRect(x * CELL, y * CELL + CELL / 2 - 1, CELL, 3);

			break;

		case 'l': // vertical line
		case 'f':
			g.setColor(Color.BLUE);
			g.fillRect(x * CELL + CELL / 2 - 1, y * CELL, 3, CELL);

			break;

		case 'b': // northeast corner
		case 'h':
		case 'o':
			xBase = x * CELL - CELL / 2;
			yBase = y * CELL + CELL / 2;
			drawCorner(g, xBase, yBase);

			break;

		case 'a': // northwest corner
		case 'g':
		case 'p':
			xBase = x * CELL + CELL / 2;
			yBase = y * CELL + CELL / 2;
			drawCorner(g, xBase, yBase);

			break;

		case 'd': // southeast corner
		case 'j':
			xBase = x * CELL - CELL / 2;
			yBase = y * CELL - CELL / 2;
			drawCorner(g, xBase, yBase);

			break;

		case 'c': // southwest corner
		case 'i':
			xBase = x * CELL + CELL / 2;
			yBase = y * CELL - CELL / 2;
			drawCorner(g, xBase, yBase);

			break;

		case 'v':
			break; // empty navigable cell

		case 'm': // navigable cell with pill
			g.setColor(Color.WHITE);
			g.fillRect(x * CELL + CELL / 2 - 1, y * CELL + CELL / 2 - 1, 3, 3);

			break;

		case 'n': // navigable cell with power pellet
			g.setColor(Color.PINK);
			g.fillOval(x * CELL + CELL / 2 - 7, y * CELL + CELL / 2 - 7, 13, 13);

			break;
		/*
		 * case 'x' : // empty non-navigable cell case 'g' : // the Corral
		 */
		default:
			break;
		}
	}

	/*
	 * Draw 3px rounded corner
	 */
	public void drawCorner(Graphics g, int xBase, int yBase) {
		Graphics2D g2 = (Graphics2D) g;
		Rectangle oldClip = g.getClipBounds();

		g2.setClip(x * CELL, y * CELL, CELL, CELL);
		g2.setColor(Color.BLUE);

		Shape oval = new Ellipse2D.Double(xBase, yBase, CELL, CELL);

		g2.setStroke(new BasicStroke(3));
		g2.draw(oval);
		g2.setClip(oldClip);
	}
}
