package org.zp.gworks.gui.canvas;

import org.zp.gworks.gui.canvas.input.GKeyListener;
import org.zp.gworks.gui.canvas.input.GMouseListener;
import org.zp.gworks.gui.canvas.rendering.GRenderer;
import org.zp.gworks.logic.GLoop;
import org.zp.gworks.logic.GState.GState;
import sun.java2d.pipe.hw.ExtendedBufferCapabilities;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public final class GCanvas extends Canvas {
	public final Dimension DIMENSION;
	public final int FPS;
	public final int FRAME_DELAY;
	private final int BUFFERS;
	private CopyOnWriteArrayList<GState> gStates;
	private Thread gameThread;
	private Thread timerAccuracyThread;
	private GLoop loop;
	private GRenderer renderer;
	private GKeyListener keyListener;
	private GMouseListener mouseListener;

	public GCanvas(final Dimension dimension, final int fps, final int buffers) {
		super();
		this.DIMENSION = dimension;
		this.FPS = fps;
		this.FRAME_DELAY = Math.round(1000000000.0F / (float) FPS);
		this.BUFFERS = buffers;
		this.gStates = new CopyOnWriteArrayList<GState>();
		setSize(dimension);
		setMinimumSize(dimension);
		setPreferredSize(dimension);
		setMaximumSize(dimension);
		createTimerAccuracyThread();
		createLoop();
		createRenderer();
		createGameThread();
		registerEventListeners();
	}

	public GCanvas(final Dimension dimension, final int fps) {
		this(dimension, fps, 2);
	}

	public void addNotify() {
		super.addNotify();
		createBufferStrategy(BUFFERS);
		if (System.getProperty("os.name").startsWith("win"))
			timerAccuracyThread.start();
		gameThread.start();
	}

	public void createBufferStrategy(final int buffers) {
		final GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().
				getDefaultScreenDevice().getDefaultConfiguration();
		final ExtendedBufferCapabilities ebc = new ExtendedBufferCapabilities(
				gc.getBufferCapabilities(),
				ExtendedBufferCapabilities.VSyncType.VSYNC_ON);
		try {
			super.createBufferStrategy(buffers, ebc);
		} catch (AWTException e) {
			System.err.println(e.toString());
			super.createBufferStrategy(buffers);
		}
	}

	private void createLoop() {
		if (loop == null)
			this.loop = new GLoop(this);
	}

	private void createRenderer() {
		if (renderer == null)
			this.renderer = new GRenderer(this);
	}

	private void createTimerAccuracyThread() {
		timerAccuracyThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(Long.MAX_VALUE);
				} catch (InterruptedException e) {
					getLoop().setIsRunning(false);
				}
			}
		});
		timerAccuracyThread.setName("GWorks Timer");
		timerAccuracyThread.setDaemon(true);
	}

	private void createGameThread() {
		this.gameThread = new Thread(getLoop());
		this.gameThread.setName("GWorks Game Thread");
	}

	private void registerEventListeners() {
		this.keyListener = new GKeyListener();
		this.mouseListener = new GMouseListener();
		addKeyListener(keyListener);
		addMouseListener(mouseListener);
	}

	public GLoop getLoop() {
		return loop;
	}

	public Thread getGameThread() {
		return gameThread;
	}

	public boolean addState(final GState state) {
		state.onAddState();
		return gStates.add(state);
	}

	public boolean removeState(final GState state) {
		state.onRemoveState();
		return gStates.remove(state);
	}

	public CopyOnWriteArrayList<GState> getStates() {
		return gStates;
	}

	public GRenderer getRenderer() {
		return renderer;
	}

	public GKeyListener getGKeyListener() {
		return keyListener;
	}

	public GMouseListener getGMouseListener() {
		return mouseListener;
	}

	public void dispose() {
		for (GState gState : gStates) {
			removeState(gState);
		}
		loop.setIsRunning(false);
		gameThread.interrupt();
		timerAccuracyThread.interrupt();
	}
}
