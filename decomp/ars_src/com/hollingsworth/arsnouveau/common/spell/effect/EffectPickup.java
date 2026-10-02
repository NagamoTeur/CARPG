package com.hollingsworth.arsnouveau.common.spell.effect;

import com.hollingsworth.arsnouveau.api.item.inv.InventoryManager;
import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAOE;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent.PickupXp;
import org.jetbrains.annotations.NotNull;

public class EffectPickup extends AbstractEffect {
   public static EffectPickup INSTANCE = new EffectPickup();

   private EffectPickup() {
      super(GlyphLib.EffectPickupID, "Item Pickup");
   }

   @Override
   public void onResolve(
      HitResult rayTraceResult, Level world, @Nullable LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver
   ) {
      BlockPos pos = new BlockPos(rayTraceResult.m_82450_());
      double expansion = 2.0 + spellStats.getAoeMultiplier();
      Vec3 posVec = new Vec3((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_());
      List<ItemEntity> entityList = world.m_45976_(
         ItemEntity.class, new AABB(posVec.m_82520_(expansion, expansion, expansion), posVec.m_82492_(expansion, expansion, expansion))
      );
      InventoryManager manager = spellContext.getCaster().getInvManager().extractSlotMax(-1);

      for (ItemEntity i : entityList) {
         ItemStack stack = i.m_32055_();
         if (!stack.m_41619_() && !MinecraftForge.EVENT_BUS.post(new EntityItemPickupEvent(this.getPlayer(shooter, (ServerLevel)world), i))) {
            stack = manager.insertStack(stack);
            i.m_32045_(stack);
         }
      }

      for (ExperienceOrb ix : world.m_45976_(
         ExperienceOrb.class, new AABB(posVec.m_82520_(expansion, expansion, expansion), posVec.m_82492_(expansion, expansion, expansion))
      )) {
         if (shooter instanceof Player) {
            Player player = (Player)shooter;
            if (this.isNotFakePlayer(player) && spellContext.castingTile == null && !MinecraftForge.EVENT_BUS.post(new PickupXp(player, ix))) {
               player.m_6756_(ix.f_20770_);
               ix.m_142687_(RemovalReason.DISCARDED);
            }
         }
      }
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(new AbstractAugment[]{AugmentAOE.INSTANCE});
   }

   @Override
   public String getBookDescription() {
      return "Picks up nearby items in a medium radius where this spell is activated. The range may be expanded with AOE.";
   }

   @Override
   public int getDefaultManaCost() {
      return 10;
   }

   @NotNull
   @Override
   public Set<SpellSchool> getSchools() {
      return this.setOf(new SpellSchool[]{SpellSchools.MANIPULATION});
   }
}
