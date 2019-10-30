*********************** COEN 283 Project: Disk Arm Scheduling *******************

---------------------------------------------------------------
Shweta Kharat

**************************************************************
Problem Statement:
	Write a program that will implement three different algorithms for scheduling the arm on a hard disk. That is, you will simulate the motion of the disk arm by taking a set of requests for cylinders/tracks to read and then determining the order in which they will be read. You will then report the total distance (number of cylinders) the disk arm had to traverse in the three algorithms using the same set of random numbers.


Compile and Execute Instructions
---------------------------------------------------------------
Below are the files for 'Project 3 Disk Arm Scheduling Algorithms’:
	DiskArmScheduling.java - PDisk Arm Scheduling Algorithms implementation source code
	Makefile - commands to compile, run and clean
	README.txt

Below are the list of commands in 'Makefile':
	1. make all - Command to compile source code
	2. make run - Command to run executable
	3. make clean - Command to clean residue i.e. .class files

**************************************************************

Results
---------------------------------------------------------------
Case 1 : Output with correct inputs
	Please enter 10 unique tracks from 0 to 99
	5
	28
	10
	7
	39
	20
	45
	67
	36
	35
	***The Disk Arm Scheduling Algorithms**** 
	1.First Come First Serve (FCFS) 
	2.Shortest Seek Time First (SSTF) 
	3.The Elevator (LOOK) 
	4.Exit 

	Please Enter Your Choice : 
	1
	First Come First Serve (FCFS) Algorithm
	Reading track : 5
	Reading track : 28
	Reading track : 10
	Reading track : 7
	Reading track : 39
	Reading track : 20
	Reading track : 45
	Reading track : 67
	Reading track : 36
	Reading track : 35
	FCFS Algorithm Total Distance = 219

	Please Enter Your Choice : 
	2
	Shortest Seek Time First (SSTF) Algorithm
	Reading track : 45
	Reading track : 39
	Reading track : 36
	Reading track : 35
	Reading track : 28
	Reading track : 20
	Reading track : 10
	Reading track : 7
	Reading track : 5
	Reading track : 67
	SSTF Algorithm Total Distance = 107

	Please Enter Your Choice : 
	3
	Reading track : 45
	Reading track : 39
	Reading track : 36
	Reading track : 35
	Reading track : 28
	Reading track : 20
	Reading track : 10
	Reading track : 7
	Reading track : 5
	Reading track : 67
	LOOK Algorithm Total Distance = 107

	Please Enter Your Choice : 
	4

Case 1 : Output with incorrect inputs
	Case a: If input is not unique number
		Please enter 10 unique tracks from 0 to 99
		5
		28
		10
		10
		Please enter unique numbers
		7
		39
		20
		45
		67
		36
		35
		***The Disk Arm Scheduling Algorithms**** 
		1.First Come First Serve (FCFS) 
		2.Shortest Seek Time First (SSTF) 
		3.The Elevator (LOOK) 
		4.Exit 

		Please Enter Your Choice : 
	—————————————————————————————————————————————

	case b: If numbers are not in range 0<=n<100
		Please enter 10 unique tracks from 0 to 99
		5
		28
		150
		Please enter tracks from 0 to 99
		10
		7
		39
		20
		45
		67
		36
		35
		***The Disk Arm Scheduling Algorithms**** 
		1.First Come First Serve (FCFS) 
		2.Shortest Seek Time First (SSTF) 
		3.The Elevator (LOOK) 
		4.Exit 

		Please Enter Your Choice : 


**************************************************************