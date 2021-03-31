all: run

clean:
	rm -f out/FloydWarshallMainModule.jar out/FloydWarshallModule.jar

out/FloydWarshallMainModule.jar: out/parcs.jar src/FloydWarshallMainModule.java
	@javac -cp out/parcs.jar src/FloydWarshallMainModule.java
	@jar cf out/FloydWarshallMainModule.jar -C src FloydWarshallMainModule.class
	@rm -f src/FloydWarshallMainModule.class

out/FloydWarshallModule.jar: out/parcs.jar src/FloydWarshallMainModule.java
	@javac -cp out/parcs.jar src/MyClass.java src/Node.java
	@jar cf out/MyClass.jar -C src MyClass.class -C src Node.class
	@rm -f src/MyClass.class src/Node.class

build: out/FloydWarshallMainModule.jar out/FloydWarshallModule.jar

run: out/FloydWarshallMainModule.jar out/FloydWarshallModule.jar
	@cd out && java -cp 'parcs.jar:FloydWarshallMainModule.jar' FloydWarshallMainModule
