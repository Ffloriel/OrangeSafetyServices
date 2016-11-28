package com.example.floriel.orangesafetyservices.objects;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;

@Entity(indexes = {
        @Index(value = "phoneNumber, date DESC", unique = true)
})
public class Contact {

    @Id
    private Long id;

    @NotNull
    private String name;
    private String phoneNumber;
    private Long type;
    private java.util.Date date;

    @Generated(hash = 672515148)
    public Contact() {
    }

    public Contact(Long id) {
        this.id = id;
    }

    @Generated(hash = 606638188)
    public Contact(Long id, @NotNull String name, String phoneNumber, Long type,
                   java.util.Date date) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getType() {
        return this.type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

}