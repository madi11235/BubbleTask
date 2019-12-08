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
	public CDatum dateCreation;
	public CDatum dateModification;
	public CDatum dateDue;
	public CDatum dateDone;
	private double priority; 
	public boolean customerRequest; //was this task requested directly by the customer
	private boolean involveOthers; //can I do this task alone or do I need to involve others
	public LinkedList<CInterface> interfaceList; //who do I need to solve this task
	public enum ImpactLevel
	{
		LOW, MEDIUM, HIGH, VERY_HIGH
	}
	public ImpactLevel projectImpact;
	public boolean topicForCustomerCall; //Has this topic the potential to ba hendled in a customer call?
	public boolean groomed; //if true, all relevant information in the task has been filled
	
	//Constructors
	CTask(String description)
	{
		this.description = description;
		this.dateCreation = new CDatum();
		this.dateModification = new CDatum();
		this.interfaceList = new LinkedList<CInterface>();
		this.involveOthers = false;
		this.notes = "";
		this.dateDue = new CDatum(0);
		this.dateDone = new CDatum(0);
		this.customerRequest = false;
		this.projectImpact = ImpactLevel.LOW;
		this.priority = calculatePriority();
		this.topicForCustomerCall = false; 
		this.groomed = false;
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
	
	public boolean getInvolveOthers()
	{
		return involveOthers;
	}
	
	public void setInvolveOthers()
	{
		if(interfaceList.size() > 0)
			this.involveOthers = true;
		else
			this.involveOthers = false; 
			
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
