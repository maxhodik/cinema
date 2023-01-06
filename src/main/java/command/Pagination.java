package command;

import javax.servlet.http.HttpServletRequest;

import static java.util.Objects.isNull;

public  class Pagination {

    public void paginate(int totalRecords, HttpServletRequest request) {
        int records = getInt(request.getParameter("records"), 1, 5);
        int offset = getInt(request.getParameter("offset"), 0, 0);
        setAttributes(request, totalRecords, records, offset);
    }

    private void setAttributes(HttpServletRequest request, int totalRecords, int records, int offset) {

        int numberOfPages = (int) Math.ceil((double) totalRecords / records);
        int currentPage = offset / records + 1;
        int startPage = currentPage == numberOfPages ? Math.max(currentPage - 2, 1)
                : Math.max(currentPage - 1, 1);
        int endPage = Math.min(startPage + 2, numberOfPages);
        request.setAttribute("offset", offset);
        request.setAttribute("records", records);
        request.setAttribute("pages", numberOfPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("start", startPage);
        request.setAttribute("end", endPage);
        request.getParameterMap().forEach((k,e) -> request.setAttribute(k, e[0]));
    }

    private static int getInt(String value, int min, int defaultValue) {
        try {
            int records = Integer.parseInt(value);
            if (records >= min) {
                return records;
            }
        } catch (NumberFormatException e) {
            return defaultValue;
        }
        return defaultValue;
    }

}