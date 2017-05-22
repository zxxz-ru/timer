/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timer;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author zxxz
 */
public class MyTimerRun {
  public static void main(String[] a){
   try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                   MyTimer  t = new MyTimer();
                   t.setVisible(true);
                }
            });
        } catch (Exception e) { 
            System.err.println("createGUI didn't successfully complete\n");
            e.printStackTrace();
        }

  }
}
