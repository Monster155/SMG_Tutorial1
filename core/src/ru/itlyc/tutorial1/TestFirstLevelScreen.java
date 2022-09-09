package ru.itlyc.tutorial1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TestFirstLevelScreen implements Screen {

    private final Texture stickmanTexture = new Texture("Game/Stickman/stickman.png");
    private final Texture enemyTexture = new Texture("Game/Stickman/stickman.png");
    private final Texture backgroundTexture = new Texture("Game/bg.jpg");
    private SpriteBatch batch;
    private int currentStickmanPlace = 0;
    private float stickmanPlaceScale = 40f;
    private float stickmanMoveMaxTime = 1f;
    private float stickmanMoveTimer = 0f;

    private Animation<TextureRegion> animation;
    private float animationTimer = 0;

    @Override
    public void show() {
        batch = new SpriteBatch();
        stickmanMoveTimer = 0f;
        currentStickmanPlace = 0;

        TextureAtlas playerRunAtlas = new TextureAtlas(Gdx.files.internal("Game/Stickman/run.pack"));
        animation = new Animation<TextureRegion>(0.5f, playerRunAtlas.getRegions(), Animation.PlayMode.LOOP);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 255);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stickmanMoveTimer += delta;
        if (stickmanMoveTimer >= stickmanMoveMaxTime && currentStickmanPlace < TaskClass.Move()) {
            stickmanMoveTimer = 0f;
            currentStickmanPlace++;
        }

        animationTimer += delta;
        float scale = 4f;
        if (currentStickmanPlace == TaskClass.Move()) {

        } else {
            batch.draw(animation.getKeyFrame(animationTimer),
                    currentStickmanPlace * stickmanPlaceScale,
                    70,
                    animation.getKeyFrame(animationTimer).getRegionWidth() * scale,
                    animation.getKeyFrame(animationTimer).getRegionHeight() * scale);
        }

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, 612, 367);
//        batch.draw(stickmanTexture, 45 + currentStickmanPlace * stickmanPlaceScale, 70, 40, 128);
        batch.draw(enemyTexture, 500, 70, 40, 128);
//        System.out.println("Stickman: 40 / 128");
//        System.out.println("Stickman Reality: " + stickmanTexture.getWidth() + " / " + stickmanTexture.getHeight());
//        System.out.println("Animation: " + animation.getKeyFrame(animationTimer).getRegionWidth() + " / " + animation.getKeyFrame(animationTimer).getRegionHeight());
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
