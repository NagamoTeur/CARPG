package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.effect.Flame_Strike_Entity;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.init.ModSounds;
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
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class The_Incinerator extends Item implements More_Tool_Attribute {
   private final Multimap<Attribute, AttributeModifier> incineratorAttributes;

   public The_Incinerator(Properties group) {
      super(group);
      Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
      builder.put(Attributes.f_22281_, new AttributeModifier(f_41374_, "Tool modifier", 13.0, Operation.ADDITION));
      builder.put(Attributes.f_22283_, new AttributeModifier(f_41375_, "Tool modifier", -2.7F, Operation.ADDITION));
      builder.put((Attribute)ForgeMod.ATTACK_RANGE.get(), new AttributeModifier(BASE_ENTITY_INTERACTION_RANGE_ID, "Tool modifier", 2.0, Operation.ADDITION));
      this.incineratorAttributes = builder.build();
   }

   public UseAnim m_6164_(ItemStack p_77661_1_) {
      return UseAnim.BOW;
   }

   public int m_8105_(ItemStack p_77626_1_) {
      return 72000;
   }

   public void m_5551_(ItemStack p_43394_, Level p_43395_, LivingEntity p_43396_, int p_43397_) {
      if (p_43396_ instanceof Player player) {
         int i = this.m_8105_(p_43394_) - p_43397_;
         double headY = player.m_20186_() + 1.0;
         int standingOnY = Mth.m_14107_(player.m_20186_()) - 2;
         float yawRadians = (float)Math.toRadians((double)(90.0F + player.m_146908_()));
         boolean hasSucceeded = false;
         if (i >= 60) {
            for (int l = 0; l < 10; l++) {
               double d2 = 2.25 * (double)(l + 1);
               int j2 = (int)(1.5F * (float)l);
               if (this.spawnFlameStrike(
                  player.m_20185_() + (double)Mth.m_14089_(yawRadians) * d2,
                  player.m_20189_() + (double)Mth.m_14031_(yawRadians) * d2,
                  (double)standingOnY,
                  headY,
                  yawRadians,
                  40,
                  j2,
                  j2,
                  p_43395_,
                  1.0F,
                  player
               )) {
                  hasSucceeded = true;
               }
            }

            if (hasSucceeded) {
               if (!p_43395_.f_46443_) {
                  player.m_36335_().m_41524_(this, CMConfig.TheIncineratorCooldown);
               }

               ScreenShake_Entity.ScreenShake(p_43395_, player.m_20182_(), 30.0F, 0.15F, 0, 30);
               player.m_5496_((SoundEvent)ModSounds.SWORD_STOMP.get(), 1.0F, 1.0F);
            }
         }
      }
   }

   public void m_5929_(Level worldIn, LivingEntity livingEntityIn, ItemStack stack, int count) {
      int i = this.m_8105_(stack) - count;
      if (i == 60) {
         livingEntityIn.m_5496_((SoundEvent)ModSounds.FLAME_BURST.get(), 1.0F, 1.0F);
      }
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

   private boolean spawnFlameStrike(
      double x, double z, double minY, double maxY, float rotation, int duration, int wait, int delay, Level world, float radius, LivingEntity player
   ) {
      BlockPos blockpos = new BlockPos(x, maxY, z);
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
      } while ((double)blockpos.m_123342_() >= minY);

      if (flag) {
         world.m_7967_(
            new Flame_Strike_Entity(world, x, (double)blockpos.m_123342_() + d0, z, rotation, duration, wait, delay, radius, 6.0F, 2.0F, false, player)
         );
         return true;
      } else {
         return false;
      }
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
      return super.canApplyAtEnchantingTable(stack, enchantment)
         || enchantment.f_44672_ != EnchantmentCategory.BREAKABLE && enchantment.f_44672_ == EnchantmentCategory.WEAPON;
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
      tooltip.add(Component.m_237115_("item.cataclysm.incinerator.desc").m_130940_(ChatFormatting.DARK_GREEN));
      tooltip.add(Component.m_237115_("item.cataclysm.incinerator2.desc").m_130940_(ChatFormatting.DARK_GREEN));
      tooltip.add(Component.m_237115_("item.cataclysm.incinerator3.desc").m_130940_(ChatFormatting.DARK_GREEN));
   }
}
