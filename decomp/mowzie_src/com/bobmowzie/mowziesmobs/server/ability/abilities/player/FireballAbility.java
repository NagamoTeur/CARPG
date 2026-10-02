package com.bobmowzie.mowziesmobs.server.ability.abilities.player;

import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.PlayerAbility;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.phys.Vec3;

public class FireballAbility extends PlayerAbility {
   public FireballAbility(AbilityType<Player, FireballAbility> abilityType, Player user) {
      super(
         abilityType,
         user,
         new AbilitySection[]{
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 20),
            new AbilitySection.AbilitySectionInstant(AbilitySection.AbilitySectionType.ACTIVE)
         },
         20
      );
   }

   @Override
   public void tick() {
      super.tick();
      if (this.getTicksInUse() == 20) {
         LivingEntity user = this.getUser();
         Vec3 lookVec = user.m_20154_();
         SmallFireball smallfireballentity = new SmallFireball(user.f_19853_, user, lookVec.f_82479_, lookVec.f_82480_, lookVec.f_82481_);
         smallfireballentity.m_6034_(smallfireballentity.m_20185_(), user.m_20227_(0.5) + 0.5, smallfireballentity.m_20189_());
         user.f_19853_.m_7967_(smallfireballentity);
      }
   }
}
