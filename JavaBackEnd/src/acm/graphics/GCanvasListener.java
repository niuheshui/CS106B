package acm.graphics;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

class GCanvasListener implements MouseListener, MouseMotionListener {
   private GCanvas gCanvas;

   public GCanvasListener(GCanvas var1) {
      this.gCanvas = var1;
   }

   public void mouseClicked(MouseEvent var1) {
      this.gCanvas.dispatchMouseEvent(var1);
   }

   public void mousePressed(MouseEvent var1) {
      this.gCanvas.requestFocus();
      this.gCanvas.dispatchMouseEvent(var1);
   }

   public void mouseReleased(MouseEvent var1) {
      this.gCanvas.dispatchMouseEvent(var1);
   }

   public void mouseEntered(MouseEvent var1) {
      this.gCanvas.dispatchMouseEvent(var1);
   }

   public void mouseExited(MouseEvent var1) {
      this.gCanvas.dispatchMouseEvent(var1);
   }

   public void mouseDragged(MouseEvent var1) {
      this.gCanvas.dispatchMouseEvent(var1);
   }

   public void mouseMoved(MouseEvent var1) {
      this.gCanvas.dispatchMouseEvent(var1);
   }
}
