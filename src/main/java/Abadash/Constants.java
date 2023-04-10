package Abadash;

public final class Constants {
    public static final double BLOCK_SIZE = 80;
    public static final double VELOCITY_X = 700;
    public static final int LEAP_BLOCKS = 4;
    public static final double JUMP_FORCE = 8.87298*VELOCITY_X/LEAP_BLOCKS;
    public static final double GRAVITY_CONSTANT = 15.746*VELOCITY_X*VELOCITY_X/(LEAP_BLOCKS*LEAP_BLOCKS*BLOCK_SIZE);
    public static final double SCENE_WIDTH = 1280;
    public static final double SCENE_HEIGHT = 720;
    public static final double FADE_DISTANCE = SCENE_WIDTH/1280 * 90;
    public static final double FLOOR_HEIGHT = SCENE_HEIGHT - BLOCK_SIZE * 2;
    public static final boolean DEBUG_MODE = true;
}
