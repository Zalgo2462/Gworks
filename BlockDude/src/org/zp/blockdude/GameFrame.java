package org.zp.blockdude;


import org.zp.blockdude.states.fakeloader.FakeLoaderState;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.logic.GState.GImmutableState;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameFrame extends Frame {
	public static final Dimension DIMENSION = new Dimension(800, 600);
	private static GCanvas canvas;
	public GImmutableState fakeLoadingState;

	public GameFrame() {
		setResizable(true);
		init();
		pack();
		setTitle("BlockDude");
		setVisible(true);
		canvas.requestFocus();
	}

	public static void main(String[] args) {
		new GameFrame();
	}

	public static GCanvas getCanvas() {
		if (canvas != null)
			return canvas;
		return (canvas = new GCanvas(DIMENSION, 50, 2));
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
		fakeLoadingState = new FakeLoaderState();
		canvas.addGState(fakeLoadingState);
		add(canvas);
	}
}
