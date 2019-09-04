package com.example.databasedemo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserObject {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String name;

    private Long age;

    @Generated(hash = 1300257024)
    public UserObject() {
    }

    @Generated(hash = 961160741)
    public UserObject(Long id, @NotNull String name, Long age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return this.id;
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

    public Long getAge() {
        return this.age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String toString() {
        return "ID:" + id + '\t' + "Name:" + name + '\t' + "Age:" + age;
    }

}
