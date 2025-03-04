package io.github.TestGame.Fighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;

public class Fighter {
    protected Vector2 position;
    protected Texture texture;
    protected float speed = 5f;
    protected int health = 100;
    protected boolean isJumping = false;
    protected float jumpVelocity = 10f;
    protected float gravity = -0.5f;
    protected boolean isAttacking = false;
    protected Rectangle hitbox;
    protected Rectangle attackBox;

    public Fighter(Vector2 position, Texture texture) {
        this.position = position;
        this.texture = texture;
        this.hitbox = new Rectangle(position.x, position.y, texture.getWidth() * 0.2f, texture.getHeight() * 0.2f);
        this.attackBox = new Rectangle();
    }

    public void move(float deltaX) {
        position.x += deltaX * speed;
        hitbox.setPosition(position.x, position.y);
    }

    public void jump() {
        if (!isJumping) {
            isJumping = true;
            position.y += jumpVelocity;
        }
    }

    public void update() {
        if (isJumping) {
            position.y += jumpVelocity;
            jumpVelocity += gravity;
            if (position.y <= 100) {
                position.y = 100;
                isJumping = false;
                jumpVelocity = 10f;
            }
        }
        hitbox.setPosition(position.x, position.y);
    }

    public void attack() {
        if (!isAttacking) {
            isAttacking = true;
            attackBox.set(position.x + (hitbox.width / 2), position.y, 30, hitbox.height);

            // Switch texture for a short attack animation
            texture = new Texture("attack.png");

            // Reset attack after 0.3 seconds
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    isAttacking = false;
                    texture = new Texture("assets/players/player1.png"); // Reset to normal sprite
                }
            }, 0.3f);
        }
    }


    public void checkAttack(Fighter opponent) {
        if (isAttacking && attackBox.overlaps(opponent.hitbox)) {
            opponent.takeDamage(10);
            isAttacking = false;
        }
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, texture.getWidth() * 0.2f, texture.getHeight() * 0.2f);
    }


    public int getHealth() {
        return health;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
        hitbox.setPosition(position.x, position.y);
    }

}
