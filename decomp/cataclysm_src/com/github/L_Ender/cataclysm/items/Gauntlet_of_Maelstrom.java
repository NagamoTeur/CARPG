package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.effect.Void_Vortex_Entity;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ToolActions;

public class Gauntlet_of_Maelstrom extends Item implements More_Tool_Attribute {
   private final Multimap<Attribute, AttributeModifier> guantletAttributes;

   public Gauntlet_of_Maelstrom(Properties group) {
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

   public void m_5551_(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
      if (entityLiving instanceof Player player) {
         int standingOnY = Mth.m_14107_(entityLiving.m_20186_()) - 10;
         boolean hasSucceeded = false;
         float yawRadians = (float)Math.toRadians((double)(90.0F + player.m_146908_()));
         HitResult result = player.m_19907_(32.0, 1.0F, true);
         if (result.m_6662_() == Type.BLOCK && !level.f_46443_) {
            BlockPos startPos = ((BlockHitResult)result).m_82425_();
            if (this.spawnVortex(
               (double)startPos.m_123341_() + 0.5,
               (double)startPos.m_123342_(),
               (double)startPos.m_123343_() + 0.5,
               standingOnY,
               yawRadians,
               level,
               entityLiving
            )) {
               hasSucceeded = true;
            }

            if (hasSucceeded) {
               player.m_36335_().m_41524_(this, CMConfig.GauntletOfMaelstromCooldown);
               player.m_36246_(Stats.f_12982_.m_12902_(this));
            }
         }
      }
   }

   private boolean spawnVortex(double x, double y, double z, int lowestYCheck, float rotation, Level world, LivingEntity player) {
      BlockPos blockpos = new BlockPos(x, y, z);
      boolean flag = false;
      double d0 = 0.0;

      do {
         BlockPos blockpos1 = blockpos.m_7495_();
         BlockState blockstate = world.m_8055_(blockpos1);
         if (blockstate.m_60783_(world, blockpos1, Direction.UP)) {
            if (!world.m_46859_(blockpos)) {
               BlockState blockstate1 = world.m_8055_(blockpos);
               VoxelShape voxelshape = blockstate1.m_60812_(world, blockpos);
               if (!voxelshape.m_83281_()) {
                  d0 = voxelshape.m_83297_(Axis.Y);
               }
            }

            flag = true;
            break;
         }

         blockpos = blockpos.m_7495_();
      } while (blockpos.m_123342_() >= lowestYCheck);

      if (flag) {
         world.m_7967_(new Void_Vortex_Entity(world, x, (double)blockpos.m_123342_() + d0, z, rotation, player, 150));
         return true;
      } else {
         return false;
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
      tooltip.add(Component.m_237115_("item.cataclysm.gauntlet_of_maelstrom.desc").m_130940_(ChatFormatting.DARK_GREEN));
   }
}
