package org.zp.blockdude.states.menus;

import org.zp.blockdude.GameFrame;
import org.zp.blockdude.states.menus.ui.GButton;
import org.zp.gworks.gui.canvas.GCanvas;
import org.zp.gworks.gui.canvas.rendering.GRenderListener;
import org.zp.gworks.logic.GState.GMutableState;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

/**
 * Date: 8/1/2014
 * Time: 7:56 PM
 */
public class GMenuState extends GMutableState implements GRenderListener {
	private Color bgColor;
	private int x, y, width, height;
	private LinkedList<GButton> buttons;
	private GMenuMouseListener mouseListener;

	public GMenuState(Color bgColor, int x, int y, int width, int height) {
		this.bgColor = bgColor;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.buttons = new LinkedList<GButton>();
		this.mouseListener = new GMenuMouseListener();
		GameFrame.getCanvas().addMouseListener(mouseListener);
		addGRenderListener(this);
	}

	public void addGButton(GButton button) {
		buttons.offer(button);
		addGRenderListener(button);
		addGTickListener(button);
	}

	public boolean removeGButton(GButton button) {
		return buttons.remove(button) && removeGRenderListener(button) && removeGTickListener(button);
	}

	@Override
	public void paint(GCanvas canvas, Graphics graphics, long delta) {
		graphics.setColor(bgColor);
		graphics.fillRect(x, y, width, height);
	}

	public void dispose() {
		GameFrame.getCanvas().removeMouseListener(mouseListener);
	}

	private class GMenuMouseListener implements MouseListener {
		private Rectangle bounds;
		private GButton currentlyPressed;

		public GMenuMouseListener() {
			this.bounds = new Rectangle(x, y, width, height);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (bounds.contains(e.getPoint())) {
				for (GButton button : buttons) {
					if (button.getBounds().contains(e.getPoint())) {
						button.buttonClicked(e);
						return;
					}
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (bounds.contains(e.getPoint())) {
				for (GButton button : buttons) {
					if (button.getBounds().contains(e.getPoint())) {
						button.setPressed(true);
						currentlyPressed = button;
						return;
					}
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (bounds.contains(e.getPoint())) {
				for (GButton button : buttons) {
					if (button.getBounds().contains(e.getPoint()) && button.isPressed()) {
						button.setPressed(false);
						currentlyPressed = null;
						return;
					}
				}
			}
			if (currentlyPressed != null) {
				currentlyPressed.setPressed(false);
				currentlyPressed = null;
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}
}
