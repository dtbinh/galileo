package mapping.history;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;

public class DrawingMap3D {
	public static void drawRectWall(float x, float y, boolean shouldItBeDrawn) {
		if (shouldItBeDrawn) {
			glPushMatrix();
			{
				glTranslatef(x, y, 0);
				glBegin(GL_QUADS);
				{
					// Top
					glTexCoord2f(0, 0);
					glVertex3f(1.0f, 1.0f, -1f); // Top Right
					glTexCoord2f(0, 1);
					glVertex3f(-1.0f, 1.0f, -1f); // Top Left
					glTexCoord2f(1, 1);
					glVertex3f(-1.0f, 1.0f, 1.0f); // Bottom Left
					glTexCoord2f(1, 0);
					glVertex3f(1.0f, 1.0f, 1.0f); // Bottom Right

					// Bottom
					glTexCoord2f(0, 0);
					glVertex3f(1.0f, -1.0f, 1.0f); // Top Right
					glTexCoord2f(0, 1);
					glVertex3f(-1.0f, -1.0f, 1.0f); // Top Left
					glTexCoord2f(1, 1);
					glVertex3f(-1.0f, -1.0f, -1f); // Bottom Left
					glTexCoord2f(1, 0);
					glVertex3f(1.0f, -1.0f, -1f); // Bottom Right

					// Front
					glTexCoord2f(0, 0);
					glVertex3f(1.0f, 1.0f, 1.0f); // Top Right
					glTexCoord2f(0, 1);
					glVertex3f(-1.0f, 1.0f, 1.0f); // Top Left
					glTexCoord2f(1, 1);
					glVertex3f(-1.0f, -1.0f, 1.0f); // Bottom Left
					glTexCoord2f(1, 0);
					glVertex3f(1.0f, -1.0f, 1.0f); // Bottom Right

					// Back
					glTexCoord2f(0, 0);
					glVertex3f(1.0f, -1.0f, -1.0f); // Bottom Left
					glTexCoord2f(0, 1);
					glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Right
					glTexCoord2f(1, 1);
					glVertex3f(-1.0f, 1.0f, -1.0f); // Top Right
					glTexCoord2f(1, 0);
					glVertex3f(1.0f, 1.0f, -1.0f); // Top Left

					// Left
					glTexCoord2f(0, 0);
					glVertex3f(-1.0f, 1.0f, 1.0f); // Top Right
					glTexCoord2f(0, 1);
					glVertex3f(-1.0f, 1.0f, -1.0f); // Top Left
					glTexCoord2f(1, 1);
					glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Left
					glTexCoord2f(1, 0);
					glVertex3f(-1.0f, -1.0f, 1.0f); // Bottom Right

					// Right
					glTexCoord2f(0, 0);
					glVertex3f(1.0f, 1.0f, -1.0f); // Top Right
					glTexCoord2f(0, 1);
					glVertex3f(1.0f, 1.0f, 1.0f); // Top Left
					glTexCoord2f(1, 1);
					glVertex3f(1.0f, -1.0f, 1.0f); // Bottom Left
					glTexCoord2f(1, 0);
					glVertex3f(1.0f, -1.0f, -1.0f); // Bottom Right
				}
				glEnd();
			}
			glPopMatrix();
		}
	}

	public static void drawRectEmpty(float x, float y, boolean shouldItBeDrawn) {
		if (shouldItBeDrawn) {
			glPushMatrix();
			{
				glTranslatef(x, y, 0);
				glBegin(GL_QUADS);
				{

					// Top
					glTexCoord2f(0, 0);
					glVertex3f(1.0f, 1.0f, -1f); // Top Right
					glTexCoord2f(0, 1);
					glVertex3f(-1.0f, 1.0f, -1f); // Top Left
					glTexCoord2f(1, 1);
					glVertex3f(-1.0f, 1.0f, -0.5f); // Bottom Left
					glTexCoord2f(1, 0);
					glVertex3f(1.0f, 1.0f, -0.5f); // Bottom Right

					// Bottom
					glTexCoord2f(0, 0);
					glVertex3f(1.0f, -1.0f, -0.5f); // Top Right
					glTexCoord2f(0, 1);
					glVertex3f(-1.0f, -1.0f, -0.5f); // Top Left
					glTexCoord2f(1, 1);
					glVertex3f(-1.0f, -1.0f, -1f); // Bottom Left
					glTexCoord2f(1, 0);
					glVertex3f(1.0f, -1.0f, -1f); // Bottom Right

					// Front
					glTexCoord2f(0, 0);
					glVertex3f(1.0f, 1.0f, -0.5f); // Top Right
					glTexCoord2f(0, 1);
					glVertex3f(-1.0f, 1.0f, -0.5f); // Top Left
					glTexCoord2f(1, 1);
					glVertex3f(-1.0f, -1.0f, -0.5f); // Bottom Left
					glTexCoord2f(1, 0);
					glVertex3f(1.0f, -1.0f, -0.5f); // Bottom Right

					// Back
					glTexCoord2f(0, 0);
					glVertex3f(1.0f, -1.0f, -1.0f); // Bottom Left
					glTexCoord2f(0, 1);
					glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Right
					glTexCoord2f(1, 1);
					glVertex3f(-1.0f, 1.0f, -1.0f); // Top Right
					glTexCoord2f(1, 0);
					glVertex3f(1.0f, 1.0f, -1.0f); // Top Left

					// Left
					glTexCoord2f(0, 0);
					glVertex3f(-1.0f, 1.0f, -0.5f); // Top Right
					glTexCoord2f(0, 1);
					glVertex3f(-1.0f, 1.0f, -1.0f); // Top Left
					glTexCoord2f(1, 1);
					glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Left
					glTexCoord2f(1, 0);
					glVertex3f(-1.0f, -1.0f, -0.5f); // Bottom Right

					// Right
					glTexCoord2f(0, 0);
					glVertex3f(1.0f, 1.0f, -1.0f); // Top Right
					glTexCoord2f(0, 1);
					glVertex3f(1.0f, 1.0f, -0.5f); // Top Left
					glTexCoord2f(1, 1);
					glVertex3f(1.0f, -1.0f, -0.5f); // Bottom Left
					glTexCoord2f(1, 0);
					glVertex3f(1.0f, -1.0f, -1.0f); // Bottom Right
				}
				glEnd();
			}
			glPopMatrix();
		}
	}

	public static void drawRectObstacle(float x, float y,
			boolean shouldItBeDrawn) {
		if (shouldItBeDrawn) {
			glPushMatrix();
			{
				glTranslatef(x, y, 0);
				glBegin(GL_QUADS);
				{
					// Top
					glTexCoord2f(0, 0);
					glVertex3f(1.0f, 1.0f, -1f); // Top Right
					glTexCoord2f(0, 1);
					glVertex3f(-1.0f, 1.0f, -1f); // Top Left
					glTexCoord2f(1, 1);
					glVertex3f(-1.0f, 1.0f, 0.1f);// Bottom Left
					glTexCoord2f(1, 0);
					glVertex3f(1.0f, 1.0f, 0.1f); // Bottom Right

					// Bottom
					glTexCoord2f(0, 0);
					glVertex3f(1.0f, -1.0f, 0.1f); // Top Right
					glTexCoord2f(0, 1);
					glVertex3f(-1.0f, -1.0f, 0.1f); // Top Left
					glTexCoord2f(1, 1);
					glVertex3f(-1.0f, -1.0f, -1f); // Bottom Left
					glTexCoord2f(1, 0);
					glVertex3f(1.0f, -1.0f, -1f); // Bottom Right

					// Front
					glTexCoord2f(0, 0);
					glVertex3f(1.0f, 1.0f, 0.1f); // Top Right
					glTexCoord2f(0, 1);
					glVertex3f(-1.0f, 1.0f, 0.1f); // Top Left
					glTexCoord2f(1, 1);
					glVertex3f(-1.0f, -1.0f, 0.1f); // Bottom Left
					glTexCoord2f(1, 0);
					glVertex3f(1.0f, -1.0f, 0.1f); // Bottom Right

					// Back
					glTexCoord2f(0, 0);
					glVertex3f(1.0f, -1.0f, -1.0f); // Bottom Left
					glTexCoord2f(0, 1);
					glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Right
					glTexCoord2f(1, 1);
					glVertex3f(-1.0f, 1.0f, -1.0f); // Top Right
					glTexCoord2f(1, 0);
					glVertex3f(1.0f, 1.0f, -1.0f); // Top Left

					// Left
					glTexCoord2f(0, 0);
					glVertex3f(-1.0f, 1.0f, 0.1f); // Top Right
					glTexCoord2f(0, 1);
					glVertex3f(-1.0f, 1.0f, -1.0f); // Top Left
					glTexCoord2f(1, 1);
					glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Left
					glTexCoord2f(1, 0);
					glVertex3f(-1.0f, -1.0f, 0.1f); // Bottom Right

					// Right
					glTexCoord2f(0, 0);
					glVertex3f(1.0f, 1.0f, -1.0f); // Top Right
					glTexCoord2f(0, 1);
					glVertex3f(1.0f, 1.0f, 0.1f); // Top Left
					glTexCoord2f(1, 1);
					glVertex3f(1.0f, -1.0f, 0.1f); // Bottom Left
					glTexCoord2f(1, 0);
					glVertex3f(1.0f, -1.0f, -1.0f); // Bottom Right
				}
				glEnd();
			}
			glPopMatrix();
		}
	}

}
