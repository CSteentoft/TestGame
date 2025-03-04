package io.github.TestGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.Animation;

public class AnimationLoader {
    public static Animation<TextureRegion> loadAnimation(String path, int frameCols, int frameRows, float frameDuration, boolean flipX) {
        Texture spriteSheet = new Texture(path);
        TextureRegion[][] tmpFrames = TextureRegion.split(spriteSheet,
            spriteSheet.getWidth() / frameCols, spriteSheet.getHeight() / frameRows);

        Array<TextureRegion> animationFrames = new Array<>();
        for (int row = 0; row < frameRows; row++) {
            for (int col = 0; col < frameCols; col++) {
                TextureRegion frame = tmpFrames[row][col];

                // Flip the frame if needed
                if (flipX) {
                    frame.flip(true, false);
                }

                animationFrames.add(frame);
            }
        }

        return new Animation<>(frameDuration, animationFrames, Animation.PlayMode.LOOP);
    }
}



