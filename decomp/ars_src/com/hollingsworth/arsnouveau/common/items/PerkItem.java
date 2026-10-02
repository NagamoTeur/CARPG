package com.hollingsworth.arsnouveau.common.items;

import com.hollingsworth.arsnouveau.api.perk.IPerk;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import com.mojang.blaze3d.platform.InputConstants;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PerkItem extends ModItem {
   public IPerk perk;

   public PerkItem(Properties properties) {
      super(properties);
   }

   public PerkItem(IPerk perk) {
      super(ItemsRegistry.defaultItemProperties());
      this.perk = perk;
   }

   public Component m_7626_(ItemStack pStack) {
      return Component.m_237113_(this.perk.getName());
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip2, TooltipFlag flagIn) {
      if (this.perk != null) {
         if (InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), Minecraft.m_91087_().f_91066_.f_92090_.getKey().m_84873_())) {
            tooltip2.add(Component.m_237115_(this.perk.getDescriptionKey()));
         } else {
            tooltip2.add(Component.m_237110_("tooltip.ars_nouveau.hold_shift", new Object[]{Minecraft.m_91087_().f_91066_.f_92090_.getKey().m_84875_()}));
         }
      }
   }
}
