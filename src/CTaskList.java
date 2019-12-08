/*
 * Class CTask list
 * Summary: 
 * A list of tasks objects of the class CTask.
 */

import java.util.*;
import java.io.*;

public class CTaskList {

	//Attribute
	public String name;
	public CDatum dateCreation; 
	public LinkedList<CTask> taskList;
	
	
	//Constructors
	CTaskList(String name)
	{
		this.name = name;
		this.dateCreation = new CDatum();
		this.taskList = new LinkedList<CTask>();
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
	
	public void saveTaskList(BufferedWriter output)
	{
	
		try 
		{
			output.write("---Start of tasklist---"); output.newLine();
			output.write(String.format("Name: %s", this.name)); output.newLine();
			output.write(String.format("Date de creation: %d\t%d\t%d\t%d", this.dateCreation.Tag, this.dateCreation.Monat, this.dateCreation.Jahr, this.dateCreation.Stunde)); output.newLine();
			output.newLine();output.write("------------------");output.newLine();output.write("Tasklist");output.newLine();
			
			
			//Vokabelkarten nacheinander abspeichern ...getrennt durch tabulatoren
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
	
	/*
	public CTaskList loadTaskLIst(BufferedReader input)
	{
		
	}
	*/
	
}
