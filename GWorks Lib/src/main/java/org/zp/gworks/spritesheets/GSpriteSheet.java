package org.zp.gworks.spritesheets;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

//TODO: convert to JSON
public class GSpriteSheet {
	private final BufferedImage sheet;
	private final String styleSheet;
	private final ConcurrentHashMap<String, Rectangle> sprites;
	private String name;

	public GSpriteSheet(final BufferedImage sheet, final String styleSheet) {
		this.sheet = addAlphaLayer(sheet);
		this.styleSheet = styleSheet;
		this.sprites = new ConcurrentHashMap<String, Rectangle>();
		loadSprites();
	}

	public GSpriteSheet(final InputStream sheet, final InputStream styleSheet) throws IOException {
		this.sheet = addAlphaLayer(ImageIO.read(sheet));
		final Scanner scanner = new Scanner(styleSheet).useDelimiter("\\Z"); //reads to end
		this.styleSheet = scanner.next();
		scanner.close();
		this.sprites = new ConcurrentHashMap<String, Rectangle>();
		loadSprites();
	}

	public GSpriteSheet(final File sheet, final File styleSheet) throws IOException {
		this(new FileInputStream(sheet), new FileInputStream(styleSheet));
	}

	public BufferedImage getSheet() {
		return sheet;
	}

	public String getName() {
		return name;
	}

	public BufferedImage getSprite(final String name) {
		final Rectangle spriteLocation = sprites.get(name);
		return sheet.getSubimage(spriteLocation.x, spriteLocation.y, spriteLocation.width, spriteLocation.height);
	}

	public void putSprite(final String name, final Rectangle rect) {
		sprites.put(name, rect);
	}

	//Returns a copy of the ConcurrentHashMap
	public HashMap<String, Rectangle> getAllSprites() {
		HashMap<String, Rectangle> spriteMap = new HashMap<String, Rectangle>();
		Set<Map.Entry<String, Rectangle>> set1 = sprites.entrySet();
		for(Map.Entry<String, Rectangle> e: set1){
			spriteMap.put(e.getKey(), (Rectangle)e.getValue().clone()); //strings are immutable, so no cloning needed
		}
		return spriteMap;
	}

	private BufferedImage addAlphaLayer(final BufferedImage image) {
		if(image.getAlphaRaster() == null) {
			BufferedImage imageWithAlpha = new BufferedImage(image.getWidth(), image.getHeight(), 2);
			Graphics2D graphics = imageWithAlpha.createGraphics();
			graphics.drawImage(image, 0, 0, null);
			graphics.dispose();
			return imageWithAlpha;
		}
		return image;
	}

	private String[] tokenize(final String input) {
		LinkedList<String> tokens = new LinkedList<String>();
		String token = "";
		char[] charArray = input.toCharArray();
		for (char aCharArray : charArray) {
			if (!Character.isWhitespace(aCharArray)) {
				token += aCharArray;
			}
            else {
                tokens.add(token);
                token = "";
            }
		}
		return tokens.toArray(new String[tokens.size()]);
	}

    //todo: change to json
	private void loadSprites() {
		String[] tokens = tokenize(styleSheet);
		for(String token : tokens) {
			if(token.startsWith("sheet")) {
				parseSheetInfo(styleSheet.substring(styleSheet.indexOf(token),
						styleSheet.indexOf('}', styleSheet.indexOf(token)) + 1));
			} else if(token.startsWith("sprite")) {
				parseSpriteInfo(styleSheet.substring(styleSheet.indexOf(token + " "),
						styleSheet.indexOf('}', styleSheet.indexOf(token + " ")) + 1));
			}
		}
	}

	private void parseSheetInfo(final String sheetInfo) {
		String[] tokens = tokenize(sheetInfo);
		for (int i = 0, tokensLength = tokens.length; i < tokensLength; i++) {
			String token = tokens[i];
			if (token.startsWith("sheet")) {
				this.name = token.substring(token.indexOf('.') + 1);
			}
		}
	}

	private void parseSpriteInfo(final String spriteInfo) {
		String name = "";
		Rectangle rectangle = new Rectangle(-1 ,-1, -1, -1);
		String[] tokens = tokenize(spriteInfo);
		for (int i = 0, tokensLength = tokens.length; i < tokensLength; i++) {
			String token = tokens[i];
			if (token.startsWith("sprite")) {
				name = token.substring(token.indexOf('.') + 1);
			} else if (token.startsWith("background")) {
				token = tokens[i + 2];
				int x = Integer.parseInt(token.replace("-", "").replace("px", ""));
				token = tokens[i + 3];
				int y = Integer.parseInt(token.replace("-","").replace("px", "").replace(";",""));
				rectangle.setLocation(x, y);
			} else if (token.startsWith("width")) {
				token = tokens[i + 1];
				rectangle.width = Integer.parseInt(token.replace("px", "").replace(";", ""));
			} else if (token.startsWith("height")) {
				token = tokens[i + 1];
				rectangle.height = Integer.parseInt(token.replace("px", "").replace(";", ""));
			}
		}
		sprites.put(name, rectangle);
	}

	public static void save(final File imageFile, final File outFile, final String name,
	                        final HashMap<String, Rectangle> sprites) throws IOException {
		String imageName = imageFile.getName();
		if(imageName.contains(File.separator))
			imageName = imageName.substring(imageName.indexOf(File.separator) + 1);
		String toString = "sheet." + name + "\n";
		for(Map.Entry<String, Rectangle> sprite : sprites.entrySet()) {
			toString += "sprite." + sprite.getKey() + " " + "{\n" +
					"\tbackground: url(" + imageName + ") -" + sprite.getValue().x + "px -" +sprite.getValue().y + "px;\n" +
					"\twidth: " + sprite.getValue().width + "px; \n" +
					"\theight: " + sprite.getValue().height + "px; \n" +
				"}\n";
		}
		if(!outFile.exists()) {
			outFile.createNewFile();
		}
		FileWriter fw = new FileWriter(outFile);
		fw.write(toString);
		fw.flush();
		fw.close();
	}
}
