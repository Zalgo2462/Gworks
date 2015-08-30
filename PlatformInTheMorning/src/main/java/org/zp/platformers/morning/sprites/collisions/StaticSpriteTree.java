package org.zp.platformers.morning.sprites.collisions;

import org.zp.gworks.gui.sprites.Sprite;

import java.awt.*;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Date: 8/29/2015
 * Time: 6:29 PM
 */
public class StaticSpriteTree {

	private SpriteTreeNode root;

	public StaticSpriteTree() {
		this.root = new ListNode(0);
	}

	public void addSprite(Sprite s) {
		SpriteTreeNode parent = null;
		SpriteTreeNode child = root;
		boolean left = false;
		while (child instanceof SplitNode) {
			left = ((SplitNode) child).lessThanSplit(s);
			parent = child;
			if (left)
				child = ((SplitNode) child).getLeft();
			else
				child = ((SplitNode) child).getRight();
		}

		ListNode listChild = (ListNode) child;
		listChild.addSprite(s);

		if (listChild.shouldSplit()) {
			if (listChild.equals(root)) {
				root = listChild.split();
			} else if (parent != null) { //should always be true in this case
				SplitNode newSplit = listChild.split();
				if (left) {
					((SplitNode) parent).setLeft(newSplit);
				} else {
					((SplitNode) parent).setRight(newSplit);
				}
			}
		}
	}

	public void removeSprite(Sprite s) {
		SpriteTreeNode traverse = root;
		while (traverse instanceof SplitNode) {
			boolean left = ((SplitNode) traverse).lessThanSplit(s);
			if (left)
				traverse = ((SplitNode) traverse).getLeft();
			else
				traverse = ((SplitNode) traverse).getRight();
		}
		ListNode listChild = (ListNode) traverse;
		listChild.removeSprite(s);
	}

	public Sprite getCollided(Sprite s) {
		SpriteTreeNode traverse = root;
		while (traverse instanceof SplitNode) {
			boolean left = ((SplitNode) traverse).lessThanSplit(s);
			if (left)
				traverse = ((SplitNode) traverse).getLeft();
			else
				traverse = ((SplitNode) traverse).getRight();
		}
		ListNode listChild = (ListNode) traverse;
		return Collider.checkForCollision(s, listChild.sprites); //wrongful access here but meh
	}

	public Double getCollisionAngle(Sprite s1, Sprite s2) {
		return Collider.getAngleIfCollision(s1, s2);
	}

	private abstract class SpriteTreeNode {
		int depth;

		protected SpriteTreeNode(int depth) {
			this.depth = depth;
		}
	}

	private class SplitNode extends SpriteTreeNode {
		private SpriteTreeNode left;
		private SpriteTreeNode right;
		private int split;

		private SplitNode(int depth, int splitValue) {
			super(depth);
			this.split = splitValue;
			left = new ListNode(depth + 1);
			right = new ListNode(depth + 1);
		}

		private boolean lessThanSplit(Sprite sprite) {
			if (depth % 2 == 0) {
				return sprite.getRotation().getRotatedCollisionArea().getBounds().x < split;
			} else {
				return sprite.getRotation().getRotatedCollisionArea().getBounds().y < split;
			}
		}

		private SpriteTreeNode getLeft() {
			return left;
		}

		private void setLeft(SpriteTreeNode left) {
			this.left = left;
		}

		private SpriteTreeNode getRight() {
			return right;
		}

		private void setRight(SpriteTreeNode right) {
			this.right = right;
		}
	}

	private class ListNode extends SpriteTreeNode {
		private static final int MAX_SIZE = 10;
		private LinkedList<Sprite> sprites;

		private ListNode(int depth) {
			super(depth);
			this.sprites = new LinkedList<Sprite>();
		}

		private void addSprite(Sprite s) {
			sprites.add(s);
		}

		private void removeSprite(Sprite s) {
			sprites.remove(s);
		}

		private boolean shouldSplit() {
			return sprites.size() > MAX_SIZE;
		}

		private SplitNode split() {
			if (depth % 2 == 0)
				return splitX();
			else
				return splitY();
		}

		private SplitNode splitX() {
			sortX();
			int split = sprites.get(sprites.size() / 2).getRotation().getRotatedCollisionArea().getBounds().x;
			SplitNode splitNode = new SplitNode(depth, split);
			ListNode left = (ListNode) splitNode.getLeft();
			ListNode right = (ListNode) splitNode.getRight();
			for (Sprite s : sprites) {
				Rectangle bounds = s.getRotation().getRotatedCollisionArea().getBounds();
				if (bounds.x < split) {
					left.addSprite(s);
				}
				if (bounds.x + bounds.width >= split) {
					right.addSprite(s);
				}
			}
			return splitNode;
		}

		private SplitNode splitY() {
			sortY();
			int split = sprites.get(sprites.size() / 2).getRotation().getRotatedCollisionArea().getBounds().y;
			SplitNode splitNode = new SplitNode(depth, split);
			ListNode left = (ListNode) splitNode.getLeft();
			ListNode right = (ListNode) splitNode.getRight();
			for (Sprite s : sprites) {
				Rectangle bounds = s.getRotation().getRotatedCollisionArea().getBounds();
				if (bounds.y < split) {
					left.addSprite(s);
				}
				if (bounds.y + bounds.height >= split) {
					right.addSprite(s);
				}
			}
			return splitNode;
		}

		private void sortX() {
			sprites.sort(new Comparator<Sprite>() {
				@Override
				public int compare(Sprite s1, Sprite s2) {
					double diff = s1.getRotation().getRotatedCollisionArea().getBounds().x -
							s2.getRotation().getRotatedCollisionArea().getBounds().x;
					if (diff < 0.000001)
						return 0;
					if (diff > 0)
						return (int) Math.ceil(diff);
					else
						return (int) Math.floor(diff);
				}
			});
		}

		private void sortY() {
			sprites.sort(new Comparator<Sprite>() {
				@Override
				public int compare(Sprite s1, Sprite s2) {
					double diff = s1.getRotation().getRotatedCollisionArea().getBounds().y -
							s2.getRotation().getRotatedCollisionArea().getBounds().y;
					if (diff < 0.000001)
						return 0;
					if (diff > 0)
						return (int) Math.ceil(diff);
					else
						return (int) Math.floor(diff);
				}
			});
		}


	}
}
