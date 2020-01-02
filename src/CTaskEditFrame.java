import javax.swing.*;

public class CTaskEditFrame {

	CDatum today;
	
	String[] SImpactLevels = {"Low", "Medium", "High", "Very High"};
	
	int taskIndex; //index of task in task list which is being edited in this window
	
	JFrame WTaskFrame; 
	JTextField JTFDescription;
	JTextArea JTANotes;
	JTextField JTFAssignee, JTFDueDay, JTFDueMonth, JTFDueYear;
	JCheckBox JCBcustomerReq, JCBcustomerCall, JCBinterfaceOthers;
	JComboBox JComboImpact = new JComboBox(SImpactLevels);
	JComboBox JComboComplexity = new JComboBox(SImpactLevels);
	JButton JBTaskOK, JBTaskCancel;
	
	CTaskEditFrame(String name){
		
		today = new CDatum();
		
		WTaskFrame = new JFrame(name);
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
		
		JBTaskOK = new JButton("OK");
		JBTaskOK.setBounds(600, 450, 150,50);
		WTaskFrame.add(JBTaskOK);
		
		JBTaskCancel = new JButton("Cancel");
		JBTaskCancel.setBounds(800,450,150,50);
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
	
	public void openEditView(int indexOfTask, CTask task)
	/*
	 * The editing window is opened; 
	 * The fields are pre-filled with the task's properties. 
	 * 
	 */
	{
		this.taskIndex = indexOfTask;
		preFillWithTask(task);
		WTaskFrame.setVisible(true);
	}
	
	private void preFillWithTask(CTask task)
	/*
	 * Fills the fields in the task editing window with 
	 * the attributes of the task. 
	 */
	{
		resetTaskFrame();
		JTFDescription.setText(task.getDescription());
		JTANotes.setText(task.getNotes());
		JTFAssignee.setText(task.getAssignee());
		
		JTFDueDay.setText(String.valueOf(task.getDueDate().Tag));
		JTFDueMonth.setText(String.valueOf(task.getDueDate().Monat));
		JTFDueYear.setText(String.valueOf(task.getDueDate().Jahr));
		
		JCBcustomerReq.setSelected(task.getCustomerRequest());
		JCBcustomerCall.setSelected(task.getTopicForCustCall());
		JCBinterfaceOthers.setSelected(task.getInvolveOthers());
		
		JComboImpact.setSelectedIndex(CTask.getIntFromImpactLevel(task.getProjectImpact()));
		JComboComplexity.setSelectedIndex(CTask.getIntFromImpactLevel(task.getComplexity()));
	}
	
	public CTask getTaskFromFrame()
	/*
	 * reads out the fields in the frame and returns a task
	 */
	{
		CTask task = new CTask();
		
		task.setDescription(JTFDescription.getText());
		task.setNotes(JTANotes.getText());
		task.setAssignee(JTFAssignee.getText());
		task.setDueDate(Integer.parseInt(JTFDueYear.getText()),
					Integer.parseInt(JTFDueMonth.getText()), Integer.parseInt(JTFDueDay.getText()));
		task.setCustomerRequest(JCBcustomerReq.isSelected());
		task.setTopicForCustCall(JCBcustomerCall.isSelected());
		task.setInvolveOthers(JCBinterfaceOthers.isSelected());
		switch (JComboImpact.getSelectedIndex())
		{
		case 0:
			task.setProjectImpact(CTask.ImpactLevel.LOW);
			break;
		case 1:
			task.setProjectImpact(CTask.ImpactLevel.MEDIUM);
			break;
		case 2:
			task.setProjectImpact(CTask.ImpactLevel.HIGH);
			break; 
		case 3: 
			task.setProjectImpact(CTask.ImpactLevel.VERY_HIGH);
			break;
		default:
			task.setProjectImpact(CTask.ImpactLevel.LOW);
			break;
		}
		switch (JComboComplexity.getSelectedIndex())
		{
		case 0:
			task.setComplexity(CTask.ImpactLevel.LOW);
			break;
		case 1:
			task.setComplexity(CTask.ImpactLevel.MEDIUM);
			break;
		case 2:
			task.setComplexity(CTask.ImpactLevel.HIGH);
			break; 
		case 3: 
			task.setComplexity(CTask.ImpactLevel.VERY_HIGH);
			break;
		default:
			task.setComplexity(CTask.ImpactLevel.LOW);
			break;
		}
		task.setGroomed(true);
		
		return task;
	}
	
	public CTask getTaskFromEditFrame(CTask task)
	/*
	 * reads out the fields in the frame and fills an already 
	 * existing task with the fields from the form
	 */
	{		
		task.setDescription(JTFDescription.getText());
		task.setNotes(JTANotes.getText());
		task.setAssignee(JTFAssignee.getText());
		task.setDueDate(Integer.parseInt(JTFDueYear.getText()),
					Integer.parseInt(JTFDueMonth.getText()), Integer.parseInt(JTFDueDay.getText()));
		task.setCustomerRequest(JCBcustomerReq.isSelected());
		task.setTopicForCustCall(JCBcustomerCall.isSelected());
		task.setInvolveOthers(JCBinterfaceOthers.isSelected());
		switch (JComboImpact.getSelectedIndex())
		{
		case 0:
			task.setProjectImpact(CTask.ImpactLevel.LOW);
			break;
		case 1:
			task.setProjectImpact(CTask.ImpactLevel.MEDIUM);
			break;
		case 2:
			task.setProjectImpact(CTask.ImpactLevel.HIGH);
			break; 
		case 3: 
			task.setProjectImpact(CTask.ImpactLevel.VERY_HIGH);
			break;
		default:
			task.setProjectImpact(CTask.ImpactLevel.LOW);
			break;
		}
		switch (JComboComplexity.getSelectedIndex())
		{
		case 0:
			task.setComplexity(CTask.ImpactLevel.LOW);
			break;
		case 1:
			task.setComplexity(CTask.ImpactLevel.MEDIUM);
			break;
		case 2:
			task.setComplexity(CTask.ImpactLevel.HIGH);
			break; 
		case 3: 
			task.setComplexity(CTask.ImpactLevel.VERY_HIGH);
			break;
		default:
			task.setComplexity(CTask.ImpactLevel.LOW);
			break;
		}
		task.setGroomed(true);
		
		return task;
	}

	
}
