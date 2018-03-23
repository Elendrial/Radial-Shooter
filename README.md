# Radial Shooter
This is a very simple 'game' where the player is a circle in the middle of the screen, which can rotate and fire bullets.
Rocks are randomly spawned from the edge of the screen and fly towards you. If you're hit it resets.

The main reason for making this was to test and improve my [other project](https://github.com/hii488/AI-Base-GANN) which provides and implementation of a neural network and genetic algorithm.
As that is the main reason, the human playable part is fairly boring and not worth anyone's time in improving/making function as a game. The other reason was to keep me awake, because apparently coding's the way to go when you need to stay awake, even if it's much harder than usual.

Due to the nature of genetic algorithms there are lots of iterations to test before you can go through to the next generation. This is sped up massively by running them all at once in parallel. Only one is displayed, which one is displayed can be changed.

Settings for the AI part are in the [RadialShooter](../ai/src/me/laurence/RadialShooter.java) class, and what inputs they are given are controlled in the [PlayerEntity](../ai/src/me/laurence/entities/PlayerEntity.java) class. Make sure to change the number of inputs in the RadialShooter class if you change the inputs in the Player class.

Controls for players:
- Left Arrow Key / A = rotate anti clockwise
- Right Arrow Key / D = rotate clockwise
- Space = Shoot

Controls for AI:
- n = display the next 'child'
- b = display the prev 'child'
- s = stop game and save the progress so far. (currently does not load it)
- d = toggles renderDebug, ie all debug info that is rendered on screen
- p = toggles printDebug, ie all debug info that is printed to the console
- t = gives a dialogue box that allows you to change the tickrate for the next generation
- a = toggles whether the shown 'child' will update if the current one dies

<sup> These are controlled in the <a href="https://github.com/hii488/Radial-Shooter/blob/AI/src/me/laurence/radialShooter/graphics/InputHandler.java">InputHandler</a> class</sup>

If you wish to change the rock spawning mechanics, that is all done in [RadialShooter](../ai/src/me/laurence/RadialShooter.java).