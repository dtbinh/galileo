package mappingRealtime;

public class GOPlayer extends GameObject {

	public static final int SIZEX = 15;
	public static final int SIZEY = SIZEX * 4;
	public static final float SPEED = 7f;

	public GOPlayer(float x, float y) {
		this.x = x;
		this.y = y;
		this.sx = SIZEX;
		this.sy = SIZEY;

	}

	@Override
	public void update() {

	}

	public void move(float mag) {
		y += SPEED * mag;
	}

	public void move2(float mag) {
		x += SPEED * mag;
	}
}
