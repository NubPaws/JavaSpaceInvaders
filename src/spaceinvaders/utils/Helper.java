package spaceinvaders.utils;

/**
 * General helper functions class.
 */
public final class Helper {
	
	/**
	 * Clamps the current float value between a max and a min values.
	 * @param cur the current value.
	 * @param min the minimal value.
	 * @param max the maximal value.
	 * @return the clamped value.
	 */
	public static float clampf(float cur, float min, float max) {
		return (cur < min) ? min : (cur > max) ? max : cur;
	}
	
	/**
	 * Clamps the current int value between a max and a min values.
	 * @param cur the current value.
	 * @param min the minimal value.
	 * @param max the maximal value.
	 * @return the clamped value.
	 */
	public static int clampi(int cur, int min, int max) {
		return (cur < min) ? min : (cur > max) ? max : cur;
	}
	
}