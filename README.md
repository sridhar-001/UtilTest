
`Custom-Filter TestListener to filter Testng Test execution based on maven command line parameter`

Usage :

Add the listener filter to main testng.xml<br>
<pre>
&lt;listeners&gt;<br>
     &lt;listener class-name="com.github.sridhar.CustomTestFilter"/&gt;<br>
 &lt;/listeners&gt;<br>
</pre>

Add the dependency to pom.xml of the individual test-module to consider filtering of tests <br>
<pre>
&lt;dependencies&gt;<br>
       &lt;dependency><br>
           &lt;groupId&gt;com.github.sridhar-001&lt;/groupId&gt;<br>
            &lt;artifactId&gt;CustomTestFilter&lt;/artifactId&gt;<br>
           &lt;version&gt;1.0.0&lt;/version&gt;<br>
       &lt;/dependency&gt;<br>
 &lt;/dependencies&gt;<br>
</pre>
 Points to note:
 1) Easy to use. No need to add listener in each and every suite xml file except for the parent testng.xml which is called by module pom.xml<br>
 
 2) Module independent.<br>
 
 3) Maven parameter to exclude/include single/multiple suitexml/class/method each of which can be comma separated<br>
 Eg: mvn clean install -DexcludeSuiteXML=tempxml,temp1.xml,temp2.xml<br>
       If the parameter value does not match any value then no tests will be executed<br>
       
 4) Backward compatible. If there is no filter criteria specified then all the tests will be executed<br>

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




