package acm.io;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ConsoleActionListener implements ActionListener {
   private IOConsole console;

   public ConsoleActionListener(IOConsole var1) {
      this.console = var1;
   }

   public void actionPerformed(ActionEvent var1) {
      this.console.menuAction(var1);
   }
}
