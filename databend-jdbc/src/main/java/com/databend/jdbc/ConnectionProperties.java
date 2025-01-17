package com.databend.jdbc;

import com.databend.client.PaginationOptions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.sql.Connection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

// all possible JDBC properties options currently supported by databend driver
public final class ConnectionProperties {
    public static final ConnectionProperty<String> USER = new User();
    public static final ConnectionProperty<String> PASSWORD = new Password();
    public static final ConnectionProperty<Boolean> SSL = new Ssl();
    public static final ConnectionProperty<String> DATABASE = new Database();
    public static final ConnectionProperty<String> ACCESS_TOKEN = new AccessToken();

    public static final ConnectionProperty<Integer> CONNECTION_TIMEOUT = new ConnectionTimeout();

    public static final ConnectionProperty<Boolean> PRESIGNED_URL_DISABLED = new PresignedUrlDisabled();
    public static final ConnectionProperty<Boolean> COPY_PURGE = new CopyPurge();
    public static final ConnectionProperty<Integer> WAIT_TIME_SECS = new WaitTimeSecs();

    public static final ConnectionProperty<Integer> MAX_ROWS_IN_BUFFER = new MaxRowsInBuffer();
    public static final ConnectionProperty<Integer> MAX_ROWS_PER_PAGE = new MaxRowsPerPage();

    private static final Set<ConnectionProperty<?>> ALL_PROPERTIES = ImmutableSet.<ConnectionProperty<?>>builder()
            .add(USER)
            .add(PASSWORD)
            .add(SSL)
            .add(DATABASE)
            .add(ACCESS_TOKEN)
            .add(PRESIGNED_URL_DISABLED)
            .add(CONNECTION_TIMEOUT)
            .add(WAIT_TIME_SECS)
            .add(MAX_ROWS_IN_BUFFER)
            .add(MAX_ROWS_PER_PAGE)
            .build();
    private static final Map<String, String> DEFAULTS;

    public static Set<ConnectionProperty<?>> allProperties() {
        return ALL_PROPERTIES;
    }

    public static Map<String, String> getDefaults() {
        return DEFAULTS;
    }

    private static class User
            extends AbstractConnectionProperty<String> {
        public User() {
            super("user", NOT_REQUIRED, ALLOWED, NON_EMPTY_STRING_CONVERTER);
        }
    }

    private static class Password
            extends AbstractConnectionProperty<String> {
        public Password() {
            super("password", NOT_REQUIRED, ALLOWED, STRING_CONVERTER);
        }
    }

    // whether enable ssl or not default is true
    private static class Ssl
            extends AbstractConnectionProperty<Boolean> {
        public Ssl() {
            super("ssl", Optional.of("false"), NOT_REQUIRED, ALLOWED, BOOLEAN_CONVERTER);
        }
    }

    private static class Database
            extends AbstractConnectionProperty<String> {
        public Database() {
            super("database", Optional.of("default"), NOT_REQUIRED, ALLOWED, STRING_CONVERTER);
        }
    }

    private static class AccessToken
            extends AbstractConnectionProperty<String> {
        public AccessToken() {
            super("accesstoken", NOT_REQUIRED, ALLOWED, STRING_CONVERTER);
        }
    }

    private static class PresignedUrlDisabled
            extends AbstractConnectionProperty<Boolean> {
        public PresignedUrlDisabled() {
            super("presigned_url_disabled", Optional.of("false"), NOT_REQUIRED, ALLOWED, BOOLEAN_CONVERTER);
        }
    }
    private static class CopyPurge extends AbstractConnectionProperty<Boolean> {
        public CopyPurge() {
            super("copy_purge", Optional.of("true"), NOT_REQUIRED, ALLOWED, BOOLEAN_CONVERTER);
        }
    }

    private static class ConnectionTimeout
            extends AbstractConnectionProperty<Integer> {
        public ConnectionTimeout() {
            super("connection_timeout", Optional.of(String.valueOf(15)), NOT_REQUIRED, ALLOWED, INTEGER_CONVERTER);
        }
    }

    private static class WaitTimeSecs
            extends AbstractConnectionProperty<Integer> {
        public WaitTimeSecs() {
            super("wait_time_secs", Optional.of(String.valueOf(PaginationOptions.getDefaultWaitTimeSec())), NOT_REQUIRED, ALLOWED, INTEGER_CONVERTER);
        }
    }

    private static class MaxRowsInBuffer
            extends AbstractConnectionProperty<Integer> {
        public MaxRowsInBuffer() {
            super("max_rows_in_buffer", Optional.of(String.valueOf(PaginationOptions.getDefaultMaxRowsInBuffer())), NOT_REQUIRED, ALLOWED, INTEGER_CONVERTER);
        }
    }

    private static class MaxRowsPerPage
            extends AbstractConnectionProperty<Integer> {
        public MaxRowsPerPage() {
            super("max_rows_per_page", Optional.of(String.valueOf(PaginationOptions.getDefaultMaxRowsPerPage())), NOT_REQUIRED, ALLOWED, INTEGER_CONVERTER);
        }
    }

    static {
        ImmutableMap.Builder<String, String> defaults = ImmutableMap.builder();
        for (ConnectionProperty<?> property : ALL_PROPERTIES) {
            property.getDefault().ifPresent(value -> defaults.put(property.getKey(), value));
        }
        DEFAULTS = defaults.buildOrThrow();
    }
}
