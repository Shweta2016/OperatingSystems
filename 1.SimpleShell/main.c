/*************************************************************************************
 * Project: Simple Shell
 *
 * Shweta Kharat
 * 
 * April 2017
 *************************************************************************************/

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <fcntl.h>
#include "myShell.h"   /* Contains function declarations */


/* 
 * Function name: main()
 * Functionalities: 
 *      Print command prompt
 *      Get command from user
 *      Run commands exit, clr, environ, help with more filter
 *      Separate commands into tokens
 *      Call run_command() to run other commands
 */
int main()
{
    printf("************* PROJECT: SIMPLE SHELL ************** \n\n");
    while(1)
    {
    	int cmdNum = 0;                 /* Count of tokens */
    	int MAX = 100;                  /* Maximum length of command */
        char cmd[MAX];                  /* Command entered by user */
		char *cmds[MAX];                /* Tokens in entered command */
		memset(cmd,0,sizeof(cmd)/sizeof(char));
		memset(cmds,0,sizeof(cmds)/sizeof(char*));
		fflush(stdout);
		fflush(stdin);

		printf("Enter cmd # ");     /* Command prompt */
		fgets(cmd , MAX , stdin);       /* Get command from command line */
		if(strcmp(cmd , "\n") == 0)     
			continue;                   /* Continue if user presses enter key */
		else if(strcmp(cmd , "exit\n") == 0)
			exit(0);                    /* Exit myshell command line */
		else if(strcmp(cmd , "clr\n") == 0 || strcmp(cmd , "clear\n") == 0)
			system("clear");            /* Clear screen */
		else if(strcmp(cmd , "environ\n") == 0)
		{
			system("env");
		}
		else if(strcmp(cmd , "about\n") == 0)
			system("echo \"Student Name : Shweta Kharat \nStudent Id   : W1384227\"");
		                                /* 'about' built in command */
		else                                        
		{
			int i = 0;                  /* Iterator */
			while(cmd[i] != '\n')       /* Replace '\n' with '\0' */
			{
				i++;
			}
			cmd[i] = '\0';

			/* Tokenize the entered command using " " delimiter */
			char *token;                    
			token = strtok(cmd , " ");
			while(token != NULL)
			{
				cmds[cmdNum] = token;
				token = strtok(NULL , " ");
				cmdNum++;
			}
			run_command(cmds);           /* Run entered command */
		}
	}
	return(0);
}

/* 
 * Function name: run_command()
 *                    cmds - contains all the tokens in the command
 * Functionalities: 
 *      Store tokens from IO Redirection command in cmdIO 
 *      Check if '<' or '>' present in command and call run_commandIO()
 *      Check if '|' present in command and call run_commandPipe()
 *      If none of above is present, call run_commandSimple()   
 */
int run_command(char *cmds[])
{

	int i = 0;                /* Iterators */
	int j = 0;
	int MAX = 100;            /* Maximum length of command */
	char *cmdIO[MAX];         /* Array to store tokens that are before '<' or '>' in command */
	memset(cmdIO,0,sizeof(cmdIO)/sizeof(char*));

    /* Store tokens in cmdIO until '<' or '>' is found */
	while(cmds[i] != NULL)
	{
		if((strcmp(cmds[i] , "<") == 0) || (strcmp(cmds[i] , ">") == 0))
			break;
		cmdIO[i] = cmds[i];
		i++;
	}

	/* 
	 * Check for '<' or '>' or '|' tokens and call functions accordingly 
     * Print error if second part of command after '<' or '>' or '|' is missing
	 */
	while(cmds[j] != NULL)
	{
		if(strcmp(cmds[j] , "|") == 0)
		{
			if(cmds[j+1] == NULL)
			{
				printf("Error... Please enter correct command !\n");
				return -1;
			}
			run_commandPipe(cmds);                        /* Call function to execute pipe */
			return 1;
		}
		else if(strcmp(cmds[j] , "<") == 0)
		{
			if(cmds[j+1] == NULL)
			{
				printf("Error... Please enter correct command !\n");
				return -1;
			}
			run_commandIO(cmdIO , cmds[j+1] , NULL , 1);  /* Call function for input redirection*/
			return 1;
		}
		else if(strcmp(cmds[j] , ">") == 0)
		{
			if(cmds[j+1] == NULL)
			{
				printf("Error... Please enter correct command !\n");
				return -1;
			}
			run_commandIO(cmdIO , NULL , cmds[j+1] , 0);  /* Call function for output redirection */
			return 1;
		}
		else if(strcmp(cmds[j] , "cd") == 0)
		{
			char cwd[MAX];                                /* Current working directory */
			char path[MAX];                               /* stores <directory> name */
			memset(cwd,0,sizeof(cwd)/sizeof(char));
			memset(path,0,sizeof(path)/sizeof(char));
			getcwd(cwd , sizeof(cwd));                    /* Get current working directory */
			if(cmds[j+1] == NULL)
			{
				strncpy(path , cwd , MAX);                 /* Set path to current working directory */                             
			}
			else
			{
				strncpy(path , cmds[j+1] , MAX);           /* Set path to one entered by user */
			}
			if(chdir(path) == -1)
			{
				printf("Error... Please enter correct directory path \n");
				return -1;
			}
			setenv((const char*)"PWD",(const char*)path,1);
			return 1;
		}
		else if(strcmp(cmds[j] , "help") == 0)
		{
			/*Help command is not supported by default in the /bin/sh shell so using /bin/bash*/
			run_commandHelp(cmds);
			return 1;
		}
		j++;
	}
	/* Call commands other than IO Redirection and Pipe */
	run_commandSimple(cmds);     
	return 0;
}

/* 
 * Function name: run_commandIO()
 *                    cmds - contains tokens before '<' or '>' in the command
 *                    inputF - contains input file name
 *                    outputF - contains output file name
 *                    flag - to differentiate '<' and '>'
 * Functionalities: 
 *      Create new process using fork()
 *      Output redirection if flag = 0
 *      Input redirection if flag = 1
 *      Open file, duplicate standard o/p or standard i/p using dup()
 *      Perform IO Redirection using execvp()
 */
void run_commandIO(char *cmds[] , char *inputF , char *outputF , int flag)
{
	int pid;                                       /* Process identification number */
	if((pid = fork()) == -1)
	{
		printf("Child process could not be created\n");
		return;
	}
	if(pid == 0)
	{
		if(flag == 0)                              /* Output Redirection */
		{ 
			/* Open or create file, truncate it to 0, write only mode */        
			int fd = open(outputF , O_CREAT | O_TRUNC | O_WRONLY , 0644);
			if(fd == -1){
				printf("Error opening file. File not found.\n");
				exit(1);
			}
			if(dup2(fd , STDOUT_FILENO) == -1){    /* Create copy of file descriptor */
				printf("Error duplicating STDOUT in file descriptor\n");
				exit(1);
			}       
			close(fd);                             /* Close file descriptor */
		}
		if(flag == 1)                              /* Input Redirection */
		{
			/* Open input file in read only mode */
			int fd1 = open(inputF , O_RDONLY);
			if(fd1 == -1){
				printf("Error opening file. File not found.\n");
				exit(1);
			}
			if(dup2(fd1 , STDIN_FILENO) == -1){    /* Create copy of file descriptor */
				printf("Error duplicating STDOUT in file descriptor\n");
				exit(1);
			}
			close(fd1);                            /* Close file descriptor */
		}
		execvp(cmds[0] , cmds);                    /* Perform IO redirection */
		printf("Error performing IO Redirection");
		exit(0);
	}
	else
	{
		waitpid(pid , NULL , 0);                   /* Wait for child process to get executed */
	}	
}


/* 
 * Function name: run_commandPipe()
 *                    cmds - all the tokens from pipe command entered
 * Functionalities: 
 *      Find the index of '|' token - pipeIndex
 *      Find the index of last token in cmds - cmdIndex
 *      Using above indices, save two '|' separated commands in cmdPipe1 and cmdPipe2
 *      Call pipe() to get two file descriptors used for pipelining
 *      fork() to create new process and dup() for duplicating file descriptors
 *      Create two child processes, one to write to pipe and other to read from pipe
 *      Execute pipe using execvp()
 */
void run_commandPipe(char *cmds[])
{
	int m=0;                                         /* Iterators */
	int n=0;
	int pipeIndex=0;                                 /* Index of '|' */
	int cmdIndex=0 ;                                 /* Index of last token */
	int pid1 , pid2 , status;                        /* Process IDs and status flag */
	int fd[2];                                       /* Array to store file descriptors returned by pipe */
	int MAX = 100;                                   /* Maximum length of command */
	char *cmdPipe1[MAX];                             /* Tokens before '|' */
	char *cmdPipe2[MAX];                             /* Tokens after '|' */
	memset(cmdPipe1,0,sizeof(cmdPipe1)/sizeof(char*));
	memset(cmdPipe2,0,sizeof(cmdPipe2)/sizeof(char*));
	while(strcmp(cmds[pipeIndex] , "|") != 0)        /* Find index where '|' is present in tokens */
	{
		pipeIndex++;
	}
	while(cmds[cmdIndex] != NULL)                    /* Find index where tokens ends */
	{
		cmdIndex++;
	}
	for(m=0 ; m < pipeIndex ; m++)                   /* Store tokens before '|' in cmdPipe1 */
	{
		cmdPipe1[m] = cmds[m];
	} 
	int var = pipeIndex + 1;   
	for(n=0 ; var < cmdIndex ; n++,var++)             /* Store tokens after '|' in cmdPipe2 */
	{
		cmdPipe2[n] = cmds[var];
	}

	/* Pipe returns two fds: fd[0] - reading and fd[1] - writing */
	pipe(fd);
	if((pid1 = fork()) == -1)
	{
		printf("Child1 process could not be created\n");
		return;
	}
	if(pid1 == 0)                                     /* Writer child process */
	{
		if(dup2(fd[1] , STDOUT_FILENO) == -1){        /* Create copy of file descriptor */
			printf("Error duplicating STDOUT in file descriptor\n");
			exit(1);
		} 
		close(fd[0]);                                 /* Not required */
		execvp(cmdPipe1[0] , cmdPipe1);               /* Execute 1st part of pipe command */
		printf("Error in execvp - Child1 could not execute 1st part of pipe command.\n");
		fflush(stdout);
	}
	if((pid2 = fork()) ==-1)
	{
		printf("Child2 process could not be created\n");
		return;
	}
	if(pid2 == 0)                                    /* Reader child process */
	{
		fflush(stdout);
		if(dup2(fd[0] , STDIN_FILENO) == -1){        /* Create copy of file descriptor */
			printf("Error duplicating STDIN in file descriptor\n");
			exit(1);
		} 
		fflush(stdout);
		close(fd[1]);                                /* Not required */
		execvp(cmdPipe2[0] , cmdPipe2);              /* Execute 2nd part of pipe command */
		printf("Error in execvp - Child2 could not execute 2nd part of pipe command.\n");
		fflush(stdout);
	}
	close(fd[0]);
	close(fd[1]);
	while(wait(NULL) > 0);                           /* Wait for all child process to exit */
	fflush(stdout);
	
}

/* 
 * Function name: run_commandSimple()
 *                    cmds - all tokens from entered command 
 * Functionalities: 
 *      Create new process using fork()
 *      execute commands with or without arguments using execvp()
 */
void run_commandSimple(char *cmds[])
{
	int pid;                                          /* Process ID */
	if((pid = fork()) == -1)
	{
		printf("Child process could not be created\n");
		return;
	}
	else if(pid == 0)
	{
		execvp(cmds[0] , cmds);                       /* Execute command */
		printf("Error in execvp - Child could not execute command.\n");
		exit(0);
	}
	else
	{
		waitpid(pid , NULL , 0);                      
	}
}

/* 
 * Function name: run_commandHelp()
 *                    cmds - all tokens from entered command 
 * Functionalities: 
 *      Create new process using fork()
 *      execute commands with or without arguments using execve()
 *      Help command is not supported by default in the /bin/sh shell so using /bin/bash
 */
void run_commandHelp(char *cmds[])
{
	int pid;                                          /* Process ID */
	if((pid = fork()) == -1)
	{
		printf("Child process could not be created\n");
		return;
	}
	else if(pid == 0)
	{
		/*Help command is not supported by default in the /bin/sh shell so using /bin/bash*/
		char *helpcmdargs[] = {"/bin/bash","-c",cmds[0],"|","more",NULL};
		execve("/bin/bash" , helpcmdargs ,NULL);      /* Execute command */                       
		printf("Error in execvp - Child could not execute command.\n");
		exit(0);
	}
	else
	{
		waitpid(pid , NULL , 0);                      
	}
}


