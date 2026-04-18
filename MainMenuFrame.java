import javax.swing.*;
import java.awt.*;

public class MainMenuFrame extends JFrame {

    public MainMenuFrame() {
        setTitle("Dead Run");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        showMainMenu();

        setVisible(true);
    }

    private void showMainMenu() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(20, 20, 20));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("DEAD RUN");
        title.setForeground(new Color(0, 220, 120));
        title.setFont(new Font("Arial", Font.BOLD, 42));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Cross the lanes. Avoid the zombies.");
        subtitle.setForeground(Color.WHITE);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startBtn = createMenuButton("Start Game");
        JButton helpBtn = createMenuButton("How to Play");
        JButton exitBtn = createMenuButton("Exit");

        startBtn.addActionListener(e -> showDifficultyMenu());
        helpBtn.addActionListener(e -> new HelpDialog(this));
        exitBtn.addActionListener(e -> System.exit(0));

        panel.add(Box.createVerticalStrut(80));
        panel.add(title);
        panel.add(Box.createVerticalStrut(10));
        panel.add(subtitle);
        panel.add(Box.createVerticalStrut(60));
        panel.add(startBtn);
        panel.add(Box.createVerticalStrut(20));
        panel.add(helpBtn);
        panel.add(Box.createVerticalStrut(20));
        panel.add(exitBtn);

        setContentPane(panel);
        revalidate();
        repaint();
    }

    private void showDifficultyMenu() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(15, 15, 15));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Select Difficulty");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 34));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton easyBtn = createMenuButton("Easy");
        JButton mediumBtn = createMenuButton("Medium");
        JButton hardBtn = createMenuButton("Hard");
        JButton backBtn = createMenuButton("Back");

        easyBtn.addActionListener(e -> startGame(Difficulty.EASY));
        mediumBtn.addActionListener(e -> startGame(Difficulty.MEDIUM));
        hardBtn.addActionListener(e -> startGame(Difficulty.HARD));
        backBtn.addActionListener(e -> showMainMenu());

        panel.add(Box.createVerticalStrut(80));
        panel.add(title);
        panel.add(Box.createVerticalStrut(50));
        panel.add(easyBtn);
        panel.add(Box.createVerticalStrut(20));
        panel.add(mediumBtn);
        panel.add(Box.createVerticalStrut(20));
        panel.add(hardBtn);
        panel.add(Box.createVerticalStrut(20));
        panel.add(backBtn);

        setContentPane(panel);
        revalidate();
        repaint();
    }

    private void startGame(Difficulty difficulty) {
        setContentPane(new GamePanel(this, difficulty));
        revalidate();
        repaint();
    }

    public void returnToMenu() {
        showMainMenu();
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(220, 50));
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setFocusPainted(false);
        return btn;
    }
}