/****************************************************************
 *  Project : Producer Consumer with threads
 *  
 *  By : Shweta Kharat
 * 
 * ***************************************************************/

import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * CLASS NAME : ProducerConsumer
 * FUNCTIANALITIES :
 *   Get maximum number of integers form user
 *   Get buffer size from user
 *   Get number of consumers from user
 *   Start producer and consumer threads
 *   Exit when both producer and consumer threads have been executed
 */
public class ProducerConsumer {
	int numConsumed = 0;         /* Number of consumers */
	static int flagC = 0;        /* Flag for consumer to stop thread after execution */
	static int flagP = 0;        /* Flag for producer to stop thread after execution */
	static int maxCapacity = 0;  /* Maximum number of integers to be produced / consumed */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int flagS = 0;           /* Flag for scanner to check correct user input */
		int bufferSize = 0;      /* Circular buffer size */
		int numConsumer = 0;     /* Number of consumers */  
		/* Gets number of integers, buffer size and number of consumers from user  */
		do{	
			Scanner scanner = new Scanner(System.in);
			try{
				System.out.println("Please enter maximum number of integers : ");
				maxCapacity = scanner.nextInt();
				System.out.println("Please enter buffer size : ");
				bufferSize = scanner.nextInt();
				System.out.println("Please enter number of consumers : ");
				numConsumer = scanner.nextInt();
				flagS = 1;
				scanner.close();
			}catch(InputMismatchException e){
				System.out.println("Please enter correct integer value...");
				flagS = 0;
			}
		}while(flagS == 0);
		ProducerConsumer pc = new ProducerConsumer();
		CircularBuffer cb = pc.new CircularBuffer(bufferSize);
		Producer p = pc.new Producer(cb , maxCapacity);       /* Producer thread */
		p.start();                                            /* Start producer thread */
		Consumer[] c = new Consumer[numConsumer];             /* Array of consumers */
		for(int i=0 ; i<numConsumer ; i++){
			c[i] = pc.new Consumer(cb , "Consumer "+i , maxCapacity); /* Consumer thread */
			c[i].start();        /* Start consumer thread for each consumer */
		}
	}
	
	/*
	 * CLASS NAME : Producer
	 * FUNCTIONALITIES : 
	 *   Implement producer logic for producer thread
	 *   Call run() to run producer thread
	 *   Call produce() to produce new integer and save in circular buffer
	 */
	class Producer extends Thread {
		private CircularBuffer cb;
		int intNumber;                    /* Integers from 0 to maxCapacity */
		int maxCapacity = 0;
		public Producer(CircularBuffer cb , int maxCapacity){
			this.cb = cb;
			this.intNumber = 0;
			this.maxCapacity = maxCapacity;
		}
		public void run(){
			/* Executes till integers up to maxCapacity are produced by producer */
			while(maxCapacity != 0){
				try {
					Thread.sleep(500);    /* Suspend producer thread for 1000 milliseconds */
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int data = intNumber++;   /* Data to be produced by producer */
				cb.produce(data);         /* Produce data in circular buffer */
				maxCapacity--;
			}
			flagP = 1;
			System.out.println("Producer Exiting:Finished Producing");
		}
	}
	
	/*
	 * CLASS NAME : Consumer
	 * FUNCTIONALITIES : 
	 *   Implement consumer logic for consumer thread
	 *   Call run() to run consumer threads
	 *   Call consume() to consume integer from circular buffer
	 */
	class Consumer extends Thread {
		private CircularBuffer cb;
		int maxCapacity = 0;
		public Consumer(CircularBuffer cb , String consumer , int maxCapacity){
			this.cb = cb;
			setName(consumer);
			this.maxCapacity = maxCapacity;
		}
		public void run(){
			/* Executes till count of integers consumed is less than maxCapacity */
			while(numConsumed < maxCapacity && flagC == 0){
				try {
					Thread.sleep(500);  /* Suspend consumer thread for mentioned milliseconds */
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cb.consume(getName());   /* Consume data from circular buffer */
				numConsumed++;
			}
			flagC = 1;
			System.out.println(getName() + " Exiting:Finished Consuming");
		}
	}
	
	/*
	 * CLASS NAME : CircularBuffer
	 * FUNCTIONALITY : 
	 *   Implement circular buffer logic for producer and consumer
	 */
	class CircularBuffer {
		private int buffer[];
		private int head;       /* Start of circular buffer */
		private int tail;       /* End of circular buffer */
		private int capacity;   /* Size of buffer */
		
		public CircularBuffer(int capacity){
			this.capacity = capacity;
			buffer = new int[capacity];
			head = 0;
			tail = 0;	
		}
		
		public synchronized void produce(int data){
			/* Execute if buffer is full */
			while(tail == capacity && flagP == 0){
				try {
					System.out.println("Producer Wait:Buffer full.");
					wait();                           /* Wait until notified by consumer */
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			buffer[(head + tail) % capacity] = data;   /* Save data in buffer */
			System.out.println("Buffer[" + ((head + tail) % capacity) + "] = " + data + " produced by Producer");
			tail++;                                    /* Increment tail */
			notifyAll();                               /* Wake up consumer threads */
		
		}
		
		public synchronized int consume(String consumer){
			/* Execute if buffer is empty */
			while(tail == 0 && flagC == 0){
				try {
					System.out.println(consumer +" Wait:Buffer empty.");
					wait();                            /* Wait until notified by producer */
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(flagC == 0){
				int data = buffer[head];
				System.out.println("Buffer[" + head + "] = " + data + " consumed by " + consumer);
				head = (head + 1) % capacity;
				tail--;                                 /* Decrement tail */
				notifyAll();                            /* Wake up producer thread */
				return data;
			}                                          
			else{
				return -1;
			}
		}
	}
}
