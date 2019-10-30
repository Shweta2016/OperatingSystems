/*********************************************************
 * Project: Disk Arm Scheduling Algorithms
 * 
 * Name : Shweta Kharat
 * 
 *********************************************************/

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * CLASS NAME : DiskArmScheduling
 * FUNCTIONALITIES : 
 * 		Get input from user
 * 		Get choice of algorithm from user
 * 		Manipulate entered values for different algorithm
 * 		Calculate seek distance for particular algorithm
 * 		Print the reading track and the total distance
 */
public class DiskArmScheduling {
	static int totalDistance = 0;         /* Total seek distance for an algorithm */
	static int [] track = new int[10];    /* Track values entered by user */
	static int [] trackSSTF = new int[10];/* Track values sequence for SSTF algorithm */
	static int [] trackLOOK = new int[10];/* Track values sequence arranged for LOOK algorithm */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DiskArmScheduling d = new DiskArmScheduling();
		int flag = 0;                     /* To check if entered input is integer */
		int caseNum = 0;                  /* Choice entered by user (1,2,3,4) for switch case */
		int index = 0;                    /* Index of track with minimum seek distance according to algorithm */
		int flag1 = 0;                    /* To check if entered choice is integer */
		System.out.println("Please enter 10 unique tracks from 0 to 99");
		/* Continue if user enters wrong input */
		do{	
			Scanner s = new Scanner(System.in);
			try{
				flag = 0;
				/* Get user entered 10 track values(Given) */
				for(int i=0 ; i<10 ; i++){
					track[i] = s.nextInt();
					/* Check if entered track is from 0 to 99 */
					if(track[i] < 0 || track[i] > 99)
					{
						System.out.println("Please enter tracks from 0 to 99");
						track[i] = 0;
						i--;
					}
					/* Check if track value is unique/ no repetition */
					for(int j=0 ; j<i ;j++){
						if(track[j] == track[i]){
							System.out.println("Track number must be unique/Cannot be repeated");
							track[i] = 0;
							i--;
							break;
						}
					}
				}
			}catch(InputMismatchException e){
				System.out.println("Please enter correct input");
				flag = 1;
			}
		}while(flag == 1);
		System.out.println("***The Disk Arm Scheduling Algorithms**** \n"+
                "1.First Come First Serve (FCFS) \n"+
                "2.Shortest Seek Time First (SSTF) \n"+
                "3.The Elevator (LOOK) \n"+
                "4.Exit \n");
		int caseExit = 0;                 /* If case number entered by user is 4.Exit */
		/* Continue till user entered case number = 4 */
		do{
			Scanner s = new Scanner(System.in);
			System.out.println("Please Enter Your Choice : ");
			flag1 = 0;
			try{
				caseNum = s.nextInt();
				if(caseNum == 4){
					caseExit = 4;
				}
				switch(caseNum){
					/*FCFS algorithm*/
					case 1:
						totalDistance = 0;
						System.out.println("First Come First Serve (FCFS) Algorithm");
						/* Call function to calculate total distance traveled by arm */
						totalDistance = d.seekDistance(track);
						System.out.println("FCFS Algorithm Total Distance = " + totalDistance + "\n");
						break;
					/*SSTF algorithm*/
					case 2:
						totalDistance = 0;
						System.out.println("Shortest Seek Time First (SSTF) Algorithm");
						int [] temp = new int[10];            /* Temporary array that stores track values */
						for(int i=0;i<10;i++){
							temp[i] = track[i];               /* Copy values of track[] to temp[] */
						}
						for(int k=0 ; k<10 ;k++){
							int min = 500;                    /* Minimum value of distance between two tracks(Initially set to max) */
							int var = 0;                      /* Temporary variable to store distance between two tracks */
							for(int i=0 ; i<10 ; i++){
								/* To find track with minimum distance from 50(Starting track) */
								if(k == 0){
									if(temp[i] > 50){
										var = temp[i] -50;
									}
									else{
										var = 50 - temp[i];
									}
								}
								/* To find track with minimum distance from current track */
								else{
									if(temp[i] > trackSSTF[k-1]){
										var = temp[i] - trackSSTF[k-1];
									}
									else{
										var = trackSSTF[k-1] - temp[i];
									}
								}
								if(var < min){
									min = var;
									index = i;
								}
							}
							/* Save track value found above */
							trackSSTF[k] = temp[index];
							temp[index] = 500;                /* Set the value to max so that it would never be solution for next loop */
						}
						/* Call function to calculate total distance traveled by arm */
						totalDistance = d.seekDistance(trackSSTF);
						System.out.println("SSTF Algorithm Total Distance = " + totalDistance + "\n");
						break;
					/*LOOK algorithm*/
					case 3:
						totalDistance = 0;                   /* Total distance traveled by arm */
						int [] temp1 = new int[10];          /* Temporary array that stores track values */
						int var = 0;                         /* Temporary variable to store distance computed */
						int min = 500;                       /*  */
						int j = 0;                           /* Index for trackLOOK[] */
						for(int i=0;i<10;i++){
							temp1[i] = track[i];
						}
						Arrays.sort(temp1);                  /* Sort track values */
						/* Find the track that has minimum distance from current track(50) */
						for(int i=0 ; i<10 ; i++){
							if(temp1[i] > 50){
								var = temp1[i] -50;
							}
							else{
								var = 50 - temp1[i];
							}
							if(var < min){
								min = var;
								index = i;
							}
						}
						/* Save values smaller than 50 in decreasing order/Move left */
						if(temp1[index] < 50){
							for(int i=index; i>=0 ;){
								trackLOOK[j] = temp1[i];
								i = i-1;
								j = j+1;
							}
							if(index !=9){
								for(int i=index+1 ; i<10 ;){
									trackLOOK[j] = temp1[i];
									i = i+1;
									j = j+1;
								}
							}
						}
						/* Save values greater than 50 in increasing order/Move right */
						else{
							for(int i=index; i<10 ;){
								trackLOOK[j] = temp1[i];
								i = i+1;
								j = j+1;
							}
							if(index !=0){
								for(int i=index-1 ; i>=0 ;){
									trackLOOK[j] = temp1[i];
									i = i-1;
									j = j+1;
								}
							}
						}
						/* Call function to calculate total distance traveled by arm */
						totalDistance = d.seekDistance(trackLOOK);
						System.out.println("LOOK Algorithm Total Distance = " + totalDistance + "\n");
						break;
						
					default:
						break;
				}
			}catch(InputMismatchException e){
				System.out.println("Please enter correct input");
				flag1 = 1;
			}
		}while(flag1 == 1 || caseExit != 4);
	}
	
	/*
	 *FUNCTION NAME : seekDistance
	 *
	 *FUNCTIONALITIES : 
	 *		Calculate total distance traveled by the arm for algorithms 
	 *		Input : Optimal array for algorithm with track values
	 *		Output : Total distance traveled by arm for particular algorithm
	 * */
	int seekDistance(int []trackNew){
		int currentTrack = 50;                       /* Current track value(Given) */
		int totalDistance = 0;                       /* Total distance traveled by arm */
		int tempTrack = 0;                                /* Temporary distance value calculated */
		/* Distance between first track and current track */
		if(trackNew[0] < currentTrack){
			totalDistance = currentTrack - trackNew[0];
		}
		else{
			totalDistance = trackNew[0] - currentTrack;
		}
		/* Distance between two consecutive tracks */
		for(int i=0 ; i<9 ; i++){
			System.out.println("Reading track : " + trackNew[i]);
			if(trackNew[i] < trackNew[i+1]){
				tempTrack = trackNew[i+1] - trackNew[i];
			}
			else{
				tempTrack = trackNew[i] - trackNew[i+1];
			}
			totalDistance = totalDistance + tempTrack;
		}
		/* Print sequence in which the track must be traversed for particular algorithm */
		System.out.println("Reading track : " + trackNew[9]);
		/* Returned distance traveled by arm */
		return totalDistance;	
	}
	
}
