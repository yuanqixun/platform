package com.superbpm.platform.db.ds;

/**
 * platform
 *
 * @author rock
 */
public class DBContextHolder {

    private static final ThreadLocal<String> DS_CONTEXT_HOLDER = new ThreadLocal<String>();

    public static void setDbType(String dbType) {
        DS_CONTEXT_HOLDER.set(dbType);
    }

    public static String getDbType() {
        return DS_CONTEXT_HOLDER.get();
    }

    public static void clearDbType() {
        DS_CONTEXT_HOLDER.remove();
    }
}
