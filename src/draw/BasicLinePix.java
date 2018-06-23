package draw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

/**
 * @author Cody Rountree 
 * 		   BasicLinePix class that extends JFrame to create a
 *         drawing panel for users to draw lines and rectangles. Properties:
 *         JPanel drawingPanle, JPanel statusBar, JLabel statusLabel, JMenuBar
 *         menuBar, Jmenu fileMenu, JMenu drawMenu, EventHandler eh, String
 *         currentState, Color myColor, ArrayList<Shape> shapes
 */
@SuppressWarnings("serial")
public class BasicLinePix extends JFrame {

	private JPanel drawingPanel; // user draws here

	private JPanel statusBar;
	private JLabel statusLabel;// used to show informational messages

	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu drawMenu;
	private EventHandler eh;

	private String currentState;
	private Color currentColor;
	private ArrayList<Shape> shapes = new ArrayList<>();

	/**
	 * Main that instantiates the BasicLinePix and sets its visibility to true.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		BasicLinePix lp = new BasicLinePix();
		lp.setVisible(true);
	}

	/**
	 * Constructor for BasicLinePix that sets up the layout, instantiates the event
	 * handler and builds the menu
	 */
	public BasicLinePix() {
		setTitle("Basic Line Pix 1.0");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Container cp;

		cp = getContentPane();
		cp.setLayout(new BorderLayout());

		eh = new EventHandler();

		drawingPanel = makeDrawingPanel();
		drawingPanel.addMouseListener(eh);
		drawingPanel.addMouseMotionListener(eh);

		statusBar = createStatusBar();

		cp.add(drawingPanel, BorderLayout.CENTER);
		cp.add(statusBar, BorderLayout.SOUTH);

		buildMenu();

		pack();
	}

	/**
	 * Private method that builds the menu for both file options and draw options.
	 * Adds action listeners to each menu item.
	 */
	private void buildMenu() {
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		drawMenu = new JMenu("Draw");

		JMenuItem menuItem = new JMenuItem("New");
		menuItem.addActionListener(eh);
		fileMenu.add(menuItem);

		menuItem = new JMenuItem("Open");
		menuItem.addActionListener(eh);
		fileMenu.add(menuItem);

		menuItem = new JMenuItem("Save");
		menuItem.addActionListener(eh);
		fileMenu.add(menuItem);

		menuItem = new JMenuItem("Exit");
		menuItem.addActionListener(eh);
		fileMenu.add(menuItem);

		menuBar.add(fileMenu);

		JMenuItem drawItem = new JMenuItem("Line");
		drawItem.addActionListener(eh);
		drawMenu.add(drawItem);

		drawItem = new JMenuItem("Rectangle");
		drawItem.addActionListener(eh);
		drawMenu.add(drawItem);

		menuBar.add(drawMenu);

		setJMenuBar(menuBar);

	}

	/**
	 * Private method that makes the drawing panel, sets its dimensions and color.
	 * 
	 * @return p, the JPanel
	 */
	private JPanel makeDrawingPanel() {
		JPanel p = new JPanel();
		p.setPreferredSize(new Dimension(500, 400));
		p.setBackground(Color.YELLOW);

		return p;
	}

	/**
	 * Private method that creates a status bar and sets its default message to "No
	 * message"
	 * 
	 * @return panel, the JPanel
	 */
	private JPanel createStatusBar() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		statusLabel = new JLabel("No message");
		panel.add(statusLabel, BorderLayout.CENTER);

		panel.setBorder(new BevelBorder(BevelBorder.LOWERED));

		return panel;
	}

	// this method overrides the paint method defined in JFrame
	// add code here for drawing the lines on the drawingPanel
	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics g1 = drawingPanel.getGraphics();
		if (shapes.size() > 0) {
			for (int i = 0; i < shapes.size(); i++) {
				Shape currShape = shapes.get(i);
				currShape.draw(g1);
			}
		}

		// Send a message to each line in the drawing to
		// draw itself on g1
	}

	/**
	 * Public method that generates a random color from a list of 4 colors: red,
	 * green, blue, and magenta.
	 * 
	 * @return colorList[randomIndex], one of the four colors in the colorList
	 */
	public Color colorize() {
		Color[] colorList = { Color.red, Color.green, Color.blue, Color.magenta };
		Random r = new Random();
		int randomIndex = r.nextInt(colorList.length);
		return colorList[randomIndex];
	}

	// Inner class - instances of this class handle action events
	private class EventHandler implements ActionListener, MouseListener, MouseMotionListener {

		private int startX, startY; // line's start coordinates
		private int lastX, lastY; // line's most recent end point

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getActionCommand().equals("Exit")) {
				statusLabel.setText("Exiting program...");
				System.exit(0);
			}
			if (arg0.getActionCommand().equals("Line")) {
				currentState = "Line";
			}
			if (arg0.getActionCommand().equals("Rectangle")) {
				currentState = "Rectangle";
			}
			if (arg0.getActionCommand().equals("New")) {
				shapes.clear();
				repaint();
			}
			if (arg0.getActionCommand().equals("Save")) {
				JFileChooser fileSaver = new JFileChooser();
				fileSaver.setCurrentDirectory(new File("./"));
				int selection = fileSaver.showSaveDialog(statusBar);

				if (selection == JFileChooser.APPROVE_OPTION) {
					File fileToSave = fileSaver.getSelectedFile();
					if (fileToSave == null) {
						return;
					}
					if (fileToSave.exists()) {
						selection = JOptionPane.showConfirmDialog(statusBar, "Replace existing file?");
						if (selection == JOptionPane.NO_OPTION) {
							return;
						}
						if (selection == JOptionPane.CANCEL_OPTION) {
							return;
						}
					}
					try {
						BufferedWriter outFile = new BufferedWriter(new FileWriter(fileToSave));
						for (Shape aShape : shapes) {
							outFile.write(aShape.toString());
							outFile.newLine();
						}
						outFile.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
			if (arg0.getActionCommand().equals("Open")) {
				shapes.clear();
				Scanner s = null;
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File("./"));
				int result = fileChooser.showOpenDialog(statusBar);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					String filePath = selectedFile.getAbsolutePath();
					File theFile = new File(filePath);
					try {
						s = new Scanner(theFile, "UTF-8");
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					while (s.hasNext()) {
						String currentLine = s.nextLine();
						if (currentLine.contains(",")) {
							String[] splitLine = currentLine.split(",");
							if (splitLine[0].contains("L")) {
								shapes.add(new Line(Integer.parseInt(splitLine[1]), Integer.parseInt(splitLine[2]),
										Integer.parseInt(splitLine[3]), Integer.parseInt(splitLine[4]), colorize()));
							}
							if (splitLine[0].contains("R")) {
								shapes.add(new Rectangle(Integer.parseInt(splitLine[1]), Integer.parseInt(splitLine[2]),
										Integer.parseInt(splitLine[3]), Integer.parseInt(splitLine[4]), colorize()));
							}
						}
					}
				}
				if (result == JFileChooser.CANCEL_OPTION) {
					return;
				}
				repaint();
			}

		}

		@Override
		public void mousePressed(MouseEvent e) {

			startX = e.getX();
			startY = e.getY();
			currentColor = colorize();
			// initialize lastX, lastY
			lastX = startX;
			lastY = startY;

		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// Implement rubber-band cursor
			Graphics g = drawingPanel.getGraphics();
			g.setColor(currentColor);

			g.setXORMode(drawingPanel.getBackground());

			// REDRAW the line that was drawn
			// most recently during this drag
			// XOR mode means that yellow pixels turn black
			// essentially erasing the existing line
			if (currentState == null || currentState == "Line") {
				g.drawLine(startX, startY, lastX, lastY);
				// draw line to current mouse position
				// XOR mode: yellow pixels become black
				// black pixels, like those from existing lines, temporarily become
				// yellow
				g.drawLine(startX, startY, e.getX(), e.getY());
				lastX = e.getX();
				lastY = e.getY();

			}
			if (currentState == "Rectangle") {
				g.drawRect(startX, startY, lastX - startX, lastY - startY);
				g.drawRect(startX, startY, e.getX() - startX, e.getY() - startY);
				lastX = e.getX();
				lastY = e.getY();
			}

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			if (currentState == null || currentState == "Line") {
				Line currentLine = new Line(startX, startY, arg0.getX(), arg0.getY(), currentColor);
				shapes.add(currentLine);
			}
			if (currentState == "Rectangle") {
				Rectangle currentRect = new Rectangle(startX, startY, arg0.getX() - startX, arg0.getY() - startY,
						currentColor);
				shapes.add(currentRect);
			}

		}

		@Override
		public void mouseClicked(MouseEvent arg0) {

		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

}