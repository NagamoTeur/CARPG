package com.hollingsworth.arsnouveau.api.spell;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.IWrappedCaster;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.LivingCaster;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import java.util.ArrayList;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

public class SpellContext implements Cloneable {
   private boolean isCanceled;
   private Spell spell;
   private ItemStack casterTool = ItemStack.f_41583_;
   @Nullable
   private LivingEntity caster;
   private int currentIndex;
   @Nullable
   public BlockEntity castingTile;
   private ParticleColor colors = ParticleColor.defaultParticleColor();
   private SpellContext.CasterType type;
   private Level level;
   public CompoundTag tag = new CompoundTag();
   private IWrappedCaster wrappedCaster;

   @Deprecated(
      forRemoval = true,
      since = "3.4.0"
   )
   public SpellContext(Level level, @NotNull Spell spell, @Nullable LivingEntity caster) {
      this.level = level;
      this.spell = spell;
      this.caster = caster;
      this.isCanceled = false;
      this.currentIndex = 0;
      this.colors = spell.color.clone();
      this.wrappedCaster = new LivingCaster(this.getUnwrappedCaster());
   }

   public SpellContext(Level level, @NotNull Spell spell, @Nullable LivingEntity caster, IWrappedCaster wrappedCaster) {
      this(level, spell, caster);
      this.wrappedCaster = wrappedCaster;
   }

   public SpellContext(Level level, @NotNull Spell spell, @Nullable LivingEntity caster, IWrappedCaster wrappedCaster, ItemStack casterTool) {
      this(level, spell, caster, wrappedCaster);
      this.casterTool = casterTool.m_41777_();
   }

   public SpellContext withWrappedCaster(IWrappedCaster caster) {
      this.wrappedCaster = caster;
      if (caster instanceof LivingCaster livingCaster) {
         this.caster = livingCaster.livingEntity;
      }

      return this;
   }

   @Nullable
   public AbstractSpellPart nextPart() {
      this.currentIndex++;
      AbstractSpellPart part = null;

      try {
         part = this.getSpell().recipe.get(this.currentIndex - 1);
      } catch (Throwable var3) {
         System.out.println("=======");
         System.out.println("Invalid spell cast found! This is a bug and should be reported!");
         System.out.println(this.spell.getDisplayString());
         System.out.println("Casting player: ");
         System.out.println(this.caster);
         System.out.println("Casting tile:");
         System.out.println(this.castingTile);
         System.out.println("=======");
         var3.printStackTrace();
      }

      return part;
   }

   public boolean hasNextPart() {
      return this.spell.isValid() && !this.isCanceled() && this.currentIndex < this.spell.recipe.size();
   }

   public SpellContext resetCastCounter() {
      this.currentIndex = 0;
      this.isCanceled = false;
      return this;
   }

   @Deprecated(
      forRemoval = true,
      since = "3.4.0"
   )
   public SpellContext withCastingTile(BlockEntity tile) {
      this.castingTile = tile;
      return this;
   }

   @Deprecated(
      forRemoval = true,
      since = "3.4.0"
   )
   public SpellContext withCaster(@Nullable LivingEntity caster) {
      this.caster = caster;
      return this;
   }

   public SpellContext withColors(ParticleColor colors) {
      this.colors = colors;
      return this;
   }

   @Deprecated(
      forRemoval = true,
      since = "3.4.0"
   )
   public SpellContext withType(SpellContext.CasterType type) {
      this.type = type;
      return this;
   }

   public SpellContext withSpell(Spell spell) {
      this.spell = spell;
      return this;
   }

   @NotNull
   public LivingEntity getUnwrappedCaster() {
      LivingEntity shooter = this.caster;
      if (shooter == null && this.castingTile != null) {
         shooter = ANFakePlayer.getPlayer((ServerLevel)this.level);
         BlockPos pos = this.castingTile.m_58899_();
         shooter.m_6034_((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_());
      }

      return (LivingEntity)(shooter == null ? ANFakePlayer.getPlayer((ServerLevel)this.level) : shooter);
   }

   @NotNull
   public IWrappedCaster getCaster() {
      return this.wrappedCaster;
   }

   public ItemStack getCasterTool() {
      return this.casterTool;
   }

   @Deprecated(
      forRemoval = true,
      since = "3.4.0"
   )
   public SpellContext.CasterType getType() {
      return this.type == null ? this.wrappedCaster.getCasterType() : this.type;
   }

   public int getCurrentIndex() {
      return this.currentIndex;
   }

   public void setCurrentIndex(int newIndex) {
      this.currentIndex = newIndex;
   }

   public boolean isCanceled() {
      return this.isCanceled;
   }

   public void setCanceled(boolean canceled) {
      this.isCanceled = canceled;
   }

   @NotNull
   public Spell getSpell() {
      return this.spell == null ? new Spell() : this.spell;
   }

   @NotNull
   public Spell getRemainingSpell() {
      return this.getCurrentIndex() >= this.getSpell().recipe.size()
         ? this.getSpell().clone().setRecipe(new ArrayList<>())
         : this.getSpell().clone().setRecipe(new ArrayList<>(this.getSpell().recipe.subList(this.getCurrentIndex(), this.getSpell().recipe.size())));
   }

   public SpellContext clone() {
      try {
         SpellContext clone = (SpellContext)super.clone();
         clone.spell = this.spell.clone();
         clone.colors = this.colors.clone();
         clone.tag = this.tag.m_6426_();
         clone.caster = this.caster;
         clone.castingTile = this.castingTile;
         clone.casterTool = this.casterTool.m_41777_();
         clone.type = this.type;
         clone.level = this.level;
         clone.wrappedCaster = this.wrappedCaster;
         return clone;
      } catch (CloneNotSupportedException var2) {
         throw new AssertionError();
      }
   }

   public ParticleColor getColors() {
      return this.colors.clone();
   }

   public void setColors(ParticleColor colors) {
      this.colors = colors;
   }

   public void setCaster(@Nullable LivingEntity caster) {
      this.caster = caster;
   }

   public void setCasterTool(ItemStack stack) {
      this.casterTool = stack.m_41777_();
   }

   public static class CasterType {
      public static final SpellContext.CasterType RUNE = new SpellContext.CasterType("rune");
      public static final SpellContext.CasterType TURRET = new SpellContext.CasterType("turret");
      public static final SpellContext.CasterType ENTITY = new SpellContext.CasterType("entity");
      public static final SpellContext.CasterType OTHER = new SpellContext.CasterType("other");
      public static final SpellContext.CasterType LIVING_ENTITY = new SpellContext.CasterType("living_entity");
      public static final SpellContext.CasterType PLAYER = new SpellContext.CasterType("player");
      public String id;

      public CasterType(String id) {
         this.id = id;
      }
   }
}
