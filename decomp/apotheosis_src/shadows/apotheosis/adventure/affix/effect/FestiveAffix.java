package shadows.apotheosis.adventure.affix.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import shadows.apotheosis.adventure.affix.Affix;
import shadows.apotheosis.adventure.affix.AffixHelper;
import shadows.apotheosis.adventure.affix.AffixInstance;
import shadows.apotheosis.adventure.affix.AffixType;
import shadows.apotheosis.adventure.affix.socket.gem.bonus.GemBonus;
import shadows.apotheosis.adventure.loot.LootCategory;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.placebo.json.PSerializer;
import shadows.placebo.util.StepFunction;

public class FestiveAffix extends Affix {
   public static Codec<FestiveAffix> CODEC = RecordCodecBuilder.create(
      inst -> inst.group(GemBonus.VALUES_CODEC.fieldOf("values").forGetter(a -> a.values)).apply(inst, FestiveAffix::new)
   );
   public static final PSerializer<FestiveAffix> SERIALIZER = PSerializer.fromCodec("Festive Affix", CODEC);
   protected final Map<LootRarity, StepFunction> values;
   private static String MARKER = "apoth.equipment";

   public FestiveAffix(Map<LootRarity, StepFunction> values) {
      super(AffixType.ABILITY);
      this.values = values;
   }

   @Override
   public void addInformation(ItemStack stack, LootRarity rarity, float level, Consumer<Component> list) {
      list.accept(Component.m_237110_("affix." + this.getId() + ".desc", new Object[]{fmt(100.0F * this.getTrueLevel(rarity, level))}));
   }

   @Override
   public boolean canApplyTo(ItemStack stack, LootCategory cat, LootRarity rarity) {
      return cat.isLightWeapon() && this.values.containsKey(rarity);
   }

   private float getTrueLevel(LootRarity rarity, float level) {
      return this.values.get(rarity).get(level);
   }

   public void markEquipment(LivingDeathEvent e) {
      if (!(e.getEntity() instanceof Player) && !e.getEntity().getPersistentData().m_128471_("apoth.no_pinata")) {
         e.getEntity().m_20158_().forEach(i -> {
            if (!i.m_41619_()) {
               i.m_41784_().m_128379_(MARKER, true);
            }
         });
      }
   }

   public void drops(LivingDropsEvent e) {
      LivingEntity dead = e.getEntity();
      if (!(dead instanceof Player) && !dead.getPersistentData().m_128471_("apoth.no_pinata")) {
         if (e.getSource().m_7639_() instanceof Player player && !e.getDrops().isEmpty()) {
            AffixInstance inst = AffixHelper.getAffixes(player.m_21205_()).get(this);
            if (inst != null && player.f_19853_.f_46441_.m_188501_() < this.getTrueLevel(inst.rarity(), inst.level())) {
               player.f_19853_
                  .m_6263_(
                     null,
                     dead.m_20185_(),
                     dead.m_20186_(),
                     dead.m_20189_(),
                     SoundEvents.f_11913_,
                     SoundSource.BLOCKS,
                     4.0F,
                     (1.0F + (player.f_19853_.f_46441_.m_188501_() - player.f_19853_.f_46441_.m_188501_()) * 0.2F) * 0.7F
                  );
               ((ServerLevel)player.f_19853_).m_8767_(ParticleTypes.f_123812_, dead.m_20185_(), dead.m_20186_(), dead.m_20189_(), 2, 1.0, 0.0, 0.0, 0.0);

               for (ItemEntity item : new ArrayList(e.getDrops())) {
                  if (!item.m_32055_().m_41782_() || !item.m_32055_().m_41783_().m_128441_(MARKER)) {
                     for (int i = 0; i < 20; i++) {
                        e.getDrops().add(new ItemEntity(player.f_19853_, item.m_20185_(), item.m_20186_(), item.m_20189_(), item.m_32055_().m_41777_()));
                     }
                  }
               }

               for (ItemEntity itemx : e.getDrops()) {
                  if (!itemx.m_32055_().m_41720_().m_41465_()) {
                     itemx.m_6034_(dead.m_20185_(), dead.m_20186_(), dead.m_20189_());
                     itemx.m_20334_(
                        -0.3 + dead.f_19853_.f_46441_.m_188500_() * 0.6,
                        0.3 + dead.f_19853_.f_46441_.m_188500_() * 0.3,
                        -0.3 + dead.f_19853_.f_46441_.m_188500_() * 0.6
                     );
                  }
               }
            }
         }
      }
   }

   public void removeMarker(LivingDropsEvent e) {
      e.getDrops().stream().forEach(ent -> {
         ItemStack s = ent.m_32055_();
         if (s.m_41782_() && s.m_41783_().m_128441_(MARKER)) {
            s.m_41783_().m_128473_(MARKER);
            if (s.m_41783_().m_128456_()) {
               s.m_41751_(null);
            }
         }

         ent.m_32045_(s);
      });
   }

   public PSerializer<? extends Affix> getSerializer() {
      return SERIALIZER;
   }
}
