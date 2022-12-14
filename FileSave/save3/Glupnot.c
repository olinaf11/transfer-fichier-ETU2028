:2]\n");
        fprintf(gnuplotPipe, "set style data linespoints\n");

        fprintf(gnuplotPipe, "plot 'mesure.txt', sin(x)\n");
        fflush(gnuplotPipe);
        pclose(gnuplotPipe);
    }
    else printf("Gnulpot not found\n");
    return 0;
}
