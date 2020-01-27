package spaceinvaders.main;

public class Executer {
	
	public static void main(String[] args) {
		LoadingScreen lScreen = new LoadingScreen();
		boolean fullscreen = false, sound = true;
		if (containsArg(args, "-fullscreen"))
			fullscreen = true;
		if (containsArg(args, "-nosound"))
			sound = false;
		
		new GameWindow(fullscreen, sound, lScreen);
	}
	
	private static boolean containsArg(String[] args, String arg) {
		for (int i = 0; i < args.length; i++)
			if (args[i].equals(arg))
				return true;
		return false;
	}
}