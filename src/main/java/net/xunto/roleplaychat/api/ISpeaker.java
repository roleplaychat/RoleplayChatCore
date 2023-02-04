package net.xunto.roleplaychat.api;

import net.xunto.roleplaychat.framework.api.Request;
import net.xunto.roleplaychat.framework.text.Text;

import java.util.UUID;

public interface ISpeaker {
    String getName(); // Name used for rendering

    String getRealName(); // Name used fore user-input (for command, for example)

    Position getPosition();

    IWorld getWorld();

    UUID getUniqueID();

    void sendMessage(Request request, Text components);

    default boolean hasPermission(IPermission permission) {
        return this.hasPermission(permission.getName());
    }

    boolean hasPermission(String string);

    int hashCode();
}
