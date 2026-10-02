package daripher.skilltree.attribute.event;

import daripher.skilltree.client.tooltip.TooltipHelper;
import daripher.skilltree.config.Config;
import daripher.skilltree.entity.player.PlayerHelper;
import daripher.skilltree.init.PSTAttributes;
import daripher.skilltree.item.ItemHelper;
import daripher.skilltree.mixin.AbstractArrowAccessor;
import daripher.skilltree.mixin.LivingEntityAccessor;
import daripher.skilltree.skill.bonus.EventListenerBonus;
import daripher.skilltree.skill.bonus.SkillBonus;
import daripher.skilltree.skill.bonus.SkillBonusHandler;
import daripher.skilltree.skill.bonus.condition.item.EquipmentCondition;
import daripher.skilltree.skill.bonus.event.EvasionEventListener;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.Tags.Items;
import net.minecraftforge.event.GrindstoneEvent.OnTakeItem;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Tick;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
   modid = "skilltree"
)
public class AttributeEvents {
   @SubscribeEvent
   public static void applyLifeRegenerationBonus(PlayerTickEvent event) {
      if (event.phase != Phase.END && !event.player.f_19853_.f_46443_) {
         if (event.player.m_36324_().m_38702_() != 0) {
            float lifeRegeneration = (float)event.player.m_21133_((Attribute)PSTAttributes.REGENERATION.get());
            if (event.player.m_21223_() != event.player.m_21233_() && event.player.f_19797_ % 20 == 0) {
               event.player.m_5634_(lifeRegeneration);
               event.player.m_36324_().m_38703_(lifeRegeneration / 5.0F);
            }
         }
      }
   }

   @SubscribeEvent
   public static void applyEvasionBonus(LivingAttackEvent event) {
      if (event.getEntity() instanceof Player player) {
         if (event.getSource() instanceof EntityDamageSource damageSource) {
            if (damageSource.m_7639_() instanceof LivingEntity attacker) {
               double evasion = player.m_21133_((Attribute)PSTAttributes.EVASION.get());
               double evasionChance = evasion * 0.05 / (1.0 + evasion * 0.05) * 0.8;
               if ((double)player.m_217043_().m_188501_() < evasionChance) {
                  player.f_19853_.m_6269_(null, player, SoundEvents.f_11893_, SoundSource.PLAYERS, 0.5F, 1.5F);
                  event.setCanceled(true);

                  for (EventListenerBonus<?> bonus : SkillBonusHandler.getSkillBonuses(player, EventListenerBonus.class)) {
                     if (bonus.getEventListener() instanceof EvasionEventListener listener) {
                        SkillBonus<? extends EventListenerBonus<?>> copy = bonus.copy();
                        listener.onEvent(player, attacker, (EventListenerBonus<?>)copy);
                     }
                  }
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void applyBlockingBonus(LivingAttackEvent event) {
      if (event.getEntity() instanceof Player player) {
         float damage = event.getAmount();
         if (!(damage <= 0.0F)) {
            DamageSource damageSource = event.getSource();
            if (!damageSource.m_19376_()) {
               Entity attacker = damageSource.m_7640_();
               if (attacker instanceof AbstractArrow arrow && arrow.m_36796_() > 0) {
                  return;
               }

               ItemStack shield = player.m_21206_();
               if (shield.m_204117_(Items.TOOLS_SHIELDS)) {
                  double blocking = player.m_21133_((Attribute)PSTAttributes.BLOCKING.get());
                  double blockChance = blocking * 0.05 / (1.0 + blocking * 0.05) * 0.8;
                  if (!((double)player.m_217043_().m_188501_() >= blockChance)) {
                     ShieldBlockEvent blockEvent = ForgeHooks.onShieldBlock(player, damageSource, damage);
                     if (!blockEvent.isCanceled()) {
                        event.setCanceled(true);
                        player.f_19853_.m_7605_(player, (byte)29);
                        if (blockEvent.shieldTakesDamage()) {
                           PlayerHelper.hurtShield(player, shield, damage);
                        }

                        if (!damageSource.m_19360_()) {
                           if (attacker instanceof LivingEntity livingAttacker) {
                              LivingEntityAccessor entityAccessor = (LivingEntityAccessor)player;
                              entityAccessor.invokeBlockUsingShield(livingAttacker);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void applyPoisonedWeaponEffects(LivingHurtEvent event) {
      if (event.getSource().m_7640_() instanceof Player player) {
         ItemStack weapon = player.m_21205_();
         if (ItemHelper.hasPoisons(weapon)) {
            List<MobEffectInstance> poisons = ItemHelper.getPoisons(weapon);
            poisons.forEach(event.getEntity()::m_7292_);
         }
      }
   }

   @SubscribeEvent
   public static void applyPoisonedThrownTridentEffects(LivingHurtEvent event) {
      DamageSource damageSource = event.getSource();
      if (damageSource.m_7640_() instanceof ThrownTrident trident) {
         AbstractArrowAccessor arrowAccessor = (AbstractArrowAccessor)trident;
         ItemStack weapon = arrowAccessor.invokeGetPickupItem();
         if (weapon != null) {
            if (ItemHelper.hasPoisons(weapon)) {
               List<MobEffectInstance> poisons = ItemHelper.getPoisons(weapon);
               LivingEntity target = event.getEntity();

               for (MobEffectInstance poison : poisons) {
                  MobEffectInstance effectInstance = new MobEffectInstance(poison);
                  target.m_7292_(effectInstance);
               }
            }
         }
      }
   }

   @SubscribeEvent(
      priority = EventPriority.HIGH
   )
   public static void addPoisonedWeaponTooltips(ItemTooltipEvent event) {
      ItemStack weapon = event.getItemStack();
      if (ItemHelper.hasPoisons(weapon)) {
         List<Component> tooltips = event.getToolTip();
         tooltips.add(Component.m_237119_());
         tooltips.add(Component.m_237115_("weapon.poisoned").m_130940_(ChatFormatting.DARK_PURPLE));

         for (MobEffectInstance poison : ItemHelper.getPoisons(weapon)) {
            Component tooltip = TooltipHelper.getEffectInstanceTooltip(poison);
            tooltips.add(tooltip);
         }
      }
   }

   @SubscribeEvent
   public static void applyExperiencePerMinuteBonus(PlayerTickEvent event) {
      Player player = event.player;
      if (!player.f_19853_.f_46443_) {
         float bonus = (float)player.m_21133_((Attribute)PSTAttributes.EXP_PER_MINUTE.get());
         int frequency = Math.max((int)(1200.0F / bonus), 1);
         if (player.f_19797_ % frequency == 0) {
            ExperienceOrb expOrb = new ExperienceOrb(player.f_19853_, player.m_20185_(), player.m_20186_(), player.m_20189_(), 1);
            player.f_19853_.m_7967_(expOrb);
         }
      }
   }

   @SubscribeEvent
   public static void applyStealthBonus(LivingChangeTargetEvent event) {
      if (event.getNewTarget() instanceof Player player) {
         double stealth = player.m_21133_((Attribute)PSTAttributes.STEALTH.get()) / 100.0;
         if (stealth != 0.0) {
            LivingEntity attacker = event.getEntity();
            double followRange = attacker.m_21133_(Attributes.f_22277_);
            if ((double)attacker.m_20270_(player) > followRange * (1.0 - stealth)) {
               event.setCanceled(true);
            }
         }
      }
   }

   @SubscribeEvent
   public static void applyGrindstoneExpPenalty(OnTakeItem event) {
      event.setXp((int)((double)event.getXp() * Config.grindstone_exp_multiplier));
   }

   @SubscribeEvent
   public static void applyRangedWeaponAttackSpeedBonus(Tick event) {
      if (EquipmentCondition.isRangedWeapon(event.getItem())) {
         AttributeInstance attribute = event.getEntity().m_21051_(Attributes.f_22283_);
         if (attribute != null) {
            double baseAttackSpeed = attribute.m_22115_();
            if (baseAttackSpeed != 0.0) {
               double attackSpeedBonus = attribute.m_22135_() / baseAttackSpeed - 1.0;
               if (attackSpeedBonus != 0.0) {
                  int tickBonus;
                  for (tickBonus = attackSpeedBonus < 0.0 ? 1 : -1; attackSpeedBonus > 1.0; attackSpeedBonus--) {
                     event.setDuration(event.getDuration() + tickBonus);
                  }

                  int bonusTickFrequency = (int)(1.0 / attackSpeedBonus);
                  if (event.getEntity().f_19797_ % bonusTickFrequency == 0) {
                     event.setDuration(event.getDuration() + tickBonus);
                  }
               }
            }
         }
      }
   }
}
