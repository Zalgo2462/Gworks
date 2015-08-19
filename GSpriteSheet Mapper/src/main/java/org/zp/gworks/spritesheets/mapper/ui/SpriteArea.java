package org.zp.gworks.spritesheets.mapper.ui;

import org.zp.gworks.spritesheets.mapper.GSpriteSheetMapper;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class SpriteArea extends JPanel {
	private final GSpriteSheetMapper frame;
	private final DefaultListModel<Sprite> listModel;
	private final JList<Sprite> list;
	private final JScrollPane listScroller;

	public SpriteArea(GSpriteSheetMapper frame) {
		super();
		this.frame = frame;
		this.listModel = new DefaultListModel<Sprite>();

		this.list = new JList<Sprite>(listModel);
		this.list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		this.list.addKeyListener(new DeletionListener());
		this.list.addListSelectionListener(new UpdateImageListener());

		this.listScroller = new JScrollPane(list);
		this.listScroller.setPreferredSize(new Dimension(GSpriteSheetMapper.APPLICATION_WIDTH, 100));
		this.listScroller.setBorder(new CompoundBorder(new LineBorder(Color.BLACK), new EmptyBorder(2, 5, 2, 5)));

		add(listScroller);
	}

	public void addSprite(final String name, final Rectangle area) {
		listModel.addElement(new Sprite(name, area));
		frame.getImagePanel().revalidate();
		frame.getImagePanel().repaint();
		list.revalidate();
		list.repaint();
	}

	public void removeSprite(final Sprite sprite) {
		listModel.removeElement(sprite);
		frame.getImagePanel().revalidate();
		frame.getImagePanel().repaint();
		list.revalidate();
		list.repaint();
	}

	public Sprite[] getSprites() {
		return Arrays.copyOf(listModel.toArray(), listModel.toArray().length, Sprite[].class);
	}

	public Sprite getSelectedValue() {
		return list.getSelectedValue();
	}

	public void clear() {
		listModel.clear();
	}

	private class UpdateImageListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			frame.getImagePanel().revalidate();
			frame.getImagePanel().repaint();
		}
	}

	private class DeletionListener implements KeyListener {

		@Override
		public void keyTyped(final KeyEvent e) { }

		@Override
		public void keyPressed(final KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_DELETE) {
				Sprite sprite = list.getSelectedValue();
				removeSprite(sprite);
			}
		}

		@Override
		public void keyReleased(final KeyEvent e) { }
	}

	class Sprite {
		private final String name;
		private final Rectangle rect;

		Sprite(final String name, final Rectangle rect) {
			this.name = name;
			this.rect = rect;
		}

		public String getName() {
			return name;
		}

		public Rectangle getRect() {
			return rect;
		}

		public String toString() {
			return name + ": " + rect.toString().substring(rect.toString().indexOf('['));
		}
	}
}
