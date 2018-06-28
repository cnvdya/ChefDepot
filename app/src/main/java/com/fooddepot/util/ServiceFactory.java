package com.fooddepot.util;

import com.fooddepot.constants.AppConstants;

/**
 *
 * Factory class for giving the services.
 * Created by ravisha on 11/2/16.
 */
public class ServiceFactory {

    /**
     * Used for returning the service instance.
     * @param serviceName
     * @param <T>
     * @return
     * @throws Exception
     */
    public  static <T>  T getService(Class<T> serviceName) throws Exception {

        String fullyQualifiedName = serviceName.getCanonicalName();
        String Service = fullyQualifiedName.replace(AppConstants.API,AppConstants.IMPL);
        return ( T )  Class.forName(Service+AppConstants.IMPL_CLASS).newInstance();
    }
}
