package org.zp.pacman;

import org.zp.gworks.gui.GFrame;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GPaintStrategy;
import org.zp.pacman.resources.Resources;

import java.awt.*;
import java.awt.image.BufferedImage;

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
		getCanvas().getBackgroundState().addGPaintStrategy(new GPaintStrategy() {
			int x = 0;
			int y = 0;
			boolean mouthOpen = false;
			@Override
			public void paint(GCanvas canvas, Graphics graphics) {
				graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
				BufferedImage image = !mouthOpen ?
						Resources.PACMAN_SPRITES.getSprite("RIGHT_PACMAN_1") :
						Resources.PACMAN_SPRITES.getSprite("RIGHT_PACMAN_2");
				graphics.drawImage(image, x, y, image.getWidth() + 10, image.getHeight() + 10,  null);
				x += 2;
				if(x % 8 == 0)
					mouthOpen = !mouthOpen;
				if(x + image.getWidth() + 10 >= canvas.getWidth()) {
					x = 0;
					y += image.getHeight() + 10;
				}
				if(y + image.getHeight() + 10 >= canvas.getHeight()) {
					x = 0;
					y = 0;
				}
			}
		});
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
