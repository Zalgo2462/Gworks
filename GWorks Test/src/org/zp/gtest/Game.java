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
		final GCanvas canvas = new GCanvas(new Dimension(400, 500), 32, 3);
		final GFrame frame = new GFrame("Test", canvas);
		canvas.registerDefaultInputListeners();
		GMutableState gameState1 = new GMutableState();
		/*gameState1.addGPaintStrategy(new GPaintStrategy() {
			int x = 0;
			int y = 0;
			boolean mouthOpen = false;
			@Override
			public void paint(GCanvas canvas, Graphics graphics) {
//				graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
				BufferedImage image = !mouthOpen ?
						Resources.PACMAN_SPRITES.getSprite("RIGHT_PACMAN_1") :
						Resources.PACMAN_SPRITES.getSprite("RIGHT_PACMAN_2");
				graphics.drawImage(image, x, y, null);
				x += 2;
				if(x % 8 == 0)
					mouthOpen = !mouthOpen;
				if(x + image.getWidth() >= canvas.getWidth()) {
					x = 0;
					y += image.getHeight();
				}
			}
		});*/
		for(int iii = 0; iii < canvas.getWidth(); iii += 20) {
			gameState1.addGPaintStrategy(new XLine(iii));
		}
		gameState1.addGPaintStrategy(new Framerate(canvas));
		gameState1.addGPaintStrategy(new XLine(0));
		canvas.setGState(gameState1);
		frame.setVisible(true);
		frame.setResizable(false);
		canvas.requestFocus();
	}
}
