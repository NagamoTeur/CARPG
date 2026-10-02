package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.capabilities.HookCapability;
import com.github.L_Ender.cataclysm.capabilities.TidalTentacleCapability;
import com.github.L_Ender.cataclysm.entity.projectile.Tidal_Hook_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Tidal_Tentacle_Entity;
import com.github.L_Ender.cataclysm.entity.util.TidalTentacleUtil;
import com.github.L_Ender.cataclysm.init.ModCapabilities;
import com.github.L_Ender.cataclysm.init.ModEntities;
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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class Tidal_Claws extends Item implements ILeftClick {
   private final Multimap<Attribute, AttributeModifier> ClawsAttributes;

   public Tidal_Claws(Properties group) {
      super(group);
      Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
      builder.put(Attributes.f_22281_, new AttributeModifier(f_41374_, "Tool modifier", 7.0, Operation.ADDITION));
      builder.put(Attributes.f_22283_, new AttributeModifier(f_41375_, "Tool modifier", -2.4F, Operation.ADDITION));
      this.ClawsAttributes = builder.build();
   }

   public UseAnim m_6164_(ItemStack p_77661_1_) {
      return UseAnim.BOW;
   }

   public int m_8105_(ItemStack p_77626_1_) {
      return 72000;
   }

   public boolean m_7579_(ItemStack stack, LivingEntity entity, LivingEntity player) {
      this.launchTendonsAt(stack, player, entity);
      return super.m_7579_(stack, entity, player);
   }

   private boolean isCharged(Player player, ItemStack stack) {
      return player.m_36403_(0.5F) > 0.9F;
   }

   @Override
   public boolean onLeftClick(ItemStack stack, LivingEntity playerIn) {
      if (stack.m_150930_((Item)ModItems.TIDAL_CLAWS.get()) && (!(playerIn instanceof Player) || this.isCharged((Player)playerIn, stack))) {
         Level worldIn = playerIn.f_19853_;
         Entity closestValid = null;
         Vec3 playerEyes = playerIn.m_20299_(1.0F);
         HitResult hitresult = worldIn.m_45547_(
            new ClipContext(playerEyes, playerEyes.m_82549_(playerIn.m_20154_().m_82490_(16.0)), Block.VISUAL, Fluid.NONE, playerIn)
         );
         if (hitresult instanceof EntityHitResult) {
            Entity entity = ((EntityHitResult)hitresult).m_82443_();
            if (!entity.equals(playerIn) && !playerIn.m_7307_(entity) && !entity.m_7307_(playerIn) && entity instanceof Mob && playerIn.m_142582_(entity)) {
               closestValid = entity;
            }
         } else {
            for (Entity entity : worldIn.m_45976_(LivingEntity.class, playerIn.m_20191_().m_82400_(16.0))) {
               if (!entity.equals(playerIn)
                  && !playerIn.m_7307_(entity)
                  && !entity.m_7307_(playerIn)
                  && entity instanceof Mob
                  && playerIn.m_142582_(entity)
                  && (closestValid == null || playerIn.m_20270_(entity) < playerIn.m_20270_(closestValid))) {
                  closestValid = entity;
               }
            }
         }

         return this.launchTendonsAt(stack, playerIn, closestValid);
      } else {
         return false;
      }
   }

   public boolean launchTendonsAt(ItemStack stack, LivingEntity playerIn, Entity closestValid) {
      Level worldIn = playerIn.f_19853_;
      TidalTentacleCapability.ITentacleCapability tentacleCapability = ModCapabilities.getCapability(playerIn, ModCapabilities.TENTACLE_CAPABILITY);
      if (tentacleCapability != null && TidalTentacleUtil.canLaunchTentacles(worldIn, playerIn)) {
         TidalTentacleUtil.retractFarTentacles(worldIn, playerIn);
         if (!worldIn.f_46443_ && closestValid != null) {
            Tidal_Tentacle_Entity segment = (Tidal_Tentacle_Entity)((EntityType)ModEntities.TIDAL_TENTACLE.get()).m_20615_(worldIn);
            segment.m_20359_(playerIn);
            worldIn.m_7967_(segment);
            segment.setCreatorEntityUUID(playerIn.m_20148_());
            segment.setFromEntityID(playerIn.m_19879_());
            segment.setToEntityID(closestValid.m_19879_());
            segment.m_20359_(playerIn);
            segment.setProgress(0.0F);
            TidalTentacleUtil.setLastTentacle(playerIn, segment);
            return true;
         }
      }

      return false;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level level, Player user, InteractionHand hand) {
      ItemStack stack = user.m_21120_(hand);
      HookCapability.IHookCapability hookCapability = ModCapabilities.getCapability(user, ModCapabilities.HOOK_CAPABILITY);
      if (hookCapability != null) {
         if (!level.f_46443_ && !hookCapability.hasHook()) {
            double maxRange = 30.0;
            double maxSpeed = 12.0;
            Tidal_Hook_Entity hookshot = new Tidal_Hook_Entity((EntityType<? extends AbstractArrow>)ModEntities.TIDAL_HOOK.get(), user, level);
            hookshot.setProperties(stack, maxRange, maxSpeed, user.m_146909_(), user.m_146908_(), 0.0F, 1.5F * (float)(maxSpeed / 10.0));
            level.m_7967_(hookshot);
         }

         user.m_6672_(hand);
         hookCapability.setHasHook(true);
      }

      return super.m_7203_(level, user, hand);
   }

   public ItemStack m_5922_(ItemStack p_40712_, Level p_40713_, LivingEntity p_40714_) {
      HookCapability.IHookCapability hookCapability = ModCapabilities.getCapability(p_40714_, ModCapabilities.HOOK_CAPABILITY);
      if (hookCapability != null) {
         hookCapability.setHasHook(false);
      }

      return super.m_5922_(p_40712_, p_40713_, p_40714_);
   }

   public void m_5551_(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
      HookCapability.IHookCapability hookCapability = ModCapabilities.getCapability(user, ModCapabilities.HOOK_CAPABILITY);
      if (hookCapability != null) {
         hookCapability.setHasHook(false);
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

   public Multimap<Attribute, AttributeModifier> m_7167_(EquipmentSlot equipmentSlot) {
      return equipmentSlot == EquipmentSlot.MAINHAND ? this.ClawsAttributes : super.m_7167_(equipmentSlot);
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept((IClientItemExtensions)Cataclysm.PROXY.getISTERProperties());
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.cataclysm.tidal_claws.desc").m_130940_(ChatFormatting.DARK_GREEN));
      tooltip.add(Component.m_237115_("item.cataclysm.tidal_claws.desc2").m_130940_(ChatFormatting.DARK_GREEN));
   }
}
