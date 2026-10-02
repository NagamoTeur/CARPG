package com.hollingsworth.arsnouveau.common.ritual;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.api.ritual.AbstractRitual;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.lib.RitualLib;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.util.FakePlayer;

public class RitualHealing extends AbstractRitual {
   @Override
   protected void tick() {
      if (this.getWorld().f_46443_) {
         ParticleUtil.spawnRitualAreaEffect(this.getPos(), this.getWorld(), this.rand, this.getCenterColor(), 5);
      } else if (this.getWorld().m_46467_() % 100L == 0L) {
         List<LivingEntity> entities = this.getWorld().m_45976_(LivingEntity.class, new AABB(this.getPos()).m_82400_(5.0));
         Optional<LivingEntity> player = entities.stream().filter(e -> e instanceof Player).findFirst();
         boolean didWorkOnce = false;

         for (LivingEntity a : entities) {
            if (a instanceof ZombieVillager zv) {
               zv.m_34383_(player.<UUID>map(Entity::m_20148_).orElse(null), 0);
               didWorkOnce = true;
            } else if (a.m_21223_() < a.m_21233_() || a.m_21222_()) {
               if (a.m_21222_()) {
                  FakePlayer player1 = ANFakePlayer.getPlayer((ServerLevel)this.getWorld());
                  a.m_6469_(DamageSource.m_19344_(player1).m_19389_(), 10.0F);
               } else {
                  a.m_5634_(10.0F);
               }

               didWorkOnce = true;
            }
         }

         if (didWorkOnce) {
            this.setNeedsSource(true);
         }
      }
   }

   @Override
   public ResourceLocation getRegistryName() {
      return new ResourceLocation("ars_nouveau", RitualLib.RESTORATION);
   }

   @Override
   public ParticleColor getCenterColor() {
      return ParticleColor.makeRandomColor(20, 240, 240, this.rand);
   }

   @Override
   public String getLangName() {
      return "Restoration";
   }

   @Override
   public String getLangDescription() {
      return "Heals nearby entities or harms undead over time. Additionally, Zombie Villagers will be instantly cured, and the resulting villager will offer discounts if a player was nearby. This ritual requires source to operate.";
   }

   @Override
   public int getSourceCost() {
      return 200;
   }
}
