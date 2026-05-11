package acm.graphics;

import java.awt.Color;
import java.awt.Graphics2D;

class EndRegionElement extends PathElement {
   public EndRegionElement() {
   }

   public void paint(Graphics2D var1, PathState var2) {
      Color var3 = var1.getColor();
      var1.setColor(var2.fillColor);
      var1.fillPolygon(var2.region.xpoints, var2.region.ypoints, var2.region.npoints);
      var1.setColor(var3);
      var1.drawPolygon(var2.region.xpoints, var2.region.ypoints, var2.region.npoints);
      var2.region = null;
   }
}
