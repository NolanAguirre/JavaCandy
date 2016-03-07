package sprite;

import map.TileType;

public interface SpriteTile extends Sprite { // for tiles, used to make maps
	void compress();
	int getCompress();
	TileType getType();
}
