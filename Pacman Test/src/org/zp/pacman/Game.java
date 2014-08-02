package org.zp.pacman;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.pacman.states.test.PacmanState;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Game extends Frame {
	public static final String TITLE = "Pacman";
	public static final Dimension GAME_SIZE = new Dimension(400, 500);
	public static final int FPS = 32;
	public static final int BUFFERS = 2;
	private static GCanvas canvas;

	public Game() {
		init();
		pack();
		setTitle(TITLE);
		setVisible(true);
		setResizable(false);
		canvas.requestFocus();
	}

	public static void main(final String args[]) {
		new Game();

	}

	private void init() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				canvas.dispose();
				dispose();
				System.exit(0);
			}
		});
		canvas = new GCanvas(GAME_SIZE, FPS, BUFFERS);
		canvas.addGState(new PacmanState(canvas));
		add(canvas);
	}
}
