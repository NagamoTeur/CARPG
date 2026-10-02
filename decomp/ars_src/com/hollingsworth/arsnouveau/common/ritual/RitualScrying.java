package com.hollingsworth.arsnouveau.common.ritual;

import com.hollingsworth.arsnouveau.api.ritual.AbstractRitual;
import com.hollingsworth.arsnouveau.api.scrying.IScryer;
import com.hollingsworth.arsnouveau.api.scrying.SingleBlockScryer;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.lib.RitualLib;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketGetPersistentData;
import com.hollingsworth.arsnouveau.common.potions.ModPotions;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.PacketDistributor;

public class RitualScrying extends AbstractRitual {
   @Override
   protected void tick() {
      ParticleUtil.spawnFallingSkyEffect(this, this.tile, this.rand, this.getCenterColor().toWrapper());
      if (this.getWorld().m_46467_() % 20L == 0L && !this.getWorld().f_46443_) {
         this.incrementProgress();
      }

      if (!this.getWorld().f_46443_ && this.getProgress() >= 15) {
         List<ServerPlayer> players = this.getWorld().m_45976_(ServerPlayer.class, new AABB(this.getPos()).m_82400_(5.0));
         if (!players.isEmpty()) {
            ItemStack item = this.getConsumedItems().stream().filter(i -> i.m_41720_() instanceof BlockItem).findFirst().orElse(ItemStack.f_41583_);
            int modifier = this.didConsumeItem(ItemsRegistry.MANIPULATION_ESSENCE) ? 3 : 1;

            for (ServerPlayer playerEntity : players) {
               if (item.m_41720_() instanceof BlockItem blockItem) {
                  grantScrying(playerEntity, 6000 * modifier, new SingleBlockScryer(blockItem.m_40614_()));
               }
            }
         }

         this.setFinished();
      }
   }

   @Override
   public ResourceLocation getRegistryName() {
      return new ResourceLocation("ars_nouveau", RitualLib.SCRYING);
   }

   public static void grantScrying(ServerPlayer playerEntity, int ticks, IScryer scryer) {
      playerEntity.m_7292_(new MobEffectInstance((MobEffect)ModPotions.SCRYING_EFFECT.get(), ticks));
      CompoundTag tag = playerEntity.getPersistentData().m_128469_("PlayerPersisted");
      tag.m_128365_("an_scryer", scryer.toTag(new CompoundTag()));
      playerEntity.getPersistentData().m_128365_("PlayerPersisted", tag);
      Networking.INSTANCE.send(PacketDistributor.PLAYER.with(() -> playerEntity), new PacketGetPersistentData(tag));
   }

   @Override
   public boolean canStart() {
      return !this.getConsumedItems().isEmpty();
   }

   @Override
   public boolean canConsumeItem(ItemStack stack) {
      Item extendTime = ItemsRegistry.MANIPULATION_ESSENCE.get();
      if (this.didConsumeItem(extendTime) && this.getConsumedItems().size() == 1 && stack.m_41720_() instanceof BlockItem) {
         return true;
      } else if (!this.getConsumedItems().isEmpty() && stack.m_41720_() instanceof BlockItem) {
         return false;
      } else if (this.didConsumeItem(stack.m_41720_())) {
         return false;
      } else {
         return this.getConsumedItems().isEmpty() && stack.m_41720_() instanceof BlockItem ? true : stack.m_41720_() == extendTime;
      }
   }

   @Override
   public ParticleColor getCenterColor() {
      return ParticleColor.makeRandomColor(50, 155, 80, this.rand);
   }

   @Override
   public String getLangName() {
      return "Scrying";
   }

   @Override
   public String getLangDescription() {
      return "Grants vision of a given block through any other block for a given time. White particles signify you are very close, green is semi-far, and blue particles are blocks very far from you.  To complete the ritual, throw any block of your choice before starting. You may also add a Manipulation Essence to increase the duration to 15 minutes.";
   }
}
