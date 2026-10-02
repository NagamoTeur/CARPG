package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.ILightable;
import com.hollingsworth.arsnouveau.api.spell.IPotionEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.common.block.SconceBlock;
import com.hollingsworth.arsnouveau.common.block.tile.LightTile;
import com.hollingsworth.arsnouveau.common.block.tile.TempLightTile;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDampen;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDurationDown;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentExtendTime;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import org.jetbrains.annotations.NotNull;

public class EffectLight extends AbstractEffect implements IPotionEffect {
   public static EffectLight INSTANCE = new EffectLight();

   private EffectLight() {
      super(GlyphLib.EffectLightID, "Conjure Magelight");
   }

   @Override
   public void onResolveEntity(
      EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      if (rayTraceResult.m_82443_() instanceof ILightable iLightable) {
         iLightable.onLight(rayTraceResult, world, shooter, spellStats, spellContext);
      }

      if (rayTraceResult.m_82443_() instanceof LivingEntity living) {
         if (shooter == null || !shooter.equals(living)) {
            this.applyConfigPotion(living, MobEffects.f_19619_, spellStats);
         }

         this.applyConfigPotion(living, MobEffects.f_19611_, spellStats);
      }
   }

   @Override
   public void onResolveBlock(
      BlockHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      BlockPos pos = rayTraceResult.m_82425_().m_121945_(rayTraceResult.m_82434_());
      if (BlockUtil.destroyRespectsClaim(this.getPlayer(shooter, (ServerLevel)world), world, pos)) {
         if (world.m_7702_(rayTraceResult.m_82425_()) instanceof ILightable lightable) {
            lightable.onLight(rayTraceResult, world, shooter, spellStats, spellContext);
         } else if (world.m_8055_(rayTraceResult.m_82425_()).m_60734_() instanceof ILightable lightable) {
            lightable.onLight(rayTraceResult, world, shooter, spellStats, spellContext);
         } else {
            if (world.m_8055_(pos).m_60767_().m_76336_()
               && world.m_45752_(BlockRegistry.LIGHT_BLOCK.m_49966_(), pos, CollisionContext.m_82750_(ANFakePlayer.getPlayer((ServerLevel)world)))
               && world.m_46739_(pos)) {
               BlockState lightBlockState = (BlockState)(spellStats.getDurationMultiplier() != 0.0 ? BlockRegistry.T_LIGHT_BLOCK : BlockRegistry.LIGHT_BLOCK)
                  .m_49966_()
                  .m_61124_(BlockStateProperties.f_61362_, world.m_6425_(pos).m_76152_() == Fluids.f_76193_);
               world.m_46597_(
                  pos, (BlockState)lightBlockState.m_61124_(SconceBlock.LIGHT_LEVEL, Math.max(0, Math.min(15, 14 + (int)spellStats.getAmpMultiplier())))
               );
               if (world.m_7702_(pos) instanceof LightTile tile) {
                  tile.color = spellContext.getColors();
                  if (tile instanceof TempLightTile tempLightTile) {
                     tempLightTile.lengthModifier = spellStats.getDurationMultiplier();
                  }
               }

               world.m_7260_(pos, world.m_8055_(pos), world.m_8055_(pos), 2);
            }
         }
      }
   }

   @Override
   public void buildConfig(Builder builder) {
      super.buildConfig(builder);
      this.addPotionConfig(builder, 30);
      this.addExtendTimeConfig(builder, 8);
   }

   @Override
   protected Map<ResourceLocation, Integer> getDefaultAugmentLimits(Map<ResourceLocation, Integer> defaults) {
      super.getDefaultAugmentLimits(defaults);
      defaults.put(AugmentAmplify.INSTANCE.getRegistryName(), 1);
      return defaults;
   }

   @Override
   public int getDefaultManaCost() {
      return 25;
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(new AbstractAugment[]{AugmentAmplify.INSTANCE, AugmentDurationDown.INSTANCE, AugmentDampen.INSTANCE, AugmentExtendTime.INSTANCE});
   }

   @Override
   public String getBookDescription() {
      return "If cast on a block, a permanent light source is created. May be amplified up to Glowstone brightness, or Dampened for a lower light level. When cast on yourself, you will receive night vision. When cast on other entities, they will receive Night Vision and Glowing.";
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.CONJURATION});
   }

   @Override
   public int getBaseDuration() {
      return this.POTION_TIME == null ? 30 : (Integer)this.POTION_TIME.get();
   }

   @Override
   public int getExtendTimeDuration() {
      return this.EXTEND_TIME == null ? 8 : (Integer)this.EXTEND_TIME.get();
   }
}
