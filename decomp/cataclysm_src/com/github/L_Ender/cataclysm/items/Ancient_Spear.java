package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.entity.projectile.Sandstorm_Projectile;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class Ancient_Spear extends Item implements ILeftClick, Vanishable, More_Tool_Attribute {
   private final Multimap<Attribute, AttributeModifier> incineratorAttributes;

   public Ancient_Spear(Properties group) {
      super(group);
      Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
      builder.put(Attributes.f_22281_, new AttributeModifier(f_41374_, "Tool modifier", 8.5, Operation.ADDITION));
      builder.put(Attributes.f_22283_, new AttributeModifier(f_41375_, "Tool modifier", -2.6F, Operation.ADDITION));
      builder.put((Attribute)ForgeMod.ATTACK_RANGE.get(), new AttributeModifier(BASE_ENTITY_INTERACTION_RANGE_ID, "Tool modifier", 1.5, Operation.ADDITION));
      this.incineratorAttributes = builder.build();
   }

   private boolean isCharged(Player player, ItemStack stack) {
      return player.m_36403_(0.5F) > 0.9F;
   }

   public boolean m_7579_(ItemStack stack, LivingEntity entity, LivingEntity player) {
      if (player instanceof Player player1 && this.isCharged(player1, stack)) {
         this.launchTornado(stack, player);
      }

      stack.m_41622_(1, player, p_43414_ -> p_43414_.m_21166_(EquipmentSlot.MAINHAND));
      return true;
   }

   public boolean m_6832_(ItemStack pickaxe, ItemStack stack) {
      return stack.m_150930_((Item)ModItems.ANCIENT_METAL_INGOT.get());
   }

   public boolean m_6813_(ItemStack p_43399_, Level p_43400_, BlockState p_43401_, BlockPos p_43402_, LivingEntity p_43403_) {
      if ((double)p_43401_.m_60800_(p_43400_, p_43402_) != 0.0) {
         p_43399_.m_41622_(2, p_43403_, p_43385_ -> p_43385_.m_21166_(EquipmentSlot.MAINHAND));
      }

      return true;
   }

   @Override
   public boolean onLeftClick(ItemStack stack, LivingEntity playerIn) {
      return !stack.m_150930_((Item)ModItems.ANCIENT_SPEAR.get())
            || !playerIn.m_21120_(InteractionHand.MAIN_HAND).m_150930_((Item)ModItems.ANCIENT_SPEAR.get())
            || playerIn instanceof Player && !this.isCharged((Player)playerIn, stack)
         ? false
         : this.launchTornado(stack, playerIn);
   }

   public boolean launchTornado(ItemStack stack, LivingEntity playerIn) {
      Level worldIn = playerIn.f_19853_;
      if (!worldIn.f_46443_) {
         stack.m_41622_(1, playerIn, p_43388_ -> p_43388_.m_21190_(playerIn.m_7655_()));
         float d7 = playerIn.m_146908_();
         float d = playerIn.m_146909_();
         float d1 = -Mth.m_14031_(d7 * (float) (Math.PI / 180.0)) * Mth.m_14089_(d * (float) (Math.PI / 180.0));
         float d2 = -Mth.m_14031_(d * (float) (Math.PI / 180.0));
         float d3 = Mth.m_14089_(d7 * (float) (Math.PI / 180.0)) * Mth.m_14089_(d * (float) (Math.PI / 180.0));
         double theta = (double)d7 * (Math.PI / 180.0);
         double vecX = Math.cos(++theta);
         double vecZ = Math.sin(theta);
         double x = playerIn.m_20185_() + vecX;
         double Z = playerIn.m_20189_() + vecZ;
         Sandstorm_Projectile largefireball = new Sandstorm_Projectile(playerIn, (double)d1, (double)d2, (double)d3, playerIn.f_19853_, 6.0F);
         largefireball.setState(1);
         largefireball.m_6034_(x, playerIn.m_20188_() - 0.5, Z);
         worldIn.m_7967_(largefireball);
         return true;
      } else {
         return false;
      }
   }

   public boolean m_8120_(ItemStack stack) {
      return true;
   }

   public int m_6473_() {
      return 16;
   }

   public boolean m_6777_(BlockState state, Level worldIn, BlockPos pos, Player player) {
      return !player.m_7500_();
   }

   public float m_8102_(ItemStack p_43288_, BlockState p_43289_) {
      if (p_43289_.m_60713_(Blocks.f_50033_)) {
         return 15.0F;
      } else {
         Material material = p_43289_.m_60767_();
         return material != Material.f_76300_ && material != Material.f_76302_ && !p_43289_.m_204336_(BlockTags.f_13035_) && material != Material.f_76285_
            ? 1.0F
            : 1.5F;
      }
   }

   public boolean m_8096_(BlockState p_43298_) {
      return p_43298_.m_60713_(Blocks.f_50033_);
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
      return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment.f_44672_ == EnchantmentCategory.WEAPON;
   }

   public Multimap<Attribute, AttributeModifier> m_7167_(EquipmentSlot equipmentSlot) {
      return equipmentSlot == EquipmentSlot.MAINHAND ? this.incineratorAttributes : super.m_7167_(equipmentSlot);
   }

   public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
      return ToolActions.DEFAULT_SWORD_ACTIONS.contains(toolAction);
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept((IClientItemExtensions)Cataclysm.PROXY.getISTERProperties());
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.cataclysm.ancient_spear.desc").m_130940_(ChatFormatting.DARK_GREEN));
   }
}
