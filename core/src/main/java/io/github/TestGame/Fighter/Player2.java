package io.github.TestGame.Fighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.github.TestGame.AnimationLoader;

public class Player2 extends Fighter {
    public Player2(Vector2 position, String basePath) {
        super(position, basePath, 9, 8, 6, 5, 10, true); // Different frame counts for Player 2
    }

    public void handleInput() {
        isMoving = false;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) move(-1);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) move(1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) jump();
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) attack();
    }
}

