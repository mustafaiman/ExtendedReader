Extended Reader
===============
Extended Reader is a colloborative text editor which enables multiple people to work on the same txt file simultaneously. It uses a server-client design. Server opens a file on his local storage. Chooses a port. Then she shares her ip and port number to the clients. Clients connects to given ip address and port number. Any change made to the file by any collaborator is immediately visible to the others.

This is a project for a freshman course in Bilkent University Computer Science Department.

Known Issues
-------------
Although the system is functional, it does not handle synchronization issues. Multiple collaborators trying to write to the same line simultaneously may mess up the txt file.