package net.cuddlebat.terrawa.cardinal;

import nerdhub.cardinal.components.api.component.Component;

public interface IProjectileComponent extends Component
{
	float getMagicDamage();
	float getVoidDamage();
	void markHit();
	boolean isActive();
}
