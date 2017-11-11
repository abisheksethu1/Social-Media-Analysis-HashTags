JCC = javac
JFLAGS = -g
default: hashtagcounter.class
hashtagcounter.class: hashtagcounter.java
	$(JCC) $(JFLAGS) hashtagcounter.java
Heap.class: Heap.java
	$(JCC) $(JFLAGS) Heap.java
HeapNode.class: HeapNode.java
	$(JCC) $(JFLAGS) HeapNode.java
clean:
	$(RM) *.class
