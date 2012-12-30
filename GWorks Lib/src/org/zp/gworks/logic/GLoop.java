package org.zp.gworks.logic;

import org.zp.gworks.gui.canvas.GCanvas;

import java.util.LinkedList;

public final class GLoop implements Runnable {
	private final GCanvas canvas;

	private long cycleTime;
	private LinkedList<Long> frameTimes;

	private volatile boolean isRunning;

	public GLoop(final GCanvas canvas) {
		this.canvas = canvas;
		frameTimes = new LinkedList<Long>();
		isRunning = true;
	}

	@Override
	public void run() {
		cycleTime = System.currentTimeMillis();
		while (isRunning) {
			syncFramerate();
			for(GTickListener listener : canvas.getBackgroundState().getTickListeners()) {
				listener.tick(this);
			}
			if(canvas.getGState() != null) {
				for(GTickListener listener : canvas.getGState().getTickListeners()) {
					listener.tick(this);
				}
			}
			canvas.getRenderer().tick(this);
		}
	}

	public void runGTickListenerAsThreaded(final GTickListener tickListener) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				tickListener.tick(GLoop.this);
			}
		}).run();
	}

	public void setIsRunning(final boolean running) {
		isRunning = running;
	}

	private void syncFramerate() {
		cycleTime += canvas.FRAME_DELAY;
		long difference = cycleTime - System.currentTimeMillis();
		try {
			Thread.sleep(Math.max(0, difference));
			frameTimes.offer(System.currentTimeMillis());
			if(frameTimes.size() > 20) {
				frameTimes.poll();
			}
		} catch (InterruptedException e) {
			setIsRunning(false);
		}
	}

	public double getActualFramerate() {
		if(frameTimes.size() > 1) {
			double runningAvg = frameTimes.get(1) - frameTimes.get(0) ;
			for (int i = 1; i < frameTimes.size(); i++) {
				double nextInterval = frameTimes.get(i) - frameTimes.get(i - 1);
				runningAvg += nextInterval;
				runningAvg /= 2;
			}
			return Math.round(1000.0D / runningAvg);
		}
		return -1;
	}
}