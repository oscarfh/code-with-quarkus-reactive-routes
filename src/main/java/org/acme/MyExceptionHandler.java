package org.acme;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.common.MyCustomException;

@Provider
public class MyExceptionHandler implements ExceptionMapper<MyCustomException>
{
    @Override
    public Response toResponse(MyCustomException exception)
    {
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}