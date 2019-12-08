import java.util.Calendar;


public class CDatum extends Calendar
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//++++++++++++++++++++++
	
	Calendar cal;
	int Tag;
	int Monat;
	int Jahr;
	int Stunde; 
	
	CDatum()
	{
		cal = Calendar.getInstance();
		this.Tag = cal.get(Calendar.DATE);
		this.Monat = cal.get(Calendar.MONTH) +1;
		this.Jahr = cal.get(Calendar.YEAR);
		this.Stunde = cal.get(Calendar.HOUR_OF_DAY);
	}
	
	
	public void setTag(int T)
	{
		this.Tag = T;
	}
	
	public void setMonat(int M)
	{
		this.Monat = M;
	}
	
	public void setJahr(int J)
	{
		this.Jahr = J;
	}
	
	
	protected void computeTime()
	{
		
	}
	
	public void add(int field, int amount)
	{
		
	}
	
	protected void computeFields()
	{
		
	}
	
	public int getGreatestMinimum(int field)
	{
		return 0;
	}
	
	public int getLeastMaximum(int field)
	{
		return 0;
	}
	
	public int getMaximum(int field)
	{
		return 0;
	}
	
	public int getMinimum(int field)
	{
		return 0;
	}
	
	 public void roll(int field, boolean up)
	 {
		 
	 }
	 
	 
	
	
}
