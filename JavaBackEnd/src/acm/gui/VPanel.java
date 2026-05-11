package acm.gui;

public class VPanel extends TablePanel {
   static final long serialVersionUID = 1L;

   public VPanel() {
      this(0, 0);
   }

   public VPanel(int var1, int var2) {
      TableLayout var3 = new TableLayout(0, 1, var1, var2);
      var3.setHorizontalAlignment(1);
      var3.setVerticalAlignment(1);
      this.setLayout(var3);
   }
}
