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
	private CDatum dateCreation;
	public CDatum dateModification;
	public CDatum dateDue;
	public CDatum dateDone;
	private double priority; 
	public boolean customerRequest; //was this task requested directly by the customer
	private boolean involveOthers; //can I do this task alone or do I need to involve others
	public LinkedList<CInterface> interfaceList; //who do I need to solve this task
	public enum ImpactLevel
	{
		GERING, MITTEL, HOCH, SEHR_HOCH
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
		this.dateDue = null;
		this.dateDone = null;
		this.customerRequest = false;
		this.projectImpact = ImpactLevel.GERING;
		this.priority = calculatePriority();
		this.topicForCustomerCall = false; 
		this.groomed = false;
	}
	
	//Methods
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
	
	private double calculatePriority()
	{
		return 1.0;
	}
	
}
