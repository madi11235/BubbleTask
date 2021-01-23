
/**
 * This class describes a Bubble area, which shows only tasks assigned to other people. 
 * 
 * @author Markus Dihlmann
 *
 */
public class CDelegateArea extends CBubbleArea {
	
	CDelegateArea(CTaskList taskList, int width, int heigth, CTaskEditFrame editFrame)
	{
		super(taskList, width, heigth, editFrame);
		
	}
	
	/**
	 * updateBubbleArea takes all tasks, which are not yet done and which are assigned to other people
	 * into the bubbleSet of this canvas.
	 */
	public void updateBubbleArea(CTaskList taskList)
	{
		nrBubbles = 0;
		for(int i = 0; i < taskList.getSize(); i++)
		{
			if(!taskList.getTask(i).getDone() && !taskList.getTask(i).getAssignee().equals("Markus"))
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
