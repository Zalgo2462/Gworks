package org.zp.blockdude;


import org.zp.blockdude.states.fakeloader.FakeLoaderState;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.logic.GState.GImmutableState;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameFrame extends Frame {
	private static GCanvas canvas;
	public static final Dimension DIMENSION = new Dimension(800, 600);
	public GImmutableState fakeLoadingState;

	public static void main(String[] args) {
		new GameFrame();
	}

	public GameFrame() {
		setResizable(true);
		init();
		pack();
		setTitle("BlockDude");
		setVisible(true);
		canvas.requestFocus();
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

	public static GCanvas getCanvas() {
		if (canvas != null)
			return canvas;
		return (canvas = new GCanvas(DIMENSION, 50, 2));
	}
}
