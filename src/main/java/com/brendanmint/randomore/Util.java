/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brendanmint.randomore;

/**
 *
 * @author Kyle
 */
public class Util {
    
    public static int parseIntDef(String obj, int def) {
        int ret = def;
        
        try {
            ret = Integer.parseInt(obj);
        }
        catch (NumberFormatException e) {
        }
        
        return ret;
    }
}
