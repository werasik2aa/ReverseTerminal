package Listeners;

import java.util.Map.Entry;
import java.util.Scanner;

import CommandHandle.Command;
import CommandHandle.InstallWizard;
import Main.Main;
import Main.Configs;


public class InputH implements Runnable {
	  static Thread thread;
	  public void start() {
	    if (thread == null) {
	      thread = new Thread(this);
	      thread.start();
	    }
	  }
	  public static Scanner input = new Scanner(System.in);
	  @Override
	  public void run() {
		  if(InputA.ValidKey() && InputA.CompareKey(Configs.GetProp("USR-KEY")))
		  try {
			  if(Configs.GetProp("USR-KEY").equals(Configs.Key) && Configs.CompareKey(Configs.GetProp("USR-KEY"))) {
				  if(Configs.GetProp("ENABLE-WEB_SHELL").equalsIgnoreCase("true"))
					  InstallWizard.InstallShellWeb();
			  while(true) {
				  String line = input.nextLine();
				  if(line.equalsIgnoreCase("AptUpgrade")) {
					  if(!Configs.GetProp("OS").equals("Default"))
						  InstallWizard.UpgradeOSApt();
					  else
						  Main.ELOG("Not available in Default Container");
				  }else if(line.equalsIgnoreCase("UpdateServer")) {
				      Main.LOG("Starting Update Process of Server.jar");
				      InstallWizard.UpdateJAR();
				  }else if(line.equalsIgnoreCase("ReloadCFG")) {
					  Main.LOG("Reloading CONFIG file... And Recovering Missings options");
					  Configs.Load();
				  }else if(line.equalsIgnoreCase("RunShellInWeb")) {
					  Main.LOG("Starting Terminal In A Web");
					  InstallWizard.InstallShellWeb();
				  }else if(line.toLowerCase().contains("installapp")) {
					  if(Configs.GetProp("OS").equalsIgnoreCase("Default"))
						  InstallWizard.LOCALInstallAPP(line.toLowerCase().replace("installapp", ""));
					  else
						  InstallWizard.InstallAPP(line.toLowerCase().replace("installapp", ""));
				  }else if(line.equalsIgnoreCase("UnInstallOS")) {
					  if(Configs.GetProp("OS").equalsIgnoreCase("Default"))
						  InstallWizard.UninstallOS();
					  else
						  Main.ELOG("Default container can't be deleted!");
				  }else if(line.equalsIgnoreCase("ListOS")) {
					  int i = 0;
					  Main.LOG("==========Available OS List============");
					  for(Entry<String, String> vals : Main.OSurl.entrySet()) {
						  if(vals.getKey().equals("Main")) continue;
						  i++;
						  Main.LOG(i+" " + vals.getKey());
					  }
					  Main.LOG("=======================================");
				  }else if(line.equalsIgnoreCase("ResetOS")) {
					  InstallWizard.ResetC();
				  }else if(line.equalsIgnoreCase("Help")) {
					  Main.LOG("===============================================");
					  Main.LOGHELP();
					  Main.LOG("===============================================");
				  } else if(line.equalsIgnoreCase("Console")) {
					  Main.LOG("Intering to direct send Command!");
					  Main.LOG("Now you can input all linux commands here");
					  while(true) {
						 line = input.nextLine();
						 if(line.equalsIgnoreCase("ConsoleExit")) {
							 Main.LOG("Returning back....");
							 Main.LOG("Returned!");
							 break;
						 }
						 Command.WriteCmd(line);
					  }
				  } else if(line.split(" ")[0].equalsIgnoreCase("install")) {
						 if(line.split(" ").length == 2)
							 if(Main.OSurl.containsKey(line.split(" ")[1]))
								 InstallWizard.InstallOS(line.split(" ")[1], false);
							 else Main.ELOG("OS: "+ line.split(" ")[1] +" Not found in library");
						 else Main.ELOG("Wrong usage use Install <OSName>");
				  } else if(line.split(" ")[0].equalsIgnoreCase("reinstall")) {
					  if(Configs.GetProp("OS").equalsIgnoreCase("Default"))
						  if(Main.OSurl.containsKey(Configs.GetProp("OS")))
								 InstallWizard.InstallOS(Configs.GetProp("OS"), true);
							 else Main.ELOG("Installed OS: "+ Configs.GetProp("OS") +" Not found in Downloaded OS's library");
					  else Main.ELOG("OS not INSTALLED! Nothing to reinstall");
				  } else if(line.equalsIgnoreCase("CloseIT")) break; 
				  else {
					  Main.ELOG("This command Doesn't exist: "+line);
				  	  Main.LOG("Input |-> Help <-| For get list of available commands");
				  }
			  	}
			  }
			} catch (Exception e) {
			  Main.ELOG(e.getMessage());
			}
		  	Main.LOG("GOODBYE!.");
		  	Main.LOG("Please don't DDOS with it or mine bitcoin");
	  }
	}