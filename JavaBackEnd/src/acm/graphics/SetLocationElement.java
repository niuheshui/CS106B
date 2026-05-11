package acm.graphics;

import java.awt.Graphics2D;

class SetLocationElement extends PathElement {
   private double cx;
   private double cy;

   public SetLocationElement(double var1, double var3) {
      this.cx = var1;
      this.cy = var3;
   }

   public void paint(Graphics2D var1, PathState var2) {
      var2.cx = this.cx;
      var2.cy = this.cy;
      if (var2.region != null) {
         var2.region.addPoint(GMath.round(var2.sx * this.cx), GMath.round(var2.sy * this.cy));
      }

   }

   public void updateBounds(GRectangle var1, PathState var2) {
      var2.cx = this.cx;
      var2.cy = this.cy;
   }
}
