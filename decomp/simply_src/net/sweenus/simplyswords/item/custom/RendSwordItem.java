package net.sweenus.simplyswords.item.custom;

import java.util.List;
import net.minecraft.ChatFormatting;
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
import net.minecraft.world.phys.AABB;
import net.sweenus.simplyswords.config.SimplySwordsConfig;
import net.sweenus.simplyswords.item.UniqueSwordItem;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

public class RendSwordItem extends UniqueSwordItem {
   private static int stepMod = 0;

   public RendSwordItem(Tier toolMaterial, int attackDamage, float attackSpeed, Properties settings) {
      super(toolMaterial, attackDamage, attackSpeed, settings);
   }

   @Override
   public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      if (!attacker.f_19853_.m_5776_()) {
         ServerLevel world = (ServerLevel)attacker.f_19853_;
         int fhitchance = (int)SimplySwordsConfig.getFloatValue("soulrend_chance");
         int fduration = (int)SimplySwordsConfig.getFloatValue("soulrend_duration");
         int maxstacks = (int)SimplySwordsConfig.getFloatValue("soulrend_max_stacks");
         if (attacker.m_217043_().m_188503_(100) <= fhitchance) {
            int choose_sound = (int)(Math.random() * 30.0);
            if (choose_sound <= 10) {
               world.m_6269_(null, target, (SoundEvent)SoundRegistry.DARK_SWORD_ATTACK_WITH_BLOOD_01.get(), SoundSource.PLAYERS, 0.4F, 1.5F);
            }

            if (choose_sound <= 20 && choose_sound > 10) {
               world.m_6269_(null, target, (SoundEvent)SoundRegistry.DARK_SWORD_ATTACK_WITH_BLOOD_02.get(), SoundSource.PLAYERS, 0.4F, 1.5F);
            }

            if (choose_sound <= 30 && choose_sound > 20) {
               world.m_6269_(null, target, (SoundEvent)SoundRegistry.DARK_SWORD_ATTACK_WITH_BLOOD_03.get(), SoundSource.PLAYERS, 0.4F, 1.5F);
            }

            if (target.m_21023_(MobEffects.f_19613_)) {
               int a = target.m_21124_(MobEffects.f_19613_).m_19564_() + 1;
               if (target.m_21124_(MobEffects.f_19613_).m_19564_() <= 0) {
                  target.m_147207_(new MobEffectInstance(MobEffects.f_19613_, fduration, a), attacker);
               }
            } else {
               target.m_147207_(new MobEffectInstance(MobEffects.f_19613_, fduration, 0), attacker);
            }

            if (target.m_21023_(MobEffects.f_19597_)) {
               int a = target.m_21124_(MobEffects.f_19597_).m_19564_() + 1;
               if (target.m_21124_(MobEffects.f_19597_).m_19564_() < maxstacks) {
                  target.m_147207_(new MobEffectInstance(MobEffects.f_19597_, fduration, a), attacker);
               }
            } else {
               target.m_147207_(new MobEffectInstance(MobEffects.f_19597_, fduration, 0), attacker);
            }
         }
      }

      return super.m_7579_(stack, target, attacker);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player user, InteractionHand hand) {
      if (!user.f_19853_.m_5776_()) {
         int rend_damage = (int)SimplySwordsConfig.getFloatValue("soulrend_rend_damage_multiplier");
         float heal_amount = SimplySwordsConfig.getFloatValue("soulrend_rend_heal_multiplier");
         int healamp = 0;
         boolean cantrigger = false;
         int hradius = (int)SimplySwordsConfig.getFloatValue("soulrend_radius");
         int vradius = (int)(SimplySwordsConfig.getFloatValue("soulrend_radius") / 2.0F);
         double x = user.m_20185_();
         double y = user.m_20186_();
         double z = user.m_20189_();
         ServerLevel sworld = (ServerLevel)user.f_19853_;
         AABB box = new AABB(x + (double)hradius, y + (double)vradius, z + (double)hradius, x - (double)hradius, y - (double)vradius, z - (double)hradius);

         for (Entity ee : sworld.m_6249_(user, box, EntitySelector.f_20403_)) {
            if (ee != null && ee instanceof LivingEntity) {
               LivingEntity le = (LivingEntity)ee;
               if (le.m_21023_(MobEffects.f_19597_) && le.m_21023_(MobEffects.f_19613_) && HelperMethods.checkFriendlyFire(le, user)) {
                  healamp += le.m_21124_(MobEffects.f_19597_).m_19564_();
                  cantrigger = true;
                  le.m_6469_(DamageSource.f_146701_, (float)(le.m_21124_(MobEffects.f_19597_).m_19564_() * rend_damage));
                  le.m_21195_(MobEffects.f_19613_);
                  le.m_21195_(MobEffects.f_19597_);
                  world.m_6269_(null, ee, (SoundEvent)SoundRegistry.DARK_SWORD_SPELL.get(), SoundSource.PLAYERS, 0.1F, 2.0F);
               }
            }
         }

         if (cantrigger) {
            healamp = (int)((float)healamp * heal_amount);
            if (healamp < 1) {
               healamp = 1;
            }

            if (healamp > 6) {
               healamp = 6;
            }

            user.m_5634_((float)healamp);
            int var22 = false;
            cantrigger = false;
         }
      }

      return super.m_7203_(world, user, hand);
   }

   public Component m_7626_(ItemStack stack) {
      return Component.m_237115_(this.m_5671_(stack)).m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD, ChatFormatting.UNDERLINE});
   }

   @Override
   public void m_6883_(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
      if (stepMod > 0) {
         stepMod--;
      }

      if (stepMod <= 0) {
         stepMod = 7;
      }

      HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.f_123746_, ParticleTypes.f_235898_, ParticleTypes.f_123785_, true);
      super.m_6883_(stack, world, entity, slot, selected);
   }

   @Override
   public void m_7373_(ItemStack itemStack, Level world, List<Component> tooltip, TooltipFlag tooltipContext) {
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.rendsworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD}));
      tooltip.add(Component.m_237115_("item.simplyswords.rendsworditem.tooltip2"));
      tooltip.add(Component.m_237115_("item.simplyswords.rendsworditem.tooltip3"));
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.onrightclick").m_130944_(new ChatFormatting[]{ChatFormatting.BOLD, ChatFormatting.GREEN}));
      tooltip.add(Component.m_237115_("item.simplyswords.rendsworditem.tooltip4"));
      tooltip.add(Component.m_237115_("item.simplyswords.rendsworditem.tooltip5"));
      tooltip.add(Component.m_237115_("item.simplyswords.rendsworditem.tooltip6"));
      super.m_7373_(itemStack, world, tooltip, tooltipContext);
   }
}
