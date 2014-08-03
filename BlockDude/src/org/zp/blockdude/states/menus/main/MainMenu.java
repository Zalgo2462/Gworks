package org.zp.blockdude.states.menus.main;

import org.zp.blockdude.ColorScheme;
import org.zp.blockdude.GameFrame;
import org.zp.blockdude.Level;
import org.zp.blockdude.states.menus.GMenuState;
import org.zp.blockdude.states.menus.ui.GTextButton;
import org.zp.blockdude.states.playstate.PlayState;
import org.zp.gtest.rendertests.ColorChanger.ColorChangerState;
import org.zp.gtest.rendertests.Framerate.FramerateState;
import org.zp.gtest.rendertests.Keyboard.KeyboardState;
import org.zp.gtest.rendertests.Mouse.MouseState;
import org.zp.gtest.rendertests.XLine.XLineState;
import org.zp.gtest.rendertests.YLine.YLineState;
import org.zp.gworks.gui.canvas.GCanvas;

import java.awt.*;

/**
 * Date: 8/2/2014
 * Time: 6:01 PM
 */
public class MainMenu {
	private static GMenuState menuState;

	public static GMenuState getMenuState(GCanvas canvas) {
		if (menuState == null) {
			menuState = new GMenuState(
					canvas,
					ColorScheme.MENU_BACKGROUND.getColor(),
					0, 0,
					GameFrame.DIMENSION.width, GameFrame.DIMENSION.height
			);
			GTextButton playButton = createPlayButton(menuState, 400);
			menuState.addGButton(playButton);
			GTextButton testButton = createTestButton(menuState, 400);
			menuState.addGButton(testButton);
		}
		return menuState;
	}

	private static GTextButton createPlayButton(final GMenuState menuState, int width) {
		GTextButton playButton = new GTextButton("Play Game");
		playButton.setBgColor(ColorScheme.BUTTON_BACKGROUND.getColor());
		playButton.setFgColor(ColorScheme.DARKER_GREEN.getColor());
		playButton.setFont(new Font("BatmanForeverOutline", Font.BOLD, 32));
		playButton.setHorizontalMargin((width - playButton.getBounds().width) / 2);
		int x = (int) (menuState.getBounds().getCenterX() - playButton.getBounds().getCenterX());
		int y = playButton.getBounds().height * 4;
		final Point p = new Point(x, y);
		playButton.setLocation(p);

		playButton.setOutlined(true);
		playButton.addRunnable(new Runnable() {
			@Override
			public void run() {
				PlayState playState = new PlayState(menuState.getCanvas());
				menuState.getCanvas().removeState(menuState);
				menuState.getCanvas().addState(playState);
				playState.initLevel(Level.ONE);
			}
		});
		return playButton;
	}

	private static GTextButton createTestButton(final GMenuState menuState, int width) {
		GTextButton testButton = new GTextButton("Display Tests");
		testButton.setBgColor(ColorScheme.BUTTON_BACKGROUND.getColor());
		testButton.setFgColor(ColorScheme.DARKER_GREEN.getColor());
		testButton.setFont(new Font("BatmanForeverOutline", Font.BOLD, 32));
		testButton.setHorizontalMargin((width - testButton.getBounds().width) / 2);
		int x = (int) (menuState.getBounds().getCenterX() - testButton.getBounds().getCenterX());
		int y = testButton.getBounds().height * 6;
		Point p = new Point(x, y);
		testButton.setLocation(p);

		testButton.setOutlined(true);
		testButton.addRunnable(new Runnable() {
			@Override
			public void run() {
				menuState.getCanvas().removeState(menuState);
				menuState.getCanvas().addState(new ColorChangerState(menuState.getCanvas()));
				menuState.getCanvas().addState(new XLineState(menuState.getCanvas()));
				menuState.getCanvas().addState(new YLineState(menuState.getCanvas()));
				menuState.getCanvas().addState(new FramerateState(menuState.getCanvas()));
				menuState.getCanvas().addState(new KeyboardState(menuState.getCanvas()));
				menuState.getCanvas().addState(new MouseState(menuState.getCanvas()));
			}
		});
		return testButton;
	}
}
