#include <stdio.h>
#include <math.h>
int main()
{
    //entree de donne
    float phi1=(sqrt(5)-1)/2;
    float phi0=1;
    float phi=0.0;
    for (int i = 0; i < 50; i++)
    {
        /* code */
        phi=phi0-phi1;
        phi0=phi1;
        phi1=phi;
    }
    
    printf("%f",phi);
    return 0;
}
