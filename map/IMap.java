package map;

import java.util.ArrayList;

import mob.Mob;
import sprite.SpriteTile;

public interface IMap {
	ArrayList<SpriteTile> getMapLayout();
	ArrayList<Mob> getMobs();
	void addMob(Mob player);
	Chest getChest();
}
