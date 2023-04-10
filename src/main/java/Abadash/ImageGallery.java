package Abadash;

import javafx.scene.image.Image;

import java.util.HashMap;

public class ImageGallery {
    private static ImageGallery instance;
    private HashMap<String, Image> gallery = new HashMap<>();

    private ImageGallery() {}

    public static ImageGallery getInstance() {
        if (instance == null) {
            instance = new ImageGallery();
        }

        return instance;
    }

    public Image load(String path) {
        if (!gallery.containsKey(path)) {
            gallery.put(path, new Image(path));
        }
        return gallery.get(path);
    }
}
