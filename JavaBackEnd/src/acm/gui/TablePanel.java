package acm.gui;

import javax.swing.JPanel;

public class TablePanel extends JPanel {
   public static final int NONE = 0;
   public static final int HORIZONTAL = 2;
   public static final int VERTICAL = 3;
   public static final int BOTH = 1;
   public static final int CENTER = 10;
   public static final int LEFT = 11;
   public static final int RIGHT = 12;
   public static final int TOP = 13;
   public static final int BOTTOM = 14;
   public static final int FILL = 1;
   static final long serialVersionUID = 1L;

   TablePanel() {
   }

   public TablePanel(int var1, int var2) {
      this(var1, var2, 0, 0);
   }

   public TablePanel(int var1, int var2, int var3, int var4) {
      this.setLayout(new TableLayout(var1, var2, var3, var4));
   }

   public void setHorizontalAlignment(int var1) {
      ((TableLayout)this.getLayout()).setHorizontalAlignment(var1);
   }

   public int getHorizontalAlignment() {
      return ((TableLayout)this.getLayout()).getHorizontalAlignment();
   }

   public void setVerticalAlignment(int var1) {
      ((TableLayout)this.getLayout()).setVerticalAlignment(var1);
   }

   public int getVerticalAlignment() {
      return ((TableLayout)this.getLayout()).getVerticalAlignment();
   }

   public void setDefaultFill(int var1) {
      ((TableLayout)this.getLayout()).setDefaultFill(var1);
   }

   public int getDefaultFill() {
      return ((TableLayout)this.getLayout()).getDefaultFill();
   }

   public void setHgap(int var1) {
      ((TableLayout)this.getLayout()).setHgap(var1);
   }

   public int getHgap() {
      return ((TableLayout)this.getLayout()).getHgap();
   }

   public void setVgap(int var1) {
      ((TableLayout)this.getLayout()).setVgap(var1);
   }

   public int getVgap() {
      return ((TableLayout)this.getLayout()).getVgap();
   }
}
