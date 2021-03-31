all: run

clean:
	rm -f out/FloydWarshallMain.jar out/FloydWarshallModule.jar

out/FloydWarshallMain.jar: out/parcs.jar src/FloydWarshallMain.java
	@javac -cp out/parcs.jar src/FloydWarshallMain.java
	@jar cf out/FloydWarshallMain.jar -C src FloydWarshallMain.class
	@rm -f src/FloydWarshallMain.class

out/DFS.jar: out/parcs.jar src/FloydWarshallModule.java src/Node.java
	@javac -cp out/parcs.jar src/FloydWarshallModule.java
	@jar cf out/FloydWarshallModule.jar -C src FloydWarshallModule.class
	@rm -f src/FloydWarshallModule.class

build: out/FloydWarshallMain.jar out/FloydWarshallModule.jar

run: out/FloydWarshallMain.jar out/FloydWarshallModule.jar
	@cd out && java -cp 'parcs.jar:FloydWarshallMain.jar' FloydWarshallMain