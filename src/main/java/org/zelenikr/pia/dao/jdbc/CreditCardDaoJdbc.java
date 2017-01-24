package org.zelenikr.pia.dao.jdbc;

import org.apache.commons.dbutils.DbUtils;
import org.springframework.stereotype.Repository;
import org.zelenikr.pia.dao.CreditCardDao;
import org.zelenikr.pia.domain.CreditCard;

import java.sql.*;

/**
 * JDBC implementation of the {@link CreditCardDao} interface.
 * @author Roman Zelenik
 */
@Repository
public class CreditCardDaoJdbc extends AbstractDaoJdbc<CreditCard> implements CreditCardDao {

    private static final String TABLE = "zelenikr_rbank_credit_card";
    private static final String CARD_NUMBER = "creditCardNumber";
    private static final String CARD_PIN = "pin";


    @Override
    public CreditCard save(CreditCard card) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet set = null;
//        System.out.println("CreditCardDaoJdbc.save");
        try {
            conn = openConnection();
            if (card.isNew()) {
                stmt = insertStatement(conn, card);
                System.out.println("insert: " + card);
            } else {
                stmt = updateStatement(conn, card);
                System.out.println("update: " + card);
            }
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating or updating credit card failed, no rows affected.");
            }
            if (card.isNew()) {
                set = stmt.getGeneratedKeys();
                if (set.next()) {
                    card.setId(set.getLong(1));
                } else {
                    throw new SQLException("Creating credit card failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn, stmt, set);
        }
        return card;
    }

    @Override
    public CreditCard findOne(Long id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet set = null;

        CreditCard card = null;
//        System.out.println("CreditCardDaoJdbc.findOne");
        try {
            conn = openConnection();
            stmt = conn.prepareStatement("SELECT * FROM " + TABLE + " WHERE " + ID + " = ?");
            stmt.setLong(1, id);
            set = stmt.executeQuery();
            if (set.next()) {
                card = create(set);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn, stmt, set);
        }
        return card;
    }

    @Override
    public void remove(CreditCard toRemove) {
        Connection conn = null;
        PreparedStatement stmt = null;
//        System.out.println("CreditCardDaoJdbc.remove");

        if (!toRemove.isNew()) {
            try {
                conn = openConnection();
                stmt = conn.prepareStatement("DELETE FROM " + TABLE + " WHERE " + ID + " = ?");
                stmt.setLong(1, toRemove.getId());
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DbUtils.closeQuietly(stmt);
                DbUtils.closeQuietly(conn);
            }
        }
    }

    @Override
    public CreditCard findByCardNumber(String creditCardNumber) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet set = null;

        CreditCard card = null;
//        System.out.println("CreditCardDaoJdbc.findByCardNumber");
        try {
            conn = openConnection();
            stmt = conn.prepareStatement("SELECT * FROM " + TABLE + " WHERE " + CARD_NUMBER + " = ?");
            stmt.setString(1, creditCardNumber);
            set = stmt.executeQuery();
            if (set.next()) {
                card = create(set);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn, stmt, set);
        }
        return card;
    }

    private CreditCard create(ResultSet actual) throws SQLException {
        CreditCard card = new CreditCard();
        card.setId(actual.getLong(ID));
        card.setCreditCardNumber(actual.getString(CARD_NUMBER));
        card.setPin(actual.getInt(CARD_PIN));
        return card;
    }

    private PreparedStatement insertStatement(Connection connection, CreditCard card) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO " + TABLE + " (" + CARD_NUMBER + ", " + CARD_PIN + ") VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS
        );
        stmt.setString(1, card.getCreditCardNumber());
        stmt.setInt(2, card.getPin());
        return stmt;
    }

    private PreparedStatement updateStatement(Connection connection, CreditCard card) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                "UPDATE " + TABLE + " SET " + CARD_NUMBER + " = ?, " + CARD_PIN + " = ? WHERE " + ID + " = ?"
        );
        stmt.setString(1, card.getCreditCardNumber());
        stmt.setInt(2, card.getPin());
        stmt.setLong(3, card.getId());
        return stmt;
    }
}
