package net.cuddlebat.terrawa.cardinal;

import java.util.LinkedList;

import net.cuddlebat.terrawa.utils.DamageLogEntry;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.CompoundTag;

public class DamageCooldownComponent implements IDamageCooldownComponent
{
	public static final int COOLDOWN_TICKS = 10;
	private LinkedList<DamageLogEntry> log = new LinkedList<DamageLogEntry>();
	private int age;
	
	@Override
	public void logDamage(DamageSource source, float amount)
	{
		if(source.getAttacker() == null)
			return;
		for (DamageLogEntry entry : log)
		{
			if(entry.getAttacker().equals(source.getAttacker().getUuid()) &&
				entry.getType().equals(source.name))
			{
				entry.setAmount(amount);
				return;
			}
		}
		log.add(new DamageLogEntry(amount, source.name, source.getAttacker().getUuid(), age));
	}

	@Override
	public float getLoggedDamage(DamageSource source, float amount)
	{
		if(source.getAttacker() == null)
			return 0;
		for (DamageLogEntry entry : log)
		{
			if(entry.getAttacker().equals(source.getAttacker().getUuid()) &&
				entry.getType().equals(source.name))
			{
				return entry.getAmount();
			}
		}
		return 0;
	}

	@Override
	public void tick()
	{
		age++;
		while(!log.isEmpty() && log.getFirst().getTimestamp() < age - COOLDOWN_TICKS)
		{
			log.removeFirst();
		}
	}

	@Override
	public void fromTag(CompoundTag tag)
	{
		CompoundTag comp = tag.getCompound("terrawa_damagelog");
		this.age = comp.getInt("age");
		int count = comp.getInt("entries");
		for(int i = 0; i < count; i++)
		{
			log.add(new DamageLogEntry(
				comp.getFloat("amount_" + i),
				comp.getString("type_" + i),
				comp.getUuid("uuid_" + i),
				comp.getInt("stamp_" + i)
				));
		}
	}

	@Override
	public CompoundTag toTag(CompoundTag tag)
	{
		CompoundTag comp = new CompoundTag();
		comp.putInt("age", age);
		int i = 0;
		for(DamageLogEntry entry : log)
		{
			comp.putFloat("amount_" + i, entry.getAmount());
			comp.putString("type_" + i, entry.getType());
			comp.putUuid("uuid_" + i, entry.getAttacker());
			comp.putInt("stamp_" + i, entry.getTimestamp());
			i++;
		}
		comp.putInt("entries", i);
		tag.put("terrawa_damagelog", comp);
		return tag;
	}
}
