package org.zp.platformers.morning.levels;

import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;
import org.zp.gworks.logic.GTickListener;
import org.zp.platformers.morning.states.PlayState;

import java.awt.*;

/**
 * Date: 8/29/2015
 * Time: 6:36 PM
 */
public class Camera implements GRenderListener, GTickListener {
	private PlayState playState;
	private int x;
	private int y;

	public Camera(PlayState playState) {
		this.playState = playState;
	}

	@Override
	public void tick(GCanvas canvas, long delta) {
		//follow player
	}


	@Override
	public void paint(GCanvas canvas, Graphics graphics, long delta) {
		//paint world
	}
}
