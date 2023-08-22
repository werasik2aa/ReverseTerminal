package Listeners;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Main.Configs;
import Main.Main;
public class InputA {
	private static String SID = "Not Implement";
	private static String PID = "Not Implement";
	private static String VERS = "Not Implement";
	private static String UUID = "Not Implement";
	@SuppressWarnings("deprecation")
	public static String GenKey() {
 	   try {
 		   if(!Configs.isExist("DEBUGHACK333222121221311221KEY")) {
 		       if(Configs.OSOut.indexOf("win") >= 0) {
 					Main.LOG("{KEY]Not Implement");
 					BufferedReader sNumReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("wmic csproduct get UUID").getInputStream()));
 					StringBuffer output = new StringBuffer();
 					String line = "";
 				    while ((line = sNumReader.readLine()) != null)
 				        output.append(line + "\n");
 				    Configs.Key = UUID=output.toString().substring(output.indexOf("\n"), output.length()).trim().replaceAll("[^0-9]", "");
 		       } else {
 		    	   SID = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("uname -n").getInputStream())).readLine();
 		    	   PID = SID.replaceAll("[^0-9]", "");
 		    	   VERS = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("uname -r").getInputStream())).readLine();
 		    	   Configs.Key = (VERS+SID+PID+VERS+SID+PID+PID+SID+VERS).replaceAll("[^0-9]", "");
 		       }
 		   } else if(Configs.GetProp("DEBUGHACK333222121221311221KEY").equals("YOU_PIZDABOL")) {
 			  Main.LOG("Verify skip! KeyCode = YOU_PIZDABOL");
 			  Configs.Key = "YOU_PIZDABOL";
 			  SID = PID = VERS = "Verify skip! KeyCode = YOU_PIZDABOL";
 		   } else
 			   Main.ELOG("YOU never hack this");
		} catch (IOException e) {
			Main.ELOG(e.getMessage());
		}
 	   if(Configs.GetProp("USR-KEY").equals("InputYourKeyHere") || !CompareKey(Configs.GetProp("USR-KEY"))) {
 		   Main.ELOG("To get key go to my discord channel https://discord.com/invite/yahCmFYjKe");
 		   Main.ELOG("#instructions-guide");
 		   Main.LOG("=============================");
 		   Main.LOG("{KEY GEN] SID: " + SID);
 		   Main.LOG("{KEY GEN] PID: " + PID);
 		   Main.LOG("{KEY GEN] VERS: " + VERS);
 		   Main.LOG("{KEY GEN] UUID: " + UUID);
 		   Main.LOG("=============================");
 	   }
 	   return Configs.Key;
	}
	public static void WriteKeyToFile(String key) {
		if(CompareKey(key))
			Configs.WriteProp("USR-KEY", key);
		else
			Main.ELOG("FAILED TO WRITE KEY; Key String WRONG");
	}
	public static Boolean CompareKey(String o) {
		return o.equals(Configs.Key) || o == Configs.Key;
	}
	public static Boolean ValidKey() {
		return Configs.GetProp("USR-KEY").equals(Configs.Key) && CompareKey(Configs.GetProp("USR-KEY"));
	}
}
