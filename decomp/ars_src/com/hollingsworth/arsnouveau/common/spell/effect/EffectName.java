package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.item.inv.InteractType;
import com.hollingsworth.arsnouveau.api.item.inv.InventoryManager;
import com.hollingsworth.arsnouveau.api.item.inv.SlotReference;
import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.ISpellCaster;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.api.util.CasterUtil;
import com.hollingsworth.arsnouveau.api.util.StackUtil;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.mojang.authlib.GameProfile;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class EffectName extends AbstractEffect {
   public static EffectName INSTANCE = new EffectName();

   private EffectName() {
      super(GlyphLib.EffectNameID, "Name");
   }

   @Override
   public void onResolveEntity(
      EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      Component newName = this.getName(world, shooter, spellStats, spellContext, resolver);
      Entity entity = rayTraceResult.m_82443_();
      entity.m_6593_(newName);
      if (entity instanceof Mob mob) {
         mob.m_21530_();
      } else if (entity instanceof ItemEntity item) {
         item.m_32055_().m_41714_(newName);
      }

      if (shooter instanceof Player player && this.isRealPlayer(shooter) && player.equals(entity)) {
         ItemStack offhand = player.m_21206_();
         offhand.m_41714_(newName);
      }
   }

   public Component getName(Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver) {
      Component newName = null;
      InventoryManager manager = spellContext.getCaster().getInvManager();
      SlotReference slotRef = manager.findItem(i -> i.m_41720_() == Items.f_42656_, InteractType.EXTRACT);
      if (slotRef.getHandler() != null) {
         ItemStack stack = slotRef.getHandler().getStackInSlot(slotRef.getSlot());
         newName = stack.m_41611_().m_6879_();
      }

      if (newName == null && this.isRealPlayer(shooter) && shooter instanceof Player player) {
         ItemStack stack = StackUtil.getHeldCasterToolOrEmpty(player);
         if (stack != ItemStack.f_41583_ && stack.m_41783_() != null) {
            ISpellCaster caster = CasterUtil.getCaster(stack);
            newName = Component.m_237113_(caster.getSpellName());
         }
      }

      return newName;
   }

   @Override
   public void onResolveBlock(
      BlockHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      Component name = this.getName(world, shooter, spellStats, spellContext, resolver);
      BlockPos pos = rayTraceResult.m_82425_();
      BlockState state = world.m_8055_(pos);
      BlockEntity blockEntity = world.m_7702_(pos);
      if (blockEntity instanceof SkullBlockEntity head) {
         head.m_59769_(new GameProfile(null, name.getString()));
         world.m_7260_(pos, state, state, 3);
         head.m_6596_();
      } else if (blockEntity instanceof BaseContainerBlockEntity nameable) {
         nameable.m_58638_(name);
         world.m_7260_(pos, state, state, 3);
         nameable.m_6596_();
      } else {
         for (Entity entity : world.m_45933_(null, new AABB(pos).m_82400_(0.08))) {
            entity.m_6593_(name);
            if (entity instanceof Mob mob) {
               mob.m_21530_();
            } else if (entity instanceof ItemEntity item) {
               item.m_32055_().m_41714_(name);
            }
         }
      }
   }

   @Override
   public SpellTier defaultTier() {
      return SpellTier.TWO;
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.MANIPULATION});
   }

   @Override
   public int getDefaultManaCost() {
      return 25;
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(new AbstractAugment[0]);
   }

   @Override
   public String getBookDescription() {
      return "Names an entity after the set Spell Name. Targeting a block will name nearby entities or name inventory blocks directly if possible. Targeting with Self will name the held offhand item. Can be overridden with a name tag in the hotbar.";
   }
}
