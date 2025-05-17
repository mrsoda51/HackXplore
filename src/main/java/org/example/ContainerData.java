package org.example;

public class ContainerData {
    public String containerId;
    public String district;
    public String address;
    public String date;
    public String time;
    public double fillLevel;

    public ContainerData(String containerId, String district, String address, String date, String time, double fillLevel) {
        this.containerId = containerId;
        this.district = district;
        this.address = address;
        this.date = date;
        this.time = time;
        this.fillLevel = fillLevel;
    }
}

