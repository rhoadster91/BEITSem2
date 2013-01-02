#include<stdio.h>
#include<conio.h>
#include<graphics.h>
#include<math.h>

#define MAX 10						// Defines max no. of objects

typedef struct point_coordinates			// Useful in drawing graph
{
	int x;						// X co-ordinate of point
	int y;						// Y co-ordinate of point
} pcoords;

float ipmatrix[MAX][MAX];				// Input matrix
int binmat[MAX][MAX];					// Binary matrix
pcoords point[MAX];					// Points for graph
int n = 0;						// Total no. of objects

void drawGraph();					// Function to draw graph

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
			if(temp<=threshold)		// Compare to threshold
				binmat[i][j] = 0;	// 0 if lesser
			else
				binmat[i][j] = 1;	// 1 if greater
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
	double thetainc;				// Theta increment
	int xc, yc;					// Center point of screen
	int xi, yi, xt, yt;				
	int i, j;
	char name[3];					
	initgraph(&gd, &gm, "C:\\TC\\BGI");
	clrscr();
	cleardevice();
	xc = getmaxx()/2;
	yc = getmaxy()/2;
	thetainc = 2 * M_PI;				// Increment = (2 * pi)/n
	thetainc = thetainc / n;		
	for(i = 1;i<=n;i++)
	{
		xi = xc + (R * cos(theta));		// Polar resolution of X component
		yi = yc + (R * sin(theta));		// Polar resolution of Y component
		point[i].x = xi;			// Set point co-ords
		point[i].y = yi;
		circle(xi, yi, 2);			// Circle to denote point, makes it more clear to see
		floodfill(xi, yi, WHITE);
		xt = xc + (R2 * cos(theta));		// R2 is slightly greater than R
		yt = yc + (R2 * sin(theta));		// so name of point appears away from point
		sprintf(name, "%d", i);
		outtextxy(xt, yt, name);
		theta += thetainc;

	}
	for(j=1;j<=n;j++)
	{
		for(i=1; i<j;i++)
		{
			if(binmat[i][j]==1)		// If binary matrix marked, connect i & j points
				line(point[i].x, point[i].y, point[j].x, point[j].y);
		}

	}

}