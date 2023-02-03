package net.xunto.roleplaychat.features.endpoints;

import net.xunto.roleplaychat.ChatTest;
import net.xunto.roleplaychat.TestUtility;
import net.xunto.roleplaychat.framework.api.Environment;
import org.junit.Test;
import org.testng.Assert;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ActionEndpointTest extends ChatTest {
    ActionEndpoint instance = new ActionEndpoint();

    public void testMatchCase(String text) {
        assertTrue(instance.matchEndpoint(setUpRequest(text), setUpEnvironment(text)));
    }

    @Test public void testMatch() {
        testMatchCase("* test * test * test");
        testMatchCase("test * test2 * tes");
    }

    public Environment testProcessCase(String text) {
        Environment environment = setUpEnvironment(text);
        instance.processWrapped(setUpRequest(text), environment, TestUtility.DO_NOTHING);

        return environment;
    }

    @Test public void testProcess() {
        Environment environment = testProcessCase("text * text * text");

        Assert.assertEquals(new String[] {"text", "text", "text"},
            environment.getState().getValue(ActionEndpoint.ACTION_PARTS));
        assertFalse(environment.getState().getValue(ActionEndpoint.START_WITH_ACTION));


        environment = testProcessCase("* text * text * text");

        Assert.assertEquals(new String[] {"text", "text", "text"},
            environment.getState().getValue(ActionEndpoint.ACTION_PARTS));
        assertTrue(environment.getState().getValue(ActionEndpoint.START_WITH_ACTION));
    }
}
