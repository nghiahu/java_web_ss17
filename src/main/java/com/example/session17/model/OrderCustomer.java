package com.example.session17.model;

import javax.validation.constraints.NotBlank;

public class OrderCustomer {
    @NotBlank(message = "Tên người nhận không được để trống")
    private String name;
    @NotBlank(message = "Số điện thoại không được để trống")
    private String phoneNumber;
    @NotBlank(message = "Địa chỉ không được để trống")
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
