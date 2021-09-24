package com.fish.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class blueFish {


    private static final float MAX_SPEED_PER_SECOND = 100F;
    private static final float COLLISION_RECTANGLE_WIDTH = 64f;
    private static final float COLLISION_RECTANGLE_HEIGHT = 32f;
    private static final float COLLISION_CIRCLE_RADIUS = 33f;
    Random r = new Random();
    int tempy= r.nextInt(600 - 150 +1)+150;
    private static final float HEIGHT_OFFSET = -400f;
    private static final float DISTANCE_BETWEEN_FLOOR_AND_CEILING = 225F;
    private int i=tempy;
    private int j;
    public static final float WIDTH = COLLISION_CIRCLE_RADIUS * 2;

    private float x = 0;
    private boolean pointClaimed = false;

    private final Rectangle blueCollisionRectangle;

    private final float y;

    private final TextureRegion blueTexture;


    public blueFish(TextureRegion blueTexture) {
        this.blueTexture = blueTexture;

        this.y = MathUtils.random(HEIGHT_OFFSET);
        this.blueCollisionRectangle = new Rectangle(x, y, COLLISION_RECTANGLE_WIDTH, COLLISION_RECTANGLE_HEIGHT);
        }

    public void update(float delta) {
        setPosition(x - (MAX_SPEED_PER_SECOND * delta));
    }

    public boolean isFlappeeColliding(Flappee flappee) {
        Circle flappeeCollisionCircle = flappee.getCollisionCircle();
        return
                Intersector.overlaps(flappeeCollisionCircle, blueCollisionRectangle);
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

        blueCollisionRectangle.setX(x);
        if(i>tempy-200){
            i--;
            blueCollisionRectangle.setY(i);
            j=i;
        }

        if(i<tempy-199){
            i--;
            j++;
            blueCollisionRectangle.setY(j);
        }
    }
    public void draw(SpriteBatch batch) {
        drawFloorFlower(batch);
    }

    private void drawFloorFlower(SpriteBatch batch) {
        float textureX =blueCollisionRectangle.x - blueTexture.getRegionWidth() / 2;
        float textureY = blueCollisionRectangle.y - blueTexture.getRegionHeight() / 2;
        batch.draw(blueTexture, textureX+32f, textureY+16);
    }


    public void drawDebug(ShapeRenderer shapeRenderer) {

        shapeRenderer.rect(blueCollisionRectangle.x, blueCollisionRectangle.y, blueCollisionRectangle.width, blueCollisionRectangle.height);
      }
}
