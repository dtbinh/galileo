package mappingRealtime;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class Game {

	private ArrayList<GameObject> objects;
	private GOPlayer player;

	public Game() {
		objects = new ArrayList<GameObject>();

		// position where to start with the robot(x, y)
		player = new GOPlayer(Display.getWidth() / 2, Display.getHeight() / 2
				- GOPlayer.SIZEY / 2);

		objects.add(player);

	}

	public void getInput() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W)
				|| Keyboard.isKeyDown(Keyboard.KEY_UP))
			player.move(1);
		if (Keyboard.isKeyDown(Keyboard.KEY_S)
				|| Keyboard.isKeyDown(Keyboard.KEY_DOWN))
			player.move(-1);
		if (Keyboard.isKeyDown(Keyboard.KEY_A)
				|| Keyboard.isKeyDown(Keyboard.KEY_LEFT))
			player.move2(-1);
		if (Keyboard.isKeyDown(Keyboard.KEY_D)
				|| Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
			player.move2(1);
	}

	public void update() {
		for (GameObject go : objects)
			go.update();

	}

	public void render() {
		for (GameObject go : objects)
			go.render();

		Display.setTitle("Wer das liest kann lesen");
	}

}