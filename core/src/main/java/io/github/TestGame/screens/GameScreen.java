package io.github.TestGame.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.TestGame.Fighter.Fighter;
import io.github.TestGame.Fighter.Player1;
import io.github.TestGame.Fighter.Player2;

public class GameScreen implements Screen {
    private static final float VIRTUAL_WIDTH = 800;
    private static final float VIRTUAL_HEIGHT = 480;

    private SpriteBatch batch;
    private Player1 player1;
    private Player2 player2;
    private OrthographicCamera camera;
    private Viewport viewport;

    private Texture backgroundTexture;

    @Override
    public void show() {
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        viewport.apply();

        camera.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
        camera.update();

        // Load Background
        backgroundTexture = new Texture("assets/background.png");

        Texture player1Texture = new Texture("assets/players/player1.png");
        Texture player1AttackTexture = new Texture("assets/players/attack/player1attack.png");

        Texture player2Texture = new Texture("assets/players/player2.png");
        Texture player2AttackTexture = new Texture("assets/players/attack/player2attack.png");

        player1 = new Player1(new Vector2(150, 100), player1Texture, player1AttackTexture);
        player2 = new Player2(new Vector2(650, 100), player2Texture, player2AttackTexture);
    }



    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        // Draw Background
        batch.draw(backgroundTexture, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        player1.handleInput();
        player2.handleInput();

        player1.update();
        player2.update();

        player1.checkAttack(player2);
        player2.checkAttack(player1);

        player1.draw(batch);
        player2.draw(batch);

        // Draw Health Bars
        drawHealthBar(player1, 50, 440); // Left Side
        drawHealthBar(player2, 550, 440); // Right Side

        batch.end();

        checkGameOver();
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        backgroundTexture.dispose(); // Dispose the background to free memory
    }
    private void drawHealthBar(Fighter player, float x, float y) {
        float healthPercentage = (float) player.getHealth() / 100;

        // Health bar texture (1x1 white pixel stretched)
        Texture healthTexture = new Texture("assets/white.png");

        batch.setColor(1, 0, 0, 1); // Red color for health bar

        if (player == player1) {
            batch.draw(healthTexture, x, y, 200 * healthPercentage, 10); // Left side health bar
        } else {
            batch.draw(healthTexture, x + (200 - (200 * healthPercentage)), y, 200 * healthPercentage, 10); // Right side mirrored
        }

        batch.setColor(1, 1, 1, 1); // Reset color
    }


    private void checkGameOver() {
        if (player1.getHealth() <= 0) {
            System.out.println("Player 2 Wins!");
            resetGame();
        } else if (player2.getHealth() <= 0) {
            System.out.println("Player 1 Wins!");
            resetGame();
        }
    }

    private void resetGame() {
        player1.setHealth(100);
        player2.setHealth(100);
        player1.setPosition(new Vector2(100, 100));
        player2.setPosition(new Vector2(400, 100));
    }



}

