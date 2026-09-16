class Log{
	
	protected final Opmode opmode;
	protected File log = null;
	//Maybe for better pedro access
	protected Follower follower = null;
	
	protected void writeFile(String s) throws IOException{
		throw new Exception("");
		//put write with throw method here, this function is just supposed to reduce name space
	}

	public Log(Opmode ref, Follower follow){
		this.opmode = ref;
		this.file = AppUtil.getInstance().getSettingsFile("my_robot_settings.txt");
		this.writeFile("Log: \t" + ref.toString());
		this.follow = follow.withLogger(/*put log producing function here*/);
	}

	


}