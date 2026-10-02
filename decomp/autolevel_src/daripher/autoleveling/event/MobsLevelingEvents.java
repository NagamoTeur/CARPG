package daripher.autoleveling.event;

import com.mojang.math.Matrix4f;
import daripher.autoleveling.config.Config;
import daripher.autoleveling.data.DimensionsLevelingSettingsReloader;
import daripher.autoleveling.data.EntitiesLevelingSettingsReloader;
import daripher.autoleveling.init.AutoLevelingAttributes;
import daripher.autoleveling.mixin.LivingEntityAccessor;
import daripher.autoleveling.network.NetworkDispatcher;
import daripher.autoleveling.network.message.SyncLevelingData;
import daripher.autoleveling.saveddata.GlobalLevelingData;
import daripher.autoleveling.saveddata.WorldLevelingData;
import daripher.autoleveling.settings.DimensionLevelingSettings;
import daripher.autoleveling.settings.LevelingSettings;
import java.util.List;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.RenderNameTagEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.StartTracking;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.network.PacketDistributor;

@EventBusSubscriber(
   modid = "autoleveling"
)
public class MobsLevelingEvents {
   private static final String LEVEL_TAG = "LEVEL";

   @SubscribeEvent
   public static void applyLevelBonuses(EntityJoinLevelEvent event) {
      if (shouldSetLevel(event.getEntity())) {
         LivingEntity entity = (LivingEntity)event.getEntity();
         if (hasLevel(entity)) {
            applyAttributeBonuses(entity);
         } else {
            BlockPos spawnPos = getSpawnPosition(entity);
            double distanceToSpawn = Math.sqrt(spawnPos.m_123331_(entity.m_20183_()));
            int level = createLevelForEntity(entity, distanceToSpawn);
            setLevel(entity, level);
            applyAttributeBonuses(entity);
            addEquipment(entity);
         }
      }
   }

   private static BlockPos getSpawnPosition(LivingEntity entity) {
      ResourceKey<Level> dimension = entity.m_9236_().m_46472_();
      DimensionLevelingSettings levelingSettings = DimensionsLevelingSettingsReloader.getSettingsForDimension(dimension);
      return levelingSettings.spawnPosOverride().orElse(entity.m_9236_().m_220360_());
   }

   @SubscribeEvent
   public static void adjustExperienceDrop(LivingExperienceDropEvent event) {
      if (hasLevel(event.getEntity())) {
         int level = getLevel(event.getEntity()) + 1;
         int originalExp = event.getDroppedExperience();
         double expBonus = (Double)Config.COMMON.expBonus.get() * (double)level;
         event.setDroppedExperience((int)((double)originalExp + (double)originalExp * expBonus));
      }
   }

   @SubscribeEvent
   public static void dropAdditionalLoot(LivingDropsEvent event) {
      if (hasLevel(event.getEntity())) {
         ResourceLocation leveledMobsLootTableLocation = new ResourceLocation("autoleveling", "gameplay/leveled_mobs");
         MinecraftServer server = event.getEntity().f_19853_.m_7654_();
         if (server != null) {
            LootTable leveledMobsLootTable = server.m_129898_().m_79217_(leveledMobsLootTableLocation);
            LootContext lootContext = createLootContext(event.getEntity(), event.getSource());
            leveledMobsLootTable.m_230922_(lootContext).forEach(event.getEntity()::m_19983_);
         }
      }
   }

   @SubscribeEvent
   public static void reloadSettings(AddReloadListenerEvent event) {
      event.addListener(new DimensionsLevelingSettingsReloader());
      event.addListener(new EntitiesLevelingSettingsReloader());
   }

   @SubscribeEvent
   public static void syncEntityLevel(StartTracking event) {
      if (hasLevel(event.getTarget())) {
         LivingEntity entity = (LivingEntity)event.getTarget();
         NetworkDispatcher.networkChannel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer)event.getEntity()), new SyncLevelingData(entity));
      }
   }

   @OnlyIn(Dist.CLIENT)
   @SubscribeEvent
   public static void renderEntityLevel(RenderNameTagEvent event) {
      if (!ModList.get().isLoaded("neat")) {
         if (event.getEntity() instanceof LivingEntity entity) {
            if (shouldShowName(entity)) {
               Minecraft minecraft = Minecraft.m_91087_();
               event.setResult(Result.ALLOW);
               double distance = minecraft.m_91290_().m_114471_(entity);
               if (ForgeHooksClient.isNameplateInRenderDistance(entity, distance)) {
                  int level = getLevel(entity) + 1;
                  Component entityName = event.getContent();
                  MutableComponent levelString = Component.m_237110_("autoleveling.level", new Object[]{level}).m_130940_(ChatFormatting.GREEN);
                  float y = entity.m_20206_() + 0.5F;
                  int yShift = "deadmau5".equals(entityName.getString()) ? -10 : 0;
                  event.getPoseStack().m_85836_();
                  event.getPoseStack().m_85837_(0.0, (double)y, 0.0);
                  event.getPoseStack().m_85845_(minecraft.m_91290_().m_114470_());
                  event.getPoseStack().m_85841_(-0.025F, -0.025F, 0.025F);
                  Matrix4f matrix4f = event.getPoseStack().m_85850_().m_85861_();
                  float backgroundOpacity = minecraft.f_91066_.m_92141_(0.25F);
                  int alpha = (int)(backgroundOpacity * 255.0F) << 24;
                  Font font = minecraft.f_91062_;
                  int x = -font.m_92852_(entityName) / 2 - 5 - font.m_92852_(levelString);
                  font.m_92841_(
                     levelString,
                     (float)x,
                     (float)yShift,
                     553648127,
                     false,
                     matrix4f,
                     event.getMultiBufferSource(),
                     !entity.m_20163_(),
                     alpha,
                     event.getPackedLight()
                  );
                  if (!entity.m_20163_()) {
                     font.m_92841_(levelString, (float)x, (float)yShift, -1, false, matrix4f, event.getMultiBufferSource(), false, 0, event.getPackedLight());
                  }

                  event.getPoseStack().m_85849_();
               }
            }
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   private static boolean shouldShowName(LivingEntity entity) {
      if (!hasLevel(entity)) {
         return false;
      } else if (!shouldShowLevel(entity)) {
         return false;
      } else {
         Minecraft minecraft = Minecraft.m_91087_();
         Boolean alwaysShowLevel = (Boolean)Config.COMMON.alwaysShowLevel.get();
         Boolean showLevelWhenLookingAt = (Boolean)Config.COMMON.showLevelWhenLookingAt.get();
         if (alwaysShowLevel || showLevelWhenLookingAt && minecraft.f_91076_ == entity) {
            LocalPlayer clientPlayer = minecraft.f_91074_;
            return clientPlayer == null
               ? false
               : Minecraft.m_91404_()
                  && entity != minecraft.m_91288_()
                  && !entity.m_20177_(clientPlayer)
                  && !entity.m_20160_()
                  && clientPlayer.m_142582_(entity);
         } else {
            return false;
         }
      }
   }

   private static boolean shouldSetLevel(Entity entity) {
      return entity.f_19853_.f_46443_ ? false : canHaveLevel(entity);
   }

   private static int createLevelForEntity(LivingEntity entity, double distance) {
      MinecraftServer server = entity.m_20194_();
      if (server == null) {
         return 0;
      } else {
         LevelingSettings levelingSettings = EntitiesLevelingSettingsReloader.getSettingsForEntity(entity.m_6095_());
         if (levelingSettings == null) {
            ResourceKey<Level> dimension = entity.f_19853_.m_46472_();
            levelingSettings = DimensionsLevelingSettingsReloader.getSettingsForDimension(dimension);
         }

         int monsterLevel = levelingSettings.startingLevel() - 1;
         int maxLevel = levelingSettings.maxLevel();
         monsterLevel += (int)((double)levelingSettings.levelsPerDistance() * distance);
         monsterLevel += (int)Math.pow(distance, distance * (double)levelingSettings.levelPowerPerDistance()) - 1;
         if (entity.m_20186_() < 64.0) {
            double deepness = 64.0 - entity.m_20186_();
            monsterLevel += (int)((double)levelingSettings.levelsPerDeepness() * deepness);
            monsterLevel += (int)Math.pow(deepness, deepness * (double)levelingSettings.levelPowerPerDeepness()) - 1;
         }

         int levelBonus = levelingSettings.randomLevelBonus() + 1;
         if (levelBonus > 0) {
            monsterLevel += entity.m_217043_().m_188503_(levelBonus);
         }

         monsterLevel = Math.abs(monsterLevel);
         monsterLevel += WorldLevelingData.get((ServerLevel)entity.f_19853_).getLevelBonus();
         if (maxLevel > 0) {
            monsterLevel = Math.min(monsterLevel, maxLevel - 1);
         }

         GlobalLevelingData globalLevelingData = GlobalLevelingData.get(server);
         return monsterLevel + globalLevelingData.getLevelBonus();
      }
   }

   @SubscribeEvent
   public static void applyDamageBonus(LivingHurtEvent event) {
      DamageSource damageSource = event.getSource();
      if (damageSource.m_7639_() instanceof LivingEntity attacker) {
         if (damageSource.m_19360_()) {
            Attribute projectileDamage = (Attribute)AutoLevelingAttributes.PROJECTILE_DAMAGE_BONUS.get();
            if (attacker.m_21051_(projectileDamage) == null) {
               return;
            }

            float damageBonus = (float)attacker.m_21133_(projectileDamage);
            event.setAmount(event.getAmount() * damageBonus);
         }

         if (damageSource.m_19372_()) {
            Attribute explosionDamage = (Attribute)AutoLevelingAttributes.EXPLOSION_DAMAGE_BONUS.get();
            if (attacker.m_21051_(explosionDamage) == null) {
               return;
            }

            float damageBonus = (float)attacker.m_21133_(explosionDamage);
            event.setAmount(event.getAmount() * damageBonus);
         }
      }
   }

   public static void applyAttributeBonuses(LivingEntity entity) {
      int level = getLevel(entity);
      Config.getAttributeBonuses().forEach((attribute, bonus) -> applyAttributeBonusIfPossible(entity, attribute, (double)(bonus * (float)level)));
   }

   private static void applyAttributeBonusIfPossible(LivingEntity entity, Attribute attribute, double bonus) {
      AttributeInstance attributeInstance = entity.m_21051_(attribute);
      if (attributeInstance != null) {
         UUID modifierId = UUID.fromString("6a102cb4-d735-4cb7-8ab2-3d383219a44e");
         AttributeModifier modifier = attributeInstance.m_22111_(modifierId);
         if (modifier == null || modifier.m_22218_() != bonus) {
            if (modifier != null) {
               attributeInstance.m_22130_(modifier);
            }

            modifier = new AttributeModifier(modifierId, "Auto Leveling Bonus", bonus, Operation.MULTIPLY_TOTAL);
            attributeInstance.m_22125_(modifier);
            if (attribute == Attributes.f_22276_) {
               entity.m_5634_(entity.m_21233_());
            }
         }
      }
   }

   public static void addEquipment(LivingEntity entity) {
      MinecraftServer server = entity.m_9236_().m_7654_();
      if (server != null) {
         for (EquipmentSlot slot : EquipmentSlot.values()) {
            LootTable equipmentTable = getEquipmentLootTableForSlot(server, entity, slot);
            if (equipmentTable != LootTable.f_79105_) {
               LootContext lootContext = createEquipmentLootContext(entity);
               equipmentTable.m_230922_(lootContext).forEach(itemStack -> entity.m_8061_(slot, itemStack));
            }
         }
      }
   }

   private static LootTable getEquipmentLootTableForSlot(MinecraftServer server, LivingEntity entity, EquipmentSlot equipmentSlot) {
      ResourceLocation entityId = EntityType.m_20613_(entity.m_6095_());
      ResourceLocation lootTableId = new ResourceLocation(entityId.m_135827_(), "equipment/" + entityId.m_135815_() + "_" + equipmentSlot.m_20751_());
      return server.m_129898_().m_79217_(lootTableId);
   }

   private static LootContext createLootContext(LivingEntity entity, DamageSource damageSource) {
      LivingEntityAccessor accessor = (LivingEntityAccessor)entity;
      int lastHurtByPlayerTime = accessor.getLastHurtByPlayerTime();
      Builder builder = accessor.invokeCreateLootContext(lastHurtByPlayerTime > 0, damageSource);
      return builder.m_78975_(LootContextParamSets.f_81415_);
   }

   private static LootContext createEquipmentLootContext(LivingEntity entity) {
      return new Builder((ServerLevel)entity.f_19853_)
         .m_230911_(entity.m_217043_())
         .m_78972_(LootContextParams.f_81455_, entity)
         .m_78972_(LootContextParams.f_81460_, entity.m_20182_())
         .m_78975_(LootContextParamSets.f_81413_);
   }

   private static boolean canHaveLevel(Entity entity) {
      if (!(entity instanceof LivingEntity)) {
         return false;
      } else if (entity.m_6095_() == EntityType.f_20532_) {
         return false;
      } else {
         ResourceLocation entityId = EntityType.m_20613_(entity.m_6095_());
         String entityNamespace = entityId.m_135827_();
         List<String> blacklistedMobs = (List<String>)Config.COMMON.blacklistedMobs.get();
         if (blacklistedMobs.contains(entityNamespace + ":*")) {
            return false;
         } else {
            List<String> whitelistedMobs = (List<String>)Config.COMMON.whitelistedMobs.get();
            if (whitelistedMobs.contains(entityNamespace + ":*")) {
               return true;
            } else if (blacklistedMobs.contains(entityId.toString())) {
               return false;
            } else {
               return !whitelistedMobs.isEmpty() ? whitelistedMobs.contains(entityId.toString()) : true;
            }
         }
      }
   }

   public static boolean shouldShowLevel(Entity entity) {
      ResourceLocation entityId = EntityType.m_20613_(entity.m_6095_());
      List<String> blacklist = (List<String>)Config.COMMON.blacklistedShownLevels.get();
      if (blacklist.contains(entityId.toString())) {
         return false;
      } else {
         String namespace = entityId.m_135827_();
         return !blacklist.contains(namespace + ":*");
      }
   }

   public static boolean hasLevel(Entity entity) {
      return entity.getPersistentData().m_128441_("LEVEL");
   }

   public static int getLevel(LivingEntity entity) {
      return entity.getPersistentData().m_128451_("LEVEL");
   }

   public static void setLevel(LivingEntity entity, int level) {
      entity.getPersistentData().m_128405_("LEVEL", level);
   }
}
