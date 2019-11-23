package net.cuddlebat.terrawa.utils;

import java.util.UUID;

public class DamageLogEntry
{
	private float amount;
	private String type;
	private UUID attacker;
	private int timestamp;
	
	public DamageLogEntry(float amount, String type, UUID attacker, int timestamp)
	{
		super();
		this.amount = amount;
		this.type = type;
		this.attacker = attacker;
		this.timestamp = timestamp;
	}

	public void setAmount(float val)
	{
		amount = val;
	}

	public float getAmount()
	{
		return amount;
	}

	public String getType()
	{
		return type;
	}

	public UUID getAttacker()
	{
		return attacker;
	}

	public int getTimestamp()
	{
		return timestamp;
	}
}
