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
	
	public CTaskList loadTasks()
	{
		try{
			
			fReader = new FileReader(datei);
			bReader = new BufferedReader(fReader);
			
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
			System.out.println("File not found");
		}
		
		//Load Data	
		CTaskList tl = new CTaskList(bReader);
	
		try
		{
			bReader.close();
			fReader.close();
		}	
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		return tl;
	}
	
	
	
	public static void main(String[] args) {
		
		System.out.println("Willkommen zum Bubble Task Manager");
		
		CTask Aufgabe1 = new CTask();
		Aufgabe1.setDescription("Aufgabe nr.1");
		CTask Aufgabe2 = new CTask();
		Aufgabe2.setDescription("Aufgabe nr.2");
		
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
		
		CTaskList tl = taskMan.loadTasks();;
		System.out.println("Task list loaded:");
		System.out.println("Name: "+tl.name);
		System.out.println(String.format("Datum: %d - %d - %d: %d ", tl.dateCreation.Tag, tl.dateCreation.Monat, tl.dateCreation.Jahr, tl.dateCreation.Stunde));
		
		//checke dass laden und schreiben korrekt funktionieren
		String str1 = Aufgabe1.getContentAsString();
		String str2 = tl.getTask(0).getContentAsString();
		if(str1.equals(str2))
			System.out.println("Speichern und laden funktioniert");
		else
		{
			System.out.println("Speichern und laden funktionieren leider nicht....");
			System.out.println(str1);
			System.out.println(str2);
		}
		
	}

}
