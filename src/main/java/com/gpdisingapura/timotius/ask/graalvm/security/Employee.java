package com.gpdisingapura.timotius.ask.graalvm.security;

import org.graalvm.polyglot.HostAccess;

public class Employee {
    private final String name;
    Employee(String name) {this.name = name;}

    @HostAccess.Export
    public String getName() {
        System.out.println("== [Inside Employee.getName] ==");
        return name;
    }
}

