import javax.swing.*;

public class CDeleteConfirmFrame {

	String TaskDescription;
	int taskIndex;
	
	JFrame WConfirmFrame;
	JLabel JLtext;
	JButton JBok, JBcancel;
	
	CDeleteConfirmFrame()
	{
		TaskDescription = "";
		taskIndex = -1;
		setupConfirmFrame();
	}
	
	private void setupConfirmFrame()
	{
		WConfirmFrame = new JFrame("Confirm Deleting");
		WConfirmFrame.setSize(400,200);
		WConfirmFrame.setLocation(400,300);
		WConfirmFrame.setLayout(null);
		
		JLtext = new JLabel("Do you really want to delete ...");
		JLtext.setBounds(50,25,300,30);
		WConfirmFrame.add(JLtext);
		
		JBok = new JButton("OK");
		JBok.setBounds(75, 80, 100, 75);
		WConfirmFrame.add(JBok);
		
		JBcancel = new JButton("Cancel");
		JBcancel.setBounds(225, 80, 100, 75);
		WConfirmFrame.add(JBcancel);
	}
	
	public void openConfirmView(int i, String description)
	{
		this.taskIndex = i;
		this.TaskDescription = description;
		
		JLtext.setText("Do you really want to delete \"" + description + "\"");
		WConfirmFrame.setVisible(true);
		
	}
	
}
