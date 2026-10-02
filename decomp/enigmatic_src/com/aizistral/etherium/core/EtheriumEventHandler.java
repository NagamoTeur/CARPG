package com.aizistral.etherium.core;

import com.aizistral.enigmaticlegacy.objects.Vector3;
import com.aizistral.etherium.items.EtheriumArmor;
import javax.annotation.Nullable;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer.Builder;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EtheriumEventHandler {
   private final IEtheriumConfig config;
   private final Item etheriumOre;

   public EtheriumEventHandler(IEtheriumConfig config, Item etheriumOre) {
      this.config = config;
      this.etheriumOre = etheriumOre;
   }

   @SubscribeEvent
   public void onEntityHurt(LivingHurtEvent event) {
      if (event.getEntity() instanceof Player player && event.getAmount() > 0.0F && EtheriumArmor.hasShield(player)) {
         if (event.getSource().m_7640_() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity)event.getSource().m_7639_();
            Vector3 vec = Vector3.fromEntityCenter(player).subtract(Vector3.fromEntityCenter(event.getSource().m_7639_())).normalize();
            attacker.m_147240_(0.75, vec.x, vec.z);
            player.f_19853_
               .m_5594_(null, player.m_20183_(), this.config.getShieldTriggerSound(), SoundSource.PLAYERS, 1.0F, 0.9F + (float)(Math.random() * 0.1));
            player.f_19853_
               .m_5594_(null, player.m_20183_(), this.config.getShieldTriggerSound(), SoundSource.PLAYERS, 1.0F, 0.9F + (float)(Math.random() * 0.1));
         }

         event.setAmount(event.getAmount() * this.config.getShieldReduction().asModifierInverted());
      }
   }

   @SubscribeEvent
   public void onEntityAttacked(LivingAttackEvent event) {
      if (!event.getEntity().f_19853_.f_46443_) {
         if (event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();
            if ((event.getSource().m_7640_() instanceof AbstractHurtingProjectile || event.getSource().m_7640_() instanceof AbstractArrow)
               && EtheriumArmor.hasShield(player)) {
               event.setCanceled(true);
               player.f_19853_
                  .m_5594_(null, player.m_20183_(), this.config.getShieldTriggerSound(), SoundSource.PLAYERS, 1.0F, 0.9F + (float)(Math.random() * 0.1));
            }
         }
      }
   }

   @SubscribeEvent(
      priority = EventPriority.LOWEST
   )
   public void onLootTablesLoaded(LootTableLoadEvent event) {
      if (this.config.isStandalone()) {
         if (event.getName().equals(BuiltInLootTables.f_78741_)) {
            LootPool epic = constructLootPool(
               "etherium",
               -11.0F,
               2.0F,
               LootItem.m_79579_(this.etheriumOre).m_79707_(60).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 2.0F)))
            );
            LootTable modified = event.getTable();
            modified.addPool(epic);
            event.setTable(modified);
         }
      }
   }

   private static LootPool constructLootPool(String poolName, float minRolls, float maxRolls, @Nullable Builder<?>... entries) {
      net.minecraft.world.level.storage.loot.LootPool.Builder poolBuilder = LootPool.m_79043_();
      poolBuilder.name(poolName);
      poolBuilder.m_165133_(UniformGenerator.m_165780_(minRolls, maxRolls));

      for (Builder<?> entry : entries) {
         if (entry != null) {
            poolBuilder.m_79076_(entry);
         }
      }

      return poolBuilder.m_79082_();
   }
}
