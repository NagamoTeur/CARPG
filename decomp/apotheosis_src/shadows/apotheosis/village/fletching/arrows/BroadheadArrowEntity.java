package shadows.apotheosis.village.fletching.arrows;

import net.minecraft.network.protocol.Packet;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.core.mobfx.api.MFEffects;

public class BroadheadArrowEntity extends Arrow {
   public BroadheadArrowEntity(EntityType<? extends Arrow> t, Level world) {
      super(t, world);
   }

   public BroadheadArrowEntity(Level world) {
      super((EntityType)Apoth.Entities.BROADHEAD_ARROW.get(), world);
   }

   public BroadheadArrowEntity(LivingEntity shooter, Level world) {
      super(world, shooter);
   }

   public BroadheadArrowEntity(Level world, double x, double y, double z) {
      super(world, x, y, z);
   }

   protected ItemStack m_7941_() {
      return new ItemStack((ItemLike)Apoth.Items.BROADHEAD_ARROW.get());
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public EntityType<?> m_6095_() {
      return (EntityType<?>)Apoth.Entities.BROADHEAD_ARROW.get();
   }

   public int m_36889_() {
      return -1;
   }

   protected void m_7761_(LivingEntity living) {
      MobEffectInstance bleed = living.m_21124_((MobEffect)MFEffects.BLEEDING.get());
      if (bleed != null) {
         living.m_7292_(new MobEffectInstance((MobEffect)MFEffects.BLEEDING.get(), bleed.m_19557_() + 60, bleed.m_19564_() + 1));
      } else {
         living.m_7292_(new MobEffectInstance((MobEffect)MFEffects.BLEEDING.get(), 300));
      }
   }

   public BroadheadArrowEntity bleed() {
      this.m_36870_(new MobEffectInstance((MobEffect)MFEffects.BLEEDING.get(), 300));
      return this;
   }
}
