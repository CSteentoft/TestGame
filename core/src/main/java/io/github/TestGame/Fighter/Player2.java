package io.github.TestGame.Fighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Player2 extends Fighter {
    private Texture normalTexture;
    private Texture attackTexture;

    public Player2(Vector2 position, Texture normalTexture, Texture attackTexture) {
        super(position, normalTexture);
        this.normalTexture = normalTexture;
        this.attackTexture = attackTexture;
    }

    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) move(-1);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) move(1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) jump();
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) attack();
    }

    @Override
    public void attack() {
        if (!isAttacking) {
            isAttacking = true;
            attackBox.set(position.x - 30, position.y, 30, hitbox.height); // Adjust attack hitbox

            // Reset back to normal sprite after 0.3 seconds
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    isAttacking = false;
                }
            }, 0.3f);
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        float scale = 0.2f;
        if (isAttacking) {
            batch.draw(attackTexture, position.x + (texture.getWidth() * scale), position.y,
                -texture.getWidth() * scale, texture.getHeight() * scale);
        } else {
            batch.draw(normalTexture, position.x, position.y, texture.getWidth() * scale, texture.getHeight() * scale);
        }
    }
}
