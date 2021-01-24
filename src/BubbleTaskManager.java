/**
 * BubbleTask is a todo list manager helping you to track your tasks and to prioritize them for you. 
 * 
 * 
 * 
 */

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Main Class for the bubble task task manager
 *
 * 
 * This class contains the main function for running the program. 
 * 
 * Furthermore, the main actions to manage the tasks are implemented here, e.g.
 * saveTasks, loadTasks, deleteTask, addNewTaskDefault, etc.
 * 
 * @author Markus Dihlmann
 * @since 2021-01-02
 */
public class BubbleTaskManager implements ActionListener {

	//constants
	public static final int CANVAS_WIDTH = 800;
	public static final int CANVAS_HEIGHT = 1500; 
	public static final int SCROLL_HEIGHT = 370;
	public static final int SCROLL_WIDTH = CANVAS_WIDTH + 20;
	
	public static final int DONE_AREA_HEIGHT = 121;
	
	public static final int DEL_AREA_WIDTH = 150;
	
	
	//Attribute
	CTaskList taskList;
	CDatum today; 
	
	File datei; 
	FileWriter fWriter;
	BufferedWriter bWriter; 
	FileReader fReader;
	BufferedReader bReader;
	
	File logFile;
	LogBook log; 
	
	CTableView TableView; 
	
	CPreferences preferences; 
	
	//Attribute Mask
	JFrame WFrame; 
	JButton JBnewTask, JBquickAdd;
	JTextField JTFnewTask;
	
	CTaskEditFrame WTaskFrame; 
	CTaskEditFrame WEditTaskFrame;
	CDeleteConfirmFrame WDelConfFrame;
	LogInterface WLogInterface;
	CPreferencesFrame WPrefFrame; 
	
	JMenuBar MenuBar;
	JMenu MenuView, MenuEdit, MenuLog;
	JMenuItem MenuTableView, MenuGroomAuto, MenuSetPref, MenuLogOpen;
	
	CMyTaskArea bubbleArea;
	CDoneTodayArea doneArea;
	CDelegateArea delegateArea;
	JScrollPane scrollPane, scrollPaneDone, scrollPaneDel; 
	JPanel Vpanel;
	
	//Constructor
	BubbleTaskManager()
	{
		datei = new File("Tasks.tsk");
	}
	
	
	/**
	 * This method is the central place to handle all actions, which are performed. 
	 * For example, if a new task is created and the "OK" button is pressed, this launches activities being
	 * defined in this method. 
	 * 
	 * @param e general action event e
	 * 
	 */
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
			this.taskList.autoGroomAllTasks();
			break;
		case "OpenLog":
			WLogInterface.Frame.setVisible(true);
			break;
		case "BTaskSubmitEdit": //submit the edited task
			CTask tsk = new CTask(taskList.getTask(WEditTaskFrame.taskIndex));
			tsk = WEditTaskFrame.getTaskFromEditFrame(tsk);
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
		case "BDeleteTask": //Delete Task button is pressed
			WDelConfFrame.openConfirmView(WEditTaskFrame.taskIndex, WEditTaskFrame.getTaskFromFrame().getDescription());
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
		case "openPrefSetting":
			WPrefFrame.openPreferencesFrame();
			break;
		case "preferencesConfirmed":
			//Click on the OK button in the Preferences window
			preferences = WPrefFrame.getPreferences();
			updatePreferences();
			WPrefFrame.closePreferencesFrame();
			break;
		case "preferencesCanceled":
			WPrefFrame.closePreferencesFrame();
			break;
		}
		//After each action, we update and save the new task list
		taskList.updateAllTasks();
		taskList.sortPriority();
		taskList.sortDueDate();
		bubbleArea.updateBubbleArea(taskList);
		doneArea.updateBubbleArea(taskList);
		delegateArea.updateBubbleArea(taskList);
		saveTasks();
		for(int i=0; i<taskList.getSize();i++)
		{
			System.out.println("Task " + String.valueOf(i) + "due Year" + String.valueOf(taskList.getTask(i).getDueDate().Jahr)); 
		}
	}
	
	//Methods
	
	/**
	 * This method saves the tasks to a standard file. 
	 * This method does not consume much computation time. Hence, it can be called rather often 
	 * when tasks are updated by the user. 
	 * The user does not necessarily need to trigger the saving himself. It is rather done automatically in the background.  
	 * 
	 */
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
	
	/**
	 * When starting up the program, the tasks are loaded from a file using this function. 
	 * 
	 */
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
	
	/**
	 * This is a very central method called when starting the program. 
	 * It instantiates all relevant object, loads the task list from file, and initializes all settings. 
	 * 
	 */
	public void initialize()
	{
	
		today = new CDatum();
		
		taskList = loadTasks();
		
		//update priorities (with current date)
		taskList.sortDueDate();
		taskList.updateAllTasks();
		
		//initialize preferences
		preferences = new CPreferences(taskList);
		
		//initialize logBook
		logFile = new File("LogBook.log");
		log = new LogBook(logFile); 
		
		/*****************
		 * Start screen
		 *****************/
		
		WFrame = new JFrame("Bubble Tasks");
		WFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		WFrame.setLayout(null);
		WFrame.setSize(1200, 800);
		WFrame.setLocation(50, 50);
		//WFrame.setIconImage(image);
		
		JLabel JLnewTask = new JLabel("New Task:"); 
		JLnewTask.setBounds(80, 30, 100, 30);
		JLnewTask.setHorizontalAlignment(JLabel.LEFT);
		WFrame.add(JLnewTask);
		
		JTFnewTask = new JTextField();
		JTFnewTask.setBounds(150, 30, 425, 30);
		JTFnewTask.requestFocus();
		WFrame.add(JTFnewTask);
		
		JBnewTask = new JButton("Add Task");
		JBnewTask.setBounds(600, 30, 120, 30);
		JBnewTask.addActionListener(this);
		JBnewTask.setActionCommand("BNewTask");
		WFrame.add(JBnewTask);
		
		JBquickAdd = new JButton("Quick Add");
		JBquickAdd.setBounds(750, 30, 120, 30);
		JBquickAdd.addActionListener(this);
		JBquickAdd.setActionCommand("BquickAdd");
		WFrame.add(JBquickAdd);
		
		JLabel JLMyTasks = new JLabel("My Tasks");
		JLMyTasks.setBounds(80,75,100,30);
		JLMyTasks.setHorizontalAlignment(JLabel.LEFT);
		WFrame.add(JLMyTasks);
		
		JLabel JLTasksDone = new JLabel("Tasks done today");
		JLTasksDone.setBounds(80, 495, 200, 30);
		JLTasksDone.setHorizontalAlignment(JLabel.LEFT);
		WFrame.add(JLTasksDone);
		
		JLabel JLTasksDeleg = new JLabel("Waiting for reply");
		JLTasksDeleg.setBounds(CANVAS_WIDTH + 130, 75, 200, 30);
		JLTasksDeleg.setHorizontalAlignment(JLabel.LEFT);
		WFrame.add(JLTasksDeleg);
		
		
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
		
		MenuSetPref = new JMenuItem("Set Preferences ...");
		MenuSetPref.addActionListener(this);
		MenuSetPref.setActionCommand("openPrefSetting");
		MenuEdit.add(MenuSetPref);
		
		MenuLog = new JMenu("Log");
		MenuLog.getAccessibleContext().setAccessibleDescription("Log book functions");
		MenuBar.add(MenuLog);
		
		MenuLogOpen = new JMenuItem("Open Log Book");
		MenuLogOpen.addActionListener(this);
		MenuLogOpen.setActionCommand("OpenLog");
		MenuLog.add(MenuLogOpen);
		
		WFrame.setJMenuBar(MenuBar);
		
		WFrame.setVisible(true);
		
		/*********************
		 * New Task screen
		 */
		WTaskFrame = new CTaskEditFrame("Task", taskList.owner);
		
		WTaskFrame.JBTaskOK.addActionListener(this);
		WTaskFrame.JBTaskOK.setActionCommand("BTaskAdd");
		
		WTaskFrame.JBTaskCancel.addActionListener(this);
		WTaskFrame.JBTaskCancel.setActionCommand("BTaskCancel");
		
		/**********************
		 *  Edit Task screen
		 */
		WEditTaskFrame = new CTaskEditFrame("Edit Task", taskList.owner);
		
		WEditTaskFrame.JBTaskOK.addActionListener(this);
		WEditTaskFrame.JBTaskOK.setActionCommand("BTaskSubmitEdit");
		
		WEditTaskFrame.JBTaskCancel.addActionListener(this);
		WEditTaskFrame.JBTaskCancel.setActionCommand("BEditCancel");
		
		WEditTaskFrame.JBTaskDelete.addActionListener(this);
		WEditTaskFrame.JBTaskDelete.setActionCommand("BDeleteTask");
		
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
		
		/******************
		 * Preferences setting frame
		 */
		WPrefFrame = new CPreferencesFrame(preferences);
		
		WPrefFrame.JBOK.addActionListener(this);
		WPrefFrame.JBOK.setActionCommand("preferencesConfirmed");
		
		WPrefFrame.JBCancel.addActionListener(this);
		WPrefFrame.JBCancel.setActionCommand("preferencesCanceled");
		
		/***********************
		 * Prepare table view
		 */
		TableView = new CTableView(taskList, WEditTaskFrame, WDelConfFrame);
		
		/*******************
		 * Set up bubble canvas
		 */
		
		bubbleArea = new CMyTaskArea(taskList, CANVAS_WIDTH, CANVAS_HEIGHT, WEditTaskFrame);
		bubbleArea.setBounds(80, 100, CANVAS_WIDTH, CANVAS_HEIGHT);
		bubbleArea.setPreferredSize(new Dimension(CANVAS_WIDTH,CANVAS_HEIGHT));
		
		scrollPane = new JScrollPane(bubbleArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(80,100,SCROLL_WIDTH, SCROLL_HEIGHT);
		WFrame.add(scrollPane);
		
		scrollPane.revalidate();
		scrollPane.repaint();
		
	
		doneArea = new CDoneTodayArea(taskList, CANVAS_WIDTH, DONE_AREA_HEIGHT, WEditTaskFrame);
		doneArea.setBounds(80, 100 + SCROLL_HEIGHT + 50, 1000, DONE_AREA_HEIGHT);
		doneArea.setPreferredSize(new Dimension(CANVAS_WIDTH * 2, DONE_AREA_HEIGHT));
		scrollPaneDone = new JScrollPane(doneArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneDone.setBounds(80, 100 + SCROLL_HEIGHT + 50, SCROLL_WIDTH, DONE_AREA_HEIGHT);
		WFrame.add(scrollPaneDone);
		
		scrollPaneDone.revalidate();
		scrollPaneDone.repaint();
		
		
		delegateArea = new CDelegateArea(taskList, DEL_AREA_WIDTH, CANVAS_HEIGHT, WEditTaskFrame);
		delegateArea.setBounds(CANVAS_WIDTH + 130, 100, DEL_AREA_WIDTH, CANVAS_HEIGHT);
		delegateArea.setPreferredSize(new Dimension(DEL_AREA_WIDTH, CANVAS_HEIGHT));
		scrollPaneDel = new JScrollPane(delegateArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneDel.setBounds(CANVAS_WIDTH + 130, 100, DEL_AREA_WIDTH + 20, SCROLL_HEIGHT + DONE_AREA_HEIGHT + 50);
		WFrame.add(scrollPaneDel);
		
		scrollPaneDel.revalidate();
		scrollPaneDel.repaint();
		
		//TODO: re-evaluate the need for grooming
		
		/*-----------------------
		 * Log book interface
		*/
		WLogInterface = new LogInterface(log);
		WLogInterface.Frame.setVisible(false);
	}
	
	
	/**
	 * Quickly adds a new task with default values. 
	 * Description is taken from the description line. 
	 */
	public void addNewTaskDefault(String Description)
	{
		CTask task = new CTask();
		task.setDescription(Description);
		taskList.addTaskToList(task);
	}
	
	/**
	 * Adds a new task, taking the information given in the 
	 * task editing window.
	 */
	public void addNewTaskWithDetails()
	{
		CTask task = WTaskFrame.getTaskFromFrame();
		taskList.addTaskToList(task);
	}
	
	/**
	 * Replaces a task in the list of task with the edited version of the task 
	 */
	public void replaceTaskInList(int index, CTask task)
	{
		taskList.replaceTaskInList(index, task);
	}
	
	/**
	 * Delet a task from the task list at the given index
	 */
	public void deleteTask(int index)
	{
		taskList.removeTaskFromList(index);
	}

	/**
	 * If preferences have been changed by the user, 
	 * the changes are taken into effect by this method
	 */
	public void updatePreferences()
	{
		taskList.owner = preferences.owner;
		WEditTaskFrame.defaultUser = preferences.owner;
		WTaskFrame.defaultUser = preferences.owner;
		
		//TODO: Ändere den Namen des assignees aller "myTasks" 
		taskList.adaptOwnerNameOfMyTasks(preferences.owner);
	}
	
	/**
	 * Main function being called at start up. 
	 * Starting point for the application. 
	 */
	public static void main(String[] args) {

		
		BubbleTaskManager taskMan = new BubbleTaskManager();
		
		//initialize
		taskMan.initialize();
		
		
	}

}
