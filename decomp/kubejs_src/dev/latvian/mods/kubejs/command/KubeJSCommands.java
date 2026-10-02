package dev.latvian.mods.kubejs.command;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import dev.latvian.mods.kubejs.CommonProperties;
import dev.latvian.mods.kubejs.KubeJS;
import dev.latvian.mods.kubejs.KubeJSPaths;
import dev.latvian.mods.kubejs.bindings.event.ServerEvents;
import dev.latvian.mods.kubejs.core.WithPersistentData;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.event.EventResult;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import dev.latvian.mods.kubejs.net.PaintMessage;
import dev.latvian.mods.kubejs.platform.IngredientPlatformHelper;
import dev.latvian.mods.kubejs.script.ScriptManager;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.script.data.ExportablePackResources;
import dev.latvian.mods.kubejs.server.CustomCommandEventJS;
import dev.latvian.mods.kubejs.server.DataExport;
import dev.latvian.mods.kubejs.server.ServerScriptManager;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.typings.Param;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.kubejs.util.UtilsJS;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.JavaMembers;
import dev.latvian.mods.rhino.JavaMembers.FieldInfo;
import dev.latvian.mods.rhino.JavaMembers.MethodInfo;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.CompoundTagArgument;
import net.minecraft.commands.arguments.DimensionArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ObjectiveArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.arguments.ScoreHolderArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.HolderSet.Named;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.ClickEvent.Action;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.commands.ReloadCommand;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.WorldData;
import net.minecraft.world.scores.Objective;
import org.apache.commons.io.FileUtils;

public class KubeJSCommands {
   private static final char UNICODE_TICK = '✔';
   private static final char UNICODE_CROSS = '✘';
   public static final DynamicCommandExceptionType NO_REGISTRY = new DynamicCommandExceptionType(
      id -> Component.m_237113_("No builtin or static registry found for " + id)
   );

   public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
      LiteralCommandNode<CommandSourceStack> cmd = dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_(
                                                                  "kubejs"
                                                               )
                                                               .then(
                                                                  Commands.m_82127_("help").executes(context -> help((CommandSourceStack)context.getSource()))
                                                               ))
                                                            .then(
                                                               Commands.m_82127_("custom_command")
                                                                  .then(
                                                                     Commands.m_82129_("id", StringArgumentType.word())
                                                                        .suggests(
                                                                           (ctx, builder) -> SharedSuggestionProvider.m_82981_(
                                                                                 ServerEvents.CUSTOM_COMMAND
                                                                                    .findUniqueExtraIds(ScriptType.SERVER)
                                                                                    .stream()
                                                                                    .map(String::valueOf),
                                                                                 builder
                                                                              )
                                                                        )
                                                                        .executes(
                                                                           context -> customCommand(
                                                                                 (CommandSourceStack)context.getSource(),
                                                                                 StringArgumentType.getString(context, "id")
                                                                              )
                                                                        )
                                                                  )
                                                            ))
                                                         .then(
                                                            Commands.m_82127_("hand")
                                                               .executes(
                                                                  context -> hand(
                                                                        ((CommandSourceStack)context.getSource()).m_81375_(), InteractionHand.MAIN_HAND
                                                                     )
                                                               )
                                                         ))
                                                      .then(
                                                         Commands.m_82127_("offhand")
                                                            .executes(
                                                               context -> hand(((CommandSourceStack)context.getSource()).m_81375_(), InteractionHand.OFF_HAND)
                                                            )
                                                      ))
                                                   .then(
                                                      Commands.m_82127_("inventory")
                                                         .executes(context -> inventory(((CommandSourceStack)context.getSource()).m_81375_()))
                                                   ))
                                                .then(
                                                   Commands.m_82127_("hotbar")
                                                      .executes(context -> hotbar(((CommandSourceStack)context.getSource()).m_81375_()))
                                                ))
                                             .then(Commands.m_82127_("errors").executes(context -> errors((CommandSourceStack)context.getSource()))))
                                          .then(Commands.m_82127_("warnings").executes(context -> warnings((CommandSourceStack)context.getSource()))))
                                       .then(
                                          ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_(
                                                               "reload"
                                                            )
                                                            .then(
                                                               ((LiteralArgumentBuilder)Commands.m_82127_("config")
                                                                     .requires(source -> source.m_81377_().m_129792_() || source.m_6761_(2)))
                                                                  .executes(context -> reloadConfig((CommandSourceStack)context.getSource()))
                                                            ))
                                                         .then(
                                                            ((LiteralArgumentBuilder)Commands.m_82127_("startup_scripts")
                                                                  .requires(source -> source.m_81377_().m_129792_() || source.m_6761_(2)))
                                                               .executes(context -> reloadStartup((CommandSourceStack)context.getSource()))
                                                         ))
                                                      .then(
                                                         ((LiteralArgumentBuilder)Commands.m_82127_("server_scripts")
                                                               .requires(source -> source.m_81377_().m_129792_() || source.m_6761_(2)))
                                                            .executes(context -> reloadServer((CommandSourceStack)context.getSource()))
                                                      ))
                                                   .then(
                                                      ((LiteralArgumentBuilder)Commands.m_82127_("client_scripts").requires(source -> true))
                                                         .executes(context -> reloadClient((CommandSourceStack)context.getSource()))
                                                   ))
                                                .then(
                                                   ((LiteralArgumentBuilder)Commands.m_82127_("textures").requires(source -> true))
                                                      .executes(context -> reloadTextures((CommandSourceStack)context.getSource()))
                                                ))
                                             .then(
                                                ((LiteralArgumentBuilder)Commands.m_82127_("lang").requires(source -> true))
                                                   .executes(context -> reloadLang((CommandSourceStack)context.getSource()))
                                             )
                                       ))
                                    .then(
                                       ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("export")
                                                   .requires(source -> source.m_81377_().m_129792_() || source.m_6761_(2)))
                                                .executes(context -> export((CommandSourceStack)context.getSource())))
                                             .then(
                                                Commands.m_82127_("pack_zips").executes(context -> exportPacks((CommandSourceStack)context.getSource(), true))
                                             ))
                                          .then(
                                             Commands.m_82127_("pack_folders").executes(context -> exportPacks((CommandSourceStack)context.getSource(), false))
                                          )
                                    ))
                                 .then(
                                    Commands.m_82127_("list_tag")
                                       .then(
                                          ((RequiredArgumentBuilder)Commands.m_82129_("registry", ResourceLocationArgument.m_106984_())
                                                .suggests(
                                                   (ctx, builder) -> SharedSuggestionProvider.m_82981_(
                                                         ((CommandSourceStack)ctx.getSource())
                                                            .m_5894_()
                                                            .m_206193_()
                                                            .map(entry -> entry.f_206233_().m_135782_().toString()),
                                                         builder
                                                      )
                                                )
                                                .executes(ctx -> listTagsFor((CommandSourceStack)ctx.getSource(), registry(ctx, "registry"))))
                                             .then(
                                                Commands.m_82129_("tag", ResourceLocationArgument.m_106984_())
                                                   .suggests(
                                                      (ctx, builder) -> SharedSuggestionProvider.m_82981_(
                                                            allTags((CommandSourceStack)ctx.getSource(), registry(ctx, "registry"))
                                                               .map(TagKey::f_203868_)
                                                               .map(ResourceLocation::toString),
                                                            builder
                                                         )
                                                   )
                                                   .executes(
                                                      ctx -> tagObjects(
                                                            (CommandSourceStack)ctx.getSource(),
                                                            TagKey.m_203882_(registry(ctx, "registry"), ResourceLocationArgument.m_107011_(ctx, "tag"))
                                                         )
                                                   )
                                             )
                                       )
                                 ))
                              .then(
                                 Commands.m_82127_("dump_registry")
                                    .then(
                                       Commands.m_82129_("registry", ResourceLocationArgument.m_106984_())
                                          .suggests(
                                             (ctx, builder) -> SharedSuggestionProvider.m_82981_(
                                                   ((CommandSourceStack)ctx.getSource())
                                                      .m_5894_()
                                                      .m_206193_()
                                                      .map(entry -> entry.f_206233_().m_135782_().toString()),
                                                   builder
                                                )
                                          )
                                          .executes(ctx -> dumpRegistry((CommandSourceStack)ctx.getSource(), registry(ctx, "registry")))
                                    )
                              ))
                           .then(
                              ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("stages")
                                          .then(
                                             Commands.m_82127_("add")
                                                .then(
                                                   Commands.m_82129_("player", EntityArgument.m_91470_())
                                                      .then(
                                                         Commands.m_82129_("stage", StringArgumentType.string())
                                                            .executes(
                                                               context -> addStage(
                                                                     (CommandSourceStack)context.getSource(),
                                                                     EntityArgument.m_91477_(context, "player"),
                                                                     StringArgumentType.getString(context, "stage")
                                                                  )
                                                            )
                                                      )
                                                )
                                          ))
                                       .then(
                                          Commands.m_82127_("remove")
                                             .then(
                                                Commands.m_82129_("player", EntityArgument.m_91470_())
                                                   .then(
                                                      Commands.m_82129_("stage", StringArgumentType.string())
                                                         .executes(
                                                            context -> removeStage(
                                                                  (CommandSourceStack)context.getSource(),
                                                                  EntityArgument.m_91477_(context, "player"),
                                                                  StringArgumentType.getString(context, "stage")
                                                               )
                                                         )
                                                   )
                                             )
                                       ))
                                    .then(
                                       Commands.m_82127_("clear")
                                          .then(
                                             Commands.m_82129_("player", EntityArgument.m_91470_())
                                                .executes(
                                                   context -> clearStages((CommandSourceStack)context.getSource(), EntityArgument.m_91477_(context, "player"))
                                                )
                                          )
                                    ))
                                 .then(
                                    Commands.m_82127_("list")
                                       .then(
                                          Commands.m_82129_("player", EntityArgument.m_91470_())
                                             .executes(
                                                context -> listStages((CommandSourceStack)context.getSource(), EntityArgument.m_91477_(context, "player"))
                                             )
                                       )
                                 )
                           ))
                        .then(
                           Commands.m_82127_("painter")
                              .then(
                                 Commands.m_82129_("player", EntityArgument.m_91470_())
                                    .then(
                                       Commands.m_82129_("object", CompoundTagArgument.m_87657_())
                                          .executes(
                                             context -> painter(
                                                   (CommandSourceStack)context.getSource(),
                                                   EntityArgument.m_91477_(context, "player"),
                                                   CompoundTagArgument.m_87660_(context, "object")
                                                )
                                          )
                                    )
                              )
                        ))
                     .then(
                        ((LiteralArgumentBuilder)Commands.m_82127_("generate_typings").requires(source -> source.m_81377_().m_129792_()))
                           .executes(context -> generateTypings((CommandSourceStack)context.getSource()))
                     ))
                  .then(
                     ((LiteralArgumentBuilder)Commands.m_82127_("packmode").executes(context -> packmode((CommandSourceStack)context.getSource(), "")))
                        .then(
                           Commands.m_82129_("name", StringArgumentType.word())
                              .executes(context -> packmode((CommandSourceStack)context.getSource(), StringArgumentType.getString(context, "name")))
                        )
                  ))
               .then(
                  Commands.m_82127_("dump_internals")
                     .then(
                        ((LiteralArgumentBuilder)Commands.m_82127_("events").requires(source -> source.m_81377_().m_129792_() || source.m_6761_(2)))
                           .executes(context -> dumpEvents((CommandSourceStack)context.getSource()))
                     )
               ))
            .then(
               ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("persistent_data")
                           .requires(source -> source.m_6761_(2)))
                        .then(addPersistentDataCommands(Commands.m_82127_("server"), ctx -> Set.of(((CommandSourceStack)ctx.getSource()).m_81377_()))))
                     .then(
                        ((LiteralArgumentBuilder)Commands.m_82127_("dimension")
                              .then(
                                 addPersistentDataCommands(
                                    Commands.m_82127_("*"),
                                    ctx -> (Collection<? extends WithPersistentData>)((CommandSourceStack)ctx.getSource()).m_81377_().m_129785_()
                                 )
                              ))
                           .then(
                              addPersistentDataCommands(
                                 Commands.m_82129_("dimension", DimensionArgument.m_88805_()), ctx -> Set.of(DimensionArgument.m_88808_(ctx, "dimension"))
                              )
                           )
                     ))
                  .then(
                     Commands.m_82127_("entity")
                        .then(addPersistentDataCommands(Commands.m_82129_("entity", EntityArgument.m_91460_()), ctx -> EntityArgument.m_91461_(ctx, "entity")))
                  )
            )
      );
      dispatcher.register((LiteralArgumentBuilder)Commands.m_82127_("kjs").redirect(cmd));
   }

   private static int dumpEvents(CommandSourceStack source) {
      Map<String, EventGroup> groups = EventGroup.getGroups();
      Path output = KubeJSPaths.LOCAL.resolve("event_groups");

      for (Entry<String, EventGroup> entry : groups.entrySet()) {
         String groupName = entry.getKey();
         EventGroup group = entry.getValue();
         Path groupFolder = output.resolve(groupName);

         try {
            Files.createDirectories(groupFolder);
            FileUtils.cleanDirectory(groupFolder.toFile());
         } catch (IOException var35) {
            ConsoleJS.SERVER.handleError(var35, null, "Failed to create folder for event group " + groupName);
            source.m_81352_(Component.m_237113_("Failed to create folder for event group " + groupName));
            return 0;
         }

         for (Entry<String, EventHandler> handlerEntry : group.getHandlers().entrySet()) {
            String handlerName = handlerEntry.getKey();
            EventHandler handler = handlerEntry.getValue();
            Path handlerFile = groupFolder.resolve(handlerName + ".md");
            String fullName = "%s.%s".formatted(groupName, handlerName);
            Class<? extends EventJS> eventType = handler.eventType.get();
            StringBuilder builder = new StringBuilder();
            builder.append("# ").append(fullName).append("\n\n");
            builder.append("## Basic info\n\n");
            builder.append("- Valid script types: ").append(handler.scriptTypePredicate.getValidTypes()).append("\n\n");
            builder.append("- Has result? ").append((char)(handler.getHasResult() ? '✔' : '✘')).append("\n\n");
            builder.append("- Event class: ");
            if (eventType.getPackageName().startsWith("dev.latvian.mods.kubejs")) {
               builder.append('[')
                  .append(UtilsJS.toMappedTypeString(eventType))
                  .append(']')
                  .append('(')
                  .append("https://github.com/KubeJS-Mods/KubeJS/tree/")
                  .append(1902)
                  .append("/common/src/main/java/")
                  .append(eventType.getPackageName().replace('.', '/'))
                  .append('/')
                  .append(eventType.getSimpleName())
                  .append(".java")
                  .append(')');
            } else {
               builder.append(UtilsJS.toMappedTypeString(eventType)).append(" (third-party)");
            }

            builder.append("\n\n");
            Info classInfo = eventType.getAnnotation(Info.class);
            if (classInfo != null) {
               builder.append("```\n").append(classInfo.value()).append("```");
               builder.append("\n\n");
            }

            ScriptManager scriptManager = ScriptType.SERVER.manager.get();
            Context cx = scriptManager.context;
            JavaMembers members = JavaMembers.lookupClass(cx, scriptManager.topLevelScope, eventType, null, false);
            boolean hasDocumentedMembers = false;
            StringBuilder documentedMembers = new StringBuilder("### Documented members:\n\n");
            builder.append("### Available fields:\n\n");
            builder.append("| Name | Type | Static? |\n");
            builder.append("| ---- | ---- | ------- |\n");

            for (FieldInfo field : members.getAccessibleFields(cx, false)) {
               if (field.field.getDeclaringClass() != Object.class) {
                  String typeName = UtilsJS.toMappedTypeString(field.field.getGenericType());
                  builder.append("| ").append(field.name).append(" | ").append(typeName).append(" | ");
                  builder.append((char)(Modifier.isStatic(field.field.getModifiers()) ? '✔' : '✘')).append(" |\n");
                  Info info = field.field.getAnnotation(Info.class);
                  if (info != null) {
                     hasDocumentedMembers = true;
                     documentedMembers.append("- `").append(typeName).append(' ').append(field.name).append("`\n");
                     documentedMembers.append("```\n");
                     String desc = info.value();
                     documentedMembers.append(desc);
                     if (!desc.endsWith("\n")) {
                        documentedMembers.append("\n");
                     }

                     documentedMembers.append("```\n\n");
                  }
               }
            }

            builder.append("\n").append("Note: Even if no fields are listed above, some methods are still available as fields through *beans*.\n\n");
            builder.append("### Available methods:\n\n");
            builder.append("| Name | Parameters | Return type | Static? |\n");
            builder.append("| ---- | ---------- | ----------- | ------- |\n");

            for (MethodInfo method : members.getAccessibleMethods(cx, false)) {
               if (!method.hidden && method.method.getDeclaringClass() != Object.class) {
                  builder.append("| ").append(method.name).append(" | ");
                  Type[] params = method.method.getGenericParameterTypes();
                  String[] paramTypes = new String[params.length];

                  for (int i = 0; i < params.length; i++) {
                     paramTypes[i] = UtilsJS.toMappedTypeString(params[i]);
                  }

                  builder.append(String.join(", ", paramTypes)).append(" | ");
                  String returnType = UtilsJS.toMappedTypeString(method.method.getGenericReturnType());
                  builder.append(" | ").append(returnType).append(" | ");
                  builder.append((char)(Modifier.isStatic(method.method.getModifiers()) ? '✔' : '✘')).append(" |\n");
                  Info info = method.method.getAnnotation(Info.class);
                  if (info != null) {
                     hasDocumentedMembers = true;
                     documentedMembers.append("- ").append('`');
                     if (Modifier.isStatic(method.method.getModifiers())) {
                        documentedMembers.append("static ");
                     }

                     documentedMembers.append(returnType).append(' ').append(method.name).append('(');
                     Param[] namedParams = info.params();
                     String[] paramNames = new String[params.length];
                     String[] signature = new String[params.length];

                     for (int i = 0; i < params.length; i++) {
                        String name = "var" + i;
                        if (namedParams.length > i) {
                           String name1 = namedParams[i].name();
                           if (!Strings.isNullOrEmpty(name1)) {
                              name = name1;
                           }
                        }

                        paramNames[i] = name;
                        signature[i] = paramTypes[i] + " " + name;
                     }

                     documentedMembers.append(String.join(", ", signature)).append(')').append('`').append("\n");
                     if (params.length > 0) {
                        documentedMembers.append("\n  Parameters:\n");

                        for (int i = 0; i < params.length; i++) {
                           documentedMembers.append("  - ")
                              .append(paramNames[i])
                              .append(": ")
                              .append(paramTypes[i])
                              .append(namedParams.length > i ? "- " + namedParams[i].value() : "")
                              .append("\n");
                        }

                        documentedMembers.append("\n");
                     }

                     documentedMembers.append("```\n");
                     String desc = info.value();
                     documentedMembers.append(desc);
                     if (!desc.endsWith("\n")) {
                        documentedMembers.append("\n");
                     }

                     documentedMembers.append("```\n\n");
                  }
               }
            }

            builder.append("\n\n");
            if (hasDocumentedMembers) {
               builder.append((CharSequence)documentedMembers).append("\n\n");
            }

            builder.append("### Example script:\n\n");
            builder.append("```js\n");
            builder.append(fullName).append('(');
            if (handler.extra != null) {
               builder.append(handler.extra.required ? "extra_id, " : "/* extra_id (optional), */ ");
            }

            builder.append("(event) => {\n");
            builder.append("\t// This space (un)intentionally left blank\n");
            builder.append("});\n");
            builder.append("```\n\n");

            try {
               Files.writeString(handlerFile, builder.toString());
            } catch (IOException var34) {
               ConsoleJS.SERVER.handleError(var34, null, "Failed to write file for event handler " + fullName);
               source.m_81352_(Component.m_237113_("Failed to write file for event handler " + fullName));
               return 0;
            }
         }
      }

      source.m_81354_(Component.m_237113_("Successfully dumped event groups to " + output), false);
      return 1;
   }

   private static <T> ResourceKey<Registry<T>> registry(CommandContext<CommandSourceStack> ctx, String arg) {
      return ResourceKey.m_135788_(ResourceLocationArgument.m_107011_(ctx, arg));
   }

   private static <T> Stream<TagKey<T>> allTags(CommandSourceStack source, ResourceKey<Registry<T>> registry) throws CommandSyntaxException {
      return ((Registry)source.m_5894_().m_6632_(registry).orElseThrow(() -> NO_REGISTRY.create(registry.m_135782_()))).m_203613_();
   }

   private static Component copy(String s, ChatFormatting col, String info) {
      MutableComponent component = Component.m_237113_("- ");
      component.m_6270_(component.m_7383_().m_131148_(TextColor.m_131270_(ChatFormatting.GRAY)));
      component.m_6270_(component.m_7383_().m_131142_(new ClickEvent(Action.COPY_TO_CLIPBOARD, s)));
      component.m_6270_(
         component.m_7383_().m_131144_(new HoverEvent(net.minecraft.network.chat.HoverEvent.Action.f_130831_, Component.m_237113_(info + " (Click to copy)")))
      );
      component.m_7220_(Component.m_237113_(s).m_130940_(col));
      return component;
   }

   private static void link(CommandSourceStack source, ChatFormatting color, String name, String url) {
      source.m_81354_(
         Component.m_237113_("• ")
            .m_7220_(Component.m_237113_(name).m_130940_(color).m_130938_(style -> style.m_131142_(new ClickEvent(Action.OPEN_URL, url)))),
         false
      );
   }

   private static int help(CommandSourceStack source) {
      link(source, ChatFormatting.GOLD, "Wiki", "https://kubejs.com/?" + KubeJS.QUERY);
      link(source, ChatFormatting.GREEN, "Support", "https://kubejs.com/support?" + KubeJS.QUERY);
      link(source, ChatFormatting.BLUE, "Changelog", "https://kubejs.com/changelog?" + KubeJS.QUERY);
      return 1;
   }

   private static int customCommand(CommandSourceStack source, String id) {
      if (ServerEvents.CUSTOM_COMMAND.hasListeners()) {
         EventResult result = ServerEvents.CUSTOM_COMMAND
            .post(new CustomCommandEventJS(source.m_81372_(), source.m_81373_(), new BlockPos(source.m_81371_()), id), id);
         if (result.type() == EventResult.Type.ERROR) {
            source.m_81352_(Component.m_237113_(result.value().toString()));
            return 0;
         } else {
            return 1;
         }
      } else {
         return 0;
      }
   }

   private static int hand(ServerPlayer player, InteractionHand hand) {
      player.m_213846_(Component.m_237113_("Item in hand:"));
      ItemStack stack = player.m_21120_(hand);
      player.m_213846_(copy(ItemStackJS.toItemString(stack), ChatFormatting.GREEN, "Item ID"));
      List<ResourceLocation> tags = new ArrayList<>(stack.kjs$getTags());
      tags.sort(null);

      for (ResourceLocation id : tags) {
         player.m_213846_(
            copy("'#" + id + "'", ChatFormatting.YELLOW, "Item Tag [" + IngredientPlatformHelper.get().tag(id.toString()).kjs$getStacks().size() + " items]")
         );
      }

      player.m_213846_(
         copy(
            "'@" + stack.kjs$getMod() + "'",
            ChatFormatting.AQUA,
            "Mod [" + IngredientPlatformHelper.get().mod(stack.kjs$getMod()).kjs$getStacks().size() + " items]"
         )
      );
      CreativeModeTab cat = stack.m_41720_().m_41471_();
      if (cat != null) {
         player.m_213846_(
            copy(
               "'%" + cat.m_40783_() + "'",
               ChatFormatting.LIGHT_PURPLE,
               "Item Group [" + IngredientPlatformHelper.get().creativeTab(cat).kjs$getStacks().size() + " items]"
            )
         );
      }

      return 1;
   }

   private static int inventory(ServerPlayer player) {
      return dump(player.m_150109_().f_35974_, player, "Inventory");
   }

   private static int hotbar(ServerPlayer player) {
      return dump(player.m_150109_().f_35974_.subList(0, 9), player, "Hotbar");
   }

   private static int dump(List<ItemStack> stacks, ServerPlayer player, String name) {
      List<String> dump = stacks.stream().filter(is -> !is.m_41619_()).map(ItemStackJS::toItemString).toList();
      player.m_213846_(copy(dump.toString(), ChatFormatting.WHITE, name + " Item List"));
      return 1;
   }

   private static int errors(CommandSourceStack source) {
      String[] lines = ScriptType.SERVER.errors.toArray(new String[0]);
      if (lines.length == 0) {
         source.m_81354_(Component.m_237113_("No errors found!").m_130940_(ChatFormatting.GREEN), false);
         if (!ScriptType.SERVER.warnings.isEmpty()) {
            source.m_81354_(ScriptType.SERVER.warningsComponent("/kubejs warnings"), false);
         }

         return 1;
      } else {
         for (int i = 0; i < lines.length; i++) {
            source.m_81354_(
               Component.m_237113_(i + 1 + ") ").m_7220_(Component.m_237113_(lines[i]).m_130940_(ChatFormatting.RED)).m_130940_(ChatFormatting.DARK_RED), false
            );
         }

         source.m_81354_(
            Component.m_237113_("More info in ")
               .m_7220_(
                  Component.m_237113_("'logs/kubejs/server.log'")
                     .kjs$clickOpenFile(ScriptType.SERVER.getLogFile().toString())
                     .kjs$hover(Component.m_237113_("Click to open"))
               )
               .m_130940_(ChatFormatting.DARK_RED),
            false
         );
         if (!ScriptType.SERVER.warnings.isEmpty()) {
            source.m_81354_(ScriptType.SERVER.warningsComponent("/kubejs warnings"), false);
         }

         return 1;
      }
   }

   private static int warnings(CommandSourceStack source) {
      String[] lines = ScriptType.SERVER.warnings.toArray(new String[0]);
      if (lines.length == 0) {
         source.m_81354_(Component.m_237113_("No warnings found!").m_130940_(ChatFormatting.GREEN), false);
         return 1;
      } else {
         for (int i = 0; i < lines.length; i++) {
            source.m_81354_(
               Component.m_237113_(i + 1 + ") ")
                  .m_7220_(Component.m_237113_(lines[i]).m_130948_(Style.f_131099_.m_131148_(TextColor.m_131266_(16753920))).m_130940_(ChatFormatting.RED)),
               false
            );
         }

         return 1;
      }
   }

   private static int reloadConfig(CommandSourceStack source) {
      KubeJS.PROXY.reloadConfig();
      source.m_81354_(Component.m_237113_("Done!"), false);
      return 1;
   }

   private static int reloadStartup(CommandSourceStack source) {
      KubeJS.getStartupScriptManager().reload(null);
      source.m_81354_(Component.m_237113_("Done!"), false);
      return 1;
   }

   private static int reloadServer(CommandSourceStack source) {
      ServerScriptManager.instance.reloadScriptManager(source.m_81377_().kjs$getReloadableResources().f_206584_());
      source.m_81354_(
         Component.m_237113_("Done! To reload recipes, tags, loot tables and other datapack things, run ")
            .m_7220_(Component.m_237113_("'/reload'").kjs$clickRunCommand("/reload").kjs$hover(Component.m_237113_("Click to run"))),
         false
      );
      return 1;
   }

   private static int reloadClient(CommandSourceStack source) {
      KubeJS.PROXY.reloadClientInternal();
      source.m_81354_(Component.m_237113_("Done! To reload textures, models and other assets, press F3 + T"), false);
      return 1;
   }

   private static int reloadTextures(CommandSourceStack source) {
      KubeJS.PROXY.reloadTextures();
      return 1;
   }

   private static int reloadLang(CommandSourceStack source) {
      KubeJS.PROXY.reloadLang();
      return 1;
   }

   private static int export(CommandSourceStack source) {
      if (DataExport.export != null) {
         return 0;
      } else {
         DataExport.export = new DataExport();
         DataExport.export.source = source;
         source.m_81354_(Component.m_237113_("Reloading server and exporting data..."), false);
         MinecraftServer minecraftServer = source.m_81377_();
         PackRepository packRepository = minecraftServer.m_129891_();
         WorldData worldData = minecraftServer.m_129910_();
         Collection<String> collection = packRepository.m_10523_();
         packRepository.m_10506_();
         Collection<String> collection2 = Lists.newArrayList(collection);
         Collection<String> collection3 = worldData.m_7513_().m_45855_();

         for (String string : packRepository.m_10514_()) {
            if (!collection3.contains(string) && !collection2.contains(string)) {
               collection2.add(string);
            }
         }

         ReloadCommand.m_138235_(collection2, source);
         return 1;
      }
   }

   private static int exportPacks(CommandSourceStack source, boolean exportZip) {
      ArrayList<ExportablePackResources> packs = new ArrayList<>();

      for (PackResources pack : source.m_81377_().m_177941_().m_7536_().toList()) {
         if (pack instanceof ExportablePackResources e) {
            packs.add(e);
         }
      }

      KubeJS.PROXY.export(packs);
      int success = 0;

      for (ExportablePackResources packx : packs) {
         try {
            if (exportZip) {
               Path path = KubeJSPaths.EXPORTED_PACKS.resolve(packx.m_8017_() + ".zip");
               Files.deleteIfExists(path);

               try (FileSystem fs = FileSystems.newFileSystem(path, Map.of("create", true))) {
                  packx.export(fs.getPath("."));
               }
            } else {
               Path path = KubeJSPaths.EXPORTED_PACKS.resolve(packx.m_8017_());
               if (Files.exists(path)) {
                  Files.walk(path).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
               }

               Files.createDirectories(path);
               packx.export(path);
            }

            source.m_81354_(
               Component.m_237113_("Successfully exported ")
                  .m_130940_(ChatFormatting.GREEN)
                  .m_7220_(Component.m_237113_(packx.m_8017_()).m_130940_(ChatFormatting.BLUE)),
               false
            );
            success++;
         } catch (IOException var12) {
            var12.printStackTrace();
            source.m_81352_(
               Component.m_237113_("Failed to export %s!".formatted(packx))
                  .m_130938_(
                     style -> style.m_131140_(ChatFormatting.RED)
                           .m_131144_(new HoverEvent(net.minecraft.network.chat.HoverEvent.Action.f_130831_, Component.m_237113_(var12.getMessage())))
                  )
            );
         }
      }

      if (source.m_81377_().m_129792_() && !source.m_81377_().m_6992_()) {
         source.m_81354_(Component.m_237113_("Exported " + success + " packs").kjs$clickOpenFile(KubeJSPaths.EXPORTED_PACKS.toAbsolutePath().toString()), false);
      } else {
         source.m_81354_(Component.m_237113_("Exported " + success + " packs"), false);
      }

      return success;
   }

   private static <T> int listTagsFor(CommandSourceStack source, ResourceKey<Registry<T>> registry) throws CommandSyntaxException {
      Stream<TagKey<T>> tags = allTags(source, registry);
      source.m_81354_(Component.m_237119_(), false);
      source.m_81354_(Component.m_237113_("List of all Tags for " + registry.m_135782_() + ":"), false);
      source.m_81354_(Component.m_237119_(), false);
      long size = tags.<ResourceLocation>map(TagKey::f_203868_)
         .map(
            tag -> Component.m_237113_("- %s".formatted(tag))
                  .m_130948_(
                     Style.f_131099_
                        .m_131142_(new ClickEvent(Action.RUN_COMMAND, "/kubejs list_tag %s %s".formatted(registry.m_135782_(), tag)))
                        .m_131144_(
                           new HoverEvent(
                              net.minecraft.network.chat.HoverEvent.Action.f_130831_, Component.m_237113_("[Show all entries for %s]".formatted(tag))
                           )
                        )
                  )
         )
         .mapToLong(msg -> {
            source.m_81354_(msg, false);
            return 1L;
         })
         .sum();
      source.m_81354_(Component.m_237119_(), false);
      source.m_81354_(Component.m_237113_("Total: %d tags".formatted(size)), false);
      source.m_81354_(Component.m_237113_("(Click on any of the above tags to list their contents!)"), false);
      source.m_81354_(Component.m_237119_(), false);
      return 1;
   }

   private static <T> int tagObjects(CommandSourceStack source, TagKey<T> key) throws CommandSyntaxException {
      Registry<T> registry = (Registry<T>)source.m_5894_().m_6632_(key.f_203867_()).orElseThrow(() -> NO_REGISTRY.create(key.f_203867_().m_135782_()));
      Optional<Named<T>> tag = registry.m_203431_(key);
      if (tag.isEmpty()) {
         source.m_81352_(Component.m_237113_("Tag not found or empty!"));
         return 0;
      } else {
         source.m_81354_(Component.m_237119_(), false);
         source.m_81354_(Component.m_237113_("Contents of #" + key.f_203868_() + " [" + key.f_203867_().m_135782_() + "]:"), false);
         source.m_81354_(Component.m_237119_(), false);
         Named<T> items = tag.get();

         for (Holder<T> holder : items) {
            String id = (String)holder.m_203439_().map(o -> o.m_135782_().toString(), o -> o + " (unknown ID)");
            source.m_81354_(Component.m_237113_("- " + id), false);
         }

         source.m_81354_(Component.m_237119_(), false);
         source.m_81354_(Component.m_237113_("Total: " + items.m_203632_() + " elements"), false);
         source.m_81354_(Component.m_237119_(), false);
         return 1;
      }
   }

   private static <T> int dumpRegistry(CommandSourceStack source, ResourceKey<Registry<T>> registry) throws CommandSyntaxException {
      Stream<Reference<T>> ids = ((Registry)source.m_5894_().m_6632_(registry).orElseThrow(() -> NO_REGISTRY.create(registry.m_135782_()))).m_203611_();
      source.m_81354_(Component.m_237119_(), false);
      source.m_81354_(Component.m_237113_("List of all entries for registry " + registry.m_135782_() + ":"), false);
      source.m_81354_(Component.m_237119_(), false);
      long size = ids.map(
            holder -> {
               ResourceLocation id = holder.m_205785_().m_135782_();
               return Component.m_237113_("- %s".formatted(id))
                  .m_130948_(
                     Style.f_131099_
                        .m_131144_(
                           new HoverEvent(
                              net.minecraft.network.chat.HoverEvent.Action.f_130831_,
                              Component.m_237113_("%s [%s]".formatted(holder.m_203334_(), holder.m_203334_().getClass().getName()))
                           )
                        )
                  );
            }
         )
         .mapToLong(msg -> {
            source.m_81354_(msg, false);
            return 1L;
         })
         .sum();
      source.m_81354_(Component.m_237119_(), false);
      source.m_81354_(Component.m_237113_("Total: %d entries".formatted(size)), false);
      source.m_81354_(Component.m_237119_(), false);
      return 1;
   }

   private static int addStage(CommandSourceStack source, Collection<ServerPlayer> players, String stage) {
      for (ServerPlayer p : players) {
         if (p.kjs$getStages().add(stage)) {
            source.m_81354_(Component.m_237113_("Added '" + stage + "' stage for " + p.m_6302_()), false);
         }
      }

      return 1;
   }

   private static int removeStage(CommandSourceStack source, Collection<ServerPlayer> players, String stage) {
      for (ServerPlayer p : players) {
         if (p.kjs$getStages().remove(stage)) {
            source.m_81354_(Component.m_237113_("Removed '" + stage + "' stage for " + p.m_6302_()), false);
         }
      }

      return 1;
   }

   private static int clearStages(CommandSourceStack source, Collection<ServerPlayer> players) {
      for (ServerPlayer p : players) {
         if (p.kjs$getStages().clear()) {
            source.m_81354_(Component.m_237113_("Cleared stages for " + p.m_6302_()), false);
         }
      }

      return 1;
   }

   private static int listStages(CommandSourceStack source, Collection<ServerPlayer> players) {
      for (ServerPlayer p : players) {
         source.m_81354_(Component.m_237113_(p.m_6302_() + " stages:"), false);
         p.kjs$getStages().getAll().stream().sorted().forEach(s -> source.m_81354_(Component.m_237113_("- " + s), false));
      }

      return 1;
   }

   private static int painter(CommandSourceStack source, Collection<ServerPlayer> players, CompoundTag object) {
      new PaintMessage(object).sendTo(players);
      return 1;
   }

   private static int generateTypings(CommandSourceStack source) {
      if (!source.m_81377_().m_129792_()) {
         source.m_81352_(Component.m_237113_("You can only run this command in singleplayer!"));
         return 0;
      } else {
         KubeJS.PROXY.generateTypings(source);
         return 1;
      }
   }

   private static int packmode(CommandSourceStack source, String packmode) {
      if (packmode.isEmpty()) {
         source.m_81354_(Component.m_237113_("Current packmode: " + CommonProperties.get().packMode), false);
      } else {
         CommonProperties.get().setPackMode(packmode);
         source.m_81354_(Component.m_237113_("Set packmode to: " + packmode), false);
      }

      return 1;
   }

   private static ArgumentBuilder<CommandSourceStack, ?> addPersistentDataCommands(
      ArgumentBuilder<CommandSourceStack, ?> cmd, KubeJSCommands.PersistentDataFactory factory
   ) {
      cmd.then(
         ((LiteralArgumentBuilder)Commands.m_82127_("get")
               .then(
                  Commands.m_82127_("*")
                     .executes(
                        ctx -> {
                           Collection<? extends WithPersistentData> objects = factory.getAll(ctx);

                           for (WithPersistentData o : objects) {
                              Component dataStr = NbtUtils.m_178061_(o.kjs$getPersistentData());
                              ((CommandSourceStack)ctx.getSource())
                                 .m_81354_(
                                    Component.m_237113_("")
                                       .m_7220_(Component.m_237113_("").m_130940_(ChatFormatting.YELLOW).m_7220_(o.kjs$getDisplayName()))
                                       .m_130946_(": ")
                                       .m_7220_(dataStr),
                                    false
                                 );
                           }

                           return objects.size();
                        }
                     )
               ))
            .then(
               Commands.m_82129_("key", StringArgumentType.string())
                  .executes(
                     ctx -> {
                        Collection<? extends WithPersistentData> objects = factory.getAll(ctx);
                        String key = StringArgumentType.getString(ctx, "key");

                        for (WithPersistentData o : objects) {
                           Tag data = (Tag)(key.equals("*") ? o.kjs$getPersistentData() : o.kjs$getPersistentData().m_128423_(key));
                           Component dataStr = (Component)(data == null ? Component.m_237113_("null").m_130940_(ChatFormatting.RED) : NbtUtils.m_178061_(data));
                           ((CommandSourceStack)ctx.getSource())
                              .m_81354_(
                                 Component.m_237113_("")
                                    .m_7220_(Component.m_237113_("").m_130940_(ChatFormatting.YELLOW).m_7220_(o.kjs$getDisplayName()))
                                    .m_130946_(": ")
                                    .m_7220_(dataStr),
                                 false
                              );
                        }

                        return objects.size();
                     }
                  )
            )
      );
      cmd.then(
         Commands.m_82127_("merge")
            .then(
               Commands.m_82129_("nbt", CompoundTagArgument.m_87657_())
                  .executes(
                     ctx -> {
                        Collection<? extends WithPersistentData> objects = factory.getAll(ctx);
                        CompoundTag tag = CompoundTagArgument.m_87660_(ctx, "nbt");

                        for (WithPersistentData o : objects) {
                           o.kjs$getPersistentData().m_128391_(tag);
                           ((CommandSourceStack)ctx.getSource())
                              .m_81354_(
                                 Component.m_237113_("")
                                    .m_7220_(Component.m_237113_("").m_130940_(ChatFormatting.YELLOW).m_7220_(o.kjs$getDisplayName()))
                                    .m_130946_(" updated"),
                                 false
                              );
                        }

                        return objects.size();
                     }
                  )
            )
      );
      cmd.then(((LiteralArgumentBuilder)Commands.m_82127_("remove").then(Commands.m_82127_("*").executes(ctx -> {
         Collection<? extends WithPersistentData> objects = factory.getAll(ctx);

         for (WithPersistentData o : objects) {
            o.kjs$getPersistentData().m_128431_().removeIf(UtilsJS.ALWAYS_TRUE);
         }

         return objects.size();
      }))).then(Commands.m_82129_("key", StringArgumentType.string()).executes(ctx -> {
         Collection<? extends WithPersistentData> objects = factory.getAll(ctx);
         String key = StringArgumentType.getString(ctx, "key");

         for (WithPersistentData o : objects) {
            o.kjs$getPersistentData().m_128473_(key);
         }

         return objects.size();
      })));
      cmd.then(
         ((LiteralArgumentBuilder)Commands.m_82127_("scoreboard")
               .then(
                  Commands.m_82127_("import")
                     .then(
                        Commands.m_82129_("key", StringArgumentType.string())
                           .then(
                              Commands.m_82129_("target", ScoreHolderArgument.m_108217_())
                                 .suggests(ScoreHolderArgument.f_108210_)
                                 .then(Commands.m_82129_("objective", ObjectiveArgument.m_101957_()).executes(ctx -> {
                                    ServerScoreboard scoreboard = ((CommandSourceStack)ctx.getSource()).m_81377_().m_129896_();
                                    Collection<? extends WithPersistentData> objects = factory.getAll(ctx);
                                    String key = StringArgumentType.getString(ctx, "key");
                                    String target = ScoreHolderArgument.m_108223_(ctx, "target");
                                    Objective objective = ObjectiveArgument.m_101960_(ctx, "objective");
                                    int score = scoreboard.m_83461_(target, objective) ? scoreboard.m_83471_(target, objective).m_83400_() : 0;

                                    for (WithPersistentData o : objects) {
                                       o.kjs$getPersistentData().m_128405_(key, score);
                                    }

                                    return objects.size();
                                 }))
                           )
                     )
               ))
            .then(
               Commands.m_82127_("export")
                  .then(
                     Commands.m_82129_("key", StringArgumentType.string())
                        .then(
                           Commands.m_82129_("targets", ScoreHolderArgument.m_108239_())
                              .suggests(ScoreHolderArgument.f_108210_)
                              .then(Commands.m_82129_("objective", ObjectiveArgument.m_101957_()).executes(ctx -> {
                                 ServerScoreboard scoreboard = ((CommandSourceStack)ctx.getSource()).m_81377_().m_129896_();
                                 WithPersistentData object = factory.getOne(ctx);
                                 String key = StringArgumentType.getString(ctx, "key");
                                 Collection<String> targets = ScoreHolderArgument.m_108243_(ctx, "targets");
                                 Objective objective = ObjectiveArgument.m_101960_(ctx, "objective");
                                 int score = object.kjs$getPersistentData().m_128451_(key);

                                 for (String target : targets) {
                                    scoreboard.m_83471_(target, objective).m_83402_(score);
                                 }

                                 return 1;
                              }))
                        )
                  )
            )
      );
      return cmd;
   }

   @FunctionalInterface
   private interface PersistentDataFactory {
      SimpleCommandExceptionType EMPTY_LIST = new SimpleCommandExceptionType(Component.m_237113_("Expected at least one target"));

      Collection<? extends WithPersistentData> apply(CommandContext<CommandSourceStack> var1) throws CommandSyntaxException;

      default Collection<? extends WithPersistentData> getAll(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
         Collection<? extends WithPersistentData> list = this.apply(ctx);
         if (list.isEmpty()) {
            throw EMPTY_LIST.create();
         } else {
            return list;
         }
      }

      default WithPersistentData getOne(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
         Collection<? extends WithPersistentData> list = this.apply(ctx);
         if (list.isEmpty()) {
            throw EMPTY_LIST.create();
         } else {
            return list.iterator().next();
         }
      }
   }
}
