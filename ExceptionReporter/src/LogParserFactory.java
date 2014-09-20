/**
 * @author asus
 *
 */
public class LogParserFactory {

	private static LogParserFactory instance=null;
	
	public static enum LOGGERTYPE{
		LOG4J;
	}
	
	private LogParserFactory()
	{
		
	}
	
	public static LogParserFactory getInstance(){
		
		if (instance==null)
		{
			
			synchronized (LogParserFactory.class) 
			{				
				if (instance==null)
					instance=new LogParserFactory();
		
			}
		}
		return instance;
	}

	public Parser getParser(String logfiletype) 
	{
		 if (logfiletype.equalsIgnoreCase(LOGGERTYPE.LOG4J.name()))
			 return new Log4jParser();
		 
		 return null;
		
	}
	
	public void printParserTypes()
	{
		System.out.println(LOGGERTYPE.values());
	}
	
}

