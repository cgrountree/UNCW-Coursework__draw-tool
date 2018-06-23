package draw;

import java.awt.Graphics;
import java.awt.Color;

/**
 * @author Cody Rountree Abstract shape class for implemented different types of
 *         shapes properties: int startX, int startY, Color myColor
 */
public abstract class Shape {
	private int startX;
	private int startY;

	private Color myColor;

	/**
	 * Public method that that sets the color
	 * 
	 * @param g,
	 *            graphics parameter
	 */
	public void draw(Graphics g) {
		g.setColor(getMyColor());
	}

	public abstract boolean contains(int x, int y);

	/**
	 * Public method to get startX property
	 * 
	 * @return startX
	 */
	public int getStartX() {
		return startX;
	}

	/**
	 * Public method to set the startX property
	 * 
	 * @param startX,
	 *            a given int
	 */
	public void setStartX(int startX) {
		this.startX = startX;
	}

	/**
	 * Public method to get the startY property
	 * 
	 * @return startY
	 */
	public int getStartY() {
		return startY;
	}

	/**
	 * Public method to set the startY property
	 * 
	 * @param startY,
	 *            a given int
	 */
	public void setStartY(int startY) {
		this.startY = startY;
	}

	/**
	 * Public method to get the myColor property
	 * 
	 * @return myColor
	 */
	public Color getMyColor() {
		return myColor;
	}

	/**
	 * Public method to set the myColor property
	 * 
	 * @param myColor,
	 *            a given Color
	 */
	public void setMyColor(Color myColor) {
		this.myColor = myColor;
	}

}