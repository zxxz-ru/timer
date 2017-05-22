/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author zxxz
 */
public class MyTimer extends JFrame implements ActionListener {

  private JPanel panel = new JPanel(new BorderLayout());
  private JLabel label = new JLabel();
  private JLabel greeting = new JLabel();
  private Font font = new Font(Font.DIALOG, Font.BOLD, 14);
  private GregorianCalendar calendar = new GregorianCalendar();
  private String[] names = {", Славик. ", ", Мастер. ", ", Медвеженок. ",
    ", Самый самый. ", ", zxxz. ", ". ", ", \"Умник\". ", ", Shtirlitz. ",
    ", Лентяй хренов. ", ", Какашка. ", ", Мidvezhonak . ", ", Славик. ", ", Казёл похотливый. "};
  Random random;
  Timer timer = new Timer(1000, this);
  Timer foldTime = new Timer(5000, this);
  int wth = 605;

  public MyTimer() {
    random = new Random();
    Dimension size = new Dimension(wth, 25);
    this.setTitle("ZClock");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(size);
    this.setPreferredSize(size);
    this.setResizable(true);
    this.addWindowListener(new MyWindowAdapter());
    senterOnScreen(wth);
    this.setUndecorated(true);

    panel.setSize(size);
    this.setContentPane(panel);
    label.setFont(font);
    label.setSize(130, 25);
    label.setText(printTime(System.currentTimeMillis()));
    label.setOpaque(false);
    greeting.setSize(150, 25);
    greeting.setFont(font);
    greeting.setOpaque(false);
    printGreetings(System.currentTimeMillis());
    panel.add(greeting, BorderLayout.CENTER);
    panel.add(label, BorderLayout.LINE_END);
    timer.setActionCommand("timer");
    timer.start();
    foldTime.setActionCommand("fold");
    foldTime.start();
  }

  private final void senterOnScreen(int w) {
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    Point cp = ge.getCenterPoint();
    int x = cp.x;
    int cf = x - (w / 2);
    setLocation(cf, 0);
  }
  
  final void printGreetings(long time) {
    int i = random.nextInt(names.length);
    String n = names[i];
    Rectangle2D rec = getWidthAdd(n);
    Number numb = rec.getWidth();
    int pl = numb.intValue();
    Rectangle r = this.getBounds();
    r.width = r.width + pl + 20;
    this.setBounds(r);
    this.greeting.setBounds(rec.getBounds());

    String t = "Привет" + n + " Cегодня " + printFormattedDate(time, "EEEE, d") + " of "
            + printFormattedDate(time, "MMMM, yyyy") + " это " + printFormattedDate(time, "D ") + "день года.  ";
    greeting.setText(t);
    greeting.repaint();
  }//printTime

  final String printFormattedDate(long time, String patern) {
    SimpleDateFormat dateFormat = new SimpleDateFormat();
    dateFormat.applyPattern(patern);
    return dateFormat.format(new Date(time));
  }//printDate

  private Rectangle2D getWidthAdd(String text) {
    BufferedImage im = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = (Graphics2D) im.getGraphics();
    FontRenderContext frc = g.getFontRenderContext();
    TextLayout layout = new TextLayout(text, font, frc);
    Rectangle2D bounds = layout.getBounds();
    return bounds;
  }//getWidthAdd

  final String printTime(long time) {
    SimpleDateFormat dateFormat = new SimpleDateFormat();
    dateFormat.applyPattern("HH:mm:ss");
    return dateFormat.format(new Date(time));
  }//printTime

  private void showShortMessage() {
    long time = System.currentTimeMillis();
    String pattern = "EEE, MMM dd, ";
    String text = printFormattedDate(time, pattern);
    this.greeting.setText(text);
    Rectangle2D rectangle2D = getWidthAdd(text);
    Number number = rectangle2D.getWidth();
    int plainInt = number.intValue();//value for width of text
    Rectangle palainBounds = this.getBounds();
    Rectangle clockBounds = this.label.getBounds();
    palainBounds.width = plainInt + clockBounds.width + 15; //width of text area and clock area
    //this.panel.setBounds(palainBounds);
    this.setBounds(palainBounds);
    this.greeting.setBounds(rectangle2D.getBounds());//set's the width for text area.
    senterOnScreen(palainBounds.width);
    this.greeting.repaint();
  }

  @Override
  public void actionPerformed(ActionEvent ae) {
    if (ae.getActionCommand().equals("timer")) {
      label.setText(printTime(System.currentTimeMillis()));
      label.repaint();
    }
    if (ae.getActionCommand().equals("fold")) {
      foldTime.stop();
      showShortMessage();
    }
  }

  private class MyWindowAdapter extends WindowAdapter {

    @Override
    public void windowOpened(WindowEvent we) {
    }

    @Override
    public void windowClosing(WindowEvent we) {
    }

    @Override
    public void windowClosed(WindowEvent we) {
    }

    @Override
    public void windowIconified(WindowEvent we) {
      timer.stop();
    }

    @Override
    public void windowDeiconified(WindowEvent we) {
      timer.restart();
    }

    @Override
    public void windowActivated(WindowEvent we) {
      timer.restart();
    }

    @Override
    public void windowDeactivated(WindowEvent we) {
      timer.stop();
    }
  }//MywindowAdapter
}
