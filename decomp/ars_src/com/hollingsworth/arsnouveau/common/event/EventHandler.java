package com.hollingsworth.arsnouveau.common.event;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.event.DispelEvent;
import com.hollingsworth.arsnouveau.api.event.EventQueue;
import com.hollingsworth.arsnouveau.api.event.ITimedEvent;
import com.hollingsworth.arsnouveau.api.loot.DungeonLootTables;
import com.hollingsworth.arsnouveau.api.perk.PerkAttributes;
import com.hollingsworth.arsnouveau.api.recipe.MultiRecipeWrapper;
import com.hollingsworth.arsnouveau.api.registry.CasterTomeRegistry;
import com.hollingsworth.arsnouveau.api.ritual.RitualEventQueue;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.api.util.CuriosUtil;
import com.hollingsworth.arsnouveau.api.util.PerkUtil;
import com.hollingsworth.arsnouveau.client.ClientInfo;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.block.LavaLily;
import com.hollingsworth.arsnouveau.common.command.AddTomeCommand;
import com.hollingsworth.arsnouveau.common.command.DataDumpCommand;
import com.hollingsworth.arsnouveau.common.command.PathCommand;
import com.hollingsworth.arsnouveau.common.command.ResetCommand;
import com.hollingsworth.arsnouveau.common.command.SummonAnimHeadCommand;
import com.hollingsworth.arsnouveau.common.command.ToggleLightCommand;
import com.hollingsworth.arsnouveau.common.compat.CaelusHandler;
import com.hollingsworth.arsnouveau.common.datagen.ItemTagProvider;
import com.hollingsworth.arsnouveau.common.entity.Whirlisprig;
import com.hollingsworth.arsnouveau.common.items.EnchantersSword;
import com.hollingsworth.arsnouveau.common.items.RitualTablet;
import com.hollingsworth.arsnouveau.common.items.VoidJar;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketJoinedServer;
import com.hollingsworth.arsnouveau.common.perk.JumpHeightPerk;
import com.hollingsworth.arsnouveau.common.perk.LootingPerk;
import com.hollingsworth.arsnouveau.common.potions.ModPotions;
import com.hollingsworth.arsnouveau.common.ritual.DenySpawnRitual;
import com.hollingsworth.arsnouveau.common.ritual.RitualFlight;
import com.hollingsworth.arsnouveau.common.ritual.RitualGravity;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectGlide;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.Config;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import com.hollingsworth.arsnouveau.setup.VillagerRegistry;
import com.hollingsworth.arsnouveau.setup.reward.Rewards;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.npc.VillagerTrades.EmeraldForItems;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.entity.npc.VillagerTrades.ItemsForEmeralds;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Finish;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
import net.minecraftforge.event.entity.living.MobEffectEvent.Added;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.level.SaplingGrowTreeEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.items.ItemHandlerHelper;

@EventBusSubscriber(
   modid = "ars_nouveau"
)
public class EventHandler {
   @SubscribeEvent(
      priority = EventPriority.LOWEST
   )
   public static void resourceLoadEvent(AddReloadListenerEvent event) {
      event.addListener(new SimplePreparableReloadListener<Object>() {
         protected Object m_5944_(ResourceManager pResourceManager, ProfilerFiller pProfiler) {
            return null;
         }

         protected void m_5787_(Object pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
            MultiRecipeWrapper.RECIPE_CACHE = new HashMap<>();
            ArsNouveauAPI.getInstance().onResourceReload();
            EventQueue.getServerInstance().addEvent(new ITimedEvent() {
               boolean expired;

               @Override
               public void tickEvent(TickEvent event) {
                  if (event instanceof ServerTickEvent serverTickEvent) {
                     CasterTomeRegistry.reloadTomeData(serverTickEvent.getServer().m_129894_());
                  }

                  this.expired = true;
               }

               @Override
               public void tick(boolean serverSide) {
               }

               @Override
               public boolean isExpired() {
                  return this.expired;
               }
            });
         }
      });
   }

   @SubscribeEvent(
      priority = EventPriority.LOWEST
   )
   public static void itemPickupEvent(EntityItemPickupEvent event) {
      Player player = event.getEntity();
      ItemStack pickingUp = event.getItem().m_32055_();
      boolean voided = VoidJar.tryVoiding(player, pickingUp);
      if (voided) {
         event.setResult(Result.ALLOW);
      }
   }

   @SubscribeEvent(
      priority = EventPriority.LOWEST
   )
   public static void itemPickupEvent(ItemPickupEvent event) {
      Player player = event.getEntity();
      ItemStack pickingUp = event.getStack();
      VoidJar.tryVoiding(player, pickingUp);
   }

   @SubscribeEvent
   public static void shieldEvent(ShieldBlockEvent e) {
      if (!e.getEntity().f_19853_.f_46443_
         && e.getEntity() instanceof Player player
         && player.m_21254_()
         && player.m_21211_().m_41720_() == ItemsRegistry.ENCHANTERS_SHIELD.m_5456_()) {
         player.m_7292_(new MobEffectInstance((MobEffect)ModPotions.MANA_REGEN_EFFECT.get(), 200, 1));
         player.m_7292_(new MobEffectInstance((MobEffect)ModPotions.SPELL_DAMAGE_EFFECT.get(), 200, 1));
      }
   }

   @SubscribeEvent
   public static void livingHurtEvent(LivingHurtEvent e) {
      if (!e.getEntity().f_19853_.f_46443_) {
         if (e.getSource().m_7639_() instanceof LivingEntity livingUser) {
            if (livingUser instanceof Player) {
               return;
            }

            if (livingUser.m_21120_(InteractionHand.MAIN_HAND).m_41720_() instanceof EnchantersSword
               && BlockUtil.distanceFrom(livingUser.f_19825_, e.getEntity().f_19825_) < 3.0) {
               livingUser.m_21120_(InteractionHand.MAIN_HAND).m_41720_().m_7579_(livingUser.m_21205_(), e.getEntity(), livingUser);
            }
         }
      }
   }

   @SubscribeEvent
   public static void livingAttackEvent(LivingAttackEvent e) {
      if (e.getSource() == DamageSource.f_19309_ && e.getEntity() != null && !e.getEntity().m_20193_().f_46443_) {
         Level world = e.getEntity().f_19853_;
         if (world.m_8055_(e.getEntity().m_20183_()).m_60734_() instanceof LavaLily) {
            e.setCanceled(true);
         }
      }
   }

   @SubscribeEvent
   public static void livingSpawnEvent(CheckSpawn checkSpawn) {
      if (checkSpawn.getLevel() instanceof Level level
         && !level.f_46443_
         && RitualEventQueue.getRitual(level, DenySpawnRitual.class, ritu -> ritu.denySpawn(checkSpawn)) != null) {
         checkSpawn.setResult(Result.DENY);
      }
   }

   @SubscribeEvent
   public static void jumpEvent(LivingJumpEvent e) {
      if (e.getEntity() != null && e.getEntity().m_21023_((MobEffect)ModPotions.SNARE_EFFECT.get())) {
         e.getEntity().m_20334_(0.0, 0.0, 0.0);
      }
   }

   @SubscribeEvent
   public static void playerLogin(PlayerLoggedInEvent e) {
      if (!e.getEntity().m_20193_().f_46443_) {
         if (e.getEntity() instanceof ServerPlayer serverPlayer) {
            boolean isContributor = Rewards.CONTRIBUTORS.contains(serverPlayer.m_20148_());
            if (isContributor) {
               Networking.sendToPlayerClient(new PacketJoinedServer(true), (ServerPlayer)e.getEntity());
            }
         }

         CompoundTag tag = e.getEntity().getPersistentData().m_128469_("PlayerPersisted");
         String book_tag = "an_book_";
         if (!tag.m_128471_(book_tag) && (Boolean)Config.SPAWN_BOOK.get()) {
            Player entity = e.getEntity();
            ItemHandlerHelper.giveItemToPlayer(entity, new ItemStack(ItemsRegistry.WORN_NOTEBOOK));
            tag.m_128379_(book_tag, true);
            e.getEntity().getPersistentData().m_128365_("PlayerPersisted", tag);
         }
      }
   }

   @SubscribeEvent
   public static void clientTickEnd(ClientTickEvent event) {
      if (event.phase == Phase.END) {
         ClientInfo.ticksInGame++;
         if (ClientInfo.redTicks()) {
            ClientInfo.redOverlayTicks--;
         }
      }
   }

   @SubscribeEvent(
      priority = EventPriority.LOWEST
   )
   public static void onGlideTick(PlayerTickEvent event) {
      if (ArsNouveau.caelusLoaded && EffectGlide.canGlide(event.player)) {
         CaelusHandler.setFlying(event.player);
      }

      if (event.player.m_21023_((MobEffect)ModPotions.FLIGHT_EFFECT.get())
         && event.player.f_19853_.m_46467_() % 20L == 0L
         && event.player.m_21124_((MobEffect)ModPotions.FLIGHT_EFFECT.get()).m_19557_() <= 600
         && event.player instanceof ServerPlayer serverPlayer) {
         RitualEventQueue.getRitual(event.player.f_19853_, RitualFlight.class, flight -> flight.attemptRefresh(serverPlayer));
      }

      if (event.player.f_19853_.m_46467_() % (long)RitualGravity.renewInterval == 0L && event.player instanceof ServerPlayer serverPlayer) {
         MobEffectInstance gravity = event.player.m_21124_((MobEffect)ModPotions.GRAVITY_EFFECT.get());
         if (gravity == null || gravity.m_19557_() <= RitualGravity.renewThreshold) {
            RitualEventQueue.getRitual(event.player.f_19853_, RitualGravity.class, ritual -> !serverPlayer.m_7500_() && ritual.attemptRefresh(serverPlayer));
         }
      }
   }

   @SubscribeEvent
   public static void onJump(LivingJumpEvent event) {
      if (!event.getEntity().f_19853_.f_46443_ && event.getEntity() instanceof Player entity) {
         RitualEventQueue.getRitual(entity.f_19853_, RitualFlight.class, flight -> flight.onJumpEvent(event));
      }
   }

   @SubscribeEvent
   public static void entityHurt(LivingHurtEvent e) {
      if (e.getEntity() != null
         && e.getEntity().m_21023_((MobEffect)ModPotions.DEFENCE_EFFECT.get())
         && (e.getSource() == DamageSource.f_19319_ || e.getSource() == DamageSource.f_19318_ || e.getSource() instanceof EntityDamageSource)
         && (double)e.getAmount() > 0.5) {
         e.setAmount(
            (float)Math.max(0.5, (double)(e.getAmount() - 1.0F - (float)e.getEntity().m_21124_((MobEffect)ModPotions.DEFENCE_EFFECT.get()).m_19564_()))
         );
      }

      if (e.getEntity() != null && e.getSource() == DamageSource.f_19306_ && e.getEntity().m_21023_((MobEffect)ModPotions.SHOCKED_EFFECT.get())) {
         float damage = e.getAmount() + 3.0F + 3.0F * (float)e.getEntity().m_21124_((MobEffect)ModPotions.SHOCKED_EFFECT.get()).m_19564_();
         e.setAmount(Math.max(0.0F, damage));
      }

      LivingEntity entity = e.getEntity();
      if (entity != null
         && entity.m_21023_((MobEffect)ModPotions.HEX_EFFECT.get())
         && (
            entity.m_21023_(MobEffects.f_19614_)
               || entity.m_21023_(MobEffects.f_19615_)
               || entity.m_6060_()
               || entity.m_21023_((MobEffect)ModPotions.SHOCKED_EFFECT.get())
         )) {
         e.setAmount(e.getAmount() + 0.5F + 0.33F * (float)entity.m_21124_((MobEffect)ModPotions.HEX_EFFECT.get()).m_19564_());
      }

      if (entity != null) {
         double warding = PerkUtil.valueOrZero(entity, (Attribute)PerkAttributes.WARDING.get());
         double feather = PerkUtil.valueOrZero(entity, (Attribute)PerkAttributes.FEATHER.get());
         if (e.getSource().m_19387_()) {
            e.setAmount((float)((double)e.getAmount() - warding));
         }

         if (e.getSource().m_146707_()) {
            e.setAmount((float)((double)e.getAmount() - (double)e.getAmount() * feather));
         }
      }
   }

   @SubscribeEvent
   public static void fallEvent(LivingFallEvent fallEvent) {
      if (fallEvent.getEntity() instanceof Player player) {
         double var4 = (double)PerkUtil.countForPerk(JumpHeightPerk.INSTANCE, player);
         fallEvent.setDistance((float)((double)fallEvent.getDistance() - var4 / 0.1));
         if (CuriosUtil.hasItem(fallEvent.getEntity(), ItemsRegistry.BELT_OF_LEVITATION.m_5456_())) {
            fallEvent.setDistance(Math.max(0.0F, fallEvent.getDistance() - 6.0F));
         }
      }
   }

   @SubscribeEvent
   public static void entityHeal(LivingHealEvent e) {
      LivingEntity entity = e.getEntity();
      if (entity != null && entity.m_21023_((MobEffect)ModPotions.HEX_EFFECT.get())) {
         e.setAmount(e.getAmount() / 2.0F);
      }

      if (entity != null && entity.m_21023_((MobEffect)ModPotions.RECOVERY_EFFECT.get())) {
         e.setAmount(e.getAmount() + 1.0F + (float)entity.m_21124_((MobEffect)ModPotions.RECOVERY_EFFECT.get()).m_19564_());
      }
   }

   @SubscribeEvent
   public static void eatEvent(Finish event) {
      if (!event.getEntity().f_19853_.f_46443_
         && event.getItem().m_41720_().m_41473_() != null
         && event.getItem().m_41720_().m_41472_()
         && event.getEntity() instanceof Player player) {
         FoodData stats = player.m_36324_();
         stats.f_38697_ = (float)((double)stats.f_38697_ * PerkUtil.perkValue(player, (Attribute)PerkAttributes.WHIRLIESPRIG.get()));
      }
   }

   @SubscribeEvent
   public static void dispelEvent(DispelEvent event) {
      if (event.rayTraceResult instanceof EntityHitResult hit && hit.m_82443_() instanceof Witch entity && entity.m_21223_() <= entity.m_21233_() / 2.0F) {
         entity.m_142687_(RemovalReason.KILLED);
         ParticleUtil.spawnPoof((ServerLevel)event.world, entity.m_20183_());
         event.world.m_7967_(new ItemEntity(event.world, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), new ItemStack(ItemsRegistry.WIXIE_SHARD)));
      }
   }

   @SubscribeEvent
   public static void commandRegister(RegisterCommandsEvent event) {
      ResetCommand.register(event.getDispatcher());
      DataDumpCommand.register(event.getDispatcher());
      PathCommand.register(event.getDispatcher());
      ToggleLightCommand.register(event.getDispatcher());
      AddTomeCommand.register(event.getDispatcher());
      SummonAnimHeadCommand.register(event.getDispatcher());
   }

   @SubscribeEvent
   public static void registerTrades(VillagerTradesEvent event) {
      if (event.getType() == VillagerRegistry.SHARDS_TRADER.get()) {
         Int2ObjectMap<List<ItemListing>> trades = event.getTrades();
         List<ItemListing> level1 = (List<ItemListing>)trades.get(1);
         List<ItemListing> level2 = (List<ItemListing>)trades.get(2);
         List<ItemListing> level3 = (List<ItemListing>)trades.get(3);
         List<ItemListing> level4 = (List<ItemListing>)trades.get(4);
         List<ItemListing> level5 = (List<ItemListing>)trades.get(5);
         level1.add((trader, rand) -> itemToEmer(BlockRegistry.SOURCEBERRY_BUSH, 16, 16, 2));
         level1.add((trader, rand) -> itemToEmer(ItemsRegistry.MAGE_FIBER, 16, 16, 2));
         level1.add((trader, rand) -> itemToEmer(BlockRegistry.BOMBEGRANTE_POD, 6, 16, 2));
         level1.add((trader, rand) -> itemToEmer(BlockRegistry.MENDOSTEEN_POD, 6, 16, 2));
         level1.add((trader, rand) -> itemToEmer(BlockRegistry.FROSTAYA_POD, 6, 16, 2));
         level1.add((trader, rand) -> itemToEmer(BlockRegistry.BASTION_POD, 6, 16, 2));
         level1.add((trader, rand) -> itemToEmer(Items.f_151049_, 32, 16, 2));
         level1.add((trader, rand) -> emerToItem(ItemsRegistry.SOURCE_BERRY_ROLL, 4, 16, 2));
         level2.add((trader, rand) -> emerToItem(BlockRegistry.GHOST_WEAVE, 1, 8, 2));
         level2.add((trader, rand) -> emerToItem(BlockRegistry.MIRROR_WEAVE, 1, 8, 2));
         level2.add((trader, rand) -> emerToItem(BlockRegistry.FALSE_WEAVE, 1, 8, 2));
         level2.add((trader, rand) -> emerToItem(ItemsRegistry.WARP_SCROLL, 1, 8, 2));

         for (ItemStack wilden : Ingredient.m_204132_(ItemTagProvider.WILDEN_DROP_TAG).m_43908_()) {
            level2.add((trader, rand) -> itemToEmer(wilden.m_41720_(), 4, 8, 12));
         }

         for (RitualTablet tablet : new ArrayList<>(ArsNouveauAPI.getInstance().getRitualItemMap().values())) {
            if (tablet.ritual.canBeTraded()) {
               level3.add((trader, rand) -> emerToItem(tablet, 4, 1, 12));
            }
         }

         for (ItemStack shard : Ingredient.m_204132_(ItemTagProvider.SUMMON_SHARDS_TAG).m_43908_()) {
            level4.add((trader, rand) -> emerToItem(shard.m_41720_(), 20, 1, 20));
         }

         level5.add((trader, rand) -> emerToItem(ItemsRegistry.SOURCE_BERRY_PIE, 4, 8, 2));
         level5.add(
            (trader, rand) -> new MerchantOffer(new ItemStack(Items.f_42616_, 48), DungeonLootTables.getRandomItem(DungeonLootTables.RARE_LOOT), 1, 20, 0.2F)
         );
      }
   }

   public static MerchantOffer emerToItem(ItemLike itemLike, int cost, int uses, int exp) {
      return new ItemsForEmeralds(itemLike.m_5456_(), cost, uses, exp).m_213663_(null, null);
   }

   public static MerchantOffer itemToEmer(ItemLike itemLike, int cost, int uses, int exp) {
      return new EmeraldForItems(itemLike.m_5456_(), cost, uses, exp).m_213663_(null, null);
   }

   @SubscribeEvent
   public static void onLootingEvent(LootingLevelEvent event) {
      if (event.getDamageSource() != null && event.getDamageSource().m_7639_() instanceof Player living) {
         event.setLootingLevel(event.getLootingLevel() + Math.round((float)PerkUtil.countForPerk(LootingPerk.INSTANCE, living)));
      }
   }

   @SubscribeEvent
   public static void potionEvent(Added event) {
      LivingEntity target = event.getEntity();
      Entity applier = event.getEffectSource();
      if (!target.f_19853_.f_46443_) {
         double bonus = 0.0;
         if (event.getEffectInstance().m_19544_().m_19486_()) {
            bonus = PerkUtil.valueOrZero(target, (Attribute)PerkAttributes.WIXIE.get());
         } else if (applier instanceof LivingEntity living) {
            bonus = PerkUtil.valueOrZero(living, (Attribute)PerkAttributes.WIXIE.get());
         }

         if (bonus > 0.0) {
            MobEffectInstance var10000 = event.getEffectInstance();
            var10000.f_19503_ = (int)((double)var10000.f_19503_ * bonus);
         }
      }
   }

   @SubscribeEvent
   public static void treeGrow(SaplingGrowTreeEvent event) {
      if (event.getLevel() instanceof ServerLevel level) {
         Set var8 = Whirlisprig.WHIRLI_MAP.getEntities(level);
         ArrayList sprigsToRemove = new ArrayList();

         for (UUID uuid : var8) {
            Entity entity = level.m_8791_(uuid);
            if (entity != null && entity instanceof Whirlisprig) {
               Whirlisprig whirlisprig = (Whirlisprig)entity;
               if (BlockUtil.distanceFrom(entity.m_20183_(), event.getPos()) <= 10.0 && !whirlisprig.isTamed()) {
                  whirlisprig.droppingShards = true;
               }
            } else {
               sprigsToRemove.add(uuid);
            }
         }

         for (UUID uuidx : sprigsToRemove) {
            Whirlisprig.WHIRLI_MAP.removeEntity(level, uuidx);
         }
      }
   }

   private EventHandler() {
   }
}
