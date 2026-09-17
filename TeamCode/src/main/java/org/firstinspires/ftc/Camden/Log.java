class Log implements Loggable{
	
	protected final Opmode opmode;
	protected File file = null;
	protected ArrayList<Loggable> logs = new ArrayList<Loggable>();
	//Maybe for better pedro access
	protected Follower follower = null;

	//Hardware map varaible needed!

	private static int totalLogCalls = 0;
	private int logCalls =0;
	private String lastCall = "";
	
	protected void writeFile(String s) throws IOException{
		throw new IOException("");
		//put write with throw method here, this function is just supposed to reduce name space
	}

	public Log(Opmode ref, Follower follow) throws IOException{
		this.opmode = ref;
		this.file = AppUtil.getInstance().getSettingsFile("my_robot_settings.txt");
		this.writeFile("Log: \t" + ref.toString());
		this.follower = follow.withLogger(this::logFunction);
	}
	public Log withLoggable(Loggable log){
		if (log != null)
			this.logs.add(log);
		return this;
	}

	public void logFunction(FollowerLog Flog){
		
		StringBuilder buff = new StringBuilder("");
		this.logCalls +=1;
		Log.totalLogCalls += 1;
		for (Loggable log : this.logs){
			if (log == null) continue;
			buff.append(log.log(this.opmode)).append("\n");
		}	
		
		//Need to wrap in try catch block
		
		if (Flog != null)
			this.lastCall = this.logCalls + "\n" 
					   	+ buff.toString() + Flog.toString() + "\n" + 
					   	"Total logs: " + Log.totalLogCalls+"\n";
			
		else
			this.lastCall = this.logCalls + "\n" 
					   	+ buff.toString() + "\n" + 
					   	"Total logs: " + Log.totalLogCalls+"\n";

		try{
			this.writeFile(this.lastCall);
		}catch (IOException e){
			//TODO!
			throw new RuntimeException("");
		}	
	}

	


	@Override
	public String log(Opmode op){
		this.logFunction(null);
		return this.lastCall;
	}	
	/*

	This would be the blueprint of making default tracking for the Log
	public static Log withDriveTrain(HardwareMap map){
		Loggable drive = new Loggable(){
			@Override
			public String log(Opmode op){
				
			}	
		}	
	}
	
	*/
	
	

	


}
