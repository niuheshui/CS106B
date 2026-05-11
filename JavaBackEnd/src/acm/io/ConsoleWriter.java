package acm.io;

import java.io.Writer;

class ConsoleWriter extends Writer {
   private ConsoleModel consoleModel;

   public ConsoleWriter(ConsoleModel var1) {
      this.consoleModel = var1;
   }

   public void close() {
   }

   public void flush() {
   }

   public void write(char[] var1, int var2, int var3) {
      String var4 = new String(var1, var2, var3);

      int var5;
      int var6;
      for(var5 = 0; (var6 = var4.indexOf(IOConsole.LINE_SEPARATOR, var5)) != -1; var5 = var6 + IOConsole.LINE_SEPARATOR.length()) {
         this.consoleModel.print(var4.substring(var5, var6), 0);
         this.consoleModel.print("\n", 0);
      }

      this.consoleModel.print(var4.substring(var5), 0);
   }
}
