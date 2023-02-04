package net.xunto.roleplaychat.features.endpoints;

import net.xunto.roleplaychat.RoleplayChatCore;
import net.xunto.roleplaychat.api.ISpeaker;
import net.xunto.roleplaychat.api.IWorld;
import net.xunto.roleplaychat.features.middleware.distance.ListenMiddleware;
import net.xunto.roleplaychat.features.permissions.PermissionGM;
import net.xunto.roleplaychat.framework.api.Environment;
import net.xunto.roleplaychat.framework.api.PrefixMatchEndpoint;
import net.xunto.roleplaychat.framework.api.Request;
import net.xunto.roleplaychat.framework.pebble.PebbleChatTemplate;
import net.xunto.roleplaychat.framework.renderer.ITemplate;
import net.xunto.roleplaychat.framework.text.TextColor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class GmOOCEndpoint extends PrefixMatchEndpoint {
    private static final Map<String, TextColor> colors = new HashMap<>();

    static {
        colors.put("default", TextColor.GOLD);
        colors.put("username", TextColor.GOLD);
    }

    private ITemplate template = new PebbleChatTemplate("templates/gm_ooc.pebble.twig");

    public GmOOCEndpoint(RoleplayChatCore core) throws EmptyPrefixError {
        super(core, "-");
    }

    @Override
    public void processEndpoint(Request request, Environment environment) {
        environment.getState().setValue(ListenMiddleware.AVOID, true);

        environment.setTemplate(template);
        environment.getColors().putAll(colors);

        Set<ISpeaker> recipients = environment.getRecipients();
        recipients.clear();

        IWorld[] worlds = request.getRequester().getWorld().getServer().getWorlds();
        for (IWorld world : worlds) {
            for (ISpeaker player : world.getPlayers()) {
                if (player.hasPermission(PermissionGM.instance))
                    recipients.add(player);
            }
        }

        recipients.add(request.getRequester());
    }
}
