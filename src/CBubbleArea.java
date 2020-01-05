import java.awt.*;
import java.awt.event.*;

public class CBubbleArea extends Canvas{

	private static final long serialVersionUID = 1L;
	private static final int MAX_SET_SIZE = 500;
	private static final int MIN_BUBBLE_diameter = 60;
	private static final int MAX_BUBBLE_diameter = 120;
	private static final int LIMIT_SMALL_diameter = 90;
	private static final int BUBBLE_PADDING = 10;
	
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
			g.fillOval(x - getRadius(), y - getRadius(), diameter, diameter);
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
					g.drawString(str[i], x - getRadius() + 10, y + (i-1)*lineIncrement);
			}
			
		}
		
		public int getDiameter()
		{
			return this.diameter;
		}
		
		public int getRadius()
		{
			return (int) Math.round(this.diameter/2.0);
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
						str = "";
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
	private int CanvasHeight, CanvasWidth;
	
	CBubbleArea(int width, int height)
	{
		setBackground(BackgroundColor);
		this.CanvasHeight = height;
		this.CanvasWidth = width;
	}
	
	CBubbleArea(CTaskList taskList, int width, int heigth)
	{
		setBackground(BackgroundColor);
		this.CanvasHeight = heigth;
		this.CanvasWidth = width;
		
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
		for(int i=0; i < getNumberOfBubbles(); i++)
		{
			bubbleSet[i].drawBubble(g);
		}
	}
	
	private void setXYofBubbles()
	{		
		CBubble bub;
		int x = 0; int y = 0;
		int radius = 0;
		int radiusOld = 0;
		int maxRadiusOld = 0;
		int idx = 0;
		int idxStart = 0;
		
		boolean maxHeightReached = false;
		
		while(idx < getNumberOfBubbles() && !maxHeightReached)
		{
			boolean rowFull = false;	
			int sumDiameter = 0;
			int maxDiameter = 0;
			//1. wieviele Bubbles in dieser Zeile?
			//2. maximaler Radius der bubbles in dieser Zeile
			while(!rowFull && idx < getNumberOfBubbles())
			{
				bub = bubbleSet[idx];
				if(sumDiameter + bub.getDiameter() < CanvasWidth)
				{
					//new bubble fits in
					sumDiameter += bub.getDiameter() + BUBBLE_PADDING;
					if(bub.getDiameter() > maxDiameter)
					{
						maxDiameter = bub.getDiameter();
					}
					idx++;
				}
				else
				{
					//max number of bubbles in line reached
					rowFull = true;
				}
			}
			
			//3. Abstand zur Zeile darüber festlegen (=y)
			y = y + (int) Math.round(maxDiameter/2.0) + BUBBLE_PADDING + maxRadiusOld;
			if(y > CanvasHeight)
			{
				maxHeightReached = true;
			}
			else
			{
				maxRadiusOld = (int) Math.round(maxDiameter/2.0);
				
				//4. einzelne Bubbbles in Abstand bringen (=x)
				x = 0;
				radiusOld = 0;
				for(int i=idxStart; i < idx; i++)
				{ 
					radius = bubbleSet[i].getRadius();
					bubbleSet[i].y = y;
					bubbleSet[i].x = x + radiusOld + BUBBLE_PADDING + radius;
					radiusOld = radius;
					x = bubbleSet[i].x;
				}
				
				idxStart = idx;
			}
		}
	}
	
	public void updateBubbleArea(CTaskList taskList)
	{
		for(int i = 0; i < taskList.getSize() && i < MAX_SET_SIZE; i++)
		{
			bubbleSet[i] = new CBubble(taskList.getTask(i));
			nrBubbles = i+1;
		}
		setXYofBubbles();
		repaint();
	}
	
	class MouseWatcher extends MouseAdapter
	{
		
	}
	
	
	
		
}
