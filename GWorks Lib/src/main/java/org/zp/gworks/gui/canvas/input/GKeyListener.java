package org.zp.gworks.gui.canvas.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class GKeyListener implements KeyListener {
	private final ConcurrentLinkedQueue<KeyEvent> typedEvents, pressedEvents, releasedEvents;
	private final ConcurrentLinkedQueue<Integer> pressedKeyCodes;

	public GKeyListener() {
		typedEvents = new ConcurrentLinkedQueue<KeyEvent>();
		pressedEvents = new ConcurrentLinkedQueue<KeyEvent>();
		releasedEvents = new ConcurrentLinkedQueue<KeyEvent>();
		pressedKeyCodes = new ConcurrentLinkedQueue<Integer>();
	}

	public KeyEvent getNextTypedEvent() {
		return typedEvents.poll();
	}

	public KeyEvent getNextPressedEvent() {
		return pressedEvents.poll();
	}

	public KeyEvent getNextReleasedEvent() {
		return releasedEvents.poll();
	}

	public Collection<Integer> getPressedKeyCodes() {
		return pressedKeyCodes;
	}

	@Override
	public void keyTyped(final KeyEvent e) {
		typedEvents.offer(e);
	}

	@Override
	public void keyPressed(final KeyEvent e) {
		pressedEvents.offer(e);
		if (!pressedKeyCodes.contains(e.getKeyCode()))
			pressedKeyCodes.offer(e.getKeyCode());
	}

	@Override
	public void keyReleased(final KeyEvent e) {
		releasedEvents.offer(e);
		if (pressedKeyCodes.contains(e.getKeyCode()))
			pressedKeyCodes.remove(e.getKeyCode());
	}
}
