"""
Renames image and xml files into a random order (from 1 to n images). 
To start assumes that images and xml have the same name and are on the same folder.
Run "validate_xml_files.py" afterwards.

arguments:
    XML_DIR: Path to the xml files
    IMAGE_FORMAT = Format of the imames ('png', 'jpg', 'jpeg', ...)

"""

import glob
import os
import random
import uuid

PATH = './../../data/imgs/'
IMAGE_FORMAT = 'jpg'


def rename_files(save_folder, new_name, image_file, xml_file):
    os.rename(image_file, f'{save_folder}/{new_name}.{IMAGE_FORMAT}')
    os.rename(xml_file, f'{save_folder}/{new_name}.xml')


def main():
    # Give Random name do files to randomly reorder files
    for xml_file, image_file in zip(glob.glob(f'{PATH}*.xml'), glob.glob(f'{PATH}*.{IMAGE_FORMAT}')):

        if not os.path.exists(f'./temp/'):
            os.mkdir(f'./temp/')

        new_name = f'{str(random.random())[2:]}{uuid.uuid1()}'

        rename_files('./temp/', new_name, image_file, xml_file)

    # Rename files in new order
    for number, files in enumerate(zip(glob.glob(f'./temp/*.xml'), glob.glob(f'./temp/*.{IMAGE_FORMAT}'))):
        xml_file, image_file = files

        rename_files(PATH, number, image_file, xml_file)

    os.rmdir('./temp/')


if __name__ == '__main__':
    main()
