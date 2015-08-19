package org.zp.gworks.gtest;

import org.zp.gworks.gtest.rendertests.ColorChanger.ColorChangerState;
import org.zp.gworks.gtest.rendertests.Framerate.FramerateState;
import org.zp.gworks.gtest.rendertests.Keyboard.KeyboardState;
import org.zp.gworks.gtest.rendertests.Mouse.MouseState;
import org.zp.gworks.gtest.rendertests.XLine.XLineState;
import org.zp.gworks.gtest.rendertests.YLine.YLineState;
import org.zp.gworks.gui.canvas.GCanvas;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Game extends Frame {
	private GCanvas canvas;

	public Game() {
		init();
		pack();
		setTitle("GTest");
		setVisible(true);
		canvas.requestFocus();
		canvas.addState(new ColorChangerState(canvas));
		canvas.addState(new XLineState(canvas));
		canvas.addState(new YLineState(canvas));
		canvas.addState(new FramerateState(canvas));
		canvas.addState(new KeyboardState(canvas));
		canvas.addState(new MouseState(canvas));
	}

	public static void main(String[] args) {
		new Game();
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
		if (canvas != null)
			return canvas;
		return (canvas = new GCanvas(new Dimension(800, 600), 60, 2));
	}
}
