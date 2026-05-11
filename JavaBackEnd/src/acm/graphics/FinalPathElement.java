package acm.graphics;

import java.awt.Graphics2D;

class FinalPathElement extends PathElement {
   public FinalPathElement() {
   }

   public void paint(Graphics2D var1, PathState var2) {
      if (var2.region != null) {
         var1.drawPolyline(var2.region.xpoints, var2.region.ypoints, var2.region.npoints);
      }

   }
}
