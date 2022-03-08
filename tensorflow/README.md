# TensorFlow

Component of the project used to train and valitade the model used in the Android application.

## Usage

### Data

First, it is necessary to obtain the data to train the model. The data used to train the model used in this version of the application can be downloaded [here](https://drive.google.com/file/d/1ItVSddwGfeMOpBn2fhIsXMXhPyEHTEpx/view?usp=sharing). In this dataset there are 5 different types of apples:

| Apple Name        | Class Label        | Example                              |
|:-----------------:|:------------------:|:------------------------------------:|
| Pink Lady         | Pink Lady          | ![](https://i.imgur.com/OvTSmPH.png) |
| Royal Gala        | Royal Gala         | ![](https://i.imgur.com/TlKeShE.png) |
| Fuji              | Fuji               | ![](https://i.imgur.com/dn08P0z.png) |
| Golden Delicious  | Golden Delicious   | ![](https://i.imgur.com/5CTGfx9.png) |
| Bravo de Esmolfe  | Bravo de Esmolfe   | ![](https://i.imgur.com/VNEYROQ.png) |


### Train the model

The file [main.py](./main.py) has the script to train and validate the model results. This process is done with the experimental TensorFlow module [tflite_model_maker](https://www.tensorflow.org/lite/api_docs/python/tflite_model_maker), where, at this moment, the model used is [EfficientDet-Lite0](https://arxiv.org/abs/1911.09070).
