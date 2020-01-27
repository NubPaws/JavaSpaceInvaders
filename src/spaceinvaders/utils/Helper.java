package spaceinvaders.utils;

public final class Helper {
	
	public static float clamp(float cur, float min, float max) {
		return (cur < min) ? min : (cur > max) ? max : cur;
	}
	
}