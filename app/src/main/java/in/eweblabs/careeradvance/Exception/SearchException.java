package in.eweblabs.careeradvance.Exception;

public class SearchException extends Exception 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int SUCCESS = 200;
	public static final int NO_RESULTS = 400;
	public static final int SERVER_DELAY = 500;
	public static final int ERROR = 600;
	
	private int mCode;
	
	public SearchException(int aCode) 
	{
		mCode = aCode;
	}
	
	public SearchException(String aCode) 
	{
		if ( (aCode.compareTo("200") == 0) || (aCode.compareTo("RESULTS COMPLETED") == 0) )
		{
			mCode = SUCCESS;
		}
		else if ( (aCode.compareTo("400") == 0) || (aCode.compareTo("300") == 0) || (aCode.compareTo("NO VENDORS AVAILABLE TO SUPPORT SELECTED ROUTE") == 0) )
		{
			mCode = NO_RESULTS;
		}
		else if ( (aCode.compareTo("500") == 0) || (aCode.compareTo("NO RESULT FOUND. SEARCH AGAIN AFTER SOME TIME") == 0) )
		{
			mCode = SERVER_DELAY;
		}
	}

	public int getCode()
	{
		return mCode;
	}
}
