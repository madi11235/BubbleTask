import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;


public class CMyTaskArea extends CBubbleArea {
		
	
	CMyTaskArea(CTaskList taskList, int width, int heigth, CTaskEditFrame editFrame)
	{
		super(taskList, width, heigth, editFrame);
		
	}
	
	
		
	
	
	public void updateBubbleArea(CTaskList taskList)
	{
		nrBubbles = 0;
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
			
			
			if(nrBubbles >= MAX_SET_SIZE)
			{
				break;
			}
		}
		setXYofBubbles();
		revalidate();
		repaint();
	}
	


}
