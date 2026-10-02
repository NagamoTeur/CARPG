package com.hollingsworth.arsnouveau.common.spell.method;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractCastMethod;
import com.hollingsworth.arsnouveau.api.spell.CastResolveType;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.common.entity.EntityProjectileSpell;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAccelerate;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentDecelerate;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentPierce;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentSensitive;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentSplit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import org.jetbrains.annotations.NotNull;

public class MethodProjectile extends AbstractCastMethod {
   public static MethodProjectile INSTANCE = new MethodProjectile();
   public IntValue PROJECTILE_TTL;

   private MethodProjectile() {
      super(GlyphLib.MethodProjectileID, "Projectile");
   }

   @Override
   public void buildConfig(Builder builder) {
      super.buildConfig(builder);
      this.PROJECTILE_TTL = builder.comment("Max lifespan of the projectile, in seconds.").defineInRange("max_lifespan", 60, 0, Integer.MAX_VALUE);
   }

   public int getProjectileLifespan() {
      return this.PROJECTILE_TTL != null ? (Integer)this.PROJECTILE_TTL.get() : 60;
   }

   @Override
   public int getDefaultManaCost() {
      return 10;
   }

   public void summonProjectiles(Level world, LivingEntity shooter, SpellStats stats, SpellResolver resolver) {
      int numSplits = 1 + stats.getBuffCount(AugmentSplit.INSTANCE);
      List<EntityProjectileSpell> projectiles = new ArrayList<>();

      for (int i = 0; i < numSplits; i++) {
         EntityProjectileSpell spell = new EntityProjectileSpell(world, resolver);
         projectiles.add(spell);
      }

      float velocity = Math.max(0.1F, 0.75F + stats.getAccMultiplier() / 2.0F);
      int opposite = -1;
      int counter = 0;

      for (EntityProjectileSpell proj : projectiles) {
         proj.shoot(shooter, shooter.m_146909_(), shooter.m_146908_() + (float)(Math.round((double)counter / 2.0) * 10L * (long)opposite), 0.0F, velocity, 0.8F);
         opposite *= -1;
         counter++;
         world.m_7967_(proj);
      }
   }

   public void summonProjectiles(Level world, BlockPos pos, LivingEntity shooter, SpellStats stats, SpellResolver resolver) {
      ArrayList<EntityProjectileSpell> projectiles = new ArrayList<>();
      EntityProjectileSpell projectileSpell = new EntityProjectileSpell(world, resolver);
      projectileSpell.m_6034_((double)pos.m_123341_(), (double)(pos.m_123342_() + 1), (double)pos.m_123343_());
      projectiles.add(projectileSpell);
      int numSplits = stats.getBuffCount(AugmentSplit.INSTANCE);

      for (int i = 1; i < numSplits + 1; i++) {
         Direction offset = shooter.m_6350_().m_122427_();
         if (i % 2 == 0) {
            offset = offset.m_122424_();
         }

         BlockPos projPos = pos.m_5484_(offset, i);
         projPos = projPos.m_7637_(0.0, 1.5, 0.0);
         EntityProjectileSpell spell = new EntityProjectileSpell(world, resolver);
         spell.m_6034_((double)projPos.m_123341_(), (double)projPos.m_123342_(), (double)projPos.m_123343_());
         projectiles.add(spell);
      }

      for (EntityProjectileSpell proj : projectiles) {
         proj.m_20256_(new Vec3(0.0, -0.1, 0.0));
         world.m_7967_(proj);
      }
   }

   @Override
   public CastResolveType onCast(ItemStack stack, LivingEntity shooter, Level world, SpellStats spellStats, SpellContext context, SpellResolver resolver) {
      this.summonProjectiles(world, shooter, spellStats, resolver);
      return CastResolveType.SUCCESS;
   }

   @Override
   public CastResolveType onCastOnBlock(UseOnContext context, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver) {
      Level world = context.m_43725_();
      Player shooter = context.m_43723_();
      this.summonProjectiles(world, shooter, spellStats, resolver);
      return CastResolveType.SUCCESS;
   }

   @Override
   public CastResolveType onCastOnBlock(
      BlockHitResult blockRayTraceResult, LivingEntity caster, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      caster.m_7618_(Anchor.EYES, blockRayTraceResult.m_82450_().m_82520_(0.0, 0.0, 0.0));
      this.summonProjectiles(caster.m_20193_(), blockRayTraceResult.m_82425_(), caster, spellStats, resolver);
      return CastResolveType.SUCCESS;
   }

   @Override
   public CastResolveType onCastOnEntity(
      ItemStack stack, LivingEntity caster, Entity target, InteractionHand hand, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      this.summonProjectiles(caster.m_20193_(), caster, spellStats, resolver);
      return CastResolveType.SUCCESS;
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(
         new AbstractAugment[]{
            AugmentPierce.INSTANCE, AugmentSplit.INSTANCE, AugmentAccelerate.INSTANCE, AugmentDecelerate.INSTANCE, AugmentSensitive.INSTANCE
         }
      );
   }

   @Override
   public String getBookDescription() {
      return "A spell you start with. Summons a projectile that applies spell effects when this projectile hits a target or block. Sensitive will allow Projectiles to break plants or other materials that do not block motion.";
   }

   @Override
   public boolean defaultedStarterGlyph() {
      return true;
   }
}
