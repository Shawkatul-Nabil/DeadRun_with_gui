import javax.swing.*;
import java.awt.*;

public class HelpDialog extends JDialog {
    public HelpDialog(JFrame parent) {
        super(parent, "How to Play", true);
        setSize(500, 320);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
        textArea.setText(
                "Welcome to Dead Run!\n\n" +
                "Controls:\n" +
                "  W / Up Arrow    - Move Up\n" +
                "  S / Down Arrow  - Move Down\n" +
                "  A / Left Arrow  - Move Left\n" +
                "  D / Right Arrow - Move Right\n\n" +
                "Objective:\n" +
                "Reach the safe zone at the bottom without touching zombies.\n" +
                "Every successful crossing increases your score.\n\n" +
                "Difficulty:\n" +
                "Easy   - slower gameplay\n" +
                "Medium - balanced gameplay\n" +
                "Hard   - faster zombies and more danger\n"
        );

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());

        add(new JScrollPane(textArea), BorderLayout.CENTER);
        add(closeButton, BorderLayout.SOUTH);

        setVisible(true);
    }
}