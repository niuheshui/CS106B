package acm.graphics;

import acm.util.ErrorException;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

public class GPolygon extends GObject implements GFillable {
   private VertexList vertices;
   private boolean complete;
   private boolean isFilled;
   private Color fillColor;
   static final long serialVersionUID = 21L;

   public GPolygon() {
      this.vertices = new VertexList();
      this.clear();
   }

   public GPolygon(double var1, double var3) {
      this();
      this.setLocation(var1, var3);
   }

   public GPolygon(GPoint[] var1) {
      this();
      this.vertices.add(var1);
      this.markAsComplete();
   }

   public void addVertex(double var1, double var3) {
      if (this.complete) {
         throw new ErrorException("You can't add vertices to a GPolygon that has been marked as complete.");
      } else {
         this.vertices.addVertex(var1, var3);
      }
   }

   public void addEdge(double var1, double var3) {
      if (this.complete) {
         throw new ErrorException("You can't add edges to a GPolygon that has been marked as complete.");
      } else {
         this.vertices.addEdge(var1, var3);
      }
   }

   public final void addPolarEdge(double var1, double var3) {
      if (this.complete) {
         throw new ErrorException("You can't add edges to a GPolygon that has been marked as complete.");
      } else {
         this.vertices.addEdge(var1 * GMath.cosDegrees(var3), -var1 * GMath.sinDegrees(var3));
      }
   }

   public void addArc(double var1, double var3, double var5, double var7) {
      if (this.complete) {
         throw new ErrorException("You can't add edges to a GPolygon that has been marked as complete.");
      } else {
         this.vertices.addArc(var1, var3, var5, var7);
      }
   }

   public GPoint getCurrentPoint() {
      return this.vertices.getCurrentPoint();
   }

   public void setFilled(boolean var1) {
      this.isFilled = var1;
      this.repaint();
   }

   public boolean isFilled() {
      return this.isFilled;
   }

   public void setFillColor(Color var1) {
      this.fillColor = var1;
      this.repaint();
   }

   public Color getFillColor() {
      return this.fillColor == null ? this.getColor() : this.fillColor;
   }

   public GRectangle getBounds() {
      GRectangle var1 = this.vertices.getBounds(this.getMatrix());
      return new GRectangle(var1.getX() + this.getX(), var1.getY() + this.getY(), var1.getWidth(), var1.getHeight());
   }

   public boolean contains(double var1, double var3) {
      return this.vertices.contains(var1 - this.getX(), var3 - this.getY(), this.getMatrix());
   }

   protected void paint2d(Graphics2D var1) {
      int var2 = this.vertices.size();
      Path2D.Double var3 = new Path2D.Double(0);
      ((Path2D)var3).moveTo(this.vertices.get(0).getX(), this.vertices.get(0).getY());

      for(int var4 = 0; var4 < var2; ++var4) {
         ((Path2D)var3).lineTo(this.vertices.get(var4).getX(), this.vertices.get(var4).getY());
      }

      ((Path2D)var3).lineTo(this.vertices.get(0).getX(), this.vertices.get(0).getY());
      if (this.isFilled()) {
         var1.setColor(this.getFillColor());
         var1.fill(var3);
         var1.setColor(this.getColor());
      }

      var1.draw(var3);
   }

   public void recenter() {
      this.vertices.recenter();
   }

   public Object clone() {
      try {
         GPolygon var1 = (GPolygon)super.clone();
         var1.vertices = new VertexList(var1.vertices);
         return var1;
      } catch (Exception var2) {
         throw new ErrorException("Impossible exception");
      }
   }

   protected void markAsComplete() {
      this.complete = true;
   }

   protected void clear() {
      if (this.complete) {
         throw new ErrorException("You can't clear a GPolygon that has been marked as complete.");
      } else {
         this.vertices.clear();
      }
   }
}
