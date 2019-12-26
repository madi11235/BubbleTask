import javax.swing.*;
import java.util.*;
import java.awt.event.*;

public class CTableView implements ActionListener{

	CTaskList taskList;
	
	JFrame WTableFrame;
	JScrollPane scrollPane;
	JTable taskTable;
	
	JButton JBupdate;
	
	CTableView()
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
	
}
