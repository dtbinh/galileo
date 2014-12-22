package mapping.history.threeD;

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

public class Map3D extends Thread {

	private final static int SIZE = 20;
	private static boolean drawingIt = true;
	private static float movementOfCameraSpeed = 0.1f;
	
	public static Map map = new mapping.MapTestingClass().map;
//	public static Map map = ComputerMain.getMap();

	 public void run() {
		 initDisplay();
		 initGL();
		 gameLoop();
		 cleanUp();
	 }


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
			e.printStackTrace();
		} catch (IOException e) {
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
			
			//keyboard input
			if (Keyboard.isKeyDown(Keyboard.KEY_W))
				cam.move(movementOfCameraSpeed, 1);
			if (Keyboard.isKeyDown(Keyboard.KEY_S))
				cam.move(-movementOfCameraSpeed, 1);
			if (Keyboard.isKeyDown(Keyboard.KEY_A))
				cam.move(movementOfCameraSpeed, 0);
			if (Keyboard.isKeyDown(Keyboard.KEY_D))
				cam.move(-movementOfCameraSpeed, 0);
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
				cam.rotateY(-movementOfCameraSpeed);
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
				cam.rotateY(movementOfCameraSpeed);
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
				 cam.rotateX(movementOfCameraSpeed);
			if (Keyboard.isKeyDown(Keyboard.KEY_UP))
				 cam.rotateX(-movementOfCameraSpeed);
			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
				cam.upDown(-movementOfCameraSpeed);
			if (Keyboard.isKeyDown(Keyboard.KEY_C))
				cam.upDown(movementOfCameraSpeed);

			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			// glClear(GL_COLOR_BUFFER_BIT);
			glLoadIdentity();
			cam.useView();
			wood.bind();

		
			int var1 = 0;
			int var2 = -1;
			// glRotatef(rotation, 1, 1, 0);
			for (int y = map.size() - 1; y >= 0; --y) {
				var2++;
				for (int x = 0; x < map.get(y).size(); x++) {

					// if WALL
					if (map.get(y).get(x) == MapObject.WALL) {
						glColor3f(1f, 0f, 0f);
						drawRect(var1, var2, 100,
								100, drawingIt);
					}

					// if OBSTACLE
					else if (map.get(y).get(x) == MapObject.OBSTACLE) {
						glColor3f(0f, 1f, 0f);

						drawRect(var1, var2 , SIZE,
								SIZE, drawingIt);
					}

					// if EMPTY
					else if (map.get(y).get(x) == MapObject.EMPTY) {
						glColor3f(0f, 0f, 1f);
						drawRect(var1 , var2 , SIZE,
								SIZE, drawingIt);
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


	private static void drawRect(float x, float y, float width, float height, boolean banana) {
		if (banana) {
			glPushMatrix();
			{
				glTranslatef(x, y, 0);
				glBegin(GL_QUADS);
				{

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
			Logger.getLogger(Map3D.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}
}