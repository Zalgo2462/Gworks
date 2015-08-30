package org.zp.platformers.morning.sprites.collisions;

import org.zp.gworks.gui.sprites.Sprite;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Date: 8/29/2015
 * Time: 6:29 PM
 */
public class StaticSpriteTree {

	private SpriteTreeNode root;

	public StaticSpriteTree() {
		this.root = new ListNode(null, false);
	}

	private void traverseToListNodes(SpriteTreeNode node, Shape s, LinkedList<ListNode> nodes) {
		if (node instanceof ListNode) {
			nodes.add((ListNode) node);
		} else {
			SplitNode split = (SplitNode) node;
			if (split.traverseLeft(s)) {
				traverseToListNodes(split.getLeft(), s, nodes);
			}
			if (split.traverseRight(s)) {
				traverseToListNodes(split.getRight(), s, nodes);
			}
		}
	}

	private void traverseToListNodes(SpriteTreeNode node, LinkedList<ListNode> nodes) {
		if (node instanceof ListNode) {
			nodes.add((ListNode) node);
		} else {
			SplitNode split = (SplitNode) node;
			traverseToListNodes(split.getLeft(), nodes);
			traverseToListNodes(split.getRight(), nodes);
		}
	}

	private LinkedList<ListNode> getListNodes(Shape s) {
		LinkedList<ListNode> lists = new LinkedList<ListNode>();
		traverseToListNodes(root, s, lists);
		return lists;
	}

	private LinkedList<ListNode> getListNodes() {
		LinkedList<ListNode> lists = new LinkedList<ListNode>();
		traverseToListNodes(root, lists);
		return lists;
	}

	public void addSprite(Sprite s) {
		LinkedList<ListNode> listNodes = getListNodes(s.getRotation().getRotatedCollisionArea());
		for (int i = 0; i < listNodes.size(); i++) {
			ListNode node = listNodes.get(i);
			node.addSprite(s);
			if (node.shouldSplit()) {
				if (node.equals(root)) {
					root = node.split();
				} else {
					SplitNode newSplit = node.split();
					if (node.isLeft()) {
						node.getParent().setLeft(newSplit);
					} else {
						node.getParent().setRight(newSplit);
					}
				}
			}
		}
	}

	public void removeSprite(Sprite s) {
		LinkedList<ListNode> listNodes = getListNodes(s.getRotation().getRotatedCollisionArea());
		for (ListNode node : listNodes) {
			node.removeSprite(s);
		}
	}

	public void removeAllSprites() {
		root = new ListNode(null, false); //let JVM take care of GC
	}

	public Sprite[] getAllSprites() {
		LinkedList<ListNode> lists = getListNodes();
		ArrayList<Sprite> arrayList = new ArrayList<Sprite>();
		for (ListNode l : lists) {
			arrayList.addAll(l.sprites);
		}
		return arrayList.toArray(new Sprite[arrayList.size()]);
	}

	public Sprite[] getAllSpritesInBounds(Shape s) {
		LinkedList<ListNode> listNodes = getListNodes(s);
		ArrayList<Sprite> collisions = new ArrayList<Sprite>();
		for (ListNode list : listNodes) {
			for (Sprite poss : list.sprites) {
				if (Collider.testIntersection(s, poss.getRotation().getRotatedCollisionArea()))
					collisions.add(poss);
			}
		}
		return collisions.toArray(new Sprite[collisions.size()]);
	}

	public Sprite getFirstCollision(Sprite s) {
		LinkedList<ListNode> listNodes = getListNodes(s.getRotation().getRotatedCollisionArea());
		Sprite collision = null;
		for (int i = 0; i < listNodes.size() && collision == null; i++) {
			ListNode list = listNodes.get(i);
			collision = Collider.getFirstCollision(s, list.sprites); //wrongful access here but meh
		}
		return collision;
	}

	public Sprite[] getAllCollisions(Sprite s) {
		LinkedList<ListNode> listNodes = getListNodes(s.getRotation().getRotatedCollisionArea());
		ArrayList<Sprite> collisions = new ArrayList<Sprite>();
		for (ListNode list : listNodes) {
			for (Sprite poss : list.sprites) {
				if (Collider.checkForCollision(s, poss))
					collisions.add(poss);
			}
		}
		return collisions.toArray(new Sprite[collisions.size()]);
	}

	public Double getCollisionAngle(Sprite s1, Sprite s2) {
		return Collider.getAngleIfCollision(s1, s2);
	}

	private abstract class SpriteTreeNode {
		int depth;

		protected SpriteTreeNode(int depth) {
			this.depth = depth;
		}

		protected int getDepth() {
			return depth;
		}
	}

	private class SplitNode extends SpriteTreeNode {
		private SpriteTreeNode left;
		private SpriteTreeNode right;
		private int split;

		private SplitNode(int depth, int splitValue) {
			super(depth);
			this.split = splitValue;
			left = new ListNode(this, true);
			right = new ListNode(this, false);
		}

		private boolean traverseLeft(Shape s) {
			Rectangle r = s.getBounds();
			if (depth % 2 == 0) {
				return r.x < split;
			} else {
				return r.y < split;
			}
		}

		private boolean traverseRight(Shape s) {
			Rectangle r = s.getBounds();
			if (depth % 2 == 0) {
				return r.x + r.width >= split;
			} else {
				return r.y + r.height >= split;
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
		private static final int MAX_SIZE = 25;
		private LinkedList<Sprite> sprites;
		private SplitNode parent;
		private boolean left;

		private ListNode(SplitNode parent, boolean left) {
			super(parent == null ? 0 : parent.getDepth() + 1);
			this.sprites = new LinkedList<Sprite>();
			this.parent = parent;
			this.left = left;
		}

		private void addSprite(Sprite s) {
			sprites.add(s);
		}

		private void removeSprite(Sprite s) {
			sprites.remove(s);
		}

		private SplitNode getParent() {
			return parent;
		}

		private boolean isLeft() {
			return left;
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
					if (diff > -0.000001 && diff < 0.000001)
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
					if (diff > -0.000001 && diff < 0.000001)
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
