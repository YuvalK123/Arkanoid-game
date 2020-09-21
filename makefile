# 312116569 
# kasnery
compile: bin
	find src -name "*.java" > sources.txt
	javac -cp biuoop-1.4.jar: -d bin @sources.txt
run:
	java -cp biuoop-1.4.jar:bin:resources Ass7Game
jar:
	jar -cfm ass7game.jar MANIFEST.MF -C bin . -C resources .
bin:
	mkdir bin
check:
	java -jar checkstyle-5.7-all.jar -c biuoop.xml src/*.java src/*/*/*.java src/*/*/*/*.java