package net.sweenus.simplyswords.util;

import dev.architectury.event.events.common.LootEvent;
import dev.architectury.event.events.common.LootEvent.ModifyLootTable;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootPool.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.sweenus.simplyswords.config.SimplySwordsConfig;
import net.sweenus.simplyswords.registry.ItemsRegistry;

public class ModLootTableModifiers {
   public static void init() {
      LootEvent.MODIFY_LOOT_TABLE
         .register(
            (ModifyLootTable)(lootTables, id, context, builtin) -> {
               if (SimplySwordsConfig.getBooleanValue("add_weapons_to_loot_tables")
                  && id.m_135815_().contains("chests")
                  && !id.m_135815_().contains("spectrum")
                  && (SimplySwordsConfig.getBooleanValue("loot_can_be_found_in_villages") || !id.m_135815_().contains("village"))) {
                  Builder pool = LootPool.m_79043_()
                     .m_165133_(ConstantValue.m_165692_(1.0F))
                     .m_79080_(LootItemRandomChanceCondition.m_81927_(SimplySwordsConfig.getGeneralSettings("standard_loot_table_weight")))
                     .m_79078_(EnchantRandomlyFunction.m_80440_())
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.IRON_LONGSWORD.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.IRON_TWINBLADE.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.IRON_RAPIER.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.IRON_CUTLASS.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.IRON_KATANA.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.IRON_GLAIVE.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.IRON_WARGLAIVE.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.IRON_SPEAR.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.IRON_SAI.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.IRON_CLAYMORE.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.IRON_CHAKRAM.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.IRON_GREATAXE.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.IRON_GREATHAMMER.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.IRON_SCYTHE.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.IRON_HALBERD.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.GOLD_LONGSWORD.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.GOLD_TWINBLADE.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.GOLD_RAPIER.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.GOLD_CUTLASS.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.GOLD_KATANA.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.GOLD_GLAIVE.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.GOLD_WARGLAIVE.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.GOLD_SPEAR.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.GOLD_SAI.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.GOLD_CLAYMORE.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.GOLD_GREATHAMMER.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.GOLD_CHAKRAM.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.GOLD_GREATAXE.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.GOLD_SCYTHE.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.GOLD_HALBERD.get()));
                  context.addPool(pool);
               }
            }
         );
      LootEvent.MODIFY_LOOT_TABLE
         .register(
            (ModifyLootTable)(lootTables, id, context, builtin) -> {
               if (SimplySwordsConfig.getBooleanValue("add_weapons_to_loot_tables")
                  && id.m_135815_().contains("chests")
                  && !id.m_135815_().contains("spectrum")
                  && (SimplySwordsConfig.getBooleanValue("loot_can_be_found_in_villages") || !id.m_135815_().contains("village"))) {
                  Builder pool = LootPool.m_79043_()
                     .m_165133_(ConstantValue.m_165692_(1.0F))
                     .m_79080_(LootItemRandomChanceCondition.m_81927_(SimplySwordsConfig.getGeneralSettings("rare_loot_table_weight")))
                     .m_79078_(EnchantRandomlyFunction.m_80440_())
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.DIAMOND_LONGSWORD.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.DIAMOND_TWINBLADE.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.DIAMOND_RAPIER.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.DIAMOND_CUTLASS.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.DIAMOND_KATANA.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.DIAMOND_SPEAR.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.DIAMOND_GLAIVE.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.DIAMOND_WARGLAIVE.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.DIAMOND_SAI.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.DIAMOND_CLAYMORE.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.DIAMOND_GREATHAMMER.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.DIAMOND_CHAKRAM.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.DIAMOND_GREATAXE.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.DIAMOND_SCYTHE.get()))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.DIAMOND_HALBERD.get()));
                  context.addPool(pool);
               }
            }
         );
      LootEvent.MODIFY_LOOT_TABLE
         .register(
            (ModifyLootTable)(lootTables, id, context, builtin) -> {
               if (SimplySwordsConfig.getBooleanValue("add_weapons_to_loot_tables")
                  && id.m_135815_().contains("chests")
                  && !id.m_135815_().contains("spectrum")
                  && (SimplySwordsConfig.getBooleanValue("loot_can_be_found_in_villages") || !id.m_135815_().contains("village"))) {
                  Builder pool = LootPool.m_79043_()
                     .m_165133_(ConstantValue.m_165692_(1.0F))
                     .m_79080_(LootItemRandomChanceCondition.m_81927_(SimplySwordsConfig.getGeneralSettings("runic_loot_table_weight")))
                     .m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.RUNIC_TABLET.get()));
                  context.addPool(pool);
               }
            }
         );
      LootEvent.MODIFY_LOOT_TABLE
         .register(
            (ModifyLootTable)(lootTables, id, context, builtin) -> {
               if (SimplySwordsConfig.getBooleanValue("add_weapons_to_loot_tables")) {
                  if (SimplySwordsConfig.getLootList(id.toString())) {
                     float lootChance = SimplySwordsConfig.getLootModifiers(id.toString());
                     if ((double)lootChance > 0.0) {
                        Builder pool = LootPool.m_79043_()
                           .m_165133_(ConstantValue.m_165692_(1.0F))
                           .m_79080_(LootItemRandomChanceCondition.m_81927_(lootChance));
                        if (SimplySwordsConfig.getBooleanValue("the_watcher")) {
                           pool.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.WATCHER_CLAYMORE.get()));
                        }

                        if (SimplySwordsConfig.getBooleanValue("watching_warglaive")) {
                           pool.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.WATCHING_WARGLAIVE.get()));
                        }

                        if (SimplySwordsConfig.getBooleanValue("longsword_of_the_plague")) {
                           pool.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.TOXIC_LONGSWORD.get()));
                        }

                        if (SimplySwordsConfig.getBooleanValue("sword_on_a_stick")) {
                           pool.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.SWORD_ON_A_STICK.get()));
                        }

                        if (SimplySwordsConfig.getBooleanValue("bramblethorn")) {
                           pool.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.BRAMBLETHORN.get()));
                        }

                        if (SimplySwordsConfig.getBooleanValue("storms_edge")) {
                           pool.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.STORMS_EDGE.get()));
                        }

                        if (SimplySwordsConfig.getBooleanValue("stormbringer")) {
                           pool.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.STORMBRINGER.get()));
                        }

                        if (SimplySwordsConfig.getBooleanValue("mjolnir")) {
                           pool.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.MJOLNIR.get()));
                        }

                        if (SimplySwordsConfig.getBooleanValue("emberblade")) {
                           pool.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.EMBERBLADE.get()));
                        }

                        if (SimplySwordsConfig.getBooleanValue("hearthflame")) {
                           pool.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.HEARTHFLAME.get()));
                        }

                        if (SimplySwordsConfig.getBooleanValue("twisted_blade")) {
                           pool.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.TWISTED_BLADE.get()));
                        }

                        if (SimplySwordsConfig.getBooleanValue("soulrender")) {
                           pool.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.SOULRENDER.get()));
                        }

                        if (SimplySwordsConfig.getBooleanValue("soulpyre")) {
                           pool.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.SOULPYRE.get()));
                        }

                        if (SimplySwordsConfig.getBooleanValue("soulkeeper")) {
                           pool.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.SOULKEEPER.get()));
                        }

                        if (SimplySwordsConfig.getBooleanValue("soulstealer")) {
                           pool.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.SOULSTEALER.get()));
                        }

                        if (SimplySwordsConfig.getBooleanValue("frostfall")) {
                           pool.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.FROSTFALL.get()));
                        }

                        if (SimplySwordsConfig.getBooleanValue("molten_edge")) {
                           pool.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.MOLTEN_EDGE.get()));
                        }

                        if (SimplySwordsConfig.getBooleanValue("livyatan")) {
                           pool.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.LIVYATAN.get()));
                        }

                        if (SimplySwordsConfig.getBooleanValue("icewhisper")) {
                           pool.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.ICEWHISPER.get()));
                        }

                        if (SimplySwordsConfig.getBooleanValue("arcanethyst")) {
                           pool.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.ARCANETHYST.get()));
                        }

                        if (SimplySwordsConfig.getBooleanValue("thunderbrand")) {
                           pool.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.THUNDERBRAND.get()));
                        }

                        if (SimplySwordsConfig.getBooleanValue("brimstone_claymore")) {
                           pool.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.BRIMSTONE_CLAYMORE.get()));
                        }

                        if (SimplySwordsConfig.getBooleanValue("slumbering_lichblade")) {
                           pool.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.SLUMBERING_LICHBLADE.get()));
                        }

                        if (SimplySwordsConfig.getBooleanValue("shadowsting")) {
                           pool.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.SHADOWSTING.get()));
                        }

                        if (SimplySwordsConfig.getBooleanValue("dormant_relic")) {
                           pool.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.DORMANT_RELIC.get()));
                        }

                        context.addPool(pool);
                     }
                  } else if (id.m_135815_().contains("chests") && !id.m_135815_().contains("spectrum")) {
                     Builder poolx = LootPool.m_79043_()
                        .m_165133_(ConstantValue.m_165692_(1.0F))
                        .m_79080_(LootItemRandomChanceCondition.m_81927_(SimplySwordsConfig.getGeneralSettings("unique_loot_table_weight")));
                     if (SimplySwordsConfig.getBooleanValue("the_watcher")) {
                        poolx.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.WATCHER_CLAYMORE.get()));
                     }

                     if (SimplySwordsConfig.getBooleanValue("watching_warglaive")) {
                        poolx.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.WATCHING_WARGLAIVE.get()));
                     }

                     if (SimplySwordsConfig.getBooleanValue("longsword_of_the_plague")) {
                        poolx.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.TOXIC_LONGSWORD.get()));
                     }

                     if (SimplySwordsConfig.getBooleanValue("sword_on_a_stick")) {
                        poolx.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.SWORD_ON_A_STICK.get()));
                     }

                     if (SimplySwordsConfig.getBooleanValue("bramblethorn")) {
                        poolx.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.BRAMBLETHORN.get()));
                     }

                     if (SimplySwordsConfig.getBooleanValue("storms_edge")) {
                        poolx.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.STORMS_EDGE.get()));
                     }

                     if (SimplySwordsConfig.getBooleanValue("stormbringer")) {
                        poolx.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.STORMBRINGER.get()));
                     }

                     if (SimplySwordsConfig.getBooleanValue("mjolnir")) {
                        poolx.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.MJOLNIR.get()));
                     }

                     if (SimplySwordsConfig.getBooleanValue("emberblade")) {
                        poolx.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.EMBERBLADE.get()));
                     }

                     if (SimplySwordsConfig.getBooleanValue("hearthflame")) {
                        poolx.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.HEARTHFLAME.get()));
                     }

                     if (SimplySwordsConfig.getBooleanValue("twisted_blade")) {
                        poolx.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.TWISTED_BLADE.get()));
                     }

                     if (SimplySwordsConfig.getBooleanValue("soulrender")) {
                        poolx.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.SOULRENDER.get()));
                     }

                     if (SimplySwordsConfig.getBooleanValue("soulpyre")) {
                        poolx.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.SOULPYRE.get()));
                     }

                     if (SimplySwordsConfig.getBooleanValue("soulkeeper")) {
                        poolx.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.SOULKEEPER.get()));
                     }

                     if (SimplySwordsConfig.getBooleanValue("soulstealer")) {
                        poolx.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.SOULSTEALER.get()));
                     }

                     if (SimplySwordsConfig.getBooleanValue("frostfall")) {
                        poolx.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.FROSTFALL.get()));
                     }

                     if (SimplySwordsConfig.getBooleanValue("molten_edge")) {
                        poolx.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.MOLTEN_EDGE.get()));
                     }

                     if (SimplySwordsConfig.getBooleanValue("livyatan")) {
                        poolx.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.LIVYATAN.get()));
                     }

                     if (SimplySwordsConfig.getBooleanValue("icewhisper")) {
                        poolx.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.ICEWHISPER.get()));
                     }

                     if (SimplySwordsConfig.getBooleanValue("arcanethyst")) {
                        poolx.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.ARCANETHYST.get()));
                     }

                     if (SimplySwordsConfig.getBooleanValue("thunderbrand")) {
                        poolx.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.THUNDERBRAND.get()));
                     }

                     if (SimplySwordsConfig.getBooleanValue("brimstone_claymore")) {
                        poolx.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.BRIMSTONE_CLAYMORE.get()));
                     }

                     if (SimplySwordsConfig.getBooleanValue("slumbering_lichblade")) {
                        poolx.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.SLUMBERING_LICHBLADE.get()));
                     }

                     if (SimplySwordsConfig.getBooleanValue("shadowsting")) {
                        poolx.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.SHADOWSTING.get()));
                     }

                     if (SimplySwordsConfig.getBooleanValue("dormant_relic")) {
                        poolx.m_79076_(LootItem.m_79579_((ItemLike)ItemsRegistry.DORMANT_RELIC.get()));
                     }

                     context.addPool(poolx);
                  }
               }
            }
         );
   }
}
