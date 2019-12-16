import javax.swing.*;
import java.util.*;;

public class CTableView {

	JFrame WTableFrame;
	JScrollPane scrollPane;
	JTable taskTable;
	
	CTableView()
	{
		
		WTableFrame = new JFrame("Table View");
		WTableFrame.setLayout(null);
		WTableFrame.setSize(800, 500);
		WTableFrame.setLocation(150, 150);
		
	}
	
	public void showTable(CTaskList taskList)
	{
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
