package shadows.apotheosis.advancements;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EnchantedItemTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.EnchantedItemTrigger.TriggerInstance;
import net.minecraft.advancements.critereon.EntityPredicate.Composite;
import net.minecraft.advancements.critereon.MinMaxBounds.Doubles;
import net.minecraft.advancements.critereon.MinMaxBounds.Ints;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class EnchantedTrigger extends EnchantedItemTrigger {
   public EnchantedTrigger.Instance createInstance(JsonObject json, Composite entityPredicate, DeserializationContext conditionsParser) {
      ItemPredicate item = ItemPredicate.m_45051_(json.get("item"));
      Ints levels = Ints.m_55373_(json.get("levels"));
      Doubles eterna = Doubles.m_154791_(json.get("eterna"));
      Doubles quanta = Doubles.m_154791_(json.get("quanta"));
      Doubles arcana = Doubles.m_154791_(json.get("arcana"));
      Doubles rectification = Doubles.m_154791_(json.get("rectification"));
      return new EnchantedTrigger.Instance(item, levels, eterna, quanta, arcana, rectification);
   }

   public void trigger(ServerPlayer player, ItemStack stack, int level, float eterna, float quanta, float arcana, float rectification) {
      this.m_66234_(
         player,
         inst -> inst instanceof EnchantedTrigger.Instance
               ? ((EnchantedTrigger.Instance)inst).test(stack, level, eterna, quanta, arcana, rectification)
               : inst.m_27691_(stack, level)
      );
   }

   public static class Instance extends TriggerInstance {
      protected final Doubles eterna;
      protected final Doubles quanta;
      protected final Doubles arcana;
      protected final Doubles rectification;

      public Instance(ItemPredicate item, Ints levels, Doubles eterna, Doubles quanta, Doubles arcana, Doubles rectification) {
         super(Composite.f_36667_, item, levels);
         this.eterna = eterna;
         this.quanta = quanta;
         this.arcana = arcana;
         this.rectification = rectification;
      }

      public static TriggerInstance any() {
         return new TriggerInstance(Composite.f_36667_, ItemPredicate.f_45028_, Ints.f_55364_);
      }

      public boolean test(ItemStack stack, int level, float eterna, float quanta, float arcana, float rectification) {
         return super.m_27691_(stack, level)
            && this.eterna.m_154810_((double)eterna)
            && this.quanta.m_154810_((double)quanta)
            && this.arcana.m_154810_((double)arcana)
            && this.rectification.m_154810_((double)rectification);
      }

      public boolean m_27691_(ItemStack stack, int level) {
         return this.test(stack, level, 0.0F, 0.0F, 0.0F, 0.0F);
      }

      public JsonObject m_7683_(SerializationContext serializer) {
         JsonObject jsonobject = super.m_7683_(serializer);
         jsonobject.add("eterna", this.eterna.m_55328_());
         jsonobject.add("quanta", this.quanta.m_55328_());
         jsonobject.add("arcana", this.arcana.m_55328_());
         jsonobject.add("rectification", this.rectification.m_55328_());
         return jsonobject;
      }
   }
}
