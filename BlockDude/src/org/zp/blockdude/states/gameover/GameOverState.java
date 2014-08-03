package org.zp.blockdude.states.gameover;

import org.zp.blockdude.ColorScheme;
import org.zp.blockdude.GameFrame;
import org.zp.blockdude.states.gameover.renderlisteners.GameOverRenderer;
import org.zp.blockdude.states.menus.GMenuState;
import org.zp.blockdude.states.menus.main.MainMenu;
import org.zp.blockdude.states.menus.ui.GTextButton;
import org.zp.gworks.gui.canvas.GCanvas;

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
		GTextButton continueButton = new GTextButton("Continue");
		continueButton.setBgColor(ColorScheme.BUTTON_BACKGROUND.getColor());
		continueButton.setFgColor(ColorScheme.DARKER_GREEN.getColor());
		continueButton.setFont(new Font("BatmanForeverOutline", Font.BOLD, 32));
		continueButton.setHorizontalMargin((300 - continueButton.getBounds().width) / 2);
		int x = (int) (getBounds().getCenterX() - continueButton.getBounds().getCenterX());
		int y = continueButton.getBounds().height * 8;
		final Point p = new Point(x, y);
		continueButton.setLocation(p);
		continueButton.setOutlined(true);

		final GameOverState state = this;
		continueButton.addRunnable(new Runnable() {
			@Override
			public void run() {
				state.getCanvas().removeGState(state);
				state.getCanvas().addGState(MainMenu.getMenuState(state.getCanvas()));
			}
		});

		addGButton(continueButton);
		canvas.addGState(this);

		addGRenderListener(new GameOverRenderer(this));

	}

	public int getScore() {
		return score;
	}

}
