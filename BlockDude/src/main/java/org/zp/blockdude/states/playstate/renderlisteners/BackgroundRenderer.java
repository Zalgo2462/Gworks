package org.zp.blockdude.states.playstate.renderlisteners;

import org.zp.blockdude.ColorScheme;
import org.zp.blockdude.GameFrame;
import org.zp.blockdude.states.playstate.PlayState;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;

import java.awt.*;

/**
 * Date: 8/2/2014
 * Time: 5:03 PM
 */
public class BackgroundRenderer implements GRenderListener {

	@Override
	public void paint(GCanvas canvas, Graphics graphics, long delta) {
		graphics.setColor(ColorScheme.MENU_BACKGROUND.getColor());
		graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        graphics.setColor(ColorScheme.DARKER_GREEN.getColor());
        graphics.drawLine(0, PlayState.UI_CONSTANTS.PLAY_AREA_TOP,
                GameFrame.DIMENSION.width, PlayState.UI_CONSTANTS.PLAY_AREA_TOP);
	}
}
