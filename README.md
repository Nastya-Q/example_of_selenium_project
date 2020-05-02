**ABOUT PROJECT:**
* Target project: YouTrack build 8823
* Target functionality: Create New User
* Target Browsers: Google Chrome, Firefox
* Platforms: Win10, macOS Catalina (for these platforms tests are debugged and working properly)


**HOW TO RUN TESTS**
1. Ensure YouTrack application is running
2. Clone the project (Maven should be installed)
3. Add system webdrivers for Google Chrome/Firefox to project directory:
e.g.
`yourpath/projectdirectory/chromedriver or
yourpath/projectdirectory/geckodriver`
4. Add properties with YouTrack URL and root user/password to 
`projectdirectory/src/test/resources` with .properties extension, e.g.:
`projectdirectory/src/test/resources/mylocal.properties` with the following content:
see example how properties should look like in `src/test/resources/local.properties` (these will be used by default if no other properties defined)
5. Run tests with one of the following way: 
* IDE runner (e.g. right click on test `suite src/test/resources/CreateUserTests.xml` and click Run)
* Maven runner with the following command:
`mvn clean test -DsuiteXmlFile="src/test/resources/CreateUserTests.xml" -Dbrowser=$targetbrowser -Dtarget=$targetproperties`, e.g:
e.g. 
`mvn clean test -DsuiteXmlFile="src/test/resources/CreateUserTests.xml" -Dbrowser="firefox" -Dtarget="localtest"
`

**NOTE:** 
if browser/properties are not defined, then Google Chrome and local properties will be used by default

**TESTS RESULTS:**
1. After running with mentioned maven command test report can be seen in 
`projectdirectory/target/surefire-reports/emailable-report.html`
2. Failed tests screenshots are saved in 
`projectdirectory/src/failed_test_screenshots`

**SOME NOTES ABOUT YOUTRACK APPLICATION (FOUND ISSUES DURING TESTS WRITING):**
1. Almost no validation on special symbols for all fields
2. Almost no validation for min/max length for all fields (except login/full name, which are limited by 50 symbols)
3. There is no check for email/jabber  format, they can be any non-valid
3. Email can be duplicated (so, during new user creation, email from already existing user can be used).

**TEST CASES DESCRIPTION CAN BE FOUND AT PROJECT DIR:**
/jbtesttask/Test_cases_description.docx
 