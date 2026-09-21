package org.firstinspires.ftc.Control;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerLog;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.jetbrains.annotations.NotNull;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.ReadWriteFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


class Log implements Loggable, AutoCloseable{

	protected final OpMode opmode;
	protected File file;
	protected ArrayList<Loggable> logs = new ArrayList<>();
	//Maybe for better pedro access
	//protected short tick =0;
	protected Follower follower;

	protected ElapsedTime timer = new ElapsedTime();
	protected boolean forceLog = false;

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
			this.follower = follow.withLogger(this::logFunction).withLogger(this::telemetryLog);
		this.timer.reset();
	}
	//add more logs
	public Log withLoggable(Loggable log){
		if (log != null)
			this.logs.add(log);
		return this;
	}
	//actual log function
	public void logFunction(FollowerLog Flog){
		this.logCalls +=1;
		Log.totalLogCalls += 1;
		if(this.timer.seconds() < 5 || !this.forceLog){

			return;
		}
		this.forceLog=false;
		timer.reset();

		try{
			this.writeFile(this.makeLogString(Flog));
		}catch (IOException e){
			//TODO! opmode.telementry.addData!
			throw new RuntimeException("");
		}
	}

	private String makeLogString(FollowerLog Flog){

		StringBuilder buff = new StringBuilder();

		for (Loggable log : this.logs){
			if (log == null) continue;
			buff.append(log.log()).append("\n");
		}



		if (Flog != null)
			this.lastCall = this.logCalls + "\n"
					+ buff.toString() + Flog.toString() + "\n" +
					"Total logs: " + Log.totalLogCalls+"\n";

		else
			this.lastCall = this.logCalls + "\n"
					+ buff.toString() + "\n" +
					"Total logs: " + Log.totalLogCalls+"\n";
		return this.lastCall;
	}


	public void telemetryLog(FollowerLog Flog){

		this.opmode.telemetry.addLine(this.makeLogString(Flog));

	}

	

	//force log
	@Override
	public String log(){
		this.forceLog =true;
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


	@Override
	public void close(){
		this.closeLog();
	}

	
	
	
	
	

	


}
