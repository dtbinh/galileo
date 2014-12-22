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

public class MappingEV3 extends Thread {

	private final static int SIZE = 20;
	private static boolean drawingIt = true;
	
	public static Map map = new mapping.MapTestingClass().map3;
//	public static Map map = ComputerMain.getMap();

//	 public void run() {
//		 initDisplay();
//		 initGL();
//		 gameLoop();
//		 cleanUp();
//	 }

	// old main -> now as thread

	public static void main(String[] args) {
		// System.out.println(a.map.toString());
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	

	private static void gameLoop() {

		Texture wood = loadTexture("wood");
		
		Camera cam = new Camera(70, (float) Display.getWidth()
				/ (float) Display.getHeight(), 0.3f, 1000);

		// float rotation = 0;

		while (!Display.isCloseRequested()) {
			
			if (Keyboard.isKeyDown(Keyboard.KEY_W))
				cam.move(0.1f, 1);
			if (Keyboard.isKeyDown(Keyboard.KEY_S))
				cam.move(-0.1f, 1);
			if (Keyboard.isKeyDown(Keyboard.KEY_A))
				cam.move(0.1f, 0);// cam.rotateY(-0.1f);
			if (Keyboard.isKeyDown(Keyboard.KEY_D))
				cam.move(-0.1f, 0);// cam.rotateY(0.1f);
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
				cam.rotateY(-0.1f);// cam.rotateY(-0.1f);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				cam.rotateY(0.1f);// cam.rotateY(0.1f);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
				 cam.rotateX(0.1f);
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
				 cam.rotateX(-0.1f);
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
				cam.upDown(-0.1f);
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
				cam.upDown(0.1f);
			}

			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			// glClear(GL_COLOR_BUFFER_BIT);
			glLoadIdentity();
			cam.useView();
			wood.bind();
			// glColor3f(0.80f, 0.20f, 0.2f);
			// drawRect(20, 40, SIZE, SIZE, 23, true);

		
			int var1 = 0;
			int var2 = -1;
			// glRotatef(rotation, 1, 1, 0);
			for (int y = map.size() - 1; y >= 0; --y) {
				var2++;
				for (int x = 0; x < map.get(y).size(); x++) {

					if (map.get(y).get(x) == MapObject.WALL) {
						glColor3f(1f, 0f, 0f);

						drawRect(var1 + (SIZE + 5), var2 + (SIZE + 5), SIZE, SIZE,
								drawingIt);
					} else if (map.get(y).get(x) == MapObject.OBSTACLE) {
						glColor3f(0f, 1f, 0f);
						// position 1, position 2, breite, hoehe, true
						drawRect(var1 + (SIZE + 5), var2 + (SIZE + 5), SIZE, SIZE,
								drawingIt);
					} else if (map.get(y).get(x) == MapObject.EMPTY) {
						glColor3f(0f, 0f, 1f);
						drawRect(var1 + (SIZE + 5), var2 + (SIZE + 5), SIZE, SIZE, 
								drawingIt);
					}
					var1++;
				}
				var1 = 0;
			}

			// if (drawingIt == false)
			// drawingIt = true;
			// else
			// drawingIt = false;

			// rotation += 1f;
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
					// glVertex2f(0, 0);
					// glVertex2f(0, height);
					// glVertex2f(width, height);
					// glVertex2f(width, 0);

					// Frontface
					glTexCoord2f(0,0); glVertex3f(-1, -1, 1);
					glTexCoord2f(0,1); glVertex3f(-1, 1, 1);
					glTexCoord2f(1,1); glVertex3f(1, 1, 1);
					glTexCoord2f(1,0); glVertex3f(1, -1, 1);

					// Backface
					glTexCoord2f(0,0); glVertex3f(-1, -1, -1);
					glTexCoord2f(0,1); glVertex3f(-1, 1, -1);
					glTexCoord2f(1,1); glVertex3f(1, 1, -1);
					glTexCoord2f(1,0); glVertex3f(1, -1, -1);
					//
					// // BottomFace
					glTexCoord2f(0,0); glVertex3f(-1, -1, -1);
					glTexCoord2f(0,1); glVertex3f(-1, -1, 1);
					glTexCoord2f(1,1); glVertex3f(-1, 1, 1);
					glTexCoord2f(1,0); glVertex3f(-1, 1, -1);
					//
					// // TopFace
					glTexCoord2f(0,0); glVertex3f(1, -1, -1);
					glTexCoord2f(0,1); glVertex3f(1, -1, 1);
					glTexCoord2f(1,1); glVertex3f(1, 1, 1);
					glTexCoord2f(1,0); glVertex3f(1, 1, -1);
					//
					// // LeftFace
					glTexCoord2f(0,0); glVertex3f(-1, -1, -1);
					glTexCoord2f(0,1); glVertex3f(1, -1, -1);
					glTexCoord2f(1,1); glVertex3f(1, -1, 1);
					glTexCoord2f(1,0); glVertex3f(-1, -1, 1);
					//
					// // RightFace
					glTexCoord2f(0,0); glVertex3f(-1, 1, -1);
					glTexCoord2f(0,1); glVertex3f(1, 1, -1);
					glTexCoord2f(1,1); glVertex3f(1, 1, 1);
					glTexCoord2f(1,0); glVertex3f(-1, 1, 1);
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
			Display.setDisplayMode(new DisplayMode(800, 800));
			Display.create();
		} catch (LWJGLException ex) {
			Logger.getLogger(MappingEV3.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}
}