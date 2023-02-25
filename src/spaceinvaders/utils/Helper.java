package spaceinvaders.utils;

public final class Helper {
	
	public static float clampf(float cur, float min, float max) {
		return (cur < min) ? min : (cur > max) ? max : cur;
	}
	
	public static int clampi(int cur, int min, int max) {
		return (cur < min) ? min : (cur > max) ? max : cur;
	}
	
}