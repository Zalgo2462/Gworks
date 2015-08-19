package org.zp.blockdude.states.menus.gameover;

import org.zp.blockdude.ColorScheme;
import org.zp.blockdude.Fonts;
import org.zp.blockdude.GameFrame;
import org.zp.blockdude.states.menus.main.MainMenu;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.menus.GMenuState;
import org.zp.gworks.gui.menus.ui.buttons.GTextButton;
import org.zp.gworks.gui.menus.ui.labels.GTextLabel;

import java.awt.*;

/**
 * Date: 8/1/2014
 * Time: 2:22 PM
 */
public class GameOverState extends GMenuState {
	private int score;

	public GameOverState(GCanvas canvas, int score) {
		super(canvas, ColorScheme.MENU_BACKGROUND.getColor(),
				0, 0, GameFrame.DIMENSION.width, GameFrame.DIMENSION.height);
		this.score = score;

		GTextLabel gameOverLabel = new GTextLabel("GAME OVER");
		gameOverLabel.setBgColor(ColorScheme.MENU_BACKGROUND.getColor());
		gameOverLabel.setFgColor(ColorScheme.DARKER_GREEN.getColor());
		gameOverLabel.setFont(Fonts.BFO.getFont().deriveFont(Font.BOLD, 50));

		GTextLabel scoreLabel = new GTextLabel("SCORE: " + score);
		scoreLabel.setBgColor(ColorScheme.MENU_BACKGROUND.getColor());
		scoreLabel.setFgColor(ColorScheme.DARKER_GREEN.getColor());
		scoreLabel.setFont(Fonts.BFO.getFont().deriveFont(Font.BOLD, 50));

		int x = (int) (getBounds().getWidth() / 2 - gameOverLabel.getBounds().getWidth() / 2);
		int y = (int) (
				getBounds().getHeight() / 2 - (
						gameOverLabel.getBounds().getHeight() + scoreLabel.getBounds().getHeight()
				) / 2
		);

		gameOverLabel.setLocation(new Point(x, y));

		x = (int) (getBounds().getWidth() / 2 - scoreLabel.getBounds().getWidth() / 2);
		y += scoreLabel.getBounds().getHeight();

		scoreLabel.setLocation(new Point(x, y));


		GTextButton continueButton = new GTextButton("Continue");
		continueButton.setBgColor(ColorScheme.BUTTON_BACKGROUND.getColor());
		continueButton.setFgColor(ColorScheme.DARKER_GREEN.getColor());
		continueButton.setFont(Fonts.BFO.getFont().deriveFont(Font.BOLD, 32));
		continueButton.setHorizontalMargin((300 - continueButton.getBounds().width) / 2);

		x = (int) (getBounds().getCenterX() - continueButton.getBounds().getCenterX());
		y = continueButton.getBounds().height * 8;
		final Point p = new Point(x, y);
		continueButton.setLocation(p);
		continueButton.setOutlined(true);

		final GameOverState state = this;
		continueButton.addRunnable(new Runnable() {
			@Override
			public void run() {
				state.getCanvas().removeState(state);
				state.getCanvas().addState(MainMenu.getMenuState(state.getCanvas()));
			}
		});

		addGLabel(gameOverLabel);
		addGLabel(scoreLabel);
		addGButton(continueButton);
		//addRenderListener(new GameOverRenderer(this));
	}

	public int getScore() {
		return score;
	}

}
