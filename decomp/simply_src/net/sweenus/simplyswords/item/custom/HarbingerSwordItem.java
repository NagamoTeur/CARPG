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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.sweenus.simplyswords.config.SimplySwordsConfig;
import net.sweenus.simplyswords.entity.BattleStandardDarkEntity;
import net.sweenus.simplyswords.item.UniqueSwordItem;
import net.sweenus.simplyswords.registry.EntityRegistry;
import net.sweenus.simplyswords.registry.SoundRegistry;
import net.sweenus.simplyswords.util.HelperMethods;

public class HarbingerSwordItem extends UniqueSwordItem {
   private static int stepMod = 0;
   int skillCooldown = (int)SimplySwordsConfig.getFloatValue("abyssalstandard_cooldown");
   int abilityChance = (int)SimplySwordsConfig.getFloatValue("abyssalstandard_chance");

   public HarbingerSwordItem(Tier toolMaterial, int attackDamage, float attackSpeed, Properties settings) {
      super(toolMaterial, attackDamage, attackSpeed, settings);
   }

   @Override
   public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      HelperMethods.playHitSounds(attacker, target);
      if (!attacker.f_19853_.m_5776_() && attacker.m_217043_().m_188503_(100) <= this.abilityChance && attacker instanceof Player player) {
         attacker.f_19853_.m_6269_(null, attacker, (SoundEvent)SoundRegistry.MAGIC_SWORD_SPELL_02.get(), SoundSource.PLAYERS, 0.3F, 1.6F);
         target.m_147207_(new MobEffectInstance(MobEffects.f_19613_, 160, 0), attacker);
      }

      return super.m_7579_(stack, target, attacker);
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player user, InteractionHand hand) {
      ItemStack itemStack = user.m_21120_(hand);
      if (!user.f_19853_.m_5776_()) {
         ServerLevel serverWorld = (ServerLevel)user.f_19853_;
         BlockState currentState = world.m_8055_(user.m_20183_().m_6630_(4).m_5484_(user.m_6374_(), 3));
         BlockState state = Blocks.f_50016_.m_49966_();
         if (currentState == state) {
            world.m_6269_(null, user, (SoundEvent)SoundRegistry.DARK_SWORD_ATTACK_WITH_BLOOD_02.get(), SoundSource.PLAYERS, 0.4F, 0.8F);
            BattleStandardDarkEntity banner = (BattleStandardDarkEntity)((EntityType)EntityRegistry.BATTLESTANDARDDARK.get())
               .m_20600_(
                  serverWorld,
                  null,
                  Component.m_237110_("entity.simplyswords.battlestandard.name", new Object[]{user.m_7755_()}),
                  user,
                  user.m_20183_().m_6630_(4).m_5484_(user.m_6374_(), 3),
                  MobSpawnType.MOB_SUMMONED,
                  true,
                  true
               );
            if (banner != null) {
               banner.m_20334_(0.0, -1.0, 0.0);
               banner.ownerEntity = user;
               banner.decayRate = 3;
               banner.standardType = "harbinger";
            }

            user.m_36335_().m_41524_(this.m_7968_().m_41720_(), this.skillCooldown);
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

      HelperMethods.createFootfalls(entity, stack, world, stepMod, ParticleTypes.f_123757_, ParticleTypes.f_123757_, ParticleTypes.f_123757_, true);
      super.m_6883_(stack, world, entity, slot, selected);
   }

   public Component m_7626_(ItemStack stack) {
      return Component.m_237115_(this.m_5671_(stack)).m_130944_(new ChatFormatting[]{ChatFormatting.DARK_RED, ChatFormatting.BOLD, ChatFormatting.UNDERLINE});
   }

   @Override
   public void m_7373_(ItemStack itemStack, Level world, List<Component> tooltip, TooltipFlag tooltipContext) {
      tooltip.add(Component.m_237113_(""));
      tooltip.add(
         Component.m_237115_("item.simplyswords.harbingersworditem.tooltip1").m_130944_(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD})
      );
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.harbingersworditem.tooltip2"));
      tooltip.add(Component.m_237113_(""));
      tooltip.add(Component.m_237115_("item.simplyswords.onrightclick").m_130944_(new ChatFormatting[]{ChatFormatting.BOLD, ChatFormatting.GREEN}));
      tooltip.add(Component.m_237115_("item.simplyswords.harbingersworditem.tooltip3"));
      tooltip.add(Component.m_237115_("item.simplyswords.harbingersworditem.tooltip4"));
      tooltip.add(Component.m_237115_("item.simplyswords.harbingersworditem.tooltip5"));
      tooltip.add(Component.m_237115_("item.simplyswords.harbingersworditem.tooltip6"));
      super.m_7373_(itemStack, world, tooltip, tooltipContext);
   }
}
