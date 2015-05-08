@echo off

cd lib
call install-dio-maven.bat
cd..

cd GrovePi-spec
call mvn install
cd ..

cd Pi-spec
call mvn install
cd ..

cd GrovePi-pi4j
call mvn install
cd ..

cd GrovePi-dio
call mvn install
cd ..

cd Pi-dio
call mvn install
cd ..

cd Pi-pi4j
call mvn install
cd ..

cd examples
call mvn install
cd ..

REM cd examples-mvn
REM call mvn install assembly:single
REM cd ..
REM copy ImageCrawlerLauncher\target\*.zip . /Y
