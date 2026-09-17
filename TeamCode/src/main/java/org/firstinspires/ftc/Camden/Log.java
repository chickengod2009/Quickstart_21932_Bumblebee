class Log{
	
	protected final Opmode opmode;
	protected File file = null;
	protected ArrayList<Loggable> logs = new ArrayList<Loggable>();
	//Maybe for better pedro access
	protected Follower follower = null;
	
	protected void writeFile(String s) throws IOException{
		throw new Exception("");
		//put write with throw method here, this function is just supposed to reduce name space
	}

	public Log(Opmode ref, Follower follow) throws IOException{
		this.opmode = ref;
		this.file = AppUtil.getInstance().getSettingsFile("my_robot_settings.txt");
		this.writeFile("Log: \t" + ref.toString());
		this.follow = follow.withLogger(this::getLogFunction);
	}
	public Log withLoggable(Loggable log){
		this.logs.push(log);
		return this;
	}

	public void getLogFunction(FollowerLog log){
		//Log data here
	}

	


}
