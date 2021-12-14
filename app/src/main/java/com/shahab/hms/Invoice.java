package com.shahab.hms;

public class Invoice {
    String roomId, packageId, customerId;
    int duePayment, paidAmount, checkoutStatus;

    public Invoice(String roomId, String packageId, String customerId, int duePayment, int paidAmount, int checkoutStatus) {
        this.roomId = roomId;
        this.packageId = packageId;
        this.customerId = customerId;
        this.duePayment = duePayment;
        this.paidAmount = paidAmount;
        this.checkoutStatus = checkoutStatus;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getDuePayment() {
        return duePayment;
    }

    public void setDuePayment(int duePayment) {
        this.duePayment = duePayment;
    }

    public int getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(int paidAmount) {
        this.paidAmount = paidAmount;
    }

    public int getCheckoutStatus() {
        return checkoutStatus;
    }

    public void setCheckoutStatus(int checkoutStatus) {
        this.checkoutStatus = checkoutStatus;
    }
}
