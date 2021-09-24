package com.fish.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;

import java.util.Random;

public class pufferFish {


    private static final float MAX_SPEED_PER_SECOND = 100F;

    private static final float COLLISION_CIRCLE_RADIUS = 27f;
    Random r = new Random();
    private static final float HEIGHT_OFFSET = -400f;
    private static final float DISTANCE_BETWEEN_FLOOR_AND_CEILING = 225F;

    public static final float WIDTH = COLLISION_CIRCLE_RADIUS * 2;

    private float x = 0;
    private boolean pointClaimed = false;
    int tempy= r.nextInt(600 - 50 +1)+50;

    private final Circle fishCollisionCircle;
    private final float y;

    private final TextureRegion fishTexture;


    public pufferFish(TextureRegion fishTexture) {
        this.fishTexture = fishTexture;

        this.y = MathUtils.random(HEIGHT_OFFSET);
        this.fishCollisionCircle = new Circle(x, y, COLLISION_CIRCLE_RADIUS);
        }

    public void update(float delta) {
        setPosition(x - (MAX_SPEED_PER_SECOND * delta));
    }

    public boolean isFlappeeColliding(Flappee flappee) {
        Circle flappeeCollisionCircle = flappee.getCollisionCircle();
        return
                Intersector.overlaps(flappeeCollisionCircle, fishCollisionCircle);
    }

    public void setPosition(float x) {
        this.x = x;

        updateCollisionCircle();
    }

    public float getX() {
        return x;
    }

    public boolean isPointClaimed() {
        return pointClaimed;
    }

    public void markPointClaimed() {
        pointClaimed = true;
    }

    private void updateCollisionCircle() {
        fishCollisionCircle.setX(x);
        fishCollisionCircle.setY(tempy);
    }
    public void draw(SpriteBatch batch) {
        drawFish(batch);
    }

    private void drawFish(SpriteBatch batch) {
        float textureX = fishCollisionCircle.x - fishTexture.getRegionWidth() / 2;
        float textureY = fishCollisionCircle.y - fishTexture.getRegionHeight() / 2;

        batch.draw(fishTexture, textureX,textureY );
    }


    public void drawDebug(ShapeRenderer shapeRenderer) {


        shapeRenderer.circle(fishCollisionCircle.x, fishCollisionCircle.y, fishCollisionCircle.radius);
}
}
