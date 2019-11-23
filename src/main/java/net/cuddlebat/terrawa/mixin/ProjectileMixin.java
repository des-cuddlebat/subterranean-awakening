package net.cuddlebat.terrawa.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.cuddlebat.terrawa.Const;
import net.cuddlebat.terrawa.cardinal.IProjectileComponent;
import net.cuddlebat.terrawa.utils.VoidProjDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

@Mixin(ProjectileEntity.class)
public abstract class ProjectileMixin extends Entity
{
	public ProjectileMixin(EntityType<?> entityType_1, World world_1)
	{
		super(entityType_1, world_1);
		// TODO Auto-generated constructor stub
	}

	@Inject(at = @At("HEAD"), method = "onHit(Lnet/minecraft/util/hit/HitResult;)V")
	protected void onHitMixin(HitResult hitResult_1, CallbackInfo info)
	{
		if(hitResult_1.getType() == HitResult.Type.ENTITY)
		{
			ProjectileEntity proj = (ProjectileEntity)(Entity)this;
			EntityHitResult hitResult = (EntityHitResult)hitResult_1;
			Entity target = hitResult.getEntity();
			IProjectileComponent comp = Const.Component.PROJECTILE_COMPONENT.maybeGet(proj).orElse(null);
			
			if(comp != null && comp.isActive())
			{
				Entity owner = proj.getOwner();
				DamageSource magicSource;
				DamageSource voidSource;
				if (owner == null)
				{
					magicSource = DamageSource.MAGIC;
					voidSource = new VoidProjDamageSource(proj);
				}
				else
				{
					magicSource = DamageSource.magic(proj, owner);
					voidSource = new VoidProjDamageSource(proj, owner);
				}
				
				target.damage(magicSource, comp.getMagicDamage());
				target.damage(voidSource, comp.getVoidDamage());
				
				comp.markHit();
			}
		}
	}
}
