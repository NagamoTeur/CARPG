package shadows.apotheosis.adventure.affix.effect;

import com.google.common.base.Predicate;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.adventure.AdventureConfig;
import shadows.apotheosis.adventure.affix.Affix;
import shadows.apotheosis.adventure.affix.AffixType;
import shadows.apotheosis.adventure.loot.LootCategory;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.placebo.json.PSerializer;
import shadows.placebo.util.StepFunction;

public class CleavingAffix extends Affix {
   public static final Codec<CleavingAffix> CODEC = RecordCodecBuilder.create(
      inst -> inst.group(LootRarity.mapCodec(CleavingAffix.CleaveValues.CODEC).fieldOf("values").forGetter(a -> a.values)).apply(inst, CleavingAffix::new)
   );
   public static final PSerializer<CleavingAffix> SERIALIZER = PSerializer.fromCodec("Cleaving Affix", CODEC);
   protected final Map<LootRarity, CleavingAffix.CleaveValues> values;
   private static boolean cleaving = false;

   public CleavingAffix(Map<LootRarity, CleavingAffix.CleaveValues> values) {
      super(AffixType.ABILITY);
      this.values = values;
   }

   @Override
   public boolean canApplyTo(ItemStack stack, LootCategory cat, LootRarity rarity) {
      return cat == LootCategory.HEAVY_WEAPON && this.values.containsKey(rarity);
   }

   @Override
   public void addInformation(ItemStack stack, LootRarity rarity, float level, Consumer<Component> list) {
      list.accept(
         Component.m_237110_(
               "affix." + this.getId() + ".desc",
               new Object[]{ItemStack.f_41584_.format((double)(100.0F * this.getChance(rarity, level))), this.getTargets(rarity, level)}
            )
            .m_130940_(ChatFormatting.YELLOW)
      );
   }

   private float getChance(LootRarity rarity, float level) {
      return this.values.get(rarity).chance.get(level);
   }

   private int getTargets(LootRarity rarity, float level) {
      level %= 0.5F;
      level *= 2.0F;
      return (int)this.values.get(rarity).targets.get(level);
   }

   @Override
   public void doPostAttack(ItemStack stack, LootRarity rarity, float level, LivingEntity user, Entity target) {
      if ((double)Apotheosis.getLocalAtkStrength(user) >= 0.98 && !cleaving && !user.f_19853_.f_46443_) {
         cleaving = true;
         float chance = this.getChance(rarity, level);
         int targets = this.getTargets(rarity, level);
         if (user.f_19853_.f_46441_.m_188501_() < chance && user instanceof Player player) {
            for (Entity e : target.f_19853_.m_6249_(target, new AABB(target.m_20183_()).m_82400_(6.0), cleavePredicate(user, target))) {
               if (targets > 0) {
                  user.f_20922_ = 300;
                  player.m_5706_(e);
                  targets--;
               }
            }
         }

         cleaving = false;
      }
   }

   public PSerializer<? extends Affix> getSerializer() {
      return SERIALIZER;
   }

   public static Predicate<Entity> cleavePredicate(Entity user, Entity target) {
      return e -> {
         if ((!(e instanceof Animal) || target instanceof Animal) && (!(e instanceof AbstractVillager) || target instanceof AbstractVillager)) {
            if (!AdventureConfig.cleaveHitsPlayers && e instanceof Player) {
               return false;
            } else if (target instanceof Enemy && !(e instanceof Enemy)) {
               return false;
            } else {
               if (e != user && e instanceof LivingEntity le && le.m_6084_()) {
                  return true;
               }

               return false;
            }
         } else {
            return false;
         }
      };
   }

   static record CleaveValues(StepFunction chance, StepFunction targets) {
      public static final Codec<CleavingAffix.CleaveValues> CODEC = RecordCodecBuilder.create(
         inst -> inst.group(StepFunction.CODEC.fieldOf("chance").forGetter(c -> c.chance), StepFunction.CODEC.fieldOf("targets").forGetter(c -> c.targets))
               .apply(inst, CleavingAffix.CleaveValues::new)
      );
   }
}
