package org.zp.blockdude;


import org.zp.blockdude.states.menus.main.MainMenu;
import org.zp.gworks.gui.canvas.GCanvas;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameFrame extends Frame {
	public static final Dimension DIMENSION = new Dimension(800, 600);
	private static GCanvas canvas;
	private MainMenu mainMenu;

	public GameFrame() {
		setResizable(true);
		init();
		pack();
		setTitle("BlockDude");
		setVisible(true);
		requestFocus();
		canvas.requestFocus();
	}

	public static void main(String[] args) {
		new GameFrame();
	}

	public void init() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				canvas.dispose();
				dispose();
				System.exit(0);
			}
		});
		canvas = new GCanvas(DIMENSION, 60, 2);
		canvas.addGState(MainMenu.getMenuState(canvas));
		add(canvas);
	}
}
