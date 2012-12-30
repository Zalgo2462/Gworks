package org.zp.gworks.gui.canvas;

import org.zp.gworks.gui.canvas.input.GKeyListener;
import org.zp.gworks.gui.canvas.input.GMouseListener;
import org.zp.gworks.gui.canvas.rendering.GPaintStrategy;
import org.zp.gworks.gui.canvas.rendering.GRenderer;
import org.zp.gworks.logic.GLoop;
import org.zp.gworks.logic.GState.GMutableState;
import org.zp.gworks.logic.GState.GState;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;

public final class GCanvas extends Canvas {
	public final int FPS;
	public final int FRAME_DELAY;
	private final int BUFFERS;
	private Thread gameThread;
	private GLoop loop;
	private GMutableState backgroundState;
	private GState gState;
	private BufferStrategy strategy;
	private GRenderer renderer;

	public GCanvas(final Dimension dimension, final int fps, final int buffers) {
		super();
		this.FPS = fps;
		this.FRAME_DELAY = Math.round(1000.0F / (float) FPS);
		this.BUFFERS = buffers;
		setMinimumSize(dimension);
		setPreferredSize(dimension);
		setMaximumSize(dimension);
	}

	public GCanvas(final Dimension dimension, final int fps) {
		this(dimension, fps, 2);
	}

	public void addNotify() {
		super.addNotify();
		createBufferStrategy(BUFFERS);
		createBackgroundState();
		createGLoop();
		createRenderer();
		createGameThread();
		gameThread.start();
	}

	public void createBufferStrategy(final int buffers) {
		super.createBufferStrategy(buffers);
		this.strategy = getBufferStrategy();
	}

	private void createBackgroundState() {
		this.backgroundState = new GMutableState();
	}

	private void createGLoop() {
		if(loop == null)
			this.loop = new GLoop(this);
	}

	private void createRenderer() {
		if(renderer == null)
			this.renderer = new GRenderer(this);
	}

	private void createGameThread() {
		this.gameThread = new Thread(getLoop());
	}

	public GMutableState getBackgroundState() {
		return backgroundState;
	}

	public GLoop getLoop() {
		return loop;
	}

	public Thread getGameThread() {
		return gameThread;
	}

	public void setGState(final GState gState) {
		this.gState = gState;
	}

	public GState getGState() {
		return gState;
	}

	public GRenderer getRenderer() {
		return renderer;
	}

	public synchronized void drawStrategy(final GPaintStrategy paintStrategy) {
		Graphics graphics = strategy.getDrawGraphics();
		paintStrategy.paint(this, graphics);
		graphics.dispose();
	}

	public synchronized void showBuffer() {
		strategy.show();
		Toolkit.getDefaultToolkit().sync();
	}

	public void registerDefaultInputListeners() {
		addKeyListener(new GKeyListener());
		addMouseListener(new GMouseListener());
	}

	public void registerCustomInputListeners(final KeyListener kl, final MouseListener ml) {
		addKeyListener(kl);
		addMouseListener(ml);
	}
}
