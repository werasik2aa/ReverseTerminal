package CommandHandle;

import Main.Main;

import java.io.File;

import Main.Configs;
public class InstallWizard {
	public static void InstallOS(String os, boolean reset) {
        Main.LOG("Installing "+os);
        if(!reset)
        	Command.WriteCmd("curl -# -SLo 1.tar.xz " + Main.OSurl.get(os) +  " && cd $HOME && tar xvf 1.tar.xz && rm 1.tar.xz && echo "+'"'+ "[Server INFO] Installation done!"+'"'+ " && proot -S . bash");
        else
        	Command.WriteCmd("ls | grep -v linux | grep -v config.ini | grep -v server.jar | xargs rm -rf && curl -# -SLo 1.tar.xz " + Main.OSurl.get(os) +  " && cd $HOME && tar xvf 1.tar.xz && rm 1.tar.xz && echo "+'"'+ "[Server INFO] Installation done!"+'"'+ " && proot -S . bash");
        Configs.WriteProp("OS", os);
        Configs.WriteProp("Startup-Command", "proot -S . bash");
	}
	public static void LOCALInstallAPP(String apps) {
        Main.LOG("Installing apps on non root container: " + apps + " && [Server Warning] Action can't be verified");
        Command.WriteCmd("apth install " + apps);
        Command.WriteCmd("export LINE1=$(find $HOME/linux -type d | awk '{printf \"%s:\", $0}') && export LD_LIBRARY_PATH=$LINE1 && export LIBRARY_PATH=$LINE1");
	}
	public static void UpdateJAR() {
		Main.LOG("Downloading Latest server.jar");
		try {
		       if(Configs.CheckUpdate()) {
		    	    new File("server.jar").renameTo(new File("server-bak.jar"));
		   			Main.LOG("<- Current Version /  New Version ->");
		   			Main.LOG(Configs.Build + " / " + Main.OSurl.get("Main"));
		   			Command.WriteCmd("curl -# -o server.jar https://igriastranomier.ucoz.ru/terminal/server.jar && rm -r server-bak.jar && echo [Server WARNING] Update server.jar maybe successfull! Need Restart");
		   			Main.LOG("Restating After 10 secs!");
		   			Thread.sleep(10000);
		   			System.exit(0);
		       } else {
		    	   Main.LOG("<- Current Version /  New Version ->");
		   		   Main.LOG(Configs.Build + " / " +  Main.OSurl.get("Main") + " :> This server.jar already Up To Date");
		       }
		} catch (Exception e) {
		    Main.ELOG("Error While downloading Latest.jar: " + e.getMessage());
		}
	}
	public static void InstallAPP(String apps) {
        Main.LOG("Installing app for fake rooted Container: " + apps);
        Command.WriteCmd("apt-get update && apt-get install -y " + apps + " && [Server Warning] Action can't be verified");
        Command.WriteCmd("export LINE1=$(find $HOME/linux -type d | awk '{printf \"%s:\", $0}') && export LD_LIBRARY_PATH=$LINE1 && export LIBRARY_PATH=$LINE1");
	}                                                                                                                                                                                                                                                                                                                                                                                                                                
	public static void InstallShellWeb() {
		Boolean a = new File("linux/usr/bin/gotty/").exists();
        Main.LOG("GOTTY Installed: " + a);
        if(!a) {
        	Main.LOG("Installing GOTTY...");
        	Command.WriteCmd("curl -# -SLo gotty.tar.gz https://github.com/yudai/gotty/releases/latest/download/gotty_linux_amd64.tar.gz && tar xf gotty.tar.gz -C $HOME/linux/usr/bin && chmod +x $HOME/linux/usr/bin/gotty");
        }
        Command.WriteCmd("nohup gotty -w -p $SERVER_PORT -c " + '"' + Configs.GetProp("WEB-USERNAME") + ":" + Configs.GetProp("WEB-PASSWORD") + '"' + " bash");
	}
	public static void UpgradeOSApt() {
        Main.LOG("Apt Upgrade process started LOCAL@: apt-get update && apt-get upgrade -y");
        Command.WriteCmd("apt-get update && apt-get upgrade -y");
	}
	public static void UninstallOS() {
        for (File file: new File(".").listFiles())
            if(file.getName().equals("config.ini") || file.getName().equals("server.jar") || file.getName().equals("Server.jar") || file.getName().equals("linux"))
            	Main.LOG("Skip file deleteion for: " + file.getName());
            else
                file.delete();
        Main.LOG("OS Uninstalled!");
        Configs.WriteProp("Startup-Command", (Configs.OSOut.indexOf("win") >= 0 ? Configs.DefaultStartUpWin : Configs.DefaultStartUpLin));
        Configs.WriteProp("OS", "Default");
	}
	public static void ResetC() {
        Main.LOG("Resetting This Container!");
    	Main.LOG("Removing " + new File(".").listFiles().length + " files");
        for (File file: new File(".").listFiles())
            if(file.getName().equals("config.ini") || file.getName().equals("server.jar") || file.getName().equals("Server.jar"))
            	Main.LOG("Skip file deleteion for: " + file.getName());
            else
                file.delete();
        if(!new File("linux/usr/bin").exists()) {
     	   new File("linux/usr/bin").mkdirs();
     	   new File("linux/bin").mkdirs();
     	   new File("linux/usr/sbin").mkdirs();
     	   new File("linux/sbin").mkdirs();
        }
        Command.WriteCmd("curl -o $HOME/linux/bin/apth https://igriastranomier.ucoz.ru/apth.txt && chmod +x $HOME/linux/bin/apth");
        Configs.WriteProp("Startup-Command", (Configs.OSOut.indexOf("win") >= 0 ? Configs.DefaultStartUpWin : Configs.DefaultStartUpLin));
        Configs.WriteProp("Autorun-Command", Configs.DefaultAutoRunUp);
        Configs.WriteProp("OS", "Default");
        Main.ELOG("Please restart to complete action");
	}
}
