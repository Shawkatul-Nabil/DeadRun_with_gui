import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class GamePanel extends JPanel {
    private final MainMenuFrame parentFrame;
    private final Difficulty difficulty;
    
    private final int width = 20;
    private final int height = 12;
    private final int tileSize = 35;
    private final int topHudHeight = 70;

    private final Player player;
    private final ZombieLane[] lanes;
    private final Random random;

    private Timer timer;
    private int score;
    private boolean gameOver;

    private final JButton restartButton;
    private final JButton menuButton;

    public GamePanel(MainMenuFrame parentFrame, Difficulty difficulty) {
        this.parentFrame = parentFrame;
        this.difficulty = difficulty;
        this.random = new Random();
        this.player = new Player(width);
        this.lanes = new ZombieLane[height];
        this.score = 0;
        this.gameOver = false;

        setLayout(null);
        setFocusable(true);
        setBackground(Color.BLACK);

        for (int i = 0; i < height; i++) {
            lanes[i] = new ZombieLane(width, random);
        }

        restartButton = new JButton("Restart");
        menuButton = new JButton("Main Menu");

        restartButton.setBounds(180, topHudHeight + height * tileSize + 10, 120, 35);
        menuButton.setBounds(320, topHudHeight + height * tileSize + 10, 140, 35);

        restartButton.addActionListener(e -> restartGame());
        menuButton.addActionListener(e -> {
            stopGame();
            parentFrame.returnToMenu();
        });

        add(restartButton);
        add(menuButton);

        setupKeyBindings();
        startGameLoop();
        requestFocusInWindow();
    }

    private void setupKeyBindings() {
        bindKey("UP", () -> movePlayer(0, -1));
        bindKey("W", () -> movePlayer(0, -1));
        bindKey("DOWN", () -> movePlayer(0, 1));
        bindKey("S", () -> movePlayer(0, 1));
        bindKey("LEFT", () -> movePlayer(-1, 0));
        bindKey("A", () -> movePlayer(-1, 0));
        bindKey("RIGHT", () -> movePlayer(1, 0));
        bindKey("D", () -> movePlayer(1, 0));
    }

    private void bindKey(String key, Runnable action) {
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key), key);
        getActionMap().put(key, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameOver) {
                    action.run();
                    repaint();
                }
            }
        });
    }

    private void movePlayer(int dx, int dy) {
        int newX = player.getX() + dx;
        int newY = player.getY() + dy;

        if (newX >= 0 && newX < width) player.setX(newX);
        if (newY >= 0 && newY < height) player.setY(newY);

        checkCollision();
        checkWin();
    }

    private void startGameLoop() {
        timer = new Timer(difficulty.getTimerDelay(), e -> {
            updateGame();
            repaint();
        });
        timer.start();
    }

    private void updateGame() {
        if (gameOver) return;

        for (int i = 1; i < height - 1; i++) {
            if (random.nextInt(difficulty.getSpawnRate()) == 1) {
                lanes[i].move();
            }
        }

        checkCollision();
    }

    private void checkCollision() {
        int px = player.getX();
        int py = player.getY();

        if (py > 0 && py < height - 1 && lanes[py].hasZombieAt(px)) {
            gameOver = true;
            timer.stop();

            SwingUtilities.invokeLater(() -> {
                int choice = JOptionPane.showOptionDialog(
                        this,
                        "Game Over!\nScore: " + score,
                        "Dead Run",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new String[]{"Play Again", "Main Menu"},
                        "Play Again"
                );

                if (choice == JOptionPane.YES_OPTION) restartGame();
                else parentFrame.returnToMenu();
            });
        }
    }

    private void checkWin() {
        if (player.getY() == height - 1) {
            score++;
            Toolkit.getDefaultToolkit().beep();
            player.reset(width);
            lanes[random.nextInt(height)].changeDirection();
        }
    }

    private void restartGame() {
        score = 0;
        gameOver = false;
        player.reset(width);

        for (int i = 0; i < height; i++) {
            lanes[i] = new ZombieLane(width, random);
        }

        if (timer != null) timer.stop();
        startGameLoop();
        requestFocusInWindow();
        repaint();
    }

    private void stopGame() {
        if (timer != null) timer.stop();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        drawHUD(g2);
        drawGrid(g2);
        drawZombies(g2);
        drawPlayer(g2);
    }

    private void drawHUD(Graphics2D g2) {
        g2.setColor(new Color(30, 30, 30));
        g2.fillRect(0, 0, width * tileSize, topHudHeight);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.drawString("Score: " + score, 20, 30);
        g2.drawString("Difficulty: " + difficulty.getDisplayName(), 20, 55);
    }

    private void drawGrid(Graphics2D g2) {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int x = col * tileSize;
                int y = topHudHeight + row * tileSize;

                if (row == 0) g2.setColor(new Color(60, 120, 255));
                else if (row == height - 1) g2.setColor(new Color(0, 140, 70));
                else g2.setColor(new Color(45, 45, 45));

                g2.fillRect(x, y, tileSize, tileSize);
                g2.setColor(Color.DARK_GRAY);
                g2.drawRect(x, y, tileSize, tileSize);
            }
        }
    }

    private void drawZombies(Graphics2D g2) {
        for (int row = 1; row < height - 1; row++) {
            for (int col = 0; col < width; col++) {
                if (lanes[row].hasZombieAt(col)) {
                    int x = col * tileSize;
                    int y = topHudHeight + row * tileSize;

                    // shadow
                    g2.setColor(new Color(0,0,0,80));
                    g2.fillOval(x+8, y+tileSize-10, tileSize-16, 6);

                    // body
                    g2.setColor(new Color(100,180,100));
                    g2.fillRect(x+4,y+4,tileSize-8,tileSize-8);

                    // outline
                    g2.setColor(Color.BLACK);
                    g2.drawRect(x+4,y+4,tileSize-8,tileSize-8);

                    // eyes
                    g2.setColor(Color.RED);
                    g2.fillOval(x+9,y+10,5,5);
                    g2.fillOval(x+20,y+10,5,5);

                    // mouth
                    g2.drawLine(x+10,y+22,x+25,y+22);
                }
            }
        }
    }

    private void drawPlayer(Graphics2D g2) {
        int x = player.getX() * tileSize;
        int y = topHudHeight + player.getY() * tileSize;

        // shadow
        g2.setColor(new Color(0,0,0,80));
        g2.fillOval(x+8, y+tileSize-10, tileSize-16, 6);

        // body
        g2.setColor(new Color(0,255,120));
        g2.fillOval(x+5,y+5,tileSize-10,tileSize-10);

        // outline
        g2.setColor(Color.BLACK);
        g2.drawOval(x+5,y+5,tileSize-10,tileSize-10);

        // eyes
        g2.fillOval(x+10,y+12,5,5);
        g2.fillOval(x+20,y+12,5,5);
    }
}