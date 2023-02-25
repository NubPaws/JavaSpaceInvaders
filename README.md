# Java Space Invaders

## Table of Contents
- [Motivation](#motivation)
- [Setup](#setup)
  - [Prerequisites](#prerequisites)
  - [Compilation](#compilation)
- [Project Layout](#project-layout)
- [Credits](#credits)

## Motivation
A recreation of the Space Invaders arcade game made in native Java 7. The entire project is self contained, utilizing Java's Swing and AWT libraries. The goal of this project was to build a gaming framework that is native to Java for future projects.

## Setup
In order to run the game you can either download it from the [release page](https://github.com/NubPaws/JavaSpaceInvaders/releases/tag/v1.0) or you can compile it yourself. <br />
You can either run the game by just running the jar file or you can run the game it via the command line using either the `-fullscreen` or the `-nosound` arguments if you'd like to run the game in fullscreen mode or without sounds - for example: <br />
```java -jar SpaceInvaders.jar -fullscreen -nosound```

### Prerequisites
You'll need
* Java 7 or higher.
  * For Unix run `apt-get install default-jre`.
  * For Windows you can download it from [Oracle's Website](https://www.oracle.com/il-en/java/technologies/downloads/).
* Apache's ANT
  * For Unix run `apt-get install ant`.
  * For Windows you can download it from [Apache's Website](https://ant.apache.org/bindownload.cgi).

### Compilation
To compile the project:
1. Clone the project using `git clone https://github.com/NubPaws/JavaSpaceInvaders.git`.
2. Compile and run using `ant run`.

## Credits
All songs credits go to [Eric Skiff](http://ericskiff.com/music/).

All SFX came from [freesound.org](https://freesound.org/).

All images were made by me.

The main font in the game is from [dafont.com](http://www.dafont.com/)
