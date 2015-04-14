/**
 *BubbleComponent.java
 *The component of Bubble that paints it onto the frame
 */

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import java.util.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import java.io.File;


@SuppressWarnings("serial")
public class BubbleComponent extends JComponent
{
	private Bubble bullet; //A bubble defined as the bullet
	private ArrayList<Bubble> list = new ArrayList<Bubble>(); //ArrayList of Bubbles
	
	/**
	 *Constructor
	 *Constructs/defines the bullet bubble and 15 Bubbles
	 */
	public BubbleComponent()
	{
		bullet = new Bubble(10, 5, this);

		for (int i = 0; i < 15; i++)
		{
			list.add(new Bubble(50, 10, this));
		}
	}
	
	/**
	 *Paints the bubbles and picture of the tank
	 *@param g The Graphics being used
	 */
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;

		try
		{
			BufferedImage img = ImageIO.read(new File("Tank.gif"));
			int x = (int)g.getClipBounds().getWidth()/2 -50;
			int y = (int)g.getClipBounds().getHeight() - 70;
			g.drawImage(img, x, y, null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		bullet.drawBullet(g2);

		for (Bubble b : list)
		{
			if (!b.popped)
				b.draw(g2);
			if (b.getBounds().intersects(bullet.getBounds()))
				b.pop();
		}
	}
	
	/**
	 *Creates threads for the bubbles and starts the animation
	 */
	public void startAnimation()
	{
		class AnimationRunnable implements Runnable
		{
			private Bubble bubble;
			public AnimationRunnable(Bubble bub)
			{
				bubble = bub;
			}

			public void run()
			{
				try
				{
					bubble.animate();
				}
				catch (InterruptedException e)
				{
				}
			}
		}

		class BulletAnimationRunnable implements Runnable
		{
			private Bubble bull;
			public BulletAnimationRunnable(Bubble bub)
			{
				bull = bub;
			}

			public void run()
			{
				try
				{
					bull.animateBullet();
				}
				catch (InterruptedException e)
				{
				}
			}
		}
		
		Runnable rt = new BulletAnimationRunnable(bullet);
		Thread tb = new Thread(rt);
		tb.start();
		
		for (Bubble b : list)
		{
			Runnable r = new AnimationRunnable(b);
			Thread t = new Thread(r);
			t.start();
		}
	}
}
