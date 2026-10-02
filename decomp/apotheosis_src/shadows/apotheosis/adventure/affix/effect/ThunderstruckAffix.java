package shadows.apotheosis.adventure.affix.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.adventure.affix.Affix;
import shadows.apotheosis.adventure.affix.AffixType;
import shadows.apotheosis.adventure.affix.socket.gem.bonus.GemBonus;
import shadows.apotheosis.adventure.loot.LootCategory;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.placebo.json.PSerializer;
import shadows.placebo.util.StepFunction;

public class ThunderstruckAffix extends Affix {
   public static final Codec<ThunderstruckAffix> CODEC = RecordCodecBuilder.create(
      inst -> inst.group(GemBonus.VALUES_CODEC.fieldOf("values").forGetter(a -> a.values)).apply(inst, ThunderstruckAffix::new)
   );
   public static final PSerializer<ThunderstruckAffix> SERIALIZER = PSerializer.fromCodec("Thunderstruck Affix", CODEC);
   protected final Map<LootRarity, StepFunction> values;

   public ThunderstruckAffix(Map<LootRarity, StepFunction> values) {
      super(AffixType.ABILITY);
      this.values = values;
   }

   @Override
   public void addInformation(ItemStack stack, LootRarity rarity, float level, Consumer<Component> list) {
      list.accept(Component.m_237110_("affix." + this.getId() + ".desc", new Object[]{(int)this.getTrueLevel(rarity, level)}));
   }

   @Override
   public boolean canApplyTo(ItemStack stack, LootCategory cat, LootRarity rarity) {
      return cat.isLightWeapon() && this.values.containsKey(rarity);
   }

   @Override
   public void doPostAttack(ItemStack stack, LootRarity rarity, float level, LivingEntity user, Entity target) {
      if (!user.f_19853_.f_46443_) {
         if ((double)Apotheosis.getLocalAtkStrength(user) >= 0.98) {
            for (Entity e : target.f_19853_.m_6249_(target, new AABB(target.m_20183_()).m_82400_(6.0), CleavingAffix.cleavePredicate(user, target))) {
               e.m_6469_(DamageSource.m_19370_(user), this.getTrueLevel(rarity, level));
            }
         }
      }
   }

   private float getTrueLevel(LootRarity rarity, float level) {
      return this.values.get(rarity).get(level);
   }

   public PSerializer<? extends Affix> getSerializer() {
      return SERIALIZER;
   }
}
