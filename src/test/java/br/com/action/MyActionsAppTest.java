package br.com.action;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.test.MockRequestBuilder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import org.junit.Test;

public class MyActionsAppTest {

  private static String fromFile(String fileName) throws IOException {
    Path absolutePath = Paths.get("src", "test", "resources", fileName);
    return new String(Files.readAllBytes(absolutePath));
  }

  @Test
  public void testWelcomeUsingRawRequest() throws Exception {
    MyActionsApp app = new MyActionsApp();
    String requestBody = fromFile("request_welcome.json");
    String expectedResponse = fromFile("response_welcome.json");

    CompletableFuture<String> future = app.handleRequest(requestBody, null /* headers */);

    String responseJson = future.get();
    assertEquals(expectedResponse, responseJson);
  }

  @Test
  public void testWelcomeUsingMockRequestBuilder() {
    MyActionsApp app = new MyActionsApp();
    MockRequestBuilder rb = MockRequestBuilder.PreBuilt.welcome("welcome", true);
    ActionRequest request = rb.build();

    ActionResponse response = app.welcome(request);
    assertTrue(response.getExpectUserResponse());
    assertEquals(1, response.getRichResponse().getItems().size());
  }

  @Test
  public void testBye() {
    MyActionsApp app = new MyActionsApp();
    MockRequestBuilder rb = new MockRequestBuilder();
    rb.setIntent("bye");
    rb.setUsesDialogflow(true);

    ActionRequest request = rb.build();
    ActionResponse response = app.bye(request);

    assertFalse(response.getExpectUserResponse());
    assertEquals(1, response.getRichResponse().getItems().size());
  }
}
