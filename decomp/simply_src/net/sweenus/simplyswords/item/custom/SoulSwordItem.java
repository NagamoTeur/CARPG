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

public class SoulSwordItem extends UniqueSwordItem {
   private static int stepMod = 0;

   public SoulSwordItem(Tier toolMaterial, int attackDamage, float attackSpeed, Properties settings) {
      super(toolMaterial, attackDamage, attackSpeed, settings);
   }

   @Override
   public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      if (!attacker.f_19853_.m_5776_()) {
         ServerLevel world = (ServerLevel)attacker.f_19853_;
         int fhitchance = (int)SimplySwordsConfig.getFloatValue("soulmeld_chance");
         int fduration = (int)SimplySwordsConfig.getFloatValue("soulmeld_duration");
         HelperMethods.playHitSounds(attacker, target);
         if (attacker.m_217043_().m_188503_(100) <= fhitchance) {
            if (attacker.m_21023_(MobEffects.f_19599_) && attacker.m_21023_(MobEffects.f_19606_)) {
               int a = attacker.m_21124_(MobEffects.f_19599_).m_19564_() + 1;
               if (attacker.m_21124_(MobEffects.f_19599_).m_19564_() <= 2) {
                  attacker.m_147207_(new MobEffectInstance(MobEffects.f_19599_, fduration, a), attacker);
                  attacker.m_147207_(new MobEffectInstance(MobEffects.f_19606_, fduration, a), attacker);
               }
            } else {
               attacker.m_147207_(new MobEffectInstance(MobEffects.f_19599_, fduration, 1), attacker);
               attacker.m_147207_(new MobEffectInstance(MobEffects.f_19606_, fduration, 1), attacker);
            }
         }
      }

      return super.m_7579_(stack, target, attacker);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player user, InteractionHand hand) {
      if (!user.f_19853_.m_5776_()) {
         int hradius = (int)SimplySwordsConfig.getFloatValue("soulmeld_radius");
         int vradius = (int)(SimplySwordsConfig.getFloatValue("soulmeld_radius") / 2.0F);
         double x = user.m_20185_();
         double y = user.m_20186_();
         double z = user.m_20189_();
         ServerLevel serverWorld = (ServerLevel)user.f_19853_;
         AABB box = new AABB(x + (double)hradius, y + (double)vradius, z + (double)hradius, x - (double)hradius, y - (double)vradius, z - (double)hradius);

         for (Entity ee : serverWorld.m_6249_(user, box, EntitySelector.f_20403_)) {
            if (ee != null && ee instanceof LivingEntity && user.m_21023_(MobEffects.f_19599_) && HelperMethods.checkFriendlyFire((LivingEntity)ee, user)) {
               LivingEntity le = (LivingEntity)ee;
               le.m_147207_(
                  new MobEffectInstance(MobEffects.f_19599_, user.m_21124_(MobEffects.f_19599_).m_19557_(), user.m_21124_(MobEffects.f_19599_).m_19564_()),
                  user
               );
               world.m_6269_(null, ee, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_SCIFI_SHOOT_IMPACT_03.get(), SoundSource.BLOCKS, 0.1F, 1.0F);
            }
         }

         if (user.m_21023_(MobEffects.f_19599_) && user.m_21023_(MobEffects.f_19606_)) {
            user.m_147207_(new MobEffectInstance(MobEffects.f_19605_, user.m_21124_(MobEffects.f_19599_).m_19564_() * 40, 2), user);
            user.m_21195_(MobEffects.f_19599_);
            user.m_21195_(MobEffects.f_19606_);
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

      HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.f_123746_, ParticleTypes.f_123746_, ParticleTypes.f_175833_, false);
      super.m_6883_(stack, world, entity, slot, selected);
   }

   public Component m_7626_(ItemStack stack) {
      return Component.m_237115_(this.m_5671_(stack)).m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD, ChatFormatting.UNDERLINE});
   }

   @Override
   public void m_7373_(ItemStack itemStack, Level world, List<Component> tooltip, TooltipFlag tooltipContext) {
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.soulsworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD}));
      tooltip.add(Component.m_237115_("item.simplyswords.soulsworditem.tooltip2"));
      tooltip.add(Component.m_237115_("item.simplyswords.soulsworditem.tooltip3"));
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.onrightclick").m_130944_(new ChatFormatting[]{ChatFormatting.BOLD, ChatFormatting.GREEN}));
      tooltip.add(Component.m_237115_("item.simplyswords.soulsworditem.tooltip4"));
      tooltip.add(Component.m_237115_("item.simplyswords.soulsworditem.tooltip5"));
      tooltip.add(Component.m_237115_("item.simplyswords.soulsworditem.tooltip6"));
      super.m_7373_(itemStack, world, tooltip, tooltipContext);
   }
}
