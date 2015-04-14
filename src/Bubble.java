/**
 *Bubble.java
 *A bubble/ellipse that floats around the frame and 'pops' when a bullet collides with it
 */


import java.util.Random;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JComponent;

public class Bubble
{
	private int delay;				//delay in ms
	public boolean popped;			//whether the bubble has been popped
	private boolean addX;			//speed in x-axis
	private boolean addY;			//speed in y-axis
	private boolean initial;		//whether the bubble is drawn for the first time
	private JComponent component;	//The component being used
	public int xLeft;				//x coord of top left corner of bubble
	public int yTop;				//y coord of top left corner of bubble
	private int diameter;			//diameter of the bubble
	private Graphics2D g;			//The graphics being used
	private Color color;			//color of the bubble
	
	private Ellipse2D mainBubble;
	
	/**
	 *Constructor
	 * @param diameter the diameter of the bubble
	 * @param delay the delay in ms
	 * @param component the component being used
	 */
	public Bubble(int diameter, int delay, JComponent component)
	{
		initial = true;
		this.diameter = diameter;
		this.delay = delay;
		this.component = component;

		Random rand = new Random(); //for colorful bubbles
		final float hue = rand.nextFloat();
		final float saturation = 0.9f;
		final float luminance = 1.0f;
		color = Color.getHSBColor(hue, saturation, luminance);
	}
	
	/**
	 *Moves the bubble to its new position
	 */
	public void animate() throws InterruptedException
	{
		while (!popped)
		{
			int x = 1;
			int y = 1;

			move(x, y);
			pause();
		}
		Thread.sleep(1000);
		popped = false;
		animate();
	}
	
	/**
	 *Moves the 'Bullet' bubble to its new position
	 */
	public void animateBullet() throws InterruptedException
	{
		while (!popped)
		{
			moveBullet();
			pause();
		}
	}
	
	/**
	 *Repaints the bubble and puts the thread to sleep for the delay amount
	 */
	public void pause() throws InterruptedException
	{
		component.repaint();
		Thread.sleep(delay);
	}
	
	/**
	 *Changes the bubble's direction when it hits the frame's borders
	 */
	public void hitEdge()
	{
		if (xLeft <= 0)
		{
			addX = true;
		}
		if (xLeft >= g.getClipBounds().getWidth()-diameter)
		{
			addX = false;
		}
		if (yTop <= 0)
		{
			addY = true;
		}
		if (yTop >= g.getClipBounds().getHeight()-diameter)
		{
			addY = false;
		}
	}

	/**
	 *Determines which direction for the bubble to move
	 *
	 *@param dx the speed of the bubble's x-axis movement
	 *@param dy the speed of the bubble's y-axis movement
	 */
	public void move(double dx, double dy) //dx dy = amount to move/speed
	{
		if (addX)
			xLeft += dx;
		else
			xLeft -= dx;
		if (addY)
			yTop += dy;
		else
			yTop -= dy;
	}

	/**
	 *Moves the "Bullet" bubble upwards and resets its position 
	 *when the bullet hits the top border
	 */
	public void moveBullet()
	{
		if (addY)
		{
			xLeft = (int) g.getClipBounds().getWidth()/2;
			yTop = (int) (g.getClipBounds().getHeight()-60);
			addY = !addY;
		}
		else
		yTop -= 1;
	}
	
	/**
	 *Draws the bubble onto the frame
	 *@param g2 The Graphics2D being used
	 */
	public void draw (Graphics2D g2)
	{
		g = g2;
		if (initial)
		{
			xLeft = (int)(Math.random() * g2.getClipBounds().getWidth());
			yTop = (int)(Math.random() * g2.getClipBounds().getHeight());
			initial = false;
		}
		hitEdge();
		mainBubble = new Ellipse2D.Double(xLeft, yTop, diameter, diameter);

		g2.setColor(color);
		g2.draw(mainBubble);
	}
	
	/**
	 *Draws the "Bullet" bubble onto the frame
	 *@param g2 The Graphics2D being used
	 */
	public void drawBullet (Graphics2D g2)
	{
		g = g2;
		if (initial)
		{
			xLeft = (int) g2.getClipBounds().getWidth()/2;
			yTop = (int) g2.getClipBounds().getHeight() - 60;
			initial = false;
		}
		hitEdge();
		mainBubble = new Ellipse2D.Double(xLeft, yTop, diameter, diameter);
		g2.setColor(Color.BLACK);
		g2.fill(mainBubble);
	}
	
	/**
	 *Sets popped to true so the bubble won't be repainted
	 */
	public void pop()
	{
		popped = true;
	}
	
	/**
	 *Returns the Rectangle2D bounds of the bubble/ellipse2D
	 *@return the bounds of the bubble
	 */
	public Rectangle2D getBounds()
	{
		return mainBubble.getBounds2D();
	}
}
