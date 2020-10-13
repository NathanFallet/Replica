package me.nathanfallet.replicapicturemaker.utils;

import java.util.HashMap;

public class Picture {

	private String name;
	private HashMap<String, Integer> blocks = new HashMap<String, Integer>();

	public Picture(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getBlock(int x, int y) {
		if (blocks.containsKey(x + ":" + y)) {
			return blocks.get(x + ":" + y);
		}
		return 0;
	}

	public void setBlock(int block, int x, int y) {
		if (blocks.containsKey(x + ":" + y)) {
			blocks.replace(x + ":" + y, block);
		} else {
			blocks.put(x + ":" + y, block);
		}
	}

}
