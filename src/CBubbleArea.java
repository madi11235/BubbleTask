import java.awt.*;
import java.awt.event.*;

public class CBubbleArea extends Canvas{

	private static final long serialVersionUID = 1L;
	private static final int MAX_SET_SIZE = 500;
	private static final int MIN_BUBBLE_diameter = 60;
	private static final int MAX_BUBBLE_diameter = 120;
	private static final int LIMIT_SMALL_diameter = 90;
	
	public static final Color BackgroundColor = new Color(250, 235, 215);
	public static final Color urgentColor = new Color(178, 34, 34);
	public static final Color bubbleColor = new Color(25, 25, 112);
	
	public static Font smallFont = new Font("Avenir", Font.PLAIN, 10);
	public static Font mediumFont = new Font("Avenir", Font.PLAIN, 12);
	
	public class CBubble
	{
		int x,y;
		private int diameter;
		private Color fillColor;
		private String text; 
		
		CBubble(CTask task)
		{
			updateBubbleFromTask(task);
			this.x = 0;
			this.y = 0;
		}
		
		private int computediameter(double prio)
		{
			double prioNormal = (prio - CTask.MIN_PRIORITY)/(CTask.MAX_PRIORITY - CTask.MIN_PRIORITY);
			double diameterDouble = prioNormal * (double)(MAX_BUBBLE_diameter - MIN_BUBBLE_diameter) + (double)(MIN_BUBBLE_diameter);
			return (int) diameterDouble; 
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
			g.fillOval(x, y, diameter, diameter);
			int lineIncrement = 11;
			
			g.setColor(Color.WHITE);
			if(diameter < LIMIT_SMALL_diameter)
			{
				g.setFont(smallFont);
				lineIncrement = 11;
			}
			else
			{
				g.setFont(mediumFont);
				lineIncrement = 14;
			}
			
			String[] str = textUmbrechen(text, diameter);
			for(int i=0; i < str.length; i++)
			{
					g.drawString(str[i], x-(int)(0.5*diameter)+10, y-(int)(0.5*diameter) + i*lineIncrement);
			}
			
		}
		
		private String[] textUmbrechen(String text, int diameter)
		{
			String[] out = {" ", " ", " "};
			
			int charPerLine  = 7;
			
			if(diameter > 70 && diameter <= 90)
				charPerLine = 11;
			else
			{
				if(diameter > 90 && diameter <=110)
					charPerLine = 14;
				else
				{
					if(diameter > 110)
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
			this.diameter = computediameter(task.getPriority());
			this.text = task.getDescription();
			this.fillColor = computeColor(task.getPriority());
		}
	}
	
	CBubble[] bubbleSet = new CBubble[MAX_SET_SIZE];
	private int nrBubbles = 0;
	
	CBubbleArea()
	{
		setBackground(BackgroundColor);
	}
	
	CBubbleArea(CTaskList taskList)
	{
		setBackground(BackgroundColor);
		
		for(int i = 0; i < taskList.getSize() && i < MAX_SET_SIZE; i++)
		{
			bubbleSet[i] = new CBubble(taskList.getTask(i));
			nrBubbles = i+1;
		}
		
		setXYofBubbles();
		
	}
	
	public int getNumberOfBubbles()
	{
		return nrBubbles;
	}
	
	public void paint(Graphics g)
	{
		
	}
	
	private void setXYofBubbles()
	{
		
	}
	
	class MouseWatcher extends MouseAdapter
	{
		
	}
	
	
	
		
}
