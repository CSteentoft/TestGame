package io.github.TestGame.Fighter;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class Player1 extends Fighter {
    public Player1(Vector2 position, String basePath) {
        super(position, basePath, 6, 8, 4, 4, 8, false); // Specify unique frame counts for Player 1
    }

    public void handleInput() {
        isMoving = false;

        if (Gdx.input.isKeyPressed(Input.Keys.A)) move(-1);
        if (Gdx.input.isKeyPressed(Input.Keys.D)) move(1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) jump();
        if (Gdx.input.isKeyJustPressed(Input.Keys.J)) attack();
    }
}


