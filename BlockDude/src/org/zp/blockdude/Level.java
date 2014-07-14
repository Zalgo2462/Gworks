package org.zp.blockdude;

public enum Level {
	ONE(1, 25, 125),
	TWO(2, 25, 125),
	THREE(2, 25, 150),
	FOUR(3, 25, 150),
	FIVE(5, 25, 150),
	SIX(5, 20, 150),
	SEVEN(7, 20, 150),
	EIGHT(8, 15, 150),
	NINE(9, 10, 150),
	TEN(10, 10, 125);
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
