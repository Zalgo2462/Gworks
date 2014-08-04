package org.zp.gworks.logic;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.logic.GState.GState;

import java.util.LinkedList;

public final class GLoop implements Runnable {
	private final GCanvas canvas;

	private long currentTime;
	private LinkedList<Long> frameTimes;

	private volatile boolean isRunning;

	public GLoop(final GCanvas canvas) {
		this.canvas = canvas;
		frameTimes = new LinkedList<Long>();
		isRunning = true;
	}

	@Override
	public void run() {
		currentTime = System.nanoTime();
		while (isRunning) {
			long newTime = System.nanoTime();
			long delta = newTime - currentTime;
			currentTime = newTime;
			for (GState state : canvas.getStates()) {
				for (GTickListener listener : state.getTickListeners()) {
                    if(canvas.getStates().contains(state)) {
                        listener.tick(canvas, delta);
                    }
				}
			}
			canvas.getRenderer().tick(canvas, delta);
			syncFramerate();
		}
	}

	public void setIsRunning(final boolean running) {
		isRunning = running;
	}

	private void syncFramerate() {
		try {
			Thread.sleep((Math.max(0, currentTime + canvas.FRAME_DELAY - System.nanoTime()) / 1000000));
			frameTimes.offer(System.nanoTime());
			if (frameTimes.size() > canvas.FPS) {
				frameTimes.poll();
			}
		} catch (InterruptedException e) {
			setIsRunning(false);
		}
	}

	public double getActualFramerate() {
		if (frameTimes.size() > 1) {
			double runningAvg = frameTimes.get(1) - frameTimes.get(0);
			for (int i = 1; i < frameTimes.size(); i++) {
				double nextInterval = frameTimes.get(i) - frameTimes.get(i - 1);
				runningAvg += nextInterval;
				runningAvg /= 2;
			}
			return 1000000000.0D / runningAvg;
		}
		return -1;
	}
}
