package com.aizistral.enigmaticlegacy.items.generic;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import java.util.Random;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;

public abstract class ItemBase extends Item {
   protected static final Random random = new Random();
   protected boolean isPlaceholder = false;

   public ItemBase() {
      this(getDefaultProperties());
   }

   public ItemBase(Properties props) {
      super(props);
   }

   public void m_7836_(ItemStack stack, Level worldIn, Player playerIn) {
   }

   public static Properties getDefaultProperties() {
      Properties props = new Properties();
      props.m_41491_(EnigmaticLegacy.MAIN_TAB);
      props.m_41487_(64);
      props.m_41497_(Rarity.COMMON);
      return props;
   }

   public static BlockHitResult rayTrace(Level worldIn, Player player, Fluid fluidMode) {
      return Item.m_41435_(worldIn, player, fluidMode);
   }

   public Item setPlaceholder() {
      this.isPlaceholder = true;
      return this;
   }

   public boolean isPlaceholder() {
      return this.isPlaceholder;
   }

   public Component m_7626_(ItemStack stack) {
      Component superName = super.m_7626_(stack);
      return (Component)(this.isPlaceholder && superName instanceof MutableComponent
         ? ((MutableComponent)superName).m_130940_(ChatFormatting.OBFUSCATED)
         : superName);
   }

   protected static String minimizeNumber(double num) {
      return SuperpositionHandler.minimizeNumber(num);
   }
}
