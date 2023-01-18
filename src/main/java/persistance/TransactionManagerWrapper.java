package persistance;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;


public class TransactionManagerWrapper {
   private static final Logger LOG = Logger.getLogger(TransactionManager.class);

   private static ThreadLocal<ConnectionWrapper> th = new ThreadLocal<>();

   public static ConnectionWrapper getConnection() {
      ConnectionWrapper connectionWrapper = th.get();

      if(connectionWrapper != null){
         return connectionWrapper;
      }

      connectionWrapper = new ConnectionWrapper(ConnectionPoolHolder.pool().getConnection());
      return connectionWrapper;
   }
   public static void startTransaction() throws SQLException {
   ConnectionWrapper connectionWrapper = th.get();
      if (connectionWrapper != null) {
         throw new SQLException("Connection already exists");
      }
      connectionWrapper = new ConnectionWrapper(ConnectionPoolHolder.pool().getConnection());
      connectionWrapper.setAutoCommit(false);
      th.set(connectionWrapper);
   }
   public static void commit() throws SQLException {
      ConnectionWrapper connectionWrapper=th.get();
      if (connectionWrapper==null){
         throw new SQLException("Transaction not started to be commit.");
      }
      connectionWrapper.commit();
      connectionWrapper.setAutoCommit(true);
      connectionWrapper.close();
      th.remove();
   }

   public static void rollback () throws SQLException {
      ConnectionWrapper connectionWrapper=th.get();
      if (connectionWrapper==null){
         throw new SQLException("Transaction not started to be rollback.");
      }
      connectionWrapper.rollback();
      connectionWrapper.setAutoCommit(true);
      connectionWrapper.close();
      th.remove();
   }

   //needed for tests
   public static void setThreadLocal(ThreadLocal<ConnectionWrapper> th) {
      TransactionManagerWrapper.th = th;
   }
}
