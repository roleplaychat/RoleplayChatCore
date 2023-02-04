package net.xunto.roleplaychat.api;

import net.xunto.roleplaychat.framework.api.Request;
import net.xunto.roleplaychat.framework.commands.CommandException;

public interface ICommand {
    String getCommandName();

    String[] getTabCompletion(ISpeaker speaker, String[] args);

    boolean canExecute(ISpeaker speaker);

    void execute(Request request, String[] args) throws CommandException;
}
