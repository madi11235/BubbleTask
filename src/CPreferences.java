
/**
 * This class describes the settings and options you can set as a user to customize the ToDo-list
 * 
 * @author Markus Dihlmann
 *
 */

public class CPreferences {
	
	String owner; // The owner of the task list

	CPreferences(CTaskList tl)
	{
		this.owner = tl.owner;
	}
	
	
}
