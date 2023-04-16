package Abadash;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.File;
import java.util.List;

import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;

import Abadash.Controllers.AudioManager;
import Abadash.Entities.*;
import static Abadash.Constants.BLOCK_SIZE;
import static Abadash.Constants.JUMP_FORCE;

public class GameTests {
    private JFXPanel jfxPanel = new JFXPanel();

    private Player player;
    private File[] mapsDir;

    @BeforeEach
    void setUp() {
        player = new Player(0, 1);
        mapsDir = null;
    }

    @Test
    void testMaps() {
        
        assertDoesNotThrow(() -> {
                mapsDir = new File(getClass().getResource("/Abadash/maps").toURI()).listFiles();
        });
        assertNotNull(mapsDir);

        for (File file : mapsDir) {
            Map map = new Map(file.getName().replace(".json", ""));
            List<Entity> entities = map.getEntities();
            
            boolean goalExists = false, floorExists = false;
            for (Entity entity : entities) {
                if (entity instanceof Goal)
                    goalExists = true;
                if (entity instanceof Floor)
                    floorExists = true;
            }
            assertTrue(goalExists);
            assertTrue(floorExists);
        }

    }

    @Test
    void testPlayer() {

        Hitbox hitbox = player.getHitbox();
        assertNotNull(hitbox);
        assertEquals(BLOCK_SIZE, hitbox.getHeight());
        assertEquals(BLOCK_SIZE, hitbox.getWidth());

        Sprite sprite = player.getSprite();
        assertNotNull(sprite.getImg());
        assertEquals(BLOCK_SIZE, sprite.getHeight());
        assertEquals(BLOCK_SIZE, sprite.getWidth());

        player.setOnGround(true);
        player.jump();
        assertEquals(-JUMP_FORCE, player.getVelocityY());

        assertFalse(player.isDead());
        player.kill();
        assertTrue(player.isDead());
    }

    @Test
    void testHandleHitPlayer() {
        Map map = new Map("A3");
        List<Entity> entities = map.getEntities();
        Block block = null;
        Spike spike = null;
        
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i) instanceof Block && block == null) {
                block = (Block) entities.get(i);
            }
            if (entities.get(i) instanceof Spike && spike == null) {
                spike = (Spike) entities.get(i);
            }
        }

        player.setOnGround(false);
        block.handleHitPlayer(player, 0);
        assertTrue(player.isOnGround());


        spike.handleHitPlayer(player, 0);
        assertTrue(player.isDead());
    }

    @Test
    void testImageGallery() {

        final String path = "Abadash/sprites/webkom.png";

        Image img1 = ImageGallery.getInstance().load(path);
        Image img2 = ImageGallery.getInstance().load(path);

        assertEquals(img1, img2);

    }

    @Test
    void testAudio() {
        AudioManager audioManager = AudioManager.getInstance();

        assertNotNull(audioManager.getMediaPlayers());

        assertEquals(.75, audioManager.getVolume());
        audioManager.changeVolume(.8);
        assertEquals(.8, audioManager.getVolume());
    }

}
