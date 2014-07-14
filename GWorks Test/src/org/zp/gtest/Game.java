package org.zp.gtest;

import org.zp.gtest.rendertests.ColorChanger.ColorChangerState;
import org.zp.gtest.rendertests.Framerate.FramerateState;
import org.zp.gtest.rendertests.ImageViewer.ImageViewerState;
import org.zp.gtest.rendertests.Keyboard.KeyboardState;
import org.zp.gtest.rendertests.Mouse.MouseState;
import org.zp.gtest.rendertests.XLine.XLineState;
import org.zp.gtest.rendertests.YLine.YLineState;
import org.zp.gtest.resources.Resources;
import org.zp.gworks.gui.canvas.GCanvas;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Game extends Frame {
	private GCanvas canvas;

	public static void main(String[] args) {
		new Game();
	}

	public Game() {
		init();
		pack();
		setTitle("GTest");
		setVisible(true);
		//setResizable(false);
		canvas.requestFocus();
		canvas.addGState(new ColorChangerState());
		canvas.addGState(new XLineState());
		canvas.addGState(new YLineState());
		canvas.addGState(new ImageViewerState(Resources.PACMAN_SPRITES.getSprite("RIGHT_PACMAN_1")));
		canvas.addGState(new FramerateState());
		canvas.addGState(new KeyboardState());
		canvas.addGState(new MouseState());
	}

	public void init() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				canvas.dispose();
				dispose();
				System.exit(0);
			}
		});
		canvas = getCanvas();
		add(canvas);
	}

	public GCanvas getCanvas() {
		if(canvas != null)
			return canvas;
		return (canvas = new GCanvas(new Dimension(800, 600), 60, 2));
	}
}
