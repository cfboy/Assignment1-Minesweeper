public class TimerCounter {
	
	public static int seconds = 0;


	public static String getTime()
	{

		if(seconds < 10)
			return "00"+seconds;
		else if(seconds < 100)
			return "0"+seconds;
		else
			return ""+seconds;
	}

	public void doStuff(){
		//do stuff here
		seconds++;
		
	}
}