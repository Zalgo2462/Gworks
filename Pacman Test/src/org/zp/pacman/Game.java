package org.zp.pacman;

import org.zp.gworks.gui.GFrame;
import org.zp.gworks.gui.canvas.GCanvas;

import java.awt.*;

public class Game {
	private static GFrame frame;
	private static GCanvas canvas;
	public static final String TITLE = "Pacman";
	public static final Dimension GAME_SIZE = new Dimension(400, 500);
	public static final int FPS = 32;
	public static final int BUFFERS = 2;

	public static void main(final String args[]) {
		frame = new GFrame(TITLE, getCanvas());
		frame.setVisible(true);
	}

	public static GFrame getFrame() {
		if(frame == null) {
			frame = new GFrame(TITLE, getCanvas());
			frame.setResizable(false);
		}
		return frame;
	}

	public static GCanvas getCanvas() {
		if(canvas == null) {
			canvas = new GCanvas(GAME_SIZE, FPS, BUFFERS);
			canvas.registerDefaultInputListeners();
		}
		return canvas;
	}
}
