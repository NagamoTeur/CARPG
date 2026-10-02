package com.obscuria.aquamirae;

import com.obscuria.aquamirae.common.items.armor.AbyssalArmorItem;
import com.obscuria.aquamirae.common.items.armor.TerribleArmorItem;
import com.obscuria.aquamirae.common.items.armor.ThreeBoltArmorItem;
import com.obscuria.aquamirae.common.items.weapon.CoralLanceItem;
import com.obscuria.aquamirae.common.items.weapon.FinCutterItem;
import com.obscuria.aquamirae.common.items.weapon.RemnantsSaberItem;
import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeFeatures;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeMobEffects;
import com.obscuria.aquamirae.registry.AquamiraeParticleTypes;
import com.obscuria.aquamirae.registry.AquamiraePotions;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.obscureapi.api.ClassManager;
import com.obscuria.obscureapi.api.common.classes.ObscureClass;
import com.obscuria.obscureapi.event.ObscureAPIEnchantmentsEvent;
import com.obscuria.obscureapi.registry.ObscureAPIAttributes;
import com.obscuria.obscureapi.util.ItemUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.level.saveddata.maps.MapDecoration.Type;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler.ConfigScreenFactory;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

@Mod("aquamirae")
public class Aquamirae {
   public static final Logger LOGGER = LogManager.getLogger(Aquamirae.class);
   public static final String MODID = "aquamirae";
   private static final String PROTOCOL_VERSION = "1";
   public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(
      new ResourceLocation("aquamirae", "main"), () -> "1", "1"::equals, "1"::equals
   );
   private static int messageID = 0;
   public static final ObscureClass SEA_WOLF = ClassManager.register("aquamirae", "sea_wolf");
   public static final TagKey<Biome> ICE_MAZE = TagKey.m_203882_(Registry.f_122885_, new ResourceLocation("aquamirae", "ice_maze"));
   public static final TagKey<Structure> SHIP = TagKey.m_203882_(Registry.f_235725_, new ResourceLocation("aquamirae", "ship"));
   public static final TagKey<Structure> OUTPOST = TagKey.m_203882_(Registry.f_235725_, new ResourceLocation("aquamirae", "outpost"));
   public static final TagKey<Structure> SHELTER = TagKey.m_203882_(Registry.f_235725_, new ResourceLocation("aquamirae", "shelter"));
   public static final TagKey<Block> EEL_MOVE = BlockTags.create(new ResourceLocation("aquamirae", "eel_move"));
   public static final TagKey<Block> MAZE_MOTHER_DESTROY = BlockTags.create(new ResourceLocation("aquamirae", "maze_mother_destroy"));
   public static final TagKey<Block> SCROLL_DESTROY = BlockTags.create(new ResourceLocation("aquamirae", "scroll_destroy"));
   public static final CreativeModeTab TAB = new CreativeModeTab("aquamirae") {
      @NotNull
      public ItemStack m_6976_() {
         return ((Item)AquamiraeItems.RUNE_OF_THE_STORM.get()).m_7968_();
      }
   };

   public Aquamirae() {
      IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
      AquamiraeConfig.register();
      AquamiraeFeatures.REGISTRY.register(modBus);
      AquamiraeSounds.REGISTRY.register(modBus);
      AquamiraeBlocks.REGISTRY.register(modBus);
      AquamiraeEntities.REGISTRY.register(modBus);
      AquamiraeItems.REGISTRY.register(modBus);
      AquamiraeMobEffects.REGISTRY.register(modBus);
      AquamiraePotions.REGISTRY.register(modBus);
      AquamiraeParticleTypes.REGISTRY.register(modBus);
      modBus.addListener(this::commonSetup);
      modBus.addListener(EventPriority.HIGHEST, this::registerEnchantments);
      MinecraftForge.EVENT_BUS.addListener(this::onEntitySpawn);
      MinecraftForge.EVENT_BUS.addListener(this::onPlayerTick);
      MinecraftForge.EVENT_BUS.addListener(this::onEntityAttacked);
      MinecraftForge.EVENT_BUS.addListener(this::onEntityHurt);
      MinecraftForge.EVENT_BUS.addListener(this::onEntityDeath);
      if (FMLEnvironment.dist == Dist.CLIENT) {
         ModLoadingContext.get().registerExtensionPoint(ConfigScreenFactory.class, AquamiraeClient.getConfig());
      }
   }

   private void registerEnchantments(ObscureAPIEnchantmentsEvent event) {
      event.registerMirror(true);
      event.registerDistance(true);
      event.registerFastSpin(true);
   }

   private void commonSetup(FMLCommonSetupEvent event) {
      ItemUtils.addLore("aquamirae:sea_casserole");
      ItemUtils.addLore("aquamirae:sea_stew");
      ItemUtils.addLore("aquamirae:poseidons_breakfast");
      ItemUtils.addLore("aquamirae:ship_graveyard_echo");
      ItemUtils.addLore("aquamirae:pirate_pouch");
      ItemUtils.addLore("aquamirae:treasure_pouch");
      ItemUtils.addLore("aquamirae:luminescent_bubble");
      ItemUtils.addLore("aquamirae:luminescent_lamp");
      ItemUtils.addLore("aquamirae:shell_horn");
      ItemUtils.addLore("aquamirae:dead_sea_scroll");
      ItemUtils.addLore("aquamirae:frozen_key");
      ItemUtils.addLore("aquamirae:wisteria_niveis");
      ItemUtils.addLore("aquamirae:golden_moth_in_a_jar");
      ItemUtils.addLore("aquamirae:rune_of_the_storm");
      ItemUtils.addLore("aquamirae:oxygelium");
   }

   public static void loadFromConfig(@NotNull LivingEntity entity, Attribute attribute, double amount) {
      AttributeInstance attributeInstance = entity.m_21051_(attribute);
      if (attributeInstance != null) {
         attributeInstance.m_22100_(amount);
      }

      if (attribute == Attributes.f_22276_) {
         entity.m_21153_(entity.m_21233_());
      }
   }

   public static ItemStack getStructureMap(TagKey<Structure> tag, @NotNull ServerLevel server, @NotNull Entity source) {
      BlockPos pos = server.m_215011_(tag, source.m_20183_(), 100, false);
      if (pos != null) {
         Component name = tag == SHIP
            ? Component.m_237115_("filled_map.aquamirae.ship")
            : (
               tag == OUTPOST
                  ? Component.m_237115_("filled_map.aquamirae.outpost")
                  : (tag == SHELTER ? Component.m_237115_("filled_map.aquamirae.shelter") : Component.m_237115_("filled_map.buried_treasure"))
            );
         ItemStack map = MapItem.m_42886_(server, pos.m_123341_(), pos.m_123343_(), (byte)2, true, true);
         MapItem.m_42850_(server, map);
         MapItemSavedData.m_77925_(map, pos, "+", Type.RED_X);
         map.m_41714_(name);
         CompoundTag display = map.m_41784_().m_128469_("display");
         ListTag lore = new ListTag();
         lore.add(StringTag.m_129297_("{\"text\":\"§7x: " + pos.m_123341_() + "\"}"));
         lore.add(StringTag.m_129297_("{\"text\":\"§7z: " + pos.m_123343_() + "\"}"));
         display.m_128365_("Lore", lore);
         map.m_41700_("display", display);
         return map;
      } else {
         return ItemStack.f_41583_;
      }
   }

   public static boolean winterEvent() {
      return Calendar.getInstance().get(2) == 11 || Calendar.getInstance().get(2) == 0;
   }

   private void onEntitySpawn(@NotNull LivingSpawnEvent event) {
      Mob mob = event.getEntity();
      if (mob instanceof Pillager) {
         this.modifyLootTable(mob, "entities/maze_pillager");
      }

      if (mob instanceof Vindicator) {
         this.modifyLootTable(mob, "entities/maze_vindicator");
      }
   }

   private void modifyLootTable(@NotNull Mob mob, String loot) {
      if (AquamiraeUtils.isInIceMaze(mob) && !mob.m_5743_().toString().endsWith("captain")) {
         mob.getPersistentData().m_128359_("DeathLootTable", "aquamirae:" + loot);
      }
   }

   private void onPlayerTick(@NotNull PlayerTickEvent event) {
      if (event.phase == Phase.END && !event.player.f_19853_.f_46443_ && !event.player.m_7500_() && !event.player.m_5833_()) {
         if (AquamiraeUtils.isInIceMaze(event.player)
            && event.player.m_20072_()
            && event.player.m_146888_() <= event.player.m_146891_() * 3
            && ItemUtils.getArmorPieces(event.player, new Class[]{ThreeBoltArmorItem.class}) < 4) {
            event.player.m_146917_(event.player.m_146888_() + 4);
         }
      }
   }

   private void onEntityAttacked(@NotNull LivingHurtEvent event) {
      if (event.getSource().m_7639_() instanceof LivingEntity source && source.m_21205_().m_41720_() instanceof FinCutterItem item) {
         int emptyHP = (int)Math.floor((double)((source.m_21233_() - source.m_21223_()) / 2.0F));
         event.setAmount(
            event.getAmount()
               + event.getAmount()
                  * Math.min((float)item.ABILITY.getVariable(source, 2) * 0.01F, (float)(emptyHP * item.ABILITY.getVariable(source, 1)) * 0.01F)
         );
      }

      if (event.getEntity() instanceof Player player) {
         int TOTAL = ItemUtils.getArmorPieces(player, new Class[]{TerribleArmorItem.class});
         if (TOTAL >= 2) {
            ItemStack piece = this.getArmor(player, TerribleArmorItem.class);
            if (player.m_20069_() && !player.m_36335_().m_41519_(piece.m_41720_()) && piece.m_41720_() instanceof TerribleArmorItem item) {
               player.m_7292_(
                  new MobEffectInstance(
                     (MobEffect)AquamiraeMobEffects.SWIM_SPEED.get(),
                     20 * item.ABILITY_HALFSET.getVariable(player, 2),
                     Math.min(19, item.ABILITY_HALFSET.getVariable(player, 1) / 10 - 1),
                     false,
                     false
                  )
               );
               int cooldown = 20 * item.ABILITY_HALFSET.getCost(player);
               this.cooldown(player, TerribleArmorItem.class, cooldown);
            }

            if (TOTAL >= 4 && event.getSource().m_7639_() instanceof LivingEntity source && piece.m_41720_() instanceof TerribleArmorItem item) {
               source.m_7292_(new MobEffectInstance(MobEffects.f_19614_, 20 * item.ABILITY_FULLSET.getVariable(player, 1), 1, false, false));
            }
         }
      }
   }

   private void onEntityHurt(@NotNull LivingHurtEvent event) {
      if (event.getSource().m_7639_() instanceof LivingEntity source && source.m_20069_() && source.m_21205_().m_41720_() instanceof RemnantsSaberItem item) {
         event.setAmount(event.getAmount() * (1.0F + (float)item.ABILITY.getVariable(source, 1) * 0.01F));
      }

      if (event.getSource().m_7639_() instanceof LivingEntity source
         && source.m_21205_().m_41720_() instanceof CoralLanceItem item
         && AquamiraeUtils.isShipGraveyardEntity(event.getEntity())) {
         event.setAmount(event.getAmount() * (1.0F + (float)item.ABILITY.getVariable(source, 1) * 0.01F));
      }
   }

   private void onEntityDeath(LivingDeathEvent event) {
      if (event != null && event.getEntity() != null) {
         LivingEntity entity = event.getEntity();
         int TOTAL = ItemUtils.getArmorPieces(entity, new Class[]{AbyssalArmorItem.class});
         if (TOTAL >= 4 && !entity.m_21023_((MobEffect)AquamiraeMobEffects.CRYSTALLIZATION.get())) {
            AbyssalArmorItem item = (AbyssalArmorItem)this.getArmor(entity, AbyssalArmorItem.class).m_41720_();
            if (!entity.getPersistentData().m_128471_("crystallization")) {
               event.setCanceled(true);
               entity.m_7292_(
                  new MobEffectInstance((MobEffect)AquamiraeMobEffects.CRYSTALLIZATION.get(), 20 * item.ABILITY_FULLSET_1.getVariable(entity, 1), 0, true, true)
               );
               entity.m_21153_(entity.m_21233_());
               if (entity.m_9236_() instanceof ServerLevel level) {
                  level.m_5594_(
                     null, new BlockPos(entity.m_20185_(), entity.m_20186_() + 1.0, entity.m_20189_()), SoundEvents.f_12513_, SoundSource.PLAYERS, 1.0F, 1.0F
                  );
               }

               ItemStack head = entity.m_6844_(EquipmentSlot.HEAD);
               ItemStack chest = entity.m_6844_(EquipmentSlot.CHEST);
               ItemStack legs = entity.m_6844_(EquipmentSlot.LEGS);
               ItemStack feet = entity.m_6844_(EquipmentSlot.FEET);
               if (head.m_220157_(50, entity.m_217043_(), null)) {
                  head.m_41774_(1);
                  head.m_41721_(0);
               }

               if (chest.m_220157_(50, entity.m_217043_(), null)) {
                  chest.m_41774_(1);
                  chest.m_41721_(0);
               }

               if (legs.m_220157_(50, entity.m_217043_(), null)) {
                  legs.m_41774_(1);
                  legs.m_41721_(0);
               }

               if (feet.m_220157_(50, entity.m_217043_(), null)) {
                  feet.m_41774_(1);
                  feet.m_41721_(0);
               }
            }
         }
      }
   }

   private ItemStack getArmor(@NotNull LivingEntity entity, @NotNull Class<?> armor) {
      return armor.isAssignableFrom(entity.m_6844_(EquipmentSlot.HEAD).m_41720_().getClass())
         ? entity.m_6844_(EquipmentSlot.HEAD)
         : (
            armor.isAssignableFrom(entity.m_6844_(EquipmentSlot.CHEST).m_41720_().getClass())
               ? entity.m_6844_(EquipmentSlot.CHEST)
               : (
                  armor.isAssignableFrom(entity.m_6844_(EquipmentSlot.LEGS).m_41720_().getClass())
                     ? entity.m_6844_(EquipmentSlot.LEGS)
                     : (
                        armor.isAssignableFrom(entity.m_6844_(EquipmentSlot.FEET).m_41720_().getClass())
                           ? entity.m_6844_(EquipmentSlot.FEET)
                           : ItemStack.f_41583_
                     )
               )
         );
   }

   private void cooldown(@NotNull Player player, @NotNull Class<?> armor, int cooldown) {
      if (armor.isAssignableFrom(player.m_6844_(EquipmentSlot.HEAD).m_41720_().getClass())) {
         player.m_36335_().m_41524_(player.m_6844_(EquipmentSlot.HEAD).m_41720_(), cooldown);
      }

      if (armor.isAssignableFrom(player.m_6844_(EquipmentSlot.CHEST).m_41720_().getClass())) {
         player.m_36335_().m_41524_(player.m_6844_(EquipmentSlot.CHEST).m_41720_(), cooldown);
      }

      if (armor.isAssignableFrom(player.m_6844_(EquipmentSlot.LEGS).m_41720_().getClass())) {
         player.m_36335_().m_41524_(player.m_6844_(EquipmentSlot.LEGS).m_41720_(), cooldown);
      }

      if (armor.isAssignableFrom(player.m_6844_(EquipmentSlot.FEET).m_41720_().getClass())) {
         player.m_36335_().m_41524_(player.m_6844_(EquipmentSlot.FEET).m_41720_(), cooldown);
      }
   }

   public static <T> void addNetworkMessage(
      Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<Context>> messageConsumer
   ) {
      PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
      messageID++;
   }

   public static class SetBuilder {
      @NotNull
      public static List<ItemStack> common() {
         int c1 = getColor(10, 220, 160);
         int c2 = getColor(10, 190, 220);
         int c3 = getColor(10, 130, 220);
         List<ItemStack> list = new ArrayList<>();
         add(list, Items.f_42407_, EquipmentSlot.HEAD, 1, 1, c1, "dead_sea_hat", Attributes.f_22281_);
         add(list, Items.f_42407_, EquipmentSlot.HEAD, 1, 1, c2, "twilight_grotto_hat", (Attribute)ObscureAPIAttributes.PENETRATION.get());
         add(list, Items.f_42407_, EquipmentSlot.HEAD, 1, 1, c3, "sea_tramps_hat", (Attribute)ObscureAPIAttributes.CRITICAL_HIT.get());
         add(list, Items.f_42408_, EquipmentSlot.CHEST, 1, 3, c1, "dead_sea_doublet", Attributes.f_22281_);
         add(list, Items.f_42408_, EquipmentSlot.CHEST, 1, 3, c2, "twilight_grotto_doublet", (Attribute)ObscureAPIAttributes.PENETRATION.get());
         add(list, Items.f_42408_, EquipmentSlot.CHEST, 1, 3, c3, "sea_tramps_doublet", (Attribute)ObscureAPIAttributes.CRITICAL_HIT.get());
         add(list, Items.f_42462_, EquipmentSlot.LEGS, 1, 2, c1, "dead_sea_pants", Attributes.f_22281_);
         add(list, Items.f_42462_, EquipmentSlot.LEGS, 1, 2, c2, "twilight_grotto_pants", (Attribute)ObscureAPIAttributes.PENETRATION.get());
         add(list, Items.f_42462_, EquipmentSlot.LEGS, 1, 2, c3, "sea_tramps_pants", (Attribute)ObscureAPIAttributes.CRITICAL_HIT.get());
         add(list, Items.f_42463_, EquipmentSlot.FEET, 1, 1, c1, "dead_sea_boots", Attributes.f_22281_);
         add(list, Items.f_42463_, EquipmentSlot.FEET, 1, 1, c2, "twilight_grotto_boots", (Attribute)ObscureAPIAttributes.PENETRATION.get());
         add(list, Items.f_42463_, EquipmentSlot.FEET, 1, 1, c3, "sea_tramps_boots", (Attribute)ObscureAPIAttributes.CRITICAL_HIT.get());
         return list;
      }

      @NotNull
      public static List<ItemStack> rare() {
         List<ItemStack> list = new ArrayList<>();
         add(list, Items.f_42468_, EquipmentSlot.HEAD, 2, 2, 0, "dead_sea_helmet", Attributes.f_22281_);
         add(list, Items.f_42468_, EquipmentSlot.HEAD, 2, 2, 0, "twilight_grotto_helmet", (Attribute)ObscureAPIAttributes.PENETRATION.get());
         add(list, Items.f_42468_, EquipmentSlot.HEAD, 2, 2, 0, "sea_tramps_helmet", (Attribute)ObscureAPIAttributes.CRITICAL_HIT.get());
         add(list, Items.f_42469_, EquipmentSlot.CHEST, 2, 6, 0, "dead_sea_chestplate", Attributes.f_22281_);
         add(list, Items.f_42469_, EquipmentSlot.CHEST, 2, 6, 0, "twilight_grotto_chestplate", (Attribute)ObscureAPIAttributes.PENETRATION.get());
         add(list, Items.f_42469_, EquipmentSlot.CHEST, 2, 6, 0, "sea_tramps_chestplate", (Attribute)ObscureAPIAttributes.CRITICAL_HIT.get());
         add(list, Items.f_42470_, EquipmentSlot.LEGS, 2, 5, 0, "dead_sea_leggings", Attributes.f_22281_);
         add(list, Items.f_42470_, EquipmentSlot.LEGS, 2, 5, 0, "twilight_grotto_leggings", (Attribute)ObscureAPIAttributes.PENETRATION.get());
         add(list, Items.f_42470_, EquipmentSlot.LEGS, 2, 5, 0, "sea_tramps_leggings", (Attribute)ObscureAPIAttributes.CRITICAL_HIT.get());
         add(list, Items.f_42471_, EquipmentSlot.FEET, 2, 2, 0, "dead_sea_boots", Attributes.f_22281_);
         add(list, Items.f_42471_, EquipmentSlot.FEET, 2, 2, 0, "twilight_grotto_boots", (Attribute)ObscureAPIAttributes.PENETRATION.get());
         add(list, Items.f_42471_, EquipmentSlot.FEET, 2, 2, 0, "sea_tramps_boots", (Attribute)ObscureAPIAttributes.CRITICAL_HIT.get());
         return list;
      }

      public static void add(List<ItemStack> list, Item item, EquipmentSlot slot, int mod, int armor, int color, String name, Attribute attribute) {
         for (int i = 1; i <= 5; i++) {
            ItemStack stack = new ItemStack(item);
            stack.m_41643_(Attributes.f_22284_, new AttributeModifier("base_armor", (double)armor, Operation.ADDITION), slot);
            stack.m_41643_(attribute, new AttributeModifier("base_bonus", (double)(mod * i) * 0.01, Operation.MULTIPLY_TOTAL), slot);
            stack.m_41714_(Component.m_237115_("set.aquamirae." + name));
            if (i == 5) {
               stack.m_41663_(Enchantments.f_44986_, mod);
            }

            if (color > 0) {
               stack.m_41698_("display").m_128405_("color", color);
            }

            list.add(stack);
         }
      }

      public static int getColor(int r, int g, int b) {
         return r * 65536 + g * 256 + b;
      }
   }
}
