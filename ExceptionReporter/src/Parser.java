import java.io.File;


public interface Parser {

	
	public void parseException(String message);
	
	public void emailReport();

	public void init(String reportname);
	
	public void createFinalReport();
	
}
