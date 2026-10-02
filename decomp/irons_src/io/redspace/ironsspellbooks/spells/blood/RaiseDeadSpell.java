package io.redspace.ironsspellbooks.spells.blood;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.entity.mobs.SummonedSkeleton;
import io.redspace.ironsspellbooks.entity.mobs.SummonedZombie;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import java.util.List;
import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

@AutoSpellConfig
public class RaiseDeadSpell extends AbstractSpell {
   private final ResourceLocation spellId = new ResourceLocation("irons_spellbooks", "raise_dead");
   private final DefaultConfig defaultConfig = new DefaultConfig()
      .setMinRarity(SpellRarity.UNCOMMON)
      .setSchoolResource(SchoolRegistry.BLOOD_RESOURCE)
      .setMaxLevel(6)
      .setCooldownSeconds(150.0)
      .build();

   @Override
   public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
      return List.of(Component.m_237110_("ui.irons_spellbooks.summon_count", new Object[]{spellLevel}));
   }

   public RaiseDeadSpell() {
      this.manaCostPerLevel = 10;
      this.baseSpellPower = 10;
      this.spellPowerPerLevel = 3;
      this.castTime = 30;
      this.baseManaCost = 50;
   }

   @Override
   public CastType getCastType() {
      return CastType.LONG;
   }

   @Override
   public DefaultConfig getDefaultConfig() {
      return this.defaultConfig;
   }

   @Override
   public ResourceLocation getSpellResource() {
      return this.spellId;
   }

   @Override
   public Optional<SoundEvent> getCastStartSound() {
      return Optional.of((SoundEvent)SoundRegistry.RAISE_DEAD_START.get());
   }

   @Override
   public Optional<SoundEvent> getCastFinishSound() {
      return Optional.of((SoundEvent)SoundRegistry.RAISE_DEAD_FINISH.get());
   }

   @Override
   public void onCast(Level world, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
      int summonTime = 12000;
      float radius = 1.5F + 0.185F * (float)spellLevel;

      for (int i = 0; i < spellLevel; i++) {
         boolean isSkeleton = Utils.random.m_188500_() < 0.3;
         ItemStack[] equipment = this.getEquipment(this.getSpellPower(spellLevel, entity), Utils.random);
         Monster undead = (Monster)(isSkeleton ? new SummonedSkeleton(world, entity, true) : new SummonedZombie(world, entity, true));
         undead.m_6518_((ServerLevel)world, world.m_6436_(undead.m_20097_()), MobSpawnType.MOB_SUMMONED, null, null);
         undead.m_7292_(new MobEffectInstance((MobEffect)MobEffectRegistry.RAISE_DEAD_TIMER.get(), summonTime, 0, false, false, false));
         this.equip(undead, equipment);
         float yrot = 6.281F / (float)spellLevel * (float)i + entity.m_146908_() * (float) (Math.PI / 180.0);
         Vec3 spawn = Utils.moveToRelativeGroundLevel(
            world, entity.m_146892_().m_82549_(new Vec3((double)(radius * Mth.m_14089_(yrot)), 0.0, (double)(radius * Mth.m_14031_(yrot)))), 10
         );
         undead.m_6034_(spawn.f_82479_, spawn.f_82480_, spawn.f_82481_);
         undead.m_146922_(entity.m_146908_());
         undead.m_146867_();
         world.m_7967_(undead);
      }

      int effectAmplifier = spellLevel - 1;
      if (entity.m_21023_((MobEffect)MobEffectRegistry.RAISE_DEAD_TIMER.get())) {
         effectAmplifier += entity.m_21124_((MobEffect)MobEffectRegistry.RAISE_DEAD_TIMER.get()).m_19564_() + 1;
      }

      entity.m_7292_(new MobEffectInstance((MobEffect)MobEffectRegistry.RAISE_DEAD_TIMER.get(), summonTime, effectAmplifier, false, false, true));
      super.onCast(world, spellLevel, entity, castSource, playerMagicData);
   }

   private void equip(Mob mob, ItemStack[] equipment) {
      mob.m_8061_(EquipmentSlot.FEET, equipment[0]);
      mob.m_8061_(EquipmentSlot.LEGS, equipment[1]);
      mob.m_8061_(EquipmentSlot.CHEST, equipment[2]);
      mob.m_8061_(EquipmentSlot.HEAD, equipment[3]);
      mob.m_21409_(EquipmentSlot.FEET, 0.0F);
      mob.m_21409_(EquipmentSlot.LEGS, 0.0F);
      mob.m_21409_(EquipmentSlot.CHEST, 0.0F);
      mob.m_21409_(EquipmentSlot.HEAD, 0.0F);
   }

   private ItemStack[] getEquipment(float power, RandomSource random) {
      Item[] leather = new Item[]{Items.f_42463_, Items.f_42462_, Items.f_42408_, Items.f_42407_};
      Item[] chain = new Item[]{Items.f_42467_, Items.f_42466_, Items.f_42465_, Items.f_42464_};
      Item[] iron = new Item[]{Items.f_42471_, Items.f_42470_, Items.f_42469_, Items.f_42468_};
      int minQuality = 12;
      int maxQuality = this.getMaxLevel() * this.spellPowerPerLevel + 15;
      ItemStack[] result = new ItemStack[4];

      for (int i = 0; i < 4; i++) {
         float quality = Mth.m_14036_((power + (float)random.m_216332_(-3, 8) - (float)minQuality) / (float)(maxQuality - minQuality), 0.0F, 0.95F);
         if (random.m_188500_() < (double)(quality * quality)) {
            if ((double)quality > 0.85) {
               result[i] = new ItemStack(iron[i]);
            } else if ((double)quality > 0.65) {
               result[i] = new ItemStack(chain[i]);
            } else if ((double)quality > 0.15) {
               result[i] = new ItemStack(leather[i]);
            } else {
               result[i] = ItemStack.f_41583_;
            }
         } else {
            result[i] = ItemStack.f_41583_;
         }
      }

      return result;
   }
}
