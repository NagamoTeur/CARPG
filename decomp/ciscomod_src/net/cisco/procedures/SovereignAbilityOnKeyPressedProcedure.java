package net.cisco.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.cisco.CiscoModMod;
import net.cisco.init.CiscoModModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class SovereignAbilityOnKeyPressedProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if ((entity instanceof LivingEntity _entGetArmorxxxxxxx ? _entGetArmorxxxxxxx.m_6844_(EquipmentSlot.HEAD) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.SOVEREIGN_ASCENDANT_HELMET.get()
            && (entity instanceof LivingEntity _entGetArmorxxxxxx ? _entGetArmorxxxxxx.m_6844_(EquipmentSlot.CHEST) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.SOVEREIGN_ASCENDANT_CHESTPLATE.get()
            && (entity instanceof LivingEntity _entGetArmorxxxxx ? _entGetArmorxxxxx.m_6844_(EquipmentSlot.LEGS) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.SOVEREIGN_ASCENDANT_LEGGINGS.get()
            && (entity instanceof LivingEntity _entGetArmorxxxx ? _entGetArmorxxxx.m_6844_(EquipmentSlot.FEET) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.SOVEREIGN_ASCENDANT_BOOTS.get()
            && !(entity instanceof LivingEntity _entGetArmorxxx ? _entGetArmorxxx.m_6844_(EquipmentSlot.HEAD) : ItemStack.f_41583_)
               .m_41784_()
               .m_128471_("sovereign1")
            && !(entity instanceof LivingEntity _entGetArmorxx ? _entGetArmorxx.m_6844_(EquipmentSlot.CHEST) : ItemStack.f_41583_)
               .m_41784_()
               .m_128471_("sovereign1")
            && !(entity instanceof LivingEntity _entGetArmorx ? _entGetArmorx.m_6844_(EquipmentSlot.LEGS) : ItemStack.f_41583_)
               .m_41784_()
               .m_128471_("sovereign1")
            && !(entity instanceof LivingEntity _entGetArmor ? _entGetArmor.m_6844_(EquipmentSlot.FEET) : ItemStack.f_41583_)
               .m_41784_()
               .m_128471_("sovereign1")) {
            (entity instanceof LivingEntity _entGetArmorxxxxxxxx ? _entGetArmorxxxxxxxx.m_6844_(EquipmentSlot.HEAD) : ItemStack.f_41583_)
               .m_41784_()
               .m_128379_("sovereign1", true);
            (entity instanceof LivingEntity _entGetArmorxxxxxxxxx ? _entGetArmorxxxxxxxxx.m_6844_(EquipmentSlot.CHEST) : ItemStack.f_41583_)
               .m_41784_()
               .m_128379_("sovereign1", true);
            (entity instanceof LivingEntity _entGetArmorxxxxxxxxxx ? _entGetArmorxxxxxxxxxx.m_6844_(EquipmentSlot.LEGS) : ItemStack.f_41583_)
               .m_41784_()
               .m_128379_("sovereign1", true);
            (entity instanceof LivingEntity _entGetArmorxxxxxxxxxxx ? _entGetArmorxxxxxxxxxxx.m_6844_(EquipmentSlot.FEET) : ItemStack.f_41583_)
               .m_41784_()
               .m_128379_("sovereign1", true);
            if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
               _entity.m_7292_(new MobEffectInstance(MobEffects.f_19603_, 420, 12));
            }

            if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
               _entity.m_7292_(new MobEffectInstance(MobEffects.f_19591_, 520, 11));
            }

            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(15.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if (entityiterator instanceof Mob && entityiterator instanceof LivingEntity) {
                  LivingEntity _entity = (LivingEntity)entityiterator;
                  if (!_entity.f_19853_.m_5776_()) {
                     _entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 320, 10, false, false));
                  }
               }
            }

            if (entity instanceof LivingEntity _entity) {
               _entity.m_21153_(entity instanceof LivingEntity _livEnt ? _livEnt.m_21233_() : -1.0F);
            }

            if (world instanceof Level _level) {
               if (!_level.m_5776_()) {
                  _level.m_5594_(
                     null,
                     new BlockPos(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("cisco_mod:sovereignactive")),
                     SoundSource.NEUTRAL,
                     1.0F,
                     1.0F
                  );
               } else {
                  _level.m_7785_(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("cisco_mod:sovereignactive")),
                     SoundSource.NEUTRAL,
                     1.0F,
                     1.0F,
                     false
                  );
               }
            }

            if (world instanceof ServerLevel _levelx) {
               _levelx.m_8767_(ParticleTypes.f_175830_, x, y, z, 70, 1.0, 1.0, 1.0, 0.3);
            }

            CiscoModMod.queueServerWork(
               420,
               () -> {
                  (entity instanceof LivingEntity _entGetArmorxxxxxxxxxxxx ? _entGetArmorxxxxxxxxxxxx.m_6844_(EquipmentSlot.HEAD) : ItemStack.f_41583_)
                     .m_41784_()
                     .m_128379_("sovereign1", false);
                  (entity instanceof LivingEntity _entGetArmorxxxxxxxxxxxxx ? _entGetArmorxxxxxxxxxxxxx.m_6844_(EquipmentSlot.CHEST) : ItemStack.f_41583_)
                     .m_41784_()
                     .m_128379_("sovereign1", false);
                  (entity instanceof LivingEntity _entGetArmorxxxxxxxxxxxxxx ? _entGetArmorxxxxxxxxxxxxxx.m_6844_(EquipmentSlot.LEGS) : ItemStack.f_41583_)
                     .m_41784_()
                     .m_128379_("sovereign1", false);
                  (entity instanceof LivingEntity _entGetArmorxxxxxxxxxxxxxxx ? _entGetArmorxxxxxxxxxxxxxxx.m_6844_(EquipmentSlot.FEET) : ItemStack.f_41583_)
                     .m_41784_()
                     .m_128379_("sovereign1", false);
               }
            );
         } else if ((entity instanceof LivingEntity _entGetArmorxxxxxxx ? _entGetArmorxxxxxxx.m_6844_(EquipmentSlot.HEAD) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.SOVEREIGN_ASCENDANT_HELMET.get()
            && (entity instanceof LivingEntity _entGetArmorxxxxxx ? _entGetArmorxxxxxx.m_6844_(EquipmentSlot.CHEST) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.SOVEREIGN_ASCENDANT_CHESTPLATE.get()
            && (entity instanceof LivingEntity _entGetArmorxxxxx ? _entGetArmorxxxxx.m_6844_(EquipmentSlot.LEGS) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.SOVEREIGN_ASCENDANT_LEGGINGS.get()
            && (entity instanceof LivingEntity _entGetArmorxxxx ? _entGetArmorxxxx.m_6844_(EquipmentSlot.FEET) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.SOVEREIGN_ASCENDANT_BOOTS.get()
            && (entity instanceof LivingEntity _entGetArmorxxx ? _entGetArmorxxx.m_6844_(EquipmentSlot.HEAD) : ItemStack.f_41583_)
               .m_41784_()
               .m_128471_("sovereign1")
            && (entity instanceof LivingEntity _entGetArmorxx ? _entGetArmorxx.m_6844_(EquipmentSlot.CHEST) : ItemStack.f_41583_)
               .m_41784_()
               .m_128471_("sovereign1")
            && (entity instanceof LivingEntity _entGetArmorx ? _entGetArmorx.m_6844_(EquipmentSlot.LEGS) : ItemStack.f_41583_)
               .m_41784_()
               .m_128471_("sovereign1")
            && (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.m_6844_(EquipmentSlot.FEET) : ItemStack.f_41583_).m_41784_().m_128471_("sovereign1")
            && entity instanceof Player _player
            && !_player.f_19853_.m_5776_()) {
            _player.m_5661_(Component.m_237113_("Time bends to my will however, patience is a virtue."), true);
         }
      }
   }
}
