package net.xunto.roleplaychat.api;

import net.xunto.roleplaychat.framework.renderer.text.Text;

public interface ICompat {
    boolean compat(ISpeaker speaker, Text text);
}
