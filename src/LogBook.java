import java.util.LinkedList;
import java.io.*;

public class LogBook {
	
	File LogFile; 
	FileWriter fWriter;
	BufferedWriter bWriter; 
	FileReader fReader;
	BufferedReader bReader;
	
	public LinkedList<LogEntry> LogEntryList;
	
	//Constructors
	LogBook()
	{
		this.LogEntryList = new LinkedList<LogEntry>();
	}
	
	LogBook(File lf)
	{
		this.LogFile = lf;
		this.LogEntryList = new LinkedList<LogEntry>();
		loadLogBook();
	}
	
	
	//Methods
	public int getSize()
	{
		return LogEntryList.size();
	}
	
	public LogEntry getLogEntry(int i)
	{
		return LogEntryList.get(i);
	}
	
	public void addLogEntry(LogEntry le)
	{
		LogEntryList.add(le);
	}

	private void loadLogBook()
	{
		if(this.LogFile.exists() && !this.LogFile.isDirectory())
		{
			try
			{
				fReader = new FileReader(this.LogFile);
				bReader = new BufferedReader(fReader);
				String str, bDate, bPlace, bText;
				int counter = 0;
				
				//Load Header
				str = bReader.readLine();
				System.out.println("Read first line log");
				System.out.println(str);
				str = bReader.readLine();
				System.out.println(str);

				System.out.println("Starting to read log entries");
				bReader.readLine();
				while(!str.equals("---***---EndOfLogBook---***---") && counter < 3)
				{
					//Load logEntry Data	
			    	bDate = str.substring(str.indexOf(":")+3);
			    	
			    	str = bReader.readLine();
			    	bPlace = str.substring(str.indexOf(":")+3);
			    	
			    	bText="";
			    	str = bReader.readLine();
			    	
			    	while(!(str.equals("---EndOfEntry---")))
			    	{
			    		bText = bText.concat(str+"\n");
				    	str = bReader.readLine();
				    	System.out.println(str);
			    	}
			    	this.addLogEntry(new LogEntry(bDate, bPlace, bText));
			    	counter++;
			    	System.out.println(counter);
			    	str = bReader.readLine();
				}	
		    	
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
		
		
	}
	
	public void saveLogBook()
	{
		 try{
				fWriter = new FileWriter(this.LogFile);
				bWriter = new BufferedWriter(fWriter);	
			}
			
			catch(IOException ex)
			{
				ex.printStackTrace();
				System.out.println("File not found");
			}
			
			
		 	try{
		 		//save the Log Book Entries
				 
			 	//Header
		 		bWriter.write("---Start of Logbook---"); bWriter.newLine();
		 		bWriter.write("----------------------");bWriter.newLine();
		 		
		 		//Save Entries
		 		for(int i=0; i<this.getSize();i++)
		 		{
		 			
		 			String str=LogEntryList.get(i).Datum.substring(6);
					str = str+".";
					str = str.concat(LogEntryList.get(i).Datum.substring(4,6));
					str = str+".";
					str=str.concat(LogEntryList.get(i).Datum.substring(0,4));
					
					bWriter.write("Datum : \t"+str);
					bWriter.newLine();
					bWriter.write("Place : \t"+LogEntryList.get(i).Place);
					bWriter.newLine();
					
					String txt = LogEntryList.get(i).Text;
					int index = txt.indexOf("\n");
					String zeile;
					while(index != -1)
					{
						zeile = txt.substring(0,index);
						bWriter.write(zeile);
						bWriter.newLine();
						txt = txt.substring(index+1);
						index = txt.indexOf("\n");
					}
					bWriter.write(txt);
					bWriter.newLine();
					bWriter.write("---EndOfEntry---");
					bWriter.newLine();
		 		}
		 		
		 		//Footer of file
		 		bWriter.write("---***---EndOfLogBook---***---");
		 	}
		 	catch(IOException exep)
			{
				exep.printStackTrace();
			}
			
			try
			{
				bWriter.close();
				fWriter.close();
			}	
			catch(IOException ex)
			{
				ex.printStackTrace();
			}
	}
}
