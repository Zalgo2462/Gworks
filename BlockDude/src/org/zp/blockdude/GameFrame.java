package org.zp.blockdude;


import org.zp.blockdude.states.menus.GMenuState;
import org.zp.blockdude.states.menus.ui.GButton;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameFrame extends Frame {
	public static final Dimension DIMENSION = new Dimension(800, 600);
	private static GCanvas canvas;

	public GameFrame() {
		setResizable(true);
		init();
		pack();
		setTitle("BlockDude");
		setVisible(true);
		requestFocus();
		canvas.requestFocus();
	}

	public static void main(String[] args) {
		new GameFrame();
	}

	public void init() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				canvas.dispose();
				dispose();
				System.exit(0);
			}
		});
		canvas = new GCanvas(DIMENSION, 60, 2);

		final GMenuState menuState = new GMenuState(
				canvas,
				ColorScheme.MENU_BACKGROUND.getColor(),
				0, 0,
				DIMENSION.width, DIMENSION.height
		);

		GButton playButton = createPlayButton(menuState, 400);
		menuState.addGButton(playButton);
		GButton testButton = createTestButton(menuState, 400);
		menuState.addGButton(testButton);

		canvas.addGState(menuState);
		add(canvas);
	}

	public GButton createPlayButton(final GMenuState menuState, int width) {
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
				PlayState playState = new PlayState(canvas);
				canvas.removeGState(menuState);
				canvas.addGState(playState);
				playState.initLevel(Level.ONE);
			}
		});
		return playButton;
	}

	public GButton createTestButton(final GMenuState menuState, int width) {
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
				canvas.removeGState(menuState);
				canvas.addGState(new ColorChangerState(canvas));
				canvas.addGState(new XLineState(canvas));
				canvas.addGState(new YLineState(canvas));
				canvas.addGState(new FramerateState(canvas));
				canvas.addGState(new KeyboardState(canvas));
				canvas.addGState(new MouseState(canvas));
			}
		});
		return testButton;

	}
}
