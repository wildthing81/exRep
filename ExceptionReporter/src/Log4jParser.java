import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 
 */

/**
 * @author asus
 * 
 */
public class Log4jParser implements Parser {

	private BufferedWriter finalReportWriter;
	private BufferedWriter tempWriter;
	private Pattern date_Pattern;
	private Pattern exception_Pattern;
	private boolean reportSubHeaderCreated=false;
	private int exceptionCount;
	private String TEMP_FILENAME="temp.txt";
	/*
	 * (non-Javadoc)
	 * 
	 * @see Parser#parseException(String message)
	 */
	@Override
	public void parseException(String message) {
		Matcher matcher = date_Pattern.matcher(message);
		if (matcher.find()){
			reportSubHeaderCreated=false;			
			return;
		}
			
		matcher = exception_Pattern.matcher(message);
		if (matcher.find()) 
			createSubHeader();
		
		if (reportSubHeaderCreated)
		{		
			try
			{
				tempWriter.write(message);
				tempWriter.write(System.getProperty("line.separator"));
			} catch (IOException e) 
			{
				e.printStackTrace();
			}			
		}

	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see Parser#emailReport()
	 */
	@Override
	public void emailReport() {

	}

	@Override
	public void init(String reportname) 
	{
		try 
		{
			finalReportWriter = new BufferedWriter(new FileWriter(reportname));
			tempWriter=new BufferedWriter(new FileWriter(TEMP_FILENAME));
		} catch (IOException e) {
			 
			e.printStackTrace();
		}

		date_Pattern = Pattern.compile("^(\\d{4})-(\\d{2})-(\\d{2})");
		exception_Pattern = Pattern.compile(".Exception");
	}

	
	public void createFinalReport() 
	{
		try
		{
			tempWriter.close();
			finalReportWriter.write("Number of exceptions in log file: "+exceptionCount);
			finalReportWriter.write(System.getProperty("line.separator"));
			if (exceptionCount!=0){
				finalReportWriter.write("Details:");
				finalReportWriter.write(System.getProperty("line.separator"));
				BufferedReader tempreader=new BufferedReader(new FileReader(TEMP_FILENAME));
				String data;
				while((data=tempreader.readLine())!=null)
				{
					finalReportWriter.write(data);
					finalReportWriter.write(System.getProperty("line.separator"));
				}
				
				tempreader.close();
				finalReportWriter.close();
			}
			
		} catch (IOException e) 
		{	 
			e.printStackTrace();
		}
		
	}
	
	private void createSubHeader() 
	{
		try
		{
			if (!reportSubHeaderCreated)
			{
				exceptionCount++;
				tempWriter.write("EXCEPTION"+exceptionCount+":");
				tempWriter.write(System.getProperty("line.separator"));
			}
			reportSubHeaderCreated=true;
			
		} catch (IOException e) {
			 
			e.printStackTrace();
		}
		
		
	}
}

