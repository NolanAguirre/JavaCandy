package map;

import java.util.ArrayList;

import player.Mob;
import sprite.SpriteTile;

public interface IMap {
	public ArrayList<SpriteTile> getMapLayout();
	public ArrayList<Mob> getEnemys();
}
