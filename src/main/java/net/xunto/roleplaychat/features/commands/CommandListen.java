package net.xunto.roleplaychat.features.commands;

import net.xunto.roleplaychat.api.ICommand;
import net.xunto.roleplaychat.api.IServer;
import net.xunto.roleplaychat.api.ISpeaker;
import net.xunto.roleplaychat.features.Translations;
import net.xunto.roleplaychat.features.middleware.distance.ListenMiddleware;
import net.xunto.roleplaychat.features.middleware.distance.hearing_gm.DistanceHearingMode;
import net.xunto.roleplaychat.features.middleware.distance.hearing_gm.IHearingMode;
import net.xunto.roleplaychat.features.middleware.distance.hearing_gm.InfiniteHearingMode;
import net.xunto.roleplaychat.features.middleware.distance.hearing_gm.NoExtraHearingMode;
import net.xunto.roleplaychat.features.permissions.PermissionGM;
import net.xunto.roleplaychat.framework.commands.CommandException;
import net.xunto.roleplaychat.framework.commands.CommandUtils;
import net.xunto.roleplaychat.framework.renderer.text.TextColor;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.Optional;

public class CommandListen implements ICommand {
    @Override
    public String getCommandName() {
        return "listen";
    }

    @Override
    public String[] getTabCompletion(ISpeaker speaker, String[] args) {
        return CommandUtils.getPlayerNames(speaker.getWorld().getServer());
    }

    @Override
    public boolean canExecute(ISpeaker speaker) {
        return speaker.hasPermission(PermissionGM.instance);
    }

    @Override
    public void execute(ISpeaker speaker, String[] args) throws CommandException {
        ISpeaker target = speaker;
        IServer server = speaker.getWorld().getServer();

        if (args.length > 0) {
            String tryUsername = args[0];
            ISpeaker[] players = CommandUtils.getPlayers(server);

            Optional<ISpeaker> search = Arrays.stream(players).filter((v) -> v.getRealName().equals(tryUsername)).findFirst();

            if (search.isPresent()) {
                target = search.get();
                args = ArrayUtils.subarray(args, 1, args.length);
            }
        }

        IHearingMode hearingMode = ListenMiddleware.getHearingMode(target);
        IHearingMode newMode;

        if (args.length == 0) {
            if (hearingMode == NoExtraHearingMode.instance) newMode = InfiniteHearingMode.instance;
            else newMode = NoExtraHearingMode.instance;
        } else if (args.length == 1) {
            try {
                int distance = Integer.parseInt(args[0]);
                newMode = new DistanceHearingMode(distance);
            } catch (NumberFormatException e) {
                throw new CommandException(Translations.HEARING_ARGUMENT_EXPECTED);
            }
        } else {
            throw new CommandException(Translations.HEARING_LESS_ARGUMENT_EXPECTED);
        }

        ListenMiddleware.setHearingMode(target, newMode);

        target.sendMessage(String.format(
                Translations.YOUR_HEARING_MODE_CHANGED,
                newMode.getHumanReadable()
        ), TextColor.GREEN);

        if (!target.equals(speaker)) {
            speaker.sendMessage(String.format(
                    Translations.HEARING_MODE_CHANGED,
                    target.getName(),
                    newMode.getHumanReadable()
            ), TextColor.GREEN);
        }
    }
}
