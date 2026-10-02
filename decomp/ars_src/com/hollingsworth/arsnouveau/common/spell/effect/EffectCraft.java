package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import java.util.Set;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class EffectCraft extends AbstractEffect {
   public static EffectCraft INSTANCE = new EffectCraft();
   private static final Component CONTAINER_NAME = Component.m_237115_("container.crafting");

   private EffectCraft() {
      super(GlyphLib.EffectCraftID, "Craft");
   }

   @Override
   public void onResolve(
      HitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      if (shooter instanceof Player playerEntity && this.isRealPlayer(shooter)) {
         playerEntity.m_5893_(
            new SimpleMenuProvider(
               (id, inventory, player) -> new EffectCraft.CustomWorkbench(id, inventory, ContainerLevelAccess.m_39289_(player.m_20193_(), player.m_20183_())),
               CONTAINER_NAME
            )
         );
      }
   }

   @Override
   public int getDefaultManaCost() {
      return 50;
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(new AbstractAugment[0]);
   }

   @Override
   public String getBookDescription() {
      return "Opens the crafting menu.";
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.MANIPULATION});
   }

   public static class CustomWorkbench extends CraftingMenu {
      public CustomWorkbench(int id, Inventory playerInventory) {
         super(id, playerInventory);
      }

      public CustomWorkbench(int id, Inventory playerInventory, ContainerLevelAccess p_i50090_3_) {
         super(id, playerInventory, p_i50090_3_);
      }

      public boolean m_6875_(Player playerIn) {
         return true;
      }
   }
}
