package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.common.entity.ModEntities;
import com.hollingsworth.arsnouveau.common.entity.SummonHorse;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAOE;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDurationDown;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentExtendTime;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.jetbrains.annotations.NotNull;

public class EffectSummonSteed extends AbstractEffect {
   public static EffectSummonSteed INSTANCE = new EffectSummonSteed();

   private EffectSummonSteed() {
      super(GlyphLib.EffectSummonSteedID, "Summon Steed");
   }

   @Override
   public void onResolve(
      HitResult rayTraceResult, Level world, @Nullable LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      if (this.canSummon(shooter)) {
         int ticks = (int)(
            20.0
               * (
                  (double)((Integer)this.GENERIC_INT.get()).intValue()
                     + (double)((Integer)this.EXTEND_TIME.get()).intValue() * spellStats.getDurationMultiplier()
               )
         );
         if (ticks <= 0) {
            ticks = 20;
         }

         Vec3 hit = rayTraceResult.m_82450_();

         for (int i = 0; (long)i < 1L + Math.round(spellStats.getAoeMultiplier()); i++) {
            SummonHorse horse = new SummonHorse((EntityType<? extends Horse>)ModEntities.SUMMON_HORSE.get(), world);
            horse.m_6034_(hit.m_7096_(), hit.m_7098_(), hit.m_7094_());
            horse.ticksLeft = ticks;
            horse.m_30637_((Player)shooter);
            horse.getHorseInventory().m_6836_(0, new ItemStack(Items.f_42450_));
            horse.setOwnerID(shooter.m_20148_());
            horse.m_21409_(EquipmentSlot.CHEST, 0.0F);
            this.summonLivingEntity(rayTraceResult, world, shooter, spellStats, spellContext, resolver, horse);
         }

         this.applySummoningSickness(shooter, 600);
      }
   }

   @Override
   public void buildConfig(Builder builder) {
      super.buildConfig(builder);
      this.addExtendTimeConfig(builder, 120);
      this.addGenericInt(builder, 300, "Base duration in seconds", "duration");
   }

   @Override
   public int getDefaultManaCost() {
      return 100;
   }

   @Override
   public SpellTier defaultTier() {
      return SpellTier.ONE;
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(new AbstractAugment[]{AugmentExtendTime.INSTANCE, AugmentDurationDown.INSTANCE, AugmentAOE.INSTANCE});
   }

   @Override
   public String getBookDescription() {
      return "Summons a saddled horse that will vanish after a few minutes. AOE will increase the amount summoned, while Extend Time will increase the duration of the summon. Applies Summoning Sickness to the caster, and cannot be cast while afflicted by this Sickness.";
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.CONJURATION});
   }
}
