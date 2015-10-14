package com.superbpm.platform.db.ds;

import com.superbpm.platform.PlatformConstants;
import com.superbpm.platform.cache.SystemCache;
import com.superbpm.platform.entity.tenant.App;
import com.superbpm.platform.utils.WatchUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * platform
 * <p/>
 * <p>
 * <pre></pre><bean id="dynamicDataSource" class="com.superbpm.platform.db.ds.DynamicDataSource">
 * <property name="targetDataSources">
 * <map key-type="java.lang.String">
 * </map>
 * </property>
 * <property name="defaultTargetDataSource" ref="dataSource" />
 * </bean>
 * </pre>
 * </p>
 *
 * @author rock
 */
public class DynamicDataSource extends AbstractRoutingDataSource {


    private static Logger LOG = Logger.getLogger(DynamicDataSource.class);

    private Map<Object, Object> targetDataSources;

    private Object defaultTargetDataSource;

    private boolean lenientFallback = true;

    private DataSourceLookup dataSourceLookup = new JndiDataSourceLookup();

    private Map<Object, DataSource> resolvedDataSources;

    private DataSource resolvedDefaultDataSource;


    /**
     * Specify the map of target DataSources, with the lookup key as key.
     * The mapped value can either be a corresponding {@link javax.sql.DataSource}
     * instance or a data source name String (to be resolved via a
     * {@link #setDataSourceLookup DataSourceLookup}).
     * <p>The key can be of arbitrary type; this class implements the
     * generic lookup process only. The concrete key representation will
     * be handled by {@link #resolveSpecifiedLookupKey(Object)} and
     * {@link #determineCurrentLookupKey()}.
     */
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        this.targetDataSources = targetDataSources;
    }

    /**
     * Specify the default target DataSource, if any.
     * <p>The mapped value can either be a corresponding {@link javax.sql.DataSource}
     * instance or a data source name String (to be resolved via a
     * {@link #setDataSourceLookup DataSourceLookup}).
     * <p>This DataSource will be used as target if none of the keyed
     * {@link #setTargetDataSources targetDataSources} match the
     * {@link #determineCurrentLookupKey()} current lookup key.
     */
    public void setDefaultTargetDataSource(Object defaultTargetDataSource) {
        this.defaultTargetDataSource = defaultTargetDataSource;
    }

    /**
     * Specify whether to apply a lenient fallback to the default DataSource
     * if no specific DataSource could be found for the current lookup key.
     * <p>Default is "true", accepting lookup keys without a corresponding entry
     * in the target DataSource map - simply falling back to the default DataSource
     * in that case.
     * <p>Switch this flag to "false" if you would prefer the fallback to only apply
     * if the lookup key was <code>null</code>. Lookup keys without a DataSource
     * entry will then lead to an IllegalStateException.
     *
     * @see #setTargetDataSources
     * @see #setDefaultTargetDataSource
     * @see #determineCurrentLookupKey()
     */
    public void setLenientFallback(boolean lenientFallback) {
        this.lenientFallback = lenientFallback;
    }

    /**
     * Set the DataSourceLookup implementation to use for resolving data source
     * name Strings in the {@link #setTargetDataSources targetDataSources} map.
     * <p>Default is a {@link JndiDataSourceLookup}, allowing the JNDI names
     * of application server DataSources to be specified directly.
     */
    public void setDataSourceLookup(DataSourceLookup dataSourceLookup) {
        this.dataSourceLookup = (dataSourceLookup != null ? dataSourceLookup : new JndiDataSourceLookup());
    }


    public void afterPropertiesSet() {
        if (this.targetDataSources == null) {
            throw new IllegalArgumentException("Property 'targetDataSources' is required");
        }
        this.resolvedDataSources = new HashMap<Object, DataSource>();
        for (Map.Entry entry : this.targetDataSources.entrySet()) {
            Object lookupKey = resolveSpecifiedLookupKey(entry.getKey());
            DataSource dataSource = resolveSpecifiedDataSource(entry.getValue());
            this.resolvedDataSources.put(lookupKey, dataSource);
        }
        if (this.defaultTargetDataSource != null) {
            this.resolvedDefaultDataSource = resolveSpecifiedDataSource(this.defaultTargetDataSource);
        }

    }


    /**
     * Resolve the specified data source object into a DataSource instance.
     * <p>The default implementation handles DataSource instances and data source
     * names (to be resolved via a {@link #setDataSourceLookup DataSourceLookup}).
     *
     * @param dataSource the data source value object as specified in the
     *                   {@link #setTargetDataSources targetDataSources} map
     * @return the resolved DataSource (never <code>null</code>)
     * @throws IllegalArgumentException in case of an unsupported value type
     */
    protected DataSource resolveSpecifiedDataSource(Object dataSource) throws IllegalArgumentException {
        if (dataSource instanceof DataSource) {
            return (DataSource) dataSource;
        } else if (dataSource instanceof String) {
            return this.dataSourceLookup.getDataSource((String) dataSource);
        } else {
            throw new IllegalArgumentException(
                    "Illegal data source value - only [javax.sql.DataSource] and String supported: " + dataSource);
        }
    }

    /**
     * Retrieve the current target DataSource. Determines the
     * {@link #determineCurrentLookupKey() current lookup key}, performs
     * a lookup in the {@link #setTargetDataSources targetDataSources} map,
     * falls back to the specified
     * {@link #setDefaultTargetDataSource default target DataSource} if necessary.
     *
     * @see #determineCurrentLookupKey()
     */
    protected DataSource determineTargetDataSource() {
        Assert.notNull(this.resolvedDataSources, "DataSource router not initialized");
        WatchUtils.lap("dynamic-data-source-start");
        Object lookupKey = determineCurrentLookupKey();
        DataSource dataSource = this.resolvedDataSources.get(lookupKey);
        if (dataSource == null) {
            //load tenant app by lookupKey
            App app = SystemCache.getApp4Data(String.valueOf(lookupKey));
            if (app != null && PlatformConstants.TENANT_APP_DB_TYPE_MYSQL == app.getDbType()) {
                dataSource = buildDataSource(app.getDbDriverClass(), app.getDbURL(), app.getDbUsername(), app.getDbPassword());
                LOG.debug("===>Data source init, key : " + lookupKey + ", url:" + app.getDbURL());
                if (dataSource != null) {
                    this.resolvedDataSources.put(lookupKey, dataSource);
                }
            }
        }
        LOG.debug("===>Data source found, key : " + lookupKey);
        if (dataSource == null && (this.lenientFallback || lookupKey == null)) {
            dataSource = this.resolvedDefaultDataSource;
            LOG.debug("===>Data source fefault");
        }
        if (dataSource == null) {
            throw new IllegalStateException("Cannot determine target DataSource for lookup key [" + lookupKey + "]");
        }
        WatchUtils.lap("dynamic-data-source-end");
        return dataSource;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DBContextHolder.getDbType();
    }

    protected DataSource buildDataSource(String driverClass, String url, String username, String password) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }


}
