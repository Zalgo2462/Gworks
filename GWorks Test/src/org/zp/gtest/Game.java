package org.zp.gtest;

import org.zp.gtest.rendertests.*;
import org.zp.gtest.resources.Resources;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.GFrame;
import org.zp.gworks.logic.GState.GImmutableState;
import org.zp.gworks.logic.GState.GMutableState;
import org.zp.gworks.logic.GState.GState;

import java.awt.*;

public class Game {
	public static void main(String[] args) {
		final GCanvas canvas = new GCanvas(new Dimension(400, 500), 32, 3);
		final GFrame frame = new GFrame("Test", canvas);
		canvas.registerDefaultInputListeners();
		GMutableState gameState1 = new GMutableState();
		gameState1.addGPaintStrategy(new ColorChanger());
		gameState1.addGPaintStrategy(new Keyboard());
		gameState1.addGPaintStrategy(new Mouse());
		gameState1.addGPaintStrategy(new Framerate(canvas));
		for(int iii = 0; iii < canvas.getWidth(); iii += 50) {
			gameState1.addGPaintStrategy(new XLine(iii));
		}
		for(int iii = 0; iii < canvas.getHeight(); iii += 50) {
			gameState1.addGPaintStrategy(new YLine(iii));
		}
		canvas.setGState(gameState1);
		frame.setVisible(true);
		frame.setResizable(false);
		canvas.requestFocus();
	}
}
