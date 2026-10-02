package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.IPotionEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.common.entity.EnchantedFallingBlock;
import com.hollingsworth.arsnouveau.common.items.curios.ShapersFocus;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.potions.ModPotions;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentExtendTime;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.jetbrains.annotations.NotNull;

public class EffectSnare extends AbstractEffect implements IPotionEffect {
   public static EffectSnare INSTANCE = new EffectSnare();

   private EffectSnare() {
      super(GlyphLib.EffectSnareID, "Snare");
   }

   @Override
   public void onResolveEntity(
      EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      if (rayTraceResult.m_82443_() instanceof LivingEntity living) {
         this.applyConfigPotion(living, (MobEffect)ModPotions.SNARE_EFFECT.get(), spellStats);
         living.m_20334_(0.0, 0.0, 0.0);
         living.f_19864_ = true;
      } else if (rayTraceResult.m_82443_() instanceof EnchantedFallingBlock fallingBlock) {
         BlockPos resultPos = fallingBlock.groundBlock(true);
         if (resultPos != null) {
            ShapersFocus.tryPropagateBlockSpell(
               new BlockHitResult(
                  new Vec3((double)resultPos.m_123341_(), (double)resultPos.m_123342_(), (double)resultPos.m_123343_()),
                  rayTraceResult.m_82443_().m_6374_(),
                  resultPos,
                  false
               ),
               world,
               shooter,
               spellContext,
               resolver
            );
         }
      }
   }

   @Override
   public void buildConfig(Builder builder) {
      super.buildConfig(builder);
      this.addPotionConfig(builder, 8);
      this.addExtendTimeConfig(builder, 1);
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(new AbstractAugment[]{AugmentExtendTime.INSTANCE});
   }

   @Override
   public String getBookDescription() {
      return "Stops entities from moving and jumping. Extend Time will increase the duration of this effect. Snaring a block created from the Focus of Block Shaping will cause it to attempt to place itself immediately.";
   }

   @Override
   public int getDefaultManaCost() {
      return 100;
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.ELEMENTAL_EARTH});
   }

   @Override
   public int getBaseDuration() {
      return this.POTION_TIME == null ? 8 : (Integer)this.POTION_TIME.get();
   }

   @Override
   public int getExtendTimeDuration() {
      return this.EXTEND_TIME == null ? 1 : (Integer)this.EXTEND_TIME.get();
   }
}
