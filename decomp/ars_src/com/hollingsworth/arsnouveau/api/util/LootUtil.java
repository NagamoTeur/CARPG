package com.hollingsworth.arsnouveau.api.util;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

public class LootUtil {
   public static ItemStack getDefaultFakeTool() {
      return new ItemStack(Items.f_42390_);
   }

   public static ItemStack getDefaultFakeWeapon() {
      return new ItemStack(Items.f_42388_);
   }

   public static Builder getDefaultContext(ServerLevel serverWorld, BlockPos pos, LivingEntity shooter) {
      return new Builder(serverWorld)
         .m_230911_(serverWorld.f_46441_)
         .m_78972_(LootContextParams.f_81460_, new Vec3((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_()))
         .m_78972_(LootContextParams.f_81455_, shooter)
         .m_78984_(LootContextParams.f_81462_, serverWorld.m_7702_(pos));
   }

   public static Builder getSilkContext(ServerLevel serverWorld, BlockPos pos, LivingEntity shooter) {
      ItemStack stack = getDefaultFakeTool();
      stack.m_41663_(Enchantments.f_44985_, 1);
      return getDefaultContext(serverWorld, pos, shooter).m_78972_(LootContextParams.f_81463_, stack);
   }

   public static Builder getFortuneContext(ServerLevel world, BlockPos pos, LivingEntity shooter, int enchLevel) {
      ItemStack stack = getDefaultFakeTool();
      stack.m_41663_(Enchantments.f_44987_, enchLevel);
      return getDefaultContext(world, pos, shooter).m_78972_(LootContextParams.f_81463_, stack);
   }

   public static Builder getLootingContext(ServerLevel world, LivingEntity player, LivingEntity slainEntity, int looting, DamageSource source) {
      ItemStack stack = getDefaultFakeWeapon();
      stack.m_41663_(Enchantments.f_44982_, looting);
      return new Builder(world)
         .m_230911_(world.f_46441_)
         .m_78972_(LootContextParams.f_81455_, slainEntity)
         .m_78972_(LootContextParams.f_81460_, new Vec3(slainEntity.m_20185_(), slainEntity.m_20186_(), slainEntity.m_20189_()))
         .m_78972_(LootContextParams.f_81456_, ANFakePlayer.getPlayer(world))
         .m_78972_(LootContextParams.f_81457_, source)
         .m_78984_(LootContextParams.f_81458_, source.m_7639_())
         .m_78984_(LootContextParams.f_81459_, source.m_7640_())
         .m_78972_(LootContextParams.f_81458_, player)
         .m_78963_(player instanceof Player ? ((Player)player).m_36336_() : 1.0F)
         .m_78972_(LootContextParams.f_81463_, stack)
         .m_78972_(LootContextParams.f_81464_, 0.0F)
         .m_78972_(LootContextParams.f_81461_, Blocks.f_50016_.m_49966_())
         .m_78984_(LootContextParams.f_81462_, null);
   }
}
