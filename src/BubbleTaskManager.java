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
	CDatum today; 
	String[] SImpactLevels = {"Low", "Medium", "High", "Very High"};
	
	File datei; 
	FileWriter fWriter;
	BufferedWriter bWriter; 
	FileReader fReader;
	BufferedReader bReader;
	
	//Attribute Maske
	JFrame WFrame; 
	JButton JBnewTask;
	JTextField JTFnewTask;
	
	JFrame WTaskFrame; 
	JTextField JTFDescription;
	JTextArea JTANotes;
	JTextField JTFAssignee, JTFDueDay, JTFDueMonth, JTFDueYear;
	JCheckBox JCBcustomerReq, JCBcustomerCall, JCBinterfaceOthers;
	JComboBox JComboImpact = new JComboBox(SImpactLevels);
	JComboBox JComboComplexity = new JComboBox(SImpactLevels);
	JButton JBTaskOK, JBTaskCancel;
	
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
	
	
	public void initialize()
	{
		today = new CDatum();
		
		/*****************
		 * Start screen
		 *****************/
		
		WFrame = new JFrame("Bubble Tasks");
		WFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		WFrame.setLayout(null);
		WFrame.setSize(800, 600);
		WFrame.setLocation(50, 50);
		
		JLabel JLnewTask = new JLabel("New Task:"); 
		JLnewTask.setBounds(50, 50, 100, 30);
		JLnewTask.setHorizontalAlignment(JLabel.RIGHT);
		WFrame.add(JLnewTask);
		
		JTFnewTask = new JTextField();
		JTFnewTask.setBounds(150, 50, 425, 30);
		WFrame.add(JTFnewTask);
		
		JBnewTask = new JButton("Add Task");
		JBnewTask.setBounds(600, 50, 150, 30);
		WFrame.add(JBnewTask);
		
		WFrame.setVisible(true);
		
		/*********************
		 * New Task screen
		 */
		
		WTaskFrame = new JFrame("Task");
		WTaskFrame.setLayout(null);
		WTaskFrame.setSize(1000, 550);
		WTaskFrame.setLocation(100, 100);
		
		JLabel JLTaskDescription = new JLabel("Task Description:"); 
		JLTaskDescription.setBounds(50, 50, 200, 30);
		JLTaskDescription.setHorizontalAlignment(JLabel.RIGHT);
		WTaskFrame.add(JLTaskDescription);
		
		JTFDescription = new JTextField();
		JTFDescription.setBounds(280,50,650,30);
		WTaskFrame.add(JTFDescription);
		
		JLabel JLNotes = new JLabel("Notes:");
		JLNotes.setBounds(600, 100, 50, 30);
		JLNotes.setHorizontalAlignment(JLabel.RIGHT);
		WTaskFrame.add(JLNotes);
		
		JTANotes = new JTextArea();
		JScrollPane JscrolP = new JScrollPane(JTANotes);
		JscrolP.setBounds(680, 100, 250, 300);
		JscrolP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		JscrolP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);;
		WTaskFrame.add(JscrolP);
		
		JLabel JLAssignee = new JLabel("Assignee:");
		JLAssignee.setBounds(50, 100, 200, 30);
		JLAssignee.setHorizontalAlignment(JLabel.RIGHT);
		WTaskFrame.add(JLAssignee);
		
		JTFAssignee = new JTextField("Markus");
		JTFAssignee.setBounds(280, 100, 250, 30);
		WTaskFrame.add(JTFAssignee);
		
		JLabel JLDueDate = new JLabel("Due Date:");
		JLDueDate.setBounds(50, 150, 200, 30);
		JLDueDate.setHorizontalAlignment(JLabel.RIGHT);
		WTaskFrame.add(JLDueDate);
		
		JTFDueDay = new JTextField(String.valueOf(today.Tag));
		JTFDueDay.setBounds(280,150,50,30);
		WTaskFrame.add(JTFDueDay);
		JTFDueMonth = new JTextField(String.valueOf(today.Monat));
		JTFDueMonth.setBounds(340,150,50,30);
		WTaskFrame.add(JTFDueMonth);
		JTFDueYear = new JTextField(String.valueOf(today.Jahr));
		JTFDueYear.setBounds(400,150,80,30);
		WTaskFrame.add(JTFDueYear);
		
		JLabel JLCustReq = new JLabel("Direct Customer request:");
		JLCustReq.setBounds(50,200,200,30);
		JLCustReq.setHorizontalAlignment(JLabel.RIGHT);
		WTaskFrame.add(JLCustReq);
		
		JCBcustomerReq = new JCheckBox();
		JCBcustomerReq.setBounds(280, 200, 30,30);
		WTaskFrame.add(JCBcustomerReq);
		
		JLabel JLCustCall = new JLabel("Topic for customer call:");
		JLCustCall.setBounds(50,250,200,30);
		JLCustCall.setHorizontalAlignment(JLabel.RIGHT);
		WTaskFrame.add(JLCustCall);
		
		JCBcustomerCall = new JCheckBox();
		JCBcustomerCall.setBounds(280,250,30,30);
		WTaskFrame.add(JCBcustomerCall);
		
		JLabel JLInvOthers = new JLabel("Involves others:");
		JLInvOthers.setBounds(50,300,200,30);
		JLInvOthers.setHorizontalAlignment(JLabel.RIGHT);
		WTaskFrame.add(JLInvOthers);
		
		JCBinterfaceOthers = new JCheckBox();
		JCBinterfaceOthers.setBounds(280, 300,30,30);
		WTaskFrame.add(JCBinterfaceOthers);
		
		JLabel JLProjImp = new JLabel("Project Impact:");
		JLProjImp.setBounds(50,350,200,30);
		JLProjImp.setHorizontalAlignment(JLabel.RIGHT);
		WTaskFrame.add(JLProjImp);
		
		JComboImpact.setBounds(280,350,200,30);
		WTaskFrame.add(JComboImpact);
		
		JLabel JLComplexity = new JLabel("Complexity");
		JLComplexity.setBounds(50,400,200,30);
		JLComplexity.setHorizontalAlignment(JLabel.RIGHT);
		WTaskFrame.add(JLComplexity);
		
		JComboComplexity.setBounds(280,400,200,30);
		WTaskFrame.add(JComboComplexity);
		
		JBTaskOK = new JButton("Add Task");
		JBTaskOK.setBounds(600, 450, 150,50);
		WTaskFrame.add(JBTaskOK);
		
		JBTaskCancel = new JButton("Cancel");
		JBTaskCancel.setBounds(800,450,150,50);
		WTaskFrame.add(JBTaskCancel);
		
		WTaskFrame.setVisible(true);
		
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
		
		
		//initialize
		taskMan.initialize();
		
	}

}
