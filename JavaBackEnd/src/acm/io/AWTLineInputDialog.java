package acm.io;

import java.awt.Button;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;

class AWTLineInputDialog extends AWTDialog {
   private Button cancelButton;
   private Button okButton;
   private TextField textLine;
   private String input;

   public AWTLineInputDialog(Frame var1, String var2, Image var3, boolean var4) {
      super(var1, "Input", var3, var4);
      this.setMessage(var2);
   }

   public String getInput() {
      return this.input;
   }

   public void setVisible(boolean var1) {
      super.setVisible(var1);
      if (var1) {
         this.textLine.requestFocus();
      }

   }

   public void initButtonPanel(Panel var1, boolean var2) {
      this.okButton = new Button("OK");
      this.okButton.addActionListener(this);
      var1.add(this.okButton);
      if (var2) {
         this.cancelButton = new Button("Cancel");
         this.cancelButton.addActionListener(this);
         var1.add(this.cancelButton);
      }

   }

   public void initDataPanel(Panel var1) {
      this.textLine = new TextField();
      this.textLine.addActionListener(this);
      var1.add(this.textLine, "South");
   }

   public void actionPerformed(ActionEvent var1) {
      Component var2 = (Component)var1.getSource();
      if (var2 != this.okButton && var2 != this.textLine) {
         if (var2 == this.cancelButton) {
            this.input = null;
            this.setVisible(false);
         }
      } else {
         this.input = this.textLine.getText();
         this.setVisible(false);
      }

   }
}
