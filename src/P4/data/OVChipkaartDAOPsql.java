package P4.data;

import P4.domain.OVChipkaart;
import P4.domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {
    Connection conn;
    ReizigerDAOPsql reizigerDAO;

    public OVChipkaartDAOPsql(Connection conn, ReizigerDAOPsql reizigerDAO) {
        this.reizigerDAO = reizigerDAO;
        this.conn = conn;
    }

    private boolean ovChipkaartStatement(OVChipkaart ovChipkaart, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setDate(1, ovChipkaart.getGeldigTot());
        preparedStatement.setInt(2, ovChipkaart.getKlasse());
        preparedStatement.setDouble(3, ovChipkaart.getSaldo());
        preparedStatement.setInt(4, ovChipkaart.getReiziger().getId());
        preparedStatement.setInt(5, ovChipkaart.getId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        return true;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO ov_chipkaart(geldig_tot, klasse, saldo, reiziger_id, kaart_nummer) VALUES(?, ?, ?, ?, ?)");
            return ovChipkaartStatement(ovChipkaart, preparedStatement);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            // Dit is zwaar onnodig :)
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE ov_chipkaart SET geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id = ? WHERE kaart_nummer = ?");
            return ovChipkaartStatement(ovChipkaart, preparedStatement);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            // Dit is zwaar onnodig :)
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM ov_chipkaart WHERE kaart_nummer = ?");
            preparedStatement.setInt(1, ovChipkaart.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            // Dit is zwaar onnodig :)
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * from ov_chipkaart WHERE reiziger_id = ?");
            preparedStatement.setInt(1, reiziger.getId());
            ResultSet rs = preparedStatement.executeQuery();
            List<OVChipkaart> ovChipkaarts = new ArrayList<>();
            while (rs.next()) {
                OVChipkaart ovChipkaart = new OVChipkaart(
                        rs.getInt("kaart_nummer"),
                        rs.getDate("geldig_tot"),
                        rs.getInt("klasse"),
                        rs.getDouble("saldo"),
                        reiziger);
                ovChipkaarts.add(ovChipkaart);
            }
            preparedStatement.close();
            rs.close();
            return ovChipkaarts;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            // Dit is zwaar onnodig :)
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<OVChipkaart> findAll() {
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * from ov_chipkaart");
            List<OVChipkaart> ovChipkaartList = new ArrayList<>();
            while (resultSet.next()) {
                OVChipkaart ovChipkaart = new OVChipkaart(
                        resultSet.getInt("kaart_nummer"),
                        resultSet.getDate("geldig_tot"),
                        resultSet.getInt("klasse"),
                        resultSet.getDouble("saldo"),
                        reizigerDAO.findById(resultSet.getInt("reiziger_id")));
                ovChipkaartList.add(ovChipkaart);
            }
            statement.close();
            resultSet.close();
            return ovChipkaartList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            // Dit is zwaar onnodig :)
            e.printStackTrace();
            return null;
        }
    }
}
