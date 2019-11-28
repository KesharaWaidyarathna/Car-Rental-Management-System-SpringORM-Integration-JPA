package lk.ijse.dep.MostWantedCabs.DTO;

import java.sql.Date;

public class ReturnDTO {
    private String issueId;
    private Date returnDate;
    private int additionalDistance;
    private double damageCost;
    private double total;

    public ReturnDTO(String issueId, Date returnDate, int additionalDistance, double damageCost, double total) {
        this.setIssueId(issueId);
        this.setReturnDate(returnDate);
        this.setAdditionalDistance(additionalDistance);
        this.setDamageCost(damageCost);
        this.setTotal(total);
    }

    public ReturnDTO() {
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public int getAdditionalDistance() {
        return additionalDistance;
    }

    public void setAdditionalDistance(int additionalDistance) {
        this.additionalDistance = additionalDistance;
    }

    public double getDamageCost() {
        return damageCost;
    }

    public void setDamageCost(double damageCost) {
        this.damageCost = damageCost;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ReturnDTO{" +
                "issueId='" + issueId + '\'' +
                ", returnDate=" + returnDate +
                ", additionalDistance=" + additionalDistance +
                ", damageCost=" + damageCost +
                ", total=" + total +
                '}';
    }
}
