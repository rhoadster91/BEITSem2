#include<stdio.h>
#include<conio.h>
#include<string.h>

#define MAXLEN 20
#define MAXCACHESIZE 50

typedef struct index_tuple
{
	char token[MAXLEN];
	int offset;
	int block;
} index;

FILE *fp = NULL;
FILE *fp2 = NULL;

char *readNextToken(FILE *, int *, int *);
int isStopWord(char *);
void updateAddress();
void resetPointers();
void sortIndex();

int blockSize = 5;
int stopwordCacheSize = 0;
int indexSize = 0;
char (stopwordCache[MAXLEN])[MAXCACHESIZE];
index indexCache[MAXCACHESIZE];

int offset = -1;
int blockPtr = 0;
int mode = 0;

void main()
{
	char kw[MAXLEN], sw[MAXLEN], temp[MAXLEN];
	int a, flag;
	int off, block, add, adj;
	char cur;
	clrscr();
	printf("Enter addressing scheme:\n1. Simple\n2. Block\nEnter your choice: ");
	scanf("%d", &mode);
	if(mode==2)
	{
		printf("Enter block size: ");
		scanf("%d", &blockSize);
	}
	fp = fopen("Girish\\doc1.txt", "r");
	fp2 = fopen("Girish\\stop.txt", "r");
	if(fp==NULL || fp2==NULL)
	{
		printf("ERROR 404: File not found.");
		exit(0);
	}
	printf("Generating stopword cache...");
	while(1)
	{
		strcpy(sw, readNextToken(fp2, &a, &a));
		if(sw[0]!='\0')
			strcpy(stopwordCache[stopwordCacheSize++], sw);
		else
			break;

	}
	fclose(fp2);
	printf("Done!!\nReading file and creatign index...");
	resetPointers();
	while(1)
	{
		strcpy(kw, readNextToken(fp, &off, &block));
		if(kw[0]!='\0')
		{
			if(isStopWord(kw)==0)
			{
				strcpy(indexCache[indexSize].token, kw);
				indexCache[indexSize].offset = off;
				indexCache[indexSize].block = block;
				indexSize++;
			}
		}
		else
			break;

	}
	fclose(fp);
	printf("Done!!\nSorting...");
	sortIndex();
	printf("Done!!\nPrinting index...");
	fp = fopen("Girish\\index.txt", "w+");
	switch(mode)
	{
		case 1:
			fprintf(fp, "VOCAB\tOCCURENCE");
			strcpy(temp, "");
			for(a=0;a<indexSize;a++)
			{
				if(strcmp(temp, indexCache[a].token)!=0)
				{
					fprintf(fp, "\n%s\t%d", indexCache[a].token, indexCache[a].offset);
					strcpy(temp, indexCache[a].token);
				}
				else
					fprintf(fp, ", %d", indexCache[a].offset);
			}
			break;

		case 2:
			fprintf(fp, "VOCAB\t{BLOCK, OFFSET}");
			strcpy(temp, "");
			for(a=0;a<indexSize;a++)
			{
				if(strcmp(temp, indexCache[a].token)!=0)
				{
					fprintf(fp, "\n%s\t{%d, %d}", indexCache[a].token, indexCache[a].block, indexCache[a].offset);
					strcpy(temp, indexCache[a].token);
				}
				else
					fprintf(fp, ", {%d, %d}", indexCache[a].block, indexCache[a].offset);

			}

			break;
	}
	fclose(fp);
	printf("Done!!\nDisplaying...\n\n");
	fp = fopen("GIRISH\\doc1.txt", "r");
	fp2 = fopen("GIRISH\\doc1.txt", "r");
	if(mode==1)
	{
		add = 0;
		flag = 1;
		do
		{
			adj = 0;
			for(a=0;a<20;a++)
			{
				cur = fgetc(fp);
				if(cur==EOF)
					break;
				if(cur==' ' || cur=='.' || cur=='\n')
					flag = 1;
				if(flag==1 && !(cur==' ' || cur=='.' || cur=='\n'))
				{
					if(adj==0 && add>=100)
						adj = 1;
					else if(adj==1 && add>=100)
						printf("\b");
					printf("%d", add);
					if(add<10)
						printf(" ");
					flag = 0;
				}
				else
					printf("  ");
				add++;
				if(flag==0)
					a--;
			}
			printf("\n");
			for(a=0;a<20;a++)
			{
				cur = fgetc(fp2);
				if(cur==EOF)
					break;
				if(cur=='\n')
					printf("  ");
				else
					printf("%c ", cur);
			}
			printf("\n\n");
		} while(cur!=EOF);
	}
	else
	{
		add = 0;

		do
		{
			adj = 0;
			for(a=0;a<(3*blockSize);a++)
			{
				cur = fgetc(fp);
				if(cur==EOF)
					break;
				if(add % blockSize==0)
				{
					if(adj==0 && (add/blockSize)>=100)
						adj = 1;
					else if(adj==1 && (add/blockSize)>=100)
						printf("\b");
					printf("%d", add/blockSize);
					if((add/blockSize)<10)
						printf(" ");

				}
				else
					printf("  ");
				add++;

			}
			printf("\n");
			for(a=0;a<(3*blockSize);a++)
			{
				cur = fgetc(fp2);
				if(cur==EOF)
					break;
				if(cur=='\n')
					printf("  ");
				else
					printf("%c ", cur);
			}
			printf("\n\n");

		} while(cur!=EOF);
	}
	getch();
}

char *readNextToken(FILE *fp, int *off, int *block)
{

	char cur;
	char keyword[MAXLEN];
	int cp = 0;
	if(mode==1)
	{
		*off = offset + 1;
		*block = blockPtr;
	}
	else
	{
		*off = (offset + 1) % blockSize;
		*block = blockPtr + ((offset + 1) / (blockSize));
	}
	do
	{
		cur = fgetc(fp);
		updateAddress();
		if(cur==EOF)
		{
			keyword[0] = '\0';
			return keyword;
		}
	}while(cur==' ' || cur=='.' || cur=='\n' || cur==EOF);
	while(1)
	{
		if(cur!=' ' && cur!='.' && cur!='\n' && cur!=EOF)
		{
			keyword[cp++] = cur;
		}
		else
			break;
		cur = fgetc(fp);
		updateAddress();
	}
	keyword[cp] = '\0';
	return keyword;
}

int isStopWord(char *kw)
{
	int i = 0;
	for(i=0;i<stopwordCacheSize;i++)
	{
		if(strcmp(stopwordCache[i], kw)==0)
			return 1;
	}
	return 0;
}

void resetPointers()
{
	offset = -1;
	blockPtr = 0;
}

void updateAddress()
{
	if(mode==1)
	{
		offset++;
	}
	else
	{
		offset++;
		blockPtr += offset / blockSize;
		offset = offset % blockSize;
	}
}

void sortIndex()
{
	index tempCache[MAXCACHESIZE];
	index temp;
	int isChecked[MAXCACHESIZE];
	int marked = 0;
	int j, least;
	for(j=0;j<indexSize;j++)
		isChecked[j] = 0;
	while(marked!=indexSize)
	{
		least = 0;
		while(isChecked[least]!=0)
			least++;
		for(j=0;j<indexSize;j++)
		{
			if(strcmp(indexCache[least].token, indexCache[j].token) > 0 && isChecked[j]!=1)
			{
				least = j;
			}
		}
		tempCache[marked++] = indexCache[least];
		isChecked[least] = 1;
	}
	for(j = 0;j<indexSize;j++)
		indexCache[j] = tempCache[j];
}
