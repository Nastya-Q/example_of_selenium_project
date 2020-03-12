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

**SOME NOTES ABOUT YOUTRACK APPLICATION**
1. When user is created, some special symbols and uniqueness are only checked for login field
2. So, special symbols are allowed in all other fields (password, email, jabber, full name) -> `see one of positive tests with evidence`
3. Email can be duplicated (so, during new user creation, email from already existing user can be used).
 Making accent on email as it can be used for user login. -> `no tests was added for duplicated email, 
 as I checked that user can successfully login with duplicated email`
4. there is no special check for email/jabber  format -> `no tests was added for this as well, as there is no app requirement for format`
 