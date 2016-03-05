package sprite;

import map.TileType;

public interface SpriteTile extends Sprite {
	void compress();
	int getCompress();
	TileType getType();
}
