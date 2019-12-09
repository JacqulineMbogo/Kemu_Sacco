package com.example.kemu_sacco.Account;

public class Kin_Model {


    private  String name, phone, relation, id;

    public Kin_Model(String name, String phone, String relation, String id) {
        this.name = name;
        this.phone = phone;
        this.relation = relation;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
