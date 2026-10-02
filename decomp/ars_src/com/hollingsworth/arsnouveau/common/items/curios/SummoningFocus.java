package com.hollingsworth.arsnouveau.common.items.curios;

import com.hollingsworth.arsnouveau.api.entity.ISummon;
import com.hollingsworth.arsnouveau.api.event.SpellCastEvent;
import com.hollingsworth.arsnouveau.api.event.SummonEvent;
import com.hollingsworth.arsnouveau.api.item.ArsNouveauCurio;
import com.hollingsworth.arsnouveau.api.item.ISpellModifierItem;
import com.hollingsworth.arsnouveau.api.spell.AbstractCastMethod;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.EntitySpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.LivingCaster;
import com.hollingsworth.arsnouveau.api.util.CuriosUtil;
import com.hollingsworth.arsnouveau.common.spell.method.MethodOrbit;
import com.hollingsworth.arsnouveau.common.spell.method.MethodSelf;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.items.IItemHandlerModifiable;

@EventBusSubscriber(
   modid = "ars_nouveau"
)
public class SummoningFocus extends ArsNouveauCurio implements ISpellModifierItem {
   public static List<AbstractCastMethod> sympatheticMethods = new ArrayList<>();

   @Override
   public SpellStats.Builder applyItemModifiers(
      ItemStack stack,
      SpellStats.Builder builder,
      AbstractSpellPart spellPart,
      HitResult rayTraceResult,
      Level world,
      @Nullable LivingEntity shooter,
      SpellContext spellContext
   ) {
      builder.addDamageModifier(1.0);
      return builder;
   }

   public static boolean containsThis(Level world, Entity entity) {
      if (!world.f_46443_ && entity instanceof Player) {
         IItemHandlerModifiable items = (IItemHandlerModifiable)CuriosUtil.getAllWornItems((LivingEntity)entity).orElse(null);
         if (items != null) {
            for (int i = 0; i < items.getSlots(); i++) {
               Item item = items.getStackInSlot(i).m_41720_();
               if (item instanceof SummoningFocus) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   @SubscribeEvent
   public static void summonedEvent(SummonEvent event) {
      if (!event.world.f_46443_ && containsThis(event.world, event.summon.m_21826_())) {
         event.summon.setTicksLeft(event.summon.getTicksLeft() * 2);
         if (event.summon.getLivingEntity() != null) {
            event.summon.getLivingEntity().m_7292_(new MobEffectInstance(MobEffects.f_19600_, 500, 2));
            event.summon.getLivingEntity().m_7292_(new MobEffectInstance(MobEffects.f_19596_, 500, 1));
         }
      }
   }

   @SubscribeEvent
   public static void castSpell(SpellCastEvent event) {
      if (!event.getWorld().f_46443_
         && event.getEntity() instanceof Player
         && containsThis(event.getWorld(), event.getEntity())
         && event.spell.getCastMethod() != null
         && sympatheticMethods.contains(event.spell.getCastMethod())) {
         for (LivingEntity i : event.getWorld().m_6443_(LivingEntity.class, new AABB(event.getEntity().m_20183_()).m_82400_(30.0), ISummon.class::isInstance)) {
            if (event.getEntity().equals(((ISummon)i).m_21826_())) {
               EntitySpellResolver spellResolver = new EntitySpellResolver(event.context.clone().withWrappedCaster(new LivingCaster(i)));
               spellResolver.onCast(ItemStack.f_41583_, i.f_19853_);
            }
         }
      }
   }

   @SubscribeEvent
   public static void summonDeathEvent(SummonEvent.Death event) {
      if (!event.world.f_46443_ && containsThis(event.world, event.summon.m_21826_())) {
         DamageSource source = event.source;
         if (source != null && source.m_7639_() != null && source.m_7639_() != event.summon.m_21826_()) {
            source.m_7639_().m_6469_(DamageSource.m_19335_(source.m_7639_()).m_19380_(), 5.0F);
         }
      }
   }

   static {
      sympatheticMethods.add(MethodSelf.INSTANCE);
      sympatheticMethods.add(MethodOrbit.INSTANCE);
   }
}
