package shadows.apotheosis.village.fletching.arrows;

import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.village.VillageModule;

public class ExplosiveArrowEntity extends AbstractArrow {
   public ExplosiveArrowEntity(EntityType<? extends AbstractArrow> t, Level world) {
      super(t, world);
   }

   public ExplosiveArrowEntity(Level world) {
      super((EntityType)Apoth.Entities.EXPLOSIVE_ARROW.get(), world);
   }

   public ExplosiveArrowEntity(LivingEntity shooter, Level world) {
      super((EntityType)Apoth.Entities.EXPLOSIVE_ARROW.get(), shooter, world);
   }

   public ExplosiveArrowEntity(Level world, double x, double y, double z) {
      super((EntityType)Apoth.Entities.EXPLOSIVE_ARROW.get(), x, y, z, world);
   }

   protected ItemStack m_7941_() {
      return new ItemStack((ItemLike)Apoth.Items.EXPLOSIVE_ARROW.get());
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   protected void m_7761_(LivingEntity living) {
      if (!this.f_19853_.f_46443_) {
         this.f_19853_.m_46518_(this, living.m_20185_(), living.m_20186_(), living.m_20189_(), 2.0F, false, VillageModule.expArrowMode);
         this.m_146870_();
      }
   }

   protected void m_8060_(BlockHitResult res) {
      super.m_8060_(res);
      Vec3 vec = res.m_82450_();
      if (!this.f_19853_.f_46443_) {
         this.f_19853_.m_46518_(this, vec.m_7096_(), vec.m_7098_(), vec.m_7094_(), 3.0F, false, VillageModule.expArrowMode);
         this.m_146870_();
      }
   }
}
