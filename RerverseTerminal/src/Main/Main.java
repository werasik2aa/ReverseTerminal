package Main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import CommandHandle.Command;
import Listeners.InputH;
import Listeners.InputA;

public class Main
{
  public static BufferedWriter bw;
  public static Map<String, String> OSurl = new HashMap<String, String>();
  private static String line = "";
  private static Process process;
  private static ProcessBuilder processBuilder = new ProcessBuilder(new String[0]);
  public static void main(String[] args) {
    try {
  	   LOG("ReverseTerminal |2 Beta| Build: " + Configs.Build);
       LOG("Loading Configuration. And recovering missing lines");
       if(!new File("linux/usr/bin").exists()) {
    	   new File("linux/usr/bin").mkdirs();
    	   new File("linux/bin").mkdirs();
    	   new File("linux/usr/sbin").mkdirs();
    	   new File("linux/sbin").mkdirs();
       }
       LOG("Initialising...");
       processBuilder = new ProcessBuilder(new String[0]);
       if(Configs.OSOut.indexOf("win") >= 0)
    	   processBuilder.command("cmd").redirectOutput(ProcessBuilder.Redirect.INHERIT).redirectError(ProcessBuilder.Redirect.INHERIT);
       else
    	   processBuilder.command("bash").redirectOutput(ProcessBuilder.Redirect.INHERIT).redirectError(ProcessBuilder.Redirect.INHERIT); 
       process = processBuilder.start();
       bw = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
       Configs.Load();
	   LOG("Exporting new Environment PATH!");
	   //THE SetUp
       Command.WriteCmd("export HOST_IP=" + Configs.GetProp("HOST-IP") + " && export HOST_PORT=" + Configs.GetProp("HOST-PORT") + " && export LINE1=$(find $HOME/linux -type d | awk '{printf \"%s:\", $0}') && export LD_LIBRARY_PATH=$LINE1 && export LIBRARY_PATH=$LINE1 && export PATH=$LINE1:$PATH && bash");
       if(!new File("linux/bin/apth/").exists()) {
           LOG("Installing proot and apth And exporting new Environment PATH!");
    	   Command.WriteCmd("curl -o $HOME/linux/bin/apth https://igriastranomier.ucoz.ru/apth.txt");
    	   Command.WriteCmd("curl -o $HOME/linux/bin/systemctl https://raw.githubusercontent.com/gdraheim/docker-systemctl-replacement/master/files/docker/systemctl3.py");
    	   Command.WriteCmd("chmod +x $HOME/linux/bin/apth && $HOME/linux/bin/apth proot wget"); 
       } else
    	   LOG("Apth, proot, wget already installed!");
	   //The Startup
	   Command.WriteCmd(Configs.GetProp("Startup-Command"));
	   if(!Configs.GetProp("Autorun-Command").equals("NaN"))
		   Command.WriteCmd("nohup " + Configs.GetProp("Autorun-Command"));
	   LOG("Autorun-Command Command:> nohup" + Configs.GetProp("Autorun-Command"));
	   //END
       LOG("Loading OSs database from web");
       URL oracle = new URL("https://igriastranomier.ucoz.ru/terminal/OSs.txt");
       BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
       String line;
       while ((line = in.readLine()) != null) {
    	   String[] e = line.split(" ");
       	   OSurl.put(e[0], e[1]);
       	   if(!e[0].equals("Main"))
       		   LOG("Found OS: "+e[0]+" URL: "+e[1]);
       }
       LOG("Available to install: "+OSurl.size() + " :OSs");
       if(Configs.CheckUpdate())
    	   LOG("Found Update! You can update your server.jar with command -> | UpdateServer |");
       in.close();
    }
     catch (IOException e) {
      e.printStackTrace();
     }
	LOG("Key gen loading");
    loop();
  }
  private static void loop() {
	  LOG("Generating key for compare...");
	  InputA.GenKey();
	  LOG("Trying to read key in config.ini");
	  if(!InputA.ValidKey()) {
		  ELOG("FAILED");
		  ELOG("Please Input Correct key: ");
		  line = InputH.input.nextLine();
		  if(!InputA.CompareKey(line)) {
			  ELOG("Wrong Key: "+line);
			  loop();
			  return;
		  }
		  LOG("Writing to file Key: "+line);
		  InputA.WriteKeyToFile(line);
	  } else LOG("Verify successfull");
	  LOG("===========REVERSE TERMINAL CONTROL V2.4===========");
	  LOG("Russiam build (17 MAY 2023$) by GGM");
	  LOG("");
	  LOG("My VK -> (GOODGAMEMAGA) -->| (ORIG release) |<-- (NativeCodeMaker GameMaker) <-- MY YT CHANEL");
	  LOG("My DS nopirateonlysteam#9956");
	  LOGHELP();
	  LOG("===============================================");
	  LOG("Input Option for to do");
	  if(InputA.ValidKey())
		  new InputH().start();
	  else {
		  LOG("Key INVALID! Please Restart Server.jar");
		  System.exit(1);
	  }
  }
  public static void LOGHELP() {
	  Main.LOG("Available commands:");
	  Main.LOG("		- ListOS (Shows list Available for download OS list)");
	  Main.LOG("		- UpdateServer (Update This .jar to latest version)");
	  Main.LOG("		- Install <OS> (Install OS)");
	  Main.LOG("		- InstallAPP <STRING> (Install APPS!)");
	  Main.LOG("		- UnInstall (Uninstall Current installed os)");
	  Main.LOG("		- REInstall (REInstall OS and clear all data)");
	  Main.LOG("		- ResetOS (Clear all Data | RESET!)");
	  Main.LOG("		- Console (For send direct commands to shell) : For exit from console type ConsoleExit");
	  Main.LOG("		- ReloadCFG (Reload config file)");
	  Main.LOG("		- CloseIT (exit from app)");
	  Main.LOG("		- RunShellInWeb (Runs Terminal on Server's port in browser)");
  }
  public static void LOG(String text) {
	  System.out.println("[Server INFO] " + text);
  }
  public static void ELOG(String text) {
	  System.out.println( "[Server ERROR] " + text);
  }
}