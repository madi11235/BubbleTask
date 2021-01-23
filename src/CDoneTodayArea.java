
/**
 * This class describes a bubble canvas, which shows takss, that have been done today. 
 * 
 * @author Markus Dihlmann
 *
 */
public class CDoneTodayArea extends CBubbleArea{

	CDoneTodayArea(CTaskList taskList, int width, int heigth, CTaskEditFrame editFrame)
	{
		super(taskList, width, heigth, editFrame);
		
	}
	
	public void updateBubbleArea(CTaskList taskList)
	{
		today.setToToday();
		nrBubbles = 0;
		for(int i = 0; i < taskList.getSize(); i++)
		{
			if(taskList.getTask(i).dateDone.Jahr == today.Jahr && 
					taskList.getTask(i).dateDone.Monat == today.Monat &&
					taskList.getTask(i).dateDone.Tag == today.Tag)
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
