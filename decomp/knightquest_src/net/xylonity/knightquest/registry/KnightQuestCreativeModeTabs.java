package net.xylonity.knightquest.registry;

import dev.xylonity.knightlib.compat.registry.KnightLibBlocks;
import dev.xylonity.knightlib.compat.registry.KnightLibItems;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public class KnightQuestCreativeModeTabs extends CreativeModeTab {
   private static final String PATH = "textures/gui/container/creative_inventory/";

   public KnightQuestCreativeModeTabs(String label) {
      super(label);
      this.setBackgroundImage(new ResourceLocation("knightquest", "textures/gui/container/creative_inventory/tab_knightquest.png"));
   }

   @NotNull
   public ItemStack m_6976_() {
      return new ItemStack((ItemLike)KnightQuestWeapons.PALADIN_SWORD.get());
   }

   @NotNull
   public Component m_40786_() {
      return Component.m_237115_("itemgroup.knightquest");
   }

   public void m_6151_(NonNullList<ItemStack> pItems) {
      pItems.add(new ItemStack((ItemLike)KnightLibBlocks.GREAT_CHALICE.get()));
      pItems.add(new ItemStack((ItemLike)KnightLibItems.SMALL_ESSENCE.get()));
      pItems.add(new ItemStack((ItemLike)KnightLibItems.GREAT_ESSENCE.get()));
      super.m_6151_(pItems);
   }
}
