package acm.graphics;

import acm.util.ErrorException;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class GRect extends GObject implements GFillable, GResizable {
   private double frameWidth;
   private double frameHeight;
   private boolean isFilled;
   private Color fillColor;
   static final long serialVersionUID = 21L;

   public GRect(double var1, double var3) {
      this((double)0.0F, (double)0.0F, var1, var3);
   }

   public GRect(double var1, double var3, double var5, double var7) {
      this.frameWidth = var5;
      this.frameHeight = var7;
      this.setLocation(var1, var3);
   }

   protected void paint2d(Graphics2D var1) {
      Rectangle2D.Double var2 = new Rectangle2D.Double((double)0.0F, (double)0.0F, this.frameWidth, this.frameHeight);
      if (this.isFilled()) {
         var1.setColor(this.getFillColor());
         var1.fill(var2);
         var1.setColor(this.getColor());
      }

      var1.draw(var2);
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

   public void setSize(double var1, double var3) {
      if (this.getMatrix() != null) {
         throw new ErrorException("setSize: Object has been transformed");
      } else {
         this.frameWidth = var1;
         this.frameHeight = var3;
         this.repaint();
      }
   }

   public final void setSize(GDimension var1) {
      this.setSize(var1.getWidth(), var1.getHeight());
   }

   public GDimension getSize() {
      return new GDimension(this.frameWidth, this.frameHeight);
   }

   public void setBounds(double var1, double var3, double var5, double var7) {
      if (this.getMatrix() != null) {
         throw new ErrorException("setBounds: Object has been transformed");
      } else {
         this.frameWidth = var5;
         this.frameHeight = var7;
         this.setLocation(var1, var3);
      }
   }

   public final void setBounds(GRectangle var1) {
      if (this.getMatrix() != null) {
         throw new ErrorException("setBounds: Object has been transformed");
      } else {
         this.setBounds(var1.getX(), var1.getY(), var1.getWidth(), var1.getHeight());
      }
   }

   public GRectangle getBounds() {
      Object var1 = new Rectangle2D.Double((double)0.0F, (double)0.0F, this.frameWidth, this.frameHeight);
      AffineTransform var2 = this.getMatrix();
      if (var2 != null) {
         var1 = var2.createTransformedShape((Shape)var1).getBounds();
      }

      Rectangle2D var3 = (Rectangle2D)var1;
      return new GRectangle(var3.getX() + this.getX(), var3.getY() + this.getY(), var3.getWidth(), var3.getHeight());
   }

   public boolean contains(double var1, double var3) {
      Object var5 = new Rectangle2D.Double((double)0.0F, (double)0.0F, this.frameWidth, this.frameHeight);
      AffineTransform var6 = this.getMatrix();
      if (var6 != null) {
         var5 = var6.createTransformedShape((Shape)var5);
      }

      return ((Shape)var5).contains(var1 - this.getX(), var3 - this.getY());
   }

   protected double getFrameWidth() {
      return this.frameWidth;
   }

   protected double getFrameHeight() {
      return this.frameHeight;
   }
}
