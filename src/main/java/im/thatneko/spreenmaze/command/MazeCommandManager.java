package im.thatneko.spreenmaze.command;

import im.thatneko.spreenmaze.SpreenMaze;
import im.thatneko.spreenmaze.command.impl.flow.DialogCommand;
import me.fixeddev.commandflow.CommandManager;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.part.PartInjector;
import me.fixeddev.commandflow.annotated.part.defaults.DefaultsModule;
import me.fixeddev.commandflow.bukkit.BukkitCommandManager;
import me.fixeddev.commandflow.bukkit.factory.BukkitModule;

import java.util.Arrays;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class MazeCommandManager {
    private final CommandManager commandManager;
    private final AnnotatedCommandTreeBuilder treeBuilder;

    public MazeCommandManager() {
        commandManager = new BukkitCommandManager(SpreenMaze.getInstance().getName());
        PartInjector partInjector = PartInjector.create();
        partInjector.install(new DefaultsModule());
        partInjector.install(new BukkitModule());
        treeBuilder = AnnotatedCommandTreeBuilder.create(partInjector);

        Arrays.asList(new DialogCommand()).forEach(this::registerCommand);
    }

    public void registerCommand(CommandClass commandClass) {
        commandManager.registerCommands(treeBuilder.fromClass(commandClass));
    }

    public void unload() {
        commandManager.unregisterAll();;
    }
}