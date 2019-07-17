package org.akceptor.core;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "Thing")
public class Thing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    //@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    //private List<Child> children = new ArrayList<>();

    public Thing() {
    }

    public Thing(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public List<Child> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }

    public void addChild(Child child) {
        this.children.add(child);
    }*/

    @Override
    public String toString() {
        return "Thing {" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Thing things = (Thing) o;

        if (id != things.id) return false;
        return name != null ? name.equals(things.name) : things.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
