package net.thirdlife.iterrpg.init;

import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.level.GameRules.Category;
import net.minecraft.world.level.GameRules.Key;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(
   bus = Bus.MOD
)
public class IterRpgModGameRules {
   public static final Key<BooleanValue> FRAGILEBLOCKS = GameRules.m_46189_("fragileBlocks", Category.PLAYER, BooleanValue.m_46250_(true));
   public static final Key<BooleanValue> BUILDINGDEBUG = GameRules.m_46189_("buildingDebug", Category.PLAYER, BooleanValue.m_46250_(false));
}
