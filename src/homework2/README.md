# Instructions for Compiling and Running Homework 2 - Practices
==================

Practice 1
------------------
1. Compile with:
```
$ javac pract1/TCPPeer.java
```
2. Open 2 terminal windows and locate in the homework2/ path

3. in Terminal 1 run:
```
$ java pract1/TCPPeer peer1 5000 localhost 5500 0 
```

4. In Terminal 2, run:
```
$ java pract1/TCPPeer peer1 5500 localhost 5000 1
```

5. Start talking between Terminals.

NOTES:
- You can only write in the terminal where there's
  a prompt as follows:
   [<user_name>] >
- The communication between is 1to1, a user cannot send
  2 messages or more messages in a row, he/she needs to wait the
  other to respond. (More than 1 messages in a row is a
  WIP feature)

	 


Practice 2
------------------
1. Compile with:
```
$ javac pract2/UDPPeer.java
```
2. Open 2 terminal windows and locate in the homework2/ path

3. in Terminal 1 run:
```
$ java pract2/UDPPeer peer1 5000 localhost 5500 0
```

4. In Terminal 2, run:
```
$ java pract2/UDPPeer peer1 5500 localhost 5000 1
```

5. Start talking between Terminals.

NOTES:
- You can only write in the terminal where there's
  a prompt as follows:
     [<user_name>] >
- The communication between is 1to1, a user cannot send
  2 messages or more messages in a row, he/she needs to wait the
  other to respond. (More than 1 messages in a row is a
  WIP feature)
