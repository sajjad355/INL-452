/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.internal.service.somruinternal.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author peter
 */
public class IPConfig {

    public static String getServerIP() {
        try {
          return InetAddress.getLocalHost().getHostAddress();
        }
        catch ( UnknownHostException e ) {
            throw new RuntimeException( e );
        }
    }

}
