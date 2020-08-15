package Screens;

import Engine.GraphicsHandler;
import Engine.Keyboard;
import Engine.Screen;
import Game.GameState;
import Game.ScreenCoordinator;
import Maps.TestMap;
import Players.Cat;
import Scene.Map;
import Scene.Player;
import Scene.PlayerListener;
import Utils.Timer;

public class PlayLevelScreen extends Screen implements PlayerListener {
    protected ScreenCoordinator screenCoordinator;
    protected Map map;
    protected Player player;
    protected PlayLevelScreenState playLevelScreenState;
    protected Timer screenTimer = new Timer();
    protected LevelClearedScreen levelClearedScreen;
    protected LevelLoseScreen levelLoseScreen;

    public PlayLevelScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    public void initialize() {
        this.map = new TestMap();
        map.reset();
        this.player = new Cat(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y, map);
        this.player.addListener(this);
        this.player.setLocation(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
        this.playLevelScreenState = PlayLevelScreenState.RUNNING;
    }

    public void update(Keyboard keyboard) {
        switch (playLevelScreenState) {
            case RUNNING:
                map.update(keyboard, player);
                player.update(keyboard, map);
                break;
            case LEVEL_COMPLETED:
                levelClearedScreen = new LevelClearedScreen();
                levelClearedScreen.initialize();
                screenTimer.setWaitTime(3000);
                playLevelScreenState = PlayLevelScreenState.LEVEL_WIN_MESSAGE;
                break;
            case LEVEL_WIN_MESSAGE:
                if (screenTimer.isTimeUp()) {
                    levelClearedScreen = null;
                    goBackToMenu();
                }
                break;
            case PLAYER_DEAD:
                levelLoseScreen = new LevelLoseScreen(this);
                levelLoseScreen.initialize();
                playLevelScreenState = PlayLevelScreenState.LEVEL_LOSE_MESSAGE;
                break;
            case LEVEL_LOSE_MESSAGE:
                levelLoseScreen.update(keyboard);
                break;
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        switch (playLevelScreenState) {
            case RUNNING:
            case LEVEL_COMPLETED:
            case PLAYER_DEAD:
                map.draw(graphicsHandler);
                player.draw(graphicsHandler);
                break;
            case LEVEL_WIN_MESSAGE:
                levelClearedScreen.draw(graphicsHandler);
                break;
            case LEVEL_LOSE_MESSAGE:
                levelLoseScreen.draw(graphicsHandler);
                break;
        }
    }

    public PlayLevelScreenState getPlayLevelScreenState() {
        return playLevelScreenState;
    }

    @Override
    public void onLevelCompleted() {
        playLevelScreenState = PlayLevelScreenState.LEVEL_COMPLETED;
    }

    @Override
    public void onDeath() {
        playLevelScreenState = PlayLevelScreenState.PLAYER_DEAD;
    }

    public void resetLevel() {
        initialize();
    }

    public void goBackToMenu() {
        screenCoordinator.setGameState(GameState.MENU);
    }

    private enum PlayLevelScreenState {
        RUNNING, LEVEL_COMPLETED, PLAYER_DEAD, LEVEL_WIN_MESSAGE, LEVEL_LOSE_MESSAGE
    }
}