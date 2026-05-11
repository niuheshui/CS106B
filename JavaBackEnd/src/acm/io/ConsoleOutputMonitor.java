package acm.io;

class ConsoleOutputMonitor {
   private StandardConsoleModel consoleModel;

   public ConsoleOutputMonitor(StandardConsoleModel var1) {
      this.consoleModel = var1;
   }

   public synchronized void print(String var1, int var2) {
      this.consoleModel.printCallback(var1, var2);
   }
}
