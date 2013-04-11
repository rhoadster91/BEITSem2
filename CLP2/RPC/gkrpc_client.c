/*
 * This is sample code generated by rpcgen.
 * These are only templates and you can use them
 * as a guideline for developing your own functions.
 */

#include "gkrpc.h"


void
gk_rpc_1(char *host)
{
	CLIENT *clnt;
	int ch = 0, sch = 0;
	int  *result;
	input_pair  arith_arg;
	int  rev_num_1_arg = 0;
	char *my_string = (char *)malloc(40 * sizeof(char));
	char **result_string;
	
#ifndef	DEBUG
	clnt = clnt_create (host, GK_RPC, RPC_VERS, "udp");
	if (clnt == NULL) 
	{
		clnt_pcreateerror (host);
		exit (1);
	}
#endif	/* DEBUG */

	printf("What would you like to perform??\n1. Arithmetic operations\n2. Reverse number\n3. String lower to upper case\n4. Exit\nEnter your choice:");
	scanf("%d", &ch);
	switch(ch)
	{
		case 1:
			printf("Enter a:");
			scanf("%d", &(arith_arg.a));
			printf("Enter b:");
			scanf("%d", &(arith_arg.b));	
			printf("Choose operation:\n1. Addition (a+b)\n2. Subtraction (a-b)\n3. Multiplication (a*b)\n4. Division (a/b)\n5. Exit\nEnter your choice: ");
			scanf("%d", &sch);
			switch(sch)
			{
				case 1:
					result = add_1(&arith_arg, clnt);
					if (result == (int *) NULL) 
					{
						clnt_perror (clnt, "call failed");
					}
					else
					{
						printf("Sum of %d and %d is %d.\n", arith_arg.a, arith_arg.b, *result);
					}
					break;
				case 2:
					result = sub_1(&arith_arg, clnt);
					if (result == (int *) NULL) 
					{
						clnt_perror (clnt, "call failed");
					}
					else
					{
						printf("Subtraction of %d and %d is %d.\n", arith_arg.a, arith_arg.b, *result);
					}
					break;
				case 3:
					result = mult_1(&arith_arg, clnt);
					if (result == (int *) NULL) 
					{
						clnt_perror (clnt, "call failed");
					}
					else
					{
						printf("Product of %d and %d is %d.\n", arith_arg.a, arith_arg.b, *result);
					}
					break;
				case 4:
					if(arith_arg.b==0)
					{
						printf("Cannot divide by zero.\n");
						break;
					}
					result = div_1(&arith_arg, clnt);
					if (result == (int *) NULL) 
					{
						clnt_perror (clnt, "call failed");
					}
					else
					{
						
						printf("Division of %d and %d is %d.\n", arith_arg.a, arith_arg.b, *result);
					}
					break;
				default:
					printf("Invalid option\n");
					break;

			}
			break;

		case 2:
			printf("Enter the number to be reversed: ");
			scanf("%d", &rev_num_1_arg);		
			result = rev_num_1(&rev_num_1_arg, clnt);
			if (result == (int *) NULL) 
			{
				clnt_perror (clnt, "call failed");
			}
			else
			{
				printf("Reverse of %d is %d.", rev_num_1_arg, *result);
			}
			break;

		case 3:
			printf("Enter the string: ");
			scanf("%s", my_string);	
			result_string = lower_to_upper_1(&my_string, clnt);
			if (result == (int *) NULL) 
			{
				clnt_perror (clnt, "call failed");
			}
			else
			{
				printf("After switching case, the answer is %s.", *result_string);
			}
			break;
		default:
			printf("Invalid option.\n");
			break;
	}
	printf("\nGood bye\n\n");	
#ifndef	DEBUG
	clnt_destroy (clnt);
#endif	 /* DEBUG */
}


int
main (int argc, char *argv[])
{
	char *host;

	if (argc < 2) {
		printf ("usage: %s server_host\n", argv[0]);
		exit (1);
	}
	host = argv[1];
	gk_rpc_1 (host);
exit (0);
}
