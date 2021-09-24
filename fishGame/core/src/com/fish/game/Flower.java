package com.fish.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Flower {


    private static final float MAX_SPEED_PER_SECOND = 100F;
    private static final float COLLISION_RECTANGLE_WIDTH = 64f;
    private static final float COLLISION_RECTANGLE_HEIGHT = 64f;
    private static final float COLLISION_CIRCLE_RADIUS = 33f;

    private static final float HEIGHT_OFFSET = -400f;
    private static final float DISTANCE_BETWEEN_FLOOR_AND_CEILING = 225F;

    public static final float WIDTH = COLLISION_CIRCLE_RADIUS * 2;

    private float x = 0;
    private boolean pointClaimed = false;

    private final Rectangle floorCollisionRectangle;

    private final float y;

    private final TextureRegion floorTexture;


    public Flower(TextureRegion floorTexture) {
        this.floorTexture = floorTexture;

        this.y = MathUtils.random(HEIGHT_OFFSET);
        this.floorCollisionRectangle = new Rectangle(x, y, COLLISION_RECTANGLE_WIDTH, COLLISION_RECTANGLE_HEIGHT);
        }

    public void update(float delta) {
        setPosition(x - (MAX_SPEED_PER_SECOND * delta));
    }

    public boolean isFlappeeColliding(Flappee flappee) {
        Circle flappeeCollisionCircle = flappee.getCollisionCircle();
        return
                Intersector.overlaps(flappeeCollisionCircle, floorCollisionRectangle);
    }

    public void setPosition(float x) {
        this.x = x;
        updateCollisionRectangle();
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
    private void updateCollisionRectangle() {
        floorCollisionRectangle.setX(x);
        floorCollisionRectangle.setY(0);

    }
    public void draw(SpriteBatch batch) {
        drawFloorFlower(batch);
    }

    private void drawFloorFlower(SpriteBatch batch) {
        float textureX = floorCollisionRectangle.x - floorTexture.getRegionWidth() / 2;

        batch.draw(floorTexture, textureX+32f, 0);
    }


    public void drawDebug(ShapeRenderer shapeRenderer) {

        shapeRenderer.rect(floorCollisionRectangle.x, 0, floorCollisionRectangle.width, floorCollisionRectangle.height);
      }
}
