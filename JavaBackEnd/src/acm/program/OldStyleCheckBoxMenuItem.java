package acm.program;

import java.awt.CheckboxMenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class OldStyleCheckBoxMenuItem extends CheckboxMenuItem implements ActionListener, ChangeListener {
   private JCheckBoxMenuItem twin;

   public OldStyleCheckBoxMenuItem(JCheckBoxMenuItem var1) {
      super(var1.getText());
      this.twin = var1;
      this.addActionListener(this);
      this.twin.addChangeListener(this);
      this.setState(this.twin.getState());
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
      this.setState(this.twin.getState());
      this.setEnabled(this.twin.isEnabled());
   }

   private MenuShortcut createShortcut(KeyStroke var1) {
      boolean var2 = (var1.getModifiers() & 1) != 0;
      return new MenuShortcut(var1.getKeyCode(), var2);
   }
}
