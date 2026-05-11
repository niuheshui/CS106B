package acm.io;

class SystemConsole extends IOConsole {
   protected ConsoleModel createConsoleModel() {
      return new SystemConsoleModel();
   }
}
