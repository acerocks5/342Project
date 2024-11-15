package model;

public class Child {
    private int age;
    private String name;
    private String parent;

    public Child(int age, String name, String parent) {
        this.age = age;
        this.name = name;
        this.parent = parent;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
