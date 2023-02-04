package net.xunto.roleplaychat.features.middleware.remember;

import net.xunto.roleplaychat.framework.api.Middleware;
import net.xunto.roleplaychat.framework.api.Priority;
import net.xunto.roleplaychat.framework.api.Request;
import net.xunto.roleplaychat.framework.api.Stage;
import net.xunto.roleplaychat.framework.text.Text;
import net.xunto.roleplaychat.framework.text.TextColor;
import net.xunto.roleplaychat.framework.text.TextComponent;

public abstract class AbstractRecallMiddleware extends Middleware {
    static void sendSetMessage(Request request, String string) {
        request.getRequester().sendMessage(
            request,
            Text.fromStringAndColor(string, TextColor.GREEN)
        );
    }

    static boolean isSetRequest(String text, String[] prefixes) {
        boolean controls = false;
        for (String prefix : prefixes) {
            controls |= text.startsWith(prefix);
        }

        for (String prefix : prefixes) {
            text = text.replace(prefix, "");
        }

        return controls && text.isEmpty();
    }

    @Override
    public Priority getPriority() {
        return Priority.HIGH;
    }

    @Override
    public Stage getStage() {
        return Stage.PRE;
    }
}
