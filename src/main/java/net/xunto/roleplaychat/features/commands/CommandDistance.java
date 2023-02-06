package net.xunto.roleplaychat.features.commands;

import net.xunto.roleplaychat.RoleplayChatCore;
import net.xunto.roleplaychat.api.ICommand;
import net.xunto.roleplaychat.api.ISpeaker;
import net.xunto.roleplaychat.features.middleware.distance.Distance;
import net.xunto.roleplaychat.features.middleware.distance.DistanceMiddleware;
import net.xunto.roleplaychat.framework.api.Environment;
import net.xunto.roleplaychat.framework.api.Request;
import net.xunto.roleplaychat.framework.commands.CommandException;
import net.xunto.roleplaychat.framework.state.MessageState;

public class CommandDistance implements ICommand {
    private final String name;
    private RoleplayChatCore core;
    private Distance distance;

    public CommandDistance(RoleplayChatCore core, String name, Distance distance) {
        this.core = core;
        this.name = name;
        this.distance = distance;
    }

    @Override
    public String getCommandName() {
        return this.name;
    }

    @Override
    public String[] getTabCompletion(ISpeaker speaker, String[] args) {
        return new String[0];
    }

    @Override
    public boolean canExecute(ISpeaker speaker) {
        return true;
    }

    private String addDistance(String msg) {
        String prefix = "";
        switch (distance) {
            case QUITE_WHISPER:
                prefix = "===";
                break;
            case WHISPER:
                prefix = "==";
                break;
            case QUITE:
                prefix = "=";
                break;
            case LOUD:
                prefix = "!";
                break;
            case SHOUT:
                prefix = "!!";
                break;
            case LOUD_SHOUT:
                prefix = "!!!";
                break;
            default:
                prefix = "";
        }

        return prefix + msg.trim();
    }

    @Override
    public void execute(ISpeaker source, String[] args) throws CommandException {
        String msg = String.join(" ", args);
        Request newRequest = new Request(this.addDistance(msg), source);
        Environment environment = new Environment(source.getName(), msg);

        MessageState state = environment.getState();
        state.setValue(DistanceMiddleware.FORCE_ENVIRONMENT, true);
        state.setValue(DistanceMiddleware.DISTANCE, distance);

        core.process(newRequest, environment);
    }
}
