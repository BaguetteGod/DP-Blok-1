package P2.data;

import P2.domain.Reiziger;

import java.util.List;

public interface ReizigerDAO {
    Boolean save(Reiziger reiziger);
    Boolean update(Reiziger reiziger);
    Boolean delete(Reiziger reiziger);
    Reiziger findById(int id);
    List<Reiziger> findByGbdatum(String datum);
    List<Reiziger> findAll();
}
