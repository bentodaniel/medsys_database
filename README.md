# medsys_database
A database for a medic to register apointments

Makes use of ORMLite, which can be checked [here](https://ormlite.com/javadoc/ormlite-core/doc-files/ormlite.html)

The jar files used are core and jdbc.

Due to a split packages conflict, there was the need to join these two jar files, using the following commands:

    $ mkdir tmp
    $ cd tmp
    $ unzip -uo ../jar1.jar
    $ unzip -uo ../jar2.jar
    $ cd ..
    $ jar -cvf combined.jar -C tmp .
