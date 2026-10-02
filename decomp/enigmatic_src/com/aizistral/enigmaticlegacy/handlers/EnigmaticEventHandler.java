package com.aizistral.enigmaticlegacy.handlers;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.api.capabilities.EnigmaticCapabilities;
import com.aizistral.enigmaticlegacy.api.capabilities.IPlaytimeCounter;
import com.aizistral.enigmaticlegacy.api.capabilities.PlayerPlaytimeCounter;
import com.aizistral.enigmaticlegacy.api.events.EndPortalActivatedEvent;
import com.aizistral.enigmaticlegacy.api.events.EnterBlockEvent;
import com.aizistral.enigmaticlegacy.api.events.SummonedEntityEvent;
import com.aizistral.enigmaticlegacy.api.items.ICursed;
import com.aizistral.enigmaticlegacy.api.quack.IAbyssalHeartBearer;
import com.aizistral.enigmaticlegacy.api.quack.IProperShieldUser;
import com.aizistral.enigmaticlegacy.client.Quote;
import com.aizistral.enigmaticlegacy.config.OmniconfigHandler;
import com.aizistral.enigmaticlegacy.effects.MoltenHeartEffect;
import com.aizistral.enigmaticlegacy.enchantments.CeaselessEnchantment;
import com.aizistral.enigmaticlegacy.entities.PermanentItemEntity;
import com.aizistral.enigmaticlegacy.gui.EnderChestInventoryButton;
import com.aizistral.enigmaticlegacy.gui.PermadeathScreen;
import com.aizistral.enigmaticlegacy.gui.ToggleMagnetEffectsButton;
import com.aizistral.enigmaticlegacy.helpers.BlueSkiesHelper;
import com.aizistral.enigmaticlegacy.helpers.EnigmaticEnchantmentHelper;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.helpers.ItemNBTHelper;
import com.aizistral.enigmaticlegacy.helpers.PotionHelper;
import com.aizistral.enigmaticlegacy.items.AngelBlessing;
import com.aizistral.enigmaticlegacy.items.AvariceScroll;
import com.aizistral.enigmaticlegacy.items.BerserkEmblem;
import com.aizistral.enigmaticlegacy.items.BlazingCore;
import com.aizistral.enigmaticlegacy.items.CosmicScroll;
import com.aizistral.enigmaticlegacy.items.CursedRing;
import com.aizistral.enigmaticlegacy.items.CursedScroll;
import com.aizistral.enigmaticlegacy.items.EnderSlayer;
import com.aizistral.enigmaticlegacy.items.EnigmaticAmulet;
import com.aizistral.enigmaticlegacy.items.EyeOfNebula;
import com.aizistral.enigmaticlegacy.items.ForbiddenAxe;
import com.aizistral.enigmaticlegacy.items.ForbiddenFruit;
import com.aizistral.enigmaticlegacy.items.HunterGuidebook;
import com.aizistral.enigmaticlegacy.items.InfernalShield;
import com.aizistral.enigmaticlegacy.items.MiningCharm;
import com.aizistral.enigmaticlegacy.items.MonsterCharm;
import com.aizistral.enigmaticlegacy.items.OceanStone;
import com.aizistral.enigmaticlegacy.items.RevelationTome;
import com.aizistral.enigmaticlegacy.items.TheInfinitum;
import com.aizistral.enigmaticlegacy.items.TheTwist;
import com.aizistral.enigmaticlegacy.items.VoidPearl;
import com.aizistral.enigmaticlegacy.items.generic.ItemSpellstoneCurio;
import com.aizistral.enigmaticlegacy.mixin.AccessorAbstractArrowEntity;
import com.aizistral.enigmaticlegacy.mixin.AccessorAdvancementCommands;
import com.aizistral.enigmaticlegacy.objects.CooldownMap;
import com.aizistral.enigmaticlegacy.objects.DamageSourceNemesisCurse;
import com.aizistral.enigmaticlegacy.objects.DimensionalPosition;
import com.aizistral.enigmaticlegacy.objects.Perhaps;
import com.aizistral.enigmaticlegacy.objects.QuarkHelper;
import com.aizistral.enigmaticlegacy.objects.RegisteredMeleeAttack;
import com.aizistral.enigmaticlegacy.objects.TransientPlayerData;
import com.aizistral.enigmaticlegacy.objects.Vector3;
import com.aizistral.enigmaticlegacy.packets.clients.PacketCosmicRevive;
import com.aizistral.enigmaticlegacy.packets.clients.PacketForceArrowRotations;
import com.aizistral.enigmaticlegacy.packets.clients.PacketPatchouliForce;
import com.aizistral.enigmaticlegacy.packets.clients.PacketPermadeath;
import com.aizistral.enigmaticlegacy.packets.clients.PacketPortalParticles;
import com.aizistral.enigmaticlegacy.packets.clients.PacketRecallParticles;
import com.aizistral.enigmaticlegacy.packets.clients.PacketSetEntryState;
import com.aizistral.enigmaticlegacy.packets.clients.PacketSlotUnlocked;
import com.aizistral.enigmaticlegacy.packets.clients.PacketSyncPlayTime;
import com.aizistral.enigmaticlegacy.packets.clients.PacketWitherParticles;
import com.aizistral.enigmaticlegacy.packets.server.PacketAnvilField;
import com.aizistral.enigmaticlegacy.packets.server.PacketEnchantingGUI;
import com.aizistral.enigmaticlegacy.packets.server.PacketEnderRingKey;
import com.aizistral.enigmaticlegacy.packets.server.PacketToggleMagnetEffects;
import com.aizistral.enigmaticlegacy.registries.EnigmaticBlocks;
import com.aizistral.enigmaticlegacy.registries.EnigmaticEffects;
import com.aizistral.enigmaticlegacy.registries.EnigmaticEnchantments;
import com.aizistral.enigmaticlegacy.registries.EnigmaticItems;
import com.aizistral.enigmaticlegacy.registries.EnigmaticPotions;
import com.aizistral.enigmaticlegacy.registries.EnigmaticSounds;
import com.aizistral.enigmaticlegacy.triggers.BeheadingTrigger;
import com.aizistral.enigmaticlegacy.triggers.ForbiddenFruitTrigger;
import com.aizistral.enigmaticlegacy.triggers.RevelationTomeBurntTrigger;
import com.aizistral.omniconfig.wrappers.Omniconfig;
import com.aizistral.omniconfig.wrappers.OmniconfigWrapper;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.WeakHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.ServerRecipeBook;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.Tuple;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemDamageFunction;
import net.minecraft.world.level.storage.loot.functions.SetNbtFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderNameTagEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent.Pre;
import net.minecraftforge.client.event.RenderTooltipEvent.Color;
import net.minecraftforge.client.event.ScreenEvent.Init;
import net.minecraftforge.client.event.ScreenEvent.Init.Post;
import net.minecraftforge.client.event.ViewportEvent.RenderFog;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.enchanting.EnchantmentLevelSetEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.EntityTeleportEvent.EnderEntity;
import net.minecraftforge.event.entity.EntityTeleportEvent.EnderPearl;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.living.LivingGetProjectileEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
import net.minecraftforge.event.entity.living.MobEffectEvent.Applicable;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.event.entity.player.PlayerEvent.HarvestCheck;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PacketDistributor.TargetPoint;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.server.ServerLifecycleHooks;
import top.theillusivec4.caelus.api.RenderCapeEvent;
import top.theillusivec4.curios.api.event.DropRulesEvent;
import top.theillusivec4.curios.api.type.capability.ICurio.DropRule;
import top.theillusivec4.curios.client.gui.CuriosScreen;

@EventBusSubscriber(
   modid = "enigmaticlegacy"
)
public class EnigmaticEventHandler {
   private static final String NBT_KEY_PATCHOULIFORCE = "enigmaticlegacy.patchouliforce";
   private static final String NBT_KEY_ENIGMATICGIFT = "enigmaticlegacy.firstjoin";
   private static final String NBT_KEY_CURSEDGIFT = "enigmaticlegacy.cursedgift";
   private static final String NBT_KEY_ENABLESPELLSTONE = "enigmaticlegacy.spellstones_enabled";
   private static final String NBT_KEY_ENABLERING = "enigmaticlegacy.rings_enabled";
   private static final String NBT_KEY_ENABLESCROLL = "enigmaticlegacy.scrolls_enabled";
   public static final ResourceLocation FIREBAR_LOCATION = new ResourceLocation("enigmaticlegacy", "textures/gui/firebar.png");
   public static final ResourceLocation ICONS_LOCATION = new ResourceLocation("enigmaticlegacy", "textures/gui/generic_icons.png");
   public static final CooldownMap DEFERRED_TOASTS = new CooldownMap();
   public static final List<Toast> SCHEDULED_TOASTS = new ArrayList<>();
   public static final Map<LivingEntity, Float> KNOCKBACK_THAT_BASTARD = new WeakHashMap<>();
   public static final Random THEY_SEE_ME_ROLLIN = new Random();
   public static final Multimap<Player, Item> POSTMORTAL_POSESSIONS = ArrayListMultimap.create();
   public static final Multimap<Player, Guardian> AGERED_GUARDIANS = ArrayListMultimap.create();
   public static final Map<Player, AABB> DESOLATION_BOXES = new WeakHashMap<>();
   public static final Map<Player, Float> LAST_HEALTH = new WeakHashMap<>();
   public static final Map<Player, Integer> LAST_SOUL_COMPASS_UPDATE = new WeakHashMap<>();
   public static final List<ServerPlayer> SCHEDULED_DATA_SYNC = new ArrayList<>();
   public static int scheduledCubeRevive = -1;
   public static boolean isPoisonHurt = false;
   public static boolean isApplyingNightVision = false;
   private long clientWorldTicks = 0L;

   @SubscribeEvent
   public void onGetProjectile(LivingGetProjectileEvent event) {
      ItemStack weapon = event.getProjectileWeaponItemStack();
      if (weapon.m_41720_() instanceof CrossbowItem && weapon.getEnchantmentLevel(EnigmaticEnchantments.CEASELESS) > 0) {
         ItemStack arrows = event.getProjectileItemStack();
         if (arrows.m_41619_() && CeaselessEnchantment.allowNoArrow.getValue()) {
            event.setProjectileItemStack(new ItemStack(Items.f_42412_, 64));
         } else if (arrows.m_41720_() instanceof ArrowItem) {
            ItemStack arrowsCopy = arrows.m_41777_();
            arrowsCopy.m_41764_(64);
            event.setProjectileItemStack(arrowsCopy);
         }
      }
   }

   @SubscribeEvent
   public void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
      if (event.getObject() instanceof Player player) {
         event.addCapability(EnigmaticCapabilities.ID_PLAYTIME_COUNTER, new PlayerPlaytimeCounter.Provider(player));
      }
   }

   @SubscribeEvent
   @OnlyIn(Dist.CLIENT)
   public void renderNameplate(RenderNameTagEvent event) {
      if (event.getEntity() == Minecraft.m_91087_().f_91074_) {
         Player player = Minecraft.m_91087_().f_91074_;
         ItemStack insignia = SuperpositionHandler.getCurioStack(player, EnigmaticItems.INSIGNIA);
         if (insignia != null && ItemNBTHelper.getBoolean(insignia, "tagDisplayEnabled", true)) {
            event.setResult(Result.ALLOW);
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   @SubscribeEvent(
      priority = EventPriority.LOWEST
   )
   public void onTooltip(ItemTooltipEvent event) {
      if (event.getEntity() != null && !event.getEntity().m_7500_()) {
         if (event.getItemStack().m_150930_(EnigmaticItems.CURSED_RING)) {
            if (CursedRing.concealAbilities.getValue() && !SuperpositionHandler.isTheCursedOne(event.getEntity())) {
               SuperpositionHandler.obscureTooltip(event.getToolTip());
            }
         } else if (event.getItemStack().m_41720_() instanceof ICursed && !SuperpositionHandler.isTheCursedOne(event.getEntity())) {
            event.getToolTip().replaceAll(component -> {
               if (component.m_214077_() instanceof TranslatableContents loc && loc.m_237508_().startsWith("tooltip.enigmaticlegacy.cursedOnesOnly")) {
                  return component;
               }

               return Component.m_237113_(SuperpositionHandler.obscureString(component.getString())).m_130948_(component.m_7383_());
            });
         }
      }
   }

   @SubscribeEvent
   @OnlyIn(Dist.CLIENT)
   public void renderCape(RenderCapeEvent event) {
      if (SuperpositionHandler.hasEnigmaticElytra(event.getEntity())) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   @OnlyIn(Dist.CLIENT)
   public void onTooltipRendering(Color event) {
      ItemStack stack = event.getItemStack();
      if (stack != null && !stack.m_41619_() && ForgeRegistries.ITEMS.getKey(stack.m_41720_()).m_135827_().equals("enigmaticlegacy")) {
         int background = -267386864;
         int borderStart = 1347420415;
         int borderEnd = 1344798847;
         if (!(stack.m_41720_() instanceof ICursed) && !(stack.m_41720_() instanceof CursedRing)) {
            if (stack.m_41720_() == EnigmaticItems.COSMIC_SCROLL) {
               background = -267386864;
               borderStart = -1331167064;
               borderEnd = (borderStart & 4079166) >> 1 | borderStart & 0xFF000000;
            }
         } else {
            background = -149942256;
            borderStart = 1358892032;
            borderEnd = borderStart;
         }

         event.setBackground(background);
         event.setBorderStart(borderStart);
         event.setBorderEnd(borderEnd);
      }
   }

   @SubscribeEvent
   public void onCommandRegistry(RegisterCommandsEvent event) {
      LiteralArgumentBuilder<CommandSourceStack> builder = (LiteralArgumentBuilder<CommandSourceStack>)Commands.m_82127_("haveadv")
         .requires(source -> source.m_6761_(2));
      builder.then(
         Commands.m_82129_("advancement", ResourceLocationArgument.m_106984_())
            .suggests(AccessorAdvancementCommands.getAdvancementSuggestions())
            .executes(source -> {
               ServerPlayer player = ((CommandSourceStack)source.getSource()).m_81375_();
               Advancement adv = ResourceLocationArgument.m_106987_(source, "advancement");
               if (adv != null) {
                  boolean have = SuperpositionHandler.hasAdvancement(player, adv.m_138327_());
                  MutableComponent reply = Component.m_237113_("Advancement exists, and you " + (have ? "do" : "don't") + " have it.");
                  ((CommandSourceStack)source.getSource()).m_81354_(reply.m_130940_(have ? ChatFormatting.GREEN : ChatFormatting.RED), true);
                  return 1;
               } else {
                  MutableComponent reply = Component.m_237113_("Advancement does not exist.");
                  ((CommandSourceStack)source.getSource()).m_81352_(reply);
                  return 0;
               }
            })
      );
      event.getDispatcher().register(builder);
   }

   @SubscribeEvent
   public void onEnderPearlTeleport(EnderPearl event) {
      if (SuperpositionHandler.hasCurio(event.getPlayer(), EnigmaticItems.EYE_OF_NEBULA)
         || SuperpositionHandler.hasCurio(event.getPlayer(), EnigmaticItems.THE_CUBE)) {
         event.setAttackDamage(0.0F);
      }
   }

   @SubscribeEvent
   public void onEnderTeleport(EnderEntity event) {
      if (event.getEntity().getPersistentData().m_128441_("ELTeleportBlock")) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public void onApplyPotion(Applicable event) {
      if (event.getEntity() instanceof Player) {
         Player player = (Player)event.getEntity();
         MobEffectInstance effect = event.getEffectInstance();
         if (isApplyingNightVision) {
            return;
         }

         if (ForgeRegistries.MOB_EFFECTS.getKey(effect.m_19544_()).equals(new ResourceLocation("mana-and-artifice", "chrono-exhaustion"))) {
            return;
         }

         if (SuperpositionHandler.hasCurio(player, EnigmaticItems.VOID_PEARL)) {
            event.setResult(Result.DENY);
         } else if ((SuperpositionHandler.hasCurio(player, EnigmaticItems.ENIGMATIC_ITEM) || SuperpositionHandler.hasCurio(player, EnigmaticItems.THE_CUBE))
            && !effect.m_19544_().m_19486_()) {
            event.setResult(Result.DENY);
         }
      }
   }

   @SubscribeEvent
   public void serverStarted(ServerStartedEvent event) {
   }

   @OnlyIn(Dist.CLIENT)
   @SubscribeEvent(
      receiveCanceled = true
   )
   public void onOverlayRender(Pre event) {
      Minecraft mc = Minecraft.m_91087_();
      if (event.getOverlay().equals(VanillaGuiOverlay.EXPERIENCE_BAR.type())) {
         TransientPlayerData data = TransientPlayerData.get(mc.f_91074_);
         if (data.getFireImmunityTimer() <= 0 || !SuperpositionHandler.hasCurio(mc.f_91074_, EnigmaticItems.BLAZING_CORE)) {
            return;
         }

         float partialTick = event.getPartialTick();
         float barFiller = data.getFireImmunityFraction(partialTick);
         PoseStack PoseStack = event.getPoseStack();
         int x = event.getWindow().m_85445_() / 2 - 91;
         int xCorrection = 0;
         int yCorrection = 0;
         boolean renderXP = false;
         if (!renderXP) {
            event.setCanceled(true);
         }

         RenderSystem.m_157456_(0, FIREBAR_LOCATION);
         int k = (int)(barFiller * 183.0F);
         int l = event.getWindow().m_85446_() - 32 + 3;
         GuiComponent.m_93133_(PoseStack, x + xCorrection, l + yCorrection, 0.0F, 0.0F, 182, 5, 256, 256);
         if (k > 0) {
            GuiComponent.m_93133_(PoseStack, x + xCorrection, l + yCorrection, 0.0F, 5.0F, k, 5, 256, 256);
         }

         String title = I18n.m_118938_("gui.enigmaticlegacy.blazing_core_bar_title", new Object[0]);
         l = (event.getWindow().m_85445_() - mc.f_91062_.m_92895_(title)) / 2;
         int j1 = event.getWindow().m_85446_() - 31 - 4;
         int stringXCorrection = 0;
         int stringYCorrection = 0;

         try {
            stringXCorrection = Integer.parseInt(I18n.m_118938_("gui.enigmaticlegacy.blazing_core_bar_offsetX", new Object[0]));
            stringYCorrection = Integer.parseInt(I18n.m_118938_("gui.enigmaticlegacy.blazing_core_bar_offsetY", new Object[0]));
         } catch (Exception var19) {
         }

         l += xCorrection + stringXCorrection;
         j1 += yCorrection + stringYCorrection;
         int boundaryColor = 5832704;
         mc.f_91062_.m_92883_(PoseStack, title, (float)(l + 1), (float)j1, boundaryColor);
         mc.f_91062_.m_92883_(PoseStack, title, (float)(l - 1), (float)j1, boundaryColor);
         mc.f_91062_.m_92883_(PoseStack, title, (float)l, (float)(j1 + 1), boundaryColor);
         mc.f_91062_.m_92883_(PoseStack, title, (float)l, (float)(j1 - 1), boundaryColor);
         mc.f_91062_.m_92883_(PoseStack, title, (float)l, (float)j1, 16770638);
      } else if (event.getOverlay().equals(VanillaGuiOverlay.AIR_LEVEL.type())) {
         if ((
               SuperpositionHandler.hasCurio(mc.f_91074_, EnigmaticItems.OCEAN_STONE)
                  || SuperpositionHandler.hasCurio(mc.f_91074_, EnigmaticItems.VOID_PEARL)
                  || SuperpositionHandler.hasCurio(mc.f_91074_, EnigmaticItems.THE_CUBE)
            )
            && OceanStone.preventOxygenBarRender.getValue()) {
            event.setCanceled(true);
         }
      } else if (event.getOverlay().equals(VanillaGuiOverlay.FOOD_LEVEL.type()) && EnigmaticItems.FORBIDDEN_FRUIT.haveConsumedFruit(mc.f_91074_)) {
         if (!ForbiddenFruit.renderHungerBar.getValue()) {
            event.setCanceled(true);
            return;
         }

         if (mc.f_91074_.m_7500_() || mc.f_91074_.m_5833_() || !ForbiddenFruit.replaceHungerBar.getValue()) {
            return;
         }

         event.setCanceled(true);
         RenderSystem.m_157456_(0, ICONS_LOCATION);
         int width = event.getWindow().m_85445_();
         int height = event.getWindow().m_85446_();
         Player player = mc.f_91074_;
         RenderSystem.m_69478_();
         int left = width / 2 + 91;
         int top = height - ((ForgeGui)Minecraft.m_91087_().f_91065_).rightHeight;
         ((ForgeGui)Minecraft.m_91087_().f_91065_).rightHeight += 10;
         boolean unused = false;
         FoodData stats = mc.f_91074_.m_36324_();
         int level = stats.m_38702_();
         int barPx = 8;
         int barNum = 10;

         for (int i = 0; i < barNum; i++) {
            int idx = i * 2 + 1;
            int xx = left - i * barPx - 9;
            int y = top;
            int icon = 16;
            byte background = 0;
            if (player.m_36324_().m_38722_() <= 0.0F && mc.f_91065_.m_93079_() % (level * 3 + 1) == 0) {
               y = top + (THEY_SEE_ME_ROLLIN.nextInt(3) - 1);
            }

            mc.f_91065_.m_93228_(event.getPoseStack(), xx, y, 0, 0, 9, 9);
         }

         RenderSystem.m_69461_();
         RenderSystem.m_157456_(0, GuiComponent.f_93098_);
      }
   }

   @SubscribeEvent
   public void onEnchantmentLevelSet(EnchantmentLevelSetEvent event) {
      BlockPos where = event.getPos();
      boolean shouldBoost = false;
      int radius = 16;

      for (Player player : event.getLevel().m_45976_(Player.class, new AABB(where.m_7918_(-radius, -radius, -radius), where.m_7918_(radius, radius, radius)))) {
         if (SuperpositionHandler.isTheCursedOne(player)) {
            shouldBoost = true;
         }
      }

      if (shouldBoost) {
         event.setEnchantLevel(event.getEnchantLevel() + CursedRing.enchantingBonus.getValue());
      }
   }

   @SubscribeEvent(
      receiveCanceled = true
   )
   public void onItemBurnt(FurnaceFuelBurnTimeEvent event) {
      if (event.getItemStack() != null
         && event.getItemStack().m_41720_() instanceof RevelationTome
         && ServerLifecycleHooks.getCurrentServer() != null
         && ItemNBTHelper.verifyExistance(event.getItemStack(), "lastHolder")) {
         ServerPlayer player = ServerLifecycleHooks.getCurrentServer()
            .m_6846_()
            .m_11259_(ItemNBTHelper.getUUID(event.getItemStack(), "lastHolder", Mth.m_14002_()));
         if (player != null) {
            RevelationTomeBurntTrigger.INSTANCE.trigger(player);
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   @SubscribeEvent(
      priority = EventPriority.LOW
   )
   public void onInventoryGuiInit(Post evt) {
      Screen screen = evt.getScreen();
      if (screen instanceof EnchantmentScreen || QuarkHelper.getMatrixEnchanterClass() != null && QuarkHelper.getMatrixEnchanterClass().isInstance(screen)) {
         EnigmaticLegacy.packetInstance.send(PacketDistributor.SERVER.noArg(), new PacketEnchantingGUI());
      }

      if (screen instanceof InventoryScreen || screen instanceof CreativeModeInventoryScreen || screen instanceof CuriosScreen) {
         AbstractContainerScreen<?> gui = (AbstractContainerScreen<?>)screen;
         boolean isCreative = screen instanceof CreativeModeInventoryScreen;
         EnderChestInventoryButton enderButton = new EnderChestInventoryButton(
            gui,
            0,
            0,
            20,
            18,
            0,
            0,
            19,
            new ResourceLocation("enigmaticlegacy:textures/gui/ender_chest_button.png"),
            input -> EnigmaticLegacy.packetInstance.send(PacketDistributor.SERVER.noArg(), new PacketEnderRingKey(true))
         );
         Tuple<Integer, Integer> enderOffsets = enderButton.getOffsets(isCreative);
         int x = (Integer)enderOffsets.m_14418_();
         int y = (Integer)enderOffsets.m_14419_();
         enderButton.f_93620_ = gui.getGuiLeft() + x;
         enderButton.f_93621_ = gui.getGuiTop() + y;
         evt.addListener(enderButton);
         ToggleMagnetEffectsButton magnetButton = new ToggleMagnetEffectsButton(
            gui,
            0,
            0,
            20,
            18,
            21,
            0,
            19,
            new ResourceLocation("enigmaticlegacy:textures/gui/ender_chest_button.png"),
            input -> EnigmaticLegacy.packetInstance.send(PacketDistributor.SERVER.noArg(), new PacketToggleMagnetEffects())
         );
         Tuple<Integer, Integer> magnetOffsets = magnetButton.getOffsets(isCreative);
         x = (Integer)magnetOffsets.m_14418_();
         y = (Integer)magnetOffsets.m_14419_();
         magnetButton.f_93620_ = gui.getGuiLeft() + x;
         magnetButton.f_93621_ = gui.getGuiTop() + y;
         evt.addListener(magnetButton);
         if (QuarkHelper.getMiniButtonClass() != null) {
            evt.getListenersList().forEach(listener -> {
               if (QuarkHelper.getMiniButtonClass().isInstance(listener)) {
                  Button button = (Button)listener;
                  button.f_93621_ -= 22;
               }
            });
         }
      }
   }

   @SubscribeEvent
   @OnlyIn(Dist.CLIENT)
   public void onFogRender(RenderFog event) {
      if (event.getCamera().m_167685_() == FogType.LAVA) {
         if (Minecraft.m_91087_().f_91074_.m_21023_(EnigmaticEffects.MOLTEN_HEART)) {
            RenderSystem.m_157445_(0.0F);
            RenderSystem.m_157443_((float)MoltenHeartEffect.lavafogDensity.getValue());
         } else if (SuperpositionHandler.hasCurio(Minecraft.m_91087_().f_91074_, EnigmaticItems.BLAZING_CORE)) {
            RenderSystem.m_157445_(0.0F);
            RenderSystem.m_157443_((float)BlazingCore.lavafogDensity.getValue());
         }
      }
   }

   @SubscribeEvent
   @OnlyIn(Dist.CLIENT)
   public void onEntityTick(ClientTickEvent event) {
      if (event.phase == Phase.END) {
         try {
            Player player = Minecraft.m_91087_().f_91074_;
            if (player != null && this.clientWorldTicks != player.f_19853_.m_46467_()) {
               this.clientWorldTicks = player.f_19853_.m_46467_();
               EnigmaticLegacy.PROXY.updateInfinitumCounters();
            }

            if (player == null && OmniconfigWrapper.onRemoteServer) {
               for (OmniconfigWrapper wrapper : OmniconfigWrapper.wrapperRegistry.values()) {
                  EnigmaticLegacy.LOGGER.info("Dismissing values of " + wrapper.config.getConfigFile().getName() + " in favor of local config...");

                  for (Omniconfig.GenericParameter param : wrapper.retrieveInvocationList()) {
                     if (param.isSynchronized()) {
                        String oldValue = param.valueToString();
                        param.invoke(wrapper.config);
                        EnigmaticLegacy.LOGGER
                           .info("Value of '" + param.getId() + "' was restored to '" + param.valueToString() + "'; former server-forced value: " + oldValue);
                     }
                  }
               }

               OmniconfigWrapper.onRemoteServer = false;
            }

            DEFERRED_TOASTS.tick(player);
            if (DEFERRED_TOASTS.getCooldown(player) == 1) {
               Minecraft.m_91087_().m_91300_().m_94922_(SCHEDULED_TOASTS.get(0));
               SCHEDULED_TOASTS.remove(0);
               if (SCHEDULED_TOASTS.size() > 0) {
                  DEFERRED_TOASTS.put(player, Integer.valueOf(5));
               }
            }
         } catch (Exception var8) {
         }
      }
   }

   @SubscribeEvent(
      priority = EventPriority.LOWEST
   )
   public void onLooting(LootingLevelEvent event) {
      if (event.getDamageSource() != null && event.getDamageSource().m_7639_() instanceof Player) {
         Player var2 = (Player)event.getDamageSource().m_7639_();
      }
   }

   @SubscribeEvent(
      priority = EventPriority.LOWEST
   )
   public void miningStuff(BreakSpeed event) {
      float originalSpeed = event.getOriginalSpeed();
      float correctedSpeed = originalSpeed;
      float miningBoost = 1.0F;
      if (SuperpositionHandler.hasCurio(event.getEntity(), EnigmaticItems.MINING_CHARM)) {
         miningBoost += MiningCharm.breakSpeedBonus.getValue().asModifier();
      }

      if (SuperpositionHandler.hasCurio(event.getEntity(), EnigmaticItems.CURSED_SCROLL)) {
         miningBoost += CursedScroll.miningBoost.getValue().asModifier() * (float)SuperpositionHandler.getCurseAmount(event.getEntity());
      }

      if (EnigmaticItems.ENIGMATIC_AMULET.hasColor(event.getEntity(), EnigmaticAmulet.AmuletColor.GREEN)) {
         miningBoost += 0.25F;
      }

      if (SuperpositionHandler.hasCurio(event.getEntity(), EnigmaticItems.THE_CUBE)) {
         miningBoost += 0.6F;
      }

      if (!event.getEntity().m_20096_()
         && (
            SuperpositionHandler.hasCurio(event.getEntity(), EnigmaticItems.HEAVEN_SCROLL)
               || SuperpositionHandler.hasCurio(event.getEntity(), EnigmaticItems.FABULOUS_SCROLL)
               || SuperpositionHandler.hasCurio(event.getEntity(), EnigmaticItems.ENIGMATIC_ITEM)
         )) {
         correctedSpeed = originalSpeed * 5.0F;
      }

      if (event.getEntity().isEyeInFluidType((FluidType)ForgeMod.WATER_TYPE.get())
         && !EnchantmentHelper.m_44934_(event.getEntity())
         && SuperpositionHandler.hasCurio(event.getEntity(), EnigmaticItems.OCEAN_STONE)) {
         correctedSpeed *= 5.0F;
      }

      correctedSpeed *= miningBoost;
      correctedSpeed -= event.getOriginalSpeed();
      event.setNewSpeed(event.getNewSpeed() + correctedSpeed);
   }

   @SubscribeEvent
   public void onHarvestCheck(HarvestCheck event) {
      if (event.getEntity() instanceof Player && !event.canHarvest() && SuperpositionHandler.hasCurio(event.getEntity(), EnigmaticItems.CURSED_RING)) {
      }
   }

   @SubscribeEvent
   public void onLivingKnockback(LivingKnockBackEvent event) {
      if (KNOCKBACK_THAT_BASTARD.containsKey(event.getEntity())) {
         float knockbackPower = KNOCKBACK_THAT_BASTARD.get(event.getEntity());
         event.setStrength(event.getStrength() * knockbackPower);
         KNOCKBACK_THAT_BASTARD.remove(event.getEntity());
      }

      if (event.getEntity() instanceof Player && SuperpositionHandler.hasCurio(event.getEntity(), EnigmaticItems.CURSED_RING)) {
         event.setStrength(event.getStrength() * CursedRing.knockbackDebuff.getValue().asModifier());
      }
   }

   @SubscribeEvent
   public void onEntitySpawn(CheckSpawn event) {
      if (event.getSpawnReason() == MobSpawnType.NATURAL) {
         LivingEntity entity = event.getEntity();
         if ((entity instanceof Piglin || entity instanceof ZombifiedPiglin || entity instanceof IronGolem || entity instanceof EnderMan)
            && DESOLATION_BOXES.values().stream().anyMatch(entity.m_20191_()::m_82381_)) {
            event.setResult(Result.DENY);
         }
      }
   }

   private void syncPlayTime(Player player) {
      if (!player.f_19853_.f_46443_) {
         IPlaytimeCounter counter = IPlaytimeCounter.get(player);
         counter.matchStats();
         EnigmaticLegacy.packetInstance
            .send(
               PacketDistributor.NEAR.with(() -> new TargetPoint(player.m_20185_(), player.m_20186_(), player.m_20189_(), 64.0, player.f_19853_.m_46472_())),
               new PacketSyncPlayTime(player.m_20148_(), counter.getTimeWithCurses(), counter.getTimeWithoutCurses())
            );
      }
   }

   @SubscribeEvent
   public void onPlayerTick(PlayerChangedDimensionEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         LAST_SOUL_COMPASS_UPDATE.remove(player);
         if (event.getEntity().f_19853_.m_46472_() == Level.f_46429_) {
            Quote.SULFUR_AIR.playOnceIfUnlocked(player, 240);
         } else if (event.getEntity().f_19853_.m_46472_() == Level.f_46430_) {
            Quote.TORTURED_ROCKS.playOnceIfUnlocked(player, 240);
         }
      }
   }

   @SubscribeEvent
   public void onPlayerTick(LivingTickEvent event) {
      if (event.getEntity().m_6084_()) {
         if (!event.getEntity().f_19853_.f_46443_ && (event.getEntity() instanceof EnderMan || event.getEntity() instanceof Shulker)) {
            int cooldown = event.getEntity().getPersistentData().m_128451_("ELTeleportBlock");
            if (cooldown > 0) {
               if (--cooldown > 0) {
                  event.getEntity().getPersistentData().m_128405_("ELTeleportBlock", cooldown);
               } else {
                  event.getEntity().getPersistentData().m_128473_("ELTeleportBlock");
               }
            }
         }

         if (event.getEntity() instanceof Player player) {
            if (!player.f_19853_.f_46443_) {
               SCHEDULED_DATA_SYNC.removeIf(p -> {
                  TransientPlayerData.get(p).syncToPlayer();
                  return true;
               });
               IPlaytimeCounter counter = IPlaytimeCounter.get(player);
               if (SuperpositionHandler.isTheCursedOne(player)) {
                  counter.incrementTimeWithCurses();
               } else {
                  counter.incrementTimeWithoutCurses();
               }

               if (SuperpositionHandler.hasCurio(player, EnigmaticItems.DESOLATION_RING) && SuperpositionHandler.isTheWorthyOne(player)) {
                  DESOLATION_BOXES.put(player, SuperpositionHandler.getBoundingBoxAroundEntity(player, 128.0));
               } else {
                  DESOLATION_BOXES.remove(player);
               }

               if (SuperpositionHandler.hasItem(player, EnigmaticItems.SOUL_COMPASS)) {
                  Integer lastUpdate = LAST_SOUL_COMPASS_UPDATE.get(player);
                  if (lastUpdate == null || player.f_19797_ - lastUpdate > 10) {
                     Optional<Tuple<UUID, BlockPos>> optional = SuperpositionHandler.updateSoulCompass((ServerPlayer)player);
                     optional.ifPresent(
                        tuple -> {
                           BlockPos pos = (BlockPos)tuple.m_14419_();
                           if (player.m_20275_((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_()) < 256.0) {
                              player.f_19853_.m_46745_(pos);
                              UUID id = (UUID)tuple.m_14418_();
                              int radius = 3;
                              AABB box = new AABB(
                                 (double)(pos.m_123341_() - radius),
                                 (double)(pos.m_123342_() - radius),
                                 (double)(pos.m_123343_() - radius),
                                 (double)(pos.m_123341_() + radius),
                                 (double)(pos.m_123342_() + radius),
                                 (double)(pos.m_123343_() + radius)
                              );
                              List<PermanentItemEntity> listx = player.f_19853_.m_6443_(PermanentItemEntity.class, box, entity -> entity.m_20148_().equals(id));
                              if (listx.size() <= 0) {
                                 SoulArchive.getInstance().removeItem(id);
                              }
                           }
                        }
                     );
                  }
               }

               if (player.f_19797_ % 100 == 0) {
                  this.syncPlayTime(player);
               }
            } else if (scheduledCubeRevive > 0 && !(Minecraft.m_91087_().f_91080_ instanceof ReceivingLevelScreen)) {
               scheduledCubeRevive--;
               if (scheduledCubeRevive == 0) {
                  EnigmaticLegacy.PROXY.displayReviveAnimation(player.m_19879_(), 1);
                  scheduledCubeRevive = -1;
               }
            }

            if (Float.isNaN(player.m_21223_())) {
               player.m_21153_(0.0F);
            }

            if (Float.isNaN(player.m_6103_())) {
               player.m_7911_(0.0F);
            }

            BlueSkiesHelper.maybeFixCapability(player);
            if (EnigmaticItems.FORBIDDEN_FRUIT.haveConsumedFruit(player)) {
               FoodData foodStats = player.m_36324_();
               foodStats.m_38705_(20);
               foodStats.f_38697_ = 0.0F;
               if (player.m_21023_(MobEffects.f_19612_)) {
                  player.m_21195_(MobEffects.f_19612_);
               }
            }

            if (!CursedRing.disableInsomnia.getValue() && player.m_5803_() && SuperpositionHandler.hasCurio(player, EnigmaticItems.CURSED_RING)) {
               if (player.m_36318_() == 5) {
                  if (player instanceof ServerPlayer) {
                     player.m_213846_(Component.m_237115_("message.enigmaticlegacy.cursed_sleep").m_130940_(ChatFormatting.RED));
                  }
               } else if (player.m_36318_() > 90) {
                  player.f_36110_ = 90;
               }
            }

            if (player.m_6060_() && SuperpositionHandler.hasCurio(player, EnigmaticItems.CURSED_RING)) {
               player.m_7311_(player.m_20094_() + 2);
            }

            if (SuperpositionHandler.hasCurio(player, EnigmaticItems.BLAZING_CORE) && !player.m_21220_().isEmpty()) {
               Collection<MobEffectInstance> effects = new ArrayList<>();
               effects.addAll(player.m_21220_());

               for (MobEffectInstance effect : effects) {
                  if (effect.m_19544_().equals(EnigmaticEffects.MOLTEN_HEART)) {
                     if (player.f_19797_ % 2 == 0 && effect.f_19503_ > 0) {
                        effect.f_19503_++;
                     }
                  } else {
                     effect.m_19552_(player, () -> {
                     });
                  }
               }
            }

            if (player instanceof ServerPlayer) {
               if (SuperpositionHandler.hasSpellstoneCooldown(player)) {
                  SuperpositionHandler.tickSpellstoneCooldown(player, 1);
               }

               TransientPlayerData data = TransientPlayerData.get(player);
               data.setFireImmunityTimer(data.getFireImmunityTimer() - (player.m_6060_() ? 100 : 200));
               if (data.needsSync) {
                  data.syncToPlayer();
                  data.needsSync = false;
               }

               EnigmaticItems.ENIGMATIC_ITEM.handleEnigmaticFlight(player);

               for (NonNullList<ItemStack> list : player.m_150109_().f_35979_) {
                  for (int i = 0; i < list.size(); i++) {
                     ItemStack stack = (ItemStack)list.get(i);
                     if (stack != null) {
                        if (stack.m_150930_(EnigmaticItems.UNWITNESSED_AMULET)) {
                           stack = new ItemStack(EnigmaticItems.ENIGMATIC_AMULET);
                           EnigmaticItems.ENIGMATIC_AMULET.setInscription(stack, player.m_36316_().getName());
                           if (EnigmaticAmulet.seededColorGen.getValue()) {
                              EnigmaticItems.ENIGMATIC_AMULET.setSeededColor(stack);
                           } else {
                              EnigmaticItems.ENIGMATIC_AMULET.setRandomColor(stack);
                           }

                           list.set(i, stack);
                        } else if (CursedRing.autoEquip.getValue()
                           && !player.m_7500_()
                           && !player.m_5833_()
                           && stack.m_150930_(EnigmaticItems.CURSED_RING)
                           && !SuperpositionHandler.hasCurio(player, EnigmaticItems.CURSED_RING)
                           && SuperpositionHandler.tryForceEquip(player, stack)) {
                           list.set(i, ItemStack.f_41583_);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @SubscribeEvent(
      priority = EventPriority.HIGHEST
   )
   public void onProbableDeath(LivingDeathEvent event) {
      if (event.getEntity() instanceof ServerPlayer) {
         ServerPlayer player = (ServerPlayer)event.getEntity();
         if (SuperpositionHandler.hasCurio(player, EnigmaticItems.CURSED_RING)) {
            POSTMORTAL_POSESSIONS.put(player, EnigmaticItems.CURSED_RING);
         }
      }
   }

   @SubscribeEvent(
      priority = EventPriority.LOWEST,
      receiveCanceled = true
   )
   public void onConfirmedDeath(LivingDeathEvent event) {
      if (event.getEntity() instanceof ServerPlayer) {
         ServerPlayer player = (ServerPlayer)event.getEntity();
         if (event.isCanceled()) {
            POSTMORTAL_POSESSIONS.removeAll(player);
            return;
         }

         SuperpositionHandler.setPersistentBoolean(player, "DeathFromEntity", event.getSource().m_7639_() instanceof LivingEntity);
         if (SuperpositionHandler.hasCurio(player, EnigmaticItems.ENIGMATIC_AMULET) || SuperpositionHandler.hasItem(player, EnigmaticItems.ENIGMATIC_AMULET)) {
            POSTMORTAL_POSESSIONS.put(player, EnigmaticItems.ENIGMATIC_AMULET);
         } else if (SuperpositionHandler.hasCurio(player, EnigmaticItems.ASCENSION_AMULET)
            || SuperpositionHandler.hasItem(player, EnigmaticItems.ASCENSION_AMULET)) {
            POSTMORTAL_POSESSIONS.put(player, EnigmaticItems.ASCENSION_AMULET);
         }

         if (SuperpositionHandler.hasItem(player, EnigmaticItems.CURSED_STONE)) {
            POSTMORTAL_POSESSIONS.put(player, EnigmaticItems.CURSED_STONE);

            for (List<ItemStack> list : player.m_150109_().f_35979_) {
               for (ItemStack itemstack : list) {
                  if (!itemstack.m_41619_() && itemstack.m_41720_() == EnigmaticItems.CURSED_STONE) {
                     itemstack.m_41764_(0);
                  }
               }
            }
         }

         if (SuperpositionHandler.hasCurio(player, EnigmaticItems.ESCAPE_SCROLL)) {
            POSTMORTAL_POSESSIONS.put(player, EnigmaticItems.ESCAPE_SCROLL);
            if (!player.f_19853_.f_46443_) {
               ItemStack tomeStack = SuperpositionHandler.getCurioStack(player, EnigmaticItems.ESCAPE_SCROLL);
               PermanentItemEntity droppedTomeStack = new PermanentItemEntity(
                  player.f_19853_, player.m_20185_(), player.m_20186_() + (double)(player.m_20206_() / 2.0F), player.m_20189_(), tomeStack.m_41777_()
               );
               droppedTomeStack.setPickupDelay(10);
               player.f_19853_.m_7967_(droppedTomeStack);
               tomeStack.m_41774_(1);
            }
         }

         if (SuperpositionHandler.hasCurio(player, EnigmaticItems.ELDRITCH_AMULET) && SuperpositionHandler.isTheWorthyOne(player)) {
            EnigmaticItems.ELDRITCH_AMULET.storeInventory(player);
            POSTMORTAL_POSESSIONS.put(player, EnigmaticItems.ELDRITCH_AMULET);
         } else {
            for (int i = 0; i < player.m_150109_().f_35975_.size(); i++) {
               ItemStack armor = (ItemStack)player.m_150109_().f_35975_.get(i);
               if (armor.getEnchantmentLevel(EnigmaticEnchantments.ETERNAL_BINDING) > 0) {
                  player.m_150109_().f_35975_.set(i, ItemStack.f_41583_);
                  SuperpositionHandler.setPersistentTag(player, "EternallyBoundArmor" + i, armor.serializeNBT());
               }
            }
         }
      }

      if (event.getSource().m_7639_() instanceof ServerPlayer playerx) {
         if (SuperpositionHandler.hasCurio(playerx, EnigmaticItems.THE_CUBE)) {
            EnigmaticItems.THE_CUBE.applyRandomEffect(playerx, true);
         }

         if (event.getEntity() instanceof WitherBoss) {
            int killedWither = SuperpositionHandler.getPersistentInteger(playerx, "TimesKilledWither", 0);
            if (killedWither <= 0) {
               Quote.BREATHES_RELIEVED.play(playerx, 140);
               killedWither++;
            } else if (killedWither == 1) {
               Quote.APPALING_PRESENCE.play(playerx, 140);
               killedWither++;
            } else if (killedWither == 2) {
               Quote.TERRIFYING_FORM.play(playerx, 140);
               killedWither++;
            } else if (killedWither > 2 && killedWither < 5) {
               killedWither++;
            } else if (killedWither == 4) {
               Quote.WHETHER_IT_IS.play(playerx, 140);
               killedWither++;
            }

            SuperpositionHandler.setPersistentInteger(playerx, "TimesKilledWither", killedWither);
         }
      }
   }

   @SubscribeEvent
   public void onCurioDrops(DropRulesEvent event) {
      event.addOverride(stack -> stack.getEnchantmentLevel(EnigmaticEnchantments.ETERNAL_BINDING) > 0, DropRule.ALWAYS_KEEP);
      if (event.getEntity() instanceof ServerPlayer player) {
         if (POSTMORTAL_POSESSIONS.containsEntry(player, EnigmaticItems.ELDRITCH_AMULET)) {
            event.addOverride(stack -> stack.getEnchantmentLevel(Enchantments.f_44963_) <= 0, DropRule.ALWAYS_KEEP);
         }

         if (this.hadUnholyStone(player) && player.f_19853_.m_46472_() == EnigmaticLegacy.PROXY.getNetherKey()) {
            BlockPos deathPos = player.m_20183_();
            if (this.isThereLava(player.f_19853_, deathPos)) {
               BlockPos surfacePos = deathPos;

               while (true) {
                  BlockPos nextAbove = surfacePos.m_7918_(0, 1, 0);
                  if (!this.isThereLava(player.f_19853_, nextAbove)) {
                     boolean confirmLavaPool = true;

                     for (int i = -3; i <= 2; i++) {
                        int fi = i;
                        boolean checkArea = BlockPos.m_121990_(surfacePos.m_7918_(-3, i, -3), surfacePos.m_7918_(3, i, 3))
                           .map(
                              blockPos -> fi <= 0
                                    ? this.isThereLava(player.f_19853_, blockPos)
                                    : player.f_19853_.m_46859_(blockPos) || this.isThereLava(player.f_19853_, blockPos)
                           )
                           .reduce((prevResult, nextElement) -> prevResult && nextElement)
                           .orElse(false);
                        confirmLavaPool = confirmLavaPool && checkArea;
                     }

                     if (confirmLavaPool) {
                        event.addOverride(
                           stack -> stack != null && (stack.m_150930_(EnigmaticItems.CURSED_RING) || stack.m_150930_(EnigmaticItems.DESOLATION_RING)),
                           DropRule.DESTROY
                        );
                        EnigmaticItems.SOUL_CRYSTAL.setLostCrystals(player, EnigmaticItems.SOUL_CRYSTAL.getLostCrystals(player) + 1);
                        SuperpositionHandler.destroyCurio(player, EnigmaticItems.CURSED_RING);
                        SuperpositionHandler.destroyCurio(player, EnigmaticItems.DESOLATION_RING);
                        player.f_19853_.m_5594_(null, player.m_20183_(), SoundEvents.f_12556_, SoundSource.PLAYERS, 1.0F, 0.5F);
                        SuperpositionHandler.setPersistentBoolean(player, "DestroyedCursedRing", true);
                     }
                     break;
                  }

                  surfacePos = nextAbove;
               }
            }
         }
      }
   }

   private boolean isThereLava(Level world, BlockPos pos) {
      FluidState fluidState = world.m_8055_(pos).m_60819_();
      return fluidState != null && fluidState.m_205070_(FluidTags.f_13132_) && fluidState.m_76170_();
   }

   @SubscribeEvent(
      priority = EventPriority.HIGHEST
   )
   public void onLivingDeath(LivingDeathEvent event) {
      if (event.getEntity() instanceof Player && !event.getEntity().f_19853_.f_46443_) {
         Player player = (Player)event.getEntity();
         if (isPoisonHurt && event.getSource() == DamageSource.f_19319_) {
            event.setCanceled(true);
            player.m_21153_(1.0F);
         } else if (LAST_HEALTH.containsKey(player) && LAST_HEALTH.get(player) >= 1.5F && SuperpositionHandler.hasCurio(player, EnigmaticItems.THE_CUBE)) {
            event.setCanceled(true);
            player.m_21153_(1.0F);
         } else if (SuperpositionHandler.hasCurio(player, EnigmaticItems.ENIGMATIC_ITEM)
            || player.m_150109_().m_36063_(new ItemStack(EnigmaticItems.ENIGMATIC_ITEM))
            || event.getSource() instanceof DamageSourceNemesisCurse) {
            event.setCanceled(true);
            player.m_21153_(1.0F);
         } else if (SuperpositionHandler.hasCurio(player, EnigmaticItems.VOID_PEARL)
            && Math.random() <= VoidPearl.undeadProbability.getValue().asMultiplier(false)) {
            event.setCanceled(true);
            player.m_21153_(1.0F);
         } else if (SuperpositionHandler.isTheWorthyOne(player)) {
            boolean infinitum = false;
            if (player.m_21205_() != null && player.m_21205_().m_41720_() == EnigmaticItems.THE_INFINITUM) {
               infinitum = true;
            } else if (player.m_21205_() != null && player.m_21205_().m_41720_() == EnigmaticItems.THE_INFINITUM) {
               infinitum = true;
            }

            if (infinitum && Math.random() <= TheInfinitum.undeadProbability.getValue().asMultiplier(false)) {
               event.setCanceled(true);
               player.m_21153_(1.0F);
            }
         }
      }
   }

   @SubscribeEvent(
      priority = EventPriority.LOW
   )
   public void onDeathLow(LivingDeathEvent event) {
      if (event.getEntity() instanceof ServerPlayer player
         && SuperpositionHandler.hasCurio(player, EnigmaticItems.THE_CUBE)
         && !SuperpositionHandler.hasSpellstoneCooldown(player)) {
         event.setCanceled(true);
         player.m_21153_(player.m_21233_() * 0.3F);
         EnigmaticItems.THE_CUBE.triggerActiveAbility(player.f_19853_, player, SuperpositionHandler.getCurioStack(player, EnigmaticItems.THE_CUBE));
         player.m_7292_(new MobEffectInstance(MobEffects.f_19605_, 1200, 2));
         player.m_7292_(new MobEffectInstance(MobEffects.f_19606_, 1200, 1));
         player.m_7292_(new MobEffectInstance(MobEffects.f_19607_, 1200, 0));
         player.m_7292_(new MobEffectInstance(MobEffects.f_19600_, 1200, 1));
         EnigmaticLegacy.packetInstance
            .send(
               PacketDistributor.NEAR.with(() -> new TargetPoint(player.m_20185_(), player.m_20186_(), player.m_20189_(), 128.0, player.f_19853_.m_46472_())),
               new PacketCosmicRevive(player.m_19879_(), 1)
            );
      }
   }

   @SubscribeEvent
   public void onLivingHeal(LivingHealEvent event) {
      if (event.getEntity() instanceof Player) {
         Player player = (Player)event.getEntity();
         if (event.getAmount() <= 1.0F && EnigmaticItems.FORBIDDEN_FRUIT.haveConsumedFruit(player)) {
            event.setAmount(event.getAmount() * ForbiddenFruit.regenerationSubtraction.getValue().asModifierInverted());
         }

         if (SuperpositionHandler.hasCurio(player, EnigmaticItems.CURSED_SCROLL)) {
            event.setAmount(
               event.getAmount() + event.getAmount() * CursedScroll.regenBoost.getValue().asModifier() * (float)SuperpositionHandler.getCurseAmount(player)
            );
         }
      }
   }

   @SubscribeEvent
   public void onProjectileImpact(ProjectileImpactEvent event) {
      if (event.getRayTraceResult() instanceof EntityHitResult) {
         EntityHitResult result = (EntityHitResult)event.getRayTraceResult();
         if (result.m_82443_() instanceof Player) {
            Player player = (Player)result.m_82443_();
            Entity arrow = event.getEntity();
            if (!player.f_19853_.f_46443_) {
               if (arrow instanceof Projectile projectile && projectile.m_37282_() == player) {
                  for (String tag : arrow.m_19880_()) {
                     if (tag.startsWith("AB_DEFLECTED")) {
                        try {
                           int time = Integer.parseInt(tag.split(":")[1]);
                           if (arrow.f_19797_ - time < 10) {
                              return;
                           }
                        } catch (Exception var9) {
                           var9.printStackTrace();
                        }
                     }
                  }
               }

               boolean trigger = false;
               double chance = 0.0;
               if (SuperpositionHandler.hasCurio(player, EnigmaticItems.ANGEL_BLESSING)) {
                  trigger = true;
                  chance += (double)AngelBlessing.deflectChance.getValue().asModifier(false);
               }

               if (SuperpositionHandler.hasCurio(player, EnigmaticItems.THE_CUBE)) {
                  trigger = true;
                  chance += 0.35;
               }

               if (EnigmaticItems.ENIGMATIC_AMULET.hasColor(player, EnigmaticAmulet.AmuletColor.VIOLET)) {
                  trigger = true;
                  chance += 0.15;
               }

               if (trigger && Math.random() <= chance) {
                  event.setCanceled(true);
                  arrow.m_20256_(arrow.m_20184_().m_82490_(-1.0));
                  arrow.f_19859_ = arrow.m_146908_() + 180.0F;
                  arrow.m_146922_(arrow.m_146908_() + 180.0F);
                  if (arrow instanceof AbstractArrow absar) {
                     if (!(absar instanceof ThrownTrident)) {
                        absar.m_5602_(player);
                     }

                     ((AccessorAbstractArrowEntity)absar).clearHitEntities();
                     absar.f_36705_ = Pickup.CREATIVE_ONLY;
                     absar.m_36767_((byte)0);
                  } else if (arrow instanceof AbstractHurtingProjectile) {
                     ((AbstractHurtingProjectile)arrow).m_5602_(player);
                     ((AbstractHurtingProjectile)arrow).f_36813_ = -((AbstractHurtingProjectile)arrow).f_36813_;
                     ((AbstractHurtingProjectile)arrow).f_36814_ = -((AbstractHurtingProjectile)arrow).f_36814_;
                     ((AbstractHurtingProjectile)arrow).f_36815_ = -((AbstractHurtingProjectile)arrow).f_36815_;
                  }

                  arrow.m_19880_().removeIf(tagx -> tagx.startsWith("AB_DEFLECTED"));
                  arrow.m_20049_("AB_DEFLECTED:" + arrow.f_19797_);
                  EnigmaticLegacy.packetInstance
                     .send(
                        PacketDistributor.NEAR
                           .with(() -> new TargetPoint(player.m_20185_(), player.m_20186_(), player.m_20189_(), 64.0, player.f_19853_.m_46472_())),
                        new PacketForceArrowRotations(
                           arrow.m_19879_(),
                           arrow.m_146908_(),
                           arrow.m_146909_(),
                           arrow.m_20184_().f_82479_,
                           arrow.m_20184_().f_82480_,
                           arrow.m_20184_().f_82481_,
                           arrow.m_20185_(),
                           arrow.m_20186_(),
                           arrow.m_20189_()
                        )
                     );
                  player.f_19853_.m_5594_(null, player.m_20183_(), EnigmaticSounds.DEFLECT, SoundSource.PLAYERS, 1.0F, 0.95F + (float)(Math.random() * 0.1));
               }
            }
         }
      }
   }

   @SubscribeEvent
   public void onEntityAttacked(LivingAttackEvent event) {
      if (!event.getEntity().f_19853_.f_46443_) {
         if (event.getEntity() instanceof Player player) {
            if (player.m_21023_(EnigmaticEffects.MOLTEN_HEART) && EnigmaticEffects.MOLTEN_HEART.providesImmunity(event.getSource())) {
               event.setCanceled(true);
               return;
            }

            if (!(event.getSource().m_7640_() instanceof AbstractHurtingProjectile) && event.getSource().m_7640_() instanceof AbstractArrow) {
            }

            if (event.getSource().f_19326_ == DamageSource.f_19315_.f_19326_
               && EnigmaticItems.ENIGMATIC_AMULET.hasColor(player, EnigmaticAmulet.AmuletColor.MAGENTA)
               && event.getAmount() <= 2.0F) {
               event.setCanceled(true);
            }

            List<ItemStack> advancedCurios = SuperpositionHandler.getAdvancedCurios(player);
            if (advancedCurios.size() > 0) {
               for (ItemStack advancedCurioStack : advancedCurios) {
                  ItemSpellstoneCurio advancedCurio = (ItemSpellstoneCurio)advancedCurioStack.m_41720_();
                  if (advancedCurio.immunityList.contains(event.getSource().f_19326_)) {
                     event.setCanceled(true);
                  }
               }
            }

            if (SuperpositionHandler.hasCurio(player, EnigmaticItems.EYE_OF_NEBULA)
               && !event.isCanceled()
               && EyeOfNebula.dodgeProbability.getValue().roll()
               && player.f_19802_ <= 10
               && event.getSource().m_7639_() instanceof LivingEntity) {
               int counter = 0;

               while (counter <= 32 && !SuperpositionHandler.validTeleportRandomly(player, player.f_19853_, (int)EyeOfNebula.dodgeRange.getValue())) {
                  counter++;
               }

               player.f_19802_ = 20;
               event.setCanceled(true);
            }

            if (!player.m_150110_().f_35934_
               && player.m_21124_(MobEffects.f_19607_) == null
               && SuperpositionHandler.hasCurio(player, EnigmaticItems.BLAZING_CORE)
               && !event.isCanceled()
               && event.getSource().f_19326_.equals(DamageSource.f_19308_.f_19326_)) {
               TransientPlayerData data = TransientPlayerData.get(player);
               if (data.getFireImmunityTimer() < data.getFireImmunityTimerCap()) {
                  if (data.getFireImmunityTimer() < data.getFireImmunityTimerCap() - data.getFireDiff()) {
                     event.setCanceled(true);
                  }

                  if (data.getFireImmunityTimer() == 0 && !player.f_19853_.f_46443_) {
                     player.f_19853_
                        .m_5594_(null, player.m_20183_(), SoundEvents.f_12031_, SoundSource.PLAYERS, 1.0F, 0.5F + THEY_SEE_ME_ROLLIN.nextFloat() * 0.5F);
                     EnigmaticLegacy.packetInstance
                        .send(
                           PacketDistributor.NEAR
                              .with(() -> new TargetPoint(player.m_20185_(), player.m_20186_(), player.m_20189_(), 32.0, player.f_19853_.m_46472_())),
                           new PacketWitherParticles(player.m_20185_(), player.m_20227_(0.25), player.m_20189_(), 8, false, 1)
                        );
                  }

                  data.setFireImmunityTimer(data.getFireImmunityTimer() + 200);
               }
            }
         } else if (event.getSource().m_7640_() instanceof Player && "player".equals(event.getSource().f_19326_)) {
            Player player = (Player)event.getSource().m_7640_();
            if (player.m_21205_() != null
               && player.m_21205_().m_41720_() == EnigmaticItems.EXTRADIMENSIONAL_EYE
               && ItemNBTHelper.verifyExistance(player.m_21205_(), "BoundDimension")
               && ItemNBTHelper.getString(player.m_21205_(), "BoundDimension", "minecraft:overworld")
                  .equals(event.getEntity().f_19853_.m_46472_().m_135782_().toString())) {
               event.setCanceled(true);
               ItemStack stack = player.m_21205_();
               EnigmaticLegacy.packetInstance
                  .send(
                     PacketDistributor.NEAR
                        .with(
                           () -> new TargetPoint(
                                 event.getEntity().m_20185_(),
                                 event.getEntity().m_20186_(),
                                 event.getEntity().m_20189_(),
                                 128.0,
                                 event.getEntity().f_19853_.m_46472_()
                              )
                        ),
                     new PacketPortalParticles(
                        event.getEntity().m_20185_(),
                        event.getEntity().m_20186_() + (double)(event.getEntity().m_20206_() / 2.0F),
                        event.getEntity().m_20189_(),
                        96,
                        1.5,
                        false
                     )
                  );
               event.getEntity()
                  .f_19853_
                  .m_5594_(null, event.getEntity().m_20183_(), SoundEvents.f_11852_, SoundSource.PLAYERS, 1.0F, (float)(0.8F + Math.random() * 0.2));
               event.getEntity()
                  .m_6021_(
                     ItemNBTHelper.getDouble(stack, "BoundX", 0.0),
                     ItemNBTHelper.getDouble(stack, "BoundY", 0.0),
                     ItemNBTHelper.getDouble(stack, "BoundZ", 0.0)
                  );
               event.getEntity()
                  .f_19853_
                  .m_5594_(null, event.getEntity().m_20183_(), SoundEvents.f_11852_, SoundSource.PLAYERS, 1.0F, (float)(0.8F + Math.random() * 0.2));
               EnigmaticLegacy.packetInstance
                  .send(
                     PacketDistributor.NEAR
                        .with(
                           () -> new TargetPoint(
                                 event.getEntity().m_20185_(),
                                 event.getEntity().m_20186_(),
                                 event.getEntity().m_20189_(),
                                 128.0,
                                 event.getEntity().f_19853_.m_46472_()
                              )
                        ),
                     new PacketRecallParticles(
                        event.getEntity().m_20185_(),
                        event.getEntity().m_20186_() + (double)(event.getEntity().m_20206_() / 2.0F),
                        event.getEntity().m_20189_(),
                        48,
                        false
                     )
                  );
               if (!player.m_150110_().f_35937_) {
                  stack.m_41774_(1);
               }
            }

            float knockbackPower = 1.0F;
            if (player.m_21205_() != null && player.m_21205_().m_41720_() == EnigmaticItems.THE_TWIST && SuperpositionHandler.isTheCursedOne(player)) {
               knockbackPower += TheTwist.knockbackBonus.getValue().asModifier(false);
            } else if (player.m_21205_() != null && player.m_21205_().m_41720_() == EnigmaticItems.THE_INFINITUM && SuperpositionHandler.isTheWorthyOne(player)
               )
             {
               knockbackPower += TheInfinitum.knockbackBonus.getValue().asModifier(false);
            } else if (player.m_21205_() != null
               && player.m_21205_().m_150930_(EnigmaticItems.ENDER_SLAYER)
               && SuperpositionHandler.isTheCursedOne(player)
               && EnigmaticItems.ENDER_SLAYER.isEndDweller(event.getEntity())) {
               knockbackPower += EnderSlayer.endKnockbackBonus.getValue().asModifier(false);
            }

            if (event.getEntity() instanceof Player victim && SuperpositionHandler.hasArchitectsFavor(player) && !SuperpositionHandler.isTheBlessedOne(victim)) {
               knockbackPower += CosmicScroll.unchosenKnockbackBonus.getValue().asModifier(false);
            }

            if (knockbackPower > 1.0F) {
               KNOCKBACK_THAT_BASTARD.put(event.getEntity(), event.getEntity() instanceof Phantom ? knockbackPower * 1.5F : knockbackPower);
            }
         }

         if (event.getEntity() instanceof Animal animal
            && event.getSource().m_7639_() instanceof Player playerx
            && SuperpositionHandler.hasItem(playerx, EnigmaticItems.ANIMAL_GUIDEBOOK)
            && EnigmaticItems.ANIMAL_GUIDEBOOK.isProtectedAnimal(animal)) {
            event.setCanceled(true);
            if (animal.m_5448_() == playerx) {
               event.setCanceled(false);
            } else {
               for (WrappedGoal goal : animal.f_21346_.m_148105_()) {
                  Goal var7 = goal.m_26015_();
                  if (var7 instanceof TargetGoal) {
                     TargetGoal targetGoal = (TargetGoal)var7;
                     if (targetGoal.f_26137_ == playerx) {
                        event.setCanceled(false);
                     }
                  }
               }
            }

            Brain<?> brain = animal.m_6274_();
            if (brain != null) {
               try {
                  Optional<? extends Object> memory = brain.m_21874_(MemoryModuleType.f_26372_) ? brain.m_21952_(MemoryModuleType.f_26372_) : Optional.empty();
                  if (memory.isPresent() && memory.get() == playerx) {
                     event.setCanceled(false);
                  }
               } catch (NullPointerException var8) {
               }
            }
         }
      }
   }

   @SubscribeEvent(
      priority = EventPriority.LOW
   )
   public void onEntityDamaged(LivingDamageEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         LAST_HEALTH.put(player, player.m_21223_());
      }

      if (event.getSource().m_7640_() instanceof Player && !event.getSource().m_7640_().f_19853_.f_46443_) {
         Player player = (Player)event.getSource().m_7640_();
         float lifesteal = 0.0F;
         if (EnigmaticItems.ENIGMATIC_AMULET.hasColor(player, EnigmaticAmulet.AmuletColor.BLACK)) {
            lifesteal += event.getAmount() * 0.1F;
         }

         if (SuperpositionHandler.hasCurio(player, EnigmaticItems.ELDRITCH_AMULET) && SuperpositionHandler.isTheWorthyOne(player)) {
            lifesteal += event.getAmount() * 0.15F;
         }

         if (player.m_21205_() != null && player.m_21205_().m_41720_() == EnigmaticItems.THE_INFINITUM && SuperpositionHandler.isTheWorthyOne(player)) {
            lifesteal += event.getAmount() * 0.1F;
         }

         if (lifesteal > 0.0F) {
            player.m_5634_(lifesteal);
         }
      }
   }

   @SubscribeEvent(
      priority = EventPriority.LOWEST
   )
   public void onEntityAttacked(AttackEntityEvent event) {
      if (!event.getEntity().f_19853_.f_46443_) {
         RegisteredMeleeAttack.registerAttack(event.getEntity());
      }
   }

   @SubscribeEvent(
      priority = EventPriority.HIGHEST
   )
   public void onPlayerHurt(LivingHurtEvent event) {
      if (event.getEntity() instanceof Player player && event.getAmount() > 0.0F && player.m_21023_(EnigmaticEffects.BLAZING_STRENGTH)) {
         player.m_21195_(EnigmaticEffects.BLAZING_STRENGTH);
      }
   }

   @SubscribeEvent(
      priority = EventPriority.LOW
   )
   public void endEntityHurt(LivingHurtEvent event) {
      if (event.getEntity() instanceof ServerPlayer player
         && event.getSource().m_7639_() != null
         && event.getAmount() > EnigmaticItems.THE_CUBE.getDamageLimit(player)
         && SuperpositionHandler.hasCurio(player, EnigmaticItems.THE_CUBE)) {
         event.setCanceled(true);
         player.f_19853_.m_5594_(null, player.m_20183_(), SoundEvents.f_12018_, SoundSource.PLAYERS, 1.0F, 1.0F);
         if (event.getSource().m_7640_() instanceof LivingEntity living) {
            Vector3 look = new Vector3(living.m_20182_()).subtract(new Vector3(player.m_20182_())).normalize();
            Vector3 dir = look.multiply(1.0);
            EnigmaticLegacy.etheriumConfig.knockBack(living, 1.0F, dir.x, dir.z);
         }
      }
   }

   @SubscribeEvent
   public void onEntityHurt(LivingHurtEvent event) {
      if (!(event.getAmount() >= Float.MAX_VALUE)) {
         if (event.getSource().m_7639_() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity)event.getSource().m_7639_();
            Entity immediateSource = event.getSource().m_7640_();
            if (immediateSource instanceof ThrownTrident || immediateSource instanceof LivingEntity) {
               ItemStack mainhandStack = ItemStack.f_41583_;
               if (immediateSource instanceof ThrownTrident) {
                  if (((ThrownTrident)immediateSource).f_37555_ != null) {
                     mainhandStack = ((ThrownTrident)immediateSource).f_37555_;
                  }
               } else if (((LivingEntity)immediateSource).m_21205_() != null) {
                  mainhandStack = ((LivingEntity)immediateSource).m_21205_();
               }

               int torrentLevel = 0;
               int wrathLevel = 0;
               if ((torrentLevel = mainhandStack.getEnchantmentLevel(EnigmaticEnchantments.TORRENT)) > 0) {
                  event.setAmount(event.getAmount() + EnigmaticEnchantments.TORRENT.bonusDamageByCreature(attacker, event.getEntity(), torrentLevel));
               }

               if ((wrathLevel = mainhandStack.getEnchantmentLevel(EnigmaticEnchantments.WRATH)) > 0) {
                  event.setAmount(event.getAmount() + EnigmaticEnchantments.WRATH.bonusDamageByCreature(attacker, event.getEntity(), wrathLevel));
               }
            }
         }

         if (event.getSource().m_7640_() instanceof Player && "player".equals(event.getSource().f_19326_)) {
            Player player = (Player)event.getSource().m_7640_();
            if (SuperpositionHandler.hasCurio(player, EnigmaticItems.VOID_PEARL)) {
               event.getEntity()
                  .m_7292_(new MobEffectInstance(MobEffects.f_19615_, VoidPearl.witheringTime.getValue(), VoidPearl.witheringLevel.getValue(), false, true));
            }

            if (player instanceof ServerPlayer && SuperpositionHandler.isTheCursedOne(player)) {
               if (event.getEntity() instanceof ServerPlayer targetPlayer) {
                  targetPlayer.m_36335_().m_41524_(Items.f_42584_, 400);
                  targetPlayer.m_36335_().m_41524_(EnigmaticItems.RECALL_POTION, 400);
                  targetPlayer.m_36335_().m_41524_(EnigmaticItems.TWISTED_MIRROR, 400);
                  if (SuperpositionHandler.hasCurio(targetPlayer, EnigmaticItems.EYE_OF_NEBULA)
                     || SuperpositionHandler.hasCurio(targetPlayer, EnigmaticItems.THE_CUBE)) {
                     SuperpositionHandler.setSpellstoneCooldown(targetPlayer, 400);
                  }
               }

               if (event.getEntity() instanceof EnderMan || event.getEntity() instanceof Shulker) {
                  event.getEntity().getPersistentData().m_128405_("ELTeleportBlock", 400);
               }
            }

            float bonusDamage = 0.0F;
            if (player.m_21205_() != null) {
               ItemStack mainhandStackx = player.m_21205_();
               if (mainhandStackx.m_150930_(EnigmaticItems.THE_TWIST)) {
                  if (SuperpositionHandler.isTheCursedOne(player)) {
                     if (OmniconfigHandler.isBossOrPlayer(event.getEntity())) {
                        bonusDamage += event.getAmount() * TheTwist.bossDamageBonus.getValue().asModifier(false);
                     }
                  } else {
                     event.setCanceled(true);
                  }
               } else if (mainhandStackx.m_150930_(EnigmaticItems.THE_INFINITUM)) {
                  if (SuperpositionHandler.isTheWorthyOne(player)) {
                     if (OmniconfigHandler.isBossOrPlayer(event.getEntity())) {
                        bonusDamage += event.getAmount() * TheInfinitum.bossDamageBonus.getValue().asModifier(false);
                     }
                  } else {
                     event.setCanceled(true);
                     player.m_7292_(new MobEffectInstance(MobEffects.f_19615_, 160, 3, false, true));
                     player.m_7292_(new MobEffectInstance(MobEffects.f_19604_, 500, 3, false, true));
                     player.m_7292_(new MobEffectInstance(MobEffects.f_19613_, 300, 3, false, true));
                     player.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 300, 3, false, true));
                     player.m_7292_(new MobEffectInstance(MobEffects.f_19599_, 300, 3, false, true));
                     player.m_7292_(new MobEffectInstance(MobEffects.f_19610_, 100, 3, false, true));
                  }
               } else if (mainhandStackx.m_150930_(EnigmaticItems.ENDER_SLAYER)) {
                  if (SuperpositionHandler.isTheCursedOne(player)) {
                     if (EnigmaticItems.ENDER_SLAYER.isEndDweller(event.getEntity())) {
                        if (player.f_19853_.m_46472_().equals(EnigmaticLegacy.PROXY.getEndKey())) {
                           if (event.getEntity() instanceof EnderMan && RegisteredMeleeAttack.getRegisteredAttackStregth(player) >= 1.0F) {
                              event.setAmount((event.getAmount() + 100.0F) * 10.0F);
                           }

                           event.getEntity().getPersistentData().m_128379_("EnderSlayerVictim", true);
                        }

                        bonusDamage += event.getAmount() * EnderSlayer.endDamageBonus.getValue().asModifier(false);
                     }
                  } else {
                     event.setCanceled(true);
                  }
               }

               event.setAmount(event.getAmount() + bonusDamage);
               int slayerLevel = 0;
               if ((slayerLevel = mainhandStackx.getEnchantmentLevel(EnigmaticEnchantments.SLAYER)) > 0) {
                  event.setAmount(event.getAmount() + EnigmaticEnchantments.SLAYER.bonusDamageByCreature(player, event.getEntity(), slayerLevel));
                  if (event.getEntity() instanceof Monster) {
                     int i = 20 + player.m_217043_().m_188503_(10 * slayerLevel);
                     event.getEntity().m_7292_(new MobEffectInstance(MobEffects.f_19597_, i, 3));
                  }
               }

               if (EnigmaticEnchantmentHelper.hasNemesisCurseEnchantment(mainhandStackx)) {
                  float supposedDamage = event.getAmount() * 0.35F;
                  player.m_6469_(new DamageSourceNemesisCurse(event.getEntity()), supposedDamage);
               }
            } else {
               event.setAmount(event.getAmount() + bonusDamage);
            }
         }

         if (event.getEntity() instanceof Player) {
            Player playerx = (Player)event.getEntity();
            List<ItemStack> advancedCurios = SuperpositionHandler.getAdvancedCurios(playerx);
            if (advancedCurios.size() > 0) {
               for (ItemStack advancedCurioStack : advancedCurios) {
                  ItemSpellstoneCurio advancedCurio = (ItemSpellstoneCurio)advancedCurioStack.m_41720_();
                  Mob trueSource = event.getSource().m_7639_() instanceof Mob ? (Mob)event.getSource().m_7639_() : null;
                  if (!event.getSource().f_19326_.startsWith("explosion")
                     || advancedCurio != EnigmaticItems.GOLEM_HEART
                     || !SuperpositionHandler.hasAnyArmor(playerx)) {
                     if (advancedCurio != EnigmaticItems.BLAZING_CORE
                        || trueSource == null
                        || trueSource.m_6336_() != MobType.f_21644_ && !(trueSource instanceof Drowned)) {
                        if (advancedCurio == EnigmaticItems.EYE_OF_NEBULA && playerx.m_20069_()) {
                           event.setAmount(event.getAmount() * 2.0F);
                        } else if (advancedCurio == EnigmaticItems.OCEAN_STONE
                           && trueSource != null
                           && (trueSource.m_6336_() == MobType.f_21644_ || trueSource instanceof Drowned)) {
                           event.setAmount(event.getAmount() * OceanStone.underwaterCreaturesResistance.getValue().asModifierInverted());
                        }
                     } else {
                        event.setAmount(event.getAmount() * 2.0F);
                     }

                     if (advancedCurio.resistanceList.containsKey(event.getSource().f_19326_)) {
                        event.setAmount(event.getAmount() * advancedCurio.resistanceList.get(event.getSource().f_19326_).get());
                     }
                  }
               }
            }

            if (event.getSource().f_19326_ == DamageSource.f_19315_.f_19326_
               && EnigmaticItems.ENIGMATIC_AMULET.hasColor(playerx, EnigmaticAmulet.AmuletColor.MAGENTA)) {
               event.setAmount(event.getAmount() - 2.0F);
            }

            if (SuperpositionHandler.hasCurio(playerx, EnigmaticItems.BLAZING_CORE)
               && event.getSource().m_7639_() instanceof LivingEntity
               && EnigmaticItems.BLAZING_CORE.nemesisList.contains(event.getSource().f_19326_)) {
               LivingEntity attacker = (LivingEntity)event.getSource().m_7639_();
               if (!attacker.m_5825_()) {
                  attacker.m_6469_(new EntityDamageSource(DamageSource.f_19307_.f_19326_, playerx), (float)BlazingCore.damageFeedback.getValue());
                  attacker.m_20254_(BlazingCore.ignitionFeedback.getValue());
               }
            }

            if (SuperpositionHandler.hasCurio(playerx, EnigmaticItems.BERSERK_CHARM)) {
               event.setAmount(
                  event.getAmount() * (1.0F - SuperpositionHandler.getMissingHealthPool(playerx) * (float)BerserkEmblem.damageResistance.getValue())
               );
            }

            if (SuperpositionHandler.hasCurio(playerx, EnigmaticItems.CURSED_RING)) {
               event.setAmount(event.getAmount() * CursedRing.painMultiplier.getValue().asModifier());
            }

            if (event.getSource().m_7639_() != null
               && playerx.m_21211_().m_41720_() instanceof InfernalShield
               && ((IProperShieldUser)playerx).isActuallyReallyBlocking()) {
               Vec3 sourcePos = event.getSource().m_7270_();
               if (sourcePos != null) {
                  Vec3 lookVec = playerx.m_20252_(1.0F);
                  Vec3 sourceToSelf = sourcePos.m_82505_(playerx.m_20182_()).m_82541_();
                  sourceToSelf = new Vec3(sourceToSelf.f_82479_, 0.0, sourceToSelf.f_82481_);
                  if (!(sourceToSelf.m_82526_(lookVec) < 0.0)) {
                     event.setAmount(event.getAmount() * 1.5F);
                  }
               }
            }
         } else if (event.getEntity() instanceof Monster || event.getEntity() instanceof EnderDragon) {
            Mob mob = (Mob)event.getEntity();
            if (event.getSource().m_7639_() instanceof Player) {
               Player playerxx = (Player)event.getSource().m_7639_();
               if (mob instanceof EnderDragon && playerxx instanceof ServerPlayer splayer) {
                  Quote.POOR_CREATURE.playOnceIfUnlocked(splayer, 60);
               }

               if (SuperpositionHandler.hasCurio(playerxx, EnigmaticItems.MONSTER_CHARM)) {
                  if (mob.m_21222_()) {
                     event.setAmount(event.getAmount() * MonsterCharm.undeadDamageBonus.getValue().asModifier(true));
                  } else if ((mob.m_5912_() || mob instanceof Creeper)
                     && !(mob instanceof EnderMan)
                     && !(mob instanceof ZombifiedPiglin)
                     && !(mob instanceof Blaze)
                     && !(mob instanceof Guardian)
                     && !(mob instanceof ElderGuardian)
                     && mob.m_6072_()) {
                     event.setAmount(event.getAmount() * MonsterCharm.hostileDamageBonus.getValue().asModifier(true));
                  }
               }

               if (SuperpositionHandler.isTheCursedOne(playerxx)) {
                  boolean bypass = false;
                  if (event.getSource().m_7640_() == playerxx
                     && (playerxx.m_21205_().m_41720_() == EnigmaticItems.THE_TWIST || playerxx.m_21205_().m_41720_() == EnigmaticItems.THE_INFINITUM)) {
                     bypass = true;
                  }

                  if (!bypass) {
                     event.setAmount(event.getAmount() * CursedRing.monsterDamageDebuff.getValue().asModifierInverted());
                  }
               }
            }
         }

         if (event.getSource().m_7639_() instanceof Player playerxxx) {
            Entity immediateSource = event.getSource().m_7640_();
            float damageBoost = 0.0F;
            if (SuperpositionHandler.hasCurio(playerxxx, EnigmaticItems.BERSERK_CHARM)) {
               damageBoost += event.getAmount() * SuperpositionHandler.getMissingHealthPool(playerxxx) * (float)BerserkEmblem.attackDamage.getValue();
            }

            if (SuperpositionHandler.hasCurio(playerxxx, EnigmaticItems.CURSED_SCROLL)) {
               damageBoost += event.getAmount() * CursedScroll.damageBoost.getValue().asModifier() * (float)SuperpositionHandler.getCurseAmount(playerxxx);
            }

            event.setAmount(event.getAmount() + damageBoost);
            if (SuperpositionHandler.hasCurio(playerxxx, EnigmaticItems.EYE_OF_NEBULA)
               && (
                  event.getSource().m_19387_()
                     || event.getSource().f_19326_ == DamageSource.f_19320_.f_19326_
                     || event.getSource().f_19326_ == DamageSource.f_19323_.f_19326_
               )) {
               event.setAmount(event.getAmount() * EyeOfNebula.magicBoost.getValue().asModifier(true));
            }

            if (TransientPlayerData.get(playerxxx).hasEyeOfNebulaPower()) {
               event.setAmount(event.getAmount() * EyeOfNebula.attackEmpower.getValue().asModifier(true));
               TransientPlayerData.get(playerxxx).setEyeOfNebulaPower(false);
            }
         }

         if (event.getEntity() instanceof TamableAnimal) {
            TamableAnimal pet = (TamableAnimal)event.getEntity();
            if (pet.m_21824_()) {
               LivingEntity owner = pet.m_21826_();
               if (owner instanceof Player
                  && SuperpositionHandler.hasItem((Player)owner, EnigmaticItems.HUNTER_GUIDEBOOK)
                  && owner.f_19853_ == pet.f_19853_
                  && owner.m_20270_(pet) <= (float)HunterGuidebook.effectiveDistance.getValue()) {
                  event.setCanceled(true);
                  owner.m_6469_(
                     event.getSource(),
                     SuperpositionHandler.hasItem((Player)owner, EnigmaticItems.ANIMAL_GUIDEBOOK)
                        ? event.getAmount() * HunterGuidebook.synergyDamageReduction.getValue().asModifierInverted()
                        : event.getAmount()
                  );
               }
            }
         }

         if (event.getEntity() instanceof ServerPlayer playerxxx
            && event.getSource().m_7640_() instanceof LivingEntity living
            && SuperpositionHandler.hasCurio(playerxxx, EnigmaticItems.THE_CUBE)) {
            if (event.getAmount() <= EnigmaticItems.THE_CUBE.getDamageLimit(playerxxx) && THEY_SEE_ME_ROLLIN.nextDouble() <= 0.35) {
               event.setCanceled(true);
               living.m_6469_(event.getSource(), event.getAmount());
               playerxxx.f_19853_.m_5594_(null, playerxxx.m_20183_(), EnigmaticSounds.SWORD_HIT_REJECT, SoundSource.PLAYERS, 1.0F, 1.0F);
            } else {
               EnigmaticItems.THE_CUBE.applyRandomEffect(living, false);
            }
         }
      }
   }

   @SubscribeEvent(
      priority = EventPriority.LOWEST
   )
   public void onLivingHurtFinal(LivingHurtEvent event) {
      if (event.getEntity() instanceof ServerPlayer player && event.getSource().m_7639_() != null) {
         int sorrowLevel = 0;

         for (ItemStack armor : player.m_150109_().f_35975_) {
            sorrowLevel += armor.getEnchantmentLevel(EnigmaticEnchantments.SORROW);
         }

         for (int i = 0; i < sorrowLevel; i++) {
            EnigmaticEnchantments.SORROW.maybeApplyDebuff(player, event.getAmount());
         }
      }
   }

   @SubscribeEvent
   public void playerClone(Clone event) {
      Player newPlayer = event.getEntity();
      Player oldPlayer = event.getOriginal();
      if (event.isWasDeath() && newPlayer instanceof ServerPlayer && oldPlayer instanceof ServerPlayer) {
         oldPlayer.revive();
         IPlaytimeCounter oldCounter = IPlaytimeCounter.get(oldPlayer);
         IPlaytimeCounter newCounter = IPlaytimeCounter.get(newPlayer);
         newCounter.deserializeNBT(oldCounter.serializeNBT());
         if (!EnigmaticItems.ELDRITCH_AMULET.reclaimInventory((ServerPlayer)oldPlayer, (ServerPlayer)newPlayer)) {
            for (int i = 0; i < 4; i++) {
               if (SuperpositionHandler.getPersistentTag(oldPlayer, "EternallyBoundArmor" + i, null) instanceof CompoundTag armor) {
                  ItemStack stack = ItemStack.m_41712_(armor);
                  newPlayer.m_150109_().f_35975_.set(i, stack);
               }

               SuperpositionHandler.removePersistentTag(newPlayer, "EternallyBoundArmor" + i);
            }
         }
      }

      EnigmaticItems.SOUL_CRYSTAL.updatePlayerSoulMap(newPlayer);
   }

   @SubscribeEvent
   public void entityJoinWorld(EntityJoinLevelEvent event) {
      Entity entity = event.getEntity();
      if (entity instanceof ServerPlayer joinedPlayer) {
         EnigmaticItems.SOUL_CRYSTAL.updatePlayerSoulMap(joinedPlayer);
         TransientPlayerData joinedData = TransientPlayerData.get(joinedPlayer);
         ServerLifecycleHooks.getCurrentServer().m_6846_().m_11314_().forEach(serverPlayer -> {
            if (serverPlayer != joinedPlayer) {
               joinedData.syncToPlayer(serverPlayer);
               TransientPlayerData.get(serverPlayer).syncToPlayer(joinedPlayer);
            }
         });
         SCHEDULED_DATA_SYNC.add(joinedPlayer);
      }

      if (entity instanceof PathfinderMob mob && ((PathfinderMob)entity).m_6336_() == MobType.f_21642_) {
         mob.f_21345_
            .m_25352_(
               3,
               new AvoidEntityGoal(
                  (PathfinderMob)entity,
                  Player.class,
                  targetEntity -> targetEntity instanceof Player && SuperpositionHandler.hasAntiInsectAcknowledgement((Player)targetEntity),
                  6.0F,
                  1.0,
                  1.3,
                  EntitySelector.f_20406_::test
               )
            );
      }

      if (entity instanceof Piglin) {
      }
   }

   @SubscribeEvent(
      priority = EventPriority.LOWEST
   )
   public void onExperienceDrop(LivingExperienceDropEvent event) {
      Player player = event.getAttackingPlayer();
      int bonusExp = 0;
      if (event.getEntity() instanceof ServerPlayer) {
         if (this.hadEnigmaticAmulet((Player)event.getEntity()) && !event.getEntity().f_19853_.m_46469_().m_46207_(GameRules.f_46133_)) {
            event.setCanceled(true);
         }
      } else if (event.getEntity() instanceof Monster && player != null && SuperpositionHandler.hasCurio(player, EnigmaticItems.MONSTER_CHARM)) {
         bonusExp = (int)((float)bonusExp + (float)event.getOriginalExperience() * (EnigmaticItems.MONSTER_CHARM.bonusXPModifier - 1.0F));
      }

      if (player != null && SuperpositionHandler.hasCurio(player, EnigmaticItems.CURSED_RING)) {
         bonusExp = (int)((double)bonusExp + (double)event.getOriginalExperience() * CursedRing.experienceBonus.getValue().asMultiplier());
      }

      event.setDroppedExperience(event.getDroppedExperience() + bonusExp);
      if (event.getEntity() instanceof EnderDragon) {
      }
   }

   @SubscribeEvent(
      priority = EventPriority.NORMAL
   )
   public void onLivingDrops(LivingDropsEvent event) {
      if (event.getEntity().getClass() == Skeleton.class
         && event.isRecentlyHit()
         && event.getSource().m_7639_() != null
         && event.getSource().m_7639_() instanceof Player) {
         ItemStack weap = ((Player)event.getSource().m_7639_()).m_21205_();
         if (weap != null
            && weap.m_41720_() == EnigmaticItems.FORBIDDEN_AXE
            && !SuperpositionHandler.ifDroplistContainsItem(event.getDrops(), Items.f_42678_)
            && this.theySeeMeRollin(event.getLootingLevel())) {
            this.addDrop(event, new ItemStack(Items.f_42678_, 1));
            if (event.getSource().m_7639_() instanceof ServerPlayer) {
               BeheadingTrigger.INSTANCE.trigger((ServerPlayer)event.getSource().m_7639_());
            }
         }
      } else if (event.getEntity().getClass() == WitherSkeleton.class
         && event.isRecentlyHit()
         && event.getSource().m_7639_() != null
         && event.getSource().m_7639_() instanceof Player) {
         ItemStack weap = ((Player)event.getSource().m_7639_()).m_21205_();
         if (weap != null && weap.m_41720_() == EnigmaticItems.FORBIDDEN_AXE) {
            if (!SuperpositionHandler.ifDroplistContainsItem(event.getDrops(), Items.f_42679_) && this.theySeeMeRollin(event.getLootingLevel())) {
               this.addDrop(event, new ItemStack(Items.f_42679_, 1));
            }

            if (event.getSource().m_7639_() instanceof ServerPlayer && SuperpositionHandler.ifDroplistContainsItem(event.getDrops(), Items.f_42679_)) {
               BeheadingTrigger.INSTANCE.trigger((ServerPlayer)event.getSource().m_7639_());
            }
         }
      } else if (event.getEntity().getClass() == Zombie.class
         && event.isRecentlyHit()
         && event.getSource().m_7639_() != null
         && event.getSource().m_7639_() instanceof Player) {
         ItemStack weap = ((Player)event.getSource().m_7639_()).m_21205_();
         if (weap != null
            && weap.m_41720_() == EnigmaticItems.FORBIDDEN_AXE
            && !SuperpositionHandler.ifDroplistContainsItem(event.getDrops(), Items.f_42681_)
            && this.theySeeMeRollin(event.getLootingLevel())) {
            this.addDrop(event, new ItemStack(Items.f_42681_, 1));
            if (event.getSource().m_7639_() instanceof ServerPlayer) {
               BeheadingTrigger.INSTANCE.trigger((ServerPlayer)event.getSource().m_7639_());
            }
         }
      } else if (event.getEntity().getClass() == Creeper.class
         && event.isRecentlyHit()
         && event.getSource().m_7639_() != null
         && event.getSource().m_7639_() instanceof Player) {
         ItemStack weap = ((Player)event.getSource().m_7639_()).m_21205_();
         if (weap != null
            && weap.m_41720_() == EnigmaticItems.FORBIDDEN_AXE
            && !SuperpositionHandler.ifDroplistContainsItem(event.getDrops(), Items.f_42682_)
            && this.theySeeMeRollin(event.getLootingLevel())) {
            this.addDrop(event, new ItemStack(Items.f_42682_, 1));
            if (event.getSource().m_7639_() instanceof ServerPlayer) {
               BeheadingTrigger.INSTANCE.trigger((ServerPlayer)event.getSource().m_7639_());
            }
         }
      } else if (event.getEntity().getClass() == EnderDragon.class
         && event.isRecentlyHit()
         && event.getSource().m_7639_() != null
         && event.getSource().m_7639_() instanceof Player) {
         ItemStack weap = ((Player)event.getSource().m_7639_()).m_21205_();
         if (weap != null
            && weap.m_41720_() == EnigmaticItems.FORBIDDEN_AXE
            && !SuperpositionHandler.ifDroplistContainsItem(event.getDrops(), Items.f_42683_)
            && this.theySeeMeRollin(event.getLootingLevel())) {
            this.addDrop(event, new ItemStack(Items.f_42683_, 1));
            if (event.getSource().m_7639_() instanceof ServerPlayer) {
               BeheadingTrigger.INSTANCE.trigger((ServerPlayer)event.getSource().m_7639_());
            }
         }
      }

      if (event.isRecentlyHit()
         && event.getSource() != null
         && event.getSource().m_7639_() instanceof Player
         && SuperpositionHandler.isTheCursedOne((Player)event.getSource().m_7639_())) {
         Player player = (Player)event.getSource().m_7639_();
         LivingEntity killed = event.getEntity();
         if (SuperpositionHandler.hasCurio(player, EnigmaticItems.AVARICE_SCROLL)) {
            this.addDropWithChance(event, new ItemStack(Items.f_42616_, 1), AvariceScroll.emeraldChance.getValue());
         }

         if (killed instanceof EnderDragon && SuperpositionHandler.isTheWorthyOne(player)) {
            int heartsGained = SuperpositionHandler.getPersistentInteger(player, "AbyssalHeartsGained", 0);
            if (heartsGained < 4) {
               ((IAbyssalHeartBearer)killed).dropAbyssalHeart(player);
            }
         }

         if (!CursedRing.enableSpecialDrops.getValue()) {
            return;
         }

         if (killed.getClass() == Shulker.class) {
            this.addDropWithChance(event, new ItemStack(EnigmaticItems.ASTRAL_DUST, 1), 20);
         } else if (killed.getClass() == Skeleton.class || killed.getClass() == Stray.class) {
            this.addDrop(event, this.getRandomSizeStack(Items.f_42412_, 3, 15));
         } else if (killed.getClass() == Zombie.class || killed.getClass() == Husk.class) {
            this.addDropWithChance(event, this.getRandomSizeStack(Items.f_42518_, 1, 3), 25);
         } else if (killed.getClass() == Spider.class || killed.getClass() == CaveSpider.class) {
            this.addDrop(event, this.getRandomSizeStack(Items.f_42401_, 2, 12));
         } else if (killed.getClass() == Guardian.class) {
            this.addDropWithChance(event, new ItemStack(Items.f_42715_, 1), 15);
            this.addDrop(event, this.getRandomSizeStack(Items.f_42696_, 2, 5));
         } else if (killed instanceof ElderGuardian) {
            this.addDrop(event, this.getRandomSizeStack(Items.f_42696_, 4, 16));
            this.addDrop(event, this.getRandomSizeStack(Items.f_42695_, 7, 28));
            this.addOneOf(
               event,
               new ItemStack(EnigmaticItems.GUARDIAN_HEART, 1),
               new ItemStack(Items.f_42716_, 1),
               new ItemStack(Items.f_42437_, 1),
               new ItemStack(Items.f_42545_, 1),
               EnchantmentHelper.m_220292_(killed.m_217043_(), new ItemStack(Items.f_42713_, 1), 25 + THEY_SEE_ME_ROLLIN.nextInt(15), true)
            );
         } else if (killed.getClass() == EnderMan.class) {
            this.addDropWithChance(event, this.getRandomSizeStack(Items.f_42545_, 1, 2), 40);
         } else if (killed.getClass() == Blaze.class) {
            this.addDrop(event, this.getRandomSizeStack(Items.f_42593_, 0, 5));
         } else if (killed.getClass() == ZombifiedPiglin.class) {
            this.addDropWithChance(event, this.getRandomSizeStack(Items.f_42417_, 1, 3), 40);
            this.addDropWithChance(event, this.getRandomSizeStack(Items.f_42525_, 1, 7), 30);
         } else if (killed.getClass() == Witch.class) {
            this.addDropWithChance(event, new ItemStack(Items.f_42586_, 1), 30);
            this.addDropWithChance(event, this.getRandomSizeStack(Items.f_42714_, 1, 3), 50);
         } else if (killed.getClass() == Pillager.class || killed.getClass() == Vindicator.class) {
            this.addDrop(event, this.getRandomSizeStack(Items.f_42616_, 0, 4));
         } else if (killed.getClass() == Villager.class) {
            this.addDrop(event, this.getRandomSizeStack(Items.f_42616_, 2, 6));
         } else if (killed.getClass() == Creeper.class) {
            this.addDrop(event, this.getRandomSizeStack(Items.f_42403_, 4, 12));
         } else if (killed.getClass() == PiglinBrute.class) {
            this.addDropWithChance(event, new ItemStack(Items.f_42419_, 1), 20);
         } else if (killed.getClass() == Evoker.class) {
            this.addDrop(event, new ItemStack(Items.f_42747_, 1));
            this.addDrop(event, this.getRandomSizeStack(Items.f_42616_, 5, 20));
            this.addDropWithChance(event, new ItemStack(Items.f_42437_, 1), 10);
            this.addDropWithChance(event, this.getRandomSizeStack(Items.f_42584_, 1, 3), 30);
            this.addDropWithChance(event, this.getRandomSizeStack(Items.f_42585_, 2, 4), 30);
            this.addDropWithChance(event, this.getRandomSizeStack(Items.f_42612_, 4, 10), 50);
         } else if (killed.getClass() == WitherSkeleton.class) {
            this.addDrop(event, this.getRandomSizeStack(Items.f_42593_, 0, 3));
            this.addDropWithChance(event, new ItemStack(Items.f_42586_, 1), 20);
            this.addDropWithChance(event, new ItemStack(Items.f_42419_, 1), 7);
         } else if (killed.getClass() == Ghast.class) {
            this.addDrop(event, this.getRandomSizeStack(Items.f_42714_, 1, 4));
         } else if (killed.getClass() == Drowned.class) {
            this.addDropWithChance(event, this.getRandomSizeStack(Items.f_42534_, 1, 3), 30);
         } else if (killed.getClass() == Vex.class) {
            this.addDrop(event, this.getRandomSizeStack(Items.f_42525_, 0, 2));
            this.addDropWithChance(event, new ItemStack(Items.f_42714_, 1), 30);
         } else if (killed.getClass() != Phantom.class) {
            if (killed.getClass() == Piglin.class) {
               this.addDropWithChance(event, this.getRandomSizeStack(Items.f_42417_, 2, 4), 50);
            } else if (killed.getClass() == Ravager.class) {
               this.addDrop(event, this.getRandomSizeStack(Items.f_42616_, 3, 10));
               this.addDrop(event, this.getRandomSizeStack(Items.f_42454_, 2, 7));
               this.addDropWithChance(event, this.getRandomSizeStack(Items.f_42415_, 0, 4), 50);
            } else if (killed.getClass() != Silverfish.class) {
               if (killed.getClass() == MagmaCube.class) {
                  this.addDropWithChance(event, new ItemStack(Items.f_42593_, 1), 50);
               } else if (killed.getClass() == Chicken.class) {
                  this.addDropWithChance(event, new ItemStack(Items.f_42521_, 1), 50);
               } else if (killed instanceof WitherBoss) {
                  this.addDrop(event, this.getRandomSizeStack(EnigmaticItems.EVIL_ESSENCE, 1, 4));
               }
            }
         }
      }
   }

   @SubscribeEvent(
      priority = EventPriority.LOWEST
   )
   public void onLivingDropsLowest(LivingDropsEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         CompoundTag deathLocation = new CompoundTag();
         deathLocation.m_128347_("x", player.m_20185_());
         deathLocation.m_128347_("y", player.m_20186_());
         deathLocation.m_128347_("z", player.m_20189_());
         deathLocation.m_128359_("dimension", player.f_19853_.m_46472_().m_135782_().toString());
         SuperpositionHandler.setPersistentTag(player, "LastDeathLocation", deathLocation);
         boolean droppedCrystal = false;
         boolean hadEscapeScroll = this.hadEscapeScroll(player);
         DimensionalPosition dimPoint = hadEscapeScroll
            ? SuperpositionHandler.getRespawnPoint(player)
            : new DimensionalPosition(player.m_20185_(), player.m_20186_(), player.m_20189_(), player.f_19853_);
         if (hadEscapeScroll) {
            BlockPos respawnPos = player.m_8961_();
            ServerLevel respawnLevel = ServerLifecycleHooks.getCurrentServer().m_129880_(player.m_8963_());
            boolean isEndAnchor = false;
            if (respawnLevel.m_8055_(respawnPos).m_60713_(EnigmaticBlocks.END_ANCHOR)) {
               dimPoint = new DimensionalPosition(
                  (double)respawnPos.m_123341_() + 0.5, (double)respawnPos.m_123342_() + 1.5, (double)respawnPos.m_123343_() + 0.5, respawnLevel
               );
               isEndAnchor = true;
            }

            player.f_19853_.m_5594_(null, player.m_20183_(), SoundEvents.f_11852_, SoundSource.PLAYERS, 1.0F, (float)(0.8F + Math.random() * 0.2));
            EnigmaticLegacy.packetInstance
               .send(
                  PacketDistributor.NEAR
                     .with(() -> new TargetPoint(player.m_20185_(), player.m_20186_(), player.m_20189_(), 128.0, player.f_19853_.m_46472_())),
                  new PacketPortalParticles(player.m_20185_(), player.m_20186_() + (double)(player.m_20206_() / 2.0F), player.m_20189_(), 100, 1.25, false)
               );

            for (ItemEntity dropIt : event.getDrops()) {
               ItemEntity alternativeDrop = new ItemEntity(dimPoint.world, dimPoint.posX, dimPoint.posY, dimPoint.posZ, dropIt.m_32055_());
               alternativeDrop.m_6021_(dimPoint.posX, dimPoint.posY, dimPoint.posZ);
               if (!isEndAnchor) {
                  alternativeDrop.m_20334_(THEY_SEE_ME_ROLLIN.nextDouble() - 0.5, THEY_SEE_ME_ROLLIN.nextDouble() - 0.5, THEY_SEE_ME_ROLLIN.nextDouble() - 0.5);
               } else {
                  alternativeDrop.m_20334_(0.0, 0.0, 0.0);
               }

               dimPoint.world.m_7967_(alternativeDrop);
               dropIt.m_32045_(ItemStack.f_41583_);
            }

            event.getDrops().clear();
            DimensionalPosition dimPointFinal = dimPoint;
            dimPoint.world
               .m_5594_(
                  null,
                  new BlockPos(dimPoint.posX, dimPoint.posY, dimPoint.posZ),
                  SoundEvents.f_11852_,
                  SoundSource.PLAYERS,
                  1.0F,
                  (float)(0.8F + Math.random() * 0.2)
               );
            EnigmaticLegacy.packetInstance
               .send(
                  PacketDistributor.NEAR
                     .with(() -> new TargetPoint(dimPointFinal.posX, dimPointFinal.posY, dimPointFinal.posZ, 128.0, dimPointFinal.world.m_46472_())),
                  new PacketRecallParticles(dimPoint.posX, dimPoint.posY, dimPoint.posZ, 48, false)
               );
         }

         if (this.hadEnigmaticAmulet(player) && !event.getDrops().isEmpty() && EnigmaticItems.ENIGMATIC_AMULET.isVesselEnabled()) {
            ItemStack soulCrystal = SuperpositionHandler.canDropSoulCrystal(player, this.hadCursedRing(player))
               ? EnigmaticItems.SOUL_CRYSTAL.createCrystalFrom(player)
               : null;
            ItemStack storageCrystal = EnigmaticItems.STORAGE_CRYSTAL.storeDropsOnCrystal(event.getDrops(), player, soulCrystal);
            PermanentItemEntity droppedStorageCrystal = new PermanentItemEntity(
               dimPoint.world, dimPoint.getPosX(), dimPoint.getPosY() + 1.5, dimPoint.getPosZ(), storageCrystal
            );
            droppedStorageCrystal.setOwnerId(player.m_20148_());
            dimPoint.world.m_7967_(droppedStorageCrystal);
            EnigmaticLegacy.LOGGER
               .info(
                  "Summoned Extradimensional Storage Crystal for "
                     + player.m_36316_().getName()
                     + " at X: "
                     + dimPoint.getPosX()
                     + ", Y: "
                     + dimPoint.getPosY()
                     + ", Z: "
                     + dimPoint.getPosZ()
               );
            event.getDrops().clear();
            if (soulCrystal != null) {
               droppedCrystal = true;
            }

            SoulArchive.getInstance().addItem(droppedStorageCrystal);
         } else if (SuperpositionHandler.canDropSoulCrystal(player, this.hadCursedRing(player))) {
            ItemStack soulCrystal = EnigmaticItems.SOUL_CRYSTAL.createCrystalFrom(player);
            PermanentItemEntity droppedSoulCrystal = new PermanentItemEntity(
               dimPoint.world, dimPoint.getPosX(), dimPoint.getPosY() + 1.5, dimPoint.getPosZ(), soulCrystal
            );
            droppedSoulCrystal.setOwnerId(player.m_20148_());
            dimPoint.world.m_7967_(droppedSoulCrystal);
            EnigmaticLegacy.LOGGER
               .info(
                  "Teared Soul Crystal from "
                     + player.m_36316_().getName()
                     + " at X: "
                     + dimPoint.getPosX()
                     + ", Y: "
                     + dimPoint.getPosY()
                     + ", Z: "
                     + dimPoint.getPosZ()
               );
            droppedCrystal = true;
            SoulArchive.getInstance().addItem(droppedSoulCrystal);
         }

         if (SuperpositionHandler.isPermanentlyDead(player)) {
            EnigmaticLegacy.packetInstance.send(PacketDistributor.PLAYER.with(() -> player), new PacketPermadeath());
            SuperpositionHandler.setCurrentWorldFractured(true);
         }

         ResourceLocation soulLossAdvancement = new ResourceLocation("enigmaticlegacy", "book/soul_loss");
         if (SuperpositionHandler.isAffectedBySoulLoss(player, this.hadCursedRing(player))) {
            SuperpositionHandler.grantAdvancement(player, soulLossAdvancement);
         } else if (SuperpositionHandler.hasAdvancement(player, soulLossAdvancement)) {
            SuperpositionHandler.revokeAdvancement(player, soulLossAdvancement);
         }

         POSTMORTAL_POSESSIONS.removeAll(player);
      } else {
         if (event.getEntity() instanceof Player) {
            POSTMORTAL_POSESSIONS.removeAll(event.getEntity());
         }

         if (event.isRecentlyHit()
            && event.getSource() != null
            && event.getSource().m_7639_() instanceof Player
            && SuperpositionHandler.isTheCursedOne((Player)event.getSource().m_7639_())) {
            Player player = (Player)event.getSource().m_7639_();
            LivingEntity killed = event.getEntity();
            if (killed instanceof EnderMan
               && killed.f_19853_.m_46472_().equals(EnigmaticLegacy.PROXY.getEndKey())
               && killed.getPersistentData().m_128471_("EnderSlayerVictim")) {
               int extraXP = 0;

               for (ItemEntity entity : event.getDrops()) {
                  if (entity.m_32055_() != null) {
                     if (entity.m_32055_().m_150930_(Items.f_42584_)) {
                        this.dropXPOrb(killed.f_19853_, killed.m_20185_(), killed.m_20186_(), killed.m_20189_(), 10);
                     } else if (entity.m_32055_().m_150930_(Items.f_42545_)) {
                        this.dropXPOrb(killed.f_19853_, killed.m_20185_(), killed.m_20186_(), killed.m_20189_(), 20);
                     }
                  }
               }

               event.getDrops().clear();
               event.setCanceled(true);
            }
         }
      }
   }

   private void dropXPOrb(Level level, double x, double y, double z, int xp) {
      ExperienceOrb orb = new ExperienceOrb(level, x, y, z, xp);
      level.m_7967_(orb);
   }

   @SubscribeEvent(
      priority = EventPriority.LOWEST
   )
   public void onLootTablesLoaded(LootTableLoadEvent event) {
      if (OmniconfigHandler.customDungeonLootEnabled.getValue()) {
         List<ResourceLocation> underwaterRuins = new ArrayList<>();
         underwaterRuins.add(BuiltInLootTables.f_78691_);
         underwaterRuins.add(BuiltInLootTables.f_78690_);
         LootPool overworldLiterature = !OmniconfigHandler.isItemEnabled(EnigmaticItems.TATTERED_TOME)
            ? null
            : SuperpositionHandler.constructLootPool(
               "overworldLiterature",
               -7.0F,
               2.0F,
               SuperpositionHandler.createOptionalLootEntry(EnigmaticItems.TATTERED_TOME, 100)
                  .m_79078_(LootFunctionRevelation.of(UniformGenerator.m_165780_(1.0F, 3.0F), UniformGenerator.m_165780_(50.0F, 500.0F)))
            );
         LootPool netherLiterature = !OmniconfigHandler.isItemEnabled(EnigmaticItems.WITHERED_TOME)
            ? null
            : SuperpositionHandler.constructLootPool(
               "netherLiterature",
               -7.0F,
               2.0F,
               SuperpositionHandler.createOptionalLootEntry(EnigmaticItems.WITHERED_TOME, 100)
                  .m_79078_(LootFunctionRevelation.of(UniformGenerator.m_165780_(1.0F, 3.0F), UniformGenerator.m_165780_(100.0F, 700.0F)))
            );
         LootPool endLiterature = !OmniconfigHandler.isItemEnabled(EnigmaticItems.CORRUPTED_TOME)
            ? null
            : SuperpositionHandler.constructLootPool(
               "endLiterature",
               -7.0F,
               2.0F,
               SuperpositionHandler.createOptionalLootEntry(EnigmaticItems.CORRUPTED_TOME, 100)
                  .m_79078_(LootFunctionRevelation.of(UniformGenerator.m_165780_(1.0F, 4.0F), UniformGenerator.m_165780_(200.0F, 1000.0F)))
            );
         if (SuperpositionHandler.getMergedAir$EarthenDungeons().contains(event.getName())) {
            LootPool poolSpellstones = SuperpositionHandler.constructLootPool(
               "spellstones",
               -12.0F,
               1.0F,
               SuperpositionHandler.createOptionalLootEntry(EnigmaticItems.GOLEM_HEART, 35),
               SuperpositionHandler.createOptionalLootEntry(EnigmaticItems.ANGEL_BLESSING, 65),
               SuperpositionHandler.createOptionalLootEntry(EnigmaticItems.ICHOR_BOTTLE, 65)
            );
            LootTable modified = event.getTable();
            modified.addPool(poolSpellstones);
            event.setTable(modified);
         } else if (SuperpositionHandler.getMergedEnder$EarthenDungeons().contains(event.getName())) {
            LootPool poolSpellstones = SuperpositionHandler.constructLootPool(
               "spellstones",
               -10.0F,
               1.0F,
               SuperpositionHandler.createOptionalLootEntry(EnigmaticItems.EYE_OF_NEBULA, 35),
               SuperpositionHandler.createOptionalLootEntry(EnigmaticItems.GOLEM_HEART, 65)
            );
            LootTable modified = event.getTable();
            modified.addPool(poolSpellstones);
            event.setTable(modified);
         } else if (SuperpositionHandler.getAirDungeons().contains(event.getName())) {
            LootPool poolSpellstones = SuperpositionHandler.constructLootPool(
               "spellstones",
               -10.0F,
               1.0F,
               SuperpositionHandler.createOptionalLootEntry(EnigmaticItems.ANGEL_BLESSING, 100),
               SuperpositionHandler.createOptionalLootEntry(EnigmaticItems.ICHOR_BOTTLE, 100)
            );
            LootTable modified = event.getTable();
            modified.addPool(poolSpellstones);
            event.setTable(modified);
         } else if (SuperpositionHandler.getEarthenDungeons().contains(event.getName())) {
            LootPool poolSpellstones = SuperpositionHandler.constructLootPool(
               "spellstones", -20.0F, 1.0F, SuperpositionHandler.createOptionalLootEntry(EnigmaticItems.GOLEM_HEART, 100)
            );
            LootTable modified = event.getTable();
            modified.addPool(poolSpellstones);
            event.setTable(modified);
         } else if (SuperpositionHandler.getNetherDungeons().contains(event.getName())) {
            LootPool poolSpellstones = SuperpositionHandler.constructLootPool(
               "spellstones", -24.0F, 1.0F, SuperpositionHandler.createOptionalLootEntry(EnigmaticItems.BLAZING_CORE, 100)
            );
            LootTable modified = event.getTable();
            modified.addPool(poolSpellstones);
            event.setTable(modified);
         } else if (SuperpositionHandler.getWaterDungeons().contains(event.getName())) {
            LootPool poolSpellstones = SuperpositionHandler.constructLootPool(
               "spellstones", -20.0F, 1.0F, SuperpositionHandler.createOptionalLootEntry(EnigmaticItems.OCEAN_STONE, 100)
            );
            LootTable modified = event.getTable();
            modified.addPool(poolSpellstones);
            event.setTable(modified);
         } else if (SuperpositionHandler.getEnderDungeons().contains(event.getName())) {
            LootPool poolSpellstones = SuperpositionHandler.constructLootPool(
               "spellstones",
               -12.0F,
               1.0F,
               SuperpositionHandler.createOptionalLootEntry(EnigmaticItems.EYE_OF_NEBULA, 90),
               SuperpositionHandler.createOptionalLootEntry(EnigmaticItems.VOID_PEARL, 10)
            );
            LootTable modified = event.getTable();
            modified.addPool(poolSpellstones);
            event.setTable(modified);
         }

         if (SuperpositionHandler.getOverworldDungeons().contains(event.getName())) {
            LootPool epic = SuperpositionHandler.constructLootPool(
               "epic",
               1.0F,
               2.0F,
               SuperpositionHandler.itemEntryBuilderED(Items.f_42385_, 10, 20.0F, 30.0F, 1.0F, 0.8F),
               SuperpositionHandler.itemEntryBuilderED(Items.f_42386_, 10, 20.0F, 30.0F, 1.0F, 0.8F),
               SuperpositionHandler.itemEntryBuilderED(Items.f_42383_, 10, 20.0F, 30.0F, 1.0F, 0.8F),
               SuperpositionHandler.itemEntryBuilderED(Items.f_42384_, 10, 20.0F, 30.0F, 1.0F, 0.8F),
               SuperpositionHandler.itemEntryBuilderED(Items.f_42411_, 10, 20.0F, 30.0F, 1.0F, 0.8F),
               SuperpositionHandler.createOptionalLootEntry(EnigmaticItems.IRON_RING, 20),
               LootItem.m_79579_(EnigmaticItems.COMMON_POTION)
                  .m_79707_(20)
                  .m_79078_(SetNbtFunction.m_81187_(PotionHelper.createAdvancedPotion(EnigmaticItems.COMMON_POTION, EnigmaticPotions.HASTE).m_41783_())),
               SuperpositionHandler.createOptionalLootEntry(EnigmaticItems.MAGNET_RING, 8),
               SuperpositionHandler.createOptionalLootEntry(EnigmaticItems.UNHOLY_GRAIL, 1),
               SuperpositionHandler.createOptionalLootEntry(EnigmaticItems.LORE_INSCRIBER, 5),
               LootItem.m_79579_(Items.f_42524_).m_79707_(10),
               LootItem.m_79579_(Items.f_42522_).m_79707_(10),
               LootItem.m_79579_(Items.f_42616_).m_79707_(20).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 4.0F))),
               LootItem.m_79579_(Items.f_42518_).m_79707_(20).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 10.0F))),
               LootItem.m_79579_(Items.f_42454_).m_79707_(35).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(3.0F, 8.0F))),
               LootItem.m_79579_(Items.f_42687_).m_79707_(25).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(4.0F, 16.0F))),
               !SuperpositionHandler.getWaterDungeons().contains(event.getName()) && !event.getName().equals(BuiltInLootTables.f_78696_)
                  ? SuperpositionHandler.createOptionalLootEntry(EnigmaticItems.EARTH_HEART, 7)
                  : null
            );
            LootTable modified = event.getTable();
            modified.addPool(epic);
            if (event.getName() != BuiltInLootTables.f_78694_ && overworldLiterature != null) {
               modified.addPool(overworldLiterature);
            }

            event.setTable(modified);
         } else if (SuperpositionHandler.getNetherDungeons().contains(event.getName())) {
            ItemStack fireResistancePotion = new ItemStack(Items.f_42589_);
            fireResistancePotion = PotionUtils.m_43549_(fireResistancePotion, Potions.f_43611_);
            LootPool epic = SuperpositionHandler.constructLootPool(
               "epic",
               1.0F,
               2.0F,
               SuperpositionHandler.itemEntryBuilderED(Items.f_42432_, 10, 25.0F, 30.0F, 1.0F, 1.0F),
               SuperpositionHandler.itemEntryBuilderED(Items.f_42433_, 10, 25.0F, 30.0F, 1.0F, 1.0F),
               SuperpositionHandler.itemEntryBuilderED(Items.f_42430_, 10, 25.0F, 30.0F, 1.0F, 1.0F),
               SuperpositionHandler.itemEntryBuilderED(Items.f_42431_, 10, 25.0F, 30.0F, 1.0F, 1.0F),
               SuperpositionHandler.createOptionalLootEntry(EnigmaticItems.VOID_STONE, 8),
               LootItem.m_79579_(Items.f_42616_).m_79707_(30).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 7.0F))),
               LootItem.m_79579_(Items.f_41951_).m_79707_(25).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 4.0F))),
               LootItem.m_79579_(Items.f_42586_).m_79707_(10).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 2.0F))),
               LootItem.m_79579_(Items.f_42448_).m_79707_(30),
               LootItem.m_79579_(Items.f_42589_).m_79707_(15).m_79078_(SetNbtFunction.m_81187_(fireResistancePotion.m_41783_())),
               SuperpositionHandler.getBastionChests().contains(event.getName()) ? LootItem.m_79579_(EnigmaticItems.FORBIDDEN_FRUIT).m_79707_(4) : null
            );
            LootTable modified = event.getTable();
            if (!event.getName().equals(BuiltInLootTables.f_78697_)) {
               modified.addPool(epic);
            } else {
               LootPool scroll = SuperpositionHandler.constructLootPool(
                  "darkest_scroll",
                  0.0F,
                  1.0F,
                  LootItem.m_79579_(EnigmaticItems.DARKEST_SCROLL)
                     .m_79707_(100)
                     .m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 1.0F)))
               );
               modified.addPool(scroll);
            }

            if (netherLiterature != null) {
               modified.addPool(netherLiterature);
            }

            event.setTable(modified);
         } else if (event.getName().equals(BuiltInLootTables.f_78741_)) {
            LootPool epicx = SuperpositionHandler.constructLootPool(
               "epic",
               1.0F,
               2.0F,
               LootItem.m_79579_(Items.f_42584_).m_79707_(40).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 5.0F))),
               LootItem.m_79579_(Items.f_42545_).m_79707_(20).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 2.0F))),
               LootItem.m_79579_(Items.f_42546_).m_79707_(30).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 4.0F))),
               LootItem.m_79579_(Items.f_42677_).m_79707_(30).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 4.0F))),
               LootItem.m_79579_(Items.f_42714_).m_79707_(25).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(3.0F, 7.0F))),
               LootItem.m_79579_(Items.f_42100_).m_79707_(10),
               LootItem.m_79579_(Items.f_42502_).m_79707_(15),
               LootItem.m_79579_(Items.f_42729_).m_79707_(7),
               SuperpositionHandler.createOptionalLootEntry(EnigmaticItems.LORE_INSCRIBER, 10),
               SuperpositionHandler.createOptionalLootEntry(EnigmaticItems.RECALL_POTION, 15),
               SuperpositionHandler.createOptionalLootEntry(EnigmaticItems.MENDING_MIXTURE, 40),
               SuperpositionHandler.createOptionalLootEntry(EnigmaticItems.ASTRAL_DUST, 85, 1.0F, 4.0F),
               SuperpositionHandler.createOptionalLootEntry(EnigmaticItems.ETHERIUM_ORE, 60, 1.0F, 2.0F),
               SuperpositionHandler.createOptionalLootEntry(EnigmaticItems.EXTRADIMENSIONAL_EYE, 20)
            );
            LootTable modifiedx = event.getTable();
            modifiedx.addPool(epicx);
            if (endLiterature != null) {
               modifiedx.addPool(endLiterature);
            }

            event.setTable(modifiedx);
         } else if (event.getName().equals(BuiltInLootTables.f_78764_)) {
            LootPool epicx = SuperpositionHandler.constructLootPool(
               "specialpyramidloot", -7.0F, 1.0F, SuperpositionHandler.createOptionalLootEntry(EnigmaticItems.INSIGNIA, 100)
            );
            LootTable modifiedx = event.getTable();
            modifiedx.addPool(epicx);
            if (endLiterature != null) {
               modifiedx.addPool(endLiterature);
            }

            event.setTable(modifiedx);
         }

         if (SuperpositionHandler.getLibraries().contains(event.getName())) {
            LootPool special = SuperpositionHandler.constructLootPool(
               "el_special",
               2.0F,
               3.0F,
               LootItem.m_79579_(EnigmaticItems.THICC_SCROLL).m_79707_(20).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 6.0F))),
               LootItem.m_79579_(EnigmaticItems.LORE_FRAGMENT).m_79707_(10).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 2.0F)))
            );
            LootTable modifiedx = event.getTable();
            modifiedx.addPool(special);
            if (OmniconfigHandler.isItemEnabled(EnigmaticItems.TATTERED_TOME)) {
               LootPool literature = SuperpositionHandler.constructLootPool(
                  "literature",
                  -4.0F,
                  3.0F,
                  SuperpositionHandler.createOptionalLootEntry(EnigmaticItems.TATTERED_TOME, 100)
                     .m_79078_(LootFunctionRevelation.of(UniformGenerator.m_165780_(1.0F, 3.0F), UniformGenerator.m_165780_(50.0F, 500.0F)))
               );
               modifiedx.addPool(literature);
            }

            event.setTable(modifiedx);
         }

         if (event.getName().equals(BuiltInLootTables.f_78691_) || event.getName().equals(BuiltInLootTables.f_78690_)) {
            LootPool special = SuperpositionHandler.constructLootPool(
               "el_special",
               -5.0F,
               1.0F,
               LootItem.m_79579_(Items.f_42713_)
                  .m_79078_(SetItemDamageFunction.m_165430_(UniformGenerator.m_165780_(0.5F, 1.0F)))
                  .m_79078_(EnchantWithLevelsFunction.m_165196_(UniformGenerator.m_165780_(15.0F, 40.0F)).m_80499_())
            );
            LootTable modifiedx = event.getTable();
            modifiedx.addPool(special);
            event.setTable(modifiedx);
         }
      }
   }

   @SubscribeEvent(
      priority = EventPriority.LOW
   )
   public void onPlayerJoin(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer) {
         ServerPlayer player = (ServerPlayer)event.getEntity();
         if (SuperpositionHandler.isPermanentlyDead(player)) {
            EnigmaticLegacy.packetInstance.send(PacketDistributor.PLAYER.with(() -> player), new PacketPermadeath());
            SuperpositionHandler.setCurrentWorldFractured(true);
         } else {
            SuperpositionHandler.setCurrentWorldFractured(false);
         }

         if (!OmniconfigWrapper.syncAllToPlayer((ServerPlayer)event.getEntity())) {
            OmniconfigWrapper.onRemoteServer = false;
            EnigmaticLegacy.LOGGER.info("Logging in to local integrated server; no synchronization is required.");
         }

         try {
            this.syncPlayTime(player);
            if (!EnigmaticLegacy.enigmaticLegacy.isCSGPresent()) {
               grantStarterGear(player);
            }

            if (EnigmaticItems.FORBIDDEN_FRUIT.haveConsumedFruit(player)) {
               ForbiddenFruitTrigger.INSTANCE.trigger(player);
            }

            if (SuperpositionHandler.hasAdvancement(player, new ResourceLocation("enigmaticlegacy", "main/discover_spellstone"))) {
               SuperpositionHandler.unlockSpecialSlot("spellstone", event.getEntity());
               SuperpositionHandler.setPersistentBoolean(player, "enigmaticlegacy.spellstones_enabled", true);
            }

            if (SuperpositionHandler.hasAdvancement(player, new ResourceLocation("enigmaticlegacy", "main/discover_scroll"))) {
               SuperpositionHandler.unlockSpecialSlot("scroll", event.getEntity());
               SuperpositionHandler.setPersistentBoolean(player, "enigmaticlegacy.scrolls_enabled", true);
            }

            ServerRecipeBook book = player.m_8952_();
            if (OmniconfigHandler.retriggerRecipeUnlocks.getValue()) {
               for (Recipe<?> theRecipe : player.f_19853_.m_7465_().m_44051_()) {
                  if (book.m_12709_(theRecipe)) {
                     CriteriaTriggers.f_10572_.m_63718_(player, theRecipe);
                  }
               }
            }
         } catch (Exception var6) {
            EnigmaticLegacy.LOGGER.error("Failed to check player's advancements upon joining the world!");
            var6.printStackTrace();
         }
      }
   }

   @SubscribeEvent
   public void onAdvancement(AdvancementEvent event) {
      String id = event.getAdvancement().m_138327_().toString();
      Player player = event.getEntity();
      if (player instanceof ServerPlayer && id.startsWith("enigmaticlegacy:book/")) {
         EnigmaticLegacy.packetInstance
            .send(PacketDistributor.PLAYER.with(() -> (ServerPlayer)player), new PacketSetEntryState(false, id.replace("book/", "")));
      }

      if (id.equals("enigmaticlegacy:main/discover_spellstone")) {
         EnigmaticLegacy.packetInstance.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer)event.getEntity()), new PacketSlotUnlocked("spellstone"));
         SuperpositionHandler.unlockSpecialSlot("spellstone", player);
         SuperpositionHandler.setPersistentBoolean(player, "enigmaticlegacy.spellstones_enabled", true);
      } else if (id.equals("enigmaticlegacy:main/discover_scroll")) {
         EnigmaticLegacy.packetInstance.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer)event.getEntity()), new PacketSlotUnlocked("scroll"));
         SuperpositionHandler.unlockSpecialSlot("scroll", player);
         SuperpositionHandler.setPersistentBoolean(player, "enigmaticlegacy.scrolls_enabled", true);
      }
   }

   @SubscribeEvent
   public void onEndPortal(EndPortalActivatedEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         Quote.END_DOORSTEP.playOnceIfUnlocked(player, 40);
      }
   }

   @SubscribeEvent
   public void onEntitySummon(SummonedEntityEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         if (event.getSummonedEntity() instanceof WitherBoss) {
            Quote.COUNTLESS_DEAD.playOnceIfUnlocked(player, 20);
         } else if (event.getSummonedEntity() instanceof EnderDragon) {
            Quote.HORRIBLE_EXISTENCE.playOnceIfUnlocked(player, 100);
         }
      }
   }

   @SubscribeEvent
   public void onEnteredBlock(EnterBlockEvent event) {
      if (event.getEntity() instanceof ServerPlayer player && event.getBlockState().m_60734_() == Blocks.f_50446_) {
         Quote.I_WANDERED.playOnceIfUnlocked(player, 160);
      }
   }

   @SubscribeEvent
   public void onPlayerRespawn(PlayerRespawnEvent event) {
      Player player = event.getEntity();
      if (!player.f_19853_.f_46443_) {
         LAST_SOUL_COMPASS_UPDATE.remove(player);
         if (!event.isEndConquered()) {
            if (SuperpositionHandler.getPersistentBoolean(player, "DestroyedCursedRing", false)) {
               SuperpositionHandler.removePersistentTag(player, "DestroyedCursedRing");
               Quote.getRandom(Quote.RING_DESTRUCTION).playIfUnlocked((ServerPlayer)player, 10);
            } else if (THEY_SEE_ME_ROLLIN.nextDouble() > 0.2) {
               if (SuperpositionHandler.getPersistentBoolean(player, "DeathFromEntity", false)) {
                  Quote.getRandom(Quote.DEATH_QUOTES_ENTITY).playIfUnlocked((ServerPlayer)player, 10);
               } else {
                  Quote.getRandom(Quote.DEATH_QUOTES).playIfUnlocked((ServerPlayer)player, 10);
               }
            }

            if (!player.f_19853_.m_46469_().m_46207_(GameRules.f_46133_)) {
               if (SuperpositionHandler.hasPersistentTag(player, "enigmaticlegacy.scrolls_enabled")) {
                  SuperpositionHandler.unlockSpecialSlot("scroll", event.getEntity());
               }

               if (SuperpositionHandler.hasPersistentTag(player, "enigmaticlegacy.spellstones_enabled")) {
                  SuperpositionHandler.unlockSpecialSlot("spellstone", event.getEntity());
               }
            }
         }

         SuperpositionHandler.setCurrentWorldCursed(SuperpositionHandler.isTheCursedOne(player));
      }
   }

   @SubscribeEvent
   @OnlyIn(Dist.CLIENT)
   public void onAnvilOpen(Init event) {
      if (PermadeathScreen.active != null && event.getScreen() != PermadeathScreen.active) {
         Minecraft.m_91087_().m_91152_(PermadeathScreen.active);
      }

      if (event.getScreen() instanceof AnvilScreen screen) {
         EnigmaticLegacy.packetInstance.send(PacketDistributor.SERVER.noArg(), new PacketAnvilField(""));

         try {
            for (Field f : screen.getClass().getDeclaredFields()) {
               f.setAccessible(true);
               if (f.get(screen) instanceof EditBox) {
                  EditBox widget = (EditBox)f.get(screen);
                  widget.m_94199_(64);
               }
            }
         } catch (Exception var8) {
            var8.printStackTrace();
         }
      }
   }

   @SubscribeEvent
   @OnlyIn(Dist.CLIENT)
   public void onLogin(PlayerLoggedInEvent event) {
   }

   @SubscribeEvent
   public void onAnvilUpdate(AnvilUpdateEvent event) {
      if (event.getLeft().m_41613_() == 1 && event.getRight().m_41720_().equals(EnigmaticItems.LORE_FRAGMENT) && event.getRight().m_41737_("display") != null) {
         event.setCost(4);
         event.setMaterialCost(1);
         event.setOutput(ItemLoreHelper.mergeDisplayData(event.getRight(), event.getLeft().m_41777_()));
      }
   }

   @SubscribeEvent
   public void onItemCrafted(ItemCraftedEvent event) {
      if (event.getEntity() != null && !event.getEntity().f_19853_.f_46443_) {
         if ((event.getInventory().m_18947_(EnigmaticItems.ENCHANTMENT_TRANSPOSER) == 1 || event.getInventory().m_18947_(EnigmaticItems.CURSE_TRANSPOSER) == 1)
            && event.getCrafting().m_41720_() == Items.f_42690_) {
            event.getEntity()
               .f_19853_
               .m_5594_(null, event.getEntity().m_20183_(), SoundEvents.f_11887_, SoundSource.PLAYERS, 1.0F, (float)(0.9F + Math.random() * 0.1F));
         } else if (event.getCrafting().m_41720_() == EnigmaticItems.CURSED_STONE) {
            event.getEntity()
               .f_19853_
               .m_5594_(null, event.getEntity().m_20183_(), SoundEvents.f_11736_, SoundSource.PLAYERS, 1.0F, (float)(0.9F + Math.random() * 0.1F));
         }
      }
   }

   @SubscribeEvent
   public void onAttackTargetSet(LivingSetAttackTargetEvent event) {
      if (event.getTarget() instanceof Player) {
         Player player = (Player)event.getTarget();
         if (event.getEntity() instanceof Mob) {
            Mob insect = (Mob)event.getEntity();
            if (insect.m_6336_() == MobType.f_21642_ && SuperpositionHandler.hasAntiInsectAcknowledgement(player)) {
               insect.m_6710_(null);
            }

            if (insect instanceof Guardian
               && insect.getClass() != ElderGuardian.class
               && SuperpositionHandler.hasItem(player, EnigmaticItems.GUARDIAN_HEART)
               && SuperpositionHandler.isTheCursedOne(player)) {
               boolean isBlacklisted = AGERED_GUARDIANS.containsEntry(player, insect);
               if (insect.m_21188_() != player && !isBlacklisted) {
                  insect.m_6710_(null);
               } else if (!isBlacklisted) {
                  AGERED_GUARDIANS.put(player, (Guardian)insect);
               }
            }
         }
      }
   }

   public void addDrop(LivingDropsEvent event, ItemStack drop) {
      ItemEntity entityitem = new ItemEntity(
         event.getEntity().f_19853_, event.getEntity().m_20185_(), event.getEntity().m_20186_(), event.getEntity().m_20189_(), drop
      );
      entityitem.m_32010_(10);
      event.getDrops().add(entityitem);
   }

   public void addDropWithChance(LivingDropsEvent event, ItemStack drop, int chance) {
      if (THEY_SEE_ME_ROLLIN.nextInt(100) < chance) {
         this.addDrop(event, drop);
      }
   }

   public ItemStack getRandomSizeStack(Item item, int minAmount, int maxAmount) {
      return new ItemStack(item, minAmount + THEY_SEE_ME_ROLLIN.nextInt(maxAmount - minAmount + 1));
   }

   public void addOneOf(LivingDropsEvent event, ItemStack... itemStacks) {
      int chosenStack = THEY_SEE_ME_ROLLIN.nextInt(itemStacks.length);
      this.addDrop(event, itemStacks[chosenStack]);
   }

   public static void grantStarterGear(ServerPlayer player) {
      EnigmaticLegacy.LOGGER.info("Granting starter gear to " + player.m_36316_().getName());
      if (!SuperpositionHandler.hasPersistentTag(player, "enigmaticlegacy.patchouliforce")) {
         EnigmaticLegacy.packetInstance.send(PacketDistributor.PLAYER.with(() -> player), new PacketPatchouliForce());
         SuperpositionHandler.setPersistentBoolean(player, "enigmaticlegacy.patchouliforce", true);
      }

      if (OmniconfigHandler.isItemEnabled(EnigmaticItems.ENIGMATIC_AMULET) && !SuperpositionHandler.hasPersistentTag(player, "enigmaticlegacy.firstjoin")) {
         ItemStack amuletStack = new ItemStack(EnigmaticItems.ENIGMATIC_AMULET);
         EnigmaticItems.ENIGMATIC_AMULET.setInscription(amuletStack, player.m_36316_().getName());
         EnigmaticItems.ENIGMATIC_AMULET.setProperlyGranted(amuletStack);
         if (!EnigmaticAmulet.seededColorGen.getValue()) {
            EnigmaticItems.ENIGMATIC_AMULET.setPseudoRandomColor(amuletStack);
         } else {
            EnigmaticItems.ENIGMATIC_AMULET.setSeededColor(amuletStack);
         }

         if (player.m_150109_().m_8020_(8).m_41619_()) {
            player.m_150109_().m_6836_(8, amuletStack);
         } else if (!player.m_150109_().m_36054_(amuletStack)) {
            ItemEntity dropAmulet = new ItemEntity(player.f_19853_, player.m_20185_(), player.m_20186_(), player.m_20189_(), amuletStack);
            player.f_19853_.m_7967_(dropAmulet);
         }

         SuperpositionHandler.setPersistentBoolean(player, "enigmaticlegacy.firstjoin", true);
      }

      if (OmniconfigHandler.isItemEnabled(EnigmaticItems.CURSED_RING) && !SuperpositionHandler.hasPersistentTag(player, "enigmaticlegacy.cursedgift")) {
         ItemStack cursedRingStack = new ItemStack(EnigmaticItems.CURSED_RING);
         if (!CursedRing.ultraHardcore.getValue()) {
            if (player.m_150109_().m_8020_(7).m_41619_()) {
               player.m_150109_().m_6836_(7, cursedRingStack);
            } else if (!player.m_150109_().m_36054_(cursedRingStack)) {
               ItemEntity dropRing = new ItemEntity(player.f_19853_, player.m_20185_(), player.m_20186_(), player.m_20189_(), cursedRingStack);
               player.f_19853_.m_7967_(dropRing);
            }
         } else {
            SuperpositionHandler.tryForceEquip(player, cursedRingStack);
         }

         SuperpositionHandler.setPersistentBoolean(player, "enigmaticlegacy.cursedgift", true);
      }
   }

   private boolean theySeeMeRollin(int lootingLevel) {
      int chance = Math.min(ForbiddenAxe.beheadingBase.getValue().asPercentage() + ForbiddenAxe.beheadingBonus.getValue().asPercentage() * lootingLevel, 100);
      return new Perhaps(chance).roll();
   }

   private boolean hadEnigmaticAmulet(Player player) {
      return POSTMORTAL_POSESSIONS.containsEntry(player, EnigmaticItems.ENIGMATIC_AMULET)
         || POSTMORTAL_POSESSIONS.containsEntry(player, EnigmaticItems.ASCENSION_AMULET);
   }

   private boolean hadEscapeScroll(Player player) {
      return POSTMORTAL_POSESSIONS.containsKey(player) ? POSTMORTAL_POSESSIONS.containsEntry(player, EnigmaticItems.ESCAPE_SCROLL) : false;
   }

   private boolean hadUnholyStone(Player player) {
      return POSTMORTAL_POSESSIONS.containsKey(player) ? POSTMORTAL_POSESSIONS.containsEntry(player, EnigmaticItems.CURSED_STONE) : false;
   }

   private boolean hadCursedRing(Player player) {
      return POSTMORTAL_POSESSIONS.containsKey(player) ? POSTMORTAL_POSESSIONS.containsEntry(player, EnigmaticItems.CURSED_RING) : false;
   }
}
