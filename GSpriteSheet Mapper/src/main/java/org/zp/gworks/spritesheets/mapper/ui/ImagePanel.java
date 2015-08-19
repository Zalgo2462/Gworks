package org.zp.gworks.spritesheets.mapper.ui;

import org.zp.gworks.spritesheets.mapper.GSpriteSheetMapper;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.event.*;


public class ImagePanel extends JPanel {
	private final GSpriteSheetMapper frame;
	private ImageViewer imageViewer;
	private final JPanel margin = new JPanel();
	private ControlPanel controlPanel;
	private JScrollPane scrollPane;

	public ImagePanel(final GSpriteSheetMapper frame, final BufferedImage image, final double scale) {
		this.frame = frame;
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		loadNewPicture(image, scale);
	}

	public void loadNewPicture(final BufferedImage image, final double scale) {
		this.imageViewer = new ImageViewer(image, scale);
		this.controlPanel = new ControlPanel();
		margin.removeAll();
		margin.add(imageViewer);
		this.scrollPane = new JScrollPane(margin);

		removeAll();
		add(scrollPane);
		add(controlPanel);
		revalidate();
		repaint();
	}

	class ImageViewer extends JPanel {
		final SelectionCursor selectionCursor;
		final BufferedImage image;
		double scale;

		ImageViewer(final BufferedImage image, final double scale) {
			this.selectionCursor = new SelectionCursor(this);
			this.image = image;
			this.scale = scale;
			addMouseListener(selectionCursor);
			addMouseMotionListener(selectionCursor);
			addKeyListener(new EnterMnemonic());
		}

		protected void paintComponent(final Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
			int imageWidth = image.getWidth();
			int imageHeight = image.getHeight();
			g2.drawImage(image, 0, 0, (int)Math.round(imageWidth * scale), (int)Math.round(imageHeight * scale), null);
			for(SpriteArea.Sprite sprite : frame.getSpriteArea().getSprites()) {
				if(frame.getSpriteArea().getSelectedValue() != null &&
						frame.getSpriteArea().getSelectedValue().equals(sprite)) {
					g2.setXORMode(Color.RED);
				} else {
					g2.setXORMode(Color.CYAN);
				}
				final Point location = imageToScreen(sprite.getRect().getLocation());
				final Dimension size = imageToScreen(sprite.getRect().getSize());
				g2.drawRect(location.x, location.y, size.width, size.height);
			}

			selectionCursor.paintSelectionArea(g2);
		}

		/**
		 * For the scroll pane.
		 */
		public Dimension getPreferredSize() {
			int w = (int)Math.round(scale * image.getWidth());
			int h = (int)Math.round(scale * image.getHeight());
			return new Dimension(w, h);
		}

		public Dimension getMaximumSize() {
			return getPreferredSize();
		}

		public void setScale(final double s) {
			scale = s;
			selectionCursor.clear();
			revalidate();
			repaint();
		}

		public Point imageToScreen(Point image) {
			return new Point((int)Math.round(image.x * scale), (int)Math.round(image.y * scale));
		}

		public Point screenToImage(Point screen) {
			return new Point((int)Math.round(screen.x / scale), (int)Math.round(screen.y / scale));
		}

		public Dimension imageToScreen(Dimension image) {
			return new Dimension((int)Math.round(image.width * scale), (int)Math.round(image.height * scale));
		}

		public Dimension screenToImage(Dimension screen) {
			return new Dimension((int)Math.round(screen.width / scale), (int)Math.round(screen.height / scale));
		}
		
		class SelectionCursor implements MouseListener, MouseMotionListener {
			private Rectangle previousRectDrawn = new Rectangle();
			private Rectangle currentRect = null;
			private Rectangle rectToDraw = null;
			private final JComponent component;

			public SelectionCursor(final JComponent component) {
				this.component = component;
			}

			@Override
			public void mouseClicked(final MouseEvent e) {
				component.requestFocusInWindow();
			}

			@Override
			public void mousePressed(final MouseEvent e) {
				Point snapPoint = imageToScreen(screenToImage(e.getPoint()));
				int x = snapPoint.x;
				int y = snapPoint.y;
				currentRect = new Rectangle(x, y, 0, 0);
				updateDrawableRect();
				component.repaint();
			}

			@Override
			public void mouseReleased(final MouseEvent e) {
				updateSize(e);
			}

			@Override
			public void mouseEntered(final MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			}

			@Override
			public void mouseExited(final MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mouseDragged(final MouseEvent e) {
				updateSize(e);
			}

			@Override
			public void mouseMoved(final MouseEvent e) { }

			private void updateSize(final MouseEvent e) {
				Point snapPoint = imageToScreen(screenToImage(e.getPoint()));
				int x = snapPoint.x;
				int y = snapPoint.y;
				currentRect.setSize(x - currentRect.x, y - currentRect.y);
				updateDrawableRect();
				Rectangle totalRepaint = rectToDraw.union(previousRectDrawn);
				component.repaint(totalRepaint.x, totalRepaint.y, totalRepaint.width, totalRepaint.height);
			}

			private void updateDrawableRect() {
				int x = currentRect.x;
				int y = currentRect.y;
				int width = currentRect.width;
				int height = currentRect.height;

				//Make the width and height positive, if necessary.
				if (width < 0) {
					width = 0 - width;
					x = x - width + 1;
					if (x < 0) {
						width += x;
						x = 0;
					}
				}
				if (height < 0) {
					height = 0 - height;
					y = y - height + 1;
					if (y < 0) {
						height += y;
						y = 0;
					}
				}

				//The rectangle shouldn't extend past the drawing area.
				if ((x + width) > component.getWidth()) {
					width = component.getWidth() - x;
				}
				if ((y + height) > component.getWidth()) {
					height = component.getWidth() - y;
				}

				//Update rectToDraw after saving old value.
				if (rectToDraw != null) {
					previousRectDrawn.setBounds(
							rectToDraw.x, rectToDraw.y,
							rectToDraw.width, rectToDraw.height);
					rectToDraw.setBounds(x, y, width, height);
				} else {
					rectToDraw = new Rectangle(x, y, width, height);
				}
				controlPanel.updateSelection(new Rectangle(
						screenToImage(new Point(rectToDraw.x, rectToDraw.y)),
						screenToImage(new Dimension(rectToDraw.width, rectToDraw.height))));
			}

			public void paintSelectionArea(final Graphics g) {
				//If currentRect exists, paint a box on top.
				if (currentRect != null) {
					//Draw a rectangle on top of the image.
					g.setXORMode(Color.white); //Color of line varies
					//depending on image colors
					g.drawRect(rectToDraw.x, rectToDraw.y,
							rectToDraw.width - 1, rectToDraw.height - 1);
				}
			}

			public void clear() {
				currentRect = new Rectangle(0,0,0,0);
				rectToDraw = null;
				updateDrawableRect();
			}
		}

		class EnterMnemonic implements KeyListener {

			@Override
			public void keyTyped(KeyEvent e) { }

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					controlPanel.addSprite.doClick();
			}

			@Override
			public void keyReleased(KeyEvent e) { }
		}
	}

	class ControlPanel extends JPanel	{
		private final SpinnerNumberModel SPINNER_MODEL = new SpinnerNumberModel(1.0, 0.1, 100.0, 1);
		private final JSpinner spinner;
		private final Rectangle currRect;
		private final JLabel currSelection;
		private final JButton addSprite;

		public ControlPanel()	{
			this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
			this.setMaximumSize(new Dimension(GSpriteSheetMapper.APPLICATION_WIDTH, 30));
			this.spinner = createSpinner();
			this.currRect = new Rectangle(0,0,0,0);
			this.currSelection = new JLabel(" ");
			this.addSprite = createAddSpriteButton();

			add(new JLabel("Scale"));
			add(spinner);
			add(Box.createHorizontalGlue());
			add(currSelection);
			add(addSprite);
		}

		private JSpinner createSpinner() {
			final JSpinner spinner = new JSpinner(SPINNER_MODEL);
			spinner.setPreferredSize(new Dimension(45, spinner.getPreferredSize().height));
			spinner.setMaximumSize(spinner.getPreferredSize());
			spinner.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e)
				{
					float scale = ((Double)spinner.getValue()).floatValue();
					imageViewer.setScale(scale);
				}
			});
			return spinner;
		}

		public void updateSelection(final Rectangle rect) {
			currRect.setBounds(rect);
			currSelection.setText(currRect.toString().substring(currRect.toString().indexOf('[')));
		}

		private JButton createAddSpriteButton() {
			final JButton spriteButton = new JButton("Add Sprite");
			spriteButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(currRect != null && currRect.width != 0 && currRect.height != 0) {
						String name = JOptionPane.showInputDialog(frame,
								"Name for " + currRect.toString().substring(currRect.toString().indexOf('[')));
						if(name != null) {
							frame.getSpriteArea().addSprite(name.replace(" ", "_"), (Rectangle)currRect.clone());
							imageViewer.selectionCursor.clear();
						}
					}
				}
			});
			return spriteButton;
		}
	}
}

