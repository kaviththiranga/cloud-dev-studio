/*
* Copyright (c) 2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.wso2.developerstudio.codenvy.launcher.java;

import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Bootstrap {

    private static final Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    protected static Map<String, String> mapContextToWebApp;
    protected static String webAppRoot;

    static {
        mapContextToWebApp = new HashMap<>();
        mapContextToWebApp.put("/api", "api");
        mapContextToWebApp.put("/datasource", "datasource");
        mapContextToWebApp.put("/java-ca", "java-ca");
        mapContextToWebApp.put("/ide", "ide");
    }

    public static void main(String args[]) {

        logger.info("Starting WSO2 Developer Studio ");

        webAppRoot = args[0];

        if(webAppRoot.equals("") || webAppRoot == null){
            webAppRoot = "../webapps";
        }

        System.out.print("WebAppRoot is : "+ webAppRoot);
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(Integer.valueOf("8080"));
        System.out.print("Host is : "+ tomcat.getHost().toString());

        try {
            addWebApps(tomcat);
        } catch (ServletException e) {
            System.out.print("Error Deploying WebApps");
            e.printStackTrace();
        }


        TomcatLauncher launcher = new TomcatLauncher(tomcat);

        try {
            launcher.launch();
        } catch (Exception ex) {

            ex.printStackTrace();

        }
    }

    private static void addWebApps(Tomcat tomcat) throws ServletException {

        Iterator it = mapContextToWebApp.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry webAppEntry = (Map.Entry)it.next();
            tomcat.addWebapp(webAppEntry.getKey().toString(),
                    new File(webAppRoot + File.separator + webAppEntry.getValue().toString()).getAbsolutePath() );

            System.out.print("Deploying web app : "+  new File(webAppRoot + File.separator + webAppEntry.getValue().toString()).getAbsolutePath());
        }
    }
}
