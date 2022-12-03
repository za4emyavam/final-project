package DAOTests;

import com.myfp.fp.entities.Transaction;
import com.myfp.fp.entities.TransactionType;
import com.myfp.fp.service.TransactionService;
import com.myfp.fp.util.ConnectionPool;
import com.myfp.fp.util.MainServiceFactoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class TransactionDAOTest {
    @Test
    public void createTest() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next())
                .thenReturn(true)
                .thenReturn(false);

        when(rs.getInt(1)).thenReturn(112);

        PreparedStatement statement = mock(PreparedStatement.class);
        when(statement.executeUpdate()).thenReturn(1);
        when(statement.getGeneratedKeys()).thenReturn(rs);

        Connection con = mock(Connection.class);
        when(con.prepareStatement("INSERT INTO transaction" +
                        "(balance_id, type, transaction_amount, transaction_date) " +
                        "VALUES (?, ?::transaction_type, ?, DEFAULT);",
                Statement.RETURN_GENERATED_KEYS
        )).thenReturn(statement);

        try (MockedStatic<ConnectionPool> conPool = mockStatic(ConnectionPool.class)) {
            conPool.when(ConnectionPool::getConnection).thenReturn(con);

            Transaction transaction = new Transaction();
            transaction.setBalanceId(21);
            transaction.setType(TransactionType.DEBIT);
            transaction.setTransactionAmount(220);

            long expected = 112L;

            TransactionService service = MainServiceFactoryImpl.getInstance().getTransactionService();
            long actual = service.insertTransaction(transaction);

            Assertions.assertEquals(expected, actual);
        }
    }

    @Test
    public void readAllTest() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        when(rs.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        when(rs.getLong("transaction_id")).thenReturn(23L).thenReturn(45L);
        when(rs.getInt("balance_id")).thenReturn(12).thenReturn(12);
        when(rs.getString("type")).thenReturn("debit").thenReturn("refill");
        when(rs.getInt("transaction_amount")).thenReturn(120).thenReturn(200);
        when(rs.getDate("transaction_date"))
                .thenReturn(Date.valueOf("2022-11-27"))
                .thenReturn(Date.valueOf("2022-11-30"));

        PreparedStatement statement = mock(PreparedStatement.class);
        when(statement.executeQuery()).thenReturn(rs);

        Connection con = mock(Connection.class);
        when(con.prepareStatement("SELECT * FROM transaction t WHERE t.balance_id=(?)"))
                .thenReturn(statement);

        try (MockedStatic<ConnectionPool> conPool = mockStatic(ConnectionPool.class)) {
            conPool.when(ConnectionPool::getConnection).thenReturn(con);

            List<Transaction> expected = new ArrayList<>();

            Transaction firstTransaction = new Transaction();
            firstTransaction.setId(23L);
            firstTransaction.setBalanceId(12);
            firstTransaction.setType(TransactionType.DEBIT);
            firstTransaction.setTransactionAmount(120);
            firstTransaction.setTransactionDate(Date.valueOf("2022-11-27"));
            expected.add(firstTransaction);

            Transaction secondTransaction = new Transaction();
            secondTransaction.setId(45L);
            secondTransaction.setBalanceId(12);
            secondTransaction.setType(TransactionType.REFILL);
            secondTransaction.setTransactionAmount(200);
            secondTransaction.setTransactionDate(Date.valueOf("2022-11-30"));
            expected.add(secondTransaction);

            TransactionService service = MainServiceFactoryImpl.getInstance().getTransactionService();
            List<Transaction> actual = service.readAllTransactionsByUserBalance(12L);

            //first transaction
            Assertions.assertTrue(new ReflectionEquals(actual.get(0)).matches(expected.get(0)));

            //second transaction
            Assertions.assertTrue(new ReflectionEquals(actual.get(1)).matches(expected.get(1)));
        }
    }
}