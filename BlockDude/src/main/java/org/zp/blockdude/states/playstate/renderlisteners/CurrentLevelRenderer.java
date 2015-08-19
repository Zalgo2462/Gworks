package org.zp.blockdude.states.playstate.renderlisteners;

import org.zp.blockdude.states.playstate.PlayState;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;

import java.awt.*;

/**
 * Created by Logan on 8/4/2014.
 */
public class CurrentLevelRenderer implements GRenderListener {

    private PlayState playState;

    public CurrentLevelRenderer(PlayState playState) {
        this.playState = playState;
    }

    @Override
    public void paint(GCanvas canvas, Graphics graphics, long delta) {
        graphics.setFont(graphics.getFont().deriveFont((float) PlayState.UI_CONSTANTS.INFO_AREA_HEIGHT));
        graphics.drawString(
                "Level: " + (playState.getCurrentLevel().ordinal() + 1),
                PlayState.UI_CONSTANTS.LEVEL_BAR_START,
                PlayState.UI_CONSTANTS.INFO_AREA_TOP + PlayState.UI_CONSTANTS.INFO_AREA_HEIGHT
        );
    }
}
