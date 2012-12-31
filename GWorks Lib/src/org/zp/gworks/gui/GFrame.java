package org.zp.gworks.gui;

import org.zp.gworks.gui.canvas.GCanvas;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public final class GFrame extends Frame {

	private final static WindowListener DEFAULT_WINDOW_LISTENER = new WindowListener() {
		@Override
		public void windowOpened(final WindowEvent e) {
		}

		@Override
		public void windowClosing(final WindowEvent e) {
			e.getWindow().dispose();
		}

		@Override
		public void windowClosed(final WindowEvent e) {
		}

		@Override
		public void windowIconified(final WindowEvent e) {
		}

		@Override
		public void windowDeiconified(final WindowEvent e) {
		}

		@Override
		public void windowActivated(final WindowEvent e) {
		}

		@Override
		public void windowDeactivated(final WindowEvent e) {
		}
	};
	private final WindowListener windowListener;
	private final GCanvas canvas;

	public GFrame(final String title, final GCanvas canvas, final WindowListener windowListener) {
		super(title);
		this.canvas = canvas;
		this.windowListener = windowListener;
		init();
		pack();

	}

	public GFrame(final String title, final GCanvas canvas) {
		this(title, canvas, DEFAULT_WINDOW_LISTENER);
	}

	private void init() {
		add(canvas);
		addWindowListener(windowListener);
	}

	public GCanvas getCanvas() {
		return canvas;
	}

	public void dispose() {
		super.dispose();
		canvas.stopCanvas();
	}
}
