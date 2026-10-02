package shadows.apotheosis.adventure.commands;

import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Arrays;
import java.util.Locale;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class ModifierCommand {
   public static final SuggestionProvider<CommandSourceStack> SUGGEST_OP = (ctx, builder) -> SharedSuggestionProvider.m_82981_(
         Arrays.stream(Operation.values()).map(Enum::name), builder
      );
   public static final SuggestionProvider<CommandSourceStack> SUGGEST_SLOT = (ctx, builder) -> SharedSuggestionProvider.m_82981_(
         Arrays.stream(EquipmentSlot.values()).map(Enum::name), builder
      );
   public static final SuggestionProvider<CommandSourceStack> SUGGEST_ATTRIB = (ctx, builder) -> SharedSuggestionProvider.m_82981_(
         ForgeRegistries.ATTRIBUTES.getKeys().stream().map(ResourceLocation::toString), builder
      );

   public static void register(LiteralArgumentBuilder<CommandSourceStack> root) {
      root.then(
         ((LiteralArgumentBuilder)Commands.m_82127_("modifier").requires(c -> c.m_6761_(2)))
            .then(
               Commands.m_82129_("attribute", ResourceLocationArgument.m_106984_())
                  .suggests(SUGGEST_ATTRIB)
                  .then(
                     Commands.m_82129_("op", StringArgumentType.word())
                        .suggests(SUGGEST_OP)
                        .then(
                           Commands.m_82129_("value", FloatArgumentType.floatArg())
                              .then(Commands.m_82129_("slot", StringArgumentType.word()).suggests(SUGGEST_SLOT).executes(c -> {
                                 Player p = ((CommandSourceStack)c.getSource()).m_81375_();
                                 Attribute attrib = (Attribute)ForgeRegistries.ATTRIBUTES
                                    .getValue((ResourceLocation)c.getArgument("attribute", ResourceLocation.class));
                                 Operation op = Operation.valueOf(((String)c.getArgument("op", String.class)).toUpperCase(Locale.ROOT));
                                 EquipmentSlot slot = EquipmentSlot.valueOf(((String)c.getArgument("slot", String.class)).toUpperCase(Locale.ROOT));
                                 float value = (Float)c.getArgument("value", Float.class);
                                 ItemStack stack = p.m_21205_();
                                 stack.m_41643_(attrib, new AttributeModifier("cmd-generated-modif", (double)value, op), slot);
                                 return 0;
                              }))
                        )
                  )
            )
      );
   }
}
