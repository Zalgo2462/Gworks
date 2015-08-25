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

				if (i > 0 && i < s.length() - 1) {
					ext = s.substring(i + 1).toLowerCase();
				}
				return ext;
			}
		}
	};
	private final ActionListener openJSONAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (currImgFile != null) {
				final JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File("."));
				fileChooser.setFileFilter(new JSONFilter());
				int returnVal = fileChooser.showOpenDialog(frame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					openFile(file);
				}
			}
		}

		private void openFile(final File styleFile) {
			try {
				final GSpriteSheet spriteSheet = new GSpriteSheet(currImgFile, styleFile);
				for (Map.Entry<String, Rectangle> e : spriteSheet.getAllSprites().entrySet()) {
					frame.getSpriteArea().addSprite(e.getKey(), e.getValue());
				}
				frame.getSpriteArea().revalidate();
				frame.getSpriteArea().repaint();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		class JSONFilter extends FileFilter {
			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				}
				String extension = getExtension(f);
				if (extension != null) {
					return extension.toLowerCase().equals("json");
				}
				return false;
			}

			@Override
			public String getDescription() {
				return "Json Files";
			}

			public String getExtension(File f) {
				String ext = null;
				String s = f.getName();
				int i = s.lastIndexOf('.');

				if (i > 0 && i < s.length() - 1) {
					ext = s.substring(i + 1).toLowerCase();
				}
				return ext;
			}
		}
	};
	private final ActionListener saveJSONAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (currImgFile != null) {
				final JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File("."));
				fileChooser.setFileFilter(new JSONFilter());
				int returnVal = fileChooser.showSaveDialog(frame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					saveFile(file);
				}
			}
		}

		private void saveFile(final File styleFile) {
			try {
				final HashMap<String, Rectangle> sprites = new HashMap<String, Rectangle>();
				for (SpriteArea.Sprite sprite : frame.getSpriteArea().getSprites()) {
					sprites.put(sprite.getName(), sprite.getRect());
				}
				//Todo: implement background color selecting
				GSpriteSheet.save(currImgFile, styleFile, null, sprites);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		class JSONFilter extends FileFilter {
			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				}
				String extension = getExtension(f);
				if (extension != null) {
					return extension.toLowerCase().equals("json");
				}
				return false;
			}

			@Override
			public String getDescription() {
				return "JSON Files";
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

		JMenuItem openJSON = new JMenuItem("Open JSON");
		openJSON.addActionListener(openJSONAction);
		file.add(openJSON);

		JMenuItem saveJSON = new JMenuItem("Save JSON");
		saveJSON.addActionListener(saveJSONAction);
		file.add(saveJSON);
	}
}
