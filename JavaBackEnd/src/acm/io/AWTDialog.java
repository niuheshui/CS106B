package acm.io;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

abstract class AWTDialog extends Dialog implements ActionListener {
   public static final int WIDTH = 260;
   public static final int HEIGHT = 115;
   private AWTMessageCanvas messageArea;

   public AWTDialog(Frame var1, String var2, Image var3, boolean var4) {
      super(var1, var2, true);
      this.setLayout(new BorderLayout());
      Panel var5 = new Panel();
      Panel var6 = new Panel();
      Panel var7 = new Panel();
      Panel var8 = new Panel();
      var5.setLayout(new BorderLayout());
      var6.setLayout(new FlowLayout());
      var7.setLayout(new BorderLayout());
      var8.setLayout(new BorderLayout());
      var8.add(new Label(" "));
      this.messageArea = new AWTMessageCanvas();
      var7.add(this.messageArea, "Center");
      this.initButtonPanel(var6, var4);
      this.initDataPanel(var7);
      var5.add(new AWTIconCanvas(var3), "West");
      var5.add(var7, "Center");
      this.add(var5, "Center");
      this.add(var6, "South");
      this.add(var8, "East");
      Rectangle var9 = var1.getBounds();
      int var10 = var9.x + var9.width / 2;
      int var11 = var9.y + var9.height / 2;
      this.setBounds(var10 - 130, var11 - 57, 260, 115);
      this.validate();
   }

   public abstract void initButtonPanel(Panel var1, boolean var2);

   public abstract void initDataPanel(Panel var1);

   public abstract void actionPerformed(ActionEvent var1);

   public void setMessage(String var1) {
      this.messageArea.setMessage(var1);
   }
}
