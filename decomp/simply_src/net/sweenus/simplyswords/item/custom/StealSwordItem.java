package net.sweenus.simplyswords.item.custom;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.sweenus.simplyswords.config.SimplySwordsConfig;
import net.sweenus.simplyswords.item.UniqueSwordItem;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

public class StealSwordItem extends UniqueSwordItem {
   private static int stepMod = 0;

   public StealSwordItem(Tier toolMaterial, int attackDamage, float attackSpeed, Properties settings) {
      super(toolMaterial, attackDamage, attackSpeed, settings);
   }

   @Override
   public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      if (!attacker.f_19853_.m_5776_()) {
         ServerLevel sworld = (ServerLevel)attacker.f_19853_;
         int fhitchance = (int)SimplySwordsConfig.getFloatValue("steal_chance");
         int fduration = (int)SimplySwordsConfig.getFloatValue("steal_duration");
         attacker.m_20256_(attacker.m_20154_().m_82490_(1.0));
         attacker.f_19864_ = true;
         HelperMethods.playHitSounds(attacker, target);
         if (attacker.m_217043_().m_188503_(100) <= fhitchance) {
            int choose_sound = (int)(Math.random() * 30.0);
            if (choose_sound <= 10) {
               sworld.m_6269_(null, target, (SoundEvent)SoundRegistry.MAGIC_SWORD_ATTACK_WITH_BLOOD_01.get(), SoundSource.PLAYERS, 0.5F, 2.0F);
            }

            if (choose_sound <= 20 && choose_sound > 10) {
               sworld.m_6269_(null, target, (SoundEvent)SoundRegistry.MAGIC_SWORD_ATTACK_WITH_BLOOD_02.get(), SoundSource.PLAYERS, 0.5F, 2.0F);
            }

            if (choose_sound <= 30 && choose_sound > 20) {
               sworld.m_6269_(null, target, (SoundEvent)SoundRegistry.MAGIC_SWORD_ATTACK_WITH_BLOOD_03.get(), SoundSource.PLAYERS, 0.5F, 2.0F);
            }

            attacker.m_147207_(new MobEffectInstance(MobEffects.f_19598_, fduration, 2), attacker);
            target.m_147207_(new MobEffectInstance(MobEffects.f_19597_, fduration, 1), attacker);
            target.m_147207_(new MobEffectInstance(MobEffects.f_19619_, fduration, 1), attacker);
         }
      }

      return super.m_7579_(stack, target, attacker);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player user, InteractionHand hand) {
      if (!user.f_19853_.m_5776_()) {
         int sradius = (int)SimplySwordsConfig.getFloatValue("steal_radius");
         int vradius = (int)(SimplySwordsConfig.getFloatValue("steal_radius") / 2.0F);
         double x = user.m_20185_();
         double y = user.m_20186_();
         double z = user.m_20189_();
         ServerLevel sworld = (ServerLevel)user.f_19853_;
         AABB box = new AABB(x + (double)sradius, y + (double)vradius, z + (double)sradius, x - (double)sradius, y - (double)vradius, z - (double)sradius);

         for (Entity entities : sworld.m_6249_(user, box, EntitySelector.f_20403_)) {
            if (entities != null && entities instanceof LivingEntity) {
               LivingEntity le = (LivingEntity)entities;
               if (HelperMethods.checkFriendlyFire(le, user)) {
                  int iduration = (int)SimplySwordsConfig.getFloatValue("steal_invis_duration");
                  int bduration = (int)SimplySwordsConfig.getFloatValue("steal_blind_duration");
                  if (le.m_21023_(MobEffects.f_19597_) && le.m_21023_(MobEffects.f_19619_) && le.m_20270_(user) > 5.0F) {
                     le.m_147207_(new MobEffectInstance(MobEffects.f_19610_, bduration, 1), user);
                     user.m_20324_(le.m_20185_(), le.m_20186_(), le.m_20189_());
                     sworld.m_6269_(null, le, (SoundEvent)SoundRegistry.ELEMENTAL_SWORD_SCIFI_ATTACK_03.get(), SoundSource.PLAYERS, 0.3F, 1.5F);
                     le.m_6469_(DamageSource.f_146701_, 5.0F);
                     le.m_21195_(MobEffects.f_19597_);
                     le.m_21195_(MobEffects.f_19619_);
                  }

                  if (le.m_21023_(MobEffects.f_19597_) && le.m_21023_(MobEffects.f_19619_) && le.m_20270_(user) <= 5.0F) {
                     user.m_147207_(new MobEffectInstance(MobEffects.f_19609_, iduration, 1), user);
                     user.m_20256_(user.m_20154_().m_82490_(2.0));
                     user.f_19864_ = true;
                     sworld.m_6269_(null, entities, (SoundEvent)SoundRegistry.MAGIC_BOW_SHOOT_MISS_01.get(), SoundSource.PLAYERS, 0.3F, 1.5F);
                     le.m_21195_(MobEffects.f_19597_);
                     le.m_21195_(MobEffects.f_19619_);
                  }
               }
            }
         }
      }

      return super.m_7203_(world, user, hand);
   }

   @Override
   public void m_6883_(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
      if (stepMod > 0) {
         stepMod--;
      }

      if (stepMod <= 0) {
         stepMod = 7;
      }

      HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.f_123775_, ParticleTypes.f_123775_, ParticleTypes.f_123757_, true);
      super.m_6883_(stack, world, entity, slot, selected);
   }

   public boolean m_6813_(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity miner) {
      return false;
   }

   public Component m_7626_(ItemStack stack) {
      return Component.m_237115_(this.m_5671_(stack)).m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD, ChatFormatting.UNDERLINE});
   }

   @Override
   public void m_7373_(ItemStack itemStack, Level world, List<Component> tooltip, TooltipFlag tooltipContext) {
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.stealsworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD}));
      tooltip.add(Component.m_237115_("item.simplyswords.stealsworditem.tooltip2"));
      tooltip.add(Component.m_237115_("item.simplyswords.stealsworditem.tooltip3"));
      tooltip.add(Component.m_237115_("item.simplyswords.stealsworditem.tooltip4"));
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.onrightclick").m_130944_(new ChatFormatting[]{ChatFormatting.BOLD, ChatFormatting.GREEN}));
      tooltip.add(Component.m_237115_("item.simplyswords.stealsworditem.tooltip5"));
      tooltip.add(Component.m_237115_("item.simplyswords.stealsworditem.tooltip6"));
      tooltip.add(Component.m_237115_("item.simplyswords.stealsworditem.tooltip7"));
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.stealsworditem.tooltip8"));
      tooltip.add(Component.m_237115_("item.simplyswords.stealsworditem.tooltip9"));
      tooltip.add(Component.m_237115_("item.simplyswords.stealsworditem.tooltip10"));
      tooltip.add(Component.m_237113_(""));
      super.m_7373_(itemStack, world, tooltip, tooltipContext);
   }
}
