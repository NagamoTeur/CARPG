package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.client.ITooltipProvider;
import com.hollingsworth.arsnouveau.api.item.IWandable;
import com.hollingsworth.arsnouveau.api.potion.PotionData;
import com.hollingsworth.arsnouveau.api.util.NBTUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.util.ColorPos;
import com.hollingsworth.arsnouveau.common.block.ITickable;
import com.hollingsworth.arsnouveau.common.entity.EntityFlyingItem;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

public class PotionDiffuserTile extends ModdedTile implements ITickable, IWandable, ITooltipProvider {
   public PotionData lastConsumedPotion = new PotionData();
   public int ticksToConsume;
   public BlockPos boundPos;
   public boolean isOff;

   public PotionDiffuserTile(BlockPos pos, BlockState state) {
      super(BlockRegistry.POTION_DIFFUSER_TILE, pos, state);
   }

   @Override
   public void tick() {
      if (!this.isOff) {
         if (this.f_58857_.f_46443_ && !this.isOff && this.ticksToConsume > 0 && !this.lastConsumedPotion.isEmpty() && this.f_58857_.m_46467_() % 8L == 0L) {
            this.f_58857_.m_7106_(ParticleTypes.f_123762_, this.getX() + 0.5, this.getY() + 1.0, this.getZ() + 0.5, 0.0, 0.0, 0.0);
         } else {
            if (this.ticksToConsume <= 0 && this.boundPos != null && this.f_58857_.m_46467_() % 60L == 0L) {
               this.obtainPotion();
            }

            if (this.ticksToConsume > 0) {
               this.ticksToConsume--;
               if (this.f_58857_.m_46467_() % 300L == 0L) {
                  for (LivingEntity entity : this.f_58857_.m_45976_(LivingEntity.class, new AABB(this.m_58899_()).m_82400_(10.0))) {
                     this.lastConsumedPotion.applyEffects(null, null, entity);
                  }
               }
            }
         }
      }
   }

   public void obtainPotion() {
      if (this.f_58857_.m_46749_(this.boundPos)
         && this.f_58857_.m_7702_(this.boundPos) instanceof PotionJarTile jar
         && !jar.getData().isEmpty()
         && jar.getAmount() >= 100) {
         this.lastConsumedPotion = jar.getData();
         this.ticksToConsume = 12000;
         jar.remove(100);
         ParticleColor color2 = ParticleColor.fromInt(jar.getColor());
         EntityFlyingItem item2 = new EntityFlyingItem(
               this.f_58857_,
               jar.m_58899_().m_7494_(),
               this.f_58858_,
               Math.round(255.0F * color2.getRed()),
               Math.round(255.0F * color2.getGreen()),
               Math.round(255.0F * color2.getBlue())
            )
            .withNoTouch();
         item2.setDistanceAdjust(2.0F);
         this.f_58857_.m_7967_(item2);
         this.updateBlock();
      }
   }

   @Override
   public void onFinishedConnectionLast(@Nullable BlockPos storedPos, @Nullable LivingEntity storedEntity, Player playerEntity) {
      if (storedPos != null && this.f_58857_.m_7702_(storedPos) instanceof PotionJarTile jar) {
         this.boundPos = storedPos.m_7949_();
         PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.potion_diffuser.set_pos"));
         if (this.lastConsumedPotion != null && !this.lastConsumedPotion.isEmpty() && !this.lastConsumedPotion.areSameEffects(jar.getData())) {
            this.obtainPotion();
         }

         this.updateBlock();
      } else {
         PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.potion_diffuser.bind_to_jar"));
      }
   }

   public void m_142466_(CompoundTag pTag) {
      super.m_142466_(pTag);
      this.boundPos = NBTUtil.getNullablePos(pTag, "boundPos");
      this.isOff = pTag.m_128471_("isOff");
      this.ticksToConsume = pTag.m_128451_("ticksToConsume");
      if (pTag.m_128441_("lastConsumedPotion")) {
         this.lastConsumedPotion = PotionData.fromTag(pTag.m_128469_("lastConsumedPotion"));
      }
   }

   @Override
   public void m_183515_(CompoundTag tag) {
      super.m_183515_(tag);
      if (this.boundPos != null) {
         NBTUtil.storeBlockPos(tag, "boundPos", this.boundPos);
      }

      tag.m_128379_("isOff", this.isOff);
      tag.m_128405_("ticksToConsume", this.ticksToConsume);
      if (this.lastConsumedPotion != null) {
         tag.m_128365_("lastConsumedPotion", this.lastConsumedPotion.toTag());
      }
   }

   @Override
   public void getTooltip(List<Component> tooltip) {
      if (this.boundPos == null) {
         tooltip.add(Component.m_237115_("ars_nouveau.potion_diffuser.no_pos").m_130940_(ChatFormatting.GOLD));
      }

      if (this.isOff) {
         tooltip.add(Component.m_237115_("ars_nouveau.potion_diffuser.off").m_130940_(ChatFormatting.GOLD));
      }

      if (this.lastConsumedPotion != null) {
         this.lastConsumedPotion.appendHoverText(tooltip);
      }
   }

   @Override
   public List<ColorPos> getWandHighlight(List<ColorPos> colorPos) {
      if (this.boundPos != null) {
         colorPos.add(ColorPos.centered(this.boundPos));
      }

      return colorPos;
   }
}
