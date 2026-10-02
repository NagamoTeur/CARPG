package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
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
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ToolActions;

public class Meat_Shredder extends Item {
   private final Multimap<Attribute, AttributeModifier> whirligigsawAttributes;

   public Meat_Shredder(Properties properties) {
      super(properties);
      Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
      builder.put(Attributes.f_22281_, new AttributeModifier(f_41374_, "Tool modifier", 7.5, Operation.ADDITION));
      builder.put(Attributes.f_22283_, new AttributeModifier(f_41375_, "Tool modifier", -2.6F, Operation.ADDITION));
      this.whirligigsawAttributes = builder.build();
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level p_77659_1_, Player p_77659_2_, InteractionHand p_77659_3_) {
      ItemStack item = p_77659_2_.m_21120_(p_77659_3_);
      InteractionHand otherhand = p_77659_3_ == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
      ItemStack otheritem = p_77659_2_.m_21120_(otherhand);
      if (otheritem.canPerformAction(ToolActions.SHIELD_BLOCK) && !p_77659_2_.m_36335_().m_41519_(otheritem.m_41720_())) {
         return InteractionResultHolder.m_19100_(item);
      } else {
         p_77659_2_.m_6672_(p_77659_3_);
         p_77659_1_.m_6263_(
            null,
            p_77659_2_.m_20185_(),
            p_77659_2_.m_20186_(),
            p_77659_2_.m_20189_(),
            (SoundEvent)ModSounds.SHREDDER_START.get(),
            SoundSource.PLAYERS,
            1.5F,
            1.0F / (p_77659_2_.m_217043_().m_188501_() * 0.4F + 0.8F)
         );
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
      return super.canApplyAtEnchantingTable(stack, enchantment)
         || enchantment.f_44672_ != EnchantmentCategory.BREAKABLE && enchantment.f_44672_ == EnchantmentCategory.WEAPON && enchantment != Enchantments.f_44983_;
   }

   public void m_5929_(Level level, LivingEntity living, ItemStack stack, int count) {
      double range = 2.5;
      Vec3 srcVec = living.m_146892_();
      Vec3 lookVec = living.m_20252_(1.0F);
      Vec3 destVec = srcVec.m_82520_(lookVec.m_7096_() * range, lookVec.m_7098_() * range, lookVec.m_7094_() * range);
      float var9 = 1.0F;
      List<Entity> possibleList = level.m_45933_(
         living,
         living.m_20191_()
            .m_82363_(lookVec.m_7096_() * range, lookVec.m_7098_() * range, lookVec.m_7094_() * range)
            .m_82377_((double)var9, (double)var9, (double)var9)
      );
      boolean flag = false;
      Cataclysm.PROXY.playWorldSound(living, (byte)1);

      for (Entity entity : possibleList) {
         if (entity instanceof LivingEntity) {
            float borderSize = 0.5F;
            AABB collisionBB = entity.m_20191_().m_82377_((double)borderSize, (double)borderSize, (double)borderSize);
            Optional<Vec3> interceptPos = collisionBB.m_82371_(srcVec, destVec);
            if (collisionBB.m_82390_(srcVec)) {
               flag = true;
            } else if (interceptPos.isPresent()) {
               flag = true;
            }

            if (flag) {
               if (entity.m_6469_(CMDamageTypes.causeShredderDamage(living), (float)living.m_21133_(Attributes.f_22281_) / 8.5F)) {
                  int j = EnchantmentHelper.m_44914_(living);
                  if (j > 0 && !entity.m_6060_()) {
                     entity.m_20254_(j * 4);
                  }

                  entity.f_19802_ = 0;
               }

               double d0 = (double)(level.m_213780_().m_188501_() - 0.5F) + entity.m_20184_().f_82479_;
               double d1 = (double)(level.m_213780_().m_188501_() - 0.5F) + entity.m_20184_().f_82480_;
               double d2 = (double)(level.m_213780_().m_188501_() - 0.5F) + entity.m_20184_().f_82481_;
               double dist = (double)(1.0F + level.m_213780_().m_188501_() * 0.2F);
               double d3 = d0 * dist;
               double d4 = d1 * dist;
               double d5 = d2 * dist;
               entity.f_19853_
                  .m_7106_(
                     ParticleTypes.f_123756_,
                     entity.m_20185_(),
                     living.m_20188_() - 0.1 + (entity.m_146892_().f_82480_ - living.m_20188_()),
                     entity.m_20189_(),
                     d3,
                     d4,
                     d5
                  );
            }
         }
      }
   }

   public void m_5551_(ItemStack stack, Level world, LivingEntity living, int remainingUseTicks) {
      world.m_6263_(
         null,
         living.m_20185_(),
         living.m_20186_(),
         living.m_20189_(),
         (SoundEvent)ModSounds.SHREDDER_END.get(),
         SoundSource.PLAYERS,
         1.5F,
         1.0F / (living.m_217043_().m_188501_() * 0.4F + 0.8F)
      );
      Cataclysm.PROXY.clearSoundCacheFor(living);
   }

   public float m_8102_(ItemStack p_41004_, BlockState p_41005_) {
      float speed = 15.0F;
      return p_41005_.m_204336_(BlockTags.f_144280_) ? speed : 1.0F;
   }

   public int m_8105_(ItemStack stack) {
      return 72000;
   }

   public UseAnim m_6164_(ItemStack stack) {
      return UseAnim.BOW;
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept((IClientItemExtensions)Cataclysm.PROXY.getISTERProperties());
   }

   public Multimap<Attribute, AttributeModifier> m_7167_(EquipmentSlot equipmentSlot) {
      return equipmentSlot == EquipmentSlot.MAINHAND ? this.whirligigsawAttributes : super.m_7167_(equipmentSlot);
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.cataclysm.meat_shredder.desc").m_130940_(ChatFormatting.DARK_GREEN));
   }
}
