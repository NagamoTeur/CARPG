package shadows.apotheosis.core.attributeslib.impl;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Tick;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.level.BlockEvent.BreakEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.core.attributeslib.AttributesLib;
import shadows.apotheosis.core.attributeslib.api.ALAttributes;
import shadows.apotheosis.core.attributeslib.api.AttributeHelper;
import shadows.apotheosis.core.attributeslib.api.IFormattableAttribute;
import shadows.apotheosis.core.attributeslib.packet.CritParticleMessage;
import shadows.apotheosis.core.attributeslib.util.AttributesUtil;
import shadows.placebo.network.PacketDistro;

public class AttributeEvents {
   private static boolean noRecurse = false;
   private static Random dodgeRand = new Random();

   @SubscribeEvent
   public void fixChangedAttributes(PlayerLoggedInEvent e) {
      AttributeMap map = e.getEntity().m_21204_();

      for (Entry<ResourceKey<Attribute>, Attribute> entry : Registry.f_122866_.m_6579_()) {
         if ("apotheosis".equals(entry.getKey().m_135782_().m_135827_())) {
            map.m_22146_(entry.getValue()).m_22100_(((RangedAttribute)entry.getValue()).m_22082_());
         }
      }

      map.m_22146_((Attribute)ForgeMod.STEP_HEIGHT_ADDITION.get()).m_22100_(0.6);
   }

   private boolean canBenefitFromDrawSpeed(ItemStack stack) {
      return stack.m_41720_() instanceof ProjectileWeaponItem || stack.m_41720_() instanceof TridentItem;
   }

   @SubscribeEvent
   public void drawSpeed(Tick e) {
      if (e.getEntity() instanceof Player player) {
         double t = player.m_21051_((Attribute)ALAttributes.DRAW_SPEED.get()).m_22135_() - 1.0;
         if (t == 0.0 || !this.canBenefitFromDrawSpeed(e.getItem())) {
            return;
         }

         int offset = -1;
         if (t < 0.0) {
            offset = 1;
            t = -t;
         }

         while (t > 1.0) {
            e.setDuration(e.getDuration() + offset);
            t--;
         }

         if (t > 0.5) {
            if (e.getEntity().f_19797_ % 2 == 0) {
               e.setDuration(e.getDuration() + offset);
            }

            t -= 0.5;
         }

         int mod = (int)Math.floor(1.0 / Math.min(1.0, t));
         if (e.getEntity().f_19797_ % mod == 0) {
            e.setDuration(e.getDuration() + offset);
         }

         t--;
      }
   }

   @SubscribeEvent(
      priority = EventPriority.LOWEST
   )
   public void lifeStealOverheal(LivingHurtEvent e) {
      if (e.getSource().m_7640_() instanceof LivingEntity attacker && AttributesUtil.isPhysicalDamage(e.getSource())) {
         float lifesteal = (float)attacker.m_21133_((Attribute)ALAttributes.LIFE_STEAL.get());
         float dmg = Math.min(e.getAmount(), e.getEntity().m_21223_());
         if ((double)lifesteal > 0.001) {
            attacker.m_5634_(dmg * lifesteal);
         }

         float overheal = (float)attacker.m_21133_((Attribute)ALAttributes.OVERHEAL.get());
         float maxOverheal = attacker.m_21233_() * 0.5F;
         if (overheal > 0.0F && attacker.m_6103_() < maxOverheal) {
            attacker.m_7911_(Math.min(maxOverheal, attacker.m_6103_() + dmg * overheal));
         }
      }
   }

   @SubscribeEvent(
      priority = EventPriority.LOWEST
   )
   public void meleeDamageAttributes(LivingAttackEvent e) {
      if (!e.getEntity().f_19853_.f_46443_) {
         if (!noRecurse) {
            noRecurse = true;
            if (e.getSource().m_7640_() instanceof LivingEntity attacker && AttributesUtil.isPhysicalDamage(e.getSource())) {
               float hpDmg = (float)attacker.m_21133_((Attribute)ALAttributes.CURRENT_HP_DAMAGE.get());
               float fireDmg = (float)attacker.m_21133_((Attribute)ALAttributes.FIRE_DAMAGE.get());
               float coldDmg = (float)attacker.m_21133_((Attribute)ALAttributes.COLD_DAMAGE.get());
               LivingEntity target = e.getEntity();
               int time = target.f_19802_;
               target.f_19802_ = 0;
               float localAtkStrength = Apotheosis.getLocalAtkStrength(attacker);
               if ((double)hpDmg > 0.001 && localAtkStrength >= 0.85F) {
                  target.m_6469_(src(attacker), localAtkStrength * hpDmg * target.m_21223_());
               }

               target.f_19802_ = 0;
               if ((double)fireDmg > 0.001 && localAtkStrength >= 0.55F) {
                  target.m_6469_(src(attacker).m_19389_().m_19380_(), localAtkStrength * fireDmg);
                  target.m_7311_(target.m_20094_() + (int)(10.0F * fireDmg));
               }

               target.f_19802_ = 0;
               if ((double)coldDmg > 0.001 && localAtkStrength >= 0.55F) {
                  target.m_6469_(src(attacker).m_19389_().m_19380_(), localAtkStrength * coldDmg);
                  target.m_7292_(new MobEffectInstance(MobEffects.f_19597_, (int)(15.0F * coldDmg), Mth.m_14143_(coldDmg / 5.0F)));
               }

               target.f_19802_ = time;
            }

            noRecurse = false;
         }
      }
   }

   private static DamageSource src(LivingEntity entity) {
      return entity instanceof Player p ? DamageSource.m_19344_(p) : DamageSource.m_19370_(entity);
   }

   @SubscribeEvent(
      priority = EventPriority.HIGH
   )
   public void apothCriticalStrike(LivingHurtEvent e) {
      LivingEntity attacker = e.getSource().m_7639_() instanceof LivingEntity le ? le : null;
      if (attacker != null) {
         double critChance = attacker.m_21133_((Attribute)ALAttributes.CRIT_CHANCE.get());
         float critDmg = (float)attacker.m_21133_((Attribute)ALAttributes.CRIT_DAMAGE.get());
         RandomSource rand = e.getEntity().f_19796_;

         float critMult;
         for (critMult = 1.0F; (double)rand.m_188501_() <= critChance && critDmg > 1.0F; critDmg *= 0.85F) {
            critChance--;
            critMult *= critDmg;
         }

         e.setAmount(e.getAmount() * critMult);
         if (critMult > 1.0F && !attacker.f_19853_.f_46443_) {
            PacketDistro.sendToTracking(
               AttributesLib.CHANNEL, new CritParticleMessage(e.getEntity().m_19879_()), (ServerLevel)attacker.f_19853_, e.getEntity().m_20183_()
            );
         }
      }
   }

   @SubscribeEvent(
      priority = EventPriority.HIGH
   )
   public void vanillaCritDmg(CriticalHitEvent e) {
      float critDmg = (float)e.getEntity().m_21133_((Attribute)ALAttributes.CRIT_DAMAGE.get());
      if (e.isVanillaCritical()) {
         e.setDamageModifier(Math.max(e.getDamageModifier(), critDmg));
      }
   }

   @SubscribeEvent(
      priority = EventPriority.HIGH
   )
   public void breakSpd(BreakSpeed e) {
      e.setNewSpeed(e.getNewSpeed() * (float)e.getEntity().m_21133_((Attribute)ALAttributes.MINING_SPEED.get()));
   }

   @SubscribeEvent(
      priority = EventPriority.HIGH
   )
   public void blockBreak(BreakEvent e) {
      double xpMult = e.getPlayer().m_21133_((Attribute)ALAttributes.EXPERIENCE_GAINED.get());
      e.setExpToDrop((int)((double)e.getExpToDrop() * xpMult));
   }

   @SubscribeEvent(
      priority = EventPriority.HIGH
   )
   public void mobXp(LivingExperienceDropEvent e) {
      Player player = e.getAttackingPlayer();
      if (player != null) {
         double xpMult = e.getAttackingPlayer().m_21133_((Attribute)ALAttributes.EXPERIENCE_GAINED.get());
         e.setDroppedExperience((int)((double)e.getDroppedExperience() * xpMult));
      }
   }

   @SubscribeEvent(
      priority = EventPriority.HIGH
   )
   public void heal(LivingHealEvent e) {
      float factor = (float)e.getEntity().m_21133_((Attribute)ALAttributes.HEALING_RECEIVED.get());
      e.setAmount(e.getAmount() * factor);
      if (e.getAmount() <= 0.0F) {
         e.setCanceled(true);
      }
   }

   @SubscribeEvent
   public void arrow(EntityJoinLevelEvent e) {
      if (e.getEntity() instanceof AbstractArrow arrow) {
         if (arrow.f_19853_.f_46443_ || arrow.getPersistentData().m_128471_("attributeslib.arrow.done")) {
            return;
         }

         if (arrow.m_37282_() instanceof LivingEntity le) {
            arrow.m_36781_(arrow.m_36789_() * le.m_21133_((Attribute)ALAttributes.ARROW_DAMAGE.get()));
            arrow.m_20256_(arrow.m_20184_().m_82490_(le.m_21133_((Attribute)ALAttributes.ARROW_VELOCITY.get())));
         }

         arrow.getPersistentData().m_128379_("attributeslib.arrow.done", true);
      }
   }

   private static double getAttackReachSqr(Entity attacker, LivingEntity pAttackTarget) {
      return (double)(attacker.m_20205_() * 2.0F * attacker.m_20205_() * 2.0F + pAttackTarget.m_20205_());
   }

   @SubscribeEvent(
      priority = EventPriority.HIGH
   )
   public void dodge(LivingAttackEvent e) {
      LivingEntity target = e.getEntity();
      if (!target.f_19853_.f_46443_) {
         Entity attacker = e.getSource().m_7640_();
         if (attacker instanceof LivingEntity) {
            double dodgeChance = target.m_21133_((Attribute)ALAttributes.DODGE_CHANCE.get());
            double atkRangeSqr = attacker instanceof Player p ? p.getAttackRange() * p.getAttackRange() : getAttackReachSqr(attacker, target);
            dodgeRand.setSeed((long)target.f_19797_);
            if (attacker.m_20280_(target) <= atkRangeSqr && (double)dodgeRand.nextFloat() <= dodgeChance) {
               this.onDodge(target);
               e.setCanceled(true);
            }
         }
      }
   }

   @SubscribeEvent(
      priority = EventPriority.HIGH
   )
   public void dodge(ProjectileImpactEvent e) {
      Entity target = e.getRayTraceResult() instanceof EntityHitResult entRes ? entRes.m_82443_() : null;
      if (target instanceof LivingEntity lvTarget) {
         double dodgeChance = lvTarget.m_21133_((Attribute)ALAttributes.DODGE_CHANCE.get());
         dodgeRand.setSeed((long)target.f_19797_);
         if ((double)dodgeRand.nextFloat() <= dodgeChance) {
            this.onDodge(lvTarget);
            e.setCanceled(true);
         }
      }
   }

   private void onDodge(LivingEntity target) {
      target.f_19853_.m_6269_(null, target, (SoundEvent)AttributesLib.DODGE_SOUND.get(), SoundSource.NEUTRAL, 1.0F, 0.7F + target.f_19796_.m_188501_() * 0.3F);
      if (target.f_19853_ instanceof ServerLevel sl) {
         double height = (double)target.m_20206_();
         double width = (double)target.m_20205_();
         sl.m_8767_(
            ParticleTypes.f_123755_,
            target.m_20185_() - width / 4.0,
            target.m_20186_(),
            target.m_20189_() - width / 4.0,
            6,
            -width / 4.0,
            height / 8.0,
            -width / 4.0,
            0.0
         );
      }
   }

   @SubscribeEvent(
      priority = EventPriority.LOWEST,
      receiveCanceled = true
   )
   public void fixMCF9370(ProjectileImpactEvent e) {
      if (e.isCanceled()) {
         Entity target = e.getRayTraceResult() instanceof EntityHitResult entRes ? entRes.m_82443_() : null;
         if (target != null && e.getProjectile() instanceof AbstractArrow arrow && arrow.m_36796_() > 0) {
            if (arrow.f_36701_ == null) {
               arrow.f_36701_ = new IntOpenHashSet(arrow.m_36796_());
            }

            arrow.f_36701_.add(target.m_19879_());
         }
      }
   }

   @SubscribeEvent
   public void affixModifiers(ItemAttributeModifierEvent e) {
      boolean hasBaseAD = e.getModifiers()
         .get(Attributes.f_22281_)
         .stream()
         .filter(m -> ((IFormattableAttribute)Attributes.f_22281_).getBaseUUID().equals(m.m_22209_()))
         .findAny()
         .isPresent();
      if (hasBaseAD) {
         boolean hasBaseAR = e.getModifiers()
            .get((Attribute)ForgeMod.ATTACK_RANGE.get())
            .stream()
            .filter(m -> ((IFormattableAttribute)ForgeMod.ATTACK_RANGE.get()).getBaseUUID().equals(m.m_22209_()))
            .findAny()
            .isPresent();
         if (!hasBaseAR) {
            e.addModifier(
               (Attribute)ForgeMod.ATTACK_RANGE.get(),
               new AttributeModifier(AttributeHelper.BASE_ATTACK_RANGE, () -> "attributeslib:fake_base_range", 0.0, Operation.ADDITION)
            );
         }
      }
   }
}
