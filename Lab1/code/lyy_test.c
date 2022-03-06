#include<stdio.h>
#include<stdlib.h>
#include<time.h>

int length = 10;
int range = 1000;
void main()
{
	double begin, end;
	begin = clock();
	srand((unsigned)time(NULL));
	int i, j,k;
	int a[1000];
	for (i = 0; i < length; i++)
	{
		a[i] = rand() % range;
		printf("%d ", a[i]);
	}
	for (i = 1; i < length; i++)
	{
		if (a[i] < a[i - 1])
		{
			j = i - 1;
			k = a[i];
			while (k < a[j])
			{
				a[j + 1] = a[j];
				j--;
			}
			a[j + 1] = k;
		}
	}
	printf("\n");
	for (i = 0; i < length; i++)
		printf("%d ", a[i]);
	end = clock();
	printf("\n运行时间：%f", (end - begin)/CLOCKS_PER_SEC);
}