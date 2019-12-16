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
			
			//Read in the tasks
			int idx=0;
			str = input.readLine();
			str = input.readLine();
			str = input.readLine();
			System.out.println("Header ingelesen...");
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
	
}
