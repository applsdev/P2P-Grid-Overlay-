JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $*.java
CLASSES = \
	FileTransfer.java \
	GPDatagram.java \
	GSocket.java \
	GSocketThreadIn.java \
	GSocketThreadOut.java \
	PGOCoord.java \
	PGOPeer.java \
	StartSocket.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class