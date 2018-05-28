package com.epam.final_task.dao.connection_pool;

import com.epam.final_task.exception.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

/**
 * Class {@code ConnectionPool} need for optimize getting connection to database.
 * @author Ihnat Mikhalkovich\
 * @since 1.0
 */
public class ConnectionPool {

    /**
     * Static variable {@code instance} of type {@code ConnectionPool}.
     */
    private static final ConnectionPool instance = new ConnectionPool();
    private final static Logger log = Logger.getLogger(ConnectionPool.class);
    /**
     * Define variable for default size of {@code connectionQueue}.
     *
     * @see #connectionQueue
     */
    private final int DEFAULT_POOL_SIZE = 5;
    /**
     * Define the queue of all connections.
     */
    private BlockingQueue<Connection> connectionQueue;
    /**
     * Define the queue of all given away connections.
     */
    private BlockingQueue<Connection> givenAwayConQueue;
    /**
     * Define variable for name of driver.
     */
    private String driverName;
    /**
     * Define variable for url of database.
     */
    private String url;
    /**
     * Define variable for username from database.
     */
    private String user;
    /**
     * Define variable for username password from database.
     */
    private String password;
    /**
     * Define variable for size of {@code connectionQueue}.
     *
     * @see #connectionQueue
     */
    private int poolSize;

    /** Don't let anyone else instantiate this class */
    private ConnectionPool() {
        DBResourceManager dbResourceManager = DBResourceManager.getInstance();
        this.driverName = dbResourceManager.getValue(DBParameter.DB_DRIVER);
        this.url = dbResourceManager.getValue(DBParameter.DB_URL);
        this.user = dbResourceManager.getValue(DBParameter.DB_USER);
        this.password = dbResourceManager.getValue(DBParameter.DB_PASSWORD);
        try {
            this.poolSize = Integer.parseInt(dbResourceManager.getValue(DBParameter.DB_POOL_SIZE));
        } catch (NumberFormatException e) {
            poolSize = DEFAULT_POOL_SIZE;
        }
        try {
            initPoolData();
        } catch (ConnectionPoolException e) {
            log.warn("Calling a method initPoolData() in constructor threw an exception.", e);
        }
    }

    /**
     * Most of the methods of class {@code ConnectionPool} are instance
     * methods and must be invoked with respect to the current runtime object.
     * @return the {@code ConnectionPool} object associated with the current Java application.
     */
    public static ConnectionPool getInstance() {
        return instance;
    }

    public void initPoolData() throws ConnectionPoolException {
        try {
            Class.forName(driverName);
            givenAwayConQueue = new ArrayBlockingQueue<>(poolSize);
            connectionQueue = new ArrayBlockingQueue<>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                PooledConnection pooledConnection = new PooledConnection(connection);
                connectionQueue.add(pooledConnection);
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException("SQLException in ConnectionPool.", e);
        } catch (ClassNotFoundException e) {
            throw new ConnectionPoolException("Can't find database driver class.", e);
        }
    }

    /**
     * Close all connections from {@code givenAwayConQueue}.
     * @see #givenAwayConQueue
     */
    public void dispose() {
        clearConnectionQueue();
    }

    private void clearConnectionQueue() {
        try {
            closeConnectionQueue(givenAwayConQueue);
        } catch (SQLException e) {
            log.error("Error closing the connection.", e);
        }
    }

    /**
     * Allows you to take a connection from the {@code connectionQueue}. The connection is transferred to the {@code givenAwayConQueue}.
     * @see #connectionQueue
     * @see #givenAwayConQueue
     * @return the connection to the database.
     * @throws  ConnectionPoolException
     *          If you receive a connection error to the data source.
     */
    public Connection takeConnection() throws ConnectionPoolException {
        Connection connection = null;
        try {
            connection = connectionQueue.take();
            givenAwayConQueue.add(connection);
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Error connecting to the data source.", e);
        }
        return connection;
    }

    /**
     * Calls the close() method of the input parameter.
     * @param connection instance of class Connection.
     * @param statement instance of class Statement.
     * @param resultSet instance of class ResultSet.
     * @see Connection
     * @see Statement
     * @see ResultSet
     */
    public void closeConnection(Connection connection, Statement statement, ResultSet resultSet) {
        if (resultSet != null) {
            closeResultSet(resultSet);
        }
        if (statement != null) {
            closeStatement(statement);
        }
        if (connection != null) {
            closeConnection(connection);
        }
    }

    /**
     * Calls the close() method of the input parameter.
     * @param connection instance of class Connection.
     * @param statement instance of class Statement.
     * @see Connection
     * @see Statement
     */
    public void closeConnection(Connection connection, Statement statement) {
        if (statement != null) {
            closeStatement(statement);
        }
        if (connection != null) {
            closeConnection(connection);
        }
    }

    /**
     * Calls the close() method of the input parameter.
     * @param connection instance of class Connection.
     * @param statements instances of class Statement.
     * @see Connection
     * @see Statement
     */
    public void closeConnection(Connection connection, Statement... statements) {
        for (Statement statement : statements) {
            if (statement != null) {
                closeStatement(statement);
            }
        }
        if (connection != null) {
            closeConnection(connection);
        }
    }

    /**
     * Calls the close() method of the input parameter.
     * @param connection instance of class Connection.
     * @param resultSet instance of class ResultSet.
     * @param statements instances of class Statement.
     * @see Connection
     * @see ResultSet
     * @see Statement
     */
    public void closeConnection(Connection connection , ResultSet resultSet, Statement... statements) {
        for (Statement statement : statements) {
            if (statement != null) {
                closeStatement(statement);
            }
        }
        if (resultSet != null) {
            closeResultSet(resultSet);
        }
        if (connection != null) {
            closeConnection(connection);
        }
    }

    private void closeStatement(Statement statement) {
        try {
            statement.close();
        } catch (SQLException e) {
            log.error("The statement was not closed.", e);
        }
    }

    private void closeResultSet(ResultSet resultSet) {
        try {
            resultSet.close();
        } catch (SQLException e) {
            log.error("The statement was not closed.", e);
        }
    }

    private void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            log.error("The connection was not closed.", e);
        }
    }

    private void closeConnectionQueue(BlockingQueue<Connection> queue) throws SQLException {
        Connection connection;
        while ((connection = queue.poll()) != null) {
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
            ((PooledConnection) connection).reallyClose();
        }
    }

    /**
     * The inner class that is the implementation of the interface Connection.
     * @see Connection
     */
    private class PooledConnection implements Connection {

        private Connection connection;

        public PooledConnection(Connection connection) throws SQLException {
            this.connection = connection;
            this.connection.setAutoCommit(true);
        }

        public void reallyClose() throws SQLException {
            connection.close();
        }

        @Override
        public void clearWarnings() throws SQLException {
            connection.clearWarnings();
        }

        @Override
        public void close() throws SQLException {
            if (connection.isClosed()) {
                throw new SQLException("Attempt to close closed connection.");
            }

            if (connection.isReadOnly()) {
                connection.setReadOnly(false);
            }

            if (!givenAwayConQueue.remove(this)) {
                throw new SQLException("Error deleting connection from the given away connections pool.");
            }

            if (!connectionQueue.offer(this)) {
                throw new SQLException("Error allocating connection in the pool.");
            }
        }

        @Override
        public Statement createStatement() throws SQLException {
            return connection.createStatement();
        }

        @Override
        public PreparedStatement prepareStatement(String sql) throws SQLException {
            return connection.prepareStatement(sql);
        }

        @Override
        public CallableStatement prepareCall(String sql) throws SQLException {
            return connection.prepareCall(sql);
        }

        @Override
        public String nativeSQL(String sql) throws SQLException {
            return connection.nativeSQL(sql);
        }

        @Override
        public boolean getAutoCommit() throws SQLException {
            return connection.getAutoCommit();
        }

        @Override
        public void setAutoCommit(boolean autoCommit) throws SQLException {
            connection.setAutoCommit(autoCommit);
        }

        @Override
        public void commit() throws SQLException {
            connection.commit();
        }

        @Override
        public void rollback() throws SQLException {
            connection.rollback();
        }

        @Override
        public boolean isClosed() throws SQLException {
            return connection.isClosed();
        }

        @Override
        public DatabaseMetaData getMetaData() throws SQLException {
            return connection.getMetaData();
        }

        @Override
        public boolean isReadOnly() throws SQLException {
            return connection.isReadOnly();
        }

        @Override
        public void setReadOnly(boolean readOnly) throws SQLException {
            connection.setReadOnly(readOnly);
        }

        @Override
        public String getCatalog() throws SQLException {
            return connection.getCatalog();
        }

        @Override
        public void setCatalog(String catalog) throws SQLException {
            connection.setCatalog(catalog);
        }

        @Override
        public int getTransactionIsolation() throws SQLException {
            return connection.getTransactionIsolation();
        }

        @Override
        public void setTransactionIsolation(int level) throws SQLException {
            connection.setTransactionIsolation(level);
        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            return connection.getWarnings();
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.createStatement(resultSetType, resultSetConcurrency);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public Map<String, Class<?>> getTypeMap() throws SQLException {
            return connection.getTypeMap();
        }

        @Override
        public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
            connection.setTypeMap(map);
        }

        @Override
        public int getHoldability() throws SQLException {
            return connection.getHoldability();
        }

        @Override
        public void setHoldability(int holdability) throws SQLException {
            connection.setHoldability(holdability);
        }

        @Override
        public Savepoint setSavepoint() throws SQLException {
            return connection.setSavepoint();
        }

        @Override
        public Savepoint setSavepoint(String name) throws SQLException {
            return connection.setSavepoint(name);
        }

        @Override
        public void rollback(Savepoint savepoint) throws SQLException {
            connection.rollback(savepoint);
        }

        @Override
        public void releaseSavepoint(Savepoint savepoint) throws SQLException {
            connection.releaseSavepoint(savepoint);
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
            return connection.prepareStatement(sql, autoGeneratedKeys);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
            return connection.prepareStatement(sql, columnIndexes);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
            return connection.prepareStatement(sql, columnNames);
        }

        @Override
        public Clob createClob() throws SQLException {
            return connection.createClob();
        }

        @Override
        public Blob createBlob() throws SQLException {
            return connection.createBlob();
        }

        @Override
        public NClob createNClob() throws SQLException {
            return connection.createNClob();
        }

        @Override
        public SQLXML createSQLXML() throws SQLException {
            return connection.createSQLXML();
        }

        @Override
        public boolean isValid(int timeout) throws SQLException {
            return connection.isValid(timeout);
        }

        @Override
        public void setClientInfo(String name, String value) throws SQLClientInfoException {
            connection.setClientInfo(name, value);
        }

        @Override
        public String getClientInfo(String name) throws SQLException {
            return connection.getClientInfo(name);
        }

        @Override
        public Properties getClientInfo() throws SQLException {
            return connection.getClientInfo();
        }

        @Override
        public void setClientInfo(Properties properties) throws SQLClientInfoException {
            connection.setClientInfo(properties);
        }

        @Override
        public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
            return connection.createArrayOf(typeName, elements);
        }

        @Override
        public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
            return connection.createStruct(typeName, attributes);
        }

        @Override
        public String getSchema() throws SQLException {
            return connection.getSchema();
        }

        @Override
        public void setSchema(String schema) throws SQLException {
            connection.setSchema(schema);
        }

        @Override
        public void abort(Executor executor) throws SQLException {
            connection.abort(executor);
        }

        @Override
        public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
            connection.setNetworkTimeout(executor, milliseconds);
        }

        @Override
        public int getNetworkTimeout() throws SQLException {
            return connection.getNetworkTimeout();
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return connection.unwrap(iface);
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return connection.isWrapperFor(iface);
        }
    }

}
