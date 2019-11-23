package net.cuddlebat.terrawa.cardinal;

import nerdhub.cardinal.components.api.component.Component;
import net.minecraft.entity.damage.DamageSource;

public interface IDamageCooldownComponent extends Component
{
	public void logDamage(DamageSource source, float amount);
	public float getLoggedDamage(DamageSource source, float amount);
	public void tick();
}
