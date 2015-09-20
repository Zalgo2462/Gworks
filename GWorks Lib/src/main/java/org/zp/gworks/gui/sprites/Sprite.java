package org.zp.gworks.gui.sprites;


import org.zp.gworks.gui.sprites.movement.Movement;

import java.lang.reflect.InvocationTargetException;

public abstract class Sprite<MovementType extends Movement> {
	protected final Renderer renderer;
	protected final Rotation rotation;
	protected MovementType movement;

	public Sprite(Class<MovementType> clazz) {
		try {
			movement = clazz.getConstructor(Sprite.class).newInstance(this);
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			//If using gworks classes this should never happen
			e.printStackTrace();
		}
		renderer = new Renderer(this);
		rotation = new Rotation(this);
	}

	public MovementType getMovement() {
		return movement;
	}

	public Rotation getRotation() {
		return rotation;
	}

	public Renderer getRenderer() {
		return renderer;
	}
}
