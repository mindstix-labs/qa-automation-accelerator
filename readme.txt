------------------------Setup --------------------------------------------------

1. Open terminal and run the following command to pull down a copy of the 'selenium-accelerator' repo :

	git clone https://github.com/mindstix-labs/selenium-accelerator.git

2. Navigate to 'selenium-accelerator' directory and run following command to generate project structure based on your IDE:

	./gradlew eclipse OR ./gradlew idea

3. Open 'selenium-accelerator' in Eclipse by doing : File > Import > Existing Projects into Workspace and browse to the ‘selenium-accelerator’ folder and open it.

4. To download gradle on system and to build project execute the following command : 
 
	./gradlew build

-------------------------Execution---------------------------------------------------------------

1. To execute all test scenarios execute the following command :

	./gradlew clean test -Denv.browser=chrome

	---Note--- 
   -Here we are selecting chrome browser for our test execution.
   -The required web drivers will be automatically downloaded and cached on first run.

2. To execute only one scenario using tag, add "-Dcucumber.options=<value>" to gradle command.
   For e.g. Following command will execute all the scenarios with '@tag1' tag :
	
	./gradlew clean test -Denv.browser=chrome -Dcucumber.options=" --tags @tag1"

3. To change chrome execution mode, add "-Denv.mode=<value>" to gradle command.
   For e.g. Following command will execute tests in grid mode on chrome :
	
	./gradlew clean test -Denv.browser=chrome -Denv.mode=grid

	---Note---
   -Chrome has following modes:-
	-normal(Default)
	-headless
	-grid
	-incognito

4. To select environment to run test cases, add "-Denv.urlPrefix=<value>" in gradle command.
   For e.g. Following command will execute tests on 'www.automationpractice.com' environment :

	./gradlew clean test -Denv.browser=chrome -Denv.urlPrefix=www -Denv.domain=.automationpractice.com

	---Note---
   -By default urlPrefix is "www"

5. Examples of tags -
    a) Run all scenarios with either @tag1 OR @tag2 OR both: --tags=@tag1,@tag2
    b) Run all scenarios with BOTH @tag1 AND @tag2 marked: --tags=@tag1 --tags=@tag2
    c) Run scenarios which are NOT marked as @tag1: --tags=~@tag1

6. Jenkinsfile technique is Used to setup pipeline jobs
    Jenkinsfile (not used anymore) : Uses gradle based trigger which also takes care of grid setup/teardown.
    Jenkinsfile-maven (not used anymore) : Uses maven based trigger which also takes care of grid setup/teardown.
    Jenkinsfile-nogrid (used actively) : Uses gradle based trigger. Does not teardown grid. But starts it if down.
