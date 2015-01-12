package mapping.history;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.ComputerMain;
import mapping.Map;
import mapping.MapObject;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import static org.lwjgl.opengl.GL11.*;

public class MainMap3D extends Thread {

	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 600;
	private static boolean drawingIt = true;
	private static float movementOfCameraSpeed = 0.1f;

	public static Map map = new mapping.MapTestingClass().map3;

	// public static Map map = ComputerMain.getMap();

	public void run() {
		initDisplay();
		initGL();
		gameLoop();
		cleanUp();
	}

	public static void main(String[] args) {
		System.out.println(map.toString());
		initDisplay();
		initGL();
		gameLoop();
		cleanUp();
	}

	public static Texture loadTexture(String key) {
		try {
			return TextureLoader.getTexture("png", new FileInputStream(
					new File("res/" + key + ".png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void gameLoop() {

		Texture wood = loadTexture("wood");

		CameraMap3D cam = new CameraMap3D(70, (float) Display.getWidth()
				/ (float) Display.getHeight(), 0.3f, 1000);

		// float rotation = 0;

		while (!Display.isCloseRequested()) {

			if (Keyboard.isKeyDown(Keyboard.KEY_W))
				cam.move(movementOfCameraSpeed, 1);
			if (Keyboard.isKeyDown(Keyboard.KEY_S))
				cam.move(-movementOfCameraSpeed, 1);
			if (Keyboard.isKeyDown(Keyboard.KEY_A))
				cam.move(movementOfCameraSpeed, 0);
			if (Keyboard.isKeyDown(Keyboard.KEY_D))
				cam.move(-movementOfCameraSpeed, 0);
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
				cam.rotateY(-movementOfCameraSpeed * 2);
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
				cam.rotateY(movementOfCameraSpeed * 2);
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
				cam.rotateX(movementOfCameraSpeed * 2);
			if (Keyboard.isKeyDown(Keyboard.KEY_UP))
				cam.rotateX(-movementOfCameraSpeed * 2);
			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
				cam.upDown(-movementOfCameraSpeed);
			if (Keyboard.isKeyDown(Keyboard.KEY_C))
				cam.upDown(movementOfCameraSpeed);

			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glLoadIdentity();
			cam.useView();
			wood.bind();

			int var1 = 0;
			int var2 = -1;
			// glRotatef(rotation, 1, 1, 0);
			for (int y = map.size() - 1; y >= 0; --y) {
				var2++;
				for (int x = 0; x < map.get(y).size(); x++) {

					int a = 2;

					if (map.get(y).get(x) == MapObject.WALL) {
						glColor3f(1f, 0f, 0f);
						DrawingMap3D
								.drawRectWall(var1 * a, var2 * a, drawingIt);
					} else if (map.get(y).get(x) == MapObject.OBSTACLE) {
						glColor3f(0f, 1f, 0f);

						DrawingMap3D.drawRectObstacle(var1 * a, var2 * a,
								drawingIt);
					} else if (map.get(y).get(x) == MapObject.EMPTY) {
						glColor3f(0f, 0f, 1f);
						DrawingMap3D.drawRectEmpty(var1 * a, var2 * a,
								drawingIt);
					} else if (map.get(y).get(x) == MapObject.ROBOT) {
						glColor3f(1f, 1f, 1f);
						DrawingMap3D.drawRectObstacle(var1 * a, var2 * a,
								drawingIt);
					}
					var1++;
				}
				var1 = 0;
			}

			Display.update();
		}
	}

	private static void initGL() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth(), 0, Display.getHeight(), -1, 1);
		glMatrixMode(GL_MODELVIEW);

		glClearColor(0, 0, 0, 1);

		glDisable(GL_DEPTH_TEST);
	}

	private static void cleanUp() {
		Display.destroy();
	}

	private static void initDisplay() {

		try {
			Display.setDisplayMode(new DisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT));
			Display.create();
		} catch (LWJGLException ex) {
			Logger.getLogger(MainMap3D.class.getName()).log(Level.SEVERE, null,
					ex);
		}
	}
}