package org.zp.gtest;

import org.zp.gtest.rendertests.*;
import org.zp.gtest.resources.Resources;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.GFrame;
import org.zp.gworks.gui.canvas.rendering.GPaintStrategy;
import org.zp.gworks.logic.GState.GImmutableState;
import org.zp.gworks.logic.GState.GMutableState;
import org.zp.gworks.logic.GState.GState;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Game {
	public static void main(String[] args) {
		final GCanvas canvas = new GCanvas(new Dimension(400, 500), 32, 2);
		final GFrame frame = new GFrame("Test", canvas);
		canvas.registerDefaultInputListeners();
		final GMutableState gameState1 = new GMutableState();
		gameState1.addGPaintStrategy(new GPaintStrategy() {
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
		gameState1.addGPaintStrategy(new Framerate(canvas));
		canvas.setGState(gameState1);
		frame.setVisible(true);
		frame.setResizable(false);
		canvas.requestFocus();
	}
}
