package io.redspace.ironsspellbooks.player;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.entity.IMagicEntity;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.api.util.CameraShakeManager;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.block.BloodCauldronBlock;
import io.redspace.ironsspellbooks.capabilities.magic.RecastResult;
import io.redspace.ironsspellbooks.capabilities.magic.SyncedSpellData;
import io.redspace.ironsspellbooks.capabilities.magic.UpgradeData;
import io.redspace.ironsspellbooks.compat.tetra.TetraProxy;
import io.redspace.ironsspellbooks.config.ServerConfigs;
import io.redspace.ironsspellbooks.data.DataFixerStorage;
import io.redspace.ironsspellbooks.data.IronsDataStorage;
import io.redspace.ironsspellbooks.datafix.IronsWorldUpgrader;
import io.redspace.ironsspellbooks.effect.AbyssalShroudEffect;
import io.redspace.ironsspellbooks.effect.EvasionEffect;
import io.redspace.ironsspellbooks.effect.SummonTimer;
import io.redspace.ironsspellbooks.entity.mobs.MagicSummon;
import io.redspace.ironsspellbooks.entity.spells.root.PreventDismount;
import io.redspace.ironsspellbooks.item.CastingItem;
import io.redspace.ironsspellbooks.item.Scroll;
import io.redspace.ironsspellbooks.item.curios.CurioBaseItem;
import io.redspace.ironsspellbooks.network.ClientboundEquipmentChanged;
import io.redspace.ironsspellbooks.network.ClientboundSyncMana;
import io.redspace.ironsspellbooks.registries.BlockRegistry;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import io.redspace.ironsspellbooks.setup.Messages;
import io.redspace.ironsspellbooks.util.MinecraftInstanceHelper;
import io.redspace.ironsspellbooks.util.ModTags;
import io.redspace.ironsspellbooks.util.UpgradeUtils;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.LevelStorageSource.LevelStorageAccess;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.SpecialSpawn;
import net.minecraftforge.event.entity.player.PlayerContainerEvent.Open;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.StartTracking;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.event.level.LevelEvent.Load;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.event.CurioAttributeModifierEvent;
import top.theillusivec4.curios.api.event.CurioChangeEvent;

@EventBusSubscriber
public class ServerPlayerEvents {
   @SubscribeEvent
   public static void onPlayerDropItem(ItemTossEvent event) {
      ItemStack itemStack = event.getEntity().m_32055_();
      if (itemStack.m_41720_() instanceof Scroll) {
         MagicData magicData = MagicData.getPlayerMagicData(event.getPlayer());
         if (magicData.isCasting() && magicData.getCastSource() == CastSource.SCROLL && magicData.getCastType() == CastType.CONTINUOUS) {
            itemStack.m_41774_(1);
         }
      }
   }

   @SubscribeEvent
   public static void onLevelLoaded(Load event) {
      if (event.getLevel() instanceof ServerLevel serverLevel && serverLevel.m_46472_() == Level.f_46428_) {
         IronsDataStorage.init(serverLevel.m_8895_());
      }
   }

   @SubscribeEvent
   public static void onServerStoppedEvent(ServerStoppedEvent event) {
      IronsSpellbooks.MCS = null;
      IronsSpellbooks.OVERWORLD = null;
   }

   @SubscribeEvent
   public static void onServerStarted(ServerStartedEvent event) {
      IronsSpellbooks.MCS = event.getServer();
      IronsSpellbooks.OVERWORLD = IronsSpellbooks.MCS.m_129783_();
   }

   @SubscribeEvent
   public static void onServerAboutToStart(ServerAboutToStartEvent event) {
      DataFixerStorage.init(event.getServer().f_129744_);
      if ((Boolean)ServerConfigs.RUN_WORLD_UPGRADER.get()) {
         MinecraftServer server = event.getServer();
         LevelStorageAccess storageSource = server.f_129744_;
         IronsWorldUpgrader iwu = new IronsWorldUpgrader(storageSource, server.m_129910_().m_5961_());
         iwu.runUpgrade();
      }
   }

   @SubscribeEvent
   public static void onLivingEquipmentChangeEvent(LivingEquipmentChangeEvent event) {
      if (event.getEntity() instanceof ServerPlayer serverPlayer) {
         MagicData playerMagicData = MagicData.getPlayerMagicData(serverPlayer);
         if (playerMagicData.isCasting() && (event.getFrom().m_41720_() instanceof CastingItem || event.getTo().m_41720_() instanceof CastingItem)) {
            Utils.serverSideCancelCast(serverPlayer);
            Messages.sendToPlayer(new ClientboundEquipmentChanged(), serverPlayer);
            return;
         }

         boolean isFromSpellContainer = ISpellContainer.isSpellContainer(event.getFrom());
         if (isFromSpellContainer && ISpellContainer.get(event.getFrom()).getIndexForSpell(playerMagicData.getCastingSpell().getSpell()) >= 0) {
            IronsSpellbooks.LOGGER
               .debug("onLivingEquipmentChangeEvent from:\n{}\n{}", event.getFrom().toString(), Integer.toHexString(event.getFrom().hashCode()));
            IronsSpellbooks.LOGGER.debug("onLivingEquipmentChangeEvent to:\n{}\n{}", event.getTo().toString(), Integer.toHexString(event.getTo().hashCode()));
            if (playerMagicData.isCasting()) {
               Utils.serverSideCancelCast(serverPlayer);
            }

            Messages.sendToPlayer(new ClientboundEquipmentChanged(), serverPlayer);
         } else if (isFromSpellContainer || ISpellContainer.isSpellContainer(event.getTo())) {
            Messages.sendToPlayer(new ClientboundEquipmentChanged(), serverPlayer);
         }
      }
   }

   @SubscribeEvent
   public static void onCurioChangeEvent(CurioChangeEvent event) {
      if (event.getEntity() instanceof ServerPlayer serverPlayer
         && (ISpellContainer.isSpellContainer(event.getFrom()) || ISpellContainer.isSpellContainer(event.getTo()))) {
         Messages.sendToPlayer(new ClientboundEquipmentChanged(), serverPlayer);
      }
   }

   @SubscribeEvent
   public static void onPlayerLogOut(PlayerLoggedOutEvent event) {
      if (event.getEntity() instanceof ServerPlayer serverPlayer) {
         Utils.serverSideCancelCast(serverPlayer);
      }
   }

   @SubscribeEvent
   public static void onPlayerOpenContainer(Open event) {
      if (!event.getEntity().f_19853_.f_46443_) {
         if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            MagicData playerMagicData = MagicData.getPlayerMagicData(serverPlayer);
            if (playerMagicData.isCasting()) {
               Utils.serverSideCancelCast(serverPlayer);
            }
         }
      }
   }

   @SubscribeEvent
   public static void handleUpgradeModifiers(ItemAttributeModifierEvent event) {
      UpgradeData upgradeData = UpgradeData.getUpgradeData(event.getItemStack());
      if (upgradeData != UpgradeData.NONE && upgradeData.getUpgradedSlot().equals(event.getSlotType().m_20751_())) {
         UpgradeUtils.handleAttributeEvent(event.getModifiers(), upgradeData, event::addModifier, event::removeModifier, Optional.empty());
      }
   }

   @SubscribeEvent
   public static void handleCurioUpgradeModifiers(CurioAttributeModifierEvent event) {
      UpgradeData upgradeData = UpgradeData.getUpgradeData(event.getItemStack());
      if (upgradeData != UpgradeData.NONE && upgradeData.getUpgradedSlot().equals(event.getSlotContext().identifier())) {
         UpgradeUtils.handleAttributeEvent(event.getModifiers(), upgradeData, event::addModifier, event::removeModifier, Optional.of(event.getUuid()));
      }
   }

   @SubscribeEvent
   public static void onExperienceDroppedEvent(LivingExperienceDropEvent event) {
      Player player = event.getAttackingPlayer();
      if (player != null) {
         int ringCount = CuriosApi.getCuriosHelper().findCurios(player, (Item)ItemRegistry.EMERALD_STONEPLATE_RING.get()).size();

         for (int i = 0; i < ringCount; i++) {
            event.setDroppedExperience((int)((double)event.getDroppedExperience() * 1.25));
         }
      }
   }

   @SubscribeEvent
   public static void onStartTracking(StartTracking event) {
      if (event.getEntity() instanceof ServerPlayer serverPlayer && event.getTarget() instanceof ServerPlayer targetPlayer) {
         MagicData.getPlayerMagicData(serverPlayer).getSyncedData().syncToPlayer(targetPlayer);
      }
   }

   @SubscribeEvent
   public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer serverPlayer) {
         MagicData playerMagicData = MagicData.getPlayerMagicData(serverPlayer);
         playerMagicData.getPlayerCooldowns().syncToPlayer(serverPlayer);
         playerMagicData.getPlayerRecasts().syncAllToPlayer();
         playerMagicData.getSyncedData().syncToPlayer(serverPlayer);
         Messages.sendToPlayer(new ClientboundSyncMana(playerMagicData), serverPlayer);
         CameraShakeManager.doSync(serverPlayer);
      }
   }

   @SubscribeEvent
   public static void onLivingDeathEvent(LivingDeathEvent event) {
      if (event.getEntity() instanceof ServerPlayer serverPlayer) {
         Utils.serverSideCancelCast(serverPlayer);
         MagicData.getPlayerMagicData(serverPlayer).getPlayerRecasts().removeAll(RecastResult.DEATH);
      }
   }

   @SubscribeEvent
   public static void onPlayerCloned(Clone event) {
      if (event.getEntity() instanceof ServerPlayer newServerPlayer) {
         boolean keepEverything = !event.isWasDeath();
         event.getOriginal().m_21220_().forEach(effect -> {
            if (effect.m_19544_() instanceof SummonTimer) {
               newServerPlayer.m_147207_(effect, newServerPlayer);
            }
         });
         event.getOriginal().reviveCaps();
         MagicData oldMagicData = MagicData.getPlayerMagicData(event.getOriginal());
         MagicData newMagicData = MagicData.getPlayerMagicData(event.getEntity());
         newMagicData.setSyncedData(oldMagicData.getSyncedData().getPersistentData());
         newMagicData.getSyncedData().doSync();
         oldMagicData.getPlayerCooldowns()
            .getSpellCooldowns()
            .forEach((spellId, cooldown) -> newMagicData.getPlayerCooldowns().getSpellCooldowns().put(spellId, cooldown));
         event.getOriginal().invalidateCaps();
      }
   }

   @SubscribeEvent
   public static void onPlayerChangedDimension(PlayerChangedDimensionEvent event) {
      if (event.getEntity() instanceof ServerPlayer serverPlayer) {
         Utils.serverSideCancelCast(serverPlayer);
      }
   }

   @SubscribeEvent
   public static void onPlayerRespawn(PlayerRespawnEvent event) {
      if (event.getEntity() instanceof ServerPlayer serverPlayer) {
         serverPlayer.m_20095_();
         serverPlayer.m_146917_(0);
         serverPlayer.f_8906_.m_9829_(new ClientboundSetEntityDataPacket(serverPlayer.m_19879_(), serverPlayer.m_20088_(), true));
         Utils.serverSideCancelCast(serverPlayer);
         serverPlayer.m_21220_().forEach(effect -> {
            if (effect.m_19544_() instanceof SummonTimer) {
               serverPlayer.f_8906_.m_9829_(new ClientboundUpdateMobEffectPacket(serverPlayer.m_19879_(), effect));
            }
         });
         MagicData.getPlayerMagicData(serverPlayer)
            .setMana((float)((int)(serverPlayer.m_21133_((Attribute)AttributeRegistry.MAX_MANA.get()) * (Double)ServerConfigs.MANA_SPAWN_PERCENT.get())));
      }
   }

   @SubscribeEvent
   public static void onLivingAttack(LivingAttackEvent event) {
      LivingEntity livingEntity = event.getEntity();
      if (livingEntity instanceof ServerPlayer || livingEntity instanceof IMagicEntity) {
         if (((CurioBaseItem)ItemRegistry.FIREWARD_RING.get()).isEquippedBy(livingEntity) && event.getSource().m_19384_()) {
            event.getEntity().m_20095_();
            event.setCanceled(true);
            return;
         }

         MagicData playerMagicData = MagicData.getPlayerMagicData(livingEntity);
         if (playerMagicData.getSyncedData().hasEffect(2L)) {
            if (EvasionEffect.doEffect(livingEntity, event.getSource())) {
               event.setCanceled(true);
            }
         } else if (playerMagicData.getSyncedData().hasEffect(8L) && AbyssalShroudEffect.doEffect(livingEntity, event.getSource())) {
            event.setCanceled(true);
         }
      }

      TetraProxy.PROXY.handleLivingAttackEvent(event);
   }

   @SubscribeEvent
   public static void onLivingChangeTarget(LivingChangeTargetEvent event) {
      LivingEntity newTarget = event.getNewTarget();
      LivingEntity entity = event.getEntity();
      if (newTarget != null) {
         if (newTarget.m_6095_().m_204039_(ModTags.VILLAGE_ALLIES) && entity.m_6095_().m_204039_(ModTags.VILLAGE_ALLIES)) {
            event.setCanceled(true);
         }

         if (newTarget instanceof MagicSummon summon && !entity.equals(((Mob)newTarget).m_5448_())) {
            event.setCanceled(true);
         }
      }
   }

   @SubscribeEvent
   public static void onLivingTakeDamage(LivingDamageEvent event) {
      if ((Boolean)ServerConfigs.BETTER_CREEPER_THUNDERHIT.get()
         && event.getSource().m_19384_()
         && event.getEntity() instanceof Creeper creeper
         && creeper.m_7090_()) {
         event.setCanceled(true);
      } else {
         if (event.getSource().m_7639_() instanceof LivingEntity livingAttacker) {
            if (livingAttacker.m_21023_((MobEffect)MobEffectRegistry.SPIDER_ASPECT.get()) && event.getEntity().m_21023_(MobEffects.f_19614_)) {
               int lvl = livingAttacker.m_21124_((MobEffect)MobEffectRegistry.SPIDER_ASPECT.get()).m_19564_() + 1;
               float before = event.getAmount();
               float multiplier = 1.0F + 0.05F * (float)lvl;
               event.setAmount(event.getAmount() * multiplier);
            }

            if (livingAttacker.m_20145_()
               && ((CurioBaseItem)ItemRegistry.LURKER_RING.get()).isEquippedBy(livingAttacker)
               && livingAttacker instanceof Player player
               && !player.m_36335_().m_41519_((Item)ItemRegistry.LURKER_RING.get())) {
               event.setAmount(event.getAmount() * 1.5F);
               player.m_36335_().m_41524_((Item)ItemRegistry.LURKER_RING.get(), 300);
            }
         }

         MagicData playerMagicData = MagicData.getPlayerMagicData(event.getEntity());
         if (playerMagicData.getSyncedData().hasEffect(4L)) {
            playerMagicData.getSyncedData().addHeartstopDamage(event.getAmount() * 0.5F);
            event.setCanceled(true);
         } else {
            if (event.getEntity() instanceof ServerPlayer serverPlayer
               && playerMagicData.isCasting()
               && playerMagicData.getCastingSpell().getSpell().canBeInterrupted(serverPlayer)
               && playerMagicData.getCastDurationRemaining() > 0
               && event.getSource() != DamageSource.f_146701_
               && event.getSource() != DamageSource.f_19313_
               && event.getSource() != DamageSource.f_19307_
               && event.getSource() != DamageSource.f_19320_
               && !playerMagicData.popMarkedPoison()) {
               Utils.serverSideCancelCast(serverPlayer);
            }
         }
      }
   }

   @SubscribeEvent
   public static void onEntityMountEvent(EntityMountEvent event) {
      if (!event.getEntity().f_19853_.f_46443_) {
         if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            MagicData playerMagicData = MagicData.getPlayerMagicData(serverPlayer);
            if (playerMagicData.isCasting()) {
               Utils.serverSideCancelCast(serverPlayer);
            }
         }
      }
   }

   @SubscribeEvent
   public static void preventDismount(EntityMountEvent event) {
      if (!event.getEntity().f_19853_.f_46443_
         && event.getEntityBeingMounted() instanceof PreventDismount
         && event.isDismounting()
         && !event.getEntityBeingMounted().m_213877_()) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void onProjectileImpact(ProjectileImpactEvent event) {
      if (event.getRayTraceResult() instanceof EntityHitResult entityHitResult) {
         Entity victim = entityHitResult.m_82443_();
         if (victim instanceof IMagicEntity || victim instanceof Player) {
            LivingEntity livingEntity = (LivingEntity)victim;
            SyncedSpellData syncedSpellData = livingEntity.f_19853_.f_46443_
               ? ClientMagicData.getSyncedSpellData(livingEntity)
               : MagicData.getPlayerMagicData(livingEntity).getSyncedData();
            if (syncedSpellData.hasEffect(2L)) {
               if (EvasionEffect.doEffect(livingEntity, new IndirectEntityDamageSource("noop", event.getProjectile(), event.getProjectile().m_37282_()))) {
                  event.setCanceled(true);
               }
            } else if (syncedSpellData.hasEffect(8L)
               && AbyssalShroudEffect.doEffect(livingEntity, new IndirectEntityDamageSource("noop", event.getProjectile(), event.getProjectile().m_37282_()))) {
               event.setCanceled(true);
            }
         }
      }
   }

   @SubscribeEvent
   public static void useOnEntityEvent(EntityInteractSpecific event) {
      if (event.getTarget() instanceof Creeper creeper) {
         Player player = event.getEntity();
         ItemStack useItem = player.m_21120_(event.getHand());
         if (useItem.m_150930_(Items.f_42590_) && creeper.m_7090_()) {
            creeper.m_6469_(DamageSource.f_19318_.m_19382_(), 5.0F);
            player.f_19853_
               .m_6263_((Player)null, player.m_20185_(), player.m_20186_(), player.m_20189_(), SoundEvents.f_11771_, SoundSource.NEUTRAL, 1.0F, 1.0F);
            player.m_6674_(event.getHand());
            event.setCancellationResult(
               InteractionResultHolder.m_19096_(ItemUtils.m_41813_(useItem, player, new ItemStack((ItemLike)ItemRegistry.LIGHTNING_BOTTLE.get()))).m_19089_()
            );
            event.setCanceled(true);
         }
      }
   }

   @SubscribeEvent
   public static void useItemEvent(RightClickItem event) {
      Player entity = event.getEntity();
      if (entity.f_19853_.f_46443_) {
         MinecraftInstanceHelper.ifPlayerPresent(localPlayer -> {
            if (ClientMagicData.isCasting() && entity.m_20148_().equals(localPlayer.m_20148_())) {
               event.setCanceled(true);
            }
         });
      } else {
         MagicData magicData = MagicData.getPlayerMagicData(entity);
         if (magicData.isCasting() && event.getItemStack() != magicData.getPlayerCastingItem()) {
            event.setCanceled(true);
         }
      }
   }

   @SubscribeEvent
   public static void handleResistanceAttributesOnSpawn(SpecialSpawn event) {
      Mob mob = event.getEntity();
      if (mob.m_6336_() == MobType.f_21641_) {
         setIfNonNull(mob, (Attribute)AttributeRegistry.HOLY_MAGIC_RESIST.get(), 0.5);
         setIfNonNull(mob, (Attribute)AttributeRegistry.BLOOD_MAGIC_RESIST.get(), 1.5);
      } else if (mob.m_6336_() == MobType.f_21644_) {
         setIfNonNull(mob, (Attribute)AttributeRegistry.LIGHTNING_MAGIC_RESIST.get(), 0.5);
      }

      if (mob.m_5825_()) {
         setIfNonNull(mob, (Attribute)AttributeRegistry.FIRE_MAGIC_RESIST.get(), 1.5);
      }

      if (mob.m_6095_() == EntityType.f_20551_) {
         setIfNonNull(mob, (Attribute)AttributeRegistry.ICE_MAGIC_RESIST.get(), 0.5);
      }
   }

   private static void setIfNonNull(LivingEntity mob, Attribute attribute, double value) {
      AttributeInstance instance = mob.m_21204_().m_22146_(attribute);
      if (instance != null) {
         instance.m_22100_(value);
      }
   }

   @SubscribeEvent
   public static void onLivingTick(LivingTickEvent event) {
      LivingEntity entity = event.getEntity();
      Level level = entity.f_19853_;
      if (!level.f_46443_ && entity.f_19797_ % 40 == 0) {
         BlockPos pos = entity.m_20183_();
         BlockState blockState = entity.m_9236_().m_8055_(pos);
         if (blockState.m_60713_(Blocks.f_50256_)) {
            BloodCauldronBlock.attemptCookEntity(blockState, entity.m_9236_(), pos, entity, () -> {
               level.m_46597_(pos, ((Block)BlockRegistry.BLOOD_CAULDRON_BLOCK.get()).m_49966_());
               level.m_142346_(null, GameEvent.f_157792_, pos);
            });
         }
      }
   }

   @SubscribeEvent
   public static void changeDigSpeed(BreakSpeed event) {
      Player player = event.getEntity();
      if (player.m_21023_((MobEffect)MobEffectRegistry.HASTENED.get())) {
         int i = 1 + player.m_21124_((MobEffect)MobEffectRegistry.HASTENED.get()).m_19564_();
         event.setNewSpeed(event.getNewSpeed() * Utils.intPow(1.2F, i));
      }

      if (player.m_21023_((MobEffect)MobEffectRegistry.SLOWED.get())) {
         int i = 1 + player.m_21124_((MobEffect)MobEffectRegistry.SLOWED.get()).m_19564_();
         event.setNewSpeed(event.getNewSpeed() * Utils.intPow(0.8F, i));
      }
   }
}
