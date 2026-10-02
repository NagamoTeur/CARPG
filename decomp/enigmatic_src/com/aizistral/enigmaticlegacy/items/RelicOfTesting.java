package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.api.items.IHidden;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBase;
import com.aizistral.enigmaticlegacy.objects.TransientPlayerData;
import com.aizistral.enigmaticlegacy.registries.EnigmaticItems;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RelicOfTesting extends ItemBase implements IHidden {
   public Random lootRandomizer = new Random();

   public RelicOfTesting() {
      super(ItemBase.getDefaultProperties().m_41497_(Rarity.EPIC).m_41487_(1).m_41491_(null));
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.relicOfTesting1");
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }

      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
      ItemStack itemstack = playerIn.m_21120_(handIn);
      playerIn.m_6672_(handIn);
      SuperpositionHandler.setSpellstoneCooldown(playerIn, 0);
      SuperpositionHandler.setPersistentInteger(playerIn, EnigmaticItems.TATTERED_TOME.persistantPointsTag, 0);
      ItemStack checkTag = (ItemStack)playerIn.m_150109_().f_35976_.get(0);
      if (checkTag != null) {
         playerIn.m_213846_(Component.m_237113_(checkTag.m_41784_().m_7916_()));
      }

      if (!worldIn.f_46443_) {
         TransientPlayerData.get(playerIn).setUnlockedNarrator(false);
      }

      playerIn.m_6674_(handIn);
      return new InteractionResultHolder(InteractionResult.SUCCESS, itemstack);
   }

   public void m_6883_(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected) {
      for (Creeper creeper : world.m_45976_(Creeper.class, SuperpositionHandler.getBoundingBoxAroundEntity(entity, 24.0))) {
         creeper.f_21345_
            .m_25352_(
               1,
               new AvoidEntityGoal(
                  creeper,
                  Player.class,
                  arg -> arg instanceof Player ? SuperpositionHandler.hasCurio(arg, EnigmaticItems.ENIGMATIC_AMULET) : false,
                  6.0F,
                  1.0,
                  1.2,
                  EntitySelector.f_20406_::test
               )
            );
         if (creeper.m_5448_() == entity) {
            creeper.m_6710_(null);
         }
      }

      if (entity instanceof ServerPlayer player && entity.f_19797_ % 20 == 0) {
      }
   }
}
