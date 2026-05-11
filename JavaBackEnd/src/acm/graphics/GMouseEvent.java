package acm.graphics;

import java.awt.Component;
import java.awt.event.MouseEvent;

class GMouseEvent extends MouseEvent {
   private Object effectiveSource;

   public GMouseEvent(Object var1, int var2, MouseEvent var3) {
      super(var3.getComponent(), var2, var3.getWhen(), var3.getModifiers(), var3.getX(), var3.getY(), var3.getClickCount(), var3.isPopupTrigger());
      this.effectiveSource = var1;
   }

   public Object getSource() {
      return this.effectiveSource;
   }

   public Component getComponent() {
      return (Component)super.getSource();
   }

   public String toString() {
      return this.getClass().getName() + "[" + this.paramString() + "] on " + this.getSource();
   }
}
