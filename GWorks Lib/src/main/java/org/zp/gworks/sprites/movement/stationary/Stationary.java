package org.zp.gworks.sprites.movement.stationary;

import org.zp.gworks.sprites.Sprite;
import org.zp.gworks.sprites.movement.Movement;

/**
 * Date: 9/19/2015
 * Time: 6:31 PM
 */
public class Stationary extends Movement {
	protected Stationary(Sprite sprite) {
		super(sprite);
	}

	@Override
	public double getXVelocity() {
		return 0;
	}

	@Override
	public double getYVelocity() {
		return 0;
	}
}
