# JImpulse

Java port of impulse, a small library for analysing sound output captured from Pulse Audio on Linux.

## Dependencies

You will need a couple of libraries, but both should be available on your Linux system. 

 * libpulse0
 * libfftw-3
 
If you are building this project from source, you will also need the `-dev` packages for these too.

## Configuring your project

### The Java Bit

The library is available in Maven Central.

### Maven

```xml
	<dependency>
		<groupId>uk.co.bithatch</groupId>
		<artifactId>jimpulse</artifactId>
		<version>1.0</version>
	</dependency>
```

Development versions (when available), will be the next version number, suffixed with -SNAPSHOT).

```xml
	<dependency>
		<groupId>uk.co.bithatch</groupId>
		<artifactId>jimpulse</artifactId>
		<version>1.1-SNAPSHOT</version>
	</dependency>
```

### The Native Bit

The native part of JImpulse is written using C and JNI, which itself uses the  [https://launchpad.net/impulse.bzr](Impulse) library by Ian Halpern. 

The jars distributed by Bithatch only currently contain `x86_64` binaries. These are automatically extracted when needed.

For other platforms you will need build yourself. The native components are built using [http://maven-nar.github.io/index.html](Maven NAR plugin). If you want to add support for other platforms, you'll need to edit the POM and build on appropriate hardware. *NOTE: I do not use NAR Native ARchives, due to some problems with modularity*.

## Try It

You can run the test application from the command line (requires Maven).

```sh
mvn compile exec:java
```

If all is well, it will simple dump out a never ending stream of floating point numbers.


## Usage

Integration with your own project is very simple.

The `Monitor` test application does the following :-

```java
	Impulse lib = new Impulse();
	
	lib.initImpulse();
	
	// You can set a different pulse device by it's index
	//	lib.setSourceIndex(0);
	
	// Set to true to turn on fast fourier transform
	boolean fft = false;
	
	while(true) {
		/* This is 256 bytes */
		double[] data = lib.getSnapshot(fft);
		
		for(double d : data) {
			System.out.print(d + " ");
		}
		System.out.println();
	}
```
