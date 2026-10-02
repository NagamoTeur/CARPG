package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.api.generic.SubscribeConfig;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBaseCurio;
import com.aizistral.enigmaticlegacy.registries.EnigmaticItems;
import com.aizistral.omniconfig.wrappers.Omniconfig;
import com.aizistral.omniconfig.wrappers.OmniconfigWrapper;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;

public class SuperMagnetRing extends MagnetRing {
   public static Omniconfig.IntParameter range;
   public static Omniconfig.BooleanParameter soundEnabled;

   @SubscribeConfig
   public static void onConfig(OmniconfigWrapper builder) {
      builder.pushPrefix("SuperMagnetRing");
      range = builder.comment("The radius in which Dislocation Ring will collect items.").min(1.0).max(256.0).getInt("Range", 16);
      soundEnabled = builder.comment("Whether or not Dislocation Ring should play sound effect when teleporting items to player.").getBoolean("Sound", false);
      builder.popPrefix();
   }

   public SuperMagnetRing() {
      super(ItemBaseCurio.getDefaultProperties().m_41497_(Rarity.EPIC));
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.superMagnetRing1");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.superMagnetRing2", ChatFormatting.GOLD, range.getValue());
         ItemLoreHelper.addLocalizedString(
            list, invertShift.getValue() ? "tooltip.enigmaticlegacy.superMagnetRing3_alt" : "tooltip.enigmaticlegacy.superMagnetRing3"
         );
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }
   }

   @Override
   public void curioTick(SlotContext context, ItemStack stack) {
      LivingEntity living = context.entity();
      if (invertShift.getValue() ? living.m_6047_() : !living.m_6047_() && !living.f_19853_.f_46443_ && living instanceof Player) {
         Player player = (Player)living;
         if (!this.hasMagnetEffectsDisabled(player)) {
            double x = living.m_20185_();
            double y = living.m_20186_() + 0.75;
            double z = living.m_20189_();
            List<ItemEntity> items = living.f_19853_
               .m_45976_(
                  ItemEntity.class,
                  new AABB(
                     x - (double)range.getValue(),
                     y - (double)range.getValue(),
                     z - (double)range.getValue(),
                     x + (double)range.getValue(),
                     y + (double)range.getValue(),
                     z + (double)range.getValue()
                  )
               );
            int pulled = 0;

            for (ItemEntity item : items) {
               if (this.canPullItem(item)) {
                  if (pulled > 512) {
                     break;
                  }

                  if (SuperpositionHandler.canPickStack(player, item.m_32055_())) {
                     item.m_32061_();
                     item.m_6123_(player);
                     pulled++;
                  }
               }
            }
         }
      }
   }

   @Override
   public boolean canEquip(SlotContext context, ItemStack stack) {
      return super.canEquip(context, stack) && !SuperpositionHandler.hasCurio(context.entity(), EnigmaticItems.MAGNET_RING);
   }

   @Override
   protected boolean canPullItem(ItemEntity item) {
      return super.canPullItem(item);
   }
}
