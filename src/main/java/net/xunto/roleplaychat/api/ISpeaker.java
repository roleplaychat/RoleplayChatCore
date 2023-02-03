package net.xunto.roleplaychat.api;

import net.xunto.roleplaychat.framework.renderer.text.Text;
import net.xunto.roleplaychat.framework.renderer.text.TextColor;

import java.util.UUID;

public interface ISpeaker {
    void sendMessage(String text, TextColor color);

    void sendMessage(Text components);

    String getName(); // Name used for rendering

    String getRealName(); // Name used fore user-input (for command, for example)

    Position getPosition();

    IWorld getWorld();

    UUID getUniqueID();

    default boolean hasPermission(IPermission permission) {
        return this.hasPermission(permission.getName());
    }

    boolean hasPermission(String string);

    int hashCode();
}
