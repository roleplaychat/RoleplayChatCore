package net.xunto.roleplaychat.framework.commands;

import net.xunto.roleplaychat.api.IServer;
import net.xunto.roleplaychat.api.ISpeaker;
import net.xunto.roleplaychat.api.IWorld;

import java.util.Arrays;

public class CommandUtils {
    public static ISpeaker[] getPlayers(IServer server) {
        return Arrays.stream(server.getWorlds())
                .map(IWorld::getPlayers)
                .flatMap(Arrays::stream).toArray(ISpeaker[]::new);
    }

    public static String[] getPlayerNames(IServer server) {
        return Arrays.stream(CommandUtils.getPlayers(server))
                .map(ISpeaker::getName).toArray(String[]::new);
    }
}
