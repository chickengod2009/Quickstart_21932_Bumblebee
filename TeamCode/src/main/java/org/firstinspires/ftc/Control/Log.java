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
		if (this.file == null) return;
		FileWriter writer = new FileWriter(this.file, true);
		try {writer.write(s);} catch (Exception e){writer.close(); throw new IOException(e);}
		writer.close();
		
		//put write with throw method here, this function is just supposed to reduce name space
	}

	public Log(Opmode ref, Follower follow) throws IOException{
		this.opmode = ref;
		this.file = (AppUtil.getInstance().getSettingsFile("my_log.txt"));
		ReadWriteFile.writeFile(this.file, "--- Start of Log: " + ref.toString() + " ---\n");
		this.follower = follow.withLogger(this::logFunction);
	}
	//add more logs
	public Log withLoggable(Loggable log){
		if (log != null)
			this.logs.add(log);
		return this;
	}
	//actual log function
	public void logFunction(FollowerLog Flog){
		
		StringBuilder buff = new StringBuilder("");
		this.logCalls +=1;
		Log.totalLogCalls += 1;
		for (Loggable log : this.logs){
			if (log == null) continue;
			buff.append(log.log()).append("\n");
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
			//TODO! opmode.telementry.addData!
			throw new RuntimeException("");
		}	
	}

	


	@Override
	public String log(){
		this.logFunction(null);
		return this.lastCall;
	}	
	

	
	public Log withDriveTrain(Drivetrain drive){
		Loggable ret = new Loggable(){
			Drivetrain drivetrain = drive;
			@Override
			public String log(){
				//Log function for drivetrain
				return "Drivetrain"
			}	
		};
		this.logs.add(ret);
		return this;
	}

	
	
	
	
	

	


}
