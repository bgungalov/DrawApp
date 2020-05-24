import java.awt.BasicStroke; 			
import java.awt.Color; 				
import java.awt.Graphics; 			
import java.awt.Graphics2D; 			
import java.awt.event.ActionEvent; 		
import java.awt.event.ActionListener; 		
import java.awt.event.MouseEvent; 		
import java.awt.event.MouseListener; 		
import java.awt.event.MouseMotionListener;	
import java.awt.image.BufferedImage;
import javax.swing.ButtonGroup; 		
import javax.swing.ImageIcon; 			
import javax.swing.JButton; 			
import javax.swing.JColorChooser;		
import javax.swing.JFrame; 			
import javax.swing.JMenu; 			
import javax.swing.JMenuBar; 			
import javax.swing.JMenuItem; 			
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTextArea; 		

public class DrawApp extends JPanel implements MouseListener, ActionListener, MouseMotionListener 				// receiving an event from mouse
{
	private static final long serialVersionUID = 1L; 		// serialVersionUID
	public static int stroke, eraser = 0; 				// stroke, erase
	private int xX1, yY1, xX2, yY2, choice;				// choice
	private static final Color BACKGROUND_COLOR = Color.WHITE;	// set background of the canvas as white
	private int eraserW = 50; 					// the eraser width is set to 50 w
	private int eraserH = 50; 					// the eraser height is set to 50 h

	public static void main(String[] args) 				// main method
	{
		new DrawApp();
	}

	DrawApp() 							// constructor which is MainFile
	{

		JFrame frame = new JFrame("DrawApp");			// the title of program is set as DrawApp
		frame.setSize(1200, 800);					// the size of the frame is set to 1200w, 800h

		frame.setBackground(BACKGROUND_COLOR); 				// this will set the background color
		frame.getContentPane().add(this);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar); 					// create a menu bar
		JMenu help = new JMenu("Help");
		menuBar.add(help);
		JMenuItem about = new JMenuItem("About");
		help.add(about);
		about.addActionListener(this);					// this event passed to every ActionListener object that registered using addActionListener

		JButton b1 = new JButton("Clear Drawing"); 			// this button is set as Clear Drawing	
		b1.addActionListener(this);					// button 1 passed to every ActionListener object that registered using addActionListener
		JButton color = new JButton("Color");
		color.addActionListener(this);
		JButton erase = new JButton("Erase"); 				// this button is set as Eraser
		erase.addActionListener(this);
		JButton b2 = new JButton("Empty Rect"); 			// this button is set as Empty Rect
		b2.addActionListener(this);					// button 2 passed to every ActionListener object that registered using addActionListener
		JButton b3 = new JButton("Empty Ellipse"); 			// this button is set as Empty Oval
		b3.addActionListener(this);					// button 3 passed to every ActionListener object that registered using addActionListener
		JButton b4 = new JButton("Line"); 				// this button is set as Line
		b4.addActionListener(this); 					// button 4 passed to every ActionListener object that registered using addActionListener
		JRadioButton medium = new JRadioButton("Medium Line"); 		// there are radio button to choose Medium Line
		medium.addActionListener(this);
		JRadioButton thick = new JRadioButton("Thick Line"); 		// there are radio button to choose Thick Line
		thick.addActionListener(this);

		ButtonGroup lineOption = new ButtonGroup();
		lineOption.add(medium); 					// line for medium
		lineOption.add(thick);

		this.add(b1);
		this.add(color);
		this.add(erase);
		this.add(b2);
		this.add(b3);
		this.add(b4);
		this.add(medium);
		this.add(thick);
		addMouseListener(this); 					// receiving an event from the mouse
		frame.setVisible(true);						// this frame is set visible to user
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 		// the operation will automatically off when user clicked exit button

	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if (grid == null)
		{
			int w = this.getWidth(); 						// width
			int h = this.getHeight(); 						// height
			grid = (BufferedImage) (this.createImage(w, h));
			gc = grid.createGraphics();
			gc.setColor(Color.BLUE);
		}

		g2.drawImage(grid, null, 0, 0);
		check();
	}

	BufferedImage grid;
	Graphics2D gc;

	public void draw()
	{
		Graphics2D g = (Graphics2D) getGraphics();
		int w = xX2 - xX1;
		if (w < 0)
			w = w * (-1);

		int h = yY2 - yY1;
		if (h < 0)
			h = h * (-1);

		if(choice == 1)
		{
			check();
			gc.drawRect(xX1, yY1, w, h);
			repaint();
		}

		else if(choice == 2)
		{
			check();
			gc.drawOval(xX1, yY1, w, h);
			repaint();
		}

		else if(choice == 3)
		{
			if (stroke == 0)
				gc.setStroke(new BasicStroke(3));
			if (stroke == 1)
				gc.setStroke(new BasicStroke(6));
			gc.drawLine(xX1, yY1, xX2, yY2);
			repaint();
		}

		else if(choice == 4)
		{
			repaint();
			Color temp = gc.getColor();
			gc.setColor(BACKGROUND_COLOR);
			gc.fillRect(0, 0, getWidth(), getHeight());
			gc.setColor(temp);
			repaint();
		}

		else
		{
			if (eraser == 1)
				gc.clearRect(xX1, yY1, w, h);
			else
			{

			}
		}
	}

	public void check()
	{
		if (xX1 > xX2)
		{
			int z = 0;
			z = xX1;
			xX1 = xX2;
			xX2 = z;
		}
		if (yY1 > yY2)
		{
			int z = 0;
			z = yY1;
			yY1 = yY2;
			yY2 = z;
		}
	}

	public void actionPerformed(ActionEvent e)
	{

		super.removeMouseMotionListener(this);

		if (e.getActionCommand().equals("Color")) 				// action when user choose Color button
		{
			Color bgColor = JColorChooser.showDialog(this, "Choose Background Color", getBackground());
			if (bgColor != null)
				gc.setColor(bgColor);
		}

		if (e.getActionCommand().equals("About")) 				// action when user choose About button			
		{
			System.out.println("About Has Been Pressed");
			JFrame about = new JFrame("About");
			about.setSize(300, 300);
			JButton picture = new JButton(new ImageIcon("logo.png"));
			about.getContentPane().add(picture);
			about.setVisible(true);
		}

		if (e.getActionCommand().equals("Empty Rect"))				// action when user choose Empty Rect
		{
			choice = 1;
		}

		if (e.getActionCommand().equals("Empty oval")) 				// action when user choose Empty Oval
		{
			choice = 2;
		}

		if (e.getActionCommand().equals("Line")) 				// action when user choose Line button
		{
			choice = 3;
		}

		if (e.getActionCommand().equals("Medium Line")) 			// action when user choose Medium Line
		{
			stroke = 0;
		}

		if (e.getActionCommand().equals("Thick Line")) 				// action when user choose Thick Line
		{

			stroke = 1;
		}

		if (e.getActionCommand().equals("Erase")) 				// action when user choose Erase button
		{
			eraser = 1;
			choice = 5;
			super.addMouseMotionListener(this);
		}

		if (e.getActionCommand().equals("Clear Drawing"))
		{
			choice = 4;
			draw();
		}

	}

	public void mouseExited(MouseEvent e) 						// action when mouse is exited
	{
	}

	public void mouseEntered(MouseEvent e)						// action when mouse is entered
	{
	}

	public void mouseClicked(MouseEvent e) 						// action when mouse is clicked
	{
	}

	public void mousePressed(MouseEvent e) 						// action when mouse is pressed
	{

		xX1 = e.getX();
		yY1 = e.getY();

	}

	public void mouseReleased(MouseEvent e)
	{
		xX2 = e.getX();
		yY2 = e.getY();
		draw();
		eraser = 0;
	}


	public void mouseDragged(MouseEvent re)
	{
		Color c = gc.getColor();
		gc.setColor(BACKGROUND_COLOR);
		gc.drawRect(re.getX(), re.getY(), eraserW, eraserH);
		gc.setColor(c);
		repaint();
	}

	public void mouseMoved(MouseEvent arg0)
	{
	}
}