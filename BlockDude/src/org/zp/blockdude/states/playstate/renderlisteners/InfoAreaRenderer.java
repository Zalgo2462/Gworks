package org.zp.blockdude.states.playstate.renderlisteners;

import org.zp.blockdude.GameFrame;
import org.zp.blockdude.states.playstate.PlayState;
import org.zp.blockdude.states.playstate.PlayState.UI_CONSTANTS;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;

import java.awt.*;

/**
 * Date: 7/31/2014
 * Time: 6:11 PM
 */
public class InfoAreaRenderer implements GRenderListener {
	private final PlayState playState;

	public InfoAreaRenderer(PlayState playState) {
		this.playState = playState;
	}

	@Override
	public void paint(GCanvas canvas, Graphics graphics, long delta) {
		graphics.setColor(Color.BLACK);
		graphics.drawLine(0, UI_CONSTANTS.PLAY_AREA_TOP,
				GameFrame.DIMENSION.width, UI_CONSTANTS.PLAY_AREA_TOP);
		graphics.setFont(graphics.getFont().deriveFont((float) UI_CONSTANTS.INFO_AREA_HEIGHT));
		graphics.drawString(
				"Level: " + (playState.getCurrentLevel().ordinal() + 1),
				UI_CONSTANTS.LEVEL_BAR_START,
				UI_CONSTANTS.INFO_AREA_TOP + UI_CONSTANTS.INFO_AREA_HEIGHT
		);
	}
}
