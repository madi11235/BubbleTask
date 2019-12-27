/*
 * Public class CTask
 * 
 * Summary: 
 * Class of singular tasks. An object of this class represents a singular tasks, which contains all the information to accomplish this task. 
 *  
 * 
 */
import java.util.*;

public class CTask {

	
	//Attributes
	private String description; 
	private String notes;
	private String assignee; 
	private boolean done; 
	public CDatum dateCreation;
	public CDatum dateModification;
	private CDatum dateDue;
	public CDatum dateDone;
	private double priority; 
	private boolean customerRequest; //was this task requested directly by the customer
	private boolean involveOthers; //can I do this task alone or do I need to involve others
	public LinkedList<CInterface> interfaceList; //who do I need to solve this task
	public static enum ImpactLevel
	{
		LOW, MEDIUM, HIGH, VERY_HIGH
	}
	private ImpactLevel projectImpact;
	private ImpactLevel complexity;
	private boolean topicForCustomerCall; //Has this topic the potential to ba hendled in a customer call?
	private boolean groomed; //if true, all relevant information in the task has been filled
	
	//Constructors
	CTask()
	{
		this.description = "";
		this.notes = "";
		this.assignee = "Markus";
		this.done = false; 
		this.dateCreation = new CDatum();
		this.dateModification = new CDatum();
		this.interfaceList = new LinkedList<CInterface>();
		this.involveOthers = false;
		this.dateDue = new CDatum(0);
		this.dateDone = new CDatum(0);
		this.customerRequest = false;
		this.projectImpact = ImpactLevel.LOW;
		this.complexity = ImpactLevel.LOW;
		this.topicForCustomerCall = false; 
		this.groomed = false;
		this.priority = calculatePriority();
	}
	
	CTask(String str)
	{
		int index, tag, monat, jahr, stunde; 
		index = str.indexOf("\t");
		this.description = str.substring(0, index);
		str = str.substring(index+1);
		index = str.indexOf("\t");
		this.notes = str.substring(0, index);
		str = str.substring(index+1);
		index = str.indexOf("\t");
		this.assignee = str.substring(0, index);
		str = str.substring(index+1);
		index = str.indexOf("\t");
		this.done = Boolean.parseBoolean(str.substring(0, index));
		str = str.substring(index+1);
		index = str.indexOf("\t");
		jahr = Integer.parseInt(str.substring(0,index));
		str = str.substring(index+1);
		index = str.indexOf("\t");
		monat = Integer.parseInt(str.substring(0,index));
		str = str.substring(index+1);
		index = str.indexOf("\t");
		tag = Integer.parseInt(str.substring(0,index));
		str = str.substring(index+1);
		index = str.indexOf("\t");
		stunde = Integer.parseInt(str.substring(0,index));
		str = str.substring(index+1);
		this.dateCreation = new CDatum(jahr, monat, tag, stunde);
		index = str.indexOf("\t");
		jahr = Integer.parseInt(str.substring(0,index));
		str = str.substring(index+1);
		index = str.indexOf("\t");
		monat = Integer.parseInt(str.substring(0,index));
		str = str.substring(index+1);
		index = str.indexOf("\t");
		tag = Integer.parseInt(str.substring(0,index));
		str = str.substring(index+1);
		index = str.indexOf("\t");
		stunde = Integer.parseInt(str.substring(0,index));
		str = str.substring(index+1);
		this.dateModification = new CDatum(jahr, monat, tag, stunde);
		index = str.indexOf("\t");
		jahr = Integer.parseInt(str.substring(0,index));
		str = str.substring(index+1);
		index = str.indexOf("\t");
		monat = Integer.parseInt(str.substring(0,index));
		str = str.substring(index+1);
		index = str.indexOf("\t");
		tag = Integer.parseInt(str.substring(0,index));
		str = str.substring(index+1);
		index = str.indexOf("\t");
		stunde = Integer.parseInt(str.substring(0,index));
		str = str.substring(index+1);
		this.dateDue = new CDatum(jahr, monat, tag, stunde);
		index = str.indexOf("\t");
		jahr = Integer.parseInt(str.substring(0,index));
		str = str.substring(index+1);
		index = str.indexOf("\t");
		monat = Integer.parseInt(str.substring(0,index));
		str = str.substring(index+1);
		index = str.indexOf("\t");
		tag = Integer.parseInt(str.substring(0,index));
		str = str.substring(index+1);
		index = str.indexOf("\t");
		stunde = Integer.parseInt(str.substring(0,index));
		str = str.substring(index+1);
		this.dateDone = new CDatum(jahr, monat, tag, stunde);
		index = str.indexOf("\t");
		this.priority = Double.parseDouble(str.substring(0, index));
		str = str.substring(index+1);
		index = str.indexOf("\t");
		this.customerRequest = Boolean.parseBoolean(str.substring(0, index));
		str = str.substring(index+1);
		index = str.indexOf("\t");
		this.involveOthers = Boolean.parseBoolean(str.substring(0,index));
		str = str.substring(index+1);
		index = str.indexOf("\t");
		this.projectImpact = getImpactLevelFromString(str.substring(0,index));
		str = str.substring(index+1);
		index = str.indexOf("\t");
		this.complexity = getImpactLevelFromString(str.substring(0,index));
		str = str.substring(index+1);
		index = str.indexOf("\t");
		this.topicForCustomerCall = Boolean.parseBoolean(str.substring(0,index));
		str = str.substring(index+1);
		index = str.indexOf("\t");
		this.groomed = Boolean.parseBoolean(str.substring(0,index));
		this.interfaceList = new LinkedList<CInterface>();
	}
	
	//Interface Methods
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String descr)
	{
		this.description = descr;
	}
	
	public String getNotes()
	{
		return notes;
	}
	
	public void setNotes(String notes)
	{
		//remove the line breaks from the string
		int index = notes.indexOf(System.lineSeparator());
		String buf;
		
		while (index != -1)
		{
			buf = notes.substring(0, index);
			notes = buf + " " + notes.substring(index + 1);
			index = notes.indexOf(System.lineSeparator());
		}
		
		//remove tabs
		index = notes.indexOf("\t");
		while (index != -1)
		{
			buf = notes.substring(0, index);
			notes = buf + " " + notes.substring(index + 1);
			index = notes.indexOf("\t");
		}
		
		this.notes = notes; 
	}
	
	public boolean getDone()
	{
		return done; 
	}
	
	public void setDone(boolean done)
	{
		this.done = done; 
	}
	
	public String getAssignee()
	{
		return this.assignee;
	}
	
	public void setAssignee(String txt)
	{
		this.assignee = txt;
	}
	
	public boolean getCustomerRequest()
	{
		return this.customerRequest;
	}
	
	public void setCustomerRequest(boolean req)
	{
		this.customerRequest = req;
	}
	
	public boolean getTopicForCustCall()
	{
		return this.topicForCustomerCall;
	}
	
	public void setTopicForCustCall(boolean custCall)
	{
		this.topicForCustomerCall = custCall;
	}
	
	public boolean getInvolveOthers()
	{
		return involveOthers;
	}
	
	public void setInvolveOthers(boolean others)
	{
		this.involveOthers = others;
/*		
		if(interfaceList.size() > 0)
			this.involveOthers = true;
		else
			this.involveOthers = false; 
*/			
	}
	
	
	public static ImpactLevel getImpactLevelFromString(String str)
	{
		if(str.equals("LOW"))
			return ImpactLevel.LOW;
		else
		{
			if(str.equals("MEDIUM"))
				return ImpactLevel.MEDIUM;
			else
			{
				if(str.equals("HIGH"))
					return ImpactLevel.HIGH;
				else
				{
					return ImpactLevel.VERY_HIGH;
				}
			}
		}
			
		
	}
	
	public static int getIntFromImpactLevel(ImpactLevel lev)
	{
		int i = 0;
		switch(lev)
		{
			case LOW:
				i = 0;
				break;
			case MEDIUM: 
				i = 1;
				break;
			case HIGH:
				i = 2; 
				break; 
			case VERY_HIGH: 
				i = 3;
				break;
			default:
				i = -1;
				break; 
		}
			
		return i;
	}
	
	public ImpactLevel getProjectImpact()
	{
		return this.projectImpact;
	}
	
	public void setProjectImpact(ImpactLevel lev)
	{
		this.projectImpact = lev;
	}
	
	public ImpactLevel getComplexity()
	{
		return this.complexity;
	}
	
	public void setComplexity(ImpactLevel compl)
	{
		this.complexity = compl;
	}
	
	public CDatum getDueDate()
	{
		return this.dateDue;
	}
	
	public void setDueDate(CDatum date)
	{
		this.dateDue = date;
	}
	
	public void setDueDate(int year, int month, int day)
	{
		this.dateDue.Jahr = year;
		this.dateDue.Monat = month;
		this.dateDue.Tag = day;
	}

	public void setDueDate(int year, int month, int day, int hour)
	{
		this.dateDue.Jahr = year;
		this.dateDue.Monat = month;
		this.dateDue.Tag = day;
		this.dateDue.Stunde = hour;
	}
	
	public boolean getGroomed()
	{
		return this.groomed;
	}
	
	public void setGroomed(boolean in)
	{
		this.groomed = in;
	}
	
	/*
	 * getContentAsString()
	 * 
	 * returns the attribute of the task as string for saving
	 */
	public String getContentAsString()
	{
		String outText = description;
		outText = outText.concat("\t");
		outText = outText.concat(notes);
		outText = outText.concat("\t");
		outText = outText.concat(assignee);
		outText = outText.concat("\t");
		outText = outText.concat(String.valueOf(done));
		outText = outText.concat("\t");
		outText = outText.concat(String.valueOf(dateCreation.Jahr));
		outText = outText.concat("\t");
		outText = outText.concat(String.valueOf(dateCreation.Monat));
		outText = outText.concat("\t");
		outText = outText.concat(String.valueOf(dateCreation.Tag));
		outText = outText.concat("\t");
		outText = outText.concat(String.valueOf(dateCreation.Stunde));
		outText = outText.concat("\t");
		outText = outText.concat(String.valueOf(dateModification.Jahr));
		outText = outText.concat("\t");
		outText = outText.concat(String.valueOf(dateModification.Monat));
		outText = outText.concat("\t");
		outText = outText.concat(String.valueOf(dateModification.Tag));
		outText = outText.concat("\t");
		outText = outText.concat(String.valueOf(dateModification.Stunde));
		outText = outText.concat("\t");
		outText = outText.concat(String.valueOf(dateDue.Jahr));
		outText = outText.concat("\t");
		outText = outText.concat(String.valueOf(dateDue.Monat));
		outText = outText.concat("\t");
		outText = outText.concat(String.valueOf(dateDue.Tag));
		outText = outText.concat("\t");
		outText = outText.concat(String.valueOf(dateDue.Stunde));
		outText = outText.concat("\t");
		outText = outText.concat(String.valueOf(dateDone.Jahr));
		outText = outText.concat("\t");
		outText = outText.concat(String.valueOf(dateDone.Monat));
		outText = outText.concat("\t");
		outText = outText.concat(String.valueOf(dateDone.Tag));
		outText = outText.concat("\t");
		outText = outText.concat(String.valueOf(dateDone.Stunde));
		outText = outText.concat("\t");
		outText = outText.concat(String.valueOf(priority));
		outText = outText.concat("\t");
		outText = outText.concat(String.valueOf(customerRequest));
		outText = outText.concat("\t");
		outText = outText.concat(String.valueOf(involveOthers));
		outText = outText.concat("\t");
		outText = outText.concat(String.valueOf(projectImpact));
		outText = outText.concat("\t");
		outText = outText.concat(String.valueOf(complexity));
		outText = outText.concat("\t");
		outText = outText.concat(String.valueOf(topicForCustomerCall));
		outText = outText.concat("\t");
		outText = outText.concat(String.valueOf(groomed));
		outText = outText.concat("\t");
		//TODO: Interface list auch speichern und laden
		
		return outText; 
	}
	
	//Methods
	
	private double calculatePriority()
	{
		return 1.0;
	}
	
	/*
	 * getFieldNames()
	 * 
	 * returns te names of all properties of a task as a string array
	 */
	public String[] getFieldNames()
	{
		String[] fieldNames = 
			{
					"Description",
					"Assignee",
					"Done",
					"Notes",
					"Creation Date",
					"Modification Data",
					"Due Date",
					"Done Date",
					"Priority",
					"Project Impact",
					"Complexity",
					"Customer Request",
					"Involves others",
					"Topic for Customer Call",
					"groomed"
			};
		return fieldNames;
	}
	
	public String[] getTaskAsStringArray()
	{
		String[] STask = {
				this.description,
				this.assignee,
				String.valueOf(this.done),
				this.notes,
				String.valueOf(this.dateCreation.Tag) + "." + String.valueOf(this.dateCreation.Monat) + "." + String.valueOf(this.dateCreation.Jahr) + ":" + String.valueOf(this.dateCreation.Stunde),
				String.valueOf(this.dateModification.Tag) + "." + String.valueOf(this.dateModification.Monat) + "." + String.valueOf(this.dateModification.Jahr)  + ":" + String.valueOf(this.dateModification.Stunde),
				String.valueOf(this.dateDue.Tag) + "." + String.valueOf(this.dateDue.Monat) + "." + String.valueOf(this.dateDue.Jahr),
				String.valueOf(this.dateDone.Tag) + "." + String.valueOf(this.dateDone.Monat) + "." + String.valueOf(this.dateDone.Jahr),
				String.valueOf(this.priority),
				String.valueOf(this.projectImpact),
				String.valueOf(this.complexity),
				String.valueOf(this.customerRequest),
				String.valueOf(this.involveOthers),
				String.valueOf(this.topicForCustomerCall),
				String.valueOf(this.groomed)
		};
		
		return STask;
	}
	
}
