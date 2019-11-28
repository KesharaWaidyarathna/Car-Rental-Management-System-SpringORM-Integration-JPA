package lk.ijse.dep.MostWantedCabs.DAO.custom.impl;

import lk.ijse.dep.MostWantedCabs.DAO.custom.QuaryDAO;
import lk.ijse.dep.MostWantedCabs.Entity.CustomEntity;
import lk.ijse.dep.MostWantedCabs.Entity.CustomReturnEntity;
import lk.ijse.dep.MostWantedCabs.Entity.Vehicle;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;

import javax.persistence.EntityManager;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QuaryDAOImpl implements QuaryDAO {

    private EntityManager entityManager;

    @Override
    public void setEntityMaanager(EntityManager entityMaanager) {
        this.entityManager=entityMaanager;
    }

    @Override
    public List<CustomEntity> getIssueinfo() throws Exception {

        List<Object[]> resultList= entityManager.createNativeQuery("SELECT issue.id AS id,issue.date AS date,issue.customerId AS customerId,customer.name AS customerName,issue.vehicleId AS vehicleId,vehicle.modelName AS vehicleModel" +
                ",issuedetail.driverId AS driverStatues,issue.statues AS Statues FROM issue LEFT JOIN issuedetail  on issue.id = issuedetail.issueId INNER JOIN " +
                "vehicle on issue.vehicleId = vehicle.id INNER JOIN customer on issue.customerId = customer.id GROUP BY issue.id").getResultList();

        List<CustomEntity> lis = new ArrayList<>();
        for (Object[] cols : resultList) {
            lis.add(new CustomEntity((String)cols[0],(Date)cols[1],(String)cols[2],(String)cols[3],(String)cols[4],(String)cols[5],(String)cols[6],(String)cols[7]));
        }
        return lis;
    }

    @Override
    public boolean updateStatues(CustomEntity customEntity) throws Exception {
         return entityManager.createNativeQuery("UPDATE issue SET statues=?1 WHERE id=?2").setParameter(1, customEntity.getStatues()).setParameter(2, customEntity.getId())!=null;

    }

    @Override
    public List<CustomReturnEntity> getReturnInfo() throws Exception {
        List<Object[]> resultList = entityManager.createNativeQuery("SELECT issue.date AS issueDate,`return`.returnDate AS returnDate,`return`.issueId AS issueId,`return`.additionalDistance AS additionalKilometers,`return`.damageCost AS costOfDamage,vehicle.modelName AS vehicleModel,customer.name AS customerModel,`return`.total AS total " +
                "FROM `return` INNER JOIN issue on `return`.issueId = issue.id INNER JOIN vehicle on issue.vehicleId = vehicle.id INNER JOIN customer on issue.customerId =customer.id ORDER BY `return`.issueId").getResultList();

        List<CustomReturnEntity> list =new ArrayList<>();
        for (Object[] cols : resultList) {
            list.add(new CustomReturnEntity((Date) cols[0],(Date)cols[1],(String) cols[2],(String)cols[3],(double)cols[4],(String)cols[5],(String)cols[6],(double)cols[7]));
        }
        return list;

    }

    @Override
    public List<Vehicle> SearchVehicle(String search) throws Exception {

        return entityManager.createNativeQuery("SELECT * FROM vehicle WHERE id LIKE ?1 OR registerNo LIKE ?1 OR categoryId LIKE ?1 OR modelName LIKE ?1 OR statues LIKE ?1 OR ownerId LIKE ?1 GROUP by id").setParameter(1,search).getResultList();
    }


}
