package com.superbpm.platform.cache;

import com.superbpm.platform.entity.tenant.App;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * platform
 *
 * @author rock
 */
public class SystemCache {

    private static final ConcurrentMap<String, Object> cache = new ConcurrentHashMap<String, Object>();

    private static final String REST_APP_CACHE_PREFIX = "__tenant_app__";

    public static App getApp(String bucket) {
        App restApp = (App) cache.get(REST_APP_CACHE_PREFIX + bucket);
        if (restApp == null) {

            //todo load tenant from db
//            ITenantService tenantService = WebApplicationContextUtil.
//                    getBean("tenantService", ITenantService.class);
//            restApp = tenantService.loadApp(bucket);

            if (restApp != null) {
                cache.put(REST_APP_CACHE_PREFIX + bucket, restApp);
            }
        }
        return restApp;
    }

    public static App getApp4Data(String bucket) {
        return getApp(bucket);
    }
}
