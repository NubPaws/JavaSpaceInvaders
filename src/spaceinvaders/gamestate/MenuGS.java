package spaceinvaders.gamestate;

import static spaceinvaders.utils.Helper.clampi;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import spaceinvaders.handlers.ImagesHandler;
import spaceinvaders.handlers.Keys;
import spaceinvaders.main.GameBoard;
import spaceinvaders.objects.GameObjectID;

public class MenuGS extends GameState {
	
	public MenuGS(GameStateManager gsm) { super(gsm); }
	
	private BufferedImage background;
	private int selected;
	private Point markerPos;
	private int playWidth, creditsWidth, quitWidth;
	
	@Override
	public void init() {
		background = ImagesHandler.get("sprites/background.png");
		selected = 0;
		markerPos = new Point(0, 0);
		
	}
	
	@Override
	public void update(int tick) {
		if (Keys.isPressed(Keys.DownArrow) || Keys.isPressed(Keys.S))
			selected = clampi(selected +1, 0, 2);
		if (Keys.isPressed(Keys.UpArrow) || Keys.isPressed(Keys.W))
			selected = clampi(selected -1, 0, 2);
		
		if (Keys.isPressed(Keys.Enter) || Keys.isPressed(Keys.Space)) {
			switch (selected) {
			case 0:
				gsm.setState(GameStateID.Game);
				break;
			case 1:
				gsm.setState(GameStateID.Credits);
				break;
			case 2:
				dispose();
				break;
			}
		}
		
		calculateMarkerPosition();
		
		if (Keys.isPressed(Keys.Escape))
			dispose();
	}
	
	@Override
	public void render(Graphics2D g2d) {
		g2d.drawImage(background, 0, 0, null);
		
		
		defineColor(g2d, 0);
		playWidth = g2d.getFontMetrics().stringWidth("Play");
		g2d.drawString("Play", (GameBoard.WIDTH -playWidth) /2, 125);
		
		defineColor(g2d, 1);
		creditsWidth = g2d.getFontMetrics().stringWidth("Credits");
		g2d.drawString("Credits", (GameBoard.WIDTH -creditsWidth) /2, 150);
		
		defineColor(g2d, 2);
		g2d.drawString("Quit", (GameBoard.WIDTH -quitWidth) /2, 175);
		quitWidth = g2d.getFontMetrics().stringWidth("Quit");
		
		g2d.drawImage(GameObjectID.EnemyMid.image(), markerPos.x, markerPos.y, null);
	}
	
	@Override
	public void dispose() {
		gsm.dispose();
	}
	
	private void defineColor(Graphics2D g2d, int i) {
		if (selected == i)
			g2d.setColor(new Color(255, 215, 0));
		else g2d.setColor(Color.white);
	}
	
	private void calculateMarkerPosition() {
		switch (selected) {
		case 0:
			markerPos.x = (GameBoard.WIDTH /2) -(playWidth /2) -15;
			markerPos.y = 117;
			break;
		case 1:
			markerPos.x = (GameBoard.WIDTH /2) -(creditsWidth /2) -15;
			markerPos.y = 142;
			break;
		case 2:
			markerPos.x = (GameBoard.WIDTH /2) -(quitWidth /2) -15;
			markerPos.y = 168;
			break;
		}
	}
	
}