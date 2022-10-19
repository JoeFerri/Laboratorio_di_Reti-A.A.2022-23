[![License: AGPL v3](https://img.shields.io/badge/License-AGPL%20v3-blue.svg)](https://www.gnu.org/licenses/agpl-3.0)
[![GitHub issues](https://img.shields.io/github/issues/JoeFerri/Laboratorio_di_Reti-A.A.2022-23)](https://github.com/JoeFerri/Laboratorio_di_Reti-A.A.2022-23/issues)
[![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-2.0-4baaaa.svg)](code_of_conduct.md)

# Assignment 3

> Simulation of the use of a university laboratory.
>
> A laboratory containing 20 computers is created.
>
> Each user can access a computer by requesting access to the tutor.
>
> Users are divided by role and priority.
>
> Students (`Studente`) (priority 0) need any 1 computer.
> Graduates (`Tesista`) (priority 1) need the specific computer on which their software is installed.
> Professors (`Professore`) (priority 2) need all computers.
> 
> The program instantiates the laboratory and the tutor, then instantiates a list of users whose number is parameterized by the arguments passed to the program.
> Arguments are optional but must respect positional order.
>
> The program also accepts extra parameters:
> 
> - `--table`            to print the queues of active or waiting users on the screen
>
> - `--debug`            to enable verbose debugging printing
>
> - `--stdout-to-file`   to redirect the stdout to file (Assignment.stdout.log)
>
> - `--stderr-to-file`   to redirect the stderr to file (Assignment.stderr.log)
> 
> Each user can log on to computers multiple times (cycles), but each logon is processed as an independent request, and queued to the others.
> A scheduler takes care of activating users at random time intervals using a `SchedulazioneUtente` data structure.
> Each schedule activates the respective user.
>
> The user makes an access request to the tutor (blocker), who provides a computer id as soon as possible (based on the user's constraints and the position in the request queue).
> The user uses the `computer(id)`, then requests the tutor to release access to the computer, then terminates, or is rescheduled for another cycle.
---

## Dependencies
- utils.jar

### compilation
    $ git clone https://github.com/JoeFerri/Laboratorio_di_Reti-A.A.2022-23
    $ cd ../path/to/the/file/assignment_3/src
    $ javac -cp ./../utils.jar ./assignment_3/*.java -d ./../build

### running
    $ cd ../path/to/the/file/assignment_3/build

    $ java <class_path> [-ea] assignment_3.Assignment [<params>...] [<studenti_size>] [<tesisti_size>] [<professori_size>]

    $ java -cp ".;./utils.jar" assignment_3.Assignment # windows
    $ java -cp ".:./utils.jar" assignment_3.Assignment # Unix
#### with enabled assert:
    $ java -ea -cp ".:./utils.jar" assignment_3.Assignment

# examples
### 1 student, 2 undergraduates, 3 professors:

    $ java -cp ".:./utils.jar" assignment_3.Assignment 1 2 3

### debug prints and tables on stdout:

    $ java -cp ".:./utils.jar" assignment_3.Assignment 1 0 0 --table --debug

### 1 student, 2 undergraduates, 3 professors, debug printouts and stderr redirection on file:

    $ java -cp ".:./utils.jar" assignment_3.Assignment --debug 1 --stderr-to-file 2 3
    

## Documentation
For the documentation see the notes inside the source code or [wiki page](https://github.com/JoeFerri/Laboratorio_di_Reti-A.A.2022-23/wiki)

### Code of conduct
[ENG](code_of_conduct-eng.md)

[ITA](code_of_conduct-ita.md)

# License 

## General license 

GNU Affero General Public License v3.0 only

  Copyright (C) 2022 Giuseppe Ferri

  assignment_3 is free software: you can redistribute it and/or modify
  it under the terms of the GNU Affero General Public License as
  published by the Free Software Foundation, either version 3 of the
  License, or (at your option) any later version.
  
  assignment_3 is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU Affero General Public License for more details.
  
  You should have received a copy of the GNU Affero General Public License
  along with this program (see file COPYING).  If not, see <http://www.gnu.org/licenses/>.
