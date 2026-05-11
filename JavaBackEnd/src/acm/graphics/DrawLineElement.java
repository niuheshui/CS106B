package acm.graphics;

import java.awt.Graphics2D;

class DrawLineElement extends PathElement {
   private double deltaX;
   private double deltaY;

   public DrawLineElement(double var1, double var3) {
      this.deltaX = var1;
      this.deltaY = var3;
   }

   public void paint(Graphics2D var1, PathState var2) {
      int var3 = GMath.round(var2.sx * var2.cx);
      int var4 = GMath.round(var2.sy * var2.cy);
      var2.cx += this.deltaX;
      var2.cy += this.deltaY;
      int var5 = GMath.round(var2.sx * var2.cx);
      int var6 = GMath.round(var2.sy * var2.cy);
      if (var2.region == null) {
         var1.drawLine(var3, var4, var5, var6);
      } else {
         var2.region.addPoint(var5, var6);
      }

   }

   public void updateBounds(GRectangle var1, PathState var2) {
      if (var1.getWidth() < (double)0.0F) {
         var1.setBounds(var2.sx * var2.cx, var2.sy * var2.cy, (double)0.0F, (double)0.0F);
      } else {
         var1.add(var2.sx * var2.cx, var2.sy * var2.cy);
      }

      var2.cx += this.deltaX;
      var2.cy += this.deltaY;
      var1.add(var2.sx * var2.cx, var2.sy * var2.cy);
   }
}
