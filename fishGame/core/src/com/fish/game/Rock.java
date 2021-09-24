package com.fish.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Rock {


    private static final float MAX_SPEED_PER_SECOND = 100F;

    private static final float COLLISION_CIRCLE_RADIUS = 35f;

    private static final float HEIGHT_OFFSET = -400f;
    private static final float DISTANCE_BETWEEN_FLOOR_AND_CEILING = 225F;

    public static final float WIDTH = COLLISION_CIRCLE_RADIUS * 2;

    private float x = 0;
    private boolean pointClaimed = false;


    private final Circle floorCollisionCircle;
    private final float y;

    private final TextureRegion floorTexture;


    public Rock(TextureRegion floorTexture) {
        this.floorTexture = floorTexture;

        this.y = MathUtils.random(HEIGHT_OFFSET);
        this.floorCollisionCircle = new Circle(x, y, COLLISION_CIRCLE_RADIUS);
        }

    public void update(float delta) {
        setPosition(x - (MAX_SPEED_PER_SECOND * delta));
    }

    public boolean isFlappeeColliding(Flappee flappee) {
        Circle flappeeCollisionCircle = flappee.getCollisionCircle();
        return
                Intersector.overlaps(flappeeCollisionCircle, floorCollisionCircle);
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
        floorCollisionCircle.setX(x);
        floorCollisionCircle.setY(0);

    }
    public void draw(SpriteBatch batch) {
        drawFloorRock(batch);
    }

    private void drawFloorRock(SpriteBatch batch) {
        float textureX = floorCollisionCircle.x - floorTexture.getRegionWidth() / 2;

        batch.draw(floorTexture, textureX, 0);
    }


    public void drawDebug(ShapeRenderer shapeRenderer) {


        shapeRenderer.circle(floorCollisionCircle.x, 0, floorCollisionCircle.radius);
}
}
