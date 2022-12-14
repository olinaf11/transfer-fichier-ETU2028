#include <stdio.h>

int main()
{
    printf("Open a pipe to glupnot \n");
    FILE *gnuplotPipe = popen("C:\\\"Program Files\"\\gnuplot\\bin\\gnuplot -persist","w");
    if (gnuplotPipe) // if glupnot is found
    {
        fprintf(gnuplotPipe, "set term