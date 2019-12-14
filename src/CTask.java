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
	public CDatum dateDue;
	public CDatum dateDone;
	private double priority; 
	public boolean customerRequest; //was this task requested directly by the customer
	private boolean involveOthers; //can I do this task alone or do I need to involve others
	public LinkedList<CInterface> interfaceList; //who do I need to solve this task
	public static enum ImpactLevel
	{
		LOW, MEDIUM, HIGH, VERY_HIGH
	}
	public ImpactLevel projectImpact;
	public boolean topicForCustomerCall; //Has this topic the potential to ba hendled in a customer call?
	public boolean groomed; //if true, all relevant information in the task has been filled
	
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
		this.priority = calculatePriority();
		this.topicForCustomerCall = false; 
		this.groomed = false;
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
		this.topicForCustomerCall = Boolean.parseBoolean(str.substring(0,index));
		str = str.substring(index+1);
		index = str.indexOf("\t");
		this.groomed = Boolean.parseBoolean(str);
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
	
}
