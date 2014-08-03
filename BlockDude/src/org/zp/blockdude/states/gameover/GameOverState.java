package org.zp.blockdude.states.gameover;

import org.zp.blockdude.ColorScheme;
import org.zp.blockdude.GameFrame;
import org.zp.blockdude.states.gameover.renderlisteners.GameOverRenderer;
import org.zp.blockdude.states.menus.GMenuState;
import org.zp.blockdude.states.menus.main.MainMenu;
import org.zp.blockdude.states.menus.ui.GTextButton;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;
import org.zp.gworks.logic.GState.GImmutableState;

import java.awt.*;

/**
 * Date: 8/1/2014
 * Time: 2:22 PM
 */
public class GameOverState extends GImmutableState {
	private int score;

	public GameOverState(final GCanvas canvas, int score) {
		super(canvas);
		final GameOverState thisState = this;
		this.score = score;
		final GMenuState continuer = new GMenuState(canvas, ColorScheme.MENU_BACKGROUND.getColor(),
				0, 0, GameFrame.DIMENSION.width, GameFrame.DIMENSION.height);
		GTextButton continueButton = new GTextButton("Continue");
		continueButton.setBgColor(ColorScheme.BUTTON_BACKGROUND.getColor());
		continueButton.setFgColor(ColorScheme.DARKER_GREEN.getColor());
		continueButton.setFont(new Font("BatmanForeverOutline", Font.BOLD, 32));
		continueButton.setHorizontalMargin((300 - continueButton.getBounds().width) / 2);
		int x = (int) (continuer.getBounds().getCenterX() - continueButton.getBounds().getCenterX());
		int y = continueButton.getBounds().height * 8;
		final Point p = new Point(x, y);
		continueButton.setLocation(p);

		continueButton.setOutlined(true);
		continueButton.addRunnable(new Runnable() {
			@Override
			public void run() {
				continuer.getCanvas().removeGState(continuer);
				continuer.getCanvas().removeGState(thisState);
				continuer.getCanvas().addGState(MainMenu.getMenuState(canvas));
			}
		});

		continuer.addGButton(continueButton);
		canvas.addGState(continuer);

		setRenderStrategies(new GRenderListener[]{new GameOverRenderer(this)});

	}

	public int getScore() {
		return score;
	}

}
