import javax.swing.*;

public class CPreferencesFrame {
	
	//Attributes
	CTaskList taskList;
	
	CPreferences pref;
	
	JFrame WPrefFrame;
	JButton JBOK, JBCancel;
	JLabel JLOwner;
	JTextField JTFOwner; 
	
	CPreferencesFrame(CPreferences pref)
	{
		this.pref = pref;
		setUpPrefFrame();
	}
	
	public void updatePreferences(CPreferences pref)
	{
		this.pref = pref; 
	}
	
	public CPreferences getPreferences()
	{
		pref.owner = JTFOwner.getText();
		
		return this.pref; 
	}
	
	public void openPreferencesFrame()
	{
		WPrefFrame.setVisible(true);
	}
	
	public void closePreferencesFrame()
	{
		WPrefFrame.setVisible(false);
	}
	
	private void setUpPrefFrame()
	{
		WPrefFrame = new JFrame("Set Preferences");
		WPrefFrame.setSize(400,400);
		WPrefFrame.setLocation(300,200);
		WPrefFrame.setLayout(null);
		
		JLOwner = new JLabel("Your Name:");
		JLOwner.setBounds(50,25,100,30);
		JLOwner.setHorizontalAlignment(JLabel.RIGHT);
		WPrefFrame.add(JLOwner);
		
		JTFOwner = new JTextField();
		JTFOwner.setBounds(170,25,130,30);
		JTFOwner.setText(pref.owner);
		WPrefFrame.add(JTFOwner);
		
		JBOK = new JButton("OK");
		JBOK.setBounds(200, 300, 80, 30);
		WPrefFrame.add(JBOK);
		
		JBCancel = new JButton("Cancel");
		JBCancel.setBounds(300, 300, 80, 30);
		WPrefFrame.add(JBCancel);
	}
	
	

}
