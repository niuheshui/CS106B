package acm.io;

import java.io.Reader;

class ConsoleReader extends Reader {
   private ConsoleModel consoleModel;
   private String buffer;

   public ConsoleReader(ConsoleModel var1) {
      this.consoleModel = var1;
      this.buffer = null;
   }

   public void close() {
   }

   public int read(char[] var1, int var2, int var3) {
      if (var3 == 0) {
         return 0;
      } else {
         if (this.buffer == null) {
            this.buffer = this.consoleModel.readLine();
            if (this.buffer == null) {
               return -1;
            }

            this.buffer = this.buffer + "\n";
         }

         if (var3 < this.buffer.length()) {
            this.buffer.getChars(0, var3, var1, var2);
            this.buffer = this.buffer.substring(var3);
         } else {
            var3 = this.buffer.length();
            this.buffer.getChars(0, var3, var1, var2);
            this.buffer = null;
         }

         return var3;
      }
   }
}
