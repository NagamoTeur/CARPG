package net.thirdlife.iterrpg.procedures;

import java.util.Iterator;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class EnemyExpandedGrantProcedure {
   public static void execute(Entity sourceentity) {
      if (sourceentity != null) {
         if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.m_21205_() : ItemStack.f_41583_)
               .m_204117_(ItemTags.create(new ResourceLocation("iter_rpg:ocean_set")))
            && sourceentity instanceof ServerPlayer _player) {
            Advancement _adv = _player.f_8924_.m_129889_().m_136041_(new ResourceLocation("iter_rpg:enemy_expanded"));
            AdvancementProgress _ap = _player.m_8960_().m_135996_(_adv);
            if (!_ap.m_8193_()) {
               Iterator _iterator = _ap.m_8219_().iterator();

               while (_iterator.hasNext()) {
                  _player.m_8960_().m_135988_(_adv, (String)_iterator.next());
               }
            }
         }
      }
   }
}
