package org.zp.gworks.spritesheets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

//TODO: finish converting to JSON
public class GSpriteSheet {
	//Access Jackson library
	private static final ObjectMapper mapper = new ObjectMapper();
	private final BufferedImage sheet;
	private final ConcurrentHashMap<String, Rectangle> sprites;
	private Color background;
	private String name;

	public GSpriteSheet(final BufferedImage sheet, final String styleJSON) {
		this.sheet = addAlphaLayer(sheet);
		this.sprites = new ConcurrentHashMap<String, Rectangle>();
		parseJSON(styleJSON);
		if (background != null) {
			adjustBackground(sheet, background);
		}
	}

	public GSpriteSheet(final InputStream sheet, final InputStream styleJSON) throws IOException {

		this.sheet = addAlphaLayer(ImageIO.read(sheet));
		final Scanner scanner = new Scanner(styleJSON).useDelimiter("\\Z"); //reads to end
		final String styleData = scanner.next();
		scanner.close();
		this.sprites = new ConcurrentHashMap<String, Rectangle>();
		parseJSON(styleData);
		if (background != null) {
			adjustBackground(this.sheet, background);
		}
	}

	public GSpriteSheet(final File sheet, final File styleJSON) throws IOException {
		this(new FileInputStream(sheet), new FileInputStream(styleJSON));
	}

	//Todo: fix the naming of json files
	public static void save(final File imageFile, final File outFile, Color background,
	                        final HashMap<String, Rectangle> sprites) throws IOException {
		String imageName = imageFile.getName();
		if (imageName.contains(File.separator))
			imageName = imageName.substring(imageName.indexOf(File.separator) + 1);
		String name = "sheet." + imageName;
		ObjectNode root = mapper.createObjectNode();
		root.put("name", name);
		if (background != null) {
			ArrayNode arrayNode = mapper.createArrayNode();
			arrayNode.add(background.getRed());
			arrayNode.add(background.getGreen());
			arrayNode.add(background.getBlue());
			root.set("background", arrayNode);
		}

		for (Map.Entry<String, Rectangle> sprite : sprites.entrySet()) {
			ObjectNode spriteNode = mapper.createObjectNode();
			spriteNode.put("x", sprite.getValue().x);
			spriteNode.put("y", sprite.getValue().y);
			spriteNode.put("width", sprite.getValue().width);
			spriteNode.put("height", sprite.getValue().height);
			root.set(sprite.getKey(), spriteNode);
		}

		String toString = root.toString();
		if (!outFile.exists()) {
			outFile.createNewFile();
		}
		FileWriter fw = new FileWriter(outFile);
		fw.write(toString);
		fw.flush();
		fw.close();
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
		for (Map.Entry<String, Rectangle> e : set1) {
			spriteMap.put(e.getKey(), (Rectangle) e.getValue().clone()); //strings are immutable, so no cloning needed
		}
		return spriteMap;
	}

	private BufferedImage addAlphaLayer(final BufferedImage image) {
		if (image.getAlphaRaster() == null) {
			BufferedImage imageWithAlpha = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D graphics = imageWithAlpha.createGraphics();
			graphics.drawImage(image, 0, 0, null);
			graphics.dispose();
			return imageWithAlpha;
		}
		return image;
	}

	private void parseJSON(String styleJSON) {
		try {
			final JsonNode rootNode = mapper.readTree(styleJSON);
			Iterator<Map.Entry<String, JsonNode>> nodeIterator = rootNode.fields();

			while (nodeIterator.hasNext()) {
				final Map.Entry<String, JsonNode> entry = (Map.Entry<String, JsonNode>) nodeIterator.next();
				if (entry.getKey().equals("background")) {
					int[] rgb = new int[3];
					int i = 0;
					for (JsonNode colorValue : entry.getValue()) {
						rgb[i] = colorValue.asInt();
						i++;
						//malformed json don't crash tho
						//TODO: add error
						if (i >= 3) {
							break;
						}
					}

					background = new Color(rgb[0], rgb[1], rgb[2]);
				} else if (entry.getKey().equals("name")) {
					this.name = entry.getValue().asText();
				} else {
					//Todo: add exception to malformed json
					int x = entry.getValue().path("x").asInt();
					int y = entry.getValue().path("y").asInt();
					int width = entry.getValue().path("width").asInt();
					int height = entry.getValue().path("height").asInt();
					sprites.put(entry.getKey(), new Rectangle(x, y, width, height));
				}
			}

			if (name == null) {
				//Todo: throw exception
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private BufferedImage adjustBackground(final BufferedImage image, final Color color) {
		//Todo: test this
		final LookupTable colorMapper = new LookupTable(0, 4) {
			@Override
			public int[] lookupPixel(int[] src, int[] dest) {
				if (dest == null) {
					dest = new int[src.length];
				}
				if (color.getRed() == src[0] && color.getGreen() == src[1] &&
						color.getBlue() == src[2]) {
					dest[0] = 0;
					dest[1] = 0;
					dest[2] = 0;
					dest[3] = 0;
				} else {
					System.arraycopy(src, 0, dest, 0, src.length);
				}
				return dest;
			}
		};
		final BufferedImageOp lookup = new LookupOp(colorMapper, null);
		return lookup.filter(image, null);
	}

}
