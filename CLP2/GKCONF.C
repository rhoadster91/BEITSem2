#include<stdio.h>
#include<conio.h>
#include<string.h>

#define MAXLEN 20
#define MAXCACHESIZE 50


FILE *fp = NULL;
FILE *fp2 = NULL;

typedef struct TokenCache
{
	char token[MAXLEN];
	int freq;
} tCache;

char *readNextToken(FILE *);
char *checkSuffix(char *kw, char *suf);
int isStopWord(char *);

int stopwordCacheSize = 0;
int suffixCacheSize = 0;
int tokenCacheSize = 0;
char (stopwordCache[MAXLEN])[MAXCACHESIZE];
char (suffixCache[MAXLEN])[MAXCACHESIZE];
tCache tokenCache[MAXCACHESIZE];

void main()
{
	char kw[MAXLEN], sw[MAXLEN], suf[MAXLEN], temp[MAXLEN];
	int i = 0;
	clrscr();
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
		strcpy(sw, readNextToken(fp2));
		if(sw[0]!='\0')
			strcpy(stopwordCache[stopwordCacheSize++], sw);
		else
			break;

	}
	fclose(fp2);
	printf("Complete!!\nGenerating suffix cache...");
	fp2 = fopen("Girish\\suffix.txt", "r");
	while(1)
	{
		strcpy(suf, readNextToken(fp2));
		if(suf[0]!='\0')
			strcpy(suffixCache[suffixCacheSize++], suf);
		else
			break;

	}
	fclose(fp2);
	fp2 = fopen("Girish\\pass1.txt", "w+");
	printf("Complete!!\nProcessing stopwords...");
	while(1)
	{
		strcpy(kw, readNextToken(fp));
		if(kw[0]!='\0')
		{
			if(!isStopWord(kw))
				fprintf(fp2, "%s\n", kw);
		}
		else
			break;
	}
	printf("Complete!!\nProcessing suffixes...");
	fclose(fp2);
	fclose(fp);
	fp = fopen("Girish\\pass1.txt", "r");
	fp2 = fopen("Girish\\pass2.txt", "w+");
	while(1)
	{
		strcpy(kw, readNextToken(fp));
		if(kw[0]=='\0')
			break;
		for(i=0;i<suffixCacheSize;i++)
		{
			strcpy(temp, checkSuffix(kw,suffixCache[i]));
			if(strcmp(temp,kw)!=0)
				break;
		}
		fprintf(fp2, "%s\n", temp);

	}
	printf("Complete!!\nPerforming frequency count...");
	fclose(fp);
	fclose(fp2);
	fp = fopen("Girish\\pass2.txt", "r");
	fp2 = fopen("Girish\\freq.txt", "w+");
	while(1)
	{
		strcpy(temp, readNextToken(fp));
		if(temp[0]=='\0')
			break;
		for(i=0;i<tokenCacheSize;i++)
		{
			if(strcmp(temp,tokenCache[i].token)==0)
			{
				tokenCache[i].freq++;
				break;
			}
		}
		if(i==tokenCacheSize)
		{
			strcpy(tokenCache[tokenCacheSize].token, temp);
			tokenCache[tokenCacheSize++].freq = 1;
		}

	}
	for(i=0;i<tokenCacheSize;i++)
		fprintf(fp2, "%d\t%s\n", tokenCache[i].freq, tokenCache[i].token);
	fclose(fp);
	fclose(fp2);
	printf("Complete!!\nPress any key to continue...");
	getch();
}

char *readNextToken(FILE *fp)
{
	char cur;
	char keyword[MAXLEN];
	int cp = 0;
	do
	{
		cur = fgetc(fp);
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

char *checkSuffix(char *kw, char *suf)
{
	int i=0, j=0, k=0;
	char temp[MAXLEN];
	if(suf[0]=='\0')
		return kw;
	while(1)
	{
		j=0;
		if(kw[i]==suf[j])
		{
			while(1)
			{
				if(kw[i]!=suf[j])
					break;
				else if(kw[i]=='\0' && suf[j]=='\0')
				{
					for(k=0;k<strlen(kw)-strlen(suf);k++)
						temp[k] = kw[k];
					temp[k] = '\0';
					return temp;
				}
				i++;
				j++;
			}
		}
		i++;
		if(kw[i]=='\0')
			return kw;
	}
}