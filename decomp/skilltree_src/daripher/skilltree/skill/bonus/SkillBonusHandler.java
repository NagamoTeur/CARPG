package daripher.skilltree.skill.bonus;

import com.mojang.datafixers.util.Either;
import daripher.itemproduction.event.ItemProducedEvent;
import daripher.skilltree.capability.skill.PlayerSkillsProvider;
import daripher.skilltree.effect.SkillBonusEffect;
import daripher.skilltree.entity.EquippedEntity;
import daripher.skilltree.entity.player.PlayerHelper;
import daripher.skilltree.item.ItemBonusProvider;
import daripher.skilltree.item.ItemHelper;
import daripher.skilltree.mixin.AbstractArrowAccessor;
import daripher.skilltree.potion.PotionHelper;
import daripher.skilltree.skill.PassiveSkill;
import daripher.skilltree.skill.bonus.event.AttackEventListener;
import daripher.skilltree.skill.bonus.event.BlockEventListener;
import daripher.skilltree.skill.bonus.event.DamageTakenEventListener;
import daripher.skilltree.skill.bonus.event.ItemUsedEventListener;
import daripher.skilltree.skill.bonus.event.KillEventListener;
import daripher.skilltree.skill.bonus.item.FoodHealingBonus;
import daripher.skilltree.skill.bonus.item.ItemBonus;
import daripher.skilltree.skill.bonus.item.ItemSkillBonus;
import daripher.skilltree.skill.bonus.player.ArrowRetrievalBonus;
import daripher.skilltree.skill.bonus.player.AttributeBonus;
import daripher.skilltree.skill.bonus.player.BlockBreakSpeedBonus;
import daripher.skilltree.skill.bonus.player.CantUseItemBonus;
import daripher.skilltree.skill.bonus.player.CraftedItemBonus;
import daripher.skilltree.skill.bonus.player.CritChanceBonus;
import daripher.skilltree.skill.bonus.player.CritDamageBonus;
import daripher.skilltree.skill.bonus.player.DamageBonus;
import daripher.skilltree.skill.bonus.player.EnchantmentAmplificationBonus;
import daripher.skilltree.skill.bonus.player.EnchantmentRequirementBonus;
import daripher.skilltree.skill.bonus.player.FreeEnchantmentBonus;
import daripher.skilltree.skill.bonus.player.GainedExperienceBonus;
import daripher.skilltree.skill.bonus.player.HealthReservationBonus;
import daripher.skilltree.skill.bonus.player.IncomingHealingBonus;
import daripher.skilltree.skill.bonus.player.JumpHeightBonus;
import daripher.skilltree.skill.bonus.player.LootDuplicationBonus;
import daripher.skilltree.skill.bonus.player.RepairEfficiencyBonus;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import javax.annotation.Nonnull;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderTooltipEvent.GatherComponents;
import net.minecraftforge.common.Tags.Blocks;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Finish;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.level.BlockEvent.BreakEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.apache.commons.lang3.StringUtils;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.event.CurioAttributeModifierEvent;
import top.theillusivec4.curios.api.event.CurioEquipEvent;

@EventBusSubscriber(
   modid = "skilltree"
)
public class SkillBonusHandler {
   @SubscribeEvent
   public static void applyBreakSpeedMultiplier(BreakSpeed event) {
      Player player = event.getEntity();
      float multiplier = 1.0F;

      for (BlockBreakSpeedBonus bonus : getSkillBonuses(player, BlockBreakSpeedBonus.class)) {
         if (bonus.getPlayerCondition().met(player)) {
            multiplier += bonus.getMultiplier();
         }
      }

      event.setNewSpeed(event.getNewSpeed() * multiplier);
   }

   @SubscribeEvent
   public static void applyFallReductionMultiplier(LivingFallEvent event) {
      if (event.getEntity() instanceof Player player) {
         float multiplier = getJumpHeightMultiplier(player);
         if (!(multiplier <= 1.0F)) {
            event.setDistance(event.getDistance() / multiplier);
         }
      }
   }

   @SubscribeEvent
   public static void applyRepairEfficiency(AnvilUpdateEvent event) {
      Player player = event.getPlayer();
      ItemStack stack = event.getLeft();
      float efficiency = getRepairEfficiency(player, stack);
      if (efficiency != 1.0F) {
         if (stack.m_41763_() && stack.m_41768_()) {
            ItemStack material = event.getRight();
            if (stack.m_41720_().m_6832_(stack, material)) {
               ItemStack result = stack.m_41777_();
               int durabilityPerMaterial = (int)((float)(result.m_41776_() * 12) * (1.0F + efficiency) / 100.0F);
               int durabilityRestored = durabilityPerMaterial;
               int materialsUsed = 0;

               int cost;
               for (cost = 0; durabilityRestored > 0 && materialsUsed < material.m_41613_(); materialsUsed++) {
                  result.m_41721_(result.m_41773_() - durabilityRestored);
                  cost++;
                  durabilityRestored = Math.min(result.m_41773_(), durabilityPerMaterial);
               }

               if (event.getName() != null && !StringUtils.isBlank(event.getName())) {
                  if (!event.getName().equals(stack.m_41786_().getString())) {
                     cost++;
                     result.m_41714_(Component.m_237113_(event.getName()));
                  }
               } else if (stack.m_41788_()) {
                  cost++;
                  result.m_41787_();
               }

               event.setMaterialCost(materialsUsed);
               event.setCost(cost);
               event.setOutput(result);
            }
         }
      }
   }

   private static float getRepairEfficiency(Player player, ItemStack stack) {
      float efficiency = 1.0F;

      for (RepairEfficiencyBonus bonus : getSkillBonuses(player, RepairEfficiencyBonus.class)) {
         if (bonus.getItemCondition().met(stack)) {
            efficiency += bonus.getMultiplier();
         }
      }

      return efficiency;
   }

   @SubscribeEvent
   public static void tickSkillBonuses(PlayerTickEvent event) {
      if (!event.player.m_21224_()) {
         if (event.player instanceof ServerPlayer player) {
            if (event.phase != Phase.END) {
               getSkillBonuses(player, SkillBonus.Ticking.class).forEach(bonus -> bonus.tick(player));
            }
         }
      }
   }

   @SubscribeEvent(
      priority = EventPriority.HIGH
   )
   public static void applyFlatDamageBonus(LivingHurtEvent event) {
      if (event.getSource().m_7639_() instanceof Player player) {
         player.getPersistentData().m_128405_("LastAttackTarget", event.getEntity().m_19879_());
         float var3 = getDamageBonus(player, event.getSource(), event.getEntity(), Operation.ADDITION);
         event.setAmount(event.getAmount() + var3);
      }
   }

   @SubscribeEvent
   public static void applyBaseDamageMultipliers(LivingHurtEvent event) {
      if (event.getSource().m_7639_() instanceof Player player) {
         float var3 = getDamageBonus(player, event.getSource(), event.getEntity(), Operation.MULTIPLY_BASE);
         event.setAmount(event.getAmount() * (1.0F + var3));
      }
   }

   @SubscribeEvent(
      priority = EventPriority.LOW
   )
   public static void applyTotalDamageMultipliers(LivingHurtEvent event) {
      if (event.getSource().m_7639_() instanceof Player player) {
         float var3 = getDamageBonus(player, event.getSource(), event.getEntity(), Operation.MULTIPLY_TOTAL);
         event.setAmount(event.getAmount() * (1.0F + var3));
      }
   }

   private static float getDamageBonus(Player player, DamageSource damageSource, LivingEntity target, Operation operation) {
      float amount = 0.0F;

      for (DamageBonus bonus : getSkillBonuses(player, DamageBonus.class)) {
         amount += bonus.getDamageBonus(operation, damageSource, player, target);
      }

      return amount;
   }

   @SubscribeEvent
   public static void applyCritBonuses(CriticalHitEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         if (event.getTarget() instanceof LivingEntity target) {
            DamageSource var7 = DamageSource.m_19344_(player);
            float critChance = getCritChance(player, var7, event.getEntity());
            if (!(player.m_217043_().m_188501_() >= critChance)) {
               float critMultiplier = event.getDamageModifier();
               critMultiplier += getCritDamageMultiplier(player, var7, target);
               if (!event.isVanillaCritical()) {
                  critMultiplier += 0.5F;
                  event.setResult(Result.ALLOW);
               }

               event.setDamageModifier(critMultiplier);
            }
         }
      }
   }

   @SubscribeEvent(
      priority = EventPriority.LOW
   )
   public static void applyCritBonuses(LivingHurtEvent event) {
      if (event.getSource().getClass() != EntityDamageSource.class || !event.getSource().f_19326_.equals("player")) {
         if (event.getSource().m_7639_() instanceof ServerPlayer player) {
            float var4 = getCritChance(player, event.getSource(), event.getEntity());
            if (!(player.m_217043_().m_188501_() >= var4)) {
               float critMultiplier = 1.5F;
               critMultiplier += getCritDamageMultiplier(player, event.getSource(), event.getEntity());
               event.setAmount(event.getAmount() * critMultiplier);
            }
         }
      }
   }

   private static float getCritDamageMultiplier(ServerPlayer player, DamageSource source, LivingEntity target) {
      float multiplier = 0.0F;

      for (CritDamageBonus bonus : getSkillBonuses(player, CritDamageBonus.class)) {
         multiplier += bonus.getDamageBonus(source, player, target);
      }

      return multiplier;
   }

   private static float getCritChance(ServerPlayer player, DamageSource source, LivingEntity target) {
      float critChance = 0.0F;

      for (CritChanceBonus bonus : getSkillBonuses(player, CritChanceBonus.class)) {
         critChance += bonus.getChanceBonus(source, player, target);
      }

      return critChance;
   }

   @SubscribeEvent
   public static void addAdditionalSocketTooltip(ItemTooltipEvent event) {
      ItemStack stack = event.getItemStack();
      int sockets = ItemHelper.getAdditionalSockets(stack);
      if (sockets > 0) {
         String key = "gem.additional_socket_" + sockets;
         Component socketTooltip = Component.m_237115_(key).m_130940_(ChatFormatting.YELLOW);
         event.getToolTip().add(1, socketTooltip);
      }
   }

   @SubscribeEvent
   public static void addCraftedItemSkillBonusTooltips(ItemTooltipEvent event) {
      List<Component> components = event.getToolTip();

      for (ItemBonus<?> itemBonus : ItemHelper.getItemBonusesExcludingGems(event.getItemStack())) {
         if (itemBonus instanceof ItemSkillBonus) {
            ItemSkillBonus skillBonus = (ItemSkillBonus)itemBonus;
            SkillBonus<?> bonus = skillBonus.getBonus();
            if (!(bonus instanceof AttributeBonus)) {
               MutableComponent tooltip = bonus.getTooltip();
               components.add(tooltip);
            }
         }
      }
   }

   @SubscribeEvent
   public static void setCraftedItemBonus(ItemProducedEvent event) {
      ItemStack stack = event.getStack();
      if (!PotionHelper.isMixture(stack)) {
         Player player = event.getPlayer();
         ItemHelper.removeItemBonuses(stack);
         getSkillBonuses(player, CraftedItemBonus.class).forEach(bonus -> bonus.itemCrafted(stack));
         ItemHelper.getItemBonuses(stack, ItemBonus.class).forEach(bonus -> bonus.itemCrafted(stack));
         ItemHelper.refreshDurabilityBonuses(stack);
      }
   }

   @SubscribeEvent
   public static void applyCraftedItemAttributeBonuses(ItemAttributeModifierEvent event) {
      ItemStack stack = event.getItemStack();
      if (event.getSlotType() == Player.m_147233_(stack)) {
         addAttributeModifiers(event::addModifier, stack);
      }
   }

   @SubscribeEvent
   public static void applyCraftedCurioAttributeBonuses(CurioAttributeModifierEvent event) {
      ItemStack stack = event.getItemStack();
      if (CuriosApi.getCuriosHelper().isStackValid(event.getSlotContext(), stack)) {
         addAttributeModifiers(event::addModifier, stack);
      }
   }

   @SubscribeEvent
   public static void applyFoodHealing(Finish event) {
      ItemStack stack = event.getItem();
      if (stack.getFoodProperties(event.getEntity()) != null) {
         float healing = 0.0F;

         for (FoodHealingBonus bonus : ItemHelper.getItemBonuses(stack, FoodHealingBonus.class)) {
            healing += bonus.getAmount();
         }

         event.getEntity().m_5634_(healing);
      }
   }

   @SubscribeEvent
   public static void applyIncomingHealingBonus(LivingHealEvent event) {
      if (event.getEntity() instanceof Player player) {
         float var5 = 1.0F;

         for (IncomingHealingBonus bonus : getSkillBonuses(player, IncomingHealingBonus.class)) {
            var5 += bonus.getHealingMultiplier(player);
         }

         event.setAmount(event.getAmount() * var5);
      }
   }

   @SubscribeEvent(
      priority = EventPriority.LOWEST
   )
   public static void applyLootDuplicationChanceBonus(LivingDropsEvent event) {
      if (!(event.getEntity() instanceof Player)) {
         if (event.getSource().m_7639_() instanceof Player player) {
            float var3;
            for (var3 = getLootMultiplier(player, LootDuplicationBonus.LootType.MOBS); var3 > 1.0F; var3--) {
               event.getDrops().addAll(getDrops(event));
            }

            if (player.m_217043_().m_188501_() < var3) {
               event.getDrops().addAll(getDrops(event));
            }
         }
      }
   }

   @SubscribeEvent
   public static void applyExperienceFromMobsBonus(LivingExperienceDropEvent event) {
      Player player = event.getAttackingPlayer();
      if (player != null) {
         float multiplier = 1.0F;
         multiplier += getExperienceMultiplier(player, GainedExperienceBonus.ExperienceSource.MOBS);
         event.setDroppedExperience((int)((float)event.getDroppedExperience() * multiplier));
      }
   }

   @SubscribeEvent
   public static void applyExperienceFromOreBonus(BreakEvent event) {
      if (event.getState().m_204336_(Blocks.ORES)) {
         float multiplier = 1.0F;
         multiplier += getExperienceMultiplier(event.getPlayer(), GainedExperienceBonus.ExperienceSource.ORE);
         event.setExpToDrop((int)((float)event.getExpToDrop() * multiplier));
      }
   }

   @SubscribeEvent
   public static void applyFishingExperienceBonus(ItemFishedEvent event) {
      Player player = event.getEntity();
      float multiplier = getExperienceMultiplier(player, GainedExperienceBonus.ExperienceSource.FISHING);
      if (multiplier != 0.0F) {
         int exp = (int)((float)(player.m_217043_().m_188503_(6) + 1) * multiplier);
         if (exp != 0) {
            ExperienceOrb expOrb = new ExperienceOrb(player.f_19853_, player.m_20185_(), player.m_20186_() + 0.5, player.m_20189_() + 0.5, exp);
            player.f_19853_.m_7967_(expOrb);
         }
      }
   }

   private static float getExperienceMultiplier(Player player, GainedExperienceBonus.ExperienceSource source) {
      float multiplier = 0.0F;

      for (GainedExperienceBonus bonus : getSkillBonuses(player, GainedExperienceBonus.class)) {
         if (bonus.getSource() == source) {
            multiplier += bonus.getMultiplier();
         }
      }

      return multiplier;
   }

   @SubscribeEvent
   public static void applyEventListenerEffect(LivingHurtEvent event) {
      Entity sourceEntity = event.getSource().m_7639_();
      if (sourceEntity instanceof Player player) {
         for (EventListenerBonus<?> bonus : getSkillBonuses(player, EventListenerBonus.class)) {
            if (bonus.getEventListener() instanceof AttackEventListener listener) {
               SkillBonus<? extends EventListenerBonus<?>> copy = bonus.copy();
               listener.onEvent(player, event.getEntity(), event.getSource(), (EventListenerBonus<?>)copy);
            }
         }
      }

      if (event.getEntity() instanceof Player player) {
         for (EventListenerBonus<?> bonusx : getSkillBonuses(player, EventListenerBonus.class)) {
            if (bonusx.getEventListener() instanceof DamageTakenEventListener listener) {
               SkillBonus<? extends EventListenerBonus<?>> copy = bonusx.copy();
               LivingEntity attacker = sourceEntity instanceof LivingEntity ? (LivingEntity)sourceEntity : null;
               listener.onEvent(player, attacker, event.getSource(), (EventListenerBonus<?>)copy);
            }
         }
      }
   }

   @SubscribeEvent
   public static void applyEventListenerEffect(ShieldBlockEvent event) {
      if (event.getEntity() instanceof Player player) {
         for (EventListenerBonus<?> bonus : getSkillBonuses(player, EventListenerBonus.class)) {
            if (bonus.getEventListener() instanceof BlockEventListener listener) {
               SkillBonus<? extends EventListenerBonus<?>> copy = bonus.copy();
               DamageSource source = event.getDamageSource();
               Entity sourceEntity = source.m_7639_();
               LivingEntity attacker = sourceEntity instanceof LivingEntity ? (LivingEntity)sourceEntity : null;
               listener.onEvent(player, attacker, source, (EventListenerBonus<?>)copy);
            }
         }
      }
   }

   @SubscribeEvent(
      priority = EventPriority.LOWEST
   )
   public static void applyEventListenerEffect(Finish event) {
      if (event.getEntity() instanceof Player player) {
         for (EventListenerBonus<?> bonus : getSkillBonuses(player, EventListenerBonus.class)) {
            if (bonus.getEventListener() instanceof ItemUsedEventListener listener) {
               SkillBonus<? extends EventListenerBonus<?>> copy = bonus.copy();
               listener.onEvent(player, event.getItem(), (EventListenerBonus<?>)copy);
            }
         }
      }
   }

   @SubscribeEvent
   public static void applyEventListenerEffect(LivingDeathEvent event) {
      if (event.getSource().m_7639_() instanceof Player player) {
         for (EventListenerBonus<?> bonus : getSkillBonuses(player, EventListenerBonus.class)) {
            if (bonus.getEventListener() instanceof KillEventListener listener) {
               SkillBonus<? extends EventListenerBonus<?>> copy = bonus.copy();
               DamageSource source = event.getSource();
               listener.onEvent(player, player, source, (EventListenerBonus<?>)copy);
            }
         }
      }
   }

   @SubscribeEvent
   public static void applyArrowRetrievalBonus(LivingHurtEvent event) {
      if (event.getSource().m_7640_() instanceof AbstractArrow arrow) {
         if (event.getSource().m_7639_() instanceof Player player) {
            AbstractArrowAccessor arrowAccessor = (AbstractArrowAccessor)arrow;
            ItemStack arrowStack = arrowAccessor.invokeGetPickupItem();
            if (arrowStack != null) {
               float retrievalChance = 0.0F;

               for (ArrowRetrievalBonus bonus : getSkillBonuses(player, ArrowRetrievalBonus.class)) {
                  retrievalChance += bonus.getChance();
               }

               if (!(player.m_217043_().m_188501_() >= retrievalChance)) {
                  LivingEntity target = event.getEntity();
                  CompoundTag targetData = target.getPersistentData();
                  ListTag stuckArrowsTag = targetData.m_128437_("StuckArrows", new CompoundTag().m_7060_());
                  stuckArrowsTag.add(arrowStack.m_41739_(new CompoundTag()));
                  targetData.m_128365_("StuckArrows", stuckArrowsTag);
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void retrieveArrows(LivingDeathEvent event) {
      LivingEntity entity = event.getEntity();
      ListTag arrowsTag = entity.getPersistentData().m_128437_("StuckArrows", new CompoundTag().m_7060_());
      if (!arrowsTag.isEmpty()) {
         for (Tag tag : arrowsTag) {
            ItemStack arrowStack = ItemStack.m_41712_((CompoundTag)tag);
            entity.m_19983_(arrowStack);
         }
      }
   }

   @SubscribeEvent
   public static void applyHealthReservationEffect(PlayerTickEvent event) {
      if (event.phase != Phase.END && event.side != LogicalSide.CLIENT) {
         float reservation = getHealthReservation(event.player);
         if (reservation != 0.0F) {
            if (event.player.m_21223_() / event.player.m_21233_() > 1.0F - reservation) {
               event.player.m_21153_(event.player.m_21233_() * (1.0F - reservation));
            }
         }
      }
   }

   @SubscribeEvent(
      priority = EventPriority.LOWEST
   )
   public static void applyHealthReservationEffect(LivingHealEvent event) {
      if (event.getEntity() instanceof Player player) {
         float reservation = getHealthReservation(player);
         if (reservation != 0.0F) {
            float healthAfterHealing = player.m_21223_() + event.getAmount();
            if (healthAfterHealing / player.m_21233_() > 1.0F - reservation) {
               event.setCanceled(true);
            }
         }
      }
   }

   private static float getHealthReservation(Player player) {
      float reservation = 0.0F;

      for (HealthReservationBonus bonus : getSkillBonuses(player, HealthReservationBonus.class)) {
         reservation += bonus.getAmount(player);
      }

      return reservation;
   }

   @SubscribeEvent
   public static void applyCantUseItemBonus(AttackEntityEvent event) {
      for (CantUseItemBonus bonus : getSkillBonuses(event.getEntity(), CantUseItemBonus.class)) {
         if (bonus.getItemCondition().met(event.getEntity().m_21205_())) {
            event.setCanceled(true);
            return;
         }
      }
   }

   @SubscribeEvent
   public static void applyCantUseItemBonus(PlayerInteractEvent event) {
      for (CantUseItemBonus bonus : getSkillBonuses(event.getEntity(), CantUseItemBonus.class)) {
         if (bonus.getItemCondition().met(event.getItemStack())) {
            event.setCancellationResult(InteractionResult.FAIL);
            if (event.isCancelable()) {
               event.setCanceled(true);
            }

            return;
         }
      }
   }

   @SubscribeEvent
   public static void applyCantUseItemBonus(CurioEquipEvent event) {
      if (event.getEntity() instanceof Player player) {
         for (CantUseItemBonus bonus : getSkillBonuses(player, CantUseItemBonus.class)) {
            if (bonus.getItemCondition().met(event.getStack())) {
               event.setResult(Result.DENY);
               return;
            }
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   @SubscribeEvent(
      priority = EventPriority.LOWEST
   )
   public static void addCantUseItemTooltip(GatherComponents event) {
      Player player = Minecraft.m_91087_().f_91074_;
      if (player != null) {
         for (CantUseItemBonus bonus : getSkillBonuses(player, CantUseItemBonus.class)) {
            if (bonus.getItemCondition().met(event.getItemStack())) {
               Component tooltip = Component.m_237115_("item.cant_use.info").m_130940_(ChatFormatting.RED);
               event.getTooltipElements().add(Either.left(tooltip));
               return;
            }
         }
      }
   }

   public static float getLootMultiplier(Player player, LootDuplicationBonus.LootType lootType) {
      Map<Float, Float> multipliers = getLootMultipliers(player, lootType);
      float multiplier = 0.0F;

      for (Entry<Float, Float> entry : multipliers.entrySet()) {
         float chance;
         for (chance = entry.getValue(); chance > 1.0F; chance--) {
            multiplier += entry.getKey();
         }

         if (player.m_217043_().m_188501_() < chance) {
            multiplier += entry.getKey();
         }
      }

      return multiplier;
   }

   @Nonnull
   private static Map<Float, Float> getLootMultipliers(Player player, LootDuplicationBonus.LootType lootType) {
      Map<Float, Float> multipliers = new HashMap<>();

      for (LootDuplicationBonus b : getSkillBonuses(player, LootDuplicationBonus.class)) {
         if (b.getLootType() == lootType) {
            float chance = b.getChance() + multipliers.getOrDefault(b.getMultiplier(), 0.0F);
            multipliers.put(b.getMultiplier(), chance);
         }
      }

      return multipliers;
   }

   protected static List<ItemEntity> getDrops(LivingDropsEvent event) {
      List<ItemEntity> drops = new ArrayList<>();

      for (ItemEntity itemEntity : event.getDrops()) {
         ItemEntity copy = itemEntity.m_32066_();
         drops.add(copy);
      }

      if (event.getEntity() instanceof EquippedEntity entity) {
         drops.removeIf(entity::hasItemEquipped);
      }

      return drops;
   }

   private static void addAttributeModifiers(BiConsumer<Attribute, AttributeModifier> addFunction, ItemStack stack) {
      for (ItemBonus<?> itemBonus : ItemHelper.getItemBonuses(stack)) {
         if (itemBonus instanceof ItemSkillBonus) {
            ItemSkillBonus itemSkillBonus = (ItemSkillBonus)itemBonus;
            SkillBonus<?> bonus = itemSkillBonus.getBonus();
            if (bonus instanceof AttributeBonus) {
               AttributeBonus attributeBonus = (AttributeBonus)bonus;
               if (!attributeBonus.hasMultiplier() && !attributeBonus.hasCondition()) {
                  addFunction.accept(attributeBonus.getAttribute(), attributeBonus.getModifier());
               }
            }
         }
      }
   }

   public static float getJumpHeightMultiplier(Player player) {
      float multiplier = 1.0F;

      for (JumpHeightBonus bonus : getSkillBonuses(player, JumpHeightBonus.class)) {
         multiplier += bonus.getJumpHeightMultiplier(player);
      }

      return multiplier;
   }

   public static void amplifyEnchantments(List<EnchantmentInstance> enchantments, RandomSource random, Player player) {
      enchantments.replaceAll(enchantmentInstance -> amplifyEnchantment(enchantmentInstance, random, player));
   }

   private static EnchantmentInstance amplifyEnchantment(EnchantmentInstance enchantment, RandomSource random, Player player) {
      if (enchantment.f_44947_.m_6586_() == 1) {
         return enchantment;
      } else {
         float amplificationChance = getAmplificationChance(enchantment, player);
         if (amplificationChance == 0.0F) {
            return enchantment;
         } else {
            int levelBonus = (int)amplificationChance;
            amplificationChance -= (float)levelBonus;
            int enchantmentLevel = enchantment.f_44948_ + levelBonus;
            if (random.m_188501_() < amplificationChance) {
               enchantmentLevel++;
            }

            return new EnchantmentInstance(enchantment.f_44947_, enchantmentLevel);
         }
      }
   }

   public static int adjustEnchantmentCost(int cost, @Nonnull Player player) {
      return (int)Math.max(1.0, (double)cost * getEnchantmentCostMultiplier(player));
   }

   public static float getFreeEnchantmentChance(@Nonnull Player player) {
      float chance = 0.0F;

      for (FreeEnchantmentBonus bonus : getSkillBonuses(player, FreeEnchantmentBonus.class)) {
         chance += bonus.getChance();
      }

      return chance;
   }

   private static double getEnchantmentCostMultiplier(@Nonnull Player player) {
      float multiplier = 1.0F;

      for (EnchantmentRequirementBonus bonus : getSkillBonuses(player, EnchantmentRequirementBonus.class)) {
         multiplier += bonus.getMultiplier();
      }

      return (double)multiplier;
   }

   private static float getAmplificationChance(EnchantmentInstance enchantment, Player player) {
      float chance = 0.0F;

      for (EnchantmentAmplificationBonus bonus : getSkillBonuses(player, EnchantmentAmplificationBonus.class)) {
         if (bonus.getCondition().met(enchantment.f_44947_.f_44672_)) {
            chance += bonus.getChance();
         }
      }

      return chance;
   }

   public static <T> List<T> getSkillBonuses(@Nonnull Player player, Class<T> type) {
      if (!PlayerSkillsProvider.hasSkills(player)) {
         return List.of();
      } else {
         List<T> bonuses = new ArrayList<>();
         bonuses.addAll(getPlayerBonuses(player, type));
         bonuses.addAll(getEffectBonuses(player, type));
         bonuses.addAll(getEquipmentBonuses(player, type));
         return bonuses;
      }
   }

   private static <T> List<T> getPlayerBonuses(Player player, Class<T> type) {
      List<T> list = new ArrayList<>();

      for (PassiveSkill skill : PlayerSkillsProvider.get(player).getPlayerSkills()) {
         for (SkillBonus<?> skillBonus : skill.getBonuses()) {
            if (type.isInstance(skillBonus)) {
               list.add(type.cast(skillBonus));
            }
         }
      }

      return list;
   }

   private static <T> List<T> getEffectBonuses(Player player, Class<T> type) {
      List<T> bonuses = new ArrayList<>();

      for (MobEffectInstance e : player.m_21220_()) {
         MobEffect bonus = e.m_19544_();
         if (bonus instanceof SkillBonusEffect) {
            SkillBonusEffect skillEffect = (SkillBonusEffect)bonus;
            SkillBonus<?> bonusx = skillEffect.getBonus().copy();
            if (type.isInstance(bonusx)) {
               SkillBonus<?> var8 = bonusx.copy().multiply((double)e.m_19564_());
               bonuses.add(type.cast(var8));
            }
         }
      }

      return bonuses;
   }

   private static <T> List<T> getEquipmentBonuses(Player player, Class<T> type) {
      return PlayerHelper.getAllEquipment(player).map(s -> getItemBonuses(s, type)).flatMap(Collection::stream).toList();
   }

   private static <T> List<T> getItemBonuses(ItemStack stack, Class<T> type) {
      List<ItemBonus<?>> itemBonuses = new ArrayList<>();
      if (stack.m_41720_() instanceof ItemBonusProvider provider) {
         itemBonuses.addAll(provider.getItemBonuses());
      }

      itemBonuses.addAll(ItemHelper.getItemBonuses(stack));
      List<T> bonuses = new ArrayList<>();

      for (ItemBonus<?> itemBonus : itemBonuses) {
         if (itemBonus instanceof ItemSkillBonus) {
            ItemSkillBonus skillBonus = (ItemSkillBonus)itemBonus;
            SkillBonus<?> bonus = skillBonus.getBonus();
            if (type.isInstance(bonus)) {
               bonuses.add(type.cast(bonus));
            }
         }
      }

      return bonuses;
   }
}
