# medsys_database
A database for a medic to register apointments

Note: this system was developed for use in portugal and is, therefore, in portuguese only

## How to use
Start by creating a database using CreateDatabase.java in dbutils. This will set up a SQLite database and all the necessary table info.

After this first step, run Main.java inside gui/client. This will start up the system.

This system allows a person to register apointments with all the info needed and update, delete or filter over these apointments.

## Screenshots

Adding an apointment to the database
![](/screenshots/sc_1_adding.PNG)

&nbsp;
&nbsp;

Updating info about an apointment
![](/screenshots/sc_2_update.PNG)

&nbsp;
&nbsp;

Filtering over the apointments' info
![](/screenshots/sc_3_filter.PNG)

&nbsp;
&nbsp;

Filtering over the columns
![](/screenshots/sc_4_column_filter.PNG)

&nbsp;
&nbsp;

## Extra info
Makes use of ORMLite, which can be checked [here](https://ormlite.com/javadoc/ormlite-core/doc-files/ormlite.html)

The jar files used are core and jdbc.

Due to a split packages conflict, there was the need to join these two jar files, using the following commands:

    $ mkdir tmp
    $ cd tmp
    $ unzip -uo ../jar1.jar
    $ unzip -uo ../jar2.jar
    $ cd ..
    $ jar -cvf combined.jar -C tmp .
