/*************************************************************************************
 * Project: Simple shell
 *
 * Shweta Kharat
 *
 * April 2017
 *************************************************************************************/


/* File contains functions declarations for functions used in main.c */

int run_command(char *cmds[]);

void run_commandIO(char *cmds[] , char *inputF , char *outputF , int flag);

void run_commandPipe(char *cmds[]);

void run_commandSimple(char *cmds[]);

void run_commandHelp(char *cmds[]);
