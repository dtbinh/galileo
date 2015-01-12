package mapping.history;

import java.util.logging.Level;
import java.util.logging.Logger;

import main.ComputerMain;
import mapping.Map;
import mapping.MapObject;
//import main.Main_Computer;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

public class Map2D extends Thread {
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 600;
	private final static int SIZE = 20;
	private static boolean drawingIt = true;

	public static Map map = new mapping.MapTestingClass().map;

	// public static Map map = ComputerMain.getMap();

	public void run() {
		System.out.println(map.toString());
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

	private static void gameLoop() {
		while (!Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT);
			glLoadIdentity();
			// glColor3f(0.80f, 0.20f, 0.2f);
			// drawRect(20, 40, SIZEX, SIZEY, 23, true);

			int var1 = 0;
			int var2 = -1;

			for (int y = map.size() - 1; y >= 0; --y) {
				var2++;
				for (int x = 0; x < map.get(y).size(); x++) {

					if (map.get(y).get(x) == MapObject.WALL) {
						glColor3f(0.80f, 0.20f, 0.2f);
						drawRect(var1 * (SIZE + 5), var2 * (SIZE + 5), SIZE,
								SIZE, drawingIt);
					} else if (map.get(y).get(x) == MapObject.OBSTACLE) {
						glColor3f(0.80f, 0.20f, 0.98f);
						drawRect(var1 * (SIZE + 5), var2 * (SIZE + 5), SIZE,
								SIZE, drawingIt);
					} else if (map.get(y).get(x) == MapObject.ROBOT) {
						glColor3f(0.30f, 0.20f, 0.28f);
						drawRect(var1 * (SIZE + 5), var2 * (SIZE + 5), SIZE,
								SIZE, drawingIt);
					}
					var1++;
				}
				var1 = 0;
			}

			Display.update();
		}
	}

	private static void drawRect(float x, float y, float width, float height,
			boolean banana) {
		drawRect(x, y, width, height, 0, banana);
	}

	private static void drawRect(float x, float y, float width, float height,
			float rot, boolean banana) {
		if (banana) {
			glPushMatrix();
			{
				glTranslatef(x, y, 0);
				glRotatef(rot, 0, 0, 1);

				glBegin(GL_QUADS);
				{
					glVertex2f(0, 0);
					glVertex2f(0, height);
					glVertex2f(width, height);
					glVertex2f(width, 0);
				}
				glEnd();
			}
			glPopMatrix();
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
			Logger.getLogger(Map2D.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}