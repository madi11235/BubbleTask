/*
 * Main Class for the bubble task task manager
 */

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BubbleTaskManager {

	//Attribute
	CTaskList taskList;
	
	File datei; 
	FileWriter fWriter;
	BufferedWriter bWriter; 
	FileReader fReader;
	BufferedReader bReader;
	
	//Konstruktor
	BubbleTaskManager()
	{
		datei = new File("Tasks.tsk");
	}
	
	//Methoden
	public void saveTasks()
	{

	    try{
				fWriter = new FileWriter(datei);
				bWriter = new BufferedWriter(fWriter);	
			}
			
			catch(IOException ex)
			{
				ex.printStackTrace();
				System.out.println("File not found");
			}
			
			//save the tasklist
			taskList.saveTaskList(bWriter);
			
			try
			{
				bWriter.close();
				fWriter.close();
			}	
			catch(IOException ex)
			{
				ex.printStackTrace();
			}
			
	}//end save tasks
	
	public void loadTasks()
	{
		
	}
	
	
	
	public static void main(String[] args) {
		
		System.out.println("Willkommen zum Bubble Task Manager");
		
		CTask Aufgabe1 = new CTask("Aufgabe 1");
		CTask Aufgabe2 = new CTask("Aufgabe 2");
		
		BubbleTaskManager taskMan = new BubbleTaskManager();
		
		taskMan.taskList = new CTaskList("Meine Aufgabenliste");
		taskMan.taskList.addTaskToList(Aufgabe1);
		taskMan.taskList.addTaskToList(Aufgabe2);
		System.out.println("Groesse der Aufgabenliste: " + taskMan.taskList.getSize());
		
		
		for (int i=0; i<taskMan.taskList.getSize(); i++)
		{
			CTask Aufg = taskMan.taskList.getTask(i);
			System.out.println("Beschreibung von Task nr. " + i + " ist: " + Aufg.getDescription());
			System.out.println(Aufg.getContentAsString());
		}
		
		System.out.println("Speichere Liste");
		taskMan.saveTasks();
		
	}

}
