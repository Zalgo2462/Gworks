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
		gameState1.addGPaintStrategy(new ColorChanger());
		for(int iii = 0; iii < canvas.getWidth(); iii += 20)
			gameState1.addGPaintStrategy(new XLine(iii));
		for(int iii = 0; iii < canvas.getHeight(); iii += 20)
			gameState1.addGPaintStrategy(new YLine(iii));
		gameState1.addGPaintStrategy(new Keyboard());
		gameState1.addGPaintStrategy(new Mouse());
		gameState1.addGPaintStrategy(new Framerate(canvas));
		canvas.setGState(gameState1);
		frame.setVisible(true);
		frame.setResizable(false);
		canvas.requestFocus();
	}
}
