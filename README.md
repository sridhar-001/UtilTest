
Filter Testng Test execution based on command line parameter value

Usage :

Add the listener filter to main testng.xml
<listeners>
     <listener class-name="com.github.sridhar.CustomTestFilter"/>
 </listeners>

Add the dependency to module pom.xml
 <dependencies>
        <dependency>
            <groupId>com.github.sridhar-001</groupId>
            <artifactId>CustomTestFilter</artifactId>
            <version>1.0.0</version>
        </dependency>
 </dependencies>

 Points to note:
 1) Easy to use. No need to add listener in each and every suite xml file except for the parent testng.xml which is called by module pom.xml
 2) Module independent.
 3) Maven parameter to exclude/include single/multiple suitexml/class/method each of which can be comma separated
 Eg: mvn clean install -DexcludeSuiteXML=tempxml,temp1.xml,temp2.xml
      If the parameter value does nt match any value no tests will be executed
 4) Backward compatible. If there is no filter criteria is specified all the tests will be executed

Maven command Line Options:

-DexcludeClass

                  Accepts single or multiple comma separated class files
				  Exclude the specified classes at run time test execution

-DincludeClass

                  Accepts single or multiple comma separated class files
				  Include the specified classes only at run time test execution

-DexcludeSuiteXML

                  Accepts single or multiple comma separated suite files
				  Exclude the specified suites at run time test execution

-DincludeSuiteXML

                  Accepts single or multiple comma separated suite files
				  Include the specified suites only at run time test execution

-DexcludeMethod

                  Accepts single or multiple comma separated methods from class files
				  Exclude the specified methods if present in class at run time test execution

-DincludeMethod

                  Accepts single or multiple comma separated methods from class files
				  Include the specified methods only if present in class at run time test execution




