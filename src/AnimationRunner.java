/**
 * @(#)AnimationRunner.java
 *
 *
 * @author Alex Wang
 * @version 1.00 2015/4/8
 */

import javax.swing.JFrame;

public class AnimationRunner
{
	/**
	 * Constructs the JFrame and adds-sets the animation component.
	 */
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();

		final int FRAME_WIDTH = 600;
		final int FRAME_HEIGHT = 400;

		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setTitle("Bubble Tank");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


	 	BubbleComponent component1 = new BubbleComponent();
		frame.add(component1);
		
		frame.setVisible(true);
		component1.startAnimation();
	}
}
