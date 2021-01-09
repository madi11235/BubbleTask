import javax.swing.*;
import java.awt.event.*;


public class LogInterface {

	LogBook log;
	
	JFrame Frame;
	JTextArea textArea;
	JTextField  TFOrt;
	JFormattedTextField JFTDatum;
	JLabel LDatum, LOrt;
	JButton BLog;
	
	
	LogInterface(LogBook log)
	{
		this.log = log;
		
		Frame = new JFrame("Logbuch");
		Frame.setSize(800,600);
		Frame.setLayout(null);
		
		textArea = new JTextArea();
		//Schriftart hier einstellen
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(2, 2, 500, 550);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		Frame.add(scrollPane);
		
		//Labels + textfields
		LDatum = new JLabel("Datum: ");
		LDatum.setBounds(560,5,50,30);
		Frame.add(LDatum);
		
		JFTDatum = new JFormattedTextField(java.util.Calendar.getInstance().getTime());
		JFTDatum.setBounds(620,5,100,30);
		Frame.add(JFTDatum);
		System.out.println(JFTDatum.getText());
		
		
		LOrt = new JLabel("Place: ");
		LOrt.setBounds(570,60,30,30);
		Frame.add(LOrt);
		
		
		TFOrt = new JTextField();
		TFOrt.setBounds(620,60,100,30);
		Frame.add(TFOrt);
		
		//Button
		BLog= new JButton("LOG");
		BLog.setBounds(650,180,100,60);
		BLog.addActionListener(new Watcher());
		BLog.setActionCommand("Log");
		Frame.add(BLog);
		
	}
	
	
	public class Watcher implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String command = e.getActionCommand();
			
			if(command.equals("Log"))
			{
				//datum konvertieren
				String str = JFTDatum.getText();
				String datum;
				datum = str.substring(6);
				System.out.println(datum);
			    datum = datum.concat(str.substring(3, 5));
			    System.out.println(datum);
			    datum = datum.concat(str.substring(0, 2));
			    System.out.println(datum);
			    
			    System.out.println(textArea.getText());
			    
			    log.addLogEntry(new LogEntry(datum, TFOrt.getText(), textArea.getText()));
			    log.saveLogBook();
				
			}
		}
		
	}
	
	
}
