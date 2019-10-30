CC=gcc
EXENAME=mysh
SRCFILES=main.c

all:
	$(CC) -o $(EXENAME) $(SRCFILES)

clean:
	rm -f *.o $(EXENAME)

run:
	./$(EXENAME)

debug:
	gdb $(EXENAME)