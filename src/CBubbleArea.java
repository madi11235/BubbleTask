import java.awt.*;
import java.awt.event.*;

public class CBubbleArea extends Canvas{

	private static final long serialVersionUID = 1L;
	private static final int MAX_SET_SIZE = 500;
	private static final int MIN_BUBBLE_RADIUS = 60;
	private static final int MAX_BUBBLE_RADIUS = 120;
	
	public static final Color BackgroundColor = new Color(250, 235, 215);
	public static final Color urgentColor = new Color(178, 34, 34);
	public static final Color bubbleColor = new Color(25, 25, 112);
	
	public static Font smallFont = new Font("Avenir", Font.PLAIN, 10);
	public static Font mediumFont = new Font("Avenir", Font.PLAIN, 12);
	
	class CBubble
	{
		int x,y;
		double radius;
		Color fillColor;
		int taskIndex; //taskIndex in taskList 
	}
	
	CBubble[] bubbleSet = new CBubble[MAX_SET_SIZE];
	
	CBubbleArea()
	{
		setBackground(BackgroundColor);
	}
	
	class MouseWatcher extends MouseAdapter
	{
		
	}
	
	public void paint(Graphics g)
	{
		g.setColor(bubbleColor);
		g.fillOval(100, 100, 70, 70);
		
		g.setColor(Color.WHITE);
		g.setFont(smallFont);
		String[] str = textUmbrechenSmall("Aufgabe eins dringend");
		for(int i=0; i < str.length; i++)
		{
				g.drawString(str[i], 110, 120 + i*11);
		}
		
		g.setColor(urgentColor);
		g.fillOval(200, 200, MAX_BUBBLE_RADIUS, MAX_BUBBLE_RADIUS);
		
		str = textUmbrechenBig("Dies ist eine groessere Aufgabe");
		g.setColor(Color.white);
		g.setFont(mediumFont);
		for(int i=0; i < str.length; i++)
		{
				g.drawString(str[i], 210, 250 + i*14);
		}
	}
	
	private String[] textUmbrechenSmall(String text)
	{
		String[] out = {" ", " ", " "};
		
		int charPerLine = 7;
		
		int indexSpace = text.indexOf(" ");
		String str = text;
		
		
		if(indexSpace > charPerLine)
		{
			//all in one line of size 9
			out[0] = text.substring(0,charPerLine + 2) + "...";
		}
		else
		{
			out[0] = str.substring(0,indexSpace);
			str = str.substring(indexSpace +1 );
			
			indexSpace = str.indexOf(" ");
			if(indexSpace > charPerLine)
			{
				out[1] = str.substring(0,charPerLine) + "...";
			}
			else
			{
				out[1] = str.substring(0,indexSpace);
				str = str.substring(indexSpace + 1);
				
				indexSpace = str.indexOf(" ");
				if(indexSpace > charPerLine || str.length() > charPerLine)
				{
					out[2] = str.substring(0, charPerLine) + "...";
				}
				else
				{
					out[2] = str.substring(0, indexSpace);
				}
			}
		}
		
		return out;
	}
	
	private String[] textUmbrechenBig(String text)
	{
		String[] out = {" ", " ", " "};
		
		int charPerLine = 15;
		
		String str = text;
		int indexSpace;
		int charsUsed = 0;
		
		for(int i=0; i < out.length; i++)
		{
			out[i] = "";
			indexSpace = str.indexOf(" ");
			if(indexSpace > charPerLine || indexSpace == -1)
			{
				if(str.length()<=charPerLine)
				{
					out[i] = str;
				}
				else
				{
					out[i] = str.substring(0, charPerLine) + "...";
					str = str.substring(indexSpace + 1);
				}
			}
			else
			{
				charsUsed = indexSpace;
				while(charsUsed <= charPerLine & indexSpace != -1)
				{	
					out[i] = out[i] + str.substring(0,indexSpace) + " ";
					str = str.substring(indexSpace + 1);
					indexSpace = str.indexOf(" ");
					charsUsed += indexSpace; 
				}
			}
		}
		
		return out;
	}
	
	
	
		
		
}
