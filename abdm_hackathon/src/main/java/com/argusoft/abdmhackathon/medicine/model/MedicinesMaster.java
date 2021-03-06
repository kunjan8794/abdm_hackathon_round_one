package com.argusoft.abdmhackathon.medicine.model;

import javax.persistence.*;

@Entity
public class MedicinesMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    @Column(columnDefinition = "text")
    private String medicine;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }
    public static class Fields {
        public static final String CODE = "code";
    }
}
