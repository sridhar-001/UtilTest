/*
 * ========================LICENSE_START=================================
 * CustomTestFilter TestListener
 * %%
 * Copyright (C) 2019 Sridhar Lakshmipuram Srinivasan
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * =========================LICENSE_END==================================
 */
/*
    Copyright (c) 2019.
    All rights reserved. Patents pending.

    $Id: CustomTestFilter $

    Responsible: sridhar lakshmipuram srinivasan.
*/

package com.github.sridhar;

import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class CustomTestFilter implements IMethodInterceptor {

    static Logger logger = Logger.getLogger(CustomTestFilter.class.getName());

    static List excludeClassList = new ArrayList();
    static List excludeXmlSuiteList = new ArrayList();
    static List excludeMethodList = new ArrayList();
    static Boolean excludeClass = false;
    static Boolean excludeSuiteXML = false;
    static Boolean excludeMethod = false;

    static List includeClassList = new ArrayList();
    static List includeXmlSuiteList = new ArrayList();
    static List includeMethodList = new ArrayList();
    static Boolean includeClass = false;
    static Boolean includeSuiteXML = false;
    static Boolean includeMethod = false;

    static {

       if (System.getProperty("excludeClass") != null) {
           String[] className = System.getProperty("excludeClass").split(",");
           excludeClassList = new ArrayList(Arrays.asList(className));
           excludeClass = true;
           logger.info("Excluded Classes :" + excludeClassList);
        }

        if (System.getProperty("excludeSuiteXML") != null) {
            String[] suiteXML = System.getProperty("excludeSuiteXML").split(",");
            excludeXmlSuiteList = new ArrayList(Arrays.asList(suiteXML));
            excludeSuiteXML = true;
            logger.info("Excluded Suites :" + excludeXmlSuiteList);
        }

        if (System.getProperty("excludeMethod") != null) {
            String[] group = System.getProperty("excludeMethod").split(",");
            excludeMethodList = new ArrayList(Arrays.asList(group));
            excludeMethod = true;
            logger.info("Excluded Methods :" + excludeMethodList);
        }

        if (System.getProperty("includeClass") != null) {
            String[] className = System.getProperty("includeClass").split(",");
            includeClassList = new ArrayList(Arrays.asList(className));
            includeClass = true;
            logger.info("Included Classes :" + includeClassList);
        }

        if (System.getProperty("includeSuiteXML") != null) {
            String[] suiteXML = System.getProperty("includeSuiteXML").split(",");
            includeXmlSuiteList = new ArrayList(Arrays.asList(suiteXML));
            includeSuiteXML = true;
            logger.info("Included Suites :" + includeXmlSuiteList);
        }

        if (System.getProperty("includeMethod") != null) {
            String[] group = System.getProperty("includeMethod").split(",");
            includeMethodList = new ArrayList(Arrays.asList(group));
            includeMethod = true;
            logger.info("Included Methods :" + includeMethodList);
        }

        if (excludeClassList.size() > 0 && includeClassList.size() > 0) {
            List tempexcludeClassList = new ArrayList(excludeClassList);
            excludeClassList.removeAll(includeClassList);
            includeClassList.removeAll(tempexcludeClassList);
            logger.info("Removing duplicate names if any");
            logger.info("Excluded Classes :" + excludeClassList);
            logger.info("Included Classes :" + includeClassList);
        }

        if (excludeXmlSuiteList.size() > 0 && includeXmlSuiteList.size() > 0) {
            List tempexcludeXmlSuiteList = new ArrayList(excludeXmlSuiteList);
            excludeXmlSuiteList.removeAll(includeXmlSuiteList);
            includeXmlSuiteList.removeAll(tempexcludeXmlSuiteList);
            logger.info("Removing duplicate names if any");
            logger.info("Excluded Suites :" + excludeXmlSuiteList);
            logger.info("Included Suites :" + includeXmlSuiteList);
        }

        if (excludeMethodList.size() > 0 && includeMethodList.size() > 0) {
            List tempexcludeMethodList = new ArrayList(excludeMethodList);
            excludeMethodList.removeAll(includeMethodList);
            includeMethodList.removeAll(tempexcludeMethodList);
            logger.info("Removing duplicate names if any");
            logger.info("Excluded Methods :" + excludeMethodList);
            logger.info("Included Methods :" + includeMethodList);
        }

    }

    /**
     *
     * @param methods
     * @param context
     * @return
     */
    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {

        List<IMethodInstance> result = new ArrayList<IMethodInstance>();
        String separator = File.separator.replace("\\", "\\\\");
        String[] xml = context.getCurrentXmlTest().getSuite().getFileName().split(separator);
        String xmlSuiteFile = xml[xml.length - 1];

        for (IMethodInstance m : methods) {

            if (excludeClass) {
                if (!excludeClassList.contains(m.getInstance().getClass().getSimpleName())) {
                    result.add(m);
                }
            } else if (excludeSuiteXML) {
                if (!excludeXmlSuiteList.contains(xmlSuiteFile)) {
                    result.add(m);
                }
            } else if (excludeMethod) {
                String method = null;

                if (m.getMethod().getMethodName() != null) {
                    method = m.getMethod().getMethodName();
                }
                if (!excludeMethodList.contains(method)) {
                    result.add(m);
                }
            } else if (includeClass) {
                if (includeClassList.contains(m.getInstance().getClass().getSimpleName())) {
                    result.add(m);
                }
            } else if (includeSuiteXML) {
                if (includeXmlSuiteList.contains(xmlSuiteFile)) {
                    result.add(m);
                }
            } else if (includeMethod) {
                String method = null;

                if (m.getMethod().getMethodName() != null) {
                    method = m.getMethod().getMethodName();
                }
                if (includeMethodList.contains(method)) {
                    result.add(m);
                }
            } else if (!excludeMethod && !excludeSuiteXML && !excludeClass && !includeMethod && !includeSuiteXML && !includeClass) {
                logger.info("Run All the Suites : ");
                result.add(m);
            }
        }

        return result;
    }
}

