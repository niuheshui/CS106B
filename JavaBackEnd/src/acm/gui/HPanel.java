package acm.gui;

public class HPanel extends TablePanel {
   static final long serialVersionUID = 1L;

   public HPanel() {
      this(0, 0);
   }

   public HPanel(int var1, int var2) {
      TableLayout var3 = new TableLayout(1, 0, var1, var2);
      var3.setHorizontalAlignment(1);
      var3.setVerticalAlignment(1);
      this.setLayout(var3);
   }
}
