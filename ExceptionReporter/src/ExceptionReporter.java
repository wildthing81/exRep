import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class ExceptionReporter {

	/**
	 * @param args
	 * @throws IOException 
	 */
	//private static String LOGFILETYPE;
	
	public static void main(String[] args) throws IOException {
		  
		BufferedReader br=new BufferedReader(new FileReader(args[0]));
		Parser parser=LogParserFactory.getInstance().getParser(args[1]);
		
		if (parser==null)
		{
			System.out.println("Input accepted format of logfile e.g.");
			LogParserFactory.getInstance().printParserTypes();
			return;
		}
		parser.init(args[2]);
		
		String logmsg;
		
		while((logmsg=br.readLine())!=null)
		{
			parser.parseException(logmsg);
		}
		
		parser.createFinalReport();
		System.out.println("Final Report is created at "+args[2]);
		//parser.emailReport();
	}

}
