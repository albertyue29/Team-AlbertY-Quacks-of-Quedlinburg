import javax.swing.JFrame;

public class QuacksFrame extends JFrame {
   private static final int WIDTH = 1920;
   private static final int HEIGHT = 1080;

   public QuacksFrame(String title) {
      super(title);
      setSize(WIDTH, HEIGHT);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      add(new QuacksPanel());
      setVisible(true);
   }
}
