package org.zp.blockdude.states.fakeloader.ticklisteners;

import org.zp.blockdude.Level;
import org.zp.blockdude.states.fakeloader.FakeLoaderState;
import org.zp.blockdude.states.playstate.PlayState;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.logic.GTickListener;

public class TimeCounter implements GTickListener {
	final long MAX_TIME = 1000000000L; //nanoseconds
	long time = 0;
	final FakeLoaderState state;

	public TimeCounter(FakeLoaderState state) {
		this.state = state;
	}

	@Override
	public void tick(GCanvas canvas, long delta) {
		time += delta;
		state.setProgress((double) time / MAX_TIME);
		if (time > MAX_TIME) {
			PlayState playState = new PlayState();
			playState.initLevel(Level.ONE);
			canvas.removeGState(state);
			canvas.addGState(playState);
		}
	}
}
