package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.common.entity.EntityWallSpell;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAOE;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAccelerate;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDampen;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDecelerate;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDurationDown;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentExtendTime;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentSensitive;
import java.util.Set;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.jetbrains.annotations.NotNull;

public class EffectWall extends AbstractEffect {
   public static EffectWall INSTANCE = new EffectWall();

   private EffectWall() {
      super(GlyphLib.EffectWallId, "Wall");
   }

   @Override
   public void onResolve(
      HitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      Vec3 hit = this.safelyGetHitPos(rayTraceResult);
      EntityWallSpell entityWallSpell = new EntityWallSpell(world, shooter);
      spellContext.setCanceled(true);
      if (spellContext.getCurrentIndex() < spellContext.getSpell().recipe.size()) {
         Spell newSpell = spellContext.getRemainingSpell();
         SpellContext newContext = spellContext.clone().withSpell(newSpell);
         entityWallSpell.setAoe((float)spellStats.getAoeMultiplier());
         entityWallSpell.setSensitive(spellStats.isSensitive());
         entityWallSpell.setAccelerates((int)spellStats.getAccMultiplier());
         entityWallSpell.extendedTime = spellStats.getDurationMultiplier();
         entityWallSpell.setShouldFall(!spellStats.hasBuff(AugmentDampen.INSTANCE));
         Direction facingDirection = spellContext.getCaster().getFacingDirection();
         entityWallSpell.setDirection(facingDirection != Direction.UP && facingDirection != Direction.DOWN ? facingDirection.m_122427_() : facingDirection);
         entityWallSpell.spellResolver = new SpellResolver(newContext);
         entityWallSpell.m_6034_(hit.f_82479_, hit.f_82480_, hit.f_82481_);
         entityWallSpell.setColor(spellContext.getColors());
         world.m_7967_(entityWallSpell);
      }
   }

   @Override
   public String getBookDescription() {
      return "Creates a lingering wall that applies spells on nearby entities for a short time. Applying Sensitive will make this spell target blocks instead. AOE will expand the effective range, Accelerate will cast spells faster, Dampen will ignore gravity, and Extend Time will increase the duration.";
   }

   @Override
   public int getDefaultManaCost() {
      return 500;
   }

   @Override
   public SpellTier defaultTier() {
      return SpellTier.THREE;
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(
         new AbstractAugment[]{
            AugmentSensitive.INSTANCE,
            AugmentAOE.INSTANCE,
            AugmentAccelerate.INSTANCE,
            AugmentDecelerate.INSTANCE,
            AugmentExtendTime.INSTANCE,
            AugmentDurationDown.INSTANCE,
            AugmentDampen.INSTANCE
         }
      );
   }

   @Override
   protected void addDefaultInvalidCombos(Set<ResourceLocation> defaults) {
      defaults.add(EffectLinger.INSTANCE.getRegistryName());
   }

   @Override
   public void buildConfig(Builder builder) {
      super.buildConfig(builder);
      this.PER_SPELL_LIMIT = builder.comment("The maximum number of times this glyph may appear in a single spell").defineInRange("per_spell_limit", 1, 1, 1);
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.MANIPULATION});
   }
}
