import java.util.Random;

public class ZombieLane {
    private final boolean[] zombies;
    private boolean moveRight;
    private final Random random;

    public ZombieLane(int width, Random random) {
        this.zombies = new boolean[width];
        this.random = random;
        this.moveRight = random.nextBoolean();
    }

    public void move() {
        if (moveRight) {
            for (int i = zombies.length - 1; i > 0; i--) {
                zombies[i] = zombies[i - 1];
            }
            zombies[0] = random.nextInt(10) == 1;
        } else {
            for (int i = 0; i < zombies.length - 1; i++) {
                zombies[i] = zombies[i + 1];
            }
            zombies[zombies.length - 1] = random.nextInt(10) == 1;
        }
    }

    public boolean hasZombieAt(int pos) {
        return zombies[pos];
    }

    public void changeDirection() {
        moveRight = !moveRight;
    }

    public boolean isMovingRight() {
        return moveRight;
    }
}