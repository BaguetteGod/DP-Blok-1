package P2.data;

import P2.domain.Reiziger;

import java.sql.Connection;
import java.util.List;

public class ReizigerDAOsql implements ReizigerDAO {

    Connection conn;

    public ReizigerDAOsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Boolean save(Reiziger reiziger) {
        return null;
    }

    @Override
    public Boolean update(Reiziger reiziger) {
        return null;
    }

    @Override
    public Boolean delete(Reiziger reiziger) {
        return null;
    }

    @Override
    public Reiziger findById(int id) {
        return null;
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        return null;
    }

    @Override
    public List<Reiziger> findAll() {
        return null;
    }
}
