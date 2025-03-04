package io.github.TestGame.Fighter;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.TestGame.AnimationLoader;
import com.badlogic.gdx.Gdx;

public class Fighter {
    protected Vector2 position;
    protected Animation<TextureRegion> idleAnimation;
    protected Animation<TextureRegion> walkAnimation;
    protected Animation<TextureRegion> attackAnimation;
    protected Animation<TextureRegion> deathAnimation;
    protected Animation<TextureRegion> jumpAnimation;

    protected float stateTime = 0;
    protected boolean isAttacking = false;
    protected boolean isMoving = false;
    protected boolean isDead = false;
    protected boolean isJumping = false;
    protected boolean isJumpAnimationPlaying = false;
    protected int health = 100;
    protected boolean isTakingDamage = false;
    protected float velocityY = 0;
    protected float gravity = -0.25f;

    // Hitbox and attack range
    protected Rectangle hitbox;
    protected Rectangle attackBox;
    private boolean facingLeft = false;

    private float lastMoveDirection = 1; // Default facing right

    public Fighter(Vector2 position, String basePath, int idleFrames, int walkFrames, int attackFrames, int deathFrames, int jumpFrames, boolean flipX) {
        this.position = position;

        idleAnimation = AnimationLoader.loadAnimation(basePath + "_idle.png", idleFrames, 1, 0.2f, flipX);
        walkAnimation = AnimationLoader.loadAnimation(basePath + "_walk.png", walkFrames, 1, 0.15f, flipX);
        attackAnimation = AnimationLoader.loadAnimation(basePath + "_attack.png", attackFrames, 1, 0.1f, flipX);
        deathAnimation = AnimationLoader.loadAnimation(basePath + "_death.png", deathFrames, 1, 0.2f, flipX);
        jumpAnimation = AnimationLoader.loadAnimation(basePath + "_jump.png", jumpFrames, 1, 0.1f, flipX);

        // Define hitbox and attack range
        hitbox = new Rectangle(position.x, position.y, 50, 100); // Adjust size to fit sprite
        attackBox = new Rectangle();
    }

    public void takeDamage(int damage) {
        if (!isDead) {
            health -= damage;
            isTakingDamage = true;
            if (health <= 0) {
                health = 0;
                isDead = true;
            }
        }
    }

    public void move(float deltaX) {
        position.x += deltaX * 2f;
        isMoving = true;

        if (deltaX > 0) {
            facingLeft = false; // Moving right
        } else if (deltaX < 0) {
            facingLeft = true; // Moving left
        }

        updateHitbox();
    }



    public void attack() {
        if (!isAttacking) {
            isAttacking = true;
            stateTime = 0;

            if (facingLeft) { // Attack extends left
                attackBox.set(hitbox.x - 50, hitbox.y, 50, hitbox.height);
            } else { // Attack extends right
                attackBox.set(hitbox.x + hitbox.width, hitbox.y, 50, hitbox.height);
            }
        }
    }





    public void checkAttack(Fighter opponent) {
        if (isAttacking && !opponent.isTakingDamage) { // Prevent multiple hits per attack
            int attackFrameIndex = attackAnimation.getKeyFrameIndex(stateTime);

            if (attackFrameIndex == 2 && attackBox.overlaps(opponent.hitbox)) { // Ensure correct frame timing
                opponent.takeDamage(10);
                opponent.isTakingDamage = true; // Prevents multiple hits per animation
                System.out.println("Hit registered! Opponent health: " + opponent.getHealth());
            }
        }

        // Reset opponent's damage flag after attack animation ends
        if (!isAttacking) {
            opponent.isTakingDamage = false;
        }
    }




    public void jump() {
        if (!isJumping && !isJumpAnimationPlaying) {
            isJumping = true;
            velocityY = 9.5f;
        }
    }

    public void update(float delta) {
        stateTime += delta;

        // Apply gravity
        if (isJumping) {
            position.y += velocityY;
            velocityY += gravity;

            if (position.y <= 100) {
                position.y = 100;
                isJumping = false;
                velocityY = 0;
            }
        }

        if (isJumpAnimationPlaying && jumpAnimation.isAnimationFinished(stateTime)) {
            isJumpAnimationPlaying = false;
        }

        // NEW: Let attack animation always play fully
        if (isAttacking && attackAnimation.isAnimationFinished(stateTime)) {
            isAttacking = false;
        }

        updateHitbox();
    }




    public void updateHitbox() {
        hitbox.set(position.x, position.y, 50, 100); // Base hitbox size

        if (facingLeft) { // Attack extends left
            attackBox.set(hitbox.x - 50, hitbox.y, 50, hitbox.height);
        } else { // Attack extends right
            attackBox.set(hitbox.x + hitbox.width, hitbox.y, 50, hitbox.height);
        }
    }





    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame;

        if (isDead) {
            currentFrame = deathAnimation.getKeyFrame(stateTime, false);
        } else if (isJumpAnimationPlaying) {
            currentFrame = jumpAnimation.getKeyFrame(stateTime, false);
        } else if (isJumping) {
            currentFrame = jumpAnimation.getKeyFrame(stateTime, false);
        } else if (isAttacking) {
            currentFrame = attackAnimation.getKeyFrame(stateTime, false);
        } else if (isMoving) {
            currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        } else {
            currentFrame = idleAnimation.getKeyFrame(stateTime, true);
        }

        batch.draw(currentFrame, position.x, position.y);
    }

    public int getHealth() {
        return health;
    }
}


