package org.zp.gworks.spritesheets.mapper.ui;

import org.zp.gworks.spritesheets.GSpriteSheet;
import org.zp.gworks.spritesheets.mapper.GSpriteSheetMapper;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MenuBar extends JMenuBar{
	private final GSpriteSheetMapper frame;
	private File currImgFile;
	private File currCssFile;

	public MenuBar(GSpriteSheetMapper frame) {
		this.frame = frame;
		init();
	}

	private void init() {
		JMenu file = new JMenu("File");
		add(file);

		JMenuItem openIMG = new JMenuItem("Open Image");
		openIMG.addActionListener(openIMGAction);
		file.add(openIMG);

		file.addSeparator();

		JMenuItem openCSS = new JMenuItem("Open CSS");
		openCSS.addActionListener(openCSSAction);
		file.add(openCSS);

		JMenuItem saveCSS = new JMenuItem("Save CSS");
		saveCSS.addActionListener(saveCSSAction);
		file.add(saveCSS);
	}

	private final ActionListener openIMGAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			final JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			fileChooser.setFileFilter(new ImageFilter());
			int returnVal = fileChooser.showOpenDialog(frame);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				openFile(file);
			}
		}

		private void openFile(final File file) {
			try {
				final BufferedImage image = ImageIO.read(file);
				frame.getSpriteArea().clear();
				frame.getImagePanel().loadNewPicture(image, 1.0);
				currImgFile = file;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		class ImageFilter extends FileFilter {
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				}
				String extension = getExtension(f);
				if (extension != null) {
					return extension.equals("tiff") ||
							extension.equals("tif") ||
							extension.equals("gif") ||
							extension.equals("jpeg") ||
							extension.equals("jpg") ||
							extension.equals("png");
				}
				return false;
			}

			public String getDescription() {
				return "Images";
			}

			public String getExtension(File f) {
				String ext = null;
				String s = f.getName();
				int i = s.lastIndexOf('.');

				if (i > 0 &&  i < s.length() - 1) {
					ext = s.substring(i+1).toLowerCase();
				}
				return ext;
			}
		}
	};

	private final ActionListener openCSSAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(currImgFile != null) {
				final JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File("."));
				fileChooser.setFileFilter(new CSSFilter());
				int returnVal = fileChooser.showOpenDialog(frame    );
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					openFile(file);
				}
			}
		}

		private void openFile(final File styleFile) {
			try {
				final GSpriteSheet spriteSheet = new GSpriteSheet(currImgFile, styleFile);
				for(Map.Entry<String, Rectangle> e : spriteSheet.getAllSprites().entrySet()) {
					frame.getSpriteArea().addSprite(e.getKey(), e.getValue());
				}
				frame.getSpriteArea().revalidate();
				frame.getSpriteArea().repaint();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		class CSSFilter extends FileFilter {
			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				}
				String extension = getExtension(f);
				if (extension != null) {
					return extension.equals("css");
				}
				return false;
			}

			@Override
			public String getDescription() {
				return "CSS Files";
			}

			public String getExtension(File f) {
				String ext = null;
				String s = f.getName();
				int i = s.lastIndexOf('.');

				if (i > 0 &&  i < s.length() - 1) {
					ext = s.substring(i+1).toLowerCase();
				}
				return ext;
			}
		}
	};

	private final ActionListener saveCSSAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(currImgFile != null) {
				String name = JOptionPane.showInputDialog(frame, "Sheet Name:");
				if(name != null) {
					name = name.replace(" ", "_");
					final JFileChooser fileChooser = new JFileChooser();
					fileChooser.setCurrentDirectory(new File("."));
					fileChooser.setFileFilter(new CSSFilter());
					int returnVal = fileChooser.showSaveDialog(frame);
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();
						saveFile(file, name);
					}
				}
			}
		}

		private void saveFile(final File styleFile, final String sheetName) {
			try {
				final HashMap<String, Rectangle> sprites = new HashMap<String, Rectangle>();
				for(SpriteArea.Sprite sprite : frame.getSpriteArea().getSprites()) {
					sprites.put(sprite.getName(), sprite.getRect());
				}
				GSpriteSheet.save(currImgFile, styleFile, sheetName, sprites);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		class CSSFilter extends FileFilter {
			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				}
				String extension = getExtension(f);
				if (extension != null) {
					return extension.equals("css");
				}
				return false;
			}

			@Override
			public String getDescription() {
				return "CSS Files";
			}

			public String getExtension(File f) {
				String ext = null;
				String s = f.getName();
				int i = s.lastIndexOf('.');

				if (i > 0 &&  i < s.length() - 1) {
					ext = s.substring(i+1).toLowerCase();
				}
				return ext;
			}
		}
	};
}
