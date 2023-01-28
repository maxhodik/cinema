package command;

import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LogOutCommandTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpSession httpSession = mock(HttpSession.class);
    private final LogOutCommand logOutCommand= new LogOutCommand();

    @Test
    void execute() {
        //given
        when(request.getSession()).thenReturn(httpSession);
        //when
        String path= logOutCommand.execute(request);
        //then
        verify(httpSession).invalidate();
        assertEquals("redirect:index.jsp", path);

    }
}