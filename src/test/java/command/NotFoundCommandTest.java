package command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class NotFoundCommandTest {
    private final HttpServletRequest request= mock(HttpServletRequest.class);
    private final NotFoundCommand notFoundCommand= new NotFoundCommand();

    @Test
    void performGet() {
        String path = notFoundCommand.performGet(request);
        assertEquals("/WEB-INF/404.jsp", path);
    }

    @Test
    void performPost() {
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, ()-> notFoundCommand.performPost(request));
        assertEquals("NOT FOUND", thrown.getMessage());
    }
}