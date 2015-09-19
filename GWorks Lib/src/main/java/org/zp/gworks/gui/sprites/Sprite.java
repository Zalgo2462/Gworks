package org.zp.gworks.gui.sprites;


public abstract class Sprite {
	protected final Movement movement;
	protected final Renderer renderer;
	protected final Rotation rotation;

	public Sprite() {
		renderer = new Renderer(this);
		movement = new Movement(this);
		rotation = new Rotation(this);
	}

	public Movement getMovement() {
		return movement;
	}

	public Rotation getRotation() {
		return rotation;
	}

	public Renderer getRenderer() {
		return renderer;
	}
}
