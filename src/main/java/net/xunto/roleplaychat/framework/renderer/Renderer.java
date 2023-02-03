package net.xunto.roleplaychat.framework.renderer;

import net.xunto.roleplaychat.framework.api.Environment;
import net.xunto.roleplaychat.framework.renderer.text.Text;

public abstract class Renderer {
    public abstract Text render(Environment environment);
}
