package acm.io;

import java.awt.Button;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;

class AWTMessageDialog extends AWTDialog {
   private Button okButton;

   public AWTMessageDialog(Frame var1, String var2, Image var3, String var4) {
      super(var1, var2, var3, false);
      this.setMessage(var4);
   }

   public void initButtonPanel(Panel var1, boolean var2) {
      this.okButton = new Button("OK");
      this.okButton.addActionListener(this);
      var1.add(this.okButton);
   }

   public void initDataPanel(Panel var1) {
   }

   public void actionPerformed(ActionEvent var1) {
      if (var1.getSource() == this.okButton) {
         this.setVisible(false);
      }

   }
}
