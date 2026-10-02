package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import java.util.Set;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.util.FakePlayer;
import org.jetbrains.annotations.NotNull;

public class EffectEnderChest extends AbstractEffect {
   public static EffectEnderChest INSTANCE = new EffectEnderChest();
   private static final Component CONTAINER_NAME = Component.m_237115_("container.enderchest");

   private EffectEnderChest() {
      super(GlyphLib.EffectEnderChestID, "Access Ender Inventory");
   }

   @Override
   public void onResolveEntity(
      EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      if (shooter instanceof Player && !(shooter instanceof FakePlayer)) {
         PlayerEnderChestContainer chestInventory = ((Player)shooter).m_36327_();
         ((Player)shooter)
            .m_5893_(
               new SimpleMenuProvider((p_226928_1_, p_226928_2_, p_226928_3_) -> ChestMenu.m_39237_(p_226928_1_, p_226928_2_, chestInventory), CONTAINER_NAME)
            );
      }
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(new AbstractAugment[0]);
   }

   @Override
   public String getBookDescription() {
      return "Opens your personal ender chest inventory from anywhere.";
   }

   @Override
   public int getDefaultManaCost() {
      return 50;
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
}
