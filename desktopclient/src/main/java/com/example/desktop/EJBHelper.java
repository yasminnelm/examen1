package com.example.desktop;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class EJBHelper {
    public static <T> T lookupRemoteEJB(Class<T> beanClass, String jndiName) throws NamingException {
        Properties jndiProperties = new Properties();
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");

        Context context = new InitialContext(jndiProperties);
        return (T) context.lookup(jndiName);
    }
}

