/**
 * Class CTask list
 * Summary: 
 * A list of tasks objects of the class CTask.
 * 
 * @author Markus Dihlmann
 * 
 */

import java.util.*;
import java.io.*;

/**
 * This class describes a list of tasks of class CTask. 
 * 
 * @author Markus Dihlmann
 */
public class CTaskList {
	
	
	//Attribute
	public String name;
	public CDatum dateCreation; 
	public String owner; 
	public LinkedList<CTask> taskList;
	
	
	//Constructors
	CTaskList(String name)
	{
		this.name = name;
		this.dateCreation = new CDatum();
		this.owner = "Me";
		this.taskList = new LinkedList<CTask>();
	}
	
	CTaskList(BufferedReader input)
	{
		this.name = "";
		this.dateCreation = new CDatum();
		this.taskList = new LinkedList<CTask>();
		loadTaskList(input);
	}
	
	//Methods
	public int getSize()
	{
		return taskList.size();
	}
	
	public CTask getTask(int i)
	{
		return taskList.get(i);
	}
	
	public boolean addTaskToList(CTask task)
	{
		return taskList.add(task);
	}
	
	public boolean replaceTaskInList(int ind, CTask task)
	{
		boolean ret = false;
		
		if(ind >= taskList.size())
		{
			System.out.println("Error in replacing task function. The index requested is larger than the size of the list. ");
		}
		else{
			taskList.set(ind, task);
			ret = true; 
		}
		
		return ret;
	}
	
	public void removeTaskFromList(int ind)
	{
		taskList.remove(ind);
	}
	
	public void saveTaskList(BufferedWriter output)
	{
	
		try 
		{
			output.write("---Start of tasklist---"); 
			output.newLine();
			output.write(String.format("Name:%s", this.name)); 
			output.newLine();
			output.write(String.format("Date de creation: %d\t%d\t%d\t%d", this.dateCreation.Tag, this.dateCreation.Monat, this.dateCreation.Jahr, this.dateCreation.Stunde)); 
			output.newLine();
			output.write(String.format("Owner:%s", this.owner));
			output.newLine();
			output.write("------------------");output.newLine();output.write("Tasklist");output.newLine();
			
			
			for(int i=0; i<taskList.size(); i++)
			{
				output.write(taskList.get(i).getContentAsString());
				output.newLine();
			}
			output.write("---EndOfTaskList---");
			output.newLine();
		}
		catch(IOException exep)
		{
			exep.printStackTrace();
		}
		
		
	}//end saveTaskList
	
	
	public void loadTaskList(BufferedReader input)
	{
		String str;
		int index; 
		
		System.out.println("In Ladefunktion von TaskList");
		
		//Empty list if it is filled
		if(this.taskList.size() > 0)
			this.taskList.clear();
		
		try {
			//Header lesen
			input.readLine();
			str = input.readLine();		
			index = str.indexOf(":")+1;
			this.name = str.substring(index);
			//Date de creation einlesen
			str = input.readLine();
			index = str.indexOf(":")+2;
			str = str.substring(index);
			System.out.println(str);
			
			index = str.indexOf("\t");
			this.dateCreation.Tag = Integer.parseInt(str.substring(0,index));
			str = str.substring(index+1);
			index = str.indexOf("\t");
			this.dateCreation.Monat = Integer.parseInt(str.substring(0,index));
			str = str.substring(index+1);
			index = str.indexOf("\t");
			this.dateCreation.Jahr = Integer.parseInt(str.substring(0,index));
			str = str.substring(index+1);
			this.dateCreation.Stunde = Integer.parseInt(str);
			
			str = input.readLine();
			index = str.indexOf(":")+1;
			this.owner = str.substring(index);
			
			//Read in the tasks
			int idx=0;
			str = input.readLine();
			str = input.readLine();
			str = input.readLine();
			System.out.println("Header eingelesen...");
			System.out.println(str);
			while(!(str.substring(0,19).equals(("---EndOfTaskList---").substring(0,19))))
			{
				System.out.println(idx); idx++;
				CTask task = new CTask(str);
				this.taskList.add(task);
				str = input.readLine();
			}
			System.out.println("end of task list reached");
			
		}
		catch(IOException exep)
		{
			exep.printStackTrace();
			System.out.println("Fehler ...putain!");
		}
	}
	
	/**
	 * This method returns the list of tasks as a double array of strings. This is a format 
	 * of the task list which can be used for exporting/saving of the tasks. 
	 * 
	 * @return String[][] String double array; first entry task index; second index is the task property
	 */
	public String[][] getListAsStringArray()
	{
		String[][] Slist;
		if(taskList.size() > 0)
		{
			// number of fields per task
			String[] fieldNames = taskList.get(0).getFieldNames();
			//set up and fill list
			Slist = new String[taskList.size()+1][fieldNames.length];
			Slist[0] = fieldNames;
			for(int i = 0; i < taskList.size(); i++)
			{
				Slist[i+1] = taskList.get(i).getTaskAsStringArray();
			}
		}
		else
		{Slist = new String[0][0];}
		
		return Slist;
	}
	
	/**
	 * Sort the task list according to priorities. 
	 */
	public void sortPriority()
	{
		Collections.sort(taskList, new CTaskPriorityComparator());
	}
	
	/**
	 * Sort the task list according to due dates.
	 */
	public void sortDueDate()
	{
		Collections.sort(taskList, new CTaskDueDateComparator());
	}
	
	/**
	 * This method updated all tesks in the list w.r.t.:
	 * - priority re-computation
	 * - TODO: grooming activation if task is over due
	 */
	public void updateAllTasks()
	{
		for(int i=0; i< taskList.size(); i++)
		{
			getTask(i).updatePriority();
			if(getTask(i).getAssignee().equals(this.owner))
			{
				getTask(i).assignedToMe = true; 
			}
			else
			{
				getTask(i).assignedToMe = false; 
			}
		}
	}
	
	/**
	 * This method goes through all the tasks in the task list
	 * and updates the due date to today in case the task is not yet completed and
	 * overdue. 
	 */
	public void autoGroomAllTasks()
	{
		CDatum today = new CDatum();
		
		for(int i=0; i< taskList.size(); i++)
		{
			if(!getTask(i).getDone() && getTask(i).assignedToMe)
			{
				if(getTask(i).computeRemainingTimeTillDate(today) < 0)
				{
					getTask(i).setDueDate(today);
				}
				getTask(i).updatePriority();	
			}
			
		}
	}
	
	/**
	 * Assuming that the name of the user/owner of the task list has changed, e.g. by 
	 * setting the preferencec, this function allows to update the names 
	 * of the assignee to all the tasks assigned to the owner of the task list
	 * 
	 * @param ownerName
	 */
	public void adaptOwnerNameOfMyTasks(String ownerName)
	{
		if(ownerName.length()>0)
		{
			for (int i=0; i<taskList.size();i++)
			{
				if(getTask(i).assignedToMe)
				{
					getTask(i).setAssignee(ownerName);
				}
			}
		}
	}
	
}
