/*
 * Main Class for the bubble task task manager
 */

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BubbleTaskManager implements ActionListener {

	//constants
	public static final int CANVAS_WIDTH = 700;
	public static final int CANVAS_HEIGHT = 500; 
	
	//Attribute
	CTaskList taskList;
	CDatum today; 
	
	File datei; 
	FileWriter fWriter;
	BufferedWriter bWriter; 
	FileReader fReader;
	BufferedReader bReader;
	
	CTableView TableView; 
	
	//Attribute Maske
	JFrame WFrame; 
	JButton JBnewTask, JBquickAdd;
	JTextField JTFnewTask;
	
	CTaskEditFrame WTaskFrame; 
	CTaskEditFrame WEditTaskFrame;
	CDeleteConfirmFrame WDelConfFrame;
	
	JMenuBar MenuBar;
	JMenu MenuView, MenuEdit;
	JMenuItem MenuTableView, MenuGroomAuto;
	
	CBubbleArea bubbleArea;
	
	//Konstruktor
	BubbleTaskManager()
	{
		datei = new File("Tasks.tsk");
	}
	
	
	public void actionPerformed(ActionEvent e)
	{
		String command;
		command = e.getActionCommand();
		
		switch (command) 
		{
		case "BNewTask": //neue Aufgabe anlegen
			WTaskFrame.openEmptyTaskWindow(JTFnewTask.getText());
			JTFnewTask.setText("");
			break;
		case "BquickAdd": //neue Aufgabe with default values
			addNewTaskDefault(JTFnewTask.getText());
			TableView.taskList = this.taskList;
			JTFnewTask.requestFocus();
			JTFnewTask.setText("");
			break;
		case "BTaskAdd": //neue Aufgabe aus Task Window hinzufügen
			addNewTaskWithDetails();
			TableView.taskList = this.taskList;
			WTaskFrame.WTaskFrame.setVisible(false);
			JTFnewTask.requestFocus();
			break;
		case "BTaskCancel": //Cancel button pressed in new task window
			WTaskFrame.WTaskFrame.setVisible(false);
			WTaskFrame.resetTaskFrame();
			JTFnewTask.requestFocus();
			break;
		case "OpenTableView":
			TableView.showTable(this.taskList);
			break;
		case "StartAutoGroom":
			System.out.println("Auto groom clicked");
			this.taskList.autoGroomAllTasks();
			break;
		case "BTaskSubmitEdit": //submit the edited task
			CTask tsk = WEditTaskFrame.getTaskFromEditFrame(taskList.getTask(WEditTaskFrame.taskIndex));
			tsk.dateModification.setToToday();
			replaceTaskInList(WEditTaskFrame.taskIndex, tsk);
			TableView.taskList = this.taskList;
			WEditTaskFrame.WTaskFrame.setVisible(false);
			JTFnewTask.requestFocus();
			break;
		case "BEditCancel": //Cancel Task editing
			WEditTaskFrame.WTaskFrame.setVisible(false);
			WEditTaskFrame.resetTaskFrame();
			JTFnewTask.requestFocus();
			break;
		case "BConfDelete": //confirm deletion
			deleteTask(WDelConfFrame.taskIndex);
			WDelConfFrame.WConfirmFrame.setVisible(false);
			JTFnewTask.requestFocus();
			break;
		case "BcancelDelete": //cancel deletion
			WDelConfFrame.WConfirmFrame.setVisible(false);
			break;
		case "CheckDone":
			CTask aufg = WEditTaskFrame.getTaskFromEditFrame(taskList.getTask(WEditTaskFrame.taskIndex));
			aufg.dateModification.setToToday();
			aufg.setDone(WEditTaskFrame.JCBdone.isSelected());
			replaceTaskInList(WEditTaskFrame.taskIndex, aufg);
			TableView.taskList = this.taskList;
			break;
		}
		//After each action, we update and save the new task list
		taskList.updateAllTasks();
		taskList.sortPriority();
		taskList.sortDueDate();
		bubbleArea.updateBubbleArea(taskList);
		saveTasks();
		for(int i=0; i<taskList.getSize();i++)
		{
			System.out.println("Task " + String.valueOf(i) + "due Year" + String.valueOf(taskList.getTask(i).getDueDate().Jahr)); 
		}
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
		CTaskList tl = new CTaskList("Neue Task List");
		
		if(datei.exists() && !datei.isDirectory())
		{
			try
			{
				fReader = new FileReader(datei);
				bReader = new BufferedReader(fReader);
				
				//Load Data	
				tl = new CTaskList(bReader);
			
			}
			catch(IOException ex)
			{
				ex.printStackTrace();
				System.out.println("File not found");
			}
		
			try
			{
				bReader.close();
				fReader.close();
			}	
			catch(IOException ex)
			{
				ex.printStackTrace();
			}
		}
		
		return tl;
	}
	
	
	public void initialize()
	{
		today = new CDatum();
		
		taskList = loadTasks();
		System.out.println("Task list loaded:");
		System.out.println("Name: "+taskList.name);
		System.out.println(String.format("Datum: %d - %d - %d: %d ", taskList.dateCreation.Tag, taskList.dateCreation.Monat, taskList.dateCreation.Jahr, taskList.dateCreation.Stunde));
		
		//update priorities (with current date)
		taskList.sortDueDate();
		taskList.updateAllTasks();
		
		/*****************
		 * Start screen
		 *****************/
		
		WFrame = new JFrame("Bubble Tasks");
		WFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		WFrame.setLayout(null);
		WFrame.setSize(1000, 800);
		WFrame.setLocation(50, 50);
		
		JLabel JLnewTask = new JLabel("New Task:"); 
		JLnewTask.setBounds(50, 50, 100, 30);
		JLnewTask.setHorizontalAlignment(JLabel.RIGHT);
		WFrame.add(JLnewTask);
		
		JTFnewTask = new JTextField();
		JTFnewTask.setBounds(150, 50, 425, 30);
		JTFnewTask.requestFocus();
		WFrame.add(JTFnewTask);
		
		JBnewTask = new JButton("Add Task");
		JBnewTask.setBounds(600, 50, 120, 30);
		JBnewTask.addActionListener(this);
		JBnewTask.setActionCommand("BNewTask");
		WFrame.add(JBnewTask);
		
		JBquickAdd = new JButton("Quick Add");
		JBquickAdd.setBounds(750, 50, 120, 30);
		JBquickAdd.addActionListener(this);
		JBquickAdd.setActionCommand("BquickAdd");
		WFrame.add(JBquickAdd);
		
		//Menu bar
		MenuBar = new JMenuBar();
		
		MenuView = new JMenu("View");
		MenuView.getAccessibleContext().setAccessibleDescription("View related menue items");
		MenuBar.add(MenuView); 
		
		MenuTableView = new JMenuItem("TableView");
		MenuTableView.addActionListener(this);
		MenuTableView.setActionCommand("OpenTableView");
		MenuView.add(MenuTableView);
		
		MenuEdit = new JMenu("Edit");
		MenuEdit.getAccessibleContext().setAccessibleDescription("Editing, performing actions");
		MenuBar.add(MenuEdit);
		
		
		MenuGroomAuto = new JMenuItem("Auto Groom");
		MenuGroomAuto.addActionListener(this);
		MenuGroomAuto.setActionCommand("StartAutoGroom");
		MenuEdit.add(MenuGroomAuto);
		
		WFrame.setJMenuBar(MenuBar);
		
		WFrame.setVisible(true);
		
		/*********************
		 * New Task screen
		 */
		WTaskFrame = new CTaskEditFrame("Task");
		
		WTaskFrame.JBTaskOK.addActionListener(this);
		WTaskFrame.JBTaskOK.setActionCommand("BTaskAdd");
		
		WTaskFrame.JBTaskCancel.addActionListener(this);
		WTaskFrame.JBTaskCancel.setActionCommand("BTaskCancel");
		
		/**********************
		 *  Edit Task screen
		 */
		WEditTaskFrame = new CTaskEditFrame("Edit Task");
		
		WEditTaskFrame.JBTaskOK.addActionListener(this);
		WEditTaskFrame.JBTaskOK.setActionCommand("BTaskSubmitEdit");
		
		WEditTaskFrame.JBTaskCancel.addActionListener(this);
		WEditTaskFrame.JBTaskCancel.setActionCommand("BEditCancel");
		
		WEditTaskFrame.JCBdone.addActionListener(this);
		WEditTaskFrame.JCBdone.setActionCommand("CheckDone");
		
		/********************
		 * Deletion confirm frame set up
		 */
		WDelConfFrame = new CDeleteConfirmFrame();
		
		WDelConfFrame.JBok.addActionListener(this);
		WDelConfFrame.JBok.setActionCommand("BConfDelete");
		
		WDelConfFrame.JBcancel.addActionListener(this);
		WDelConfFrame.JBcancel.setActionCommand("BcancelDelete");
		
		/***********************
		 * Prepare table view
		 */
		TableView = new CTableView(taskList, WEditTaskFrame, WDelConfFrame);
		
		/*******************
		 * Set up bubble canvas
		 */
		bubbleArea = new CBubbleArea(taskList, CANVAS_WIDTH, CANVAS_HEIGHT, WEditTaskFrame);
		bubbleArea.setBounds(80, 150, CANVAS_WIDTH, CANVAS_HEIGHT);
		WFrame.add(bubbleArea);
		
		
		//TODO: re-evaluate the need for grooming
		
		
	}
	
	
	public void addNewTaskDefault(String Description)
	/*
	 * Quickly adds a new task with default values. 
	 * Description is taken from the description line. 
	 */
	{
		CTask task = new CTask();
		task.setDescription(Description);
		taskList.addTaskToList(task);
	}
	
	public void addNewTaskWithDetails()
	/*
	 * Adds a new task, taking the information given in the 
	 * task editing window.
	 */
	{
		CTask task = WTaskFrame.getTaskFromFrame();
		taskList.addTaskToList(task);
	}
	
	public void replaceTaskInList(int index, CTask task)
	/*
	 * Replaces a task in the list of task with the edited version of the task 
	 */
	{
		taskList.replaceTaskInList(index, task);
	}
	
	public void deleteTask(int index)
	/*
	 * Delet a task from the task list at the given index
	 */
	{
		taskList.removeTaskFromList(index);
	}
	
	public static void main(String[] args) {
		
		System.out.println("Willkommen zum Bubble Task Manager");
		
		/*
		CTask Aufgabe1 = new CTask();
		Aufgabe1.setDescription("Aufgabe nr.1");
		CTask Aufgabe2 = new CTask();
		Aufgabe2.setDescription("Aufgabe nr.2");
		*/
		
		BubbleTaskManager taskMan = new BubbleTaskManager();
		
		/*
		taskMan.taskList.addTaskToList(Aufgabe1);
		taskMan.taskList.addTaskToList(Aufgabe2);
		System.out.println("Groesse der Aufgabenliste: " + taskMan.taskList.getSize());
		
		for (int i=0; i<taskMan.taskList.getSize(); i++)
		{
			CTask Aufg = taskMan.taskList.getTask(i);
			System.out.println("Beschreibung von Task nr. " + i + " ist: " + Aufg.getDescription());
			System.out.println(Aufg.getContentAsString());
		}
		*/
		//System.out.println("Speichere Liste");
		//taskMan.saveTasks();
		
		
		//checke dass laden und schreiben korrekt funktionieren
		/*String str1 = Aufgabe1.getContentAsString();
		String str2 = tl.getTask(0).getContentAsString();
		if(str1.equals(str2))
			System.out.println("Speichern und laden funktioniert");
		else
		{
			System.out.println("Speichern und laden funktionieren leider nicht....");
			System.out.println(str1);
			System.out.println(str2);
		}
		*/
		
		//initialize
		taskMan.initialize();
		
	}

}
