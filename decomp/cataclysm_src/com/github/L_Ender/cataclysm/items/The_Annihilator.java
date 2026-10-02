package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.client.particle.RingParticle;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
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
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class The_Annihilator extends Item {
   private final Multimap<Attribute, AttributeModifier> annihilator;

   public The_Annihilator(Properties group) {
      super(group);
      Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
      builder.put(Attributes.f_22281_, new AttributeModifier(f_41374_, "Tool modifier", 6.5, Operation.ADDITION));
      builder.put(Attributes.f_22283_, new AttributeModifier(f_41375_, "Tool modifier", -2.4F, Operation.ADDITION));
      this.annihilator = builder.build();
   }

   public UseAnim m_6164_(ItemStack p_77661_1_) {
      return UseAnim.SPEAR;
   }

   public int m_8105_(ItemStack p_77626_1_) {
      return 72000;
   }

   public void m_5551_(ItemStack p_43394_, Level p_43395_, LivingEntity p_43396_, int p_43397_) {
      if (p_43396_ instanceof Player player) {
         int i = this.m_8105_(p_43394_) - p_43397_;
         if (i >= 40) {
            this.yall(p_43395_, p_43396_);
            if (!p_43395_.f_46443_) {
               player.m_36335_().m_41524_(this, 100);
            }
         }
      }
   }

   public void m_5929_(Level worldIn, LivingEntity livingEntityIn, ItemStack stack, int count) {
      int i = this.m_8105_(stack) - count;
      if (i == 10) {
         this.masseffectParticle(worldIn, livingEntityIn, 2.0F);
      }

      if (i == 20) {
         this.masseffectParticle(worldIn, livingEntityIn, 3.5F);
      }

      if (i == 30) {
         this.masseffectParticle(worldIn, livingEntityIn, 5.0F);
      }

      if (i == 40) {
         livingEntityIn.m_5496_((SoundEvent)ModSounds.MALEDICTUS_SHORT_ROAR.get(), 1.0F, 1.0F);
      }
   }

   private void yall(Level world, LivingEntity caster) {
      double radius = 6.0;
      ScreenShake_Entity.ScreenShake(world, caster.m_20182_(), 30.0F, 0.1F, 0, 30);
      world.m_6263_(
         null,
         caster.m_20185_(),
         caster.m_20186_(),
         caster.m_20189_(),
         SoundEvents.f_11913_,
         SoundSource.PLAYERS,
         1.5F,
         1.0F / (caster.m_217043_().m_188501_() * 0.4F + 0.8F)
      );

      for (Entity entity : world.m_45933_(caster, caster.m_20191_().m_82377_(radius, radius, radius))) {
         if (entity instanceof LivingEntity) {
            entity.m_6469_(DamageSource.m_19370_(caster), (float)caster.m_21133_(Attributes.f_22281_) * 2.0F);
         }
      }

      if (world.f_46443_) {
         world.m_7106_(
            new RingParticle.RingData(0.0F, (float) (Math.PI / 2), 30, 0.337F, 0.925F, 0.8F, 1.0F, 85.0F, false, RingParticle.EnumRingBehavior.GROW),
            caster.m_20185_(),
            caster.m_20186_() + 0.03F,
            caster.m_20189_(),
            0.0,
            0.0,
            0.0
         );
      }
   }

   private void masseffectParticle(Level world, LivingEntity caster, float radius) {
      if (world.f_46443_) {
         for (int j = 0; j < 70; j++) {
            float angle = (float)(Math.random() * 2.0 * Math.PI);
            double distance = Math.sqrt(Math.random()) * (double)radius;
            double extraX = caster.m_20185_() + distance * (double)Mth.m_14089_(angle);
            double extraY = caster.m_20186_() + 0.3F;
            double extraZ = caster.m_20189_() + distance * (double)Mth.m_14031_(angle);
            world.m_7106_((ParticleOptions)ModParticle.PHANTOM_WING_FLAME.get(), extraX, extraY, extraZ, 0.0, world.f_46441_.m_188583_() * 0.04, 0.0);
         }
      }
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      ItemStack otherHand = hand == InteractionHand.MAIN_HAND ? player.m_21120_(InteractionHand.OFF_HAND) : player.m_21120_(InteractionHand.MAIN_HAND);
      if (otherHand.m_150930_((Item)ModItems.THE_ANNIHILATOR.get())) {
         player.m_6672_(hand);
         return InteractionResultHolder.m_19096_(itemstack);
      } else {
         return InteractionResultHolder.m_19100_(itemstack);
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
      return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment != Enchantments.f_44983_ && enchantment.f_44672_ == EnchantmentCategory.WEAPON;
   }

   public Multimap<Attribute, AttributeModifier> m_7167_(EquipmentSlot equipmentSlot) {
      return equipmentSlot == EquipmentSlot.MAINHAND ? this.annihilator : super.m_7167_(equipmentSlot);
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept((IClientItemExtensions)Cataclysm.PROXY.getISTERProperties());
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.cataclysm.annihilator.desc").m_130940_(ChatFormatting.DARK_GREEN));
      tooltip.add(Component.m_237115_("item.cataclysm.annihilator2.desc").m_130940_(ChatFormatting.DARK_GREEN));
   }
}
