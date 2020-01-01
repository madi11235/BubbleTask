import java.awt.*;
import java.awt.event.*;

public class CBubbleArea extends Canvas{

	private static final long serialVersionUID = 1L;
	private static final int MAX_SET_SIZE = 500;
	
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
		setBackground(Color.WHITE);
	}
	
	class MouseWatcher extends MouseAdapter
	{
		
	}
	
	public void paint(Graphics g)
	{
		g.setColor(Color.BLUE);
		g.fillOval(100, 100, 49, 49);
	}
	
}
