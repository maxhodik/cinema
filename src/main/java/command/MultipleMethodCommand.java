package command;



import javax.servlet.http.HttpServletRequest;

public abstract class MultipleMethodCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String methodName=null;
        String type = request.getMethod();
        switch (type) {
            case "GET":
                methodName = performGet(request);
                break;
            case "POST":
                methodName = performPost(request);
                break;
        }
        return methodName;
    }

    protected abstract String performGet(HttpServletRequest request);

    protected abstract String performPost(HttpServletRequest request);


}
