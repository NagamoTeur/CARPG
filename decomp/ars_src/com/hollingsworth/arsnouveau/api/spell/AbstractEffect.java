package com.hollingsworth.arsnouveau.api.spell;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.api.entity.ISummon;
import com.hollingsworth.arsnouveau.api.event.SpellDamageEvent;
import com.hollingsworth.arsnouveau.api.event.SummonEvent;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.LivingCaster;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.api.util.LootUtil;
import com.hollingsworth.arsnouveau.common.items.VoidJar;
import com.hollingsworth.arsnouveau.common.potions.ModPotions;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDurationDown;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentExtendTime;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentExtract;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentFortune;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.common.util.FakePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractEffect extends AbstractSpellPart {
   public DoubleValue DAMAGE;
   public DoubleValue AMP_VALUE;
   public IntValue POTION_TIME;
   public IntValue EXTEND_TIME;
   public IntValue GENERIC_INT;
   public DoubleValue GENERIC_DOUBLE;

   public AbstractEffect(String tag, String description) {
      super(tag, description);
   }

   public AbstractEffect(ResourceLocation tag, String description) {
      super(tag, description);
   }

   @Override
   public Integer getTypeIndex() {
      return 10;
   }

   public void onResolve(
      HitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      if (rayTraceResult instanceof BlockHitResult blockHitResult) {
         this.onResolveBlock(blockHitResult, world, shooter, spellStats, spellContext, resolver);
      } else if (rayTraceResult instanceof EntityHitResult entityHitResult) {
         this.onResolveEntity(entityHitResult, world, shooter, spellStats, spellContext, resolver);
      }
   }

   public void onResolveEntity(
      EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
   }

   public void onResolveBlock(
      BlockHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
   }

   @Deprecated(
      forRemoval = true
   )
   public void applyConfigPotion(LivingEntity entity, MobEffect potionEffect, SpellStats spellStats) {
      this.applyConfigPotion(entity, potionEffect, spellStats, true);
   }

   @Deprecated(
      forRemoval = true
   )
   public void applyConfigPotion(LivingEntity entity, MobEffect potionEffect, SpellStats spellStats, boolean showParticles) {
      this.applyPotion(
         entity,
         potionEffect,
         spellStats,
         this.POTION_TIME == null ? 30 : (Integer)this.POTION_TIME.get(),
         this.EXTEND_TIME == null ? 8 : (Integer)this.EXTEND_TIME.get(),
         showParticles
      );
   }

   @Deprecated(
      forRemoval = true
   )
   public void applyPotion(
      LivingEntity entity, MobEffect potionEffect, SpellStats stats, int baseDurationSeconds, int durationBuffSeconds, boolean showParticles
   ) {
      if (entity != null) {
         int ticks = baseDurationSeconds * 20 + durationBuffSeconds * stats.getDurationInTicks();
         int amp = (int)stats.getAmpMultiplier();
         entity.m_7292_(new MobEffectInstance(potionEffect, ticks, amp, false, showParticles, true));
      }
   }

   public boolean canSummon(LivingEntity playerEntity) {
      return this.isRealPlayer(playerEntity)
         && (playerEntity.m_21124_((MobEffect)ModPotions.SUMMONING_SICKNESS_EFFECT.get()) == null || playerEntity instanceof Player player && player.m_7500_());
   }

   public void applySummoningSickness(LivingEntity playerEntity, int time) {
      playerEntity.m_7292_(new MobEffectInstance((MobEffect)ModPotions.SUMMONING_SICKNESS_EFFECT.get(), time));
   }

   @Deprecated(
      forRemoval = true
   )
   public void summonLivingEntity(
      HitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats augments, SpellContext spellContext, ISummon summon
   ) {
      this.summonLivingEntity(rayTraceResult, world, shooter, augments, spellContext, null, summon);
   }

   public void summonLivingEntity(
      HitResult rayTraceResult,
      Level world,
      @NotNull LivingEntity shooter,
      SpellStats augments,
      SpellContext spellContext,
      @Nullable SpellResolver resolver,
      ISummon summon
   ) {
      if (this.isRealPlayer(shooter)) {
         summon.setOwnerID(shooter.m_20148_());
      }

      LivingEntity summonLivingEntity = summon.getLivingEntity();
      if (summonLivingEntity != null) {
         world.m_7967_(summon.getLivingEntity());
         if (resolver != null && resolver.hasFocus(ItemsRegistry.SUMMONING_FOCUS.get().m_7968_())) {
            EntitySpellResolver spellResolver = new EntitySpellResolver(
               spellContext.clone().withSpell(spellContext.getRemainingSpell()).withWrappedCaster(new LivingCaster(summonLivingEntity))
            );
            spellResolver.onResolveEffect(world, new EntityHitResult(summonLivingEntity));
            spellContext.setCanceled(true);
         }
      }

      MinecraftForge.EVENT_BUS.post(new SummonEvent(rayTraceResult, world, shooter, augments, spellContext, summon));
   }

   public Player getPlayer(LivingEntity entity, ServerLevel world) {
      return (Player)(entity instanceof Player player ? player : ANFakePlayer.getPlayer(world));
   }

   public int getBaseHarvestLevel(SpellStats stats) {
      return (int)(3.0 + stats.getAmpMultiplier());
   }

   public boolean canBlockBeHarvested(SpellStats stats, Level world, BlockPos pos) {
      return BlockUtil.canBlockBeHarvested(stats, world, pos);
   }

   @Deprecated(
      forRemoval = true
   )
   public void dealDamage(Level world, @NotNull LivingEntity shooter, float baseDamage, SpellStats stats, Entity entity, DamageSource source) {
      if (!(world instanceof ServerLevel server)) {
         return;
      }

      if (entity instanceof LivingEntity living && living.m_21223_() <= 0.0F) {
         return;
      }

      float totalDamage = (float)((double)baseDamage + stats.getDamageModifier());
      SpellDamageEvent.Pre preDamage = new SpellDamageEvent.Pre(source, shooter, entity, totalDamage, null);
      MinecraftForge.EVENT_BUS.post(preDamage);
      source = preDamage.damageSource;
      totalDamage = preDamage.damage;
      if (!(totalDamage <= 0.0F) && !preDamage.isCanceled()) {
         if (entity.m_6469_(source, totalDamage)) {
            shooter.m_21335_(entity);
            SpellDamageEvent.Post postDamage = new SpellDamageEvent.Post(source, shooter, entity, totalDamage, null);
            MinecraftForge.EVENT_BUS.post(postDamage);
            if (entity instanceof LivingEntity mob && mob.m_21223_() <= 0.0F && !mob.m_213877_() && stats.hasBuff(AugmentFortune.INSTANCE)) {
               Player playerContext = (Player)(shooter instanceof Player player ? player : ANFakePlayer.getPlayer(server));
               int looting = stats.getBuffCount(AugmentFortune.INSTANCE);
               Builder lootContext = LootUtil.getLootingContext(server, shooter, mob, looting, DamageSource.m_19344_(playerContext));
               ResourceLocation lootTable = mob.m_5743_();
               LootTable loottable = server.m_7654_().m_129898_().m_79217_(lootTable);
               List<ItemStack> items = loottable.m_230922_(lootContext.m_78975_(LootContextParamSets.f_81415_));
               items.forEach(mob::m_19983_);
            }
         }
      }
   }

   public Vec3 safelyGetHitPos(HitResult result) {
      return result instanceof EntityHitResult entityHitResult ? entityHitResult.m_82443_().m_20182_() : result.m_82450_();
   }

   public boolean isRealPlayer(Entity entity) {
      return entity instanceof Player && this.isNotFakePlayer(entity);
   }

   public boolean isNotFakePlayer(Entity entity) {
      return !(entity instanceof FakePlayer);
   }

   public void applyEnchantments(SpellStats stats, ItemStack stack) {
      if (stats.hasBuff(AugmentExtract.INSTANCE)) {
         stack.m_41663_(Enchantments.f_44985_, 1);
      }

      if (stats.hasBuff(AugmentFortune.INSTANCE)) {
         stack.m_41663_(Enchantments.f_44987_, stats.getBuffCount(AugmentFortune.INSTANCE));
      }
   }

   @Override
   public void buildConfig(net.minecraftforge.common.ForgeConfigSpec.Builder builder) {
      super.buildConfig(builder);
      super.buildAugmentLimitsConfig(builder, this.getDefaultAugmentLimits(new HashMap<>()));
      super.buildInvalidCombosConfig(builder, this.getDefaultInvalidCombos(new HashSet<>()));
   }

   public void addDamageConfig(net.minecraftforge.common.ForgeConfigSpec.Builder builder, double defaultValue) {
      this.DAMAGE = builder.defineInRange("damage", defaultValue, 0.0, 2.147483647E9);
   }

   public void addAmpConfig(net.minecraftforge.common.ForgeConfigSpec.Builder builder, double defaultValue) {
      this.AMP_VALUE = builder.defineInRange("amplify", defaultValue, 0.0, 2.147483647E9);
   }

   public void addPotionConfig(net.minecraftforge.common.ForgeConfigSpec.Builder builder, int defaultTime) {
      this.POTION_TIME = builder.comment("Potion duration, in seconds").defineInRange("potion_time", defaultTime, 0, Integer.MAX_VALUE);
   }

   public void addExtendTimeConfig(net.minecraftforge.common.ForgeConfigSpec.Builder builder, int defaultTime) {
      this.EXTEND_TIME = builder.comment("Extend time duration, in seconds").defineInRange("extend_time", defaultTime, 0, Integer.MAX_VALUE);
   }

   public void addGenericInt(net.minecraftforge.common.ForgeConfigSpec.Builder builder, int val, String comment, String path) {
      this.GENERIC_INT = builder.comment(comment).defineInRange(path, val, 0, Integer.MAX_VALUE);
   }

   public void addGenericDouble(net.minecraftforge.common.ForgeConfigSpec.Builder builder, double val, String comment, String path) {
      this.GENERIC_DOUBLE = builder.comment(comment).defineInRange(path, val, 0.0, Double.MAX_VALUE);
   }

   public void addDefaultPotionConfig(net.minecraftforge.common.ForgeConfigSpec.Builder builder) {
      this.addPotionConfig(builder, 30);
      this.addExtendTimeConfig(builder, 8);
   }

   @Deprecated(
      forRemoval = true,
      since = "3.4.0"
   )
   public ItemStack getItemFromCaster(@NotNull LivingEntity shooter, SpellContext spellContext, Predicate<ItemStack> predicate) {
      if (spellContext.castingTile instanceof IInventoryResponder iInventoryResponder) {
         return iInventoryResponder.getItem(predicate);
      } else if (shooter instanceof IInventoryResponder responder) {
         return responder.getItem(predicate);
      } else {
         if (shooter instanceof Player playerEntity) {
            NonNullList<ItemStack> list = playerEntity.f_36093_.f_35974_;

            for (int i = 0; i < 9; i++) {
               ItemStack stack = (ItemStack)list.get(i);
               if (predicate.test(stack)) {
                  return stack;
               }
            }
         }

         return ItemStack.f_41583_;
      }
   }

   @Deprecated(
      forRemoval = true,
      since = "3.4.0"
   )
   public ItemStack getItemFromCaster(@NotNull LivingEntity shooter, SpellContext spellContext, Item item) {
      return this.getItemFromCaster(shooter, spellContext, (Predicate<ItemStack>)(i -> i.m_41656_(new ItemStack(item))));
   }

   @Deprecated(
      forRemoval = true,
      since = "3.4.0"
   )
   public ItemStack extractStackFromCaster(@NotNull LivingEntity shooter, SpellContext spellContext, Predicate<ItemStack> predicate, int maxExtract) {
      IInventoryResponder responder = null;
      if (spellContext.castingTile instanceof IInventoryResponder) {
         responder = (IInventoryResponder)spellContext.castingTile;
      } else if (shooter instanceof IInventoryResponder) {
         responder = (IInventoryResponder)shooter;
      }

      if (responder != null) {
         return responder.extractItem(predicate, maxExtract);
      } else {
         if (shooter instanceof Player playerEntity) {
            NonNullList<ItemStack> list = playerEntity.f_36093_.f_35974_;

            for (int i = 0; i < 9; i++) {
               ItemStack stack = (ItemStack)list.get(i);
               if (predicate.test(stack)) {
                  return stack.m_41620_(maxExtract);
               }
            }
         }

         return ItemStack.f_41583_;
      }
   }

   @Deprecated(
      forRemoval = true,
      since = "3.4.0"
   )
   public ItemStack insertStackToCaster(@NotNull LivingEntity shooter, SpellContext spellContext, ItemStack stack) {
      IPickupResponder responder = null;
      if (spellContext.castingTile instanceof IPickupResponder) {
         responder = (IPickupResponder)spellContext.castingTile;
      } else if (shooter instanceof IInventoryResponder) {
         responder = (IPickupResponder)shooter;
      }

      if (responder != null) {
         return responder.onPickup(stack);
      } else {
         if (this.isRealPlayer(shooter)) {
            Player player = (Player)shooter;
            VoidJar.tryVoiding(player, stack);
            if (!player.m_36356_(stack)) {
               ItemEntity i = new ItemEntity(shooter.f_19853_, player.m_20185_(), player.m_20186_(), player.m_20189_(), stack);
               shooter.f_19853_.m_7967_(i);
            }
         }

         return stack;
      }
   }

   protected Set<AbstractAugment> getPotionAugments() {
      return this.augmentSetOf(new AbstractAugment[]{AugmentExtendTime.INSTANCE, AugmentDurationDown.INSTANCE, AugmentAmplify.INSTANCE});
   }

   protected Set<AbstractAugment> getSummonAugments() {
      return this.augmentSetOf(new AbstractAugment[]{AugmentExtendTime.INSTANCE, AugmentDurationDown.INSTANCE});
   }
}
