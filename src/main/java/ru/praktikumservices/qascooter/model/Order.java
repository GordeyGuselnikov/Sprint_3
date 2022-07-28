package ru.praktikumservices.qascooter.model;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class Order {

    public final String firstName;
    public final String lastName;
    public final String address;
    public final int metroStation;
    public final String phone;
    public final int rentTime;
    public final String deliveryDate;
    public final String comment;
    public final String[] color;

    public Order(String firstName, String lastName, String address, int metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    public static Order getOrderWithColor(String[] colors) {
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        final String lastName = RandomStringUtils.randomAlphabetic(10);
        final String address = RandomStringUtils.randomAlphabetic(10);
        final int metroStation = 4;
        final String phone = RandomStringUtils.randomAlphabetic(10);
        final int rentTime = RandomUtils.nextInt(1, 5);
        final String deliveryDate = "2020-06-06";
        final String comment = RandomStringUtils.randomAlphabetic(10);
        final String[] color = colors;
        return new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }

}
