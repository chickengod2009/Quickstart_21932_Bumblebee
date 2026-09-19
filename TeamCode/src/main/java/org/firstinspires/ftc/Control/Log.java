package org.firstinspires.ftc.Control;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerLog;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.jetbrains.annotations.NotNull;

import com.qualcomm.robotcore.util.ReadWriteFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


class Log implements Loggable{

	protected final OpMode opmode;
	protected File file;
	protected ArrayList<Loggable> logs = new ArrayList<>();
	//Maybe for better pedro access
	protected short tick =0;
	protected Follower follower;

	//Hardware map varaible needed!

	private static int totalLogCalls = 0;
	private int logCalls =0;
	private String lastCall = "";
	private BufferedWriter writer;

	protected void writeFile(String s) throws IOException{
		if (this.writer == null) return;
		this.writer.write(s);
	}

	public Log(@NotNull OpMode ref, Follower follow) throws IOException{
		this.opmode = ref;
		this.file = (AppUtil.getInstance().getSettingsFile("my_log.txt"));
		ReadWriteFile.writeFile(this.file, "--- Start of Log: " + ref.toString() + " ---\n");
		this.writer = new BufferedWriter(new FileWriter(this.file, true));
		if(follow == null)
			this.follower = null;
		else
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
		if(this.tick < 1000){
			this.tick++;
			return;
		}
		this.tick=0;
		StringBuilder buff = new StringBuilder();
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

	

	//force log
	@Override
	public String log(){
		this.tick = 1001;
		this.logFunction(null);
		return this.lastCall;
	}	

	
	

/*	
	public Log withDriveTrain(Drivetrain drive){
		Loggable ret = new Loggable(){
			Drivetrain drivetrain = drive;
			@Override
			public String log(){
				//Log function for drivetrain
				return "Drivetrain";
			}	
		};
		this.logs.add(ret);
		return this;
	}
*/
	//closes the stream and makes sure everything gets written. Must be calles at the end of any program using a log!
	public void closeLog(){
		try{
			if(this.writer == null) return;
			this.writer.flush();
			this.writer.close();
			this.writer = null;
		}catch (Exception e){
			//telementry print, might not be worth it, though, as program is ending
		}	

	}	

	
	
	
	
	

	


}
