import java.awt.*;
import java.awt.event.*;

public class CBubbleArea extends Canvas{

	private static final long serialVersionUID = 1L;
	private static final int MAX_SET_SIZE = 500;
	private static final int MIN_BUBBLE_RADIUS = 60;
	private static final int MAX_BUBBLE_RADIUS = 120;
	
	public static final Color BackgroundColor = new Color(250, 235, 215);
	public static final Color urgentColor = new Color(178, 34, 34);
	public static final Color bubbleColor = new Color(25, 25, 112);
	
	public static Font mediumFont = new Font("Avenir", Font.PLAIN, 10);
	
	class CBubble
	{
		int x,y;
		double radius;
		Color fillColor;
		int taskIndex; //taskIndex in taskList 
	}
	
	CBubble[] bubbleSet = new CBubble[MAX_SET_SIZE];
	
	CBubbleArea()
	{
		setBackground(BackgroundColor);
	}
	
	class MouseWatcher extends MouseAdapter
	{
		
	}
	
	public void paint(Graphics g)
	{
		g.setColor(bubbleColor);
		g.fillOval(100, 100, 60, 60);
		
		g.setColor(Color.WHITE);
		g.setFont(mediumFont);
		g.drawString("Aufgabe eins", 110, 120);
		
		g.setColor(urgentColor);
		g.fillOval(200, 200, MAX_BUBBLE_RADIUS, MAX_BUBBLE_RADIUS);
	}
	
}
