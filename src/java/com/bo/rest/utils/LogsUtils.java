/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bo.rest.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

/**
 *
 * @author aarauco2608
 */
public class LogsUtils {

    public static Logger getLogger(String name) {
        Logger logger = LoggerFactory.getLogger(name);

        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        JoranConfigurator jc = new JoranConfigurator();
        context.reset(); // override default configuration
        context.putProperty("CLIENT_NAME", name);
        jc.setContext(context);

        try {
            // Archivo que obtiene la configuracion del log
            jc.doConfigure("/home/pagos/bin/logback-config.xml");//"C:\\Users\\IGNACIO\\Documents\\NetBeansProjects\\cashBo\\logback-config.xml");
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return logger;
    }
}
