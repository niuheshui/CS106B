package acm.graphics;

import java.awt.Graphics2D;
import java.io.Serializable;

abstract class PathElement implements Serializable {
   public PathElement() {
   }

   public void paint(Graphics2D var1, PathState var2) {
   }

   public void updateBounds(GRectangle var1, PathState var2) {
   }
}
