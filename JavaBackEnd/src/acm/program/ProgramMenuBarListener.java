package acm.program;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ProgramMenuBarListener implements ActionListener {
   private ProgramMenuBar menuBar;

   public ProgramMenuBarListener(ProgramMenuBar var1) {
      this.menuBar = var1;
   }

   public void actionPerformed(ActionEvent var1) {
      this.menuBar.fireActionListeners(var1);
   }
}
