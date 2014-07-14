package org.zp.blockdude;


import org.zp.blockdude.states.fakeloader.FakeLoaderState;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.logic.GState.GImmutableState;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameFrame extends Frame {
	private static GCanvas canvas;
	public static GImmutableState fakeLoadingState = new FakeLoaderState();

	public static void main(String[] args) {
		new GameFrame();
	}

	public GameFrame() {
		init();
		pack();
		setTitle("BlockDude");
		setVisible(true);
		setResizable(false);
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
		canvas.addGState(fakeLoadingState);
		add(canvas);
	}

	public static GCanvas getCanvas() {
		if(canvas != null)
			return canvas;
		return (canvas = new GCanvas(new Dimension(800, 600), 60, 2));
	}
}
