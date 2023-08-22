package CommandHandle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Main.Main;
import Main.Configs;
public class Command {
	public static void WriteCmd(String cmd) {
	    try {
	    	Main.LOG(Configs.GetProp("WEB-USERNAME")+"@"+new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("pwd").getInputStream())).readLine() +": " + cmd);
			Main.bw.write(cmd);
		    Main.bw.newLine();
		    Main.bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}