@echo off
rmdir /q /s bin
mkdir bin
cd GrovePi-spec
call mvn install
copy target\*.jar ..\bin
cd ..
cd Pi-spec
call mvn install
copy target\*.jar ..\bin
cd ..
cd GrovePi-pi4j
call mvn install
copy target\*.jar ..\bin
cd ..
cd GrovePi-dio
call mvn install
copy target\*.jar ..\bin
cd ..
cd Pi-dio
call mvn install
copy target\*.jar ..\bin
cd ..
cd Pi-pi4j
call mvn install
copy target\*.jar ..\bin
cd ..
cd examples
call mvn install
copy target\*.jar ..\bin
cd ..

copy lib\dio.jar bin
copy lib\pi4j-core.jar bin


REM cd examples-mvn
REM call mvn install assembly:single
REM cd ..
REM copy ImageCrawlerLauncher\target\*.zip . /Y
