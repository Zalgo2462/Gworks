package org.zp.blockdude;

public enum Level {
	ONE(1, 25, 250),
	TWO(2, 25, 250),
	THREE(2, 25, 250),
	FOUR(3, 25, 250),
	FIVE(5, 25, 250),
	SIX(5, 20, 300),
	SEVEN(7, 20, 250),
	EIGHT(8, 15, 250),
	NINE(9, 10, 250),
	TEN(10, 10, 250);
	private final int enemies;
	private final int enemySize;
	private final int enemySpeed;

	private Level(final int enemies, final int enemySize, final int enemySpeed) {
		this.enemies = enemies;
		this.enemySize = enemySize;
		this.enemySpeed = enemySpeed;
	}

	public int getEnemies() {
		return enemies;
	}

	public int getEnemySize() {
		return enemySize;
	}

	public int getEnemySpeed() {
		return enemySpeed;
	}
}
