package com.fish.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;


import java.util.Iterator;
import java.util.logging.Handler;

public class GameScreen extends ScreenAdapter {


    private static final float WORLD_WIDTH = 1000;
    private static final float WORLD_HEIGHT = 640;
    float posX;
    float posY;
    long totalSeconds = 30;
    long intervalSeconds = 1;


    private TiledMap tiledMap;
    private static final float GAP_BETWEEN_FLOWERS = 200F;
    private static final float GAP_BETWEEN_Rocks = 350F;
    private static final float GAP_BETWEEN_PUFFFISHS = 800F;
    private static final float GAP_BETWEEN_ORFISHS = 900F;
    private static final float GAP_BETWEEN_BLFISHS = 600F;
    private ShapeRenderer shapeRenderer;
    private Viewport viewport;
    private Camera camera;
    private BitmapFont bitmapFont;
    private BitmapFont bitmapFont2;
    public static GlyphLayout glyphLayout = new GlyphLayout();

    private SpriteBatch batch;
    private float timeState=0f;
    private Flappee flappee;

    private Array<Flower> flowers = new Array<Flower>();
    private Array<Rock> rocks = new Array<Rock>();
    private Array<pufferFish> pufffishs = new Array<pufferFish>();
    private Array<orangeFish> orfishs = new Array<orangeFish>();
    private Array<blueFish> blfishs = new Array<blueFish>();
    private int score = 0;
    private Music music;
    private TextureRegion background;
    private TextureRegion flowerBottom;
    private TextureRegion rockBottom;
    private TextureRegion pufferTexture;
    private TextureRegion orangeTexture;
    private TextureRegion blueTexture;
    private TextureRegion flappeeTexture;
    private TextureRegion flappeeTexture1;
    private TextureRegion flappeeTexture2;
    private final FISH_Class flappeeBeeGame;

    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;

    public GameScreen(FISH_Class flappeeBeeGame) {
        this.flappeeBeeGame = flappeeBeeGame;
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height);
    }

    @Override
    public void show() {
        super.show();
        camera = new OrthographicCamera();
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        camera.update();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();


        TextureAtlas textureAtlas = flappeeBeeGame.getAssetManager().get("flappee_bee_assets.atlas");
        TextureAtlas textureAtlasFish = new TextureAtlas(Gdx.files.internal("allfish.atlas"));
        Texture image=new Texture ("plswork.png");
        background = new TextureRegion(image,0,0,1600,800);
        rockBottom = textureAtlasFish.findRegion("rock");
        pufferTexture = textureAtlasFish.findRegion("pufferfish1");
        orangeTexture = textureAtlasFish.findRegion("orangefish");
        blueTexture = textureAtlasFish.findRegion("bluefish2");
        flowerBottom = textureAtlasFish.findRegion("algueverte1");
        pufferTexture.flip(true,false);
        orangeTexture.flip(true,false);
        blueTexture.flip(true,false);
        flappeeTexture1 = textureAtlasFish.findRegion("redfish1");
        flappeeTexture2 = textureAtlasFish.findRegion("redfish2");
        bitmapFont = flappeeBeeGame.getAssetManager().get("score.fnt");
        bitmapFont2 = new BitmapFont();
        flappee = new Flappee(flappeeTexture1 ,flappeeTexture2 );
        flappee.setPosition(WORLD_WIDTH / 4, WORLD_HEIGHT / 2);
        music= Gdx.audio.newMusic(Gdx.files.internal("Space Cadet.ogg"));
        music.setLooping(true);
        music.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        clearScreen();
        draw();
        //drawDebug();
    }

    private void update(float delta) {

        updateScore();
        if(score>0) {
            updateFlappee(delta*1);
            updatepuffFishes(delta*2);
            updateorFishes(delta*2);
            updateblFishes(delta*2);
            updateFlowers(delta*1);
            updateRocks(delta*1);}
        if(score>60) {
            updateFlappee(delta*2);

            updateFlowers(delta*2);
            updatepuffFishes(delta*2);
            updateorFishes(delta*3);
            updateblFishes(delta*2);
            updateRocks(delta*2);}
        if(score>120) {
            updateFlappee(delta*2);
            updateFlowers(delta*2);
            updatepuffFishes(delta*3);
            updateorFishes(delta*3);
            updateblFishes(delta*2);
            updateRocks(delta*2);}
        if(score>180) {
            updateFlappee(delta*3);
            updateFlowers(delta*3);
            updatepuffFishes(delta*3);
            updateorFishes(delta*3);
            updateblFishes(delta*3);
            updateRocks(delta*3);}
        if(score>240) {
            updateFlappee(delta*3);
            updateFlowers(delta*3);
            updatepuffFishes(delta*4);
            updateorFishes(delta*3);
            updateblFishes(delta*3);
            updateRocks(delta*3);}
        if (checkForCollision()) {
            restart();
        }
    }

    private void restart() {
        flappee.setPosition(WORLD_WIDTH / 4, WORLD_HEIGHT / 2);
        flowers.clear();
        rocks.clear();
        pufffishs.clear();
        orfishs.clear();
        blfishs.clear();
        score = 0;
    }

    private boolean checkForCollision() {
        for (Flower flower : flowers) {
            if (flower.isFlappeeColliding(flappee)) {
                return true;
            }
        }
        for (Rock rock : rocks) {
            if (rock.isFlappeeColliding(flappee)) {
                return true;
            }
        }
        for (pufferFish pufffish : pufffishs) {
            if (pufffish.isFlappeeColliding(flappee)) {
                return true;
            }
        }
        for (orangeFish orFish : orfishs) {
            if (orFish.isFlappeeColliding(flappee)) {
                return true;
            }
        }
        for (blueFish blFish : blfishs) {
            if (blFish.isFlappeeColliding(flappee)) {
                return true;
            }
        }
        return false;
    }

    private void updateFlappee(float delta) {

        posX= Gdx.input.getAccelerometerX();
        posY= Gdx.input.getAccelerometerY();

        flappee.updatepos(-posX,posY);
        blockFlappeeLeavingTheWorld();
    }

    private void updateScore() {
        timeState+=Gdx.graphics.getDeltaTime();
        if(timeState>=1f){
            timeState=0f;
            score++;
        }
    }



    private void blockFlappeeLeavingTheWorld() {
        if (flappee.getY() < 0) {
            //flappee.setPosition(flappee.getX(), 0);
            restart();
        }
        if (flappee.getY() > WORLD_HEIGHT) {
           // flappee.setPosition(flappee.getX(), WORLD_HEIGHT);
            restart();
        }
        if (flappee.getX() < 0) {
          //  flappee.setPosition(0, flappee.getY());
            restart();
        }
        if (flappee.getX() > WORLD_WIDTH) {
          //  flappee.setPosition(WORLD_WIDTH,  flappee.getY());
            restart();
        }
    }

    private void updateFlowers(float delta) {
        for (Flower flower : flowers) {
            flower.update(delta);
        }
        checkIfNewFlowerIsNeeded();
        removeFlowersIfPassed();
    }
    private void updateRocks(float delta) {
        for (Rock rock : rocks) {
            rock.update(delta);
        }
        checkIfNewRockIsNeeded();
        removeRocksIfPassed();
    }
    private void updatepuffFishes(float delta) {
        for (pufferFish pufffish : pufffishs) {
            pufffish.update(delta);
        }
        checkIfNewpuffFishIsNeeded();
        removepuffFishsIfPassed();
    }
    private void updateorFishes(float delta) {
        for (orangeFish orfish : orfishs) {
            orfish.update(delta);
        }
        checkIfNeworFishIsNeeded();
        removeorFishsIfPassed();
    }
    private void updateblFishes(float delta) {
        for (blueFish blfish : blfishs) {
            blfish.update(delta);
        }
        checkIfNewblFishIsNeeded();
        removeblFishsIfPassed();
    }
    private void checkIfNewFlowerIsNeeded() {
        if (flowers.size == 0) {
            createNewFlower();
        } else {
            Flower flower = flowers.peek();
            if (flower.getX() < WORLD_WIDTH - GAP_BETWEEN_FLOWERS) {
                createNewFlower();
            }
        }
    }
    private void checkIfNewRockIsNeeded() {
        if (rocks.size == 0) {
            createNewRock();
        } else {
            Rock rock = rocks.peek();
            if (rock.getX() < WORLD_WIDTH - GAP_BETWEEN_Rocks) {
                createNewRock();
            }
        }
    }
    private void checkIfNewpuffFishIsNeeded() {
        if (pufffishs.size == 0) {
            createNewpuffFish();
        } else {
            pufferFish pufffish =pufffishs.peek();
            if (pufffish.getX() < WORLD_WIDTH - GAP_BETWEEN_PUFFFISHS) {
                createNewpuffFish();
            }
        }
    }
    private void checkIfNeworFishIsNeeded() {
        if (orfishs.size == 0) {
            createNeworFish();
        } else {
            orangeFish orfish =orfishs.peek();
            if (orfish.getX() < WORLD_WIDTH - GAP_BETWEEN_ORFISHS) {
                createNeworFish();
            }
        }
    }
    private void checkIfNewblFishIsNeeded() {
        if (blfishs.size == 0) {
            createNewblFish();
        } else {
            blueFish blfish =blfishs.peek();
            if (blfish.getX() < WORLD_WIDTH - GAP_BETWEEN_BLFISHS) {
                createNewblFish();
            }
        }
    }
    private void createNewFlower() {
        Flower newFlower = new Flower(flowerBottom);
        newFlower.setPosition(WORLD_WIDTH + Flower.WIDTH);
        flowers.add(newFlower);
    }
    private void createNewRock() {
        Rock newRock = new Rock(rockBottom);
        newRock.setPosition(WORLD_WIDTH + Rock.WIDTH);
        rocks.add(newRock);
    }
    private void createNewpuffFish() {
        pufferFish newpuffFish = new pufferFish(pufferTexture);
        newpuffFish.setPosition(WORLD_WIDTH + pufferFish.WIDTH);
        pufffishs.add(newpuffFish);
    }
    private void createNeworFish() {
        orangeFish neworFish = new orangeFish(orangeTexture);
        neworFish.setPosition(WORLD_WIDTH + orangeFish.WIDTH);
        orangeFish neworFish2 = new orangeFish(orangeTexture);
        neworFish2.setPosition(WORLD_WIDTH + orangeFish.WIDTH);
        orfishs.add(neworFish);
        orfishs.add(neworFish2);

    }
    private void createNewblFish() {
        blueFish newblFish = new blueFish(blueTexture);
        newblFish.setPosition(WORLD_WIDTH + blueFish.WIDTH);
        blfishs.add(newblFish);
    }
    private void removeFlowersIfPassed() {
        if (flowers.size > 0) {
            Flower firstFlower = flowers.first();
            if (firstFlower.getX() < -Flower.WIDTH) {
                flowers.removeValue(firstFlower, true);
            }
        }
    }
    private void removeRocksIfPassed() {
        if (rocks.size > 0) {
            Rock firstRock = rocks.first();
            if (firstRock.getX() < -Rock.WIDTH) {
                rocks.removeValue(firstRock, true);
            }
        }
    }
    private void removepuffFishsIfPassed() {
        if (pufffishs.size > 0) {
            pufferFish firstpuffFish = pufffishs.first();
            if (firstpuffFish.getX() < -pufferFish.WIDTH) {
                pufffishs.removeValue(firstpuffFish, true);
            }
        }
    }
    private void removeorFishsIfPassed() {
        if (orfishs.size > 0) {
            orangeFish firstorFish = orfishs.first();
            if (firstorFish.getX() < -orangeFish.WIDTH) {
                orfishs.removeValue(firstorFish, true);
            }
        }
    }
    private void removeblFishsIfPassed() {
        if (blfishs.size > 0) {
            blueFish firstblFish = blfishs.first();
            if (firstblFish.getX() < -blueFish.WIDTH) {
                blfishs.removeValue(firstblFish, true);
            }
        }
    }
    private void clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void draw() {
//        batch.totalRenderCalls = 0;
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
        batch.draw(background, 0, 0);
        drawRocks();
        drawpuffFishs();
        draworFishs();
        drawblFishs();
        drawFlowers();

        flappee.draw(batch);
        drawScore();
        batch.end();
//        System.out.println(batch.totalRenderCalls);
    }

    private void drawScore() {
        String scoreAsString = Integer.toString(score);
        glyphLayout.setText(bitmapFont, scoreAsString);
        //BitmapFont.TextBounds scoreBounds = bitmapFont.getBounds(scoreAsString);
        //bitmapFont.draw(batch, scoreAsString, viewport.getWorldWidth() / 2 - scoreBounds.width / 2, (4 * viewport.getWorldHeight() / 5) - scoreBounds.height / 2);
        bitmapFont.draw(batch, scoreAsString, viewport.getWorldWidth() / 2 - glyphLayout.width / 2, (4 * viewport.getWorldHeight() / 5) - glyphLayout.height / 2);
        String Niveau1="Stage 1";
        bitmapFont2.setColor(0,0,0,1);
        glyphLayout.setText(bitmapFont2, Niveau1);
        String Niveau2="Stage 2";
        bitmapFont2.setColor(0,0,0,1);
        glyphLayout.setText(bitmapFont2, Niveau2);
        String Niveau3="Stage 3";
        bitmapFont2.setColor(0,0,0,1);
        glyphLayout.setText(bitmapFont2, Niveau3);
        String Niveau4="Stage 4";
        bitmapFont2.setColor(0,0,0,1);
        glyphLayout.setText(bitmapFont2, Niveau4);
        String Niveau5="Stage 5";
        bitmapFont2.setColor(0,0,0,1);
        glyphLayout.setText(bitmapFont2, Niveau5);
        if(score<60) {
            bitmapFont2.draw(batch, Niveau1, viewport.getWorldWidth() / 2 - glyphLayout.width / 2, (4 * viewport.getWorldHeight() / 4) - glyphLayout.height / 2);
        }
        if(59<score && score<120) {
            bitmapFont2.draw(batch, Niveau2, viewport.getWorldWidth() / 2 - glyphLayout.width / 2, (4 * viewport.getWorldHeight() / 4) - glyphLayout.height / 2);
        }
        if(119<score && score<180) {
            bitmapFont2.draw(batch, Niveau3, viewport.getWorldWidth() / 2 - glyphLayout.width / 2, (4 * viewport.getWorldHeight() / 4) - glyphLayout.height / 2);
        }
        if(179<score && score<240) {
            bitmapFont2.draw(batch, Niveau4, viewport.getWorldWidth() / 2 - glyphLayout.width / 2, (4 * viewport.getWorldHeight() / 4) - glyphLayout.height / 2);
        }
        if(239<score && score<300) {
            bitmapFont2.draw(batch, Niveau5, viewport.getWorldWidth() / 2 - glyphLayout.width / 2, (4 * viewport.getWorldHeight() / 4) - glyphLayout.height / 2);
        }
    }

    private void drawFlowers() {
        for (Flower flower : flowers) {
            flower.draw(batch);
        }
    }
    private void drawRocks() {
        for (Rock rock : rocks) {
            rock.draw(batch);
        }
    }
    private void drawpuffFishs() {
        for (pufferFish pufffish : pufffishs) {
            pufffish.draw(batch);
        }
    }
    private void draworFishs() {
        for (orangeFish orfish : orfishs) {
            orfish.draw(batch);
        }
    }
    private void drawblFishs() {
        for (blueFish blfish : blfishs) {
            blfish.draw(batch);
        }
    }
    private void drawDebug() {
        shapeRenderer.setProjectionMatrix(camera.projection);
        shapeRenderer.setTransformMatrix(camera.view);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (Flower flower : flowers) {
            flower.drawDebug(shapeRenderer);
        }
        for (Rock rock : rocks) {
            rock.drawDebug(shapeRenderer);
        }
        for (pufferFish pufffish : pufffishs) {
            pufffish.drawDebug(shapeRenderer);
        }
        for (orangeFish orfish : orfishs) {
            orfish.drawDebug(shapeRenderer);
        }
        for (blueFish blfish : blfishs) {
            blfish.drawDebug(shapeRenderer);
        }
        flappee.drawDebug(shapeRenderer);
        shapeRenderer.end();
    }

}
