package acm.util;

import java.applet.Applet;
import java.awt.Component;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JScrollBar;
import javax.swing.JSlider;

public class Animator extends Thread {
   public static final int INITIAL = 0;
   public static final int RUNNING = 1;
   public static final int STEPPING = 2;
   public static final int CALLING = 3;
   public static final int STOPPING = 4;
   public static final int STOPPED = 5;
   public static final int FINISHED = 6;
   public static final int TERMINATING = 7;
   private static final double SLOW_DELAY = (double)1000.0F;
   private static final double CLIP_DELAY = (double)200.0F;
   private static final double FAST_DELAY = (double)0.0F;
   private static HashMap<Applet, ArrayList<Thread>> animatorTable = new HashMap();
   private int animatorState = 0;
   private int currentDepth = 0;
   private int callDepth = 0;
   private int delayCount = 0;
   private double animatorSpeed = (double)0.5F;
   private Component speedBar;
   private boolean resumed;

   public Animator() {
      this.initAnimator();
   }

   public Animator(ThreadGroup var1) {
      super(var1, (Runnable)null);
      this.initAnimator();
   }

   public Animator(Runnable var1) {
      super(var1);
      this.initAnimator();
   }

   public Animator(ThreadGroup var1, Runnable var2) {
      super(var1, var2);
      this.initAnimator();
   }

   public int getAnimatorState() {
      return this.animatorState;
   }

   public void pause(double var1) {
      if (this.animatorState == 7) {
         this.terminate();
      }

      JTFTools.pause(var1);
   }

   public void startAction() {
      this.start(1);
   }

   public void stopAction() {
      switch (this.animatorState) {
         case 1:
         case 2:
         case 3:
            this.animatorState = 4;
         default:
      }
   }

   public void stepAction() {
      this.start(2);
   }

   public void callAction() {
      this.callDepth = this.currentDepth;
      this.start(3);
   }

   public boolean buttonAction(String var1) {
      if (var1.equals("Start")) {
         this.startAction();
      } else if (var1.equals("Stop")) {
         this.stopAction();
      } else if (var1.equals("Step")) {
         this.stepAction();
      } else {
         if (!var1.equals("Call")) {
            return false;
         }

         this.callAction();
      }

      return true;
   }

   public void setSpeed(double var1) {
      this.animatorSpeed = var1;
      if (this.speedBar instanceof JSlider) {
         JSlider var3 = (JSlider)this.speedBar;
         int var4 = var3.getMinimum();
         int var5 = var3.getMaximum();
         var3.setValue((int)Math.round((double)var4 + var1 * (double)(var5 - var4)));
      } else if (this.speedBar instanceof JScrollBar) {
         JScrollBar var6 = (JScrollBar)this.speedBar;
         int var7 = var6.getMinimum();
         int var8 = var6.getMaximum();
         var6.setValue((int)Math.round((double)var7 + var1 * (double)(var8 - var7)));
      }

   }

   public double getSpeed() {
      return this.animatorSpeed;
   }

   public void trace() {
      this.trace(0);
   }

   public void trace(int var1) {
      if (Thread.currentThread() != this) {
         throw new ErrorException("trace() can be called only by the animator thread itself");
      } else {
         this.currentDepth = var1;
         switch (this.animatorState) {
            case 1:
               this.delay();
               break;
            case 2:
            case 4:
               this.breakpoint();
               break;
            case 3:
               if (this.callDepth < this.currentDepth) {
                  this.delay();
               } else {
                  this.breakpoint();
               }
            case 5:
            case 6:
            default:
               break;
            case 7:
               this.terminate();
         }

      }
   }

   public void breakpoint() {
      if (Thread.currentThread() != this) {
         throw new ErrorException("breakpoint() can be called only by the animator thread itself");
      } else {
         this.animatorState = 5;
         this.breakHook();
         this.suspendAnimator();
      }
   }

   public void delay() {
      boolean var1 = true;
      double var2 = (double)0.0F;
      if (this.animatorSpeed < (double)0.25F) {
         var2 = (double)1000.0F + this.animatorSpeed / (double)0.25F * (double)-800.0F;
      } else if (this.animatorSpeed < 0.9) {
         var2 = (double)200.0F + Math.sqrt((this.animatorSpeed - (double)0.25F) / 0.65) * (double)-200.0F;
      } else {
         switch ((int)(this.animatorSpeed * 99.99 - (double)90.0F)) {
            case 0:
               var1 = true;
               break;
            case 1:
               var1 = this.delayCount % 10 != 0;
               break;
            case 2:
               var1 = this.delayCount % 7 != 0;
               break;
            case 3:
               var1 = this.delayCount % 5 != 0;
               break;
            case 4:
               var1 = this.delayCount % 3 != 0;
               break;
            case 5:
               var1 = this.delayCount % 2 == 0;
               break;
            case 6:
               var1 = this.delayCount % 3 == 0;
               break;
            case 7:
               var1 = this.delayCount % 4 == 0;
               break;
            case 8:
               var1 = this.delayCount % 6 == 0;
               break;
            case 9:
               var1 = false;
         }

         this.delayCount = (this.delayCount + 1) % 420;
      }

      if (var1) {
         this.delayHook();
         JTFTools.pause(var2);
      }

   }

   public void registerSpeedBar(JSlider var1) {
      SpeedBarListener.register(this, var1);
      this.speedBar = var1;
   }

   public void registerSpeedBar(JScrollBar var1) {
      SpeedBarListener.register(this, var1);
      this.speedBar = var1;
   }

   public Component getSpeedBar() {
      return this.speedBar;
   }

   public void requestTermination() {
      this.animatorState = 7;
   }

   public void checkForTermination() {
      if (this.animatorState == 7) {
         this.terminate();
      } else {
         yield();
      }

   }

   public static void shutdown(Applet var0) {
      try {
         Class var1 = Class.forName("java.lang.Thread");
         Method var2 = var1.getMethod("stop");
         Object[] var3 = new Object[0];
         ArrayList var4 = (ArrayList)animatorTable.get(var0);
         if (var4 != null) {
            animatorTable.remove(var0);
            int var5 = var4.size();

            for(int var6 = 0; var6 < var5; ++var6) {
               Thread var7 = (Thread)var4.get(var6);
               var2.invoke(var7, var3);
            }
         }
      } catch (Exception var8) {
      }

   }

   protected void delayHook() {
   }

   protected void breakHook() {
   }

   protected void resumeHook() {
   }

   protected void controllerHook() {
   }

   public void start() {
      this.start(1);
   }

   private void initAnimator() {
      Applet var1 = JTFTools.getApplet();
      if (var1 != null) {
         JTFTools.registerApplet(var1, this);
         ArrayList var2 = (ArrayList)animatorTable.get(var1);
         if (var2 == null) {
            var2 = new ArrayList();
            animatorTable.put(var1, var2);
         }

         var2.add(this);
      }

   }

   private void start(int var1) {
      switch (this.animatorState) {
         case 0:
         case 6:
            this.animatorState = var1;
            this.resumeHook();
            this.controllerHook();
            super.start();
            break;
         case 5:
            this.animatorState = var1;
            this.resumeHook();
            this.controllerHook();
            this.resumeAnimator();
      }

   }

   private synchronized void suspendAnimator() {
      this.resumed = false;

      while(!this.resumed) {
         try {
            this.wait();
         } catch (InterruptedException var2) {
         }
      }

   }

   private synchronized void resumeAnimator() {
      this.resumed = true;
      this.notifyAll();
   }

   private void terminate() {
      this.animatorState = 6;
      if (Thread.currentThread() == this) {
         throw new ThreadDeath();
      } else {
         throw new ErrorException("Illegal call to terminate");
      }
   }
}
