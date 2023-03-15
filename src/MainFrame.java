import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{
    public MainFrame() {
        setTitle("Equation Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        EquationPanel panel = new EquationPanel();
        panel.setPreferredSize(new Dimension(500, 250));
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
    }
}
