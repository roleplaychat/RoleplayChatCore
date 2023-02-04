package net.xunto.roleplaychat.framework.renderer;

import net.xunto.roleplaychat.framework.text.Text;
import net.xunto.roleplaychat.framework.text.TextColor;
import net.xunto.roleplaychat.framework.state.MessageState;

import java.util.Map;

public interface ITemplate {
    Text render(MessageState state, Map<String, TextColor> colors);
}
