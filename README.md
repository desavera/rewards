# rewards
Social Networking Rewarding Engine

## Problem description

A company is planning a way to reward customers for inviting their friends. They're planning a reward system that will
give a customer points for each confirmed invitation they played a part into. The definition of a confirmed invitation is one where another invitation's invitee invited someone.

The inviter gets (1/2)^k points for each confirmed invitation, where k is the level of the invitation: level 0 (people directly invited) yields 1 point, level 1 (people invited by someone invited by the original customer) gives 1/2 points, level 2 invitations (people invited by someone on level 1) awards 1/4 points and so on. Only the first invitation counts: multiple invites sent to the same person don't produce any further points, even if they come from different inviters.

Also, to count as a valid invitation, the invited customer must have invited someone (so customers that didn't invite anyone don't count as points for the customer that invited them).

So, given the input:
1 2
1 3 
3 4
2 4
4 5
4 6

The score is:
1 - 2.5 (2 because he invited 2 and 3 plus 0.5 as 3 invited 4)
3 - 1 (1 as 3 invited 4 and 4 invited someone)
2 - 0 (even as 2 invited 4, it doesn't count as 4 was invited before by 3)
4 - 0 (invited 5 and 6, but 5 and 6 didn't invite anyone)
5 - 0 (no further invites)
6 - 0 (no further invites)

Note that 2 invited 4, but, since 3 invited 4 first, customer 3 gets the points.

Write a program that receives a text file with the input and exposes the ranking on a JSON format on a HTTP endpoint. Also, create another endpoint to add a new invitation.

You should deliver a git repository, or a link to a shared private repository on GitHub, Bitbucket or similar, with your code and a short README file outlining the solution and explaining how to build and run the code. You should follow the functional programming paradigm — Clojure, Common Lisp, Scheme, Haskell, ML, F# and Scala are acceptable languages — and we'll analyse the structure and readability of the codebase. We expect production-grade code. There is no problem in using libraries, for instance for testing or network interaction, but please refrain from using a library that already implements the core algorithms to solve this problem (e.g. tree or graph algorithms).

## Solution

### The Algo

As the input is sorted by invitation order and we want a DAG , we should :

1- Create a separate Map (constant O) to hold the nodes that are part of the network and this will be the output structure with a key as the member id and value as the member score.
2- Build the DAG following the invitation order as the input and adding unique keys to the map from (1).
3- A member node has a flag indicating an invitation has being made already.
3- The insert node method checks for NON INVITED nodes first and is a recursive implementation which calculates K (from the problem definition) going backwards as soon as the member is added the K value is (-1) which means NO ADDITION for points in the first backwards iteration which adds +1 to K. The next backwards iteration K=1 as we are in a node that is a parent to a invitee node at least.

## The endpoints

A single endpoint indicating the calculation function trigged pointing to a file located in the server filesystem.


## Compiling & Running


You will need to install Scala 2.12 !

```
cd [PROJECT_DIR]
./sbt assembly
scala -classpath target/scala-2.12/rewards-api-assembly-0.1.jar com.rewards.web.WebServer

```

then go to http://localhost:8080/health on your browser and check the service health.
