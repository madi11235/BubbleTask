import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;

/*
 * This class describes the area in the gui, where the 
 * bubbles representing the tasks are drawn. 
 * It contains all methods relevant to interactions of the user with the bubble tasks
 * area.
 */
public class CBubbleArea extends JPanel{

	private static final long serialVersionUID = 1L;
	private static final int MAX_SET_SIZE = 500;
	private static final int MIN_BUBBLE_diameter = 60;
	private static final int MAX_BUBBLE_diameter = 120;
	private static final int LIMIT_SMALL_diameter = 90;
	private static final int BUBBLE_PADDING = 10;
	private static final int DonePipeWidth = 150;
	private static final int DonePipeWOPadding = 120;
	
	public static final Color BackgroundColor = new Color(250, 235, 215);
	public static final Color highPrioColor = new Color(91,44,111);
	public static final Color urgentColor = new Color(178, 34, 34);
	public static final Color bubbleColor = new Color(25, 25, 112);
	public static final Color doneColor = new Color(85, 107, 47); //Olive green
	public static final Color lineColor = new Color(160, 82, 45); //Sienna
	
	public static Font smallFont = new Font("Avenir", Font.PLAIN, 10);
	public static Font mediumFont = new Font("Avenir", Font.PLAIN, 12);
	
	public class CBubble
	{
		int x,y;
		private int diameter;
		private Color fillColor;
		private String text; 
		private CTask task; 
		public int taskIndex;
		
		CBubble(CTask task, int idx)
		{
			updateBubbleFromTask(task, idx);
			this.x = 0;
			this.y = 0;
		}
		
		private int computediameter(double prio)
		{
			double prioNormal = (prio - CTask.MIN_PRIORITY)/(CTask.MAX_PRIORITY - CTask.MIN_PRIORITY);
			double diameterDouble = prioNormal * (double)(MAX_BUBBLE_diameter - MIN_BUBBLE_diameter) + (double)(MIN_BUBBLE_diameter);
			return (int) diameterDouble; 
		}
		
		private Color computeColor(double prio, boolean done)
		{
			Color retColor = bubbleColor;
			if(done)
				retColor = doneColor;
			else
			{
				if(prio > CTask.MAX_PRIORITY)
				{
					retColor = highPrioColor;
				}
				else
				{
					double limit = (2.0/3.0) * (CTask.MAX_PRIORITY - CTask.MIN_PRIORITY) + CTask.MIN_PRIORITY;
					if(prio > limit)
						retColor = urgentColor;
				}
			}
			
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
		
		public void updateBubbleFromTask(CTask task, int idx)
		{
			this.diameter = computediameter(task.getPriority());
			this.text = task.getDescription();
			this.fillColor = computeColor(task.getPriority(), task.getDone());
			this.task = task;
			this.taskIndex = idx;
		}
	}
	
	CDatum today;
	CBubble[] bubbleSet = new CBubble[MAX_SET_SIZE];
	CBubble[] doneTodaySet = new CBubble[MAX_SET_SIZE];
	private int nrBubbles = 0;
	private int nrDoneToday = 0;
	private int CanvasHeight, CanvasWidth;
	
	CTaskEditFrame editFrame; 
	
	/*
	CBubbleArea(int width, int height, CTaskEditFrame editFrame)
	{
		setBackground(BackgroundColor);
		this.CanvasHeight = height;
		this.CanvasWidth = width - DonePipeWidth;
		this.editFrame = editFrame;
		
		today = new CDatum();
		
		addMouseListener(new MouseWatcher());
	}
	*/
	
	CBubbleArea(CTaskList taskList, int width, int heigth, CTaskEditFrame editFrame)
	{
		System.out.println("In constructor for bubble area");
		setBackground(BackgroundColor);
		this.CanvasHeight = heigth;
		this.CanvasWidth = width - DonePipeWidth;
		this.editFrame = editFrame; 
		
		today = new CDatum();
		
		addMouseListener(new MouseWatcher());
		
		//TODO: check if we need to initialize all bubbles in the bubble set
		
		updateBubbleArea(taskList);
	}
	
	public void editBubble(int mouseX, int mouseY)
	/*
	 * Identifies the bubble, that has been clicked and then 
	 * opens an edit frame pre-filled with the chosen task fields.
	 */
	{
		//find task index
		CTask task = null;
		int taskIndex = findBubbleIdx(mouseX, mouseY, bubbleSet, nrBubbles);
		
		//if task index valid, open task editing window
		if(taskIndex != -1)
		{
			task = bubbleSet[taskIndex].task;
			editFrame.openEditView(bubbleSet[taskIndex].taskIndex, task);
		}
		else
		{
			taskIndex = findBubbleIdx(mouseX, mouseY, doneTodaySet, nrDoneToday);
			if(taskIndex != -1)
			{
				task = doneTodaySet[taskIndex].task;
				editFrame.openEditView(doneTodaySet[taskIndex].taskIndex, task);
			}
		}
	}
	
	public int findBubbleIdx(int x, int y, CBubble[] set, int setSize)
	/*
	 *  finds the bubble associated with the x and y coordinates on the
	 *  canvas. 
	 */
	{
		int idx = 0;
		boolean bubFound = false;

		while(!bubFound && idx < setSize)
		{
			int dx = (x - set[idx].x);
			int dy = (y - set[idx].y);
			int dSquared = dx * dx + dy * dy;
			int rSquared = set[idx].getRadius() * set[idx].getRadius();
			
			if( dSquared < rSquared)
				bubFound = true;
			else
				idx++;
		}
		
		if(!bubFound)
			idx = -1;
		
		return idx;
	}
	
	public int getNumberOfBubbles()
	{
		return nrBubbles;
	}
	
	public int getNrOfDoneToday()
	{
		return nrDoneToday; 
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		//draw line between open tasks canvas and done pipe
		g.setColor(lineColor);
		g.drawLine(CanvasWidth + 15, 20 , CanvasWidth + 15, CanvasHeight - 15);
		
		//draw open task bubbles
		for(int i=0; i < getNumberOfBubbles(); i++)
		{
			bubbleSet[i].drawBubble(g);
		}
		
		//draw done today tasks
		for(int i=0; i< getNrOfDoneToday(); i++)
		{
			doneTodaySet[i].drawBubble(g);
		}
	}
	
	private void setXYofBubbles()
	/*
	 * Computes the centers of the bubbles. 
	 * The algorithms fills line by line the lines with bubbles.
	 * If the line is filled, the algo switches to the next line. 
	 * 
	 * It also sets the x- / y-coordinates of the bubbles of the 
	 * tasks that have been done today. 
	 */
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
		
		// ----
		// Assembling the bubbles of tasks done today
		x = CanvasWidth + DonePipeWidth - DonePipeWOPadding / 2;
		y = CanvasHeight;
		radius = 0;
		radiusOld = 0;
		
		if(nrDoneToday > 0)
		{
			for(int i=0; i<nrDoneToday; i++)
			{
				doneTodaySet[i].x = x;
				radius = doneTodaySet[i].getRadius();
				y = y - radiusOld - radius;
				doneTodaySet[i].y = y;
				radiusOld = radius; 
			}
		}
		
	}
	
	public void updateBubbleArea(CTaskList taskList)
	{
		nrBubbles = 0;
		nrDoneToday = 0;
		for(int i = 0; i < taskList.getSize(); i++)
		{
			if(!taskList.getTask(i).getDone())
			{
				if(bubbleSet[nrBubbles] == null)
				{
					bubbleSet[nrBubbles] = new CBubble(taskList.getTask(i), i);
					nrBubbles++;
				}
				else
				{
					bubbleSet[nrBubbles].updateBubbleFromTask(taskList.getTask(i), i);
					nrBubbles++;
				}
			}
			else
			{
				today.setToToday();
				if(taskList.getTask(i).dateDone.Jahr == today.Jahr && 
						taskList.getTask(i).dateDone.Monat == today.Monat &&
						taskList.getTask(i).dateDone.Tag == today.Tag)
				{
					if(doneTodaySet[nrDoneToday] == null)
					{
						doneTodaySet[nrDoneToday] = new CBubble(taskList.getTask(i), i);
						nrDoneToday++;
					}
					else
					{
						doneTodaySet[nrDoneToday].updateBubbleFromTask(taskList.getTask(i), i);
						nrDoneToday++;
					}
				}
			}
			
			if(nrBubbles >= MAX_SET_SIZE || nrDoneToday >= MAX_SET_SIZE)
			{
				break;
			}
		}
		setXYofBubbles();
		revalidate();
		repaint();
	}
	
	class MouseWatcher extends MouseAdapter
	{
		public void mousePressed(MouseEvent e)
		{
			int mouseX = e.getX();
			int mouseY = e.getY();
			
			editBubble(mouseX, mouseY);
		}
	}
	
	
	
		
}
