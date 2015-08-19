package org.zp.gworks.gui.canvas.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class GMouseListener implements MouseListener {
	private final ConcurrentLinkedQueue<MouseEvent> clickedEvents, pressedEvents, releasedEvents, enteredEvents, exitedEvents;

	public GMouseListener() {
		clickedEvents = new ConcurrentLinkedQueue<MouseEvent>();
		pressedEvents = new ConcurrentLinkedQueue<MouseEvent>();
		releasedEvents = new ConcurrentLinkedQueue<MouseEvent>();
		enteredEvents = new ConcurrentLinkedQueue<MouseEvent>();
		exitedEvents = new ConcurrentLinkedQueue<MouseEvent>();
	}

	public MouseEvent getNextClickedEvent() {
		return clickedEvents.poll();
	}

	public MouseEvent getNextPressedEvent() {
		return pressedEvents.poll();
	}

	public MouseEvent getNextReleasedEvent() {
		return releasedEvents.poll();
	}

	public MouseEvent getNextEnteredEvent() {
		return enteredEvents.poll();
	}

	public MouseEvent getNextExitedEvent() {
		return exitedEvents.poll();
	}


	@Override
	public void mouseClicked(final MouseEvent e) {
		clickedEvents.offer(e);
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		pressedEvents.offer(e);
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		releasedEvents.offer(e);
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
		enteredEvents.offer(e);
	}

	@Override
	public void mouseExited(final MouseEvent e) {
		exitedEvents.offer(e);
	}
}
