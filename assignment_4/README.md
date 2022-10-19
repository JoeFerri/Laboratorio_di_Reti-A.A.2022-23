[![License: AGPL v3](https://img.shields.io/badge/License-AGPL%20v3-blue.svg)](https://www.gnu.org/licenses/agpl-3.0)
[![GitHub issues](https://img.shields.io/github/issues/JoeFerri/Laboratorio_di_Reti-A.A.2022-23)](https://github.com/JoeFerri/Laboratorio_di_Reti-A.A.2022-23/issues)
[![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-2.0-4baaaa.svg)](code_of_conduct.md)

# Assignment 4

> The program takes a list of text file paths separated by spaces,
> and counts the occurrences of the letters in each file.
> 
> The file encoding format is assumed to be UTF-8
> unless a specific encoding parameter is passed.
> 
> At the end of the processing it creates a "report.txt" file with the format:
>  
>     <a>,<number of occurrences>\n
>     <b>,<number of occurrences>\n
>                  ...           \n
>     <z>,<number of occurrences>
> 
> If there is an error in one or more files,
> the "report.txt" file will still be generated
> which will contain only the value -1 for all letters.
> 
> ## The program also accepts the following extra parameters:
>
> | parameter             | description|
> | --------------------- | ---------- |
> | `--debug`             | To enable verbose debugging printing<br>and the number of occurrences for each individual file |
> | `--test-all-text-txt` | Passed arguments are ignored and all files<br>in the assignment_4/data folder are processed |
> | `--normalize`         | Characters are normalized.<br>After normalization `'a' == 'A' == 'à' == 'á'`<br>Without normalization (default) `'a' == 'A'`, but `'à'` is ignored |
> 
> ### the following parameters set the file encoding:
> |                |
> | -------------- |
> | `--iso-8859-1` |
> | `--us-ascii`   |
> | `--utf-16`     |
---

## IMPLEMENTATION:

A pool of `CharsReader` is created, each of which (`Thread`)
is in charge of processing a single file and updating the total report.
The `FilesReport` class holds both the total report
and the respective individual file reports.

## Dependencies
- utils.jar

### compilation
    $ git clone https://github.com/JoeFerri/Laboratorio_di_Reti-A.A.2022-23
    $ cd ../path/to/the/file/assignment_4/src
    $ javac -cp ./../utils.jar ./assignment_4/*.java ./assignment_4/Assignment.java -d ./../build

### running
    $ cd ../path/to/the/file/assignment_4/build

    $ java assignment_4.Assignment <path_1> ... <path_n> [--debug] [--test-all-text-txt] [--normalize] [...]

    $ java -cp ".;./utils.jar" assignment_4.Assignment <argument...> # windows
    $ java -cp ".:./utils.jar" assignment_4.Assignment <argument...> # Unix

# examples
### parses and processes file_1.txt and file_2.txt:

    $ java assignment_4.Assignment ./../test/data/file_1.txt ./../test/data/file_2.txt

### parses and processes file_5.txt, normalizes characters and uses UTF-16 encoding

    $ java assignment_4.Assignment ./../test/data/file_5.utf_16.txt --utf-16 --normalize

### since it uses UTF-8 encoding by default, it throws an exception

    $ java assignment_4.Assignment ./../test/data/file_5.utf_16.txt

### parses and processes file_1.txt and file_2.txt, debug prints on stdout:

    $ java assignment_4.Assignment ./../test/data/file_1.txt ./../test/data/file_2.txt --debug

### parses and processes file_1.txt and file_2.txt, normalizes characters, debug prints on stdout:

    $ java assignment_4.Assignment ./../test/data/file_1.txt ./../test/data/file_2.txt --normalize --debug

### parses and processes all files in the assignment_4/data:

    $ java assignment_4.Assignment --test-all-text-txt

## Documentation
For the documentation see the notes inside the source code or [wiki page](https://github.com/JoeFerri/Laboratorio_di_Reti-A.A.2022-23/wiki)

### Code of conduct
[ENG](code_of_conduct-eng.md)

[ITA](code_of_conduct-ita.md)

# License 

## General license 

GNU Affero General Public License v3.0 only

  Copyright (C) 2022 Giuseppe Ferri

  assignment_4 is free software: you can redistribute it and/or modify
  it under the terms of the GNU Affero General Public License as
  published by the Free Software Foundation, either version 3 of the
  License, or (at your option) any later version.
  
  assignment_4 is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Affero General Public License for more details.
  
  You should have received a copy of the GNU Affero General Public License
  along with this program (see file COPYING).  If not, see <http://www.gnu.org/licenses/>.
