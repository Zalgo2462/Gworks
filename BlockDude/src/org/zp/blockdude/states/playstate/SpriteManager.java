package org.zp.blockdude.states.playstate;

import org.zp.blockdude.sprites.Sprite;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderStrategy;

import java.awt.*;
import java.util.ArrayList;

public class SpriteManager implements GRenderStrategy{

	private ArrayList<Sprite> sprites = new ArrayList<Sprite>();

	public void registerSprite(Sprite sprite) {
		sprites.add(sprite);
	}

	public void unregisterSprite(Sprite sprite) {
		sprites.remove(sprite);
	}

	public boolean checkForCollision(Sprite sprite) {
		for(Sprite s : sprites) {
			if(s.shouldRender()) {
				if(sprite.getBounds().intersects(s.getBounds())) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void paint(GCanvas canvas, Graphics graphics) {
		for(Sprite s : sprites) {
			s.getSpriteRenderer().paint(canvas, graphics);
		}
	}
}
