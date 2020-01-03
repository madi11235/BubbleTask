import java.awt.*;
import java.awt.event.*;

public class CBubbleArea extends Canvas{

	private static final long serialVersionUID = 1L;
	private static final int MAX_SET_SIZE = 500;
	private static final int MIN_BUBBLE_RADIUS = 60;
	private static final int MAX_BUBBLE_RADIUS = 120;
	private static final int LIMIT_SMALL_RADIUS = 90;
	
	public static final Color BackgroundColor = new Color(250, 235, 215);
	public static final Color urgentColor = new Color(178, 34, 34);
	public static final Color bubbleColor = new Color(25, 25, 112);
	
	public static Font smallFont = new Font("Avenir", Font.PLAIN, 10);
	public static Font mediumFont = new Font("Avenir", Font.PLAIN, 12);
	
	public class CBubble
	{
		int x,y;
		private int radius;
		private Color fillColor;
		private String text;
		private CTask task;  
		
		CBubble(CTask task)
		{
			updateBubbleFromTask(task);
			this.x = 0;
			this.y = 0;
		}
		
		private int computeRadius(double prio)
		{
			double prioNormal = (prio - CTask.MIN_PRIORITY)/(CTask.MAX_PRIORITY - CTask.MIN_PRIORITY);
			double radiusDouble = prioNormal * (double)(MAX_BUBBLE_RADIUS - MIN_BUBBLE_RADIUS) + (double)(MIN_BUBBLE_RADIUS);
			return (int) radiusDouble; 
		}
		
		private Color computeColor(double prio)
		{
			Color retColor = bubbleColor;
			double limit = (2.0/3.0) * (CTask.MAX_PRIORITY - CTask.MIN_PRIORITY) + CTask.MIN_PRIORITY;
			if(prio > limit)
				retColor = urgentColor;
			
			return retColor;
		}
		
		public void drawBubble(Graphics g)
		{
			g.setColor(fillColor);
			g.fillOval(x, y, radius, radius);
			int lineIncrement = 11;
			
			g.setColor(Color.WHITE);
			if(radius < LIMIT_SMALL_RADIUS)
			{
				g.setFont(smallFont);
				lineIncrement = 11;
			}
			else
			{
				g.setFont(mediumFont);
				lineIncrement = 14;
			}
			
			String[] str = textUmbrechen(text, radius);
			for(int i=0; i < str.length; i++)
			{
					g.drawString(str[i], x-radius+10, y-radius + i*lineIncrement);
			}
			
		}
		
		private String[] textUmbrechen(String text, int radius)
		{
			String[] out = {" ", " ", " "};
			
			int charPerLine  = 7;
			
			if(radius > 70 && radius <= 90)
				charPerLine = 11;
			else
			{
				if(radius > 90 && radius <=110)
					charPerLine = 14;
				else
				{
					if(radius > 110)
						charPerLine = 16;
				}
			}
			
			String str = text;
			int indexSpace;
			int charsUsed = 0;
			
			for(int i=0; i < out.length; i++)
			{
				out[i] = "";
				indexSpace = str.indexOf(" ");
				if(indexSpace > charPerLine || indexSpace == -1)
				{
					if(str.length()<=charPerLine)
					{
						out[i] = str;
					}
					else
					{
						out[i] = str.substring(0, charPerLine) + "...";
						str = str.substring(indexSpace + 1);
					}
				}
				else
				{
					charsUsed = indexSpace;
					while(charsUsed <= charPerLine & indexSpace != -1)
					{	
						out[i] = out[i] + str.substring(0,indexSpace) + " ";
						str = str.substring(indexSpace + 1);
						indexSpace = str.indexOf(" ");
						charsUsed += indexSpace; 
					}
				}
			}
			
			return out;
		}
		
		
		public void updateBubbleFromTask(CTask task)
		{
			this.task = task;
			this.radius = computeRadius(task.getPriority());
			this.text = task.getDescription();
			this.fillColor = computeColor(task.getPriority());
		}
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
		
	}
	
	/*
	private String[] textUmbrechenSmall(String text)
	{
		String[] out = {" ", " ", " "};
		
		int charPerLine = 7;
		
		int indexSpace = text.indexOf(" ");
		String str = text;
		
		
		if(indexSpace > charPerLine)
		{
			//all in one line of size 9
			out[0] = text.substring(0,charPerLine + 2) + "...";
		}
		else
		{
			out[0] = str.substring(0,indexSpace);
			str = str.substring(indexSpace +1 );
			
			indexSpace = str.indexOf(" ");
			if(indexSpace > charPerLine)
			{
				out[1] = str.substring(0,charPerLine) + "...";
			}
			else
			{
				out[1] = str.substring(0,indexSpace);
				str = str.substring(indexSpace + 1);
				
				indexSpace = str.indexOf(" ");
				if(indexSpace > charPerLine || str.length() > charPerLine)
				{
					out[2] = str.substring(0, charPerLine) + "...";
				}
				else
				{
					out[2] = str.substring(0, indexSpace);
				}
			}
		}
		
		return out;
	}
	*/
	
	
	
	
		
		
}
