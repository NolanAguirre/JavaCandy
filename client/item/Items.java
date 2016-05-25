package item;

public interface Items {
	int getAmount();
	void add();
	ItemType getType();
	StatusEffect getEffect();
	Items use();
	int getEffectAmount();
}
