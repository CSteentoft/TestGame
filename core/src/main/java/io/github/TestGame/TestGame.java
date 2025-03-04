package io.github.TestGame;

import com.badlogic.gdx.Game;
import io.github.TestGame.screens.GameScreen;

public class TestGame extends Game {
    @Override
    public void create() {
        this.setScreen(new GameScreen());
    }
}
