package org.zp.blockdude.states.playstate.renderlisteners;

import org.zp.blockdude.GameFrame;
import org.zp.blockdude.states.playstate.PlayState.UI_CONSTANTS;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;

import java.awt.*;

/**
 * Date: 7/31/2014
 * Time: 6:11 PM
 */
public class InfoAreaRenderer implements GRenderListener {
	@Override
	public void paint(GCanvas canvas, Graphics graphics, long delta) {
		graphics.setColor(Color.BLACK);
		graphics.drawLine(0, UI_CONSTANTS.PLAY_AREA_TOP,
				GameFrame.DIMENSION.width, UI_CONSTANTS.PLAY_AREA_TOP);
	}
}
