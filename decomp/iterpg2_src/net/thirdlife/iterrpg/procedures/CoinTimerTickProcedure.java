package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.Collectors;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.thirdlife.iterrpg.init.IterRpgModItems;
import net.thirdlife.iterrpg.init.IterRpgModParticleTypes;

public class CoinTimerTickProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         boolean agr = false;
         boolean share = false;
         double fromZ = 0.0;
         double fromX = 0.0;
         double fromY = 0.0;
         share = false;
         if (entity.getPersistentData().m_128459_("otcup") >= 1.0) {
            entity.getPersistentData().m_128347_("otcup", entity.getPersistentData().m_128459_("otcup") - 1.0);
         }

         if (entity.getPersistentData().m_128459_("war") >= 1.0) {
            entity.getPersistentData().m_128347_("war", entity.getPersistentData().m_128459_("war") - 1.0);
         }

         if (entity.getPersistentData().m_128459_("otcup") <= 9600.0 && entity.getPersistentData().m_128459_("war") <= 1.0) {
            Vec3 _center = new Vec3(x, y + (double)(entity.m_20206_() / 2.0F), z);

            for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_((double)entity.m_20206_() / 2.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if (entityiterator instanceof ItemEntity
                  && (entityiterator instanceof ItemEntity _itemEnt ? _itemEnt.m_32055_() : ItemStack.f_41583_).m_41720_() == IterRpgModItems.COIN.get()) {
                  if (world instanceof ServerLevel _level) {
                     _level.m_8767_(
                        (SimpleParticleType)IterRpgModParticleTypes.COIN_PARTICLE.get(),
                        entityiterator.m_20185_(),
                        entityiterator.m_20186_(),
                        entityiterator.m_20189_(),
                        8,
                        0.0,
                        0.0,
                        0.0,
                        0.25
                     );
                  }

                  (entityiterator instanceof ItemEntity _itemEntx ? _itemEntx.m_32055_() : ItemStack.f_41583_).m_41774_(1);
                  entity.getPersistentData()
                     .m_128347_("otcup", entity.getPersistentData().m_128459_("otcup") + (double)Mth.m_216271_(RandomSource.m_216327_(), 1200, 3000));
                  share = true;
               }
            }
         }

         if (share) {
            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiteratorx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(12.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if (entityiteratorx instanceof Player && entityiteratorx instanceof ServerPlayer _player) {
                  Advancement _adv = _player.f_8924_.m_129889_().m_136041_(new ResourceLocation("iter_rpg:life_tax"));
                  AdvancementProgress _ap = _player.m_8960_().m_135996_(_adv);
                  if (!_ap.m_8193_()) {
                     Iterator _iterator = _ap.m_8219_().iterator();

                     while (_iterator.hasNext()) {
                        _player.m_8960_().m_135988_(_adv, (String)_iterator.next());
                     }
                  }
               }
            }

            _center = new Vec3(x, y, z);

            for (Entity entityiteratorxx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(16.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if (entityiteratorxx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:goblins")))) {
                  entityiteratorxx.getPersistentData()
                     .m_128347_("otcup", entityiteratorxx.getPersistentData().m_128459_("otcup") + (double)Mth.m_216271_(RandomSource.m_216327_(), 1200, 3000));
               }
            }
         }
      }
   }
}
