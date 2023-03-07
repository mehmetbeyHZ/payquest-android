package com.payquestion.payquest.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentRequestResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("payment_requests")
    @Expose
    private List<PaymentRequest> paymentRequests = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PaymentRequest> getPaymentRequests() {
        return paymentRequests;
    }

    public void setPaymentRequests(List<PaymentRequest> paymentRequests) {
        this.paymentRequests = paymentRequests;
    }

}
