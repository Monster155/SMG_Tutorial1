package ru.itlyc.tutorial1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Timer;

public class FirstLevelScreen implements Screen {
    private final float PlayerScale = 4f;
    private final float EnemyScale = 5f;

    private SpriteBatch batch;
    private final Texture backgroundTexture = new Texture("Game/Screens/bg.jpg");
    private final Texture winTexture = new Texture("Game/Screens/win.png");
    private final Texture notReachedTexture = new Texture("Game/Screens/not.png");
    private final Texture deadTexture = new Texture("Game/Screens/dead.png");
    private final Texture enemyTexture = new Texture("Game/Stickman/stickman.png");

    private Animation<TextureRegion> runAnimation, attackAnimation, idleAnimation, dieAnimation;
    private Animation<TextureRegion> e_attackAnimation, e_idleAnimation, e_dieAnimation;
    private float animationTimer;
    private float playerMoveTimer;
    private int currentPlayerPlace;
    private int step;
    private boolean showResultScreen;

    @Override
    public void show() {
        batch = new SpriteBatch();

        TextureAtlas playerRunAtlas = new TextureAtlas(Gdx.files.internal("Game/Stickman/run.pack"));
        runAnimation = new Animation<TextureRegion>(0.05f, playerRunAtlas.getRegions(), Animation.PlayMode.LOOP);
        TextureAtlas playerAttackAtlas = new TextureAtlas(Gdx.files.internal("Game/Stickman/attack.pack"));
        attackAnimation = new Animation<TextureRegion>(0.1f, playerAttackAtlas.getRegions(), Animation.PlayMode.NORMAL);
        TextureAtlas playerIdleAtlas = new TextureAtlas(Gdx.files.internal("Game/Stickman/idle.pack"));
        idleAnimation = new Animation<TextureRegion>(0.3f, playerIdleAtlas.getRegions(), Animation.PlayMode.LOOP);
        TextureAtlas playerDieAtlas = new TextureAtlas(Gdx.files.internal("Game/Stickman/die.pack"));
        dieAnimation = new Animation<TextureRegion>(0.1f, playerDieAtlas.getRegions(), Animation.PlayMode.NORMAL);

        TextureAtlas enemyIdleAtlas = new TextureAtlas(Gdx.files.internal("Game/Enemy/idle.pack"));
        e_idleAnimation = new Animation<TextureRegion>(0.3f, enemyIdleAtlas.getRegions(), Animation.PlayMode.LOOP);
        TextureAtlas enemyAttackAtlas = new TextureAtlas(Gdx.files.internal("Game/Enemy/attack.pack"));
        e_attackAnimation = new Animation<TextureRegion>(0.1f, enemyAttackAtlas.getRegions(), Animation.PlayMode.NORMAL);
        TextureAtlas enemyDieAtlas = new TextureAtlas(Gdx.files.internal("Game/Enemy/die.pack"));
        e_dieAnimation = new Animation<TextureRegion>(0.1f, enemyDieAtlas.getRegions(), Animation.PlayMode.NORMAL);

        animationTimer = 0;
        playerMoveTimer = 0;
        step = 0;
        showResultScreen = false;
    }

    private void act(float delta) {
        animationTimer += delta;
        playerMoveTimer += delta;
        switch (step) {
            case 0:
                if (currentPlayerPlace < Math.min(8, TaskClass.Move())) {
                    if (playerMoveTimer >= 1) {
                        playerMoveTimer = 0f;
                        currentPlayerPlace++;
                    }
                } else {
                    animationTimer = 0;
                    if (TaskClass.Move() == 8) {
                        step = 1;
                    } else {
                        if (TaskClass.Move() < 8) {
                            step = 2;
                        } else {
                            step = 3;
                        }
                    }

                    Timer.schedule(
                            new Timer.Task() {
                                @Override
                                public void run() {
                                    showResultScreen = true;
                                }
                            },
                            3f
                    );
                }
                break;
        }
    }

    private void draw(float delta) {
        batch.draw(backgroundTexture, 0, 0, 612, 367);

        switch (step) {
            case 0:
                batch.draw(runAnimation.getKeyFrame(animationTimer),
                        currentPlayerPlace * 40f,
                        10,
                        runAnimation.getKeyFrame(animationTimer).getRegionWidth() * PlayerScale,
                        runAnimation.getKeyFrame(animationTimer).getRegionHeight() * PlayerScale);
                batch.draw(e_idleAnimation.getKeyFrame(animationTimer),
                        650,
                        0,
                        -e_idleAnimation.getKeyFrame(animationTimer).getRegionWidth() * EnemyScale,
                        e_idleAnimation.getKeyFrame(animationTimer).getRegionHeight() * EnemyScale);
                break;
            case 1:
                batch.draw(attackAnimation.getKeyFrame(animationTimer),
                        currentPlayerPlace * 40f,
                        10,
                        attackAnimation.getKeyFrame(animationTimer).getRegionWidth() * PlayerScale,
                        attackAnimation.getKeyFrame(animationTimer).getRegionHeight() * PlayerScale);
                batch.draw(e_dieAnimation.getKeyFrame(animationTimer),
                        650,
                        0,
                        -e_dieAnimation.getKeyFrame(animationTimer).getRegionWidth() * EnemyScale,
                        e_dieAnimation.getKeyFrame(animationTimer).getRegionHeight() * EnemyScale);
                break;
            case 2:
                batch.draw(idleAnimation.getKeyFrame(animationTimer),
                        currentPlayerPlace * 40f,
                        10,
                        idleAnimation.getKeyFrame(animationTimer).getRegionWidth() * PlayerScale,
                        idleAnimation.getKeyFrame(animationTimer).getRegionHeight() * PlayerScale);
                batch.draw(e_idleAnimation.getKeyFrame(animationTimer),
                        650,
                        0,
                        -e_idleAnimation.getKeyFrame(animationTimer).getRegionWidth() * EnemyScale,
                        e_idleAnimation.getKeyFrame(animationTimer).getRegionHeight() * EnemyScale);
                break;
            case 3:
                batch.draw(dieAnimation.getKeyFrame(animationTimer),
                        currentPlayerPlace * 40f,
                        10,
                        dieAnimation.getKeyFrame(animationTimer).getRegionWidth() * PlayerScale,
                        dieAnimation.getKeyFrame(animationTimer).getRegionHeight() * PlayerScale);
                batch.draw(e_attackAnimation.getKeyFrame(animationTimer),
                        650,
                        0,
                        -e_attackAnimation.getKeyFrame(animationTimer).getRegionWidth() * EnemyScale,
                        e_attackAnimation.getKeyFrame(animationTimer).getRegionHeight() * EnemyScale);
                break;
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 255);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        act(delta);
        draw(delta);
        if (showResultScreen)
            showResultScreen();
        batch.end();
    }

    private void showResultScreen() {
        switch (step) {
            case 1:
                batch.draw(winTexture, 0, 0, 612, 367);
                break;
            case 2:
                batch.draw(notReachedTexture, 0, 0, 612, 367);
                break;
            case 3:
                batch.draw(deadTexture, 0, 0, 612, 367);
                break;
        }
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
