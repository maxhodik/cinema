package command;

import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaginationTest {
    private final HttpServletRequest request =mock(HttpServletRequest.class);
    private final static int TOTAL_RECORDS= 10;
    private final Pagination paginate = new Pagination();

    @Test
    void paginate() {
        //given
        when(request.getParameter("records")).thenReturn("1");
        when(request.getParameter("offset")).thenReturn("0");
       //when
        paginate.paginate(TOTAL_RECORDS,request);
        //then
        verify(request).setAttribute("offset", 0);
        verify(request).setAttribute("records", 1);
        verify(request).setAttribute("pages", 10);
        verify(request).setAttribute("currentPage", 1);
        verify(request).setAttribute("start", 1);
        verify(request).setAttribute("end", 3);

    }
    @Test
    void paginateZeroRecords() {
        //given
        when(request.getParameter("records")).thenReturn("0");
        when(request.getParameter("offset")).thenReturn("0");
        //when
        paginate.paginate(TOTAL_RECORDS,request);
        //then
        verify(request).setAttribute("offset", 0);
        verify(request).setAttribute("records", 5);
        verify(request).setAttribute("pages", 2);
        verify(request).setAttribute("currentPage", 1);
        verify(request).setAttribute("start", 1);
        verify(request).setAttribute("end", 2);

    }

    @Test
    void paginateRecords() {
        //given
        when(request.getParameter("records")).thenReturn("10");
        when(request.getParameter("offset")).thenReturn("0");
        //when
        paginate.paginate(TOTAL_RECORDS,request);
        //then
        verify(request).setAttribute("offset", 0);
        verify(request).setAttribute("records", 10);
        verify(request).setAttribute("pages", 1);
        verify(request).setAttribute("currentPage", 1);
        verify(request).setAttribute("start", 1);
        verify(request).setAttribute("end", 1);

    }

    @Test
    void paginateWrongRecords() {
        //given
        when(request.getParameter("records")).thenReturn("d");
        when(request.getParameter("offset")).thenReturn("0");
        //when
        paginate.paginate(TOTAL_RECORDS,request);
        //then
        verify(request).setAttribute("offset", 0);
        verify(request).setAttribute("records", 5);
        verify(request).setAttribute("pages", 2);
        verify(request).setAttribute("currentPage", 1);
        verify(request).setAttribute("start", 1);
        verify(request).setAttribute("end", 2);

    }
    @Test
    void paginateNegativeRecords() {
        //given
        when(request.getParameter("records")).thenReturn("-3.5");
        when(request.getParameter("offset")).thenReturn("0");
        //when
        paginate.paginate(TOTAL_RECORDS,request);
        //then
        verify(request).setAttribute("offset", 0);
        verify(request).setAttribute("records", 5);
        verify(request).setAttribute("pages", 2);
        verify(request).setAttribute("currentPage", 1);
        verify(request).setAttribute("start", 1);
        verify(request).setAttribute("end", 2);
    }
    @Test
    void paginateWrongOffset() {
        //given
        when(request.getParameter("records")).thenReturn("5");
        when(request.getParameter("offset")).thenReturn("F");
        //when
        paginate.paginate(TOTAL_RECORDS,request);
        //then
        verify(request).setAttribute("offset", 0);
        verify(request).setAttribute("records", 5);
        verify(request).setAttribute("pages", 2);
        verify(request).setAttribute("currentPage", 1);
        verify(request).setAttribute("start", 1);
        verify(request).setAttribute("end", 2);
    }
    @Test
    void paginateNegativeOffset() {
        //given
        when(request.getParameter("records")).thenReturn("5");
        when(request.getParameter("offset")).thenReturn("-5");
        //when
        paginate.paginate(TOTAL_RECORDS,request);
        //then
        verify(request).setAttribute("offset", 0);
        verify(request).setAttribute("records", 5);
        verify(request).setAttribute("pages", 2);
        verify(request).setAttribute("currentPage", 1);
        verify(request).setAttribute("start", 1);
        verify(request).setAttribute("end", 2);
    }
}