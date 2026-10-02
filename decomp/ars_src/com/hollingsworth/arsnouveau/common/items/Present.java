package com.hollingsworth.arsnouveau.common.items;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.api.loot.DungeonLootTables;
import com.hollingsworth.arsnouveau.api.nbt.ItemstackData;
import com.hollingsworth.arsnouveau.common.datagen.DungeonLootGenerator;
import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import java.util.List;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.Nullable;

public class Present extends ModItem {
   public Present(Properties properties) {
      super(properties);
   }

   public void m_6883_(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
      super.m_6883_(pStack, pLevel, pEntity, pSlotId, pIsSelected);
      if (!pLevel.f_46443_) {
         Present.PresentData data = new Present.PresentData(pStack);
         if (data.uuid == null && pEntity instanceof Player player) {
            data.setName(player.m_7755_().getString());
            data.setUUID(player.m_20148_());
         }
      }
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
      if (pLevel.f_46443_) {
         return super.m_7203_(pLevel, pPlayer, pUsedHand);
      } else {
         Present.PresentData presentData = new Present.PresentData(pPlayer.m_21120_(pUsedHand));
         int bonusRolls = presentData.uuid != null && !presentData.uuid.equals(pPlayer.m_20148_()) ? 2 : 0;
         DungeonLootGenerator.DungeonLootEnhancerModifier modifier = new DungeonLootGenerator.DungeonLootEnhancerModifier(
            new LootItemCondition[0], 0.5, 0.2, 0.1, 3 + bonusRolls, 1 + bonusRolls, 1 + bonusRolls
         );
         List<ItemStack> stacks = DungeonLootTables.getRandomRoll(modifier);
         if (stacks.isEmpty()) {
            Starbuncle giftStarby = new Starbuncle(pLevel, true);
            giftStarby.m_6034_(pPlayer.m_20185_(), pPlayer.m_20186_(), pPlayer.m_20189_());
            pLevel.m_7967_(giftStarby);
         }

         for (ItemStack stack : stacks) {
            ItemEntity entity = new ItemEntity(pLevel, pPlayer.m_20185_(), pPlayer.m_20186_(), pPlayer.m_20189_(), stack);
            pLevel.m_7967_(entity);
         }

         pPlayer.m_21120_(pUsedHand).m_41774_(1);
         return super.m_7203_(pLevel, pPlayer, pUsedHand);
      }
   }

   @Override
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip2, TooltipFlag flagIn) {
      super.m_7373_(stack, worldIn, tooltip2, flagIn);
      Present.PresentData data = new Present.PresentData(stack);
      if (data.uuid != null) {
         if (data.uuid.equals(ArsNouveau.proxy.getPlayer().m_20148_())) {
            tooltip2.add(Component.m_237115_("ars_nouveau.present.give"));
         } else {
            tooltip2.add(Component.m_237110_("ars_nouveau.present.from", new Object[]{data.name}).m_130940_(ChatFormatting.GOLD));
         }
      }
   }

   public static class PresentData extends ItemstackData {
      String name;
      UUID uuid;

      public PresentData(ItemStack stack) {
         super(stack);
         CompoundTag tag = this.getItemTag(stack);
         if (tag != null) {
            this.name = tag.m_128461_("name");
            if (tag.m_128441_("uuid")) {
               this.uuid = tag.m_128342_("uuid");
            }
         }
      }

      public void setName(String name) {
         this.name = name;
         this.writeItem();
      }

      public void setUUID(UUID uuid) {
         this.uuid = uuid;
         this.writeItem();
      }

      @Override
      public void writeToNBT(CompoundTag tag) {
         if (this.name != null) {
            tag.m_128359_("name", this.name);
         }

         if (this.uuid != null) {
            tag.m_128362_("uuid", this.uuid);
         }
      }

      @Override
      public String getTagString() {
         return "an_present_data";
      }
   }
}
