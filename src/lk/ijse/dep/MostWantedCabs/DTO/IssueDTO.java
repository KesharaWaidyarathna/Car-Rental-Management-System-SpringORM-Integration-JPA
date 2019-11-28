package lk.ijse.dep.MostWantedCabs.DTO;

import java.sql.Date;

public class IssueDTO {
    private String id;
    private Date date;
    private String vehicleId;
    private String customerId;
    private String statues;
    private String driverID;

    public IssueDTO(String id, Date date, String vehicleId, String customerId, String statues, String driverID) {
        this.id = id;
        this.date = date;
        this.vehicleId = vehicleId;
        this.customerId = customerId;
        this.statues = statues;
        this.driverID = driverID;
    }

    public IssueDTO(String id, Date date, String vehicleId, String customerId, String statues) {
        this.setId(id);
        this.setDate(date);
        this.setVehicleId(vehicleId);
        this.setCustomerId(customerId);
        this.setStatues(statues);
    }
    public IssueDTO(Date date, String vehicleId, String customerId, String statues) {
        this.setDate(date);
        this.setVehicleId(vehicleId);
        this.setCustomerId(customerId);
        this.setStatues(statues);
    }

    public IssueDTO() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getStatues() {
        return statues;
    }

    public void setStatues(String statues) {
        this.statues = statues;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driver) {
        this.driverID = driver;
    }

    @Override
    public String toString() {
        return "IssueDTO{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", vehicleId='" + vehicleId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", statues='" + statues + '\'' +
                '}';
    }


}
