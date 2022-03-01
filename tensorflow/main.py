import time
from pprint import pprint

from tflite_model_maker import model_spec
from tflite_model_maker import object_detector

from tflite_support import metadata

import tensorflow as tf
assert tf.__version__.startswith('2')

tf.get_logger().setLevel('ERROR')
from absl import logging
logging.set_verbosity(logging.ERROR)


TRAIN_IMAGES_AND_XML = './data/train'
TEST_IMAGES_AND_XML = './data/test'
#LABEL_MAP = {
#    1:"bravo de esmolfe",
#    2:"pink lady",
#    3:"fuji",
#    4:"golden",
#    5:"royal gala",
#}

LABEL_MAP = {
    1:"pink lady",
    2:"golden",
}

MODEL = 'efficientdet_lite0'

BATCH_SIZE = 4
EPOCH = 100
TRAIN_WHOLE_MODEL = True

EXPORT_PATH = './model'
EXPORT_FILENAME = f'{MODEL}-{time.time()}.tflite'

def main():
    train_data = object_detector.DataLoader.from_pascal_voc(
        images_dir=TRAIN_IMAGES_AND_XML,
        annotations_dir=TRAIN_IMAGES_AND_XML,
        label_map=LABEL_MAP
    )

    val_data = object_detector.DataLoader.from_pascal_voc(
        images_dir=TEST_IMAGES_AND_XML,
        annotations_dir=TEST_IMAGES_AND_XML,
        label_map=LABEL_MAP
    )

    spec = model_spec.get(MODEL)

    model = object_detector.create(
        train_data, 
        model_spec=spec, 
        batch_size=BATCH_SIZE, 
        train_whole_model=TRAIN_WHOLE_MODEL, 
        epochs=EPOCH, 
        validation_data=val_data
    )   

    print(f'\n ***************************** Val Metrics *****************************')
    pprint(model.evaluate(val_data))

    model.export(export_dir=EXPORT_PATH, tflite_filename=EXPORT_FILENAME)


if __name__ == '__main__':
    main()
