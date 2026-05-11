package acm.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

class StartRegionElement extends PathElement {
   private Color color;

   public StartRegionElement(Color var1) {
      this.color = var1;
   }

   public void paint(Graphics2D var1, PathState var2) {
      var2.region = new Polygon();
      var2.fillColor = this.color;
   }
}
