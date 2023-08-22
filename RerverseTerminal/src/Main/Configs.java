package Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class Configs {
	private static Properties prop = new Properties();
	public static File configF = new File("config.ini");
	public static String OSOut = System.getProperty("os.name").toLowerCase();
	public static String DefaultStartUpLin = "bash";
	public static String DefaultStartUpWin = "cmd";
	public static String DefaultAutoRunUp = "NaN";
	public static String OSSHELL = "Default";
	public static String Build = "202305171";
	public static String Key;
	public static void Load() {
		Main.LOG("Checking file");
		try {
			Main.LOG("Checking outside OS: " + OSOut);
			if(OSOut.indexOf("win") >= 0)
				Main.ELOG("Windows Server! Config Not Implement");
			Main.LOG("Loading config data from file: config.ini");
			if(!configF.exists()) {
				Main.ELOG("File: config.ini |> doesn't exist! If it first launch it's normal! We creating it for you");
				prop.store(new FileOutputStream(configF), null);
			} else Main.LOG("File Exist. OK");
		    prop.load(new FileInputStream(configF));
			LoadDefaultsConfig();
		    Main.LOG("Loaded Config.ini Data: ");
		    Main.LOG("		- Startup-Command: " + Configs.GetProp("Startup-Command"));
		    Main.LOG("		- Autorun-Command: " + Configs.GetProp("Autorun-Command"));
		    Main.LOG("		- Public-IP: " + Configs.GetProp("Public-IP"));
		    Main.LOG("		- Private-IP: " + Configs.GetProp("Private-IP"));
		    Main.LOG("		- HOST_IP: " + Configs.GetProp("HOST-IP") + " : IP For host your Apps in your (bash, .sh) code files");
		    Main.LOG("		- HOST_PORT: " + Configs.GetProp("HOST-PORT") + " : PORT For host your Apps in your (bash, .sh) code files");
		    Main.LOG("		- ENABLE-WEB_SHELL: " + Configs.GetProp("ENABLE-WEB_SHELL") + " : Enable or disable web terminal (True/False)");
		    Main.LOG("		- WEB-USERNAME: " + Configs.GetProp("WEB-USERNAME") + " : Username For Web Terminal");
		    Main.LOG("		- WEB-PASSWORD: " + Configs.GetProp("WEB-PASSWORD") + " : Password For Web Terminal");
		    Main.LOG("Example FOR host some web or another app:");
		    Main.LOG("[1] php -S $HOST_IP:$HOST_PORT");
		    Main.LOG("[2] java -jar file.jar -port $HOST_PORT");
		} catch (Exception ex) {
			Main.ELOG("Failed to load File: config.ini : "+ex.getMessage());
		}
	}
	public static Boolean CheckUpdate() {
		Main.LOG("Checking Update");
		return !Configs.Build.equals(Main.OSurl.get("Main"));
	}
	public static String GetProp(String property) {
		return prop.getProperty(property);
	}
	public static void WriteProp(String property, String value) {
		try {
			prop.setProperty(property, value);
			prop.store(new FileOutputStream(configF), null);
		} catch (Exception e) {
			Main.ELOG("Failed TO save PROPERTY: "+e.getMessage());
		}
	}
	public static Boolean isExist(String e) {
		return prop.containsKey(e);
	}
	private static void LoadDefaultsConfig() {
		try {
			if(!isExist("USR-KEY"))
			    prop.setProperty("USR-KEY", "InputYourKeyHere");
			if(!isExist("OS"))
				prop.setProperty("OS", OSSHELL);
			if(!isExist("HOST-IP"))
				prop.setProperty("HOST-IP", "0.0.0.0");
			if(!isExist("HOST-PORT"))
				prop.setProperty("HOST-PORT", System.getenv("SERVER_PORT"));
			if(!isExist("Public-IP"))	
				prop.setProperty("Public-IP", System.getenv("SERVER_IP"));
			if(!isExist("Private-IP"))
				prop.setProperty("Private-IP", System.getenv("INTERNAL_IP"));
			if(!isExist("Startup-Command"))
				prop.setProperty("Startup-Command", OSOut.indexOf("win") >= 0 ? DefaultStartUpWin : DefaultStartUpLin);
			if(!isExist("Autorun-Command"))
				prop.setProperty("Autorun-Command", DefaultAutoRunUp);
			if(!isExist("ENABLE-WEB_SHELL"))
				prop.setProperty("ENABLE-WEB_SHELL", "False");
			if(!isExist("WEB-USERNAME"))
				prop.setProperty("WEB-USERNAME", "ADMIN");
			if(!isExist("WEB-PASSWORD"))
				prop.setProperty("WEB-PASSWORD", "12345");
			prop.store(new FileOutputStream(configF), null);
		} catch (Exception e) {
			Main.ELOG("Error while creating config.ini Num 7: "+e.getMessage());
		}
	}
	public static Boolean CompareKey(String o) {
		return o.equals(Configs.Key) || o == Configs.Key;
	}
	public static Boolean ValidKey() {
		return Configs.GetProp("USR-KEY").equals(Configs.Key) && CompareKey(Configs.GetProp("USR-KEY"));
	}
}
