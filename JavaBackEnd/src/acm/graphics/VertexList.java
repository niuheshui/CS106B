package acm.graphics;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;

class VertexList implements Serializable {
   private ArrayList<GPoint> vertices;
   private double cx;
   private double cy;

   public VertexList() {
      this.vertices = new ArrayList();
      this.cx = (double)0.0F;
      this.cy = (double)0.0F;
   }

   public VertexList(VertexList var1) {
      this();

      for(int var2 = 0; var2 < var1.vertices.size(); ++var2) {
         this.vertices.add(var1.vertices.get(var2));
      }

   }

   public synchronized void addVertex(double var1, double var3) {
      this.cx = var1;
      this.cy = var3;
      this.vertices.add(new GPoint(this.cx, this.cy));
   }

   public synchronized void addEdge(double var1, double var3) {
      this.cx += var1;
      this.cy += var3;
      this.vertices.add(new GPoint(this.cx, this.cy));
   }

   public void addArc(double var1, double var3, double var5, double var7) {
      double var9 = var3 / var1;
      double var11 = var1 / (double)2.0F;
      double var13 = var3 / (double)2.0F;
      double var15 = this.cx - var11 * GMath.cosDegrees(var5);
      double var17 = this.cy + var13 * GMath.sinDegrees(var5);
      if (var7 > 359.99) {
         var7 = (double)360.0F;
      }

      if (var7 < -359.99) {
         var7 = (double)-360.0F;
      }

      double var19 = Math.atan2((double)1.0F, Math.max(var1, var3));
      int var21 = (int)(GMath.toRadians(Math.abs(var7)) / var19);
      var19 = GMath.toRadians(var7) / (double)var21;
      double var22 = GMath.toRadians(var5);

      for(int var24 = 0; var24 < var21; ++var24) {
         var22 += var19;
         double var25 = var15 + var11 * Math.cos(var22);
         double var27 = var17 - var11 * Math.sin(var22) * var9;
         this.addVertex(var25, var27);
      }

   }

   public synchronized void add(GPoint[] var1) {
      for(int var2 = 0; var2 < var1.length; ++var2) {
         this.vertices.add(new GPoint(var1[var2].getX(), var1[var2].getY()));
      }

   }

   public synchronized void remove(GPoint var1) {
      this.vertices.remove(var1);
   }

   public synchronized void clear() {
      this.vertices.clear();
   }

   public int size() {
      return this.vertices.size();
   }

   GPoint get(int var1) {
      return (GPoint)this.vertices.get(var1);
   }

   public GPoint getCurrentPoint() {
      return this.vertices.size() == 0 ? null : new GPoint(this.cx, this.cy);
   }

   public synchronized GRectangle getBounds(AffineTransform var1) {
      int var2 = this.vertices.size();
      if (var2 == 0) {
         return new GRectangle();
      } else {
         double var3 = (double)0.0F;
         double var5 = (double)0.0F;
         double var7 = (double)0.0F;
         double var9 = (double)0.0F;
         boolean var11 = true;

         for(int var12 = 0; var12 < this.vertices.size(); ++var12) {
            GPoint var13 = (GPoint)this.vertices.get(var12);
            Point2D.Double var14 = new Point2D.Double(var13.getX(), var13.getY());
            if (var1 != null) {
               var1.transform(var14, var14);
            }

            double var15 = ((Point2D)var14).getX();
            double var17 = ((Point2D)var14).getY();
            if (var11) {
               var3 = var15;
               var5 = var15;
               var7 = var17;
               var9 = var17;
               var11 = false;
            } else {
               var3 = Math.min(var3, var15);
               var5 = Math.max(var5, var15);
               var7 = Math.min(var7, var17);
               var9 = Math.max(var9, var17);
            }
         }

         return new GRectangle(var3, var7, var5 - var3, var9 - var7);
      }
   }

   public synchronized boolean contains(double var1, double var3, AffineTransform var5) {
      int var6 = this.vertices.size();
      boolean var7 = false;

      for(int var8 = 0; var8 < var6; ++var8) {
         GPoint var9 = (GPoint)this.vertices.get(var8);
         Point2D.Double var10 = new Point2D.Double(var9.getX(), var9.getY());
         if (var5 != null) {
            var5.transform(var10, var10);
         }

         var9 = (GPoint)this.vertices.get((var8 + 1) % var6);
         Point2D.Double var11 = new Point2D.Double(var9.getX(), var9.getY());
         if (var5 != null) {
            var5.transform(var11, var11);
         }

         if ((((Point2D)var10).getY() < var3 && ((Point2D)var11).getY() >= var3 || ((Point2D)var11).getY() < var3 && ((Point2D)var10).getY() >= var3) && ((Point2D)var10).getX() + (var3 - ((Point2D)var10).getY()) / (((Point2D)var11).getY() - ((Point2D)var10).getY()) * (((Point2D)var11).getX() - ((Point2D)var10).getX()) < var1) {
            var7 = !var7;
         }
      }

      return var7;
   }

   public void recenter() {
      double var1 = (double)0.0F;
      double var3 = (double)0.0F;
      double var5 = (double)0.0F;
      double var7 = (double)0.0F;
      boolean var9 = true;

      for(int var10 = 0; var10 < this.vertices.size(); ++var10) {
         GPoint var11 = (GPoint)this.vertices.get(var10);
         if (var9) {
            var1 = var11.getX();
            var3 = var11.getX();
            var5 = var11.getY();
            var7 = var11.getY();
            var9 = false;
         } else {
            var1 = Math.min(var1, var11.getX());
            var3 = Math.max(var3, var11.getX());
            var5 = Math.min(var5, var11.getY());
            var7 = Math.max(var7, var11.getY());
         }
      }

      double var16 = (var1 + var3) / (double)2.0F;
      double var12 = (var5 + var7) / (double)2.0F;

      for(int var14 = 0; var14 < this.vertices.size(); ++var14) {
         GPoint var15 = (GPoint)this.vertices.get(var14);
         var15.translate(-var16, -var12);
      }

   }
}
