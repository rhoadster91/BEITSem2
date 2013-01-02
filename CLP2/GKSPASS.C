#include<stdio.h>
#include<conio.h>
#include<graphics.h>
#include<math.h>

#define MAX 10

typedef struct point_coordinates
{
	int x;
	int y;
} pcoords;

float ipmatrix[MAX][MAX];
int binmat[MAX][MAX];
pcoords point[MAX];
int n = 0;

void drawGraph();

void main()
{
	int i, j;
	float temp, threshold;
	clrscr();
	printf("Please enter no. of objects: ");
	scanf("%d", &n);
	printf("Enter threshold: ");
	scanf("%f", &threshold);
	printf("Enter matrix: ");
	for(i=1;i<=n;i++)
	{
		for(j=i+1;j<=n;j++)
		{
			printf("\nEnter element[%d][%d]: ", i, j);
			scanf("%f", &temp);
			ipmatrix[i][j] = temp;
			if(temp<=threshold)
				binmat[i][j] = 0;
			else
				binmat[i][j] = 1;
		}
	}
	printf("\nPrinting matrix...\n");
	for(j=1;j<=n;j++)
	{
		for(i=1; i<j;i++)
			printf("%.1f\t", ipmatrix[i][j]);
		printf("\n\n");
	}
	printf("Printing binary matrix...\n");
	for(j=1;j<=n;j++)
	{
		for(i=1; i<j;i++)
			printf("%d\t", binmat[i][j]);
		printf("\n\n");
	}
	printf("Shifting to graphics mode...\n");
	getch();
	drawGraph();
	getch();

}

void drawGraph()
{
	int gd = DETECT, gm = 0, R = 60, R2 = 80;
	double theta = 0;
	double thetainc;
	int xc, yc;
	int xi, yi, xt, yt;
	int i, j;
	char name[3];
	initgraph(&gd, &gm, "C:\\TC\\BGI");
	clrscr();
	cleardevice();
	xc = getmaxx()/2;
	yc = getmaxy()/2;
	thetainc = 2 * M_PI;
	thetainc = thetainc / n;
	for(i = 1;i<=n;i++)
	{
		xi = xc + (R * cos(theta));
		yi = yc + (R * sin(theta));
		point[i].x = xi;
		point[i].y = yi;
		circle(xi, yi, 2);
		floodfill(xi, yi, WHITE);
		xt = xc + (R2 * cos(theta));
		yt = yc + (R2 * sin(theta));
		sprintf(name, "%d", i);
		outtextxy(xt, yt, name);
		theta += thetainc;

	}
	for(j=1;j<=n;j++)
	{
		for(i=1; i<j;i++)
		{
			if(binmat[i][j]==1)
				line(point[i].x, point[i].y, point[j].x, point[j].y);
		}

	}

}