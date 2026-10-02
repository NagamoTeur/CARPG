package shadows.apotheosis.adventure.affix.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.StringUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.registries.ForgeRegistries;
import shadows.apotheosis.adventure.affix.Affix;
import shadows.apotheosis.adventure.affix.AffixType;
import shadows.apotheosis.adventure.loot.LootCategory;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.placebo.codec.EnumCodec;
import shadows.placebo.json.PSerializer;
import shadows.placebo.util.StepFunction;

public class PotionAffix extends Affix {
   public static final Codec<PotionAffix> CODEC = RecordCodecBuilder.create(
      inst -> inst.group(
               ForgeRegistries.MOB_EFFECTS.getCodec().fieldOf("mob_effect").forGetter(a -> a.effect),
               PotionAffix.Target.CODEC.fieldOf("target").forGetter(a -> a.target),
               LootRarity.mapCodec(PotionAffix.EffectData.CODEC).fieldOf("values").forGetter(a -> a.values),
               Codec.INT.optionalFieldOf("cooldown", 0).forGetter(a -> a.cooldown),
               LootCategory.SET_CODEC.fieldOf("types").forGetter(a -> a.types),
               Codec.BOOL.optionalFieldOf("stack_on_reapply", false).forGetter(a -> a.stackOnReapply)
            )
            .apply(inst, PotionAffix::new)
   );
   public static final PSerializer<PotionAffix> SERIALIZER = PSerializer.fromCodec("Potion Affix", CODEC);
   protected final MobEffect effect;
   protected final PotionAffix.Target target;
   protected final Map<LootRarity, PotionAffix.EffectData> values;
   @Deprecated(
      forRemoval = true,
      since = "6.3.0"
   )
   protected final int cooldown;
   protected final Set<LootCategory> types;
   protected final boolean stackOnReapply;

   public PotionAffix(
      MobEffect effect,
      PotionAffix.Target target,
      Map<LootRarity, PotionAffix.EffectData> values,
      int cooldown,
      Set<LootCategory> types,
      boolean stackOnReapply
   ) {
      super(AffixType.ABILITY);
      this.effect = effect;
      this.target = target;
      this.values = values;
      this.cooldown = cooldown;
      this.types = types;
      this.stackOnReapply = stackOnReapply;
   }

   @Override
   public void addInformation(ItemStack stack, LootRarity rarity, float level, Consumer<Component> list) {
      MobEffectInstance inst = this.values.get(rarity).build(this.effect, level);
      MutableComponent comp = this.target.toComponent(toComponent(inst));
      int cooldown = this.getCooldown(rarity);
      if (cooldown != 0) {
         Component cd = Component.m_237110_("affix.apotheosis.cooldown", new Object[]{StringUtil.m_14404_(cooldown)});
         comp = comp.m_130946_(" ").m_7220_(cd);
      }

      if (this.stackOnReapply) {
         comp = comp.m_130946_(" ").m_7220_(Component.m_237115_("affix.apotheosis.stacking"));
      }

      list.accept(comp);
   }

   @Override
   public boolean canApplyTo(ItemStack stack, LootCategory cat, LootRarity rarity) {
      return (this.types.isEmpty() || this.types.contains(cat)) && this.values.containsKey(rarity);
   }

   @Override
   public void doPostHurt(ItemStack stack, LootRarity rarity, float level, LivingEntity user, Entity attacker) {
      if (this.target == PotionAffix.Target.HURT_SELF) {
         this.applyEffect(user, rarity, level);
      } else if (this.target == PotionAffix.Target.HURT_ATTACKER && attacker instanceof LivingEntity tLiving) {
         this.applyEffect(tLiving, rarity, level);
      }
   }

   @Override
   public void doPostAttack(ItemStack stack, LootRarity rarity, float level, LivingEntity user, Entity target) {
      if (this.target == PotionAffix.Target.ATTACK_SELF) {
         this.applyEffect(user, rarity, level);
      } else if (this.target == PotionAffix.Target.ATTACK_TARGET && target instanceof LivingEntity tLiving) {
         this.applyEffect(tLiving, rarity, level);
      }
   }

   @Override
   public void onBlockBreak(ItemStack stack, LootRarity rarity, float level, Player player, LevelAccessor world, BlockPos pos, BlockState state) {
      if (this.target == PotionAffix.Target.BREAK_SELF) {
         this.applyEffect(player, rarity, level);
      }
   }

   @Override
   public void onArrowImpact(AbstractArrow arrow, LootRarity rarity, float level, HitResult res, Type type) {
      if (this.target == PotionAffix.Target.ARROW_SELF) {
         if (arrow.m_37282_() instanceof LivingEntity owner) {
            this.applyEffect(owner, rarity, level);
         }
      } else if (this.target == PotionAffix.Target.ARROW_TARGET && type == Type.ENTITY && ((EntityHitResult)res).m_82443_() instanceof LivingEntity target) {
         this.applyEffect(target, rarity, level);
      }
   }

   @Override
   public float onShieldBlock(ItemStack stack, LootRarity rarity, float level, LivingEntity entity, DamageSource source, float amount) {
      if (this.target == PotionAffix.Target.BLOCK_SELF) {
         this.applyEffect(entity, rarity, level);
      } else if (this.target == PotionAffix.Target.BLOCK_ATTACKER && source.m_7640_() instanceof LivingEntity target) {
         this.applyEffect(target, rarity, level);
      }

      return amount;
   }

   protected int getCooldown(LootRarity rarity) {
      PotionAffix.EffectData data = this.values.get(rarity);
      return data.cooldown != -1 ? data.cooldown : this.cooldown;
   }

   private void applyEffect(LivingEntity target, LootRarity rarity, float level) {
      if (!target.f_19853_.m_5776_()) {
         int cooldown = this.getCooldown(rarity);
         if (cooldown == 0 || !isOnCooldown(this.getId(), cooldown, target)) {
            PotionAffix.EffectData data = this.values.get(rarity);
            MobEffectInstance inst = target.m_21124_(this.effect);
            if (!this.stackOnReapply || inst == null) {
               target.m_7292_(data.build(this.effect, level));
            } else if (inst != null) {
               MobEffectInstance newInst = new MobEffectInstance(
                  this.effect, (int)Math.max((float)inst.m_19557_(), data.duration.get(level)), (int)((float)(inst.m_19564_() + 1) + data.amplifier.get(level))
               );
               target.m_7292_(newInst);
            }

            startCooldown(this.getId(), target);
         }
      }
   }

   public PSerializer<? extends Affix> getSerializer() {
      return SERIALIZER;
   }

   public static Component toComponent(MobEffectInstance inst) {
      MutableComponent mutablecomponent = Component.m_237115_(inst.m_19576_());
      MobEffect mobeffect = inst.m_19544_();
      if (inst.m_19564_() > 0) {
         mutablecomponent = Component.m_237110_(
            "potion.withAmplifier", new Object[]{mutablecomponent, Component.m_237115_("potion.potency." + inst.m_19564_())}
         );
      }

      if (inst.m_19557_() > 20) {
         mutablecomponent = Component.m_237110_("potion.withDuration", new Object[]{mutablecomponent, MobEffectUtil.m_19581_(inst, 1.0F)});
      }

      return mutablecomponent.m_130940_(mobeffect.m_19483_().m_19497_());
   }

   public static record EffectData(StepFunction duration, StepFunction amplifier, int cooldown) {
      private static Codec<PotionAffix.EffectData> CODEC = RecordCodecBuilder.create(
         inst -> inst.group(
                  StepFunction.CODEC.fieldOf("duration").forGetter(PotionAffix.EffectData::duration),
                  StepFunction.CODEC.fieldOf("amplifier").forGetter(PotionAffix.EffectData::amplifier),
                  Codec.INT.optionalFieldOf("cooldown", -1).forGetter(PotionAffix.EffectData::cooldown)
               )
               .apply(inst, PotionAffix.EffectData::new)
      );

      public MobEffectInstance build(MobEffect effect, float level) {
         return new MobEffectInstance(effect, this.duration.getInt(level), this.amplifier.getInt(level));
      }
   }

   public static enum Target {
      ATTACK_SELF("attack_self"),
      ATTACK_TARGET("attack_target"),
      HURT_SELF("hurt_self"),
      HURT_ATTACKER("hurt_attacker"),
      BREAK_SELF("break_self"),
      ARROW_SELF("arrow_self"),
      ARROW_TARGET("arrow_target"),
      BLOCK_SELF("block_self"),
      BLOCK_ATTACKER("block_attacker");

      public static final Codec<PotionAffix.Target> CODEC = new EnumCodec(PotionAffix.Target.class);
      private final String id;

      private Target(String id) {
         this.id = id;
      }

      public MutableComponent toComponent(Object... args) {
         return Component.m_237110_("affix.apotheosis.target." + this.id, args);
      }
   }
}
