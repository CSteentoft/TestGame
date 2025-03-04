package io.github.TestGame.Fighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;

public class Player1 extends Fighter {
    private Texture normalTexture;
    private Texture attackTexture;
    public Player1(Vector2 position, Texture normalTexture, Texture attackTexture) {
        super(position, normalTexture);
        this.normalTexture = normalTexture;
        this.attackTexture = attackTexture;
    }

    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) move(-1);
        if (Gdx.input.isKeyPressed(Input.Keys.D)) move(1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) jump();
        if (Gdx.input.isKeyJustPressed(Input.Keys.J)) attack();
    }
    @Override
    public void attack() {
        if (!isAttacking) {
            isAttacking = true;
            texture = attackTexture; // Switch to attack sprite

            attackBox.set(position.x + (hitbox.width / 2), position.y, 30, hitbox.height);

            // Reset back to normal sprite after 0.3 seconds
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    isAttacking = false;
                    texture = normalTexture;
                }
            }, 0.3f);
        }
    }
}
