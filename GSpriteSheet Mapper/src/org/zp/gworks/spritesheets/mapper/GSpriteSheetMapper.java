package org.zp.gworks.spritesheets.mapper;

import org.zp.gworks.spritesheets.mapper.ui.*;
import org.zp.gworks.spritesheets.mapper.ui.MenuBar;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GSpriteSheetMapper extends JFrame {
	public static final int APPLICATION_WIDTH = 800;
	public static final int APPLICATION_HEIGHT = 600;

	private MenuBar jMenuBar;
	private ImagePanel imagePanel;
	private SpriteArea spriteArea;

	public static void main(String[] args) {
		GSpriteSheetMapper instance = new GSpriteSheetMapper();
		instance.setVisible(true);
	}

	private GSpriteSheetMapper() {
		init();
		pack();
	}

	private void init() {
		JPanel contentPanel = new JPanel(new BorderLayout());
		this.jMenuBar = new MenuBar(this);
		this.imagePanel = new ImagePanel(this, new BufferedImage(1,1, BufferedImage.TYPE_INT_ARGB), 1.0);
		this.spriteArea = new SpriteArea(this);

		this.setTitle("GSpriteSheet Mapper");
		this.setPreferredSize(new Dimension(APPLICATION_WIDTH, APPLICATION_HEIGHT));
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setContentPane(contentPanel);

		contentPanel.add(jMenuBar, BorderLayout.NORTH);
		contentPanel.add(imagePanel, BorderLayout.CENTER);
		contentPanel.add(spriteArea, BorderLayout.SOUTH);
	}

	public MenuBar getJMenuBar() {
		return jMenuBar;
	}

	public ImagePanel getImagePanel() {
		return imagePanel;
	}

	public SpriteArea getSpriteArea() {
		return spriteArea;
	}
}
