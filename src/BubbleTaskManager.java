/*
 * Main Class for the bubble task task manager
 */

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BubbleTaskManager implements ActionListener {

	//Attribute
	CTaskList taskList;
	CDatum today; 
	String[] SImpactLevels = {"Low", "Medium", "High", "Very High"};
	
	File datei; 
	FileWriter fWriter;
	BufferedWriter bWriter; 
	FileReader fReader;
	BufferedReader bReader;
	
	CTableView TableView = new CTableView();;
	
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
	
	JMenuBar MenuBar;
	JMenu MenuView;
	JMenuItem MenuTableView;
	
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
		//TODO: Wann speichern wir die veränderte Task Liste ? (nach jeder Action?)
		case "BNewTask": //neue Aufgabe anlegen
			openEmptyTaskWindow(JTFnewTask.getText());
			break;
		case "BTaskAdd": //neue Aufgabe aus Task Window hinzufügen
			addNewTaskWithDetails();
			TableView.taskList = this.taskList;
			WTaskFrame.setVisible(false);
			break;
		case "BTaskCancel": //Cancel button pressed in new task window
			WTaskFrame.setVisible(false);
			resetTaskFrame();
			break;
		case "OpenTableView":
			TableView.showTable(this.taskList);
			break;
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
		JBnewTask.addActionListener(this);
		JBnewTask.setActionCommand("BNewTask");
		WFrame.add(JBnewTask);
		
		//Menu bar
		MenuBar = new JMenuBar();
		
		MenuView = new JMenu("View");
		MenuView.getAccessibleContext().setAccessibleDescription("View related menue items");
		MenuBar.add(MenuView); 
		
		MenuTableView = new JMenuItem("TableView");
		MenuTableView.addActionListener(this);
		MenuTableView.setActionCommand("OpenTableView");
		MenuView.add(MenuTableView);
		
		WFrame.setJMenuBar(MenuBar);
		
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
		JBTaskOK.addActionListener(this);
		JBTaskOK.setActionCommand("BTaskAdd");
		WTaskFrame.add(JBTaskOK);
		
		JBTaskCancel = new JButton("Cancel");
		JBTaskCancel.setBounds(800,450,150,50);
		JBTaskCancel.addActionListener(this);
		JBTaskCancel.setActionCommand("BTaskCancel");
		WTaskFrame.add(JBTaskCancel);
	}
	
	public void resetTaskFrame()
	/*
	 * resets the task Edit / new task window to default values
	 */
	{
		JTFDescription.setText("");
		JTANotes.setText("");
		JTFAssignee.setText("Markus");
		
		JTFDueDay.setText(String.valueOf(today.Tag));
		JTFDueMonth.setText(String.valueOf(today.Monat));
		JTFDueYear.setText(String.valueOf(today.Jahr));
		
		JCBcustomerReq.setSelected(false);
		JCBcustomerCall.setSelected(false);
		JCBinterfaceOthers.setSelected(false);
		
		JComboImpact.setSelectedIndex(-1);
		JComboComplexity.setSelectedIndex(-1);
	}
	
	public void openEmptyTaskWindow(String descriptionText)
	/*
	 * Opens the empty task window for adding a new task
	 * If a description is available, it fills out the description text in the new task frame. 
	 */
	{
		resetTaskFrame();
		JTFDescription.setText(descriptionText);
		WTaskFrame.setVisible(true);
	}
	
	public void addNewTaskWithDetails()
	/*
	 * Adds a new task, taking the information given in the 
	 * task editing window.
	 */
	{
		CTask task = new CTask();
		task.setDescription(JTFDescription.getText());
		task.setNotes(JTANotes.getText());
		task.setAssignee(JTFAssignee.getText());
		task.dateDue.Tag = Integer.parseInt(JTFDueDay.getText());
		task.dateDue.Monat = Integer.parseInt(JTFDueMonth.getText());
		task.dateDue.Jahr = Integer.parseInt(JTFDueYear.getText());
		task.customerRequest = JCBcustomerReq.isSelected();
		task.topicForCustomerCall = JCBcustomerCall.isSelected();
		task.setInvolveOthers(JCBinterfaceOthers.isSelected());
		switch (JComboImpact.getSelectedIndex())
		{
		case 0:
			task.projectImpact = CTask.ImpactLevel.LOW;
			break;
		case 1:
			task.projectImpact = CTask.ImpactLevel.MEDIUM;
			break;
		case 2:
			task.projectImpact = CTask.ImpactLevel.HIGH;
			break; 
		case 3: 
			task.projectImpact = CTask.ImpactLevel.VERY_HIGH;
			break;
		default:
			task.projectImpact = CTask.ImpactLevel.LOW;
			break;
		}
		switch (JComboComplexity.getSelectedIndex())
		{
		case 0:
			task.complexity = CTask.ImpactLevel.LOW;
			break;
		case 1:
			task.complexity = CTask.ImpactLevel.MEDIUM;
			break;
		case 2:
			task.complexity = CTask.ImpactLevel.HIGH;
			break; 
		case 3: 
			task.complexity = CTask.ImpactLevel.VERY_HIGH;
			break;
		default:
			task.complexity = CTask.ImpactLevel.LOW;
			break;
		}
		task.dateCreation.Jahr = today.Jahr;
		task.dateCreation.Monat = today.Monat;
		task.dateCreation.Tag = today.Tag;
		task.dateCreation.Stunde = today.Stunde;
		
		task.dateModification.Jahr = today.Jahr;
		task.dateModification.Monat = today.Monat;
		task.dateModification.Tag = today.Tag;
		task.dateModification.Stunde = today.Stunde;
		
		taskList.addTaskToList(task);
		WTaskFrame.setVisible(false);
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
