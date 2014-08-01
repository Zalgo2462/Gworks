package org.zp.blockdude.states.playstate.renderlisteners;

import org.zp.blockdude.states.playstate.PlayState;
import org.zp.blockdude.states.playstate.PlayState.UI_CONSTANTS;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;

import java.awt.*;

/**
 * Date: 8/1/2014
 * Time: 3:11 PM
 */
public class ScoreRenderer implements GRenderListener {
	private final int SCORE_SPEED = 25;
	private int displayedScore;
	private PlayState playState;

	public ScoreRenderer(PlayState playState) {
		this.playState = playState;
	}

	@Override
	public void paint(GCanvas canvas, Graphics graphics, long delta) {
		double dScore = SCORE_SPEED * delta / 1000000000D;
		if (playState.getScore() - displayedScore < 0) {
			displayedScore -= Math.ceil(dScore);
			if (displayedScore < playState.getScore()) {
				displayedScore = playState.getScore();
			}
		} else if (playState.getScore() - displayedScore > 0) {
			displayedScore += Math.ceil(dScore);
			if (displayedScore > playState.getScore()) {
				displayedScore = playState.getScore();
			}
		}
		graphics.setColor(Color.BLACK);
		graphics.setFont(graphics.getFont().deriveFont((float) UI_CONSTANTS.INFO_AREA_HEIGHT));
		graphics.drawString(
				Integer.toString(displayedScore),
				UI_CONSTANTS.INFO_AREA_LEFT,
				UI_CONSTANTS.INFO_AREA_TOP + UI_CONSTANTS.INFO_AREA_HEIGHT
		);
	}
}
