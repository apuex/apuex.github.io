# Parsing Command Line Arguments

## C
```
#include <stdio.h>
#include <stdlib.h>
#include <getopt.h>
#include <string.h>
#include <sys/time.h>
#include <sys/types.h>
#include <unistd.h>

#define KEY_LENGTH 128

void
help (char *cmd)
{
  fprintf (stderr, "Usage: %s [-k key] [-t nsecs]\n", cmd);
}

void
version ()
{
  fprintf (stderr, "parse-cmdline version 1.0.0.\n");
}

int
main (int argc, char *argv[])
{
  fd_set rfds;
  struct timeval tv;
  int retval;
  int nsecs = 5;
  char key[KEY_LENGTH]; memset(key, 0, KEY_LENGTH);
  int c;
  int digit_optind = 0;

  while (1)
    {
      int this_option_optind = optind ? optind : 1;
      int option_index = 0;
      static struct option long_options[] = {
	{"key", required_argument, 0, 'k'},
	{"timeout", required_argument, 0, 't'},
	{"version", no_argument, 0, 'v'},
	{"help", no_argument, 0, 'h'},
	{0, 0, 0, 0}
      };

      c = getopt_long (argc, argv, "k:t:vh", long_options, &option_index);
      if (c == -1)
	break;

      switch (c)
```
Compile & Run:
```
$ gcc -o parse-cmdline parse-cmdline.c
$ ./parse-cmdline
--key=
--timeout=5
$ ./parse-cmdline -k my-id -t 10
--key=my-id
--timeout=10
$ ./parse-cmdline -k my-id -t 10 file.txt
--key=my-id
--timeout=10
non-option ARGV-elements: file.txt 
$
```

## C++

## Haskell

## Java

## NodeJS

## Python

## Shell

