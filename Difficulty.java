public enum Difficulty {
    EASY("Easy", 120, 10),
    MEDIUM("Medium", 80, 7),
    HARD("Hard", 50, 4);

    private final String displayName;
    private final int timerDelay;
    private final int spawnRate;

    Difficulty(String displayName, int timerDelay, int spawnRate) {
        this.displayName = displayName;
        this.timerDelay = timerDelay;
        this.spawnRate = spawnRate;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getTimerDelay() {
        return timerDelay;
    }

    public int getSpawnRate() {
        return spawnRate;
    }
}