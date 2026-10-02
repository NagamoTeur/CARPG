package net.cisco.procedures;

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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class CrimsonAbilityActiveProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if ((entity instanceof LivingEntity _entGetArmorxxxxxxx ? _entGetArmorxxxxxxx.m_6844_(EquipmentSlot.HEAD) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.ASCENDED_HERO_ROUGE_HELMET.get()
            && (entity instanceof LivingEntity _entGetArmorxxxxxx ? _entGetArmorxxxxxx.m_6844_(EquipmentSlot.CHEST) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.ASCENDED_HERO_ROUGE_CHESTPLATE.get()
            && (entity instanceof LivingEntity _entGetArmorxxxxx ? _entGetArmorxxxxx.m_6844_(EquipmentSlot.LEGS) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.ASCENDED_HERO_ROUGE_LEGGINGS.get()
            && (entity instanceof LivingEntity _entGetArmorxxxx ? _entGetArmorxxxx.m_6844_(EquipmentSlot.FEET) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.ASCENDED_HERO_ROUGE_BOOTS.get()
            && !(entity instanceof LivingEntity _entGetArmorxxx ? _entGetArmorxxx.m_6844_(EquipmentSlot.HEAD) : ItemStack.f_41583_)
               .m_41784_()
               .m_128471_("crimson1")
            && !(entity instanceof LivingEntity _entGetArmorxx ? _entGetArmorxx.m_6844_(EquipmentSlot.CHEST) : ItemStack.f_41583_)
               .m_41784_()
               .m_128471_("crimson1")
            && !(entity instanceof LivingEntity _entGetArmorx ? _entGetArmorx.m_6844_(EquipmentSlot.LEGS) : ItemStack.f_41583_)
               .m_41784_()
               .m_128471_("crimson1")
            && !(entity instanceof LivingEntity _entGetArmor ? _entGetArmor.m_6844_(EquipmentSlot.FEET) : ItemStack.f_41583_).m_41784_().m_128471_("crimson1")) {
            if (world instanceof Level _level) {
               if (!_level.m_5776_()) {
                  _level.m_5594_(
                     null,
                     new BlockPos(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("cisco_mod:lightability")),
                     SoundSource.NEUTRAL,
                     1.0F,
                     1.0F
                  );
               } else {
                  _level.m_7785_(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("cisco_mod:lightability")),
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

            if (!world.m_6443_(Player.class, AABB.m_165882_(new Vec3(x, y, z), 20.0, 20.0, 20.0), e -> true).isEmpty()) {
               if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                  _entity.m_7292_(new MobEffectInstance(MobEffects.f_19606_, 320, 3, false, false));
               }

               (entity instanceof LivingEntity _entGetArmorxxxxxxxx ? _entGetArmorxxxxxxxx.m_6844_(EquipmentSlot.HEAD) : ItemStack.f_41583_)
                  .m_41784_()
                  .m_128379_("crimson1", true);
               (entity instanceof LivingEntity _entGetArmorxxxxxxxxx ? _entGetArmorxxxxxxxxx.m_6844_(EquipmentSlot.CHEST) : ItemStack.f_41583_)
                  .m_41784_()
                  .m_128379_("crimson1", true);
               (entity instanceof LivingEntity _entGetArmorxxxxxxxxxx ? _entGetArmorxxxxxxxxxx.m_6844_(EquipmentSlot.LEGS) : ItemStack.f_41583_)
                  .m_41784_()
                  .m_128379_("crimson1", true);
               (entity instanceof LivingEntity _entGetArmorxxxxxxxxxxx ? _entGetArmorxxxxxxxxxxx.m_6844_(EquipmentSlot.FEET) : ItemStack.f_41583_)
                  .m_41784_()
                  .m_128379_("crimson1", true);
               CiscoModMod.queueServerWork(
                  420,
                  () -> {
                     (entity instanceof LivingEntity _entGetArmorxxxxxxxxxxxx ? _entGetArmorxxxxxxxxxxxx.m_6844_(EquipmentSlot.HEAD) : ItemStack.f_41583_)
                        .m_41784_()
                        .m_128379_("crimson1", false);
                     (entity instanceof LivingEntity _entGetArmorxxxxxxxxxxxxx ? _entGetArmorxxxxxxxxxxxxx.m_6844_(EquipmentSlot.CHEST) : ItemStack.f_41583_)
                        .m_41784_()
                        .m_128379_("crimson1", false);
                     (entity instanceof LivingEntity _entGetArmorxxxxxxxxxxxxxx ? _entGetArmorxxxxxxxxxxxxxx.m_6844_(EquipmentSlot.LEGS) : ItemStack.f_41583_)
                        .m_41784_()
                        .m_128379_("crimson1", false);
                     (entity instanceof LivingEntity _entGetArmorxxxxxxxxxxxxxxx ? _entGetArmorxxxxxxxxxxxxxxx.m_6844_(EquipmentSlot.FEET) : ItemStack.f_41583_)
                        .m_41784_()
                        .m_128379_("crimson1", false);
                  }
               );
            }
         } else if ((entity instanceof LivingEntity _entGetArmorxxxxxxx ? _entGetArmorxxxxxxx.m_6844_(EquipmentSlot.HEAD) : ItemStack.f_41583_)
               .m_41784_()
               .m_128471_("crimson1")
            && (entity instanceof LivingEntity _entGetArmorxxxxxx ? _entGetArmorxxxxxx.m_6844_(EquipmentSlot.CHEST) : ItemStack.f_41583_)
               .m_41784_()
               .m_128471_("crimson1")
            && (entity instanceof LivingEntity _entGetArmorxxxxx ? _entGetArmorxxxxx.m_6844_(EquipmentSlot.LEGS) : ItemStack.f_41583_)
               .m_41784_()
               .m_128471_("crimson1")
            && (entity instanceof LivingEntity _entGetArmorxxxx ? _entGetArmorxxxx.m_6844_(EquipmentSlot.FEET) : ItemStack.f_41583_)
               .m_41784_()
               .m_128471_("crimson1")
            && (entity instanceof LivingEntity _entGetArmorxxx ? _entGetArmorxxx.m_6844_(EquipmentSlot.HEAD) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.ASCENDED_HERO_ROUGE_HELMET.get()
            && (entity instanceof LivingEntity _entGetArmorxx ? _entGetArmorxx.m_6844_(EquipmentSlot.CHEST) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.ASCENDED_HERO_ROUGE_CHESTPLATE.get()
            && (entity instanceof LivingEntity _entGetArmorx ? _entGetArmorx.m_6844_(EquipmentSlot.LEGS) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.ASCENDED_HERO_ROUGE_LEGGINGS.get()
            && (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.m_6844_(EquipmentSlot.FEET) : ItemStack.f_41583_).m_41720_()
               == CiscoModModItems.ASCENDED_HERO_ROUGE_BOOTS.get()
            && entity instanceof Player _player
            && !_player.f_19853_.m_5776_()) {
            _player.m_5661_(Component.m_237113_("My light needs time to recharge."), true);
         }
      }
   }
}
