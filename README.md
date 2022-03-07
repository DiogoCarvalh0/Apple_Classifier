# Apple Classifier

A small Android application that tells you which type of apple you are looking at 😄.

<img src="/resources/demo.gif?raw=true" alt="Demo" width="250"/>

## Components

### 🤖 [Android](./android/)
The Android project, responsible for capturing the device's camera feed and calling the Tensorflow model with it's frames.

### 🧑‍🔬 [Tensorflow](./tensorflow/)
The project that builds and trains the Apple Tensorflow model used by the Android app.
