************* Project: Producer-Consumer with Threads ************

---------------------------------------------------------------
Shweta Kharat 

**************************************************************
Problem statement:
	One thread, the producer, will generate a sequence of integers and write them in order to successive buffer cells. Several consumer threads will then read these integers from the buffer and print them out in the order read. The is a circular buffer shared between the producer and consumers.


Compile and Execute Instructions
---------------------------------------------------------------
Below are the files for 'Project 2 Producer-Consumer with Threads':
	ProducerConsumer.java - Producer consumer project implementation source code
	Makefile - commands to compile, run and clean
	README.txt

Below are the list of commands in 'Makefile':
	1. make all - Command to compile source code
	2. make run / make prodcon - Command to run executable
	3. make clean - Command to clean residue i.e. .class files

**************************************************************

Results
---------------------------------------------------------------
Integers : 5 Buffer : 5 Consumer : 1 Producer : 1 (Category: Single consumer Test)
	Please enter maximum number of integers : 
	5
	Please enter buffer size : 
	5
	Please enter number of consumers : 
	1
	Buffer[0] = 0 produced by Producer
	Buffer[0] = 0 consumed by Consumer 0
	Consumer 0 Wait:Buffer empty.
	Buffer[1] = 1 produced by Producer
	Buffer[1] = 1 consumed by Consumer 0
	Consumer 0 Wait:Buffer empty.
	Buffer[2] = 2 produced by Producer
	Buffer[2] = 2 consumed by Consumer 0
	Consumer 0 Wait:Buffer empty.
	Buffer[3] = 3 produced by Producer
	Buffer[3] = 3 consumed by Consumer 0
	Consumer 0 Wait:Buffer empty.
	Buffer[4] = 4 produced by Producer
	Buffer[4] = 4 consumed by Consumer 0
	Producer Exiting:Finished Producing
	Consumer 0 Exiting:Finished Consuming

Integers : 5 Buffer : 5 Consumer : 5 Producer : 1 (Category: Consumer wait Test: Buffer empty)
	Please enter maximum number of integers : 
	5
	Please enter buffer size : 
	5
	Please enter number of consumers : 
	5
	Consumer 2 Wait:Buffer empty.
	Buffer[0] = 0 produced by Producer
	Buffer[0] = 0 consumed by Consumer 0
	Consumer 1 Wait:Buffer empty.
	Consumer 3 Wait:Buffer empty.
	Consumer 2 Wait:Buffer empty.
	Consumer 4 Wait:Buffer empty.
	Buffer[1] = 1 produced by Producer
	Buffer[1] = 1 consumed by Consumer 4
	Consumer 2 Wait:Buffer empty.
	Consumer 3 Wait:Buffer empty.
	Consumer 1 Wait:Buffer empty.
	Consumer 0 Wait:Buffer empty.
	Consumer 4 Wait:Buffer empty.
	Buffer[2] = 2 produced by Producer
	Buffer[2] = 2 consumed by Consumer 4
	Consumer 0 Wait:Buffer empty.
	Consumer 1 Wait:Buffer empty.
	Consumer 3 Wait:Buffer empty.
	Consumer 2 Wait:Buffer empty.
	Buffer[3] = 3 produced by Producer
	Buffer[3] = 3 consumed by Consumer 2
	Consumer 3 Wait:Buffer empty.
	Consumer 1 Wait:Buffer empty.
	Consumer 0 Wait:Buffer empty.
	Consumer 4 Wait:Buffer empty.
	Buffer[4] = 4 produced by Producer
	Producer Exiting:Finished Producing
	Buffer[4] = 4 consumed by Consumer 4
	Consumer 4 Exiting:Finished Consuming
	Consumer 1 Exiting:Finished Consuming
	Consumer 3 Exiting:Finished Consuming
	Consumer 2 Exiting:Finished Consuming
	Consumer 0 Exiting:Finished Consuming

Integers : 5 Buffer : 2 Consumer : 5 Producer : 1 (Category: Circular Buffer Test)
	Please enter maximum number of integers : 
	5
	Please enter buffer size : 
	2
	Please enter number of consumers : 
	5
	Buffer[0] = 0 produced by Producer
	Buffer[0] = 0 consumed by Consumer 4
	Consumer 3 Wait:Buffer empty.
	Consumer 1 Wait:Buffer empty.
	Consumer 2 Wait:Buffer empty.
	Consumer 0 Wait:Buffer empty.
	Consumer 4 Wait:Buffer empty.
	Buffer[1] = 1 produced by Producer
	Buffer[1] = 1 consumed by Consumer 4
	Consumer 0 Wait:Buffer empty.
	Consumer 2 Wait:Buffer empty.
	Consumer 1 Wait:Buffer empty.
	Consumer 3 Wait:Buffer empty.
	Consumer 4 Wait:Buffer empty.
	Buffer[0] = 2 produced by Producer
	Buffer[0] = 2 consumed by Consumer 4
	Consumer 3 Wait:Buffer empty.
	Consumer 1 Wait:Buffer empty.
	Consumer 2 Wait:Buffer empty.
	Consumer 0 Wait:Buffer empty.
	Consumer 4 Wait:Buffer empty.
	Buffer[1] = 3 produced by Producer
	Buffer[1] = 3 consumed by Consumer 4
	Consumer 0 Wait:Buffer empty.
	Consumer 2 Wait:Buffer empty.
	Consumer 1 Wait:Buffer empty.
	Consumer 3 Wait:Buffer empty.
	Buffer[0] = 4 produced by Producer
	Buffer[0] = 4 consumed by Consumer 3
	Producer Exiting:Finished Producing
	Consumer 3 Exiting:Finished Consuming
	Consumer 1 Exiting:Finished Consuming
	Consumer 2 Exiting:Finished Consuming
	Consumer 4 Exiting:Finished Consuming
	Consumer 0 Exiting:Finished Consuming

Integers : 5 Buffer : 2 Consumer : 1 Producer : 1 (Category: Producer wait Test: Buffer Full)
     (Note : Increase sleep time for consumer to verify this condition)
	Please enter maximum number of integers : 
	5
	Please enter buffer size : 
	2
	Please enter number of consumers : 
	1
	Buffer[0] = 0 produced by Producer
	Buffer[1] = 1 produced by Producer
	Producer Wait:Buffer full.
	Buffer[0] = 0 consumed by Consumer 0
	Buffer[0] = 2 produced by Producer
	Producer Wait:Buffer full.
	Buffer[1] = 1 consumed by Consumer 0
	Buffer[1] = 3 produced by Producer
	Producer Wait:Buffer full.
	Buffer[0] = 2 consumed by Consumer 0
	Buffer[0] = 4 produced by Producer
	Producer Exiting:Finished Producing
	Buffer[1] = 3 consumed by Consumer 0
	Buffer[0] = 4 consumed by Consumer 0
	Consumer 0 Exiting:Finished Consuming

Wrong Input: Integers : 1q Buffer : 10 Consumer : 10 Producer : 1
	Please enter maximum number of integers : 
	1q
	Please enter correct integer value...
	Please enter maximum number of integers :

**************************************************************

Discussion
----------------------------------------------------------------
1. How many of each integer should you see printed?
-> User should be able to see each integer the number of times it is produced by producer or the number of times it is consumed by consumer. In this case, the user will be able to see each integer only once. Only the buffer location will get reused due to 'Circular Buffer' property.

2. In what order should you expect to see them printed? Why?
-> The order will be same as the order in which the integers are consumed because the integers are stored in circular buffer and the same integers are consumed by the consumer one after other sequentially as produced by producer. But the order of consumers may differ based on which consumer thread that executes first.

3. Did your results differ from your answers in (1) and (2)? Why or why not?
-> No. The answers did not differ because of the circular buffer implementation and threads synchronization. Only the order of consumers may differ based on which consumer thread executes first.


**************************************************************