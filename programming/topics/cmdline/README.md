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
	{
	case 'k':
	  if (strlen (optarg) >= KEY_LENGTH)
	    {
	      exit (EXIT_FAILURE);
	    }
	  else
	    {
	      strncpy (key, optarg, KEY_LENGTH - 1);
	    }
	  break;

	case 't':
	  nsecs = atoi (optarg);
	  break;

	case 'h':
	  help (argv[0]);
	  exit (EXIT_SUCCESS);

	case 'v':
	  version ();
	  exit (EXIT_SUCCESS);
	  break;

	default:
	  printf ("?? getopt returned character code 0%o ??\n", c);
	  exit (EXIT_FAILURE);
	}
    }

  printf("--key=%s\n", key);
  printf("--timeout=%d\n", nsecs);
  if (optind < argc)
    {
      printf ("non-option ARGV-elements: ");
      while (optind < argc)
	printf ("%s ", argv[optind++]);
      printf ("\n");
      exit (EXIT_FAILURE);
    }

  exit (EXIT_SUCCESS);
}
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
$ ./parse-cmdline -h
Usage: ./parse-cmdline [-k key] [-t nsecs]
$
```

## C++

## Haskell

## Java

## NodeJS

This sample requires package `commander`.

```
/*
 * Web API test script template for Copy & Paste oriented programming ^_^||...
 */
var http = require('axios');
var program = require('commander');
const assert = require('assert').strict;

program
  .option('-s, --server-ip <server ip>', 'server ip')
  .option('-p, --server-port <server port>', 'server port')
  .option('    --my-id   <id>', 'my ID')
  .option('    --my-name <name>', 'my Name');
       
program.parse(process.argv);

var opts = program.opts();
checkOpts(opts);

var requestBody = {
    myId: opts.myId,
    myIp: opts.myName
};

var url = `http://${opts.serverIp}:${opts.serverPort}/api/my/path`; 

console.log(`request = ${JSON.stringify(requestBody)}`);

var request = http.post(url, requestBody)
  .then(function (response) {
     console.log(`response = ${JSON.stringify(response.data)}`);
     assert.equal(response.data.myId, opts.myId, "my ID does not match.");
     assert.equal(response.data.myName, opts.myName, "my Name does not match.");
     assert.equal(response.data.result, 'SUCCESS', "Result does not match, expected value is 'SUCCESS'.");
  })
  .catch(function (error) {
    console.log("there're complicated situations...");
    console.log(JSON.stringify(error));
    process.exit(-1);
  });

function checkOpts(opts) {
  console.log(`options = ${JSON.stringify(opts)}`);;
  if(!(opts.serverIp 
              && opts.serverPort 
              && opts.myId
              )) {
    console.error('Invalid command: missing required option.');
    program.outputHelp();
    process.exit(-1);
  }
```
Run:
```
$ nodejs parse-cmdline.js --my-name=1 --my-id=1
options = {"myId":"1","myName":"1"}
Invalid command: missing required option.
Usage: parse-cmdline [options]

Options:
  -s, --server-ip <server ip>      server ip
  -p, --server-port <server port>  server port
      --my-id   <id>               my ID
      --my-name <name>             my Name
  -h, --help                       output usage information
$
```
## Python
```
from optparse import OptionParser

parser = OptionParser()
parser.add_option("-f", "--file", dest="filename",
    help="write report to FILE", metavar="FILE")
parser.add_option("-q", "--quiet",
    action="store_false", dest="verbose", default=True,
    help="don't print status messages to stdout")

(options, args) = parser.parse_args()
print options, args
```

Run:
```
$ python parse-cmdline.py
{'verbose': True, 'filename': None} []
$ python parse-cmdline.py file.txt
{'verbose': True, 'filename': None} ['file.txt']
$ python parse-cmdline.py --file my-secret.doc file.txt
{'verbose': True, 'filename': 'my-secret.doc'} ['file.txt']
$ python parse-cmdline.py -h
Usage: parse-cmdline.py [options]

Options:
  -h, --help            show this help message and exit
  -f FILE, --file=FILE  write report to FILE
  -q, --quiet           don't print status messages to stdout
$
```

## Shell

