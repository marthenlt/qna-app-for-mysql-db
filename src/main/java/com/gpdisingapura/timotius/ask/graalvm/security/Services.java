package com.gpdisingapura.timotius.ask.graalvm.security;

import org.graalvm.polyglot.HostAccess;

public class Services {
    @HostAccess.Export
    public Employee createEmployee(String name) {
        System.out.println("== [Inside Services.createEmployee] ==");
        return new Employee(name);
    }

    public void exitVM() {
        System.out.println("== [Inside Services.exitVM] ==");
        System.exit(1);
        System.out.println("== [After calling System.exit] ==");
    }
}