package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.capabilities.RenderRushCapability;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.projectile.Phantom_Halberd_Entity;
import com.github.L_Ender.cataclysm.init.ModCapabilities;
import com.github.L_Ender.cataclysm.init.ModParticle;
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
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.ToolActions;

public class Soul_Render extends Item implements More_Tool_Attribute {
   private final Multimap<Attribute, AttributeModifier> whirligigsawAttributes;

   public Soul_Render(Properties properties) {
      super(properties);
      Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
      builder.put(Attributes.f_22281_, new AttributeModifier(f_41374_, "Tool modifier", 14.0, Operation.ADDITION));
      builder.put(Attributes.f_22283_, new AttributeModifier(f_41375_, "Tool modifier", -2.9F, Operation.ADDITION));
      builder.put((Attribute)ForgeMod.ATTACK_RANGE.get(), new AttributeModifier(BASE_ENTITY_INTERACTION_RANGE_ID, "Tool modifier", 2.0, Operation.ADDITION));
      builder.put((Attribute)ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(BASE_BLOCK_INTERACTION_RANGE_ID, "Tool modifier", 2.0, Operation.ADDITION));
      this.whirligigsawAttributes = builder.build();
   }

   public void m_5551_(ItemStack stack, Level level, LivingEntity livingEntity, int timeLeft) {
      boolean hasSucceeded = false;
      if (livingEntity instanceof Player player) {
         int i = this.m_8105_(stack) - timeLeft;
         if (livingEntity.m_6144_()) {
            this.StrikeWindmillHalberd(level, player, 7, 5, 1.0, 1.0, 0.2, 1);
            if (!level.f_46443_) {
               player.m_36335_().m_41524_(this, CMConfig.SoulRenderCooldown);
            }
         } else {
            int t = Mth.m_14045_(i, 0, 60);
            if (t > 0) {
               float f = 0.1F * (float)t;
               Vec3 vec3 = player.m_20184_().m_82549_(player.m_20252_(1.0F).m_82541_().m_82542_((double)f, (double)(f * 0.15F), (double)f));
               livingEntity.m_20256_(vec3.m_82520_(0.0, (double)(livingEntity.m_20096_() ? 0.2F : 0.0F), 0.0));
               RenderRushCapability.IRenderRushCapability ChargeCapability = ModCapabilities.getCapability(livingEntity, ModCapabilities.RENDER_RUSH_CAPABILITY);
               if (ChargeCapability != null) {
                  ChargeCapability.setRush(true);
                  ChargeCapability.setTimer(t / 2);
                  ChargeCapability.setdamage((float)player.m_21133_(Attributes.f_22281_));
                  hasSucceeded = true;
               }

               if (!level.f_46443_ && hasSucceeded) {
                  player.m_36335_().m_41524_(this, CMConfig.SoulRenderCooldown);
               }
            }
         }
      }
   }

   private void StrikeWindmillHalberd(
      Level level,
      LivingEntity player,
      int numberOfBranches,
      int particlesPerBranch,
      double initialRadius,
      double radiusIncrement,
      double curveFactor,
      int delay
   ) {
      float angleIncrement = (float)((Math.PI * 2) / (double)numberOfBranches);

      for (int branch = 0; branch < numberOfBranches; branch++) {
         float baseAngle = angleIncrement * (float)branch;

         for (int i = 0; i < particlesPerBranch; i++) {
            double currentRadius = initialRadius + (double)i * radiusIncrement;
            float currentAngle = (float)((double)baseAngle + (double)((float)i * angleIncrement) / initialRadius + (double)((float)((double)i * curveFactor)));
            double xOffset = currentRadius * Math.cos((double)currentAngle);
            double zOffset = currentRadius * Math.sin((double)currentAngle);
            double spawnX = player.m_20185_() + xOffset;
            double spawnY = player.m_20186_() + 0.3;
            double spawnZ = player.m_20189_() + zOffset;
            int d3 = delay * (i + 1);
            double deltaX = level.m_213780_().m_188583_() * 0.007;
            double deltaY = level.m_213780_().m_188583_() * 0.007;
            double deltaZ = level.m_213780_().m_188583_() * 0.007;
            if (level.f_46443_) {
               level.m_7106_((ParticleOptions)ModParticle.PHANTOM_WING_FLAME.get(), spawnX, spawnY, spawnZ, deltaX, deltaY, deltaZ);
            }

            this.spawnHalberd(spawnX, spawnZ, player.m_20186_() - 5.0, player.m_20186_() + 3.0, currentAngle, d3, level, player);
         }
      }
   }

   private void spawnHalberd(double x, double z, double minY, double maxY, float rotation, int delay, Level world, LivingEntity player) {
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
      } while (blockpos.m_123342_() >= Mth.m_14107_(minY) - 1);

      if (flag) {
         world.m_7967_(
            new Phantom_Halberd_Entity(world, x, (double)blockpos.m_123342_() + d0, z, rotation, delay, player, (float)CMConfig.PhantomHalberddamage)
         );
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
      return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment.f_44672_ == EnchantmentCategory.WEAPON && enchantment != Enchantments.f_44983_;
   }

   public float m_8102_(ItemStack p_41004_, BlockState p_41005_) {
      float speed = 15.0F;
      return p_41005_.m_204336_(BlockTags.f_144280_) ? speed : 1.0F;
   }

   public static float getPowerForTime(int i) {
      float f = (float)i / (float)getMaxLoadTime();
      f = (f * f + f * 2.0F) / 3.0F;
      if (f > 1.0F) {
         f = 1.0F;
      }

      return f;
   }

   private static int getMaxLoadTime() {
      return 20;
   }

   public int m_8105_(ItemStack stack) {
      return 72000;
   }

   public UseAnim m_6164_(ItemStack stack) {
      return UseAnim.BLOCK;
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept((IClientItemExtensions)Cataclysm.PROXY.getISTERProperties());
   }

   public Multimap<Attribute, AttributeModifier> m_7167_(EquipmentSlot equipmentSlot) {
      return equipmentSlot == EquipmentSlot.MAINHAND ? this.whirligigsawAttributes : super.m_7167_(equipmentSlot);
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.cataclysm.soul_render.desc").m_130940_(ChatFormatting.DARK_GREEN));
      tooltip.add(Component.m_237115_("item.cataclysm.soul_render2.desc").m_130940_(ChatFormatting.DARK_GREEN));
   }
}
