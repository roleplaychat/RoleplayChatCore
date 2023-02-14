package net.xunto.roleplaychat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.xunto.roleplaychat.api.ICommand;
import net.xunto.roleplaychat.api.ICompat;
import net.xunto.roleplaychat.api.ILogger;
import net.xunto.roleplaychat.api.IPermission;
import net.xunto.roleplaychat.api.ISpeaker;
import net.xunto.roleplaychat.features.commands.CommandDistance;
import net.xunto.roleplaychat.features.commands.CommandListen;
import net.xunto.roleplaychat.features.endpoints.ActionEndpoint;
import net.xunto.roleplaychat.features.endpoints.GmActionEndpoint;
import net.xunto.roleplaychat.features.endpoints.GmOOCEndpoint;
import net.xunto.roleplaychat.features.endpoints.OOCEndpoint;
import net.xunto.roleplaychat.features.middleware.distance.Distance;
import net.xunto.roleplaychat.features.middleware.distance.DistanceMiddleware;
import net.xunto.roleplaychat.features.middleware.distance.ListenMiddleware;
import net.xunto.roleplaychat.features.middleware.remember.RecallDistanceMiddleware;
import net.xunto.roleplaychat.features.middleware.remember.RecallEndpointMiddleware;
import net.xunto.roleplaychat.features.permissions.PermissionGM;
import net.xunto.roleplaychat.framework.api.Environment;
import net.xunto.roleplaychat.framework.api.Middleware;
import net.xunto.roleplaychat.framework.api.Request;
import net.xunto.roleplaychat.framework.middleware_flow.Flow;
import net.xunto.roleplaychat.framework.text.Text;
import net.xunto.roleplaychat.pluginloader.PluginLoader;

public class RoleplayChatCore {
    public final static RoleplayChatCore instance = new RoleplayChatCore();

    private ILogger logger;
    private final Set<ICompat> compats = new HashSet<>();

    private final List<Middleware> middleware = new ArrayList<>();
    private final List<ICommand> commands = new ArrayList<>();
    private final List<IPermission> permissions = new ArrayList<>();
    
    private final PluginLoader pluginLoader = new PluginLoader(this);

    public RoleplayChatCore() {
        // Middleware
        this.register(new RecallDistanceMiddleware());
        this.register(new RecallEndpointMiddleware());
        this.register(new DistanceMiddleware());
        this.register(new ListenMiddleware());
        this.register(new ActionEndpoint());

        this.register(new OOCEndpoint(this).registerCommand("o"));
        this.register(new GmOOCEndpoint(this).registerCommand("gmo"));
        this.register(new GmActionEndpoint(this).registerCommand("gmsay"));

        // Commands
        this.register(new CommandListen());
        this.register(new CommandDistance(this, "qqq", Distance.QUITE_WHISPER));
        this.register(new CommandDistance(this, "qq", Distance.WHISPER));
        this.register(new CommandDistance(this, "q", Distance.QUITE));
        this.register(new CommandDistance(this, "l", Distance.LOUD));
        this.register(new CommandDistance(this, "ll", Distance.SHOUT));
        this.register(new CommandDistance(this, "lll", Distance.LOUD_SHOUT));

        // Permission
        this.register(PermissionGM.instance);
    }

    public void registerCompat(ICompat compat) {
        this.compats.add(compat);
    }

    public void register(IPermission permission) {
        this.permissions.add(permission);
    }

    public <T extends Middleware> T findMiddleware(Class<T> clazz) {
        for (Middleware middleware1 : middleware) {
            if (middleware1.getClass().equals(clazz)) {
                return (T) middleware1;
            }
        }

        return null;
    }

    public void onPlayerLeave(ISpeaker speaker) {
        ListenMiddleware.resetHearingMode(speaker);
    }

    public void register(Middleware newMiddleware) {
        middleware.add(newMiddleware);
        middleware.sort(Comparator.comparing(Middleware::getStage).thenComparing(Middleware::getPriority));
    }

    public void register(ICommand command) {
        commands.add(command);
    }

    public void warmUpRenderer() {
    }

    public List<Text> process(Request request) {
        Environment response = new Environment(request.getRequester().getName(), request.getText());

        return this.process(request, response);
    }

    public List<Text> process(Request request, Environment environment) {
        environment.setCore(this);

        List<Environment> toSend = new ArrayList<>();
        Flow flow = new Flow(this.middleware, request, environment, toSend::add);
        flow.next();

        List<Text> result = new ArrayList<>();
        for (Environment resultEnvironment : toSend) {
            Text text = this.send(request, resultEnvironment);

            if (!resultEnvironment.getState().getValue(Flow.IS_LIGHT_FORK, false)) {
                result.add(text);
            }
        }

        for (Text text : result) {
            boolean shouldLog = false;
            for (ICompat iCompat : compats) {
                shouldLog |= iCompat.compat(request.getRequester(), text);
            }

            if (shouldLog) this.logger.log(text);
        }

        return result;
    }

    public Text send(Request request, Environment environment) {
        Text text = environment.getTemplate().render(environment.getState(), environment.getColors());

        for (ISpeaker recipient : environment.getRecipients()) {
            recipient.sendMessage(request, text);
        }

        return text;
    }

    public List<Middleware> getMiddleware() {
        return middleware;
    }

    public List<ICommand> getCommands() {
        return this.commands;
    }

    public List<IPermission> getPermissions() {
        return permissions;
    }

    public void setLogger(ILogger logger) {
        this.logger = logger;
    }

    public void loadPlugins(String[] plugins) {
        this.pluginLoader.loadAll(plugins);
    }

    public void init() {
        this.warmUpRenderer();
        this.pluginLoader.loadWithServiceLoader();
    }
}
