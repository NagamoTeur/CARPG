package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.api.generic.SubscribeConfig;
import com.aizistral.enigmaticlegacy.api.items.ICursed;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBase;
import com.aizistral.enigmaticlegacy.objects.Vector3;
import com.aizistral.enigmaticlegacy.packets.clients.PacketGenericParticleEffect;
import com.aizistral.omniconfig.wrappers.Omniconfig;
import com.aizistral.omniconfig.wrappers.OmniconfigWrapper;
import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.monster.piglin.PiglinBruteAi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PacketDistributor.TargetPoint;

public class GuardianHeart extends ItemBase implements ICursed, Vanishable {
   public static final List<Class<? extends LivingEntity>> excludedMobs = Lists.newArrayList(new Class[]{AbstractPiglin.class, Guardian.class});
   public static Omniconfig.IntParameter abilityRange;
   public static Omniconfig.IntParameter enrageRange;
   public static Omniconfig.IntParameter abilityCooldown;

   @SubscribeConfig
   public static void onConfig(OmniconfigWrapper builder) {
      builder.pushPrefix("GuardianHeart");
      abilityRange = builder.comment("Range of active/passive abilities of Heart of the Guardian.").getInt("AbilityRange", 24);
      enrageRange = builder.comment(
            "Range in which monster enraged by active ability of Heart of the Guardian will seek new target. Monsters within this range will target enraged monster back."
         )
         .getInt("EnrageRange", 12);
      abilityCooldown = builder.comment("Cooldown of enraging ability of Heart of the Guardian. Measured in ticks.").getInt("ActiveAbilityCooldown", 200);
      builder.popPrefix();
   }

   public GuardianHeart() {
      super(getDefaultProperties().m_41487_(1).m_41497_(Rarity.EPIC).m_41486_());
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, Level worldIn, List<Component> list, TooltipFlag flagIn) {
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.guardianHeart1", ChatFormatting.GOLD, abilityRange);
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.guardianHeart2");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.guardianHeart3");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.guardianHeart4");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.guardianHeart5", ChatFormatting.GOLD, abilityRange);
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.guardianHeart6");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.guardianHeart7");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.guardianHeart8", ChatFormatting.GOLD, enrageRange);
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.guardianHeart9");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.guardianHeart10");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.guardianHeart11");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.guardianHeart12", ChatFormatting.GOLD, (double)abilityCooldown.getValue() / 20.0);
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }

      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      ItemLoreHelper.indicateCursedOnesOnly(list);
   }

   public void m_6883_(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected) {
      if (entity instanceof Player && !world.f_46443_) {
         Player player = (Player)entity;
         List<Monster> genericMobs = player.f_19853_
            .m_45976_(Monster.class, SuperpositionHandler.getBoundingBoxAroundEntity(player, (double)abilityRange.getValue()));
         if (SuperpositionHandler.isTheCursedOne(player) && Inventory.m_36045_(itemSlot) && !player.m_36335_().m_41519_(this)) {
            Monster oneWatched = null;

            for (Monster monster : genericMobs) {
               if (SuperpositionHandler.doesObserveEntity(player, monster) && !this.isExcluded(monster)) {
                  oneWatched = monster;
                  break;
               }
            }

            if (oneWatched != null && oneWatched.m_6084_()) {
               Monster theOne = oneWatched;
               Vector3 vec = Vector3.fromEntityCenter(theOne);
               List<Monster> surroundingMobs = player.f_19853_
                  .m_6443_(
                     Monster.class,
                     SuperpositionHandler.getBoundingBoxAroundEntity(theOne, (double)enrageRange.getValue()),
                     living -> living.m_6084_() && theOne.m_142582_(living)
                  );
               Monster closestMonster = SuperpositionHandler.getClosestEntity(surroundingMobs, monsterx -> monsterx != theOne, vec.x, vec.y, vec.z);
               if (closestMonster != null) {
                  this.setAttackTarget(theOne, closestMonster);
                  theOne.m_7292_(new MobEffectInstance(MobEffects.f_19610_, 300, 0, false, true));
                  theOne.m_7292_(new MobEffectInstance(MobEffects.f_19600_, 300, 1, false, false));
                  theOne.m_7292_(new MobEffectInstance(MobEffects.f_19606_, 300, 1, false, false));

                  for (Monster otherMonster : surroundingMobs) {
                     this.setAttackTarget(otherMonster, theOne);
                  }

                  player.f_19853_.m_6263_(null, player.m_20185_(), player.m_20186_(), player.m_20189_(), SoundEvents.f_11880_, SoundSource.HOSTILE, 1.0F, 1.0F);
                  if (player instanceof ServerPlayer serverPlayer) {
                     EnigmaticLegacy.packetInstance
                        .send(
                           PacketDistributor.NEAR
                              .with(() -> new TargetPoint(theOne.m_20185_(), theOne.m_20186_(), theOne.m_20189_(), 64.0, theOne.f_19853_.m_46472_())),
                           new PacketGenericParticleEffect(
                              theOne.m_20185_(), theOne.m_20188_(), theOne.m_20189_(), 0, false, PacketGenericParticleEffect.Effect.GUARDIAN_CURSE
                           )
                        );
                  }

                  player.m_36335_().m_41524_(this, abilityCooldown.getValue());
               }
            }
         }

         for (Monster monsterx : genericMobs) {
            if (monsterx instanceof Guardian && monsterx.getClass() != ElderGuardian.class) {
               Guardian guardian = (Guardian)monsterx;
               if (guardian.m_5448_() == null) {
                  List<Monster> surroundingMobs = player.f_19853_
                     .m_6443_(
                        Monster.class,
                        SuperpositionHandler.getBoundingBoxAroundEntity(guardian, 12.0),
                        living -> living.m_6084_() && guardian.m_142582_(living)
                     );
                  Monster closestMonster = SuperpositionHandler.getClosestEntity(
                     surroundingMobs, checked -> !(checked instanceof Guardian), guardian.m_20185_(), guardian.m_20186_(), guardian.m_20189_()
                  );
                  if (closestMonster != null) {
                     this.setAttackTarget(guardian, closestMonster);
                  }
               }
            }
         }
      }
   }

   private boolean isExcluded(LivingEntity entity) {
      boolean excluded = false;

      for (Class<? extends LivingEntity> excludedClass : excludedMobs) {
         if (excludedClass.isInstance(entity)) {
            excluded = true;
         }
      }

      return excluded;
   }

   private void setAttackTarget(Monster monster, Monster otherMonster) {
      if (monster != null && otherMonster != null && monster != otherMonster) {
         if (monster instanceof AbstractPiglin) {
            ((AbstractPiglin)monster).m_6274_().m_21879_(MemoryModuleType.f_26372_, otherMonster);
            monster.m_6710_(otherMonster);
            monster.m_6703_(otherMonster);
            if (monster instanceof PiglinBrute) {
               PiglinBruteAi.m_35096_((PiglinBrute)monster, otherMonster);
            } else if (monster instanceof Piglin) {
               PiglinAi.m_34837_((Piglin)monster, otherMonster);
            }
         } else if (monster instanceof NeutralMob) {
            ((NeutralMob)monster).m_6710_(otherMonster);
         } else {
            monster.m_6710_(otherMonster);
            monster.m_6703_(otherMonster);
         }
      }
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
      return super.m_7203_(worldIn, playerIn, handIn);
   }
}
