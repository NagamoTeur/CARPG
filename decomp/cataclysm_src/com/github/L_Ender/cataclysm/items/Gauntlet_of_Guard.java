package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
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
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ToolActions;

public class Gauntlet_of_Guard extends Item implements More_Tool_Attribute {
   private final Multimap<Attribute, AttributeModifier> guantletAttributes;

   public Gauntlet_of_Guard(Properties group) {
      super(group);
      Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
      builder.put(Attributes.f_22281_, new AttributeModifier(f_41374_, "Tool modifier", 10.0, Operation.ADDITION));
      builder.put(Attributes.f_22283_, new AttributeModifier(f_41375_, "Tool modifier", -2.4F, Operation.ADDITION));
      builder.put(Attributes.f_22284_, new AttributeModifier(BASE_ARMOR_ID, "Tool modifier", 3.0, Operation.ADDITION));
      builder.put(Attributes.f_22285_, new AttributeModifier(BASE_ARMOR_TOUGHNESS_ID, "Tool modifier", 3.0, Operation.ADDITION));
      builder.put(Attributes.f_22278_, new AttributeModifier(BASE_KNOCKBACK_RESISTANCE_ID, "Tool modifier", 0.15F, Operation.ADDITION));
      this.guantletAttributes = builder.build();
   }

   public UseAnim m_6164_(ItemStack p_77661_1_) {
      return UseAnim.BOW;
   }

   public int m_8105_(ItemStack p_77626_1_) {
      return 72000;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level p_77659_1_, Player p_77659_2_, InteractionHand p_77659_3_) {
      ItemStack item = p_77659_2_.m_21120_(p_77659_3_);
      InteractionHand otherhand = p_77659_3_ == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
      ItemStack otheritem = p_77659_2_.m_21120_(otherhand);
      if (otheritem.canPerformAction(ToolActions.SHIELD_BLOCK) && !p_77659_2_.m_36335_().m_41519_(otheritem.m_41720_())) {
         return InteractionResultHolder.m_19100_(item);
      } else {
         p_77659_2_.m_6672_(p_77659_3_);
         return InteractionResultHolder.m_19096_(item);
      }
   }

   public void m_5929_(Level worldIn, LivingEntity livingEntityIn, ItemStack stack, int count) {
      double radius = 11.0;
      Level world = livingEntityIn.f_19853_;

      for (LivingEntity entity : world.m_45976_(LivingEntity.class, livingEntityIn.m_20191_().m_82400_(radius))) {
         if (!(entity instanceof Player) || !((Player)entity).m_150110_().f_35934_) {
            Vec3 diff = entity.m_20182_().m_82546_(livingEntityIn.m_20182_().m_82520_(0.0, 0.0, 0.0));
            diff = diff.m_82541_().m_82490_(0.1);
            entity.m_20256_(entity.m_20184_().m_82546_(diff));
         }
      }

      if (world.f_46443_) {
         for (int i = 0; i < 3; i++) {
            int j = world.f_46441_.m_188503_(2) * 2 - 1;
            int k = world.f_46441_.m_188503_(2) * 2 - 1;
            double d0 = livingEntityIn.m_20185_() + 0.25 * (double)j;
            double d1 = (double)((float)livingEntityIn.m_20186_() + world.f_46441_.m_188501_());
            double d2 = livingEntityIn.m_20189_() + 0.25 * (double)k;
            double d3 = (double)(world.f_46441_.m_188501_() * (float)j);
            double d4 = ((double)world.f_46441_.m_188501_() - 0.5) * 0.125;
            double d5 = (double)(world.f_46441_.m_188501_() * (float)k);
            world.m_7106_(ParticleTypes.f_123760_, d0, d1, d2, d3, d4, d5);
         }
      }
   }

   public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
      return true;
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

   public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
      return super.canApplyAtEnchantingTable(stack, enchantment)
         || enchantment.f_44672_ != EnchantmentCategory.BREAKABLE && enchantment.f_44672_ == EnchantmentCategory.WEAPON && enchantment != Enchantments.f_44983_;
   }

   public Multimap<Attribute, AttributeModifier> m_7167_(EquipmentSlot equipmentSlot) {
      return equipmentSlot == EquipmentSlot.MAINHAND ? this.guantletAttributes : super.m_7167_(equipmentSlot);
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept((IClientItemExtensions)Cataclysm.PROXY.getISTERProperties());
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.cataclysm.gauntlet_of_guard.desc").m_130940_(ChatFormatting.DARK_GREEN));
   }
}
