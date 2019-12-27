import java.util.Calendar;


public class CDatum extends Calendar
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int zeroDay = 1;
	public static final int zeroMonth = 1;
	public static final int zeroYear = 2019;
	
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
	
	CDatum(int i)
	//for default dates
	{
		cal = Calendar.getInstance();
		this.Tag = i;
		this.Monat = i;
		this.Jahr = i;
		this.Stunde = i;
	}
	
	CDatum(int jahr, int monat, int tag, int stunde)
	//for default dates
	{
		cal = Calendar.getInstance();
		this.Tag = tag;
		this.Monat = monat;
		this.Jahr = jahr;
		this.Stunde = stunde;
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
	
	public long convertDateInHours()
	/*
	 * Function converts the date into hours starting with
	 * a predefined zero-hour.
	 * Assuming a 1.1.XXXX as zero hour. Otherwise the computation requires more thought.  
	 */
	{
		//compute the difference from zero hour:
		long diffYear = this.Jahr - zeroYear;
		long diffMonth = this.Monat - zeroMonth;
		long diffDay = this.Tag - zeroDay;
		
		return ((long) this.Stunde + 24 * diffDay + 24 * 30 * diffMonth + 24 * 365 * diffYear); 	
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
