public class Player {
    private int x;
    private int y;

    public Player(int width) {
        this.x = width / 2;
        this.y = 0;
    }

    public void reset(int width) {
        this.x = width / 2;
        this.y = 0;
    }

    public void moveLeft() {
        x--;
    }

    public void moveRight() {
        x++;
    }

    public void moveUp() {
        y--;
    }

    public void moveDown() {
        y++;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}