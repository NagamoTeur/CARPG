package shadows.apotheosis.core.attributeslib.client;

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.CritParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack.TooltipPart;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.event.ScreenEvent.Init.Post;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.adventure.AdventureModule;
import shadows.apotheosis.core.attributeslib.AttributesLib;
import shadows.apotheosis.core.attributeslib.api.AddAttributeTooltipsEvent;
import shadows.apotheosis.core.attributeslib.api.AttributeHelper;
import shadows.apotheosis.core.attributeslib.api.GatherEffectScreenTooltipsEvent;
import shadows.apotheosis.core.attributeslib.api.GatherSkippedAttributeTooltipsEvent;
import shadows.apotheosis.core.attributeslib.api.IFormattableAttribute;

public class AttributesLibClient {
   private static final UUID FAKE_MERGED_UUID = UUID.fromString("a6b0ac71-e435-416e-a991-7623eaa129a4");

   @SubscribeEvent
   public static void particleFactories(RegisterParticleProvidersEvent e) {
      e.register((ParticleType)Apoth.Particles.APOTH_CRIT.get(), AttributesLibClient.ApothCritProvider::new);
   }

   @SubscribeEvent(
      priority = EventPriority.HIGHEST
   )
   public void tooltips(ItemTooltipEvent e) {
      ItemStack stack = e.getItemStack();
      List<Component> list = e.getToolTip();
      int markIdx1 = -1;
      int markIdx2 = -1;

      for (int i = 0; i < list.size(); i++) {
         ComponentContents var8 = list.get(i).m_214077_();
         if (var8 instanceof LiteralContents) {
            LiteralContents tc = (LiteralContents)var8;
            if ("APOTH_REMOVE_MARKER".equals(tc.f_237368_())) {
               markIdx1 = i;
            }

            if ("APOTH_REMOVE_MARKER_2".equals(tc.f_237368_())) {
               markIdx2 = i;
               break;
            }
         }
      }

      if (markIdx1 != -1 && markIdx2 != -1) {
         ListIterator<Component> it = list.listIterator(markIdx1);

         for (int ix = markIdx1; ix < markIdx2 + 1; ix++) {
            it.next();
            it.remove();
         }

         int flags = getHideFlags(stack);
         if (shouldShowInTooltip(flags, TooltipPart.MODIFIERS)) {
            applyModifierTooltips(e.getEntity(), stack, it::add, e.getFlags());
         }

         MinecraftForge.EVENT_BUS.post(new AddAttributeTooltipsEvent(stack, e.getEntity(), list, it, e.getFlags()));
      }
   }

   @SubscribeEvent(
      priority = EventPriority.HIGH
   )
   public void addAttribComponent(Post e) {
      if (e.getScreen() instanceof InventoryScreen scn) {
         AttributesGui atrComp = new AttributesGui(scn);
         e.addListener(atrComp);
         e.addListener(atrComp.toggleBtn);
         e.addListener(atrComp.hideUnchangedBtn);
         if (AttributesGui.wasOpen) {
            atrComp.toggleVisibility();
         }
      }
   }

   @SubscribeEvent(
      priority = EventPriority.HIGH
   )
   public void effectGuiTooltips(GatherEffectScreenTooltipsEvent e) {
      List<Component> tooltips = e.getTooltip();
      MobEffectInstance effectInst = e.getEffectInstance();
      MobEffect effect = effectInst.m_19544_();
      MutableComponent name = (MutableComponent)tooltips.get(0);
      Component duration = tooltips.remove(1);
      Component var14 = Component.m_237110_("(%s)", new Object[]{duration}).m_130940_(ChatFormatting.WHITE);
      name.m_130946_(" ").m_7220_(var14);
      if (AttributesLib.getTooltipFlag().m_7050_()) {
         name.m_130946_(" ").m_7220_(Component.m_237110_("[%s]", new Object[]{Registry.f_122823_.m_7981_(effect)}).m_130940_(ChatFormatting.GRAY));
      }

      String key = effect.m_19481_() + ".desc";
      if (I18n.m_118936_(key)) {
         tooltips.add(Component.m_237115_(key).m_130944_(new ChatFormatting[]{ChatFormatting.YELLOW, ChatFormatting.ITALIC}));
      } else if (AttributesLib.getTooltipFlag().m_7050_() && effect.m_19485_().isEmpty()) {
         tooltips.add(Component.m_237115_(key).m_130944_(new ChatFormatting[]{ChatFormatting.GRAY, ChatFormatting.ITALIC}));
      }

      List<Pair<Attribute, AttributeModifier>> list = Lists.newArrayList();
      Map<Attribute, AttributeModifier> map = effect.m_19485_();
      if (!map.isEmpty()) {
         for (Entry<Attribute, AttributeModifier> entry : map.entrySet()) {
            AttributeModifier attributemodifier = entry.getValue();
            AttributeModifier attributemodifier1 = new AttributeModifier(
               attributemodifier.m_22214_(), effect.m_7048_(effectInst.m_19564_(), attributemodifier), attributemodifier.m_22217_()
            );
            list.add(new Pair(entry.getKey(), attributemodifier1));
         }
      }

      if (!list.isEmpty()) {
         tooltips.add(CommonComponents.f_237098_);

         for (Pair<Attribute, AttributeModifier> pair : list) {
            tooltips.add(IFormattableAttribute.toComponent((Attribute)pair.getFirst(), (AttributeModifier)pair.getSecond(), AttributesLib.getTooltipFlag()));
         }
      }
   }

   @SubscribeEvent
   public void potionTooltips(ItemTooltipEvent e) {
      ItemStack stack = e.getItemStack();
      List<Component> tooltips = e.getToolTip();
      if (stack.m_41720_() instanceof PotionItem) {
         List<MobEffectInstance> effects = PotionUtils.m_43547_(stack);
         if (effects.size() == 1 && tooltips.size() >= 2) {
            MobEffect effect = effects.get(0).m_19544_();
            String key = effect.m_19481_() + ".desc";
            if (I18n.m_118936_(key)) {
               tooltips.add(2, Component.m_237115_(key).m_130944_(new ChatFormatting[]{ChatFormatting.YELLOW, ChatFormatting.ITALIC}));
               tooltips.add(3, CommonComponents.f_237098_);
            } else if (e.getFlags().m_7050_() && effect.m_19485_().isEmpty()) {
               tooltips.add(2, Component.m_237115_(key).m_130944_(new ChatFormatting[]{ChatFormatting.GRAY, ChatFormatting.ITALIC}));
               tooltips.add(3, CommonComponents.f_237098_);
            }
         }
      }
   }

   public static Multimap<Attribute, AttributeModifier> getSortedModifiers(ItemStack stack, EquipmentSlot slot) {
      Multimap<Attribute, AttributeModifier> unsorted = stack.m_41638_(slot);
      Multimap<Attribute, AttributeModifier> map = AttributeHelper.sortedMap();

      for (Entry<Attribute, AttributeModifier> ent : unsorted.entries()) {
         if (ent.getKey() != null && ent.getValue() != null) {
            map.put(ent.getKey(), ent.getValue());
         } else {
            AdventureModule.LOGGER.debug("Detected broken attribute modifier entry on item {}.  Attr={}, Modif={}", stack, ent.getKey(), ent.getValue());
         }
      }

      return map;
   }

   public static void apothCrit(int entityId) {
      Entity entity = Minecraft.m_91087_().f_91073_.m_6815_(entityId);
      if (entity != null) {
         Minecraft.m_91087_().f_91061_.m_107329_(entity, (ParticleOptions)Apoth.Particles.APOTH_CRIT.get());
      }
   }

   private static boolean shouldShowInTooltip(int pHideFlags, TooltipPart pPart) {
      return (pHideFlags & pPart.m_41809_()) == 0;
   }

   private static int getHideFlags(ItemStack stack) {
      return stack.m_41782_() && stack.m_41783_().m_128425_("HideFlags", 99)
         ? stack.m_41783_().m_128451_("HideFlags")
         : stack.m_41720_().getDefaultTooltipHideFlags(stack);
   }

   private static void applyModifierTooltips(@Nullable Player player, ItemStack stack, Consumer<Component> tooltip, TooltipFlag flag) {
      Multimap<Attribute, AttributeModifier> mainhand = getSortedModifiers(stack, EquipmentSlot.MAINHAND);
      Multimap<Attribute, AttributeModifier> offhand = getSortedModifiers(stack, EquipmentSlot.OFFHAND);
      Multimap<Attribute, AttributeModifier> dualHand = AttributeHelper.sortedMap();

      for (Attribute atr : mainhand.keys()) {
         Collection<AttributeModifier> modifMh = mainhand.get(atr);
         Collection<AttributeModifier> modifOh = offhand.get(atr);
         modifMh.stream().filter(a1 -> modifOh.stream().anyMatch(a2 -> a1.m_22209_().equals(a2.m_22209_()))).forEach(modif -> dualHand.put(atr, modif));
      }

      dualHand.values().forEach(m -> {
         mainhand.values().remove(m);
         offhand.values().removeIf(m1 -> m1.m_22209_().equals(m.m_22209_()));
      });
      Set<UUID> skips = new HashSet<>();
      MinecraftForge.EVENT_BUS.post(new GatherSkippedAttributeTooltipsEvent(stack, player, skips, flag));
      applyTextFor(player, stack, tooltip, dualHand, "both_hands", skips, flag);
      applyTextFor(player, stack, tooltip, mainhand, EquipmentSlot.MAINHAND.m_20751_(), skips, flag);
      applyTextFor(player, stack, tooltip, offhand, EquipmentSlot.OFFHAND.m_20751_(), skips, flag);

      for (EquipmentSlot slot : EquipmentSlot.values()) {
         if (slot.ordinal() >= 2) {
            Multimap<Attribute, AttributeModifier> modifiers = getSortedModifiers(stack, slot);
            applyTextFor(player, stack, tooltip, modifiers, slot.m_20751_(), skips, flag);
         }
      }
   }

   private static MutableComponent padded(String padding, Component comp) {
      return Component.m_237113_(padding).m_7220_(comp);
   }

   private static MutableComponent list() {
      return AttributeHelper.list();
   }

   private static void applyTextFor(
      @Nullable Player player,
      ItemStack stack,
      Consumer<Component> tooltip,
      Multimap<Attribute, AttributeModifier> modifierMap,
      String group,
      Set<UUID> skips,
      TooltipFlag flag
   ) {
      if (!modifierMap.isEmpty()) {
         modifierMap.values().removeIf(m -> skips.contains(m.m_22209_()));
         tooltip.accept(Component.m_237119_());
         tooltip.accept(Component.m_237115_("item.modifiers." + group).m_130940_(ChatFormatting.GRAY));
         if (modifierMap.isEmpty()) {
            return;
         }

         Map<Attribute, AttributesLibClient.BaseModifier> baseModifs = new IdentityHashMap<>();
         modifierMap.forEach((attrx, modif) -> {
            if (modif.m_22209_().equals(((IFormattableAttribute)attrx).getBaseUUID())) {
               baseModifs.put(attrx, new AttributesLibClient.BaseModifier(modif, new ArrayList<>()));
            }
         });
         modifierMap.forEach((attrx, modif) -> {
            AttributesLibClient.BaseModifier basex = baseModifs.get(attrx);
            if (basex != null && basex.base != modif) {
               basex.children.add(modif);
            }
         });

         for (Entry<Attribute, AttributesLibClient.BaseModifier> entry : baseModifs.entrySet()) {
            Attribute attr = entry.getKey();
            AttributesLibClient.BaseModifier baseModif = entry.getValue();
            double entityBase = player == null ? 0.0 : player.m_21172_(attr);
            double base = baseModif.base.m_22218_() + entityBase;
            double rawBase = base;
            double amt = base;
            double baseBonus = ((IFormattableAttribute)attr).getBonusBaseValue(stack);

            for (AttributeModifier modif : baseModif.children) {
               if (modif.m_22217_() == Operation.ADDITION) {
                  base = amt += modif.m_22218_();
               } else if (modif.m_22217_() == Operation.MULTIPLY_BASE) {
                  amt += modif.m_22218_() * base;
               } else {
                  amt *= 1.0 + modif.m_22218_();
               }
            }

            amt += baseBonus;
            boolean isMerged = !baseModif.children.isEmpty() || baseBonus != 0.0;
            MutableComponent text = IFormattableAttribute.toBaseComponent(attr, amt, entityBase, isMerged, flag);
            tooltip.accept(padded(" ", text).m_130940_(isMerged ? ChatFormatting.GOLD : ChatFormatting.DARK_GREEN));
            if (Screen.m_96638_() && isMerged) {
               text = IFormattableAttribute.toBaseComponent(attr, rawBase, entityBase, false, flag);
               tooltip.accept(list().m_7220_(text.m_130940_(ChatFormatting.DARK_GREEN)));

               for (AttributeModifier modifier : baseModif.children) {
                  tooltip.accept(list().m_7220_(IFormattableAttribute.toComponent(attr, modifier, flag)));
               }

               if (baseBonus > 0.0) {
                  ((IFormattableAttribute)attr).addBonusTooltips(stack, tooltip, flag);
               }
            }
         }

         for (Attribute attr : modifierMap.keySet()) {
            if (!baseModifs.containsKey(attr)) {
               Collection<AttributeModifier> modifs = modifierMap.get(attr);
               if (modifs.size() > 1) {
                  double[] sums = new double[3];
                  boolean[] merged = new boolean[3];
                  Map<Operation, List<AttributeModifier>> shiftExpands = new HashMap<>();

                  for (AttributeModifier modifier : modifs) {
                     if (modifier.m_22218_() != 0.0) {
                        if (sums[modifier.m_22217_().ordinal()] != 0.0) {
                           merged[modifier.m_22217_().ordinal()] = true;
                        }

                        sums[modifier.m_22217_().ordinal()] += modifier.m_22218_();
                        shiftExpands.computeIfAbsent(modifier.m_22217_(), k -> new LinkedList<>()).add(modifier);
                     }
                  }

                  for (Operation op : Operation.values()) {
                     int i = op.ordinal();
                     if (sums[i] != 0.0) {
                        if (merged[i]) {
                           TextColor color = sums[i] < 0.0 ? TextColor.m_131266_(16331057) : TextColor.m_131266_(8026873);
                           if (sums[i] < 0.0) {
                              sums[i] *= -1.0;
                           }

                           AttributeModifier fakeModif = new AttributeModifier(FAKE_MERGED_UUID, () -> "attributeslib:merged", sums[i], op);
                           MutableComponent comp = IFormattableAttribute.toComponent(attr, fakeModif, flag);
                           tooltip.accept(comp.m_130948_(comp.m_7383_().m_131148_(color)));
                           if (merged[i] && Screen.m_96638_()) {
                              shiftExpands.get(Operation.m_22236_(i))
                                 .forEach(modifx -> tooltip.accept(list().m_7220_(IFormattableAttribute.toComponent(attr, modifx, flag))));
                           }
                        } else {
                           AttributeModifier fakeModif = new AttributeModifier(FAKE_MERGED_UUID, () -> "attributeslib:merged", sums[i], op);
                           tooltip.accept(IFormattableAttribute.toComponent(attr, fakeModif, flag));
                        }
                     }
                  }
               } else {
                  modifs.forEach(m -> {
                     if (m.m_22218_() != 0.0) {
                        tooltip.accept(IFormattableAttribute.toComponent(attr, m, flag));
                     }
                  });
               }
            }
         }
      }
   }

   public static class ApothCritParticle extends CritParticle {
      public ApothCritParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
         super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
         this.f_107229_ = 1.0F;
         this.f_107227_ = 0.3F;
         this.f_107228_ = 0.8F;
      }
   }

   public static class ApothCritProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;

      public ApothCritProvider(SpriteSet pSprites) {
         this.sprite = pSprites;
      }

      public Particle createParticle(
         SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed
      ) {
         CritParticle critparticle = new AttributesLibClient.ApothCritParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
         critparticle.m_108335_(this.sprite);
         return critparticle;
      }
   }

   private static record BaseModifier(AttributeModifier base, List<AttributeModifier> children) {
   }
}
