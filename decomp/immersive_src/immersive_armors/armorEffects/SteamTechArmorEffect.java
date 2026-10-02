package immersive_armors.armorEffects;

import immersive_armors.cobalt.network.NetworkHandler;
import immersive_armors.network.c2s.ArmorCommandMessage;
import immersive_armors.util.FlowingText;
import java.util.List;
import java.util.Locale;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class SteamTechArmorEffect extends ArmorEffect {
   @Override
   public void appendTooltip(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
      super.appendTooltip(stack, world, tooltip, context);
      MutableComponent text = Component.m_237115_("armorEffect.steamTech." + this.getEquipmentSlot(stack).name().toLowerCase(Locale.ROOT))
         .m_130940_(ChatFormatting.GRAY);
      tooltip.addAll(FlowingText.wrap(text, 140));
   }

   @Override
   public void equippedTick(ItemStack armor, Level world, LivingEntity entity, int slot) {
      super.equippedTick(armor, world, entity, slot);
      if (this.getEquipmentSlot(armor) == EquipmentSlot.FEET) {
         entity.f_19789_ = Math.min(1.0F, entity.f_19789_);
         if (entity.m_217043_().m_188503_(10) == 0) {
            double x = Math.cos((double)(entity.f_20883_ / 180.0F) * Math.PI - (Math.PI / 2)) * 0.25;
            double z = Math.sin((double)(entity.f_20883_ / 180.0F) * Math.PI - (Math.PI / 2)) * 0.25;
            world.m_7106_(ParticleTypes.f_123762_, entity.m_20185_() + x, entity.m_20186_() + 1.2F, entity.m_20189_() + z, x * 0.2F, -0.025F, z * 0.2F);
         }

         Vec3 velocity = entity.m_20184_();
         if (velocity.m_7098_() < -0.75) {
            entity.m_20334_(velocity.f_82479_, -0.75, velocity.f_82481_);
            this.createSteamParticle(entity);
            if (entity.f_19797_ % 5 == 0) {
               entity.f_19853_.m_6263_(null, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), SoundEvents.f_12374_, entity.m_5720_(), 0.1F, 1.0F);
            }
         }
      }

      if (!world.m_5776_() && entity.m_6144_() && this.getEquipmentSlot(armor) == EquipmentSlot.HEAD && entity.f_19797_ % 20 == 0) {
         boolean[] sound = new boolean[]{false};
         world.m_45933_(entity, new AABB(entity.m_20182_(), entity.m_20182_()).m_82400_(16.0)).forEach(e -> {
            if (e instanceof Monster) {
               LivingEntity le = (LivingEntity)e;
               le.m_7292_(new MobEffectInstance(MobEffects.f_19619_, 5));
               if (!sound[0]) {
                  sound[0] = true;
                  entity.f_19853_.m_6263_(null, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), SoundEvents.f_12444_, entity.m_5720_(), 0.25F, 3.0F);
               }
            }
         });
      }

      if (world.f_46443_ && this.getEquipmentSlot(armor) == EquipmentSlot.LEGS) {
         CompoundTag nbt = armor.m_41784_();
         if (entity.m_20096_()) {
            nbt.m_128405_("thrusterState", SteamTechArmorEffect.ThrusterState.CHARGED.ordinal());
         } else if (nbt.m_128451_("thrusterState") == SteamTechArmorEffect.ThrusterState.CHARGED.ordinal()) {
            if (!this.isJumping()) {
               nbt.m_128405_("thrusterState", SteamTechArmorEffect.ThrusterState.READY.ordinal());
            }
         } else if (nbt.m_128451_("thrusterState") == SteamTechArmorEffect.ThrusterState.READY.ordinal() && this.isJumping()) {
            this.thrust(entity);
            nbt.m_128405_("thrusterState", SteamTechArmorEffect.ThrusterState.OFFLINE.ordinal());
            NetworkHandler.sendToServer(new ArmorCommandMessage(slot, "thrust"));
         }
      }
   }

   private void createSteamParticle(LivingEntity entity) {
      entity.f_19853_
         .m_7106_(
            ParticleTypes.f_123777_,
            entity.m_20208_(0.5),
            entity.m_20186_() - 0.25,
            entity.m_20262_(0.5),
            ((double)entity.m_217043_().m_188501_() - 0.5) * 0.1,
            -0.75 + ((double)entity.m_217043_().m_188501_() - 0.5) * 0.1,
            ((double)entity.m_217043_().m_188501_() - 0.5) * 0.1
         );
   }

   @Override
   public void receiveCommand(ItemStack armor, Level world, LivingEntity entity, int slot, String command) {
      if (command.equals("thrust")) {
         this.thrust(entity);
      }
   }

   private boolean isJumping() {
      Minecraft client = Minecraft.m_91087_();
      return client != null && client.f_91074_ != null && client.f_91074_.f_108618_.f_108572_;
   }

   private void thrust(LivingEntity entity) {
      Vec3 velocity = entity.m_20184_();
      entity.m_20334_(velocity.f_82479_, 0.6, velocity.f_82481_);

      for (int i = 0; i < 5; i++) {
         this.createSteamParticle(entity);
      }

      entity.f_19853_.m_6263_(null, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), SoundEvents.f_12374_, entity.m_5720_(), 0.25F, 0.75F);
   }

   private EquipmentSlot getEquipmentSlot(ItemStack stack) {
      return ((ArmorItem)stack.m_41720_()).m_40402_();
   }

   private static enum ThrusterState {
      OFFLINE,
      CHARGED,
      READY;
   }
}
