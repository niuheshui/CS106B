package acm.io;

class ConsoleInputMonitor {
   private StandardConsoleModel consoleModel;

   public ConsoleInputMonitor(StandardConsoleModel var1) {
      this.consoleModel = var1;
   }

   public synchronized String readLine() {
      return this.consoleModel.readLineCallback();
   }
}
