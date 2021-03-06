package checkers.game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import gui.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1650;
        config.height = 928;
        new LwjglApplication(new Game(), config);
    }
}
