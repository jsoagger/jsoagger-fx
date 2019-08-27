-- Java 9 handling

1. com.sun.javafx.* package content have been made inaccessible by orable in jdk9
	Class using library in this class must be refactored in order to run in JAVA9
	
2A. RUN JSOAGGER DESKTOP CLIENT ON JAVA9 -- NO MODULES
	As JAXB is planned to removal in JAVA11 and made private in JAVA9, must use --add-modules to make it accessible
	-> JAXB should be carried as maven dependency in futur version
	$JAVA_HOME/bin/java -jar --add-modules java.xml.bind,javafx.base,javafx.controls -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=1044 -Dspring.profiles.active=desktop,dev,h2 jsoagger-jfxcore-desktop-demoapp-11.0.2-spring-boot.jar
	
	
	2.1	RUN JSOAGGER DESKTOP CLIENT ON JAVA8
		java -jar -Dspring.profiles.active=desktop,dev,h2 jsoagger-jfxcore-desktop-spring-boot.jar
	2.2 Remote DEBUG
		-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=1045
		
2B. RUN JSOAGGER DESKTOP CLIENT ON JAVA9/10 WITH MODULES (SPRING BOOT)
	java -Dspring.profiles.active=desktop,dev,h2 --add-opens javafx.controls/javafx.scene.control.skin=ALL-UNNAMED,javafx.controls/com.sun.javafx.scene.control.skin=ALL-UNNAMED,javafx.graphics/com.sun.javafx.application=ALL-UNNAMED  -jar ./target/jsoagger-jfxcore-desktop-demoapp-11.0.2-spring-boot.jar

2C. RUN JSOAGGER DESKTOP CLIENT ON JAVA9/10 WITH MODULES (Jar WITH EXTERNAL DEPENDENCIES)
	DEPENDENCIES ARE LOCATED IN EXTERNAL LIB FOLDER -- manual, dependencies are out of the final jar,
	java -Dspring.profiles.active=desktop,dev,h2 -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=1044 --add-opens javafx.controls/javafx.scene.control.skin=ALL-UNNAMED,javafx.controls/com.sun.javafx.scene.control.skin=ALL-UNNAMED,javafx.graphics/com.sun.javafx.application=ALL-UNNAMED  -jar jsoagger-jfxcore-desktop-demoapp-11.0.2.jar

2D. RUN ON JAVA11
	java -Dspring.profiles.active=desktop,dev,h2 -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=1044 -jar

	
3. LIST ALL DEPENDENCIES OF A LIBRARY 
	/Library/Java/JavaVirtualMachines/jdk-9.0.4.jdk/Contents/Home/bin/jdeps --list-deps target/jsoagger-jfxcore.jar
	
4. LIST ALL MODULES OF JDK
	/Library/Java/JavaVirtualMachines/jdk-9.0.4.jdk/Contents/Home/bin/java --list-modules

5. jdeps --module-path modules --add-modules io.github.jsoagger.core.jfx,io.github.jsoagger.ui --generate-module-info 



------------------------------------------------ run with java 11 ----------------------------------------

/Library/Java/JavaVirtualMachines/jdk-11.jdk/Contents/Home/bin/java -Dspring.profiles.active=mobile --module-path ./target/modules -cp ./target/libs --add-modules=javafx.controls,javafx.fxml --add-opens javafx.controls/javafx.scene.control.skin=ALL-UNNAMED,javafx.controls/com.sun.javafx.scene.control.skin=ALL-UNNAMED,javafx.graphics/com.sun.javafx.application=ALL-UNNAMED --add-exports javafx.graphics/com.sun.javafx.application=ALL-UNNAMED  --add-exports javafx.graphics/com.sun.javafx.css=ALL-UNNAMED -jar target/library-name.jar 


/Library/Java/JavaVirtualMachines/jdk-11.jdk/Contents/Home/bin/java -Dspring.profiles.active=mobile --module-path ./target/modules -cp ./target/libs --add-modules=javafx.controls,javafx.fxml --add-opens javafx.controls/javafx.scene.control.skin=ALL-UNNAMED,javafx.controls/com.sun.javafx.scene.control.skin=ALL-UNNAMED,javafx.graphics/com.sun.javafx.application=ALL-UNNAMED,javafx.graphics/com.sun.javafx.scene=ALL-UNNAMED --add-exports javafx.graphics/com.sun.javafx.application=ALL-UNNAMED --add-exports javafx.graphics/com.sun.javafx.css=ALL-UNNAMED --add-exports javafx.graphics/com.sun.javafx.scene=ALL-UNNAMED -jar ./target/jsoagger-jfxcore-demoapp.jar 

/Library/Java/JavaVirtualMachines/jdk-11.jdk/Contents/Home/bin/java -Dspring.profiles.active=mobile --module-path ./target/modules -cp ./target/libs --add-modules=javafx.controls,javafx.fxml --add-opens javafx.controls/javafx.scene.control.skin=ALL-UNNAMED,javafx.controls/com.sun.javafx.scene.control.skin=ALL-UNNAMED,javafx.graphics/com.sun.javafx.application=ALL-UNNAMED,javafx.graphics/com.sun.javafx.scene=ALL-UNNAMED --add-exports javafx.graphics/com.sun.javafx.application=ALL-UNNAMED --add-exports javafx.graphics/com.sun.javafx.css=ALL-UNNAMED --add-exports javafx.graphics/com.sun.javafx.scene=ALL-UNNAMED -jar ./target/jsoagger-jfxcore-demoapp.jar

java -Dspring.profiles.active=desktop --module-path ./modules -cp ./libs --add-modules=javafx.controls,javafx.fxml --add-opens javafx.controls/javafx.scene.control.skin=ALL-UNNAMED,javafx.controls/com.sun.javafx.scene.control.skin=ALL-UNNAMED,javafx.graphics/com.sun.javafx.application=ALL-UNNAMED --add-exports javafx.graphics/com.sun.javafx.application=ALL-UNNAMED  --add-exports javafx.graphics/com.sun.javafx.css=ALL-UNNAMED  jsoagger-jfxcore-demoapp.jar

java -Dspring.profiles.active=desktop  --module-path ./modules -cp ./libs  --add-exports javafx.graphics/com.sun.javafx.application=ALL-UNNAMED  --add-exports javafx.graphics/com.sun.javafx.css=ALL-UNNAMED  jsoagger-jfxcore-demoapp.jar


 --add-exports javafx.base/com.sun.javafx.event=ALL-UNNAMED --add-exports javafx.graphics/com.sun.javafx.stage=ALL-UNNAMED --add-exports javafx.base/com.sun.javafx.binding=ALL-UNNAMED --add-exports  javafx.controls/com.sun.javafx.scene.control.behavior=ALL-UNNAMED --add-exports javafx.graphics/com.sun.javafx.application=ALL-UNNAMED --add-exports javafx.graphics/com.sun.javafx.css=ALL-UNNAMED --add-exports javafx.graphics/com.sun.javafx.util=ALL-UNNAMED --add-exports javafx.graphics/com.sun.javafx.scene=ALL-UNNAMED --add-opens javafx.controls/javafx.scene.control.skin=ALL-UNNAMED,javafx.controls/com.sun.javafx.scene.control.skin=ALL-UNNAMED,javafx.graphics/com.sun.javafx.application=ALL-UNNAMED 

java -Dspring.profiles.active=desktop  --module-path ../../modules --add-modules=javafx.controls,javafx.fxml \
--add-exports javafx.graphics/com.sun.javafx.application=ALL-UNNAMED \
--add-exports javafx.graphics/com.sun.javafx.css=ALL-UNNAMED \
--add-exports javafx.graphics/com.sun.javafx.util=ALL-UNNAMED \
--add-exports javafx.graphics/com.sun.javafx.scene=ALL-UNNAMED \
-jar testosterone-1.0-SNAPSHOT.jar

java -Dspring.profiles.active=mobile  --module-path ../../modules --add-modules=javafx.controls,javafx.fxml \
--add-exports javafx.graphics/com.sun.javafx.application=ALL-UNNAMED \
--add-exports javafx.graphics/com.sun.javafx.css=ALL-UNNAMED \
--add-exports javafx.graphics/com.sun.javafx.util=ALL-UNNAMED \
--add-exports javafx.graphics/com.sun.javafx.scene=ALL-UNNAMED \
-jar testosterone-1.0-SNAPSHOT.jar


java -Dspring.profiles.active=xpad  --module-path ../../modules --add-modules=javafx.controls,javafx.fxml \
--add-exports javafx.graphics/com.sun.javafx.application=ALL-UNNAMED \
--add-exports javafx.graphics/com.sun.javafx.css=ALL-UNNAMED \
--add-exports javafx.graphics/com.sun.javafx.util=ALL-UNNAMED \
--add-exports javafx.graphics/com.sun.javafx.scene=ALL-UNNAMED \
-jar testosterone-1.0-SNAPSHOT.jar

 

# BASIC CLASSPATH MODE
# CLASSPATH GENERATED IN MANIFEST AND POINT ./libs folder
java -Dspring.profiles.active=xpad  -jar jsoagger-jfxcore-demoapp.jar


------------------------------------------------ archetype create ------------------------------------------------
 mvn archetype:generate -DarchetypeGroupId=io.github.jsoagger -DarchetypeArtifactId=jsoagger-jfx-archetype  -DarchetypeVersion=11.0.2 -DgroupId=test  -DartifactId=testosterone
 mvn archetype:generate -DarchetypeGroupId=io.github.jsoagger -DarchetypeArtifactId=jsoagger-jfx-archetype  -DarchetypeVersion=11.0.3-SNAPSHOT -DgroupId=test  -DartifactId=testosterone
 