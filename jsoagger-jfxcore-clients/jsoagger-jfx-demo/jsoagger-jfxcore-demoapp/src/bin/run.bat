@ECHO OFF
set DIR=%~dp0

# no arguments passed, default desktop mode
if .%1 == . ( 
	SPRING_PROFILE="desktop"
	) ELSE (
	 SPRING_PROFILE=%1
	)
	
java -Dspring.profiles.active=%SPRING_PROFILE% --module-path=%PATH_TO_FX% --add-modules=javafx.controls,javafx.fxml \
--add-exports javafx.graphics/com.sun.javafx.application=ALL-UNNAMED \
--add-exports javafx.graphics/com.sun.javafx.css=ALL-UNNAMED \
--add-exports javafx.graphics/com.sun.javafx.util=ALL-UNNAMED \
--add-exports javafx.graphics/com.sun.javafx.scene=ALL-UNNAMED \
--add-exports javafx.graphics/com.sun.javafx.sg.prism=ALL-UNNAMED \
--add-exports javafx.controls/com.sun.javafx.scene.control.behavior=ALL-UNNAMED \
--add-exports javafx.base/com.sun.javafx.event=ALL-UNNAMED \
--add-exports javafx.graphics/com.sun.javafx.stage=ALL-UNNAMED \
--add-exports javafx.base/com.sun.javafx.binding=ALL-UNNAMED \
--add-opens javafx.controls/javafx.scene.control.skin=ALL-UNNAMED \
--add-opens javafx.controls/com.sun.javafx.scene.control.skin=ALL-UNNAMED \
--add-opens javafx.graphics/com.sun.javafx.application=ALL-UNNAMED -jar %DIR%/bin/jsoagger-jfxcore-demoapp.jar

