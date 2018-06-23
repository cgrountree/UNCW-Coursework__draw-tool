package draw;

import java.awt.Color;
import java.awt.Graphics;

/**
 * @author Cody Rountree Rectangle class that extends the Shape class to
 *         implement a rectangle properties: int width, int height
 */
public class Rectangle extends Shape {
	private int width;
	private int height;

	/**
	 * Constructor for the Rectangle class that sets given parameters to the
	 * properties of the Rectangle class
	 * 
	 * @param startX,
	 *            a given int
	 * @param startY,
	 *            a given int
	 * @param width,
	 *            a given int
	 * @param height,
	 *            a given int
	 * @param aColor,
	 *            a given Color
	 */
	public Rectangle(int startX, int startY, int width, int height, Color aColor) {
		this.setStartX(startX);
		this.setStartY(startY);
		this.width = width;
		this.height = height;
		this.setMyColor(aColor);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getMyColor());
		g.drawRect(getStartX(), getStartY(), width, height);

	}

	@Override
	public boolean contains(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Public method to get the property width
	 * 
	 * @return width
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * Public method to get the property height
	 * 
	 * @return height
	 */
	public int getHeight() {
		return this.height;
	}

	public String toString() {
		String result = "R" + "," + this.getStartX() + "," + this.getStartY() + "," + this.getWidth() + ","
				+ this.getHeight();
		return result;
	}

}
