package acm.graphics;

import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

public class GRoundRect extends GRect {
   public static final double DEFAULT_ARC = (double)10.0F;
   private double aWidth;
   private double aHeight;
   static final long serialVersionUID = 21L;

   public GRoundRect(double var1, double var3) {
      this((double)0.0F, (double)0.0F, var1, var3, (double)10.0F);
   }

   public GRoundRect(double var1, double var3, double var5, double var7) {
      this(var1, var3, var5, var7, (double)10.0F);
   }

   public GRoundRect(double var1, double var3, double var5, double var7, double var9) {
      this(var1, var3, var5, var7, var9, var9);
   }

   public GRoundRect(double var1, double var3, double var5, double var7, double var9, double var11) {
      super(var1, var3, var5, var7);
      this.aWidth = var9;
      this.aHeight = var11;
   }

   public double getArcWidth() {
      return this.aWidth;
   }

   public double getArcHeight() {
      return this.aHeight;
   }

   protected void paint2d(Graphics2D var1) {
      RoundRectangle2D.Double var2 = new RoundRectangle2D.Double((double)0.0F, (double)0.0F, this.getFrameWidth(), this.getFrameHeight(), this.aWidth, this.aHeight);
      if (this.isFilled()) {
         var1.setColor(this.getFillColor());
         var1.fill(var2);
         var1.setColor(this.getColor());
      }

      var1.draw(var2);
   }
}
