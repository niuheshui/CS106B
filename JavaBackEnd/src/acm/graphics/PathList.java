package acm.graphics;

import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;

class PathList implements Serializable {
   private static final PathElement FINAL_PATH_ELEMENT = new FinalPathElement();
   private ArrayList<PathElement> path = new ArrayList();

   public PathList() {
   }

   public synchronized void add(PathElement var1) {
      this.path.add(var1);
   }

   public synchronized void add(PathElement var1, PathElement var2) {
      this.path.add(var1);
      this.path.add(var2);
   }

   public synchronized void remove(PathElement var1) {
      this.path.remove(var1);
   }

   public synchronized void clear() {
      this.path.clear();
   }

   public int getElementCount() {
      return this.path.size();
   }

   public PathElement getElement(int var1) {
      return (PathElement)this.path.get(var1);
   }

   public synchronized GRectangle getBounds(PathState var1) {
      GRectangle var2 = new GRectangle((double)-1.0F, (double)-1.0F, (double)-1.0F, (double)-1.0F);
      int var3 = this.path.size();

      for(int var4 = 0; var4 < var3; ++var4) {
         PathElement var5 = (PathElement)this.path.get(var4);
         var5.updateBounds(var2, var1);
      }

      return var2;
   }

   public synchronized void mapPaint(Graphics2D var1, PathState var2) {
      int var3 = this.path.size();

      for(int var4 = 0; var4 < var3; ++var4) {
         PathElement var5 = (PathElement)this.path.get(var4);
         var5.paint(var1, var2);
      }

      FINAL_PATH_ELEMENT.paint(var1, var2);
   }
}
