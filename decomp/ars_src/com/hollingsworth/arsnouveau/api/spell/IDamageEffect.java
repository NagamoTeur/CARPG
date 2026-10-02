package com.hollingsworth.arsnouveau.api.spell;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.api.event.SpellDamageEvent;
import com.hollingsworth.arsnouveau.api.util.LootUtil;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentFortune;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentRandomize;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;

public interface IDamageEffect {
   default boolean canDamage(LivingEntity shooter, SpellStats stats, SpellContext spellContext, SpellResolver resolver, @NotNull Entity entity) {
      return (!(entity instanceof LivingEntity living) || !(living.m_21223_() <= 0.0F)) && !entity.m_7307_(shooter);
   }

   default void attemptDamage(
      Level world,
      @NotNull LivingEntity shooter,
      SpellStats stats,
      SpellContext spellContext,
      SpellResolver resolver,
      Entity entity,
      DamageSource source,
      float baseDamage
   ) {
      if (this.canDamage(shooter, stats, spellContext, resolver, entity)) {
         ServerLevel server = (ServerLevel)world;
         float totalDamage = (float)((double)baseDamage + stats.getDamageModifier());
         if (stats.isRandomized()) {
            totalDamage += (float)(stats.getBuffCount(AugmentRandomize.INSTANCE) * server.f_46441_.m_216332_(-1, 1));
         }

         SpellDamageEvent.Pre preDamage = new SpellDamageEvent.Pre(source, shooter, entity, totalDamage, spellContext);
         MinecraftForge.EVENT_BUS.post(preDamage);
         source = preDamage.damageSource;
         totalDamage = preDamage.damage;
         if (!(totalDamage <= 0.0F) && !preDamage.isCanceled()) {
            if (entity.m_6469_(source, totalDamage)) {
               shooter.m_21335_(entity);
               SpellDamageEvent.Post postDamage = new SpellDamageEvent.Post(source, shooter, entity, totalDamage, spellContext);
               MinecraftForge.EVENT_BUS.post(postDamage);
               if (entity instanceof LivingEntity mob && mob.m_21223_() <= 0.0F && !mob.m_213877_() && stats.hasBuff(AugmentFortune.INSTANCE)) {
                  Player playerContext = (Player)(shooter instanceof Player player ? player : ANFakePlayer.getPlayer(server));
                  int looting = stats.getBuffCount(AugmentFortune.INSTANCE);
                  Builder lootContext = LootUtil.getLootingContext(server, shooter, mob, looting, DamageSource.m_19344_(playerContext));
                  ResourceLocation lootTable = mob.m_5743_();
                  LootTable loottable = server.m_7654_().m_129898_().m_79217_(lootTable);
                  List<ItemStack> items = loottable.m_230922_(lootContext.m_78975_(LootContextParamSets.f_81415_));
                  items.forEach(mob::m_19983_);
               }
            }
         }
      }
   }

   default DamageSource buildDamageSource(Level world, LivingEntity shooter) {
      LivingEntity var3 = !(shooter instanceof Player) ? ANFakePlayer.getPlayer((ServerLevel)world) : shooter;
      return DamageSource.m_19344_((Player)var3);
   }
}
