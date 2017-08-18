[![Build Status](https://travis-ci.org/pelensky/clojure_ttt.svg?branch=master)](https://travis-ci.org/pelensky/clojure_ttt)
# Clojure Tic Tac Toe

This is a simple Tic Tac Toe game built in Clojure.

A user can choose to be a player or a spectator of a game.

There are three types of players, and a game can be played by any combination of them:
1. Human
2. Simple Computer
3. Expert Computer

The expert computer
player uses [the API I built using an AWS
Lambda](https://github.com/pelensky/ttt_network_player).

#### Prerequisites 
1. Install [Java](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
2. Install [Homebrew](https://brew.sh/) by running `$ /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"`
3. Install [Clojure](https://clojure.org/guides/getting_started) by running `$ brew install clojure`
4. Install [Leiningen](https://leiningen.org/) by running `$ brew install leiningen`

#### Running instructions
1. Clone the repository by clicking on the green "Clone or Download" button
2. Select Download Zip
3. Double click the zip file to unzip it
4. In terminal, CD into the repository
5. Create an [AWS account](https://aws.amazon.com/) 
6. Set the following Environment Variables. I use
   [EnvPane](https://github.com/hschmidt/EnvPane). `AWS_ACCOUNT_ID`, `AWS_ACCESS_KEY_ID` and `AWS_SECRET_KEY`.
7. Run the app with `$ lein run`

#### The Rules

The rules of tic-tac-toe are as follows:

* There are two players in the game (X and O)
* Players take turns until the game is over
* A player can claim a field if it is not already taken
* A turn ends when a player claims a field
* A player wins if they claim all the fields in a row, column or diagonal
* A game is over if a player wins
* A game is over when all fields are taken
