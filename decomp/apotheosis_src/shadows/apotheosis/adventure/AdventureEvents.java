package shadows.apotheosis.adventure;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.SpecialSpawn;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.event.entity.player.PlayerEvent.HarvestCheck;
import net.minecraftforge.event.level.BlockEvent.BreakEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.adventure.affix.Affix;
import shadows.apotheosis.adventure.affix.AffixHelper;
import shadows.apotheosis.adventure.affix.AffixInstance;
import shadows.apotheosis.adventure.affix.effect.TelepathicAffix;
import shadows.apotheosis.adventure.affix.socket.gem.GemManager;
import shadows.apotheosis.adventure.commands.BossCommand;
import shadows.apotheosis.adventure.commands.CategoryCheckCommand;
import shadows.apotheosis.adventure.commands.GemCommand;
import shadows.apotheosis.adventure.commands.LootifyCommand;
import shadows.apotheosis.adventure.commands.ModifierCommand;
import shadows.apotheosis.adventure.commands.RarityCommand;
import shadows.apotheosis.adventure.commands.SocketCommand;
import shadows.apotheosis.adventure.compat.GameStagesCompat;
import shadows.apotheosis.adventure.loot.LootCategory;
import shadows.apotheosis.adventure.loot.LootController;
import shadows.placebo.events.AnvilLandEvent;
import shadows.placebo.events.GetEnchantmentLevelEvent;
import shadows.placebo.events.ItemUseEvent;
import shadows.placebo.json.WeightedJsonReloadListener.IDimensional;

public class AdventureEvents {
   private static ThreadLocal<AtomicBoolean> reentrantLock = ThreadLocal.withInitial(() -> new AtomicBoolean(false));

   @SubscribeEvent
   public void cmds(Apotheosis.ApotheosisCommandEvent e) {
      RarityCommand.register(e.getRoot());
      CategoryCheckCommand.register(e.getRoot());
      LootifyCommand.register(e.getRoot());
      ModifierCommand.register(e.getRoot());
      GemCommand.register(e.getRoot());
      SocketCommand.register(e.getRoot());
      BossCommand.register(e.getRoot());
   }

   @SubscribeEvent
   public void affixModifiers(ItemAttributeModifierEvent e) {
      ItemStack stack = e.getItemStack();
      if (stack.m_41782_()) {
         Map<Affix, AffixInstance> affixes = AffixHelper.getAffixes(stack);
         affixes.forEach((afx, inst) -> inst.addModifiers(e.getSlotType(), e::addModifier));
      }
   }

   @SubscribeEvent(
      priority = EventPriority.HIGH
   )
   public void preventBossSuffocate(LivingHurtEvent e) {
      if (e.getSource() == DamageSource.f_19310_ && e.getEntity().getPersistentData().m_128441_("apoth.boss")) {
         e.setCanceled(true);
      }
   }

   @SubscribeEvent(
      priority = EventPriority.HIGH
   )
   public void fireArrow(EntityJoinLevelEvent e) {
      if (e.getEntity() instanceof AbstractArrow arrow
         && !arrow.getPersistentData().m_128471_("apoth.generated")
         && arrow.m_37282_() instanceof LivingEntity living) {
         ItemStack bow = living.m_21211_();
         if (bow.m_41619_()) {
            bow = living.m_21205_();
            if (bow.m_41619_() || !LootCategory.forItem(bow).isRanged()) {
               bow = living.m_21206_();
            }
         }

         if (bow.m_41619_()) {
            return;
         }

         Map<Affix, AffixInstance> affixes = AffixHelper.getAffixes(bow);
         affixes.values().forEach(a -> a.onArrowFired(living, arrow));
         AffixHelper.copyFrom(bow, arrow);
      }
   }

   @SubscribeEvent
   public void impact(ProjectileImpactEvent e) {
      if (e.getProjectile() instanceof AbstractArrow arrow) {
         Map<Affix, AffixInstance> affixes = AffixHelper.getAffixes(arrow);
         affixes.values().forEach(inst -> inst.onArrowImpact(arrow, e.getRayTraceResult(), e.getRayTraceResult().m_6662_()));
      }
   }

   @SubscribeEvent(
      priority = EventPriority.LOW
   )
   public void onDamage(LivingHurtEvent e) {
      Apoth.Affixes.MAGICAL.ifPresent(afx -> afx.onHurt(e));
      DamageSource src = e.getSource();
      LivingEntity ent = e.getEntity();
      float amount = e.getAmount();

      for (ItemStack s : ent.m_20158_()) {
         Map<Affix, AffixInstance> affixes = AffixHelper.getAffixes(s);

         for (AffixInstance inst : affixes.values()) {
            amount = inst.onHurt(src, ent, amount);
         }
      }

      e.setAmount(amount);
   }

   @SubscribeEvent
   public void onItemUse(ItemUseEvent e) {
      ItemStack s = e.getItemStack();
      Map<Affix, AffixInstance> affixes = AffixHelper.getAffixes(s);

      for (AffixInstance inst : affixes.values()) {
         InteractionResult type = inst.onItemUse(e.getContext());
         if (type != null) {
            e.setCanceled(true);
            e.setCancellationResult(type);
         }
      }
   }

   @SubscribeEvent
   public void shieldBlock(ShieldBlockEvent e) {
      ItemStack stack = e.getEntity().m_21211_();
      Map<Affix, AffixInstance> affixes = AffixHelper.getAffixes(stack);
      float blocked = e.getBlockedDamage();

      for (AffixInstance inst : affixes.values()) {
         blocked = inst.onShieldBlock(e.getEntity(), e.getDamageSource(), blocked);
      }

      if (blocked != e.getOriginalBlockedDamage()) {
         e.setBlockedDamage(blocked);
      }
   }

   @SubscribeEvent
   public void blockBreak(BreakEvent e) {
      ItemStack stack = e.getPlayer().m_21205_();
      Map<Affix, AffixInstance> affixes = AffixHelper.getAffixes(stack);

      for (AffixInstance inst : affixes.values()) {
         inst.onBlockBreak(e.getPlayer(), e.getLevel(), e.getPos(), e.getState());
      }
   }

   @SubscribeEvent(
      priority = EventPriority.HIGH
   )
   public void dropsHigh(LivingDropsEvent e) {
      if (e.getSource().m_7639_() instanceof ServerPlayer p && e.getEntity() instanceof Monster) {
         if (p instanceof FakePlayer) {
            return;
         }

         float chance = AdventureConfig.gemDropChance + (e.getEntity().getPersistentData().m_128441_("apoth.boss") ? AdventureConfig.gemBossBonus : 0.0F);
         if (p.f_19796_.m_188501_() <= chance) {
            Entity ent = e.getEntity();
            e.getDrops()
               .add(
                  new ItemEntity(
                     ent.f_19853_,
                     ent.m_20185_(),
                     ent.m_20186_(),
                     ent.m_20189_(),
                     GemManager.createRandomGemStack(
                        p.f_19796_, (ServerLevel)p.f_19853_, p.m_36336_(), IDimensional.matches(p.m_9236_()), GameStagesCompat.IStaged.matches(p)
                     ),
                     0.0,
                     0.0,
                     0.0
                  )
               );
         }
      }
   }

   @SubscribeEvent(
      priority = EventPriority.LOW
   )
   public void drops(LivingDropsEvent e) {
      Apoth.Affixes.FESTIVE.ifPresent(afx -> afx.drops(e));
   }

   @SubscribeEvent(
      priority = EventPriority.LOW
   )
   public void deathMark(LivingDeathEvent e) {
      Apoth.Affixes.FESTIVE.ifPresent(afx -> afx.markEquipment(e));
   }

   @SubscribeEvent(
      priority = EventPriority.LOWEST
   )
   public void dropsLowest(LivingDropsEvent e) {
      TelepathicAffix.drops(e);
   }

   @SubscribeEvent(
      priority = EventPriority.LOWEST,
      receiveCanceled = true
   )
   public void festive_removeMarker(LivingDropsEvent e) {
      Apoth.Affixes.FESTIVE.ifPresent(afx -> afx.removeMarker(e));
   }

   @SubscribeEvent
   public void harvest(HarvestCheck e) {
      Apoth.Affixes.OMNETIC.ifPresent(afx -> afx.harvest(e));
   }

   @SubscribeEvent(
      priority = EventPriority.HIGHEST
   )
   public void speed(BreakSpeed e) {
      Apoth.Affixes.OMNETIC.ifPresent(afx -> afx.speed(e));
   }

   @SubscribeEvent(
      priority = EventPriority.LOW
   )
   public void onBreak(BreakEvent e) {
      Apoth.Affixes.RADIAL.ifPresent(afx -> afx.onBreak(e));
   }

   @SubscribeEvent(
      priority = EventPriority.LOW
   )
   public void special(SpecialSpawn e) {
      if (e.getSpawnReason() == MobSpawnType.NATURAL
         && e.getLevel().m_213780_().m_188501_() <= AdventureConfig.randomAffixItem
         && e.getEntity() instanceof Monster) {
         Player player = e.getLevel().m_45924_(e.getX(), e.getY(), e.getZ(), -1.0, false);
         if (player == null) {
            return;
         }

         ItemStack affixItem = LootController.createRandomLootItem(e.getLevel().m_213780_(), null, player, (ServerLevel)e.getEntity().f_19853_);
         if (affixItem.m_41619_()) {
            return;
         }

         affixItem.m_41784_().m_128379_("apoth_rspawn", true);
         LootCategory cat = LootCategory.forItem(affixItem);
         EquipmentSlot slot = cat.getSlots()[0];
         e.getEntity().m_8061_(slot, affixItem);
         e.getEntity().m_21508_(slot);
      }
   }

   @SubscribeEvent
   public void gemSmashing(AnvilLandEvent e) {
      Level level = e.getLevel();
      BlockPos pos = e.getPos();

      for (ItemEntity ent : level.m_45976_(ItemEntity.class, new AABB(pos, pos.m_7918_(1, 1, 1)))) {
         ItemStack stack = ent.m_32055_();
         if (stack.m_41720_() == Apoth.Items.GEM.get()) {
            ent.m_32045_(new ItemStack((ItemLike)Apoth.Items.GEM_DUST.get(), stack.m_41613_()));
         }
      }
   }

   @SubscribeEvent(
      priority = EventPriority.HIGH
   )
   public void enchLevels(GetEnchantmentLevelEvent e) {
      boolean isReentrant = reentrantLock.get().getAndSet(true);
      if (!isReentrant) {
         AffixHelper.streamAffixes(e.getStack()).forEach(inst -> inst.getEnchantmentLevels(e.getEnchantments()));
         reentrantLock.get().set(false);
      }
   }

   @SubscribeEvent
   public void update(LivingTickEvent e) {
      LivingEntity entity = e.getEntity();
      if (entity.getPersistentData().m_128441_("apoth.burns_in_sun") && entity.f_19853_.m_46461_() && !entity.f_19853_.f_46443_) {
         float f = entity.m_213856_();
         BlockPos blockpos = new BlockPos(entity.m_20185_(), entity.m_20188_(), entity.m_20189_());
         boolean flag = entity.m_20071_() || entity.f_146808_ || entity.f_146809_;
         if (f > 0.5F && entity.f_19796_.m_188501_() * 30.0F < (f - 0.4F) * 2.0F && !flag && entity.f_19853_.m_45527_(blockpos)) {
            entity.m_20254_(8);
         }
      }
   }

   @SubscribeEvent
   public void clone(Clone e) {
      int oldSeed = e.getOriginal().getPersistentData().m_128451_("apoth_reforge_seed");
      e.getEntity().getPersistentData().m_128405_("apoth_reforge_seed", oldSeed);
   }
}
