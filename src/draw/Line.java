package draw;

import java.awt.Color;
import java.awt.Graphics;

/**
 * @author Cody Rountree Line class that extends the Shape class to implement a
 *         line properties: int endX, int endY
 */
public class Line extends Shape {
	private int endX;
	private int endY;

	/**
	 * Constructor for the Line class that sets given parameters to the properties
	 * of the class
	 * 
	 * @param startX,
	 *            a given int
	 * @param startY,
	 *            a given int
	 * @param endX,
	 *            a given int
	 * @param endY,
	 *            a given int
	 * @param aColor,
	 *            a given Color
	 */
	public Line(int startX, int startY, int endX, int endY, Color aColor) {
		this.setStartX(startX);
		this.setStartY(startY);
		this.endX = endX;
		this.endY = endY;
		this.setMyColor(aColor);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getMyColor());
		g.drawLine(getStartX(), getStartY(), endX, endY);

	}

	@Override
	public boolean contains(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Public method to get the property endX
	 * 
	 * @return endX
	 */
	public int getEndX() {
		return this.endX;
	}

	/**
	 * Public method to get the property endY
	 * 
	 * @return endY
	 */
	public int getEndY() {
		return this.endY;
	}

	public String toString() {
		String result = "L" + "," + this.getStartX() + "," + this.getStartY() + "," + this.getEndX() + ","
				+ this.getEndY();
		return result;
	}

}
