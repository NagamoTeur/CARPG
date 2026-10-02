package shadows.apotheosis.village.fletching.arrows;

import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;
import shadows.apotheosis.Apoth;

public class ObsidianArrowEntity extends AbstractArrow {
   public ObsidianArrowEntity(EntityType<? extends AbstractArrow> t, Level world) {
      super(t, world);
   }

   public ObsidianArrowEntity(Level world) {
      super((EntityType)Apoth.Entities.OBSIDIAN_ARROW.get(), world);
   }

   public ObsidianArrowEntity(LivingEntity shooter, Level world) {
      super((EntityType)Apoth.Entities.OBSIDIAN_ARROW.get(), shooter, world);
   }

   public ObsidianArrowEntity(Level world, double x, double y, double z) {
      super((EntityType)Apoth.Entities.OBSIDIAN_ARROW.get(), x, y, z, world);
   }

   protected ItemStack m_7941_() {
      return new ItemStack((ItemLike)Apoth.Items.OBSIDIAN_ARROW.get());
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   protected void m_5790_(EntityHitResult res) {
      double base = this.m_36789_();
      this.m_36781_(base * 1.2F);
      super.m_5790_(res);
      this.m_36781_(base);
   }
}
