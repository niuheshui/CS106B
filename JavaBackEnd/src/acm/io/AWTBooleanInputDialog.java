package acm.io;

import java.awt.Button;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;

class AWTBooleanInputDialog extends AWTDialog {
   private Button trueButton;
   private Button falseButton;
   private Button cancelButton;
   private Boolean input;

   public AWTBooleanInputDialog(Frame var1, String var2, Image var3, String var4, String var5, boolean var6) {
      super(var1, "Input", var3, var6);
      this.setMessage(var2);
      this.trueButton.setLabel(var4);
      this.falseButton.setLabel(var5);
   }

   public Boolean getInput() {
      return this.input;
   }

   public void initButtonPanel(Panel var1, boolean var2) {
      this.trueButton = new Button("True");
      this.trueButton.addActionListener(this);
      var1.add(this.trueButton);
      this.falseButton = new Button("False");
      this.falseButton.addActionListener(this);
      var1.add(this.falseButton);
      if (var2) {
         this.cancelButton = new Button("Cancel");
         this.cancelButton.addActionListener(this);
         var1.add(this.cancelButton);
      }

   }

   public void initDataPanel(Panel var1) {
   }

   public void actionPerformed(ActionEvent var1) {
      Component var2 = (Component)var1.getSource();
      if (var2 == this.trueButton) {
         this.input = Boolean.TRUE;
         this.setVisible(false);
      } else if (var2 == this.falseButton) {
         this.input = Boolean.FALSE;
         this.setVisible(false);
      } else if (var2 == this.cancelButton) {
         this.input = null;
         this.setVisible(false);
      }

   }
}
