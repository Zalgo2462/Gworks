package org.zp.blockdude;


import org.zp.blockdude.states.menus.main.MainMenu;
import org.zp.gworks.gui.canvas.GCanvas;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameFrame extends Frame {
	public static final Dimension DIMENSION = new Dimension(800, 600);
	private static GCanvas canvas;
    private static GameFrame gameFrame;

	public GameFrame() {
		setResizable(false);
		init();
		pack();
		setTitle("BlockDude");
		setVisible(true);
		requestFocus();
		canvas.requestFocus();
	}

	public static void main(String[] args) {
		gameFrame = new GameFrame();
	}

	public static void exit() {
		gameFrame.dispose();
	}

	public void init() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		canvas = new GCanvas(DIMENSION, 240, 2);
		canvas.addState(MainMenu.getMenuState(canvas));
		add(canvas);
	}

	public void dispose() {
		if (EventQueue.isDispatchThread()) {
			canvas.dispose();
			super.dispose();
		} else {
			final Window w = this;
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					canvas.dispose();
					w.dispose();
				}
			});
		}
	}
}
