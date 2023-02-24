package spaceinvaders.main;

/**
 * The main class for the program. Used to execute the game and
 * handle command line parameters passed in from the user.
 */
public class Executer {
	
	/**
	 * The main function. Handles opening the game window with
	 * the proper settings.
	 * 
	 * @param args The command line arguments. For fullscreen run
	 * with -fullscreen. To run with no sounds run -nosound.
	 */
	public static void main(String[] args) {
		// Create a loading screen while the game loads all of the resources.
		LoadingScreen lScreen = new LoadingScreen();
		
		// Set the flags for the game based on the user input.
		// By default we run with fullscreen = false and sound = true.
		boolean fullscreen = false, sound = true;
		if (containsArg(args, "-fullscreen"))
			fullscreen = true;
		if (containsArg(args, "-nosound"))
			sound = false;
		
		// Initialize the new game window passing to it the flags and the
		// loading screen so that it can close it once all loading is done.
		new GameWindow(fullscreen, sound, lScreen);
	}
	
	/**
	 * Checks if the argument list contains a certain argument.
	 * This ensures that the arguments can be passed in whichever order
	 * the user chooses.
	 * 
	 * @param args The list of arguments to check.
	 * @param arg The argument to check if it is inside args.
	 * @return true If arg is in args, false otherwise.
	 */
	private static boolean containsArg(String[] args, String arg) {
		for (int i = 0; i < args.length; i++)
			if (args[i].equals(arg))
				return true;
		return false;
	}
}