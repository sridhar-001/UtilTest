import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by sridhar srinivasan on 21-09-2018.
 */
public class CustomFilterTestListener implements IMethodInterceptor {

    static Logger logger = Logger.getLogger(CustomFilterTestListener.class.getName());

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
                String group = null;

                if (m.getMethod().getGroups() != null && m.getMethod().getGroups().length > 0) {
                    group = m.getMethod().getGroups()[0];
                }
                if (!excludeMethodList.contains(group)) {
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
                String group = null;

                if (m.getMethod().getGroups() != null && m.getMethod().getGroups().length > 0) {
                    group = m.getMethod().getGroups()[0];
                }
                if (includeMethodList.contains(group)) {
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

