import javax.swing.*;
import java.util.*;
import java.awt.event.*;

public class CTableView implements ActionListener{

	CTaskList taskList;
	CTaskEditFrame editFrame;
	
	JFrame WTableFrame;
	JScrollPane scrollPane;
	JTable taskTable;
	
	JButton JBupdate, JBedit;
	
	CTableView()
	{
		setUpWindow();
	}
	
	CTableView(CTaskList taskList, CTaskEditFrame editFrame)
	{
		this.taskList = taskList;
		this.editFrame = editFrame; 
		setUpWindow();
	}
	
	private void setUpWindow()
	{
		WTableFrame = new JFrame("Table View");
		WTableFrame.setLayout(null);
		WTableFrame.setSize(900, 500);
		WTableFrame.setLocation(150, 150);
		
		JBupdate = new JButton("Update");
		JBupdate.setBounds(775,50,75,50);
		JBupdate.addActionListener(this);
		JBupdate.setActionCommand("UpdateTable");
		WTableFrame.add(JBupdate);
		
		JBedit = new JButton("Edit");
		JBedit.setBounds(775, 125, 75, 50);
		JBedit.addActionListener(this);
		JBedit.setActionCommand("EditEntry");
		WTableFrame.add(JBedit);
	
	}
	
	public void actionPerformed(ActionEvent e)
	{
		String command;
		command = e.getActionCommand();
		
		switch (command)
		{
		case "UpdateTable":
			showTable(this.taskList);
			break;
		case "EditEntry":
			editTableEntry();
			break;
		}
	}
	
	public void showTable(CTaskList taskList)
	{
		this.taskList = taskList;
		
		String[][] SList = taskList.getListAsStringArray();
		
		String[][] Content = Arrays.copyOfRange(SList,1,SList.length);
		
		taskTable = new JTable( Content, SList[0]);
		scrollPane = new JScrollPane(taskTable);
		scrollPane.setBounds(50,50,700,400);
		taskTable.setFillsViewportHeight(true);
		WTableFrame.add(scrollPane);
		WTableFrame.setVisible(true);
	}
	
	public void editTableEntry()
	/*
	 * Function identifies table entry line to be modified
	 * and opens a window allowing the editing of this task.
	 * (assuimung, that this classes object has a pointer to the 
	 * task list object and not a copied task list.) 
	 */
	{
		CTask task = null;
		int[] selectedRows = taskTable.getSelectedRows();
		String selTaskDescr = (String) taskTable.getValueAt(selectedRows[0], 0);
		
		// find index in task list
		boolean descrFound = false;
		int index = 0;
		while(!descrFound && index < taskList.getSize())
		{
			task = taskList.getTask(index);
			if(selTaskDescr == task.getDescription())
			{
				descrFound = true;
			}
			else
			{
				index++;
			}
		}
		
		//edit task via task frame
		if(task != null)
		{
			editFrame.openEditView(index, task);
		}
		else
		{
			System.out.println("Error: Task from table for editing not identified. Bug in software in Table View Class. Congratlations!");
		}
		
	}
	
}
