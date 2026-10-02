package shadows.apotheosis.adventure.affix.socket.gem.bonus;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import net.minecraft.ChatFormatting;
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
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.adventure.affix.Affix;
import shadows.apotheosis.adventure.affix.effect.PotionAffix;
import shadows.apotheosis.adventure.affix.socket.gem.GemClass;
import shadows.apotheosis.adventure.loot.LootRarity;

public class PotionBonus extends GemBonus {
   public static final Codec<PotionBonus> CODEC = RecordCodecBuilder.create(
      inst -> inst.group(
               gemClass(),
               ForgeRegistries.MOB_EFFECTS.getCodec().fieldOf("mob_effect").forGetter(a -> a.effect),
               PotionAffix.Target.CODEC.fieldOf("target").forGetter(a -> a.target),
               LootRarity.mapCodec(PotionBonus.EffectData.CODEC).fieldOf("values").forGetter(a -> a.values),
               Codec.BOOL.optionalFieldOf("stack_on_reapply", false).forGetter(a -> a.stackOnReapply)
            )
            .apply(inst, PotionBonus::new)
   );
   protected final MobEffect effect;
   protected final PotionAffix.Target target;
   protected final Map<LootRarity, PotionBonus.EffectData> values;
   protected final boolean stackOnReapply;

   public PotionBonus(GemClass gemClass, MobEffect effect, PotionAffix.Target target, Map<LootRarity, PotionBonus.EffectData> values, boolean stackOnReapply) {
      super(Apotheosis.loc("mob_effect"), gemClass);
      this.effect = effect;
      this.target = target;
      this.values = values;
      this.stackOnReapply = stackOnReapply;
   }

   @Override
   public Component getSocketBonusTooltip(ItemStack gem, LootRarity rarity) {
      MobEffectInstance inst = this.values.get(rarity).build(this.effect);
      MutableComponent comp = this.target.toComponent(toComponent(inst)).m_130940_(ChatFormatting.YELLOW);
      int cooldown = this.getCooldown(rarity);
      if (cooldown != 0) {
         Component cd = Component.m_237110_("affix.apotheosis.cooldown", new Object[]{StringUtil.m_14404_(cooldown)});
         comp = comp.m_130946_(" ").m_7220_(cd);
      }

      if (this.stackOnReapply) {
         comp = comp.m_130946_(" ").m_7220_(Component.m_237115_("affix.apotheosis.stacking"));
      }

      return comp;
   }

   @Override
   public int getNumberOfUUIDs() {
      return 0;
   }

   public Codec<? extends GemBonus> getCodec() {
      return CODEC;
   }

   @Override
   public boolean supports(LootRarity rarity) {
      return this.values.containsKey(rarity);
   }

   @Override
   public void doPostHurt(ItemStack gem, LootRarity rarity, LivingEntity user, Entity attacker) {
      if (this.target == PotionAffix.Target.HURT_SELF) {
         this.applyEffect(gem, user, rarity);
      } else if (this.target == PotionAffix.Target.HURT_ATTACKER && attacker instanceof LivingEntity tLiving) {
         this.applyEffect(gem, tLiving, rarity);
      }
   }

   @Override
   public void doPostAttack(ItemStack gem, LootRarity rarity, LivingEntity user, Entity target) {
      if (this.target == PotionAffix.Target.ATTACK_SELF) {
         this.applyEffect(gem, user, rarity);
      } else if (this.target == PotionAffix.Target.ATTACK_TARGET && target instanceof LivingEntity tLiving) {
         this.applyEffect(gem, tLiving, rarity);
      }
   }

   @Override
   public void onBlockBreak(ItemStack gem, LootRarity rarity, Player player, LevelAccessor world, BlockPos pos, BlockState state) {
      if (this.target == PotionAffix.Target.BREAK_SELF) {
         this.applyEffect(gem, player, rarity);
      }
   }

   @Override
   public void onArrowImpact(ItemStack gemStack, LootRarity rarity, AbstractArrow arrow, HitResult res, Type type) {
      if (this.target == PotionAffix.Target.ARROW_SELF) {
         if (arrow.m_37282_() instanceof LivingEntity owner) {
            this.applyEffect(gemStack, owner, rarity);
         }
      } else if (this.target == PotionAffix.Target.ARROW_TARGET && type == Type.ENTITY && ((EntityHitResult)res).m_82443_() instanceof LivingEntity target) {
         this.applyEffect(gemStack, target, rarity);
      }
   }

   @Override
   public float onShieldBlock(ItemStack gem, LootRarity rarity, LivingEntity entity, DamageSource source, float amount) {
      if (this.target == PotionAffix.Target.BLOCK_SELF) {
         this.applyEffect(gem, entity, rarity);
      } else if (this.target == PotionAffix.Target.BLOCK_ATTACKER && source.m_7640_() instanceof LivingEntity target) {
         this.applyEffect(gem, target, rarity);
      }

      return amount;
   }

   protected int getCooldown(LootRarity rarity) {
      PotionBonus.EffectData data = this.values.get(rarity);
      return data.cooldown;
   }

   private void applyEffect(ItemStack gemStack, LivingEntity target, LootRarity rarity) {
      int cooldown = this.getCooldown(rarity);
      if (cooldown == 0 || !Affix.isOnCooldown(this.getCooldownId(gemStack), cooldown, target)) {
         PotionBonus.EffectData data = this.values.get(rarity);
         MobEffectInstance inst = target.m_21124_(this.effect);
         if (!this.stackOnReapply || inst == null) {
            target.m_7292_(data.build(this.effect));
         } else if (inst != null) {
            MobEffectInstance newInst = new MobEffectInstance(this.effect, Math.max(inst.m_19557_(), data.duration), inst.m_19564_() + 1 + data.amplifier);
            target.m_7292_(newInst);
         }

         Affix.startCooldown(this.getCooldownId(gemStack), target);
      }
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

   public PotionBonus validate() {
      Preconditions.checkNotNull(this.effect, "Null mob effect");
      Preconditions.checkNotNull(this.target, "Null target");
      Preconditions.checkNotNull(this.values, "Null values map");
      return this;
   }

   public static record EffectData(int duration, int amplifier, int cooldown) {
      private static Codec<PotionBonus.EffectData> CODEC = RecordCodecBuilder.create(
         inst -> inst.group(
                  Codec.INT.fieldOf("duration").forGetter(PotionBonus.EffectData::duration),
                  Codec.INT.fieldOf("amplifier").forGetter(PotionBonus.EffectData::amplifier),
                  Codec.INT.optionalFieldOf("cooldown", 0).forGetter(PotionBonus.EffectData::cooldown)
               )
               .apply(inst, PotionBonus.EffectData::new)
      );

      public MobEffectInstance build(MobEffect effect) {
         return new MobEffectInstance(effect, this.duration, this.amplifier);
      }
   }
}
