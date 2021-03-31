all: run

clean:
	rm -f out/FloydWarshallMainModule.jar out/.jar

out/FloydWarshallMainModule.jar: out/parcs.jar src/FloydWarshallMainModule.java
	@javac -cp out/parcs.jar src/FloydWarshallMainModule.java
	@jar cf out/FloydWarshallMainModule.jar -C src FloydWarshallMainModule.class
	@rm -f src/FloydWarshallMainModule.class

out/.jar: out/parcs.jar src/FloydWarshallMainModule.java
	@javac -cp out/parcs.jar src/FloydWarshallModule.java
	@jar cf out/FloydWarshallModule.jar -C src FloydWarshallModule.class
	@rm -f src/FloydWarshallModule.class

build: out/FloydWarshallMainModule.jar out/.jar

run: out/FloydWarshallMainModule.jar out/.jar
	@cd out && java -cp 'parcs.jar:FloydWarshallMainModule.jar' FloydWarshallMainModule
