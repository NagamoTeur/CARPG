package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LootGenerator extends ItemBase implements Vanishable {
   public Random lootRandomizer = new Random();
   public List<ResourceLocation> lootList = new ArrayList<>();

   public LootGenerator() {
      super(ItemBase.getDefaultProperties().m_41497_(Rarity.EPIC).m_41487_(1));
      this.lootList.add(BuiltInLootTables.f_78740_);
      this.lootList.add(BuiltInLootTables.f_78741_);
      this.lootList.add(BuiltInLootTables.f_78742_);
      this.lootList.add(BuiltInLootTables.f_78759_);
      this.lootList.add(BuiltInLootTables.f_78760_);
      this.lootList.add(BuiltInLootTables.f_78761_);
      this.lootList.add(BuiltInLootTables.f_78762_);
      this.lootList.add(BuiltInLootTables.f_78763_);
      this.lootList.add(BuiltInLootTables.f_78764_);
      this.lootList.add(BuiltInLootTables.f_78686_);
      this.lootList.add(BuiltInLootTables.f_78687_);
      this.lootList.add(BuiltInLootTables.f_78688_);
      this.lootList.add(BuiltInLootTables.f_78689_);
      this.lootList.add(BuiltInLootTables.f_78690_);
      this.lootList.add(BuiltInLootTables.f_78691_);
      this.lootList.add(BuiltInLootTables.f_78692_);
      this.lootList.add(BuiltInLootTables.f_78693_);
      this.lootList.add(BuiltInLootTables.f_78694_);
      this.lootList.add(BuiltInLootTables.f_78695_);
      this.lootList.add(BuiltInLootTables.f_78696_);
      this.lootList.add(BuiltInLootTables.f_78697_);
      this.lootList.add(BuiltInLootTables.f_78698_);
      this.lootList.add(BuiltInLootTables.f_78699_);
      this.lootList.add(BuiltInLootTables.f_78700_);
      this.lootList.add(BuiltInLootTables.f_78701_);
      this.lootList.add(BuiltInLootTables.f_78743_);
      this.lootList.add(BuiltInLootTables.f_78744_);
      this.lootList.add(BuiltInLootTables.f_78745_);
      this.lootList.add(BuiltInLootTables.f_78746_);
      this.lootList.add(BuiltInLootTables.f_78747_);
      this.lootList.add(BuiltInLootTables.f_78748_);
      this.lootList.add(BuiltInLootTables.f_78749_);
      this.lootList.add(BuiltInLootTables.f_78750_);
      this.lootList.add(BuiltInLootTables.f_78751_);
      this.lootList.add(BuiltInLootTables.f_78752_);
      this.lootList.add(BuiltInLootTables.f_78753_);
      this.lootList.add(BuiltInLootTables.f_78754_);
      this.lootList.add(BuiltInLootTables.f_78755_);
      this.lootList.add(BuiltInLootTables.f_78756_);
      this.lootList.add(BuiltInLootTables.f_78757_);
      this.lootList.add(BuiltInLootTables.f_78758_);
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.lootGenerator1");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.lootGenerator2");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.lootGenerator3");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.lootGenerator4");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.lootGenerator5");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.lootGenerator6");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.lootGenerator7");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.lootGenerator8");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.lootGenerator9");
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }

      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.lootGeneratorCurrent");
      list.add(Component.m_237113_(this.lootList.get(stack.m_41773_()) + "").m_130940_(ChatFormatting.GOLD));
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      if (!player.m_6047_()) {
         player.m_6672_(hand);
         if (itemstack.m_41773_() < this.lootList.size() - 1) {
            itemstack.m_41721_(itemstack.m_41773_() + 1);
         } else {
            itemstack.m_41721_(0);
         }

         player.m_6674_(hand);
      } else {
         if (itemstack.m_41773_() > 0) {
            itemstack.m_41721_(itemstack.m_41773_() - 1);
         } else {
            itemstack.m_41721_(this.lootList.size() - 1);
         }

         player.m_6674_(hand);
      }

      if (player instanceof ServerPlayer) {
         player.m_5661_(Component.m_237113_("Table: " + this.lootList.get(itemstack.m_41773_())), true);
      }

      return new InteractionResultHolder(InteractionResult.SUCCESS, itemstack);
   }

   public InteractionResult m_6225_(UseOnContext context) {
      Player player = context.m_43723_();
      Level world = context.m_43725_();
      ItemStack stack = context.m_43722_();
      if (world.f_46443_) {
         return InteractionResult.SUCCESS;
      } else if (world.m_8055_(context.m_8083_()).m_155947_() && world.m_7702_(context.m_8083_()) instanceof ChestBlockEntity && player.m_6047_()) {
         ChestBlockEntity chest = (ChestBlockEntity)world.m_7702_(context.m_8083_());
         Direction dir = context.m_43719_();
         if (dir == Direction.UP) {
            chest.m_59626_(this.lootList.get(stack.m_41773_()), this.lootRandomizer.nextLong());
            chest.m_59640_(player);
         } else if (dir == Direction.DOWN && !SuperpositionHandler.hasSpellstoneCooldown(player)) {
            SuperpositionHandler.setSpellstoneCooldown(player, 40);
            HashMap<Item, Integer> lootMap = new HashMap<>();

            for (int counter = 0; counter < 32768; counter++) {
               chest.m_59626_(this.lootList.get(stack.m_41773_()), this.lootRandomizer.nextLong());
               chest.m_59640_(player);

               for (int slot = 0; slot < chest.m_6643_(); slot++) {
                  ItemStack generatedStack = chest.m_8020_(slot);
                  Item generatedItem = generatedStack.m_41720_();
                  int amount = generatedStack.m_41613_();
                  if (!generatedStack.m_41619_()) {
                     if (lootMap.containsKey(generatedItem)) {
                        lootMap.put(generatedItem, lootMap.get(generatedItem) + amount);
                     } else {
                        lootMap.put(generatedItem, amount);
                     }
                  }
               }

               chest.m_6211_();
            }

            EnigmaticLegacy.LOGGER.info("Estimated generation complete in 32768 instances, results:");

            for (Item theItem : lootMap.keySet()) {
               EnigmaticLegacy.LOGGER.info("Item: " + theItem.m_7626_(new ItemStack(theItem)).getString() + ", Amount: " + lootMap.get(theItem));
            }

            player.m_213846_(Component.m_237115_("message.enigmaticlegacy.gen_sim_complete").m_130940_(ChatFormatting.DARK_PURPLE));
         } else {
            chest.m_6211_();
         }

         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.PASS;
      }
   }
}
