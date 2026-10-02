package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.capabilities.ChargeCapability;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.init.ModCapabilities;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModSounds;
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
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
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

public class Gauntlet_of_Bulwark extends Item implements More_Tool_Attribute {
   private final Multimap<Attribute, AttributeModifier> guantletAttributes;

   public Gauntlet_of_Bulwark(Properties group) {
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
      double radius = 4.5;
      Level world = livingEntityIn.f_19853_;
      List<Entity> list = world.m_45933_(livingEntityIn, livingEntityIn.m_20191_().m_82400_(radius));
      int c = this.m_8105_(stack) - count;
      if (c == 20) {
         livingEntityIn.m_5496_((SoundEvent)ModSounds.FLAME_BURST.get(), 1.0F, 1.0F);

         for (Entity entity : list) {
            if (entity instanceof LivingEntity && (!(entity instanceof Player) || !((Player)entity).m_150110_().f_35934_)) {
               ((LivingEntity)entity).m_7292_(new MobEffectInstance((MobEffect)ModEffect.EFFECTBLAZING_BRAND.get(), 40));
               if (entity.m_20096_()) {
                  double d0 = entity.m_20185_() - livingEntityIn.m_20185_();
                  double d1 = entity.m_20189_() - livingEntityIn.m_20189_();
                  double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
                  float f = 1.5F;
                  entity.m_5997_(d0 / d2 * (double)f, 0.1F, d1 / d2 * (double)f);
               }
            }
         }

         if (world.f_46443_) {
            for (int i = 0; i < 20; i++) {
               float velocity = 0.2F;
               float yaw = (float)i * (float) (Math.PI / 10);
               float vy = world.m_213780_().m_188501_() * 0.1F - 0.05F;
               float vx = 0.2F * Mth.m_14089_(yaw);
               float vz = 0.2F * Mth.m_14031_(yaw);
               world.m_7106_(
                  ParticleTypes.f_123744_,
                  livingEntityIn.m_20185_(),
                  livingEntityIn.m_20186_() + 1.0,
                  livingEntityIn.m_20189_(),
                  (double)vx,
                  (double)vy,
                  (double)vz
               );
            }
         }
      }
   }

   public void m_5551_(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
      if (!entityLiving.m_6144_() && !entityLiving.m_21255_()) {
         int i = this.m_8105_(stack) - timeLeft;
         int t = Mth.m_14045_(i, 1, 5);
         float f7 = entityLiving.m_146908_();
         float f = entityLiving.m_146909_();
         if (i >= 20) {
            float f1 = -Mth.m_14031_(f7 * (float) (Math.PI / 180.0)) * Mth.m_14089_(f * (float) (Math.PI / 180.0));
            float f2 = -Mth.m_14031_(f * (float) (Math.PI / 180.0));
            float f3 = Mth.m_14089_(f7 * (float) (Math.PI / 180.0)) * Mth.m_14089_(f * (float) (Math.PI / 180.0));
            float f4 = Mth.m_14116_(f1 * f1 + f2 * f2 + f3 * f3);
            float f5 = 3.0F * ((float)t / 6.0F);
            f1 *= f5 / f4;
            f3 *= f5 / f4;
            entityLiving.m_5997_((double)f1, 0.0, (double)f3);
            if (entityLiving.m_20096_()) {
               float f6 = 1.1999999F;
               entityLiving.m_6478_(MoverType.SELF, new Vec3(0.0, (double)f6 / 2.0, 0.0));
            }

            ChargeCapability.IChargeCapability ChargeCapability = ModCapabilities.getCapability(entityLiving, ModCapabilities.CHARGE_CAPABILITY);
            if (ChargeCapability != null) {
               ChargeCapability.setCharge(true);
               ChargeCapability.setTimer(t * 2);
               ChargeCapability.seteffectiveChargeTime(t * 2);
               ChargeCapability.setknockbackSpeedIndex((float)t * 0.35F);
               ChargeCapability.setdamagePerEffectiveCharge(1.2F);
               ChargeCapability.setdx(f1 * 0.5F);
               ChargeCapability.setdZ(f3 * 0.5F);
            }

            if (!level.f_46443_) {
               ((Player)entityLiving).m_36335_().m_41524_(this, CMConfig.GauntletOfBulwarkCooldown);
            }
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
      tooltip.add(Component.m_237115_("item.cataclysm.gauntlet_of_bulwark.desc").m_130940_(ChatFormatting.DARK_GREEN));
      tooltip.add(Component.m_237115_("item.cataclysm.gauntlet_of_bulwark.desc2").m_130940_(ChatFormatting.DARK_GREEN));
   }
}
