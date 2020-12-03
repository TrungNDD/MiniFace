/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trungndd.resources;

import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

/**
 *
 * @author Admin
 */
public class MyProperties extends Properties implements Serializable {

    private static final String MEMBER = "trungndd/resources/Member.properties";
    private static final String GUEST = "trungndd/resources/Guest.properties";
    private static final String ADMIN = "trungndd/resources/Admin.properties";

    public MyProperties(String role) throws IOException {
        super();
        if (role.matches("guest")) {
            this.load(getClass().getClassLoader().getResourceAsStream(GUEST));
        } else if (role.matches("member")) {
            this.load(getClass().getClassLoader().getResourceAsStream(MEMBER));
        } else if (role.matches("admin")){
            this.load(getClass().getClassLoader().getResourceAsStream(ADMIN));
        }
    }

    public String getMyProperty(String key) {
        return this.getProperty(key);
    }
}
