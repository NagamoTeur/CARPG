package net.thirdlife.iterrpg.procedures;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.init.IterRpgModItems;

@EventBusSubscriber
public class HumieButcherBonusDamageProcedure {
   @SubscribeEvent
   public static void onEntityAttacked(LivingAttackEvent event) {
      Entity entity = event.getEntity();
      if (event != null && entity != null) {
         execute(
            event, entity.m_9236_(), entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), entity, event.getSource().m_7639_(), (double)event.getAmount()
         );
      }
   }

   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity, double amount) {
      execute(null, world, x, y, z, entity, sourceentity, amount);
   }

   private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity, double amount) {
      if (entity != null && sourceentity != null) {
         if ((sourceentity instanceof LivingEntity _livEntx ? _livEntx.m_21205_() : ItemStack.f_41583_).m_41720_() == IterRpgModItems.HUMIE_BUTCHER.get()
            && (
               entity.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("forge:humanoid")))
                  || entity instanceof LivingEntity _livEnt && _livEnt.m_6336_() == MobType.f_21643_
                  || entity instanceof Zombie
                  || entity instanceof ZombieVillager
                  || entity instanceof Player
                  || entity instanceof Villager
            )) {
            if (world instanceof Level _level) {
               if (!_level.m_5776_()) {
                  _level.m_5594_(
                     null,
                     new BlockPos(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither.break_block")),
                     SoundSource.NEUTRAL,
                     0.1F,
                     2.0F
                  );
               } else {
                  _level.m_7785_(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither.break_block")),
                     SoundSource.NEUTRAL,
                     0.1F,
                     2.0F,
                     false
                  );
               }
            }

            if (world instanceof Level _levelx) {
               if (!_levelx.m_5776_()) {
                  _levelx.m_5594_(
                     null,
                     new BlockPos(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.slime_block.break")),
                     SoundSource.NEUTRAL,
                     1.25F,
                     1.6F
                  );
               } else {
                  _levelx.m_7785_(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.slime_block.break")),
                     SoundSource.NEUTRAL,
                     1.25F,
                     1.6F,
                     false
                  );
               }
            }

            if (world instanceof ServerLevel _levelxx) {
               _levelxx.m_8767_(
                  ParticleTypes.f_123797_,
                  x,
                  y + (double)(entity.m_20206_() / 2.0F),
                  z,
                  16,
                  (double)(entity.m_20205_() / 2.0F),
                  (double)(entity.m_20206_() / 2.0F),
                  (double)(entity.m_20205_() / 2.0F),
                  0.0
               );
            }

            if (entity instanceof LivingEntity _entity) {
               _entity.m_21153_((float)((double)(entity instanceof LivingEntity _livEntxx ? _livEntxx.m_21223_() : -1.0F) - amount * 0.25));
            }

            if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
               _entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 30, 0, false, true));
            }
         }
      }
   }
}
