package acm.util;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.lang.reflect.Method;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class SpeedBarListener implements AdjustmentListener, ChangeListener {
   private Animator animator;
   private Object speedBar;
   private Method getValue;
   private Method getMinimum;
   private Method getMaximum;

   public static void register(Animator var0, Object var1) {
      SpeedBarListener var2 = new SpeedBarListener();
      var2.animator = var0;
      var2.speedBar = var1;
      Class var3 = var1.getClass();
      Method var4 = lookForMethod(var3, "addAdjustmentListener");
      if (var4 == null) {
         var4 = lookForMethod(var3, "addChangeListener");
      }

      if (var4 == null) {
         var4 = lookForMethod(var3, "addChangeListener");
      }

      try {
         var2.getValue = var3.getMethod("getValue");
         var2.getMinimum = var3.getMethod("getMinimum");
         var2.getMaximum = var3.getMethod("getMaximum");
         Object[] var5 = new Object[]{var2};
         var4.invoke(var1, var5);
      } catch (Exception var6) {
         throw new ErrorException("Illegal speed bar object");
      }

      var2.setSpeed();
   }

   public void adjustmentValueChanged(AdjustmentEvent var1) {
      this.setSpeed();
   }

   public void stateChanged(ChangeEvent var1) {
      this.setSpeed();
   }

   public void setSpeed() {
      try {
         int var1 = (Integer)this.getMinimum.invoke(this.speedBar);
         int var2 = (Integer)this.getMaximum.invoke(this.speedBar);
         int var3 = (Integer)this.getValue.invoke(this.speedBar);
         double var4 = (double)(var3 - var1) / (double)(var2 - var1);
         this.animator.setSpeed(var4);
      } catch (Exception var6) {
         throw new ErrorException(var6);
      }
   }

   private static Method lookForMethod(Class<?> var0, String var1) {
      Method[] var2 = var0.getMethods();

      for(int var3 = 0; var3 < var2.length; ++var3) {
         if (var1.equals(var2[var3].getName())) {
            return var2[var3];
         }
      }

      return null;
   }
}
