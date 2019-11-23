package net.cuddlebat.terrawa.cardinal;

import net.cuddlebat.terrawa.Const;
import net.minecraft.nbt.CompoundTag;

public class ProjectileComponent implements IProjectileComponent
{
	private int magicDamage = 0;
	private int voidDamage = 0;
	private boolean active = true;

	@Override
	public float getMagicDamage()
	{
		return magicDamage;
	}

	public void setMagicDamage(int magicDamage)
	{
		this.magicDamage = magicDamage;
	}

	public void addMagicDamage(int magicDamage)
	{
		this.magicDamage += magicDamage;
	}

	@Override
	public float getVoidDamage()
	{
		return voidDamage;
	}

	public void setVoidDamage(int voidDamage)
	{
		this.voidDamage = voidDamage;
	}

	public void addVoidDamage(int voidDamage)
	{
		this.voidDamage += voidDamage; 
	}
	
	@Override
	public void markHit()
	{
		active = false;
		
	}

	@Override
	public boolean isActive()
	{
		return active;
	}

	@Override
	public void fromTag(CompoundTag tag)
	{
		CompoundTag comp = tag.getCompound(Const.MODID);
		magicDamage = comp.getInt("magic");
		voidDamage = comp.getInt("void");
	}

	@Override
	public CompoundTag toTag(CompoundTag tag)
	{
		CompoundTag comp = new CompoundTag();
		comp.putInt("magic", magicDamage);
		comp.putInt("void", voidDamage);
		tag.put(Const.MODID, comp);
		return tag;
	}
}
