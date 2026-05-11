package acm.program;

import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class OldStyleMenuItem extends MenuItem implements ActionListener, ChangeListener {
   private JMenuItem twin;

   public OldStyleMenuItem(JMenuItem var1) {
      super(var1.getText());
      this.twin = var1;
      this.addActionListener(this);
      this.twin.addChangeListener(this);
      this.setEnabled(this.twin.isEnabled());
      KeyStroke var2 = this.twin.getAccelerator();
      if (var2 != null) {
         this.setShortcut(this.createShortcut(var2));
      }

   }

   public void actionPerformed(ActionEvent var1) {
      this.twin.doClick(0);
   }

   public void stateChanged(ChangeEvent var1) {
      this.setEnabled(this.twin.isEnabled());
   }

   private MenuShortcut createShortcut(KeyStroke var1) {
      boolean var2 = (var1.getModifiers() & 1) != 0;
      return new MenuShortcut(var1.getKeyCode(), var2);
   }
}
