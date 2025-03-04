package io.github.TestGame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.Game;

public class KOScreen implements Screen {
    private final Game game;
    private Stage stage;
    private Texture koBackgroundTexture;

    public KOScreen(final Game game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Load KO background image
        koBackgroundTexture = new Texture(Gdx.files.internal("assets/background.png"));

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        // KO Label
        Label koLabel = new Label("K.O.!", skin);
        koLabel.setFontScale(6);
        koLabel.setPosition(Gdx.graphics.getWidth() / 2f - koLabel.getWidth() / 2f, Gdx.graphics.getHeight() / 2f + 100);

        // Rematch Button
        TextButton restartButton = new TextButton("Rematch", skin);
        restartButton.setSize(300, 100);
        restartButton.setPosition(Gdx.graphics.getWidth() / 2f - 150, Gdx.graphics.getHeight() / 2f - 50);

        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });

        stage.addActor(koLabel);
        stage.addActor(restartButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getBatch().begin();
        stage.getBatch().draw(koBackgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        koBackgroundTexture.dispose();
    }

    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}


