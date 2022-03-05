import time
from pprint import pprint

from tflite_model_maker import model_spec
from tflite_model_maker import object_detector

import tensorflow as tf
assert tf.__version__.startswith('2')

tf.get_logger().setLevel('ERROR')
from absl import logging
logging.set_verbosity(logging.ERROR)


TRAIN_IMAGES_AND_XML = './data/train'
TEST_IMAGES_AND_XML = './data/test'
LABEL_MAP = {
    1:"Bravo de Esmolfe",
    2:"Pink Lady",
    3:"Fuji",
    4:"Golden Delicious",
    5:"Royal Gala",
}

MODEL = 'efficientdet_lite0'

BATCH_SIZE = 4
EPOCH = 15
TRAIN_WHOLE_MODEL = True

EXPORT_PATH = './model'

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
    val_metrics = model.evaluate(val_data)
    pprint(val_metrics)
    
    print(val_metrics['AP50'])

    model.export(
        export_dir=EXPORT_PATH, 
        tflite_filename='{model}-AP50-{metric}-{timee}.tflite'.format(
            model=MODEL,
            metric=val_metrics['AP50'],
            timee=time.time()
        )
    )


if __name__ == '__main__':
    main()
