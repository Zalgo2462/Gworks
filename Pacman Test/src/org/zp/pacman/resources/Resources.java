package org.zp.pacman.resources;

import org.zp.gworks.spritesheets.GSpriteSheet;

import java.io.IOException;

public class Resources {
	public static GSpriteSheet PACMAN_SPRITES;

	static {
		try {
			PACMAN_SPRITES = new GSpriteSheet(
					Resources.class.getResourceAsStream("/org/zp/gtest/resources/pacman.png"),
					Resources.class.getResourceAsStream(("/org/zp/gtest/resources/pacman.css")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
