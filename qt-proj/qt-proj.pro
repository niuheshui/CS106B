TEMPLATE = subdirs
SUBDIRS += scl-lib helloWorld simple-project1 vectorExample
HOME = $$system(echo ~)/proj
message("HOME: $$HOME")

helloWorld.depends = scl-lib
simple-project1.depends = scl-lib
vectorExample.depends = scl-lib

