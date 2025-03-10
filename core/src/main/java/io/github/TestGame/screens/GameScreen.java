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
import io.github.TestGame.Fighter.Player1;
import io.github.TestGame.Fighter.Player2;
import com.badlogic.gdx.Game;

public class GameScreen implements Screen {
    private static final float VIRTUAL_WIDTH = 800;
    private static final float VIRTUAL_HEIGHT = 480;

    private Game game;
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private Texture healthBarTexture;
    private Player1 player1;
    private Player2 player2;
    private OrthographicCamera camera;
    private Viewport viewport;

    public GameScreen(Game game) {
        this.game = game;
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        viewport.apply();

        camera.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
        camera.update();

        // Load textures
        backgroundTexture = new Texture(Gdx.files.internal("assets/background.png"));
        healthBarTexture = new Texture(Gdx.files.internal("assets/white.png")); // Ensure this file exists

        player1 = new Player1(new Vector2(150, 100), "assets/player1");
        player2 = new Player2(new Vector2(650, 100), "assets/player2");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);

        player1.handleInput();
        player2.handleInput();

        player1.update(delta);
        player2.update(delta);

        // Check if attacks hit the opponent
        player1.checkAttack(player2);
        player2.checkAttack(player1);

        player1.draw(batch);
        player2.draw(batch);

        drawHealthBar(player1, 50, 440);
        drawHealthBar(player2, 550, 440);

        batch.end();

        checkGameOver();
    }


    private void drawHealthBar(Player1 player, float x, float y) {
        float healthPercentage = (float) player.getHealth() / 100;
        batch.setColor(1, 0, 0, 1);
        batch.draw(healthBarTexture, x, y, 200 * healthPercentage, 10);
        batch.setColor(1, 1, 1, 1);
    }

    private void drawHealthBar(Player2 player, float x, float y) {
        float healthPercentage = (float) player.getHealth() / 100;
        batch.setColor(1, 0, 0, 1);
        batch.draw(healthBarTexture, x, y, 200 * healthPercentage, 10);
        batch.setColor(1, 1, 1, 1);
    }

    private void checkGameOver() {
        if (player1.getHealth() <= 0) {
            System.out.println("Player 2 Wins!");
            game.setScreen(new KOScreen(game));
        } else if (player2.getHealth() <= 0) {
            System.out.println("Player 1 Wins!");
            game.setScreen(new KOScreen(game));
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundTexture.dispose();
        healthBarTexture.dispose();
    }

    @Override
    public void show() {}

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}

