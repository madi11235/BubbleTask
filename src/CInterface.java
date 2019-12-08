/*
 * CInterface
 * 
 * Summary: Object of this class represents the link to a person. FOr this link, there is a reason, e.g. I need information from a person. 
 * 
 */

public class CInterface {

	//Attributes
	public String namePerson;
	public String reasonForContacting; 
	public CDatum dueDateForContact; 
	
	//Constructors
	CInterface(String namePerson, String reasonForContact, CDatum dueDateForContact)
	{
		this.namePerson = namePerson; 
		this.reasonForContacting = reasonForContact;
		this.dueDateForContact = dueDateForContact; 
	}
	
	CInterface(String namePerson, String reasonForContact)
	{
		this.namePerson = namePerson; 
		this.reasonForContacting = reasonForContact;
		this.dueDateForContact = null;
	}
	//Methods
	//TODO: Implement return as string
	/*public String getContentAsString()
	{
		
	}*/
}
